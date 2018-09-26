package com.zzc.ss.service.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.zzc.ss.common.Const;
import com.zzc.ss.entity.JobCategory;
import com.zzc.ss.entity.JobInfo;
import com.zzc.ss.entity.UserJob;
import com.zzc.ss.enums.JobPayPeriodEnum;
import com.zzc.ss.enums.JobStatusEnum;
import com.zzc.ss.exception.JobNotExistException;
import com.zzc.ss.repository.EnterpriseInfoRepository;
import com.zzc.ss.repository.JobCategoryRepository;
import com.zzc.ss.repository.JobInfoRepository;
import com.zzc.ss.repository.UserJobRepository;
import com.zzc.ss.service.JobInfoBackupService;
import com.zzc.ss.service.JobService;
import com.zzc.ss.vo.JobVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @author JianGuo
 * on 2018/8/10
 * description:
 */
@Service("jobService")
@Slf4j
public class JobServiceImpl implements JobService {


    @Autowired
    private JobInfoRepository jobInfoRepository;

    @Autowired
    private JobInfoBackupService jobInfoBackupService;

    @Autowired
    private UserJobRepository userJobRepository;

    @Autowired
    private JobCategoryRepository jobCategoryRepository;

    @Autowired
    private EnterpriseInfoRepository enterpriseInfoRepository;

    private Integer userId;


    @Override
    public JobInfo findJobInfoByJobId(Integer jobId) {
        return jobInfoRepository.findById(jobId).get();
    }

    @Override
    public Page<JobVO> getJobVOListWithUserId(Integer userId, Pageable pageable, String jobCategoryId, String payPeriodType) {

        Specification<JobInfo> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicateList = Lists.newArrayList();
            // 查询状态为已通过审核的job
            predicateList.add(criteriaBuilder.equal(root.get("status"), JobStatusEnum.APPLY_PASS.getCode()));
            if (!Strings.isNullOrEmpty(jobCategoryId)) {
                predicateList.add(criteriaBuilder.equal(root.get("jobCategoryId"), Integer.parseInt(jobCategoryId)));
            }
            if (!Strings.isNullOrEmpty(payPeriodType)) {
                predicateList.add(criteriaBuilder.equal(root.get("payPeriodType"), Integer.parseInt(payPeriodType)));
            }
            int size = predicateList.size();
            return criteriaBuilder.and(predicateList.toArray(new Predicate[size]));
        };

        Page<JobInfo> page = jobInfoRepository.findAll(specification, pageable);

