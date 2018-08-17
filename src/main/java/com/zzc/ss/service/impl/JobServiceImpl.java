package com.zzc.ss.service.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.zzc.ss.entity.JobInfo;
import com.zzc.ss.entity.UserJob;
import com.zzc.ss.enums.JobStatusEnum;
import com.zzc.ss.exception.JobNotExistException;
import com.zzc.ss.repository.JobInfoRepository;
import com.zzc.ss.repository.UserJobRepository;
import com.zzc.ss.service.JobInfoBackupService;
import com.zzc.ss.service.JobService;
import com.zzc.ss.vo.JobVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * @author JianGuo
 * on 2018/8/10
 * description:
 */
@Service("jobService")
public class JobServiceImpl implements JobService {


    @Autowired
    private JobInfoRepository jobInfoRepository;

    @Autowired
    private JobInfoBackupService jobInfoBackupService;

    @Autowired
    private UserJobRepository userJobRepository;

    private Integer userId;


    @Override
    public JobInfo findJobInfoByJobId(Integer jobId) {
        return jobInfoRepository.getOne(jobId);
    }

    @Override
    public Page<JobVO> getJobVOListWithUserId(Integer userId, Pageable pageable, Integer categoryId) {

        Specification<JobInfo> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicateList = Lists.newArrayList();
            // 查询状态为已通过审核的job
            predicateList.add(criteriaBuilder.equal(root.get("status"), JobStatusEnum.APPLY_PASS.getCode()));
            if (categoryId != null) {
                predicateList.add(criteriaBuilder.equal(root.get("jobCategoryId"), categoryId));
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
        UserJob userJob = userJobRepository.findByUserIdAndJobId(userId, jobInfo.getJobId());
        if (userJob != null) {
            jobVO.setUserApplyStatus(userJob.getStatus());
        }
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
    public Page<JobInfo> getJonInfoList(Pageable pageable, String jobCategoryId, String enterpriseId, String status) {
        Specification<JobInfo> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicateList = Lists.newArrayList();
            if (!Strings.isNullOrEmpty(jobCategoryId)) {
                predicateList.add(criteriaBuilder.equal(root.get("jobCategoryId"), Integer.parseInt(jobCategoryId)));
            }
            if (!Strings.isNullOrEmpty(enterpriseId)) {
                predicateList.add(criteriaBuilder.equal(root.get("enterpriseId"), Integer.parseInt(enterpriseId)));
            }
            if (!Strings.isNullOrEmpty(status)) {
                predicateList.add(criteriaBuilder.equal(root.get("enterpriseId"), Integer.parseInt(status)));
            }

            int size = predicateList.size();
            return criteriaBuilder.and(predicateList.toArray(new Predicate[size]));
        };

        return jobInfoRepository.findAll(specification,pageable);
    }

    private JobVO jobInfoConvertVO(JobInfo jobInfo) {

        JobVO jobVO = new JobVO();
        BeanUtils.copyProperties(jobInfo, jobVO);
        UserJob userJob = userJobRepository.findByUserIdAndJobId(userId, jobInfo.getJobId());
        if (userJob != null) {
            jobVO.setUserApplyStatus(userJob.getStatus());
        }
        return jobVO;
    }


}
