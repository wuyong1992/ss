package com.zzc.ss.service.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.zzc.ss.entity.EnterpriseInfo;
import com.zzc.ss.entity.JobInfo;
import com.zzc.ss.entity.UserInfo;
import com.zzc.ss.enums.EnterpriseInfoStatusEnum;
import com.zzc.ss.enums.InfoCompleteStatusEnum;
import com.zzc.ss.enums.JobStatusEnum;
import com.zzc.ss.exception.EnterPriseNotExistException;
import com.zzc.ss.exception.InfoNotCompleteException;
import com.zzc.ss.repository.EnterpriseInfoRepository;
import com.zzc.ss.repository.JobInfoRepository;
import com.zzc.ss.repository.UserInfoRepository;
import com.zzc.ss.service.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * @author JianGuo
 * on 2018/8/10
 * description:
 */
@Service("enterprise")
public class EnterpriseServiceImpl implements EnterpriseService {

    @Autowired
    private EnterpriseInfoRepository enterpriseInfoRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private JobInfoRepository jobInfoRepository;

    @Override
    public Boolean applySuccess(Integer enterpriseId) {
        EnterpriseInfo enterpriseInfo = enterpriseInfoRepository.getOne(enterpriseId);
        if (enterpriseInfo == null) {
            throw new EnterPriseNotExistException();
        }
        enterpriseInfo.setStatus(EnterpriseInfoStatusEnum.APPLY_PASS.getCode());
        enterpriseInfo.setStatusReason("");
        enterpriseInfoRepository.save(enterpriseInfo);
        return true;
    }

    @Override
    public Boolean applyFail(Integer enterpriseId, String reason) {
        EnterpriseInfo enterpriseInfo = enterpriseInfoRepository.getOne(enterpriseId);
        if (enterpriseInfo == null) {
            throw new EnterPriseNotExistException();
        }
        enterpriseInfo.setStatus(EnterpriseInfoStatusEnum.APPLY_FAIL.getCode());
        enterpriseInfo.setStatusReason(reason);
        enterpriseInfoRepository.save(enterpriseInfo);
        return true;

    }

    @Override
    public Page<EnterpriseInfo> getEnterpriseList(Pageable pageable, String fullName, String status) {
        Specification<EnterpriseInfo> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicateList = Lists.newArrayList();
            if (!Strings.isNullOrEmpty(fullName)) {
                predicateList.add(criteriaBuilder.like(root.get("fullName"), "%" + fullName + "%"));
            }
            if (!Strings.isNullOrEmpty(status)) {
                predicateList.add(criteriaBuilder.equal(root.get("status"), Integer.parseInt(status)));
            }

            int size = predicateList.size();
            return criteriaBuilder.and(predicateList.toArray(new Predicate[size]));
        };

        return enterpriseInfoRepository.findAll(specification, pageable);
    }

    @Override
    public EnterpriseInfo save(Integer userId, EnterpriseInfo enterpriseInfo) {
        enterpriseInfo.setStatus(EnterpriseInfoStatusEnum.IN_APPLYING.getCode());
        EnterpriseInfo result = enterpriseInfoRepository.save(enterpriseInfo);
        if (result != null) {
            // 企业和用户关联起来
            UserInfo userInfo = userInfoRepository.getOne(userId);
            userInfo.setEnterpriseId(result.getEnterpriseId());
            userInfoRepository.save(userInfo);
        }
        return result;
    }

    @Override
    public JobInfo enterpriseSaveJob(Integer enterpriseId, JobInfo jobInfo) {
        EnterpriseInfo enterpriseInfo = enterpriseInfoRepository.getOne(enterpriseId);
        if (InfoCompleteStatusEnum.YES.getCode().equals(enterpriseInfo.getIsInfoComplete())) {
            throw new InfoNotCompleteException();
        }
        jobInfo.setEnterpriseId(enterpriseId);
        jobInfo.setStatus(JobStatusEnum.IN_APPLYING.getCode());
        return jobInfoRepository.save(jobInfo);
    }

    @Override
    public List<JobInfo> getSelfJobList(Integer enterpriseId) {
        return jobInfoRepository.findAllByEnterpriseId(enterpriseId);
    }

    @Override
    public List<EnterpriseInfo> getSimpleList() {
        return enterpriseInfoRepository.selectSimpleList();
    }

    @Override
    public List<EnterpriseInfo> getAllList() {
        return enterpriseInfoRepository.findAll(new Sort(Sort.Direction.DESC, "sort"));
    }

}
