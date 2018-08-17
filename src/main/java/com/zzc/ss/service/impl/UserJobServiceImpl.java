package com.zzc.ss.service.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.zzc.ss.entity.UserInfo;
import com.zzc.ss.entity.UserJob;
import com.zzc.ss.enums.UserApplyJobStatusEnum;
import com.zzc.ss.exception.UserJobNotExistException;
import com.zzc.ss.exception.UserJobNotInApplyingException;
import com.zzc.ss.repository.EnterpriseInfoRepository;
import com.zzc.ss.repository.JobInfoRepository;
import com.zzc.ss.repository.UserInfoRepository;
import com.zzc.ss.repository.UserJobRepository;
import com.zzc.ss.service.UserJobService;
import com.zzc.ss.vo.UserJobVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * @author JianGuo
 * on 2018/8/16
 * description:
 */
@Service("userJobService")
public class UserJobServiceImpl implements UserJobService {


    @Autowired
    private UserJobRepository userJobRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private JobInfoRepository jobInfoRepository;

    @Autowired
    private EnterpriseInfoRepository enterpriseInfoRepository;


    @Override
    public Page<UserJobVO> getUserJobVOList(Pageable pageable, String realName, String phone, String enterpriseFullName, String status) {

        Specification<UserJob> userJobSpecification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicateList = Lists.newArrayList();
            if (!Strings.isNullOrEmpty(realName)) {
                predicateList.add(criteriaBuilder.like(root.get("realName"), "%" + realName + "%"));
            }
            if (!Strings.isNullOrEmpty(phone)) {
                predicateList.add(criteriaBuilder.like(root.get("realName"), "%" + phone + "%"));
            }
            if (!Strings.isNullOrEmpty(enterpriseFullName)) {
                List<Integer> enterpriseIdList = enterpriseInfoRepository.selectEnterpriseIdByFullNameLike(enterpriseFullName);
                if (CollectionUtils.isNotEmpty(enterpriseIdList)) {
                    CriteriaBuilder.In<Integer> in = criteriaBuilder.in(root.get("enterpriseId"));
                    for (Integer enterpriseId : enterpriseIdList) {
                        in.value(enterpriseId);
                    }
                    predicateList.add(criteriaBuilder.and(in));
                }
            }
            if (!Strings.isNullOrEmpty(status)) {
                Integer intStatus = Integer.parseInt(status);
                predicateList.add(criteriaBuilder.equal(root.get("status"), intStatus));
            }

            int size = predicateList.size();
            return criteriaBuilder.and(predicateList.toArray(new Predicate[size]));
        };

        Page<UserJob> page = userJobRepository.findAll(userJobSpecification, pageable);

        return null;
    }

    @Override
    public Boolean applySuccess(Integer id) {
        UserJob userJob = userJobRepository.getOne(id);
        if (userJob == null) {
            throw new UserJobNotExistException();
        }
        if (!userJob.getStatus().equals(UserApplyJobStatusEnum.IN_APPLYING.getCode())) {
            throw new UserJobNotInApplyingException();
        }

        // TODO: 2018/8/16 判断job是否还有效

        userJob.setStatus(UserApplyJobStatusEnum.APPLY_PASS.getCode());
        userJob.setApplySuccessCount(userJob.getApplySuccessCount() + 1);
        userJobRepository.save(userJob);

        return true;
    }

    @Override
    public Boolean applyFail(Integer id) {
        UserJob userJob = userJobRepository.getOne(id);
        if (userJob == null) {
            throw new UserJobNotExistException();
        }
        if (!userJob.getStatus().equals(UserApplyJobStatusEnum.IN_APPLYING.getCode())) {
            throw new UserJobNotInApplyingException();
        }

        // TODO: 2018/8/16 判断job是否还有效

        userJob.setStatus(UserApplyJobStatusEnum.APPLE_FAIL.getCode());
        userJobRepository.save(userJob);
        return true;
    }
}