        // TODO: 2018/8/15 是否可以采用成员变量的方式 ？？？
        this.userId = userId;
        return page.map(this::jobInfoConvertVO);
    }

    @Override
    public JobVO getJobVOWithUserId(Integer userId, Integer jobId) {
        JobInfo jobInfo = jobInfoRepository.getOne(jobId);
        if (jobInfo == null) {
            throw new JobNotExistException();
        }
        JobVO jobVO = new JobVO();
        BeanUtils.copyProperties(jobInfo, jobVO);
        Integer payPeriodType = jobVO.getPayPeriodType();
        if (payPeriodType != null) {
            jobVO.setPayPeriodTypeStr(JobPayPeriodEnum.codeOf(payPeriodType).getValue());
        }
        UserJob userJob = userJobRepository.findByUserIdAndJobId(userId, jobInfo.getJobId());
        if (userJob != null) {
            jobVO.setUserApplyStatus(userJob.getStatus());
        }
        Integer jobCategoryId = jobInfo.getJobCategoryId();
        if (jobCategoryId != null) {
            JobCategory jobCategory = jobCategoryRepository.getOne(jobCategoryId);
            jobVO.setJobCategoryName(jobCategory.getCategoryName());
        }

        Integer enterpriseId = jobInfo.getEnterpriseId();
        if (enterpriseId != null) {
            String fullName = enterpriseInfoRepository.selectEnterpriseFullNameById(enterpriseId);
            jobVO.setEnterpriseName(fullName);
        }

        log.info("获取单个工作详情success");
        return jobVO;
    }

    @Override
    public JobInfo save(JobInfo jobInfo) {
        return jobInfoRepository.save(jobInfo);
    }

    @Override
    public void deleteByJobId(Integer jobId) {
        // backup 再删除
        jobInfoBackupService.backupJobInfo(jobId);

        jobInfoRepository.deleteById(jobId);
    }

    @Override
    public Page<JobVO> getJonInfoList(Pageable pageable, String jobCategoryId, String enterpriseId, String status) {
        Specification<JobInfo> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicateList = Lists.newArrayList();
            if (!Strings.isNullOrEmpty(jobCategoryId)) {
                predicateList.add(criteriaBuilder.equal(root.get("jobCategoryId"), Integer.parseInt(jobCategoryId)));
            }
            if (!Strings.isNullOrEmpty(enterpriseId)) {
                predicateList.add(criteriaBuilder.equal(root.get("enterpriseId"), Integer.parseInt(enterpriseId)));
            }
            if (!Strings.isNullOrEmpty(status)) {
                predicateList.add(criteriaBuilder.equal(root.get("status"), Integer.parseInt(status)));
            }

            int size = predicateList.size();
            return criteriaBuilder.and(predicateList.toArray(new Predicate[size]));
        };

        Page<JobInfo> page = jobInfoRepository.findAll(specification, pageable);
        Page<JobVO> jobVOPage = page.map(this::jobInfoConvertVO);
        this.userId = null;
        return jobVOPage;
    }

    @Override
    public Boolean applySuccessByJobId(Integer jobId) {
        JobInfo jobInfo = jobInfoRepository.getOne(jobId);
        if (jobInfo == null) {
            throw new JobNotExistException();
        }
        jobInfo.setStatus(JobStatusEnum.APPLY_PASS.getCode());
        jobInfoRepository.save(jobInfo);
        return true;
    }

    @Override
    public Boolean applyFailByJobId(Integer jobId) {
        JobInfo jobInfo = jobInfoRepository.getOne(jobId);
        if (jobInfo == null) {
            throw new JobNotExistException();
        }
        jobInfo.setStatus(JobStatusEnum.APPLE_FAIL.getCode());
        jobInfoRepository.save(jobInfo);
        return true;
    }

    @Override
    public Boolean hideByJobId(Integer jobId) {
        JobInfo jobInfo = jobInfoRepository.getOne(jobId);
        if (jobInfo == null) {
            throw new JobNotExistException();
        }
        jobInfo.setStatus(JobStatusEnum.FORBIDDEN.getCode());
        jobInfoRepository.save(jobInfo);
        return true;
    }

    @Override
    public Future<String> browseNumPlus(Integer jobId) {
        if (jobId == null) {
            return new AsyncResult<>(Const.ASYNC_RESULT_SUCCESS);
        }
        JobInfo jobInfo = jobInfoRepository.findById(jobId).orElse(null);
        if (jobInfo == null) {
            return new AsyncResult<>(Const.ASYNC_RESULT_SUCCESS);
        }
        jobInfo.setBrowseNum(jobInfo.getBrowseNum() + 1);
        jobInfoRepository.save(jobInfo);

        return new AsyncResult<>(Const.ASYNC_RESULT_SUCCESS);
    }

    @Override
    public Future<String> applyNumPlus(Integer jobId) {
        if (jobId == null) {
            return new AsyncResult<>(Const.ASYNC_RESULT_SUCCESS);
        }
        JobInfo jobInfo = jobInfoRepository.findById(jobId).orElse(null);
        if (jobInfo == null) {
            return new AsyncResult<>(Const.ASYNC_RESULT_SUCCESS);
        }
        jobInfo.setApplyNum(jobInfo.getApplyNum() + 1);
        jobInfoRepository.save(jobInfo);

        return new AsyncResult<>(Const.ASYNC_RESULT_SUCCESS);
    }

    private JobVO jobInfoConvertVO(JobInfo jobInfo) {

        JobVO jobVO = new JobVO();
        BeanUtils.copyProperties(jobInfo, jobVO);
        Integer payPeriodType = jobVO.getPayPeriodType();
        if (payPeriodType != null) {
            jobVO.setPayPeriodTypeStr(JobPayPeriodEnum.codeOf(payPeriodType).getValue());
        }

        if (this.userId != null) {
            UserJob userJob = userJobRepository.findByUserIdAndJobId(userId, jobInfo.getJobId());
            if (userJob != null) {
                jobVO.setUserApplyStatus(userJob.getStatus());
            }
        }

        Integer jobCategoryId = jobInfo.getJobCategoryId();
        if (jobCategoryId != null) {
            JobCategory jobCategory = jobCategoryRepository.getOne(jobCategoryId);
            jobVO.setJobCategoryName(jobCategory.getCategoryName());
        }

        Integer enterpriseId = jobInfo.getEnterpriseId();
        if (enterpriseId != null) {
            String fullName = enterpriseInfoRepository.selectEnterpriseFullNameById(enterpriseId);
            jobVO.setEnterpriseName(fullName);
        }

        return jobVO;
    }


}
