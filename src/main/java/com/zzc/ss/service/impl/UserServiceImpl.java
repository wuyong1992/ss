package com.zzc.ss.service.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zzc.ss.common.Const;
import com.zzc.ss.entity.JobInfo;
import com.zzc.ss.entity.UserInfo;
import com.zzc.ss.entity.UserJob;
import com.zzc.ss.enums.InfoCompleteStatusEnum;
import com.zzc.ss.enums.UserApplyJobStatusEnum;
import com.zzc.ss.enums.UserSubscribeEnum;
import com.zzc.ss.exception.*;
import com.zzc.ss.repository.UserInfoRepository;
import com.zzc.ss.repository.UserJobRepository;
import com.zzc.ss.service.JobService;
import com.zzc.ss.service.UserService;
import com.zzc.ss.utils.GsonUtil;
import com.zzc.ss.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author JianGuo
 * on 2018/8/10
 * description:
 */
@Service("userService")
@Slf4j
public class UserServiceImpl implements UserService {


    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private JobService jobService;

    @Autowired
    private UserJobRepository userJobRepository;

    @Override
    public UserInfo save(UserInfo userInfo) {
        userInfo.setIsInfoComplete(InfoCompleteStatusEnum.YES.getCode());
        return userInfoRepository.save(userInfo);
    }

    @Override
    public UserInfo findByOpenid(String openid) {
        return userInfoRepository.findByOpenid(openid);
    }

    @Override
    public void subscribe(WxMpUser wxMpUser) {
        saveUserFromWxMpUser(wxMpUser);
    }


    @Override
    public void unsubscribeByOpenid(String openId) {
        UserInfo userInfo = userInfoRepository.findByOpenid(openId);
        if (userInfo != null) {
            userInfo.setSubscribeStatus(UserSubscribeEnum.UN_SUBSCRIBE.getCode());
            userInfoRepository.save(userInfo);
        }
    }

    @Override
    public String wechatWebAuthAndReturnToken(WxMpUser wxMpUser) {
        UserInfo userInfo = this.saveUserFromWxMpUser(wxMpUser);

        // 构建TOKEN
        String token;
        try {
            token = this.createToken(userInfo);
        } catch (Exception e) {
            log.error(Const.ExceptionMessage.TOKEN_CREATE_FAIL + "，用户openid为：" + wxMpUser.getOpenId());
            throw new TokenCreateException();
        }

        return token;
    }

    @Override
    public void applyJob(Integer userId, Integer jobId) {
        JobInfo jobinfo = jobService.findJobInfoByJobId(jobId);
        if (jobinfo == null) {
            throw new JobNotExistException();
        }

        UserInfo userInfo = userInfoRepository.getOne(userId);
        if (userInfo.getPhone() == null) {
            throw new InfoNotCompleteException();
        }

        // TODO: 2018/8/15 判断job是否已经过期

        UserJob userJob = userJobRepository.findByUserIdAndJobId(userId, jobId);
        if (userJob != null) {
            if (userJob.getStatus().equals(UserApplyJobStatusEnum.IN_APPLYING.getCode())) {
                throw new JobInApplyingException();
            }

            // 申请通过和申请失败都可以再次申请
            userJob.setApplyCount(userJob.getApplyCount() + 1);
        } else {
            userJob = UserJob.builder()
                    .enterpriseId(jobinfo.getEnterpriseId())
                    .userId(userId)
                    .jobId(jobId)
                    .applyCount(1)
                    .status(UserApplyJobStatusEnum.IN_APPLYING.getCode())
                    .createTime(new Date())
                    .build();
        }
        userJobRepository.save(userJob);

    }

    @Override
    public UserInfo getUserInfoByUserId(Integer userId) {
        UserInfo userInfo = userInfoRepository.getOne(userId);
        if (userInfo == null) {
            throw new UserNotExistException();
        }
        return userInfo;
    }

    @Override
    public Page<UserInfo> getUserList(Pageable pageable, String nickname, String realName, String phone, String subscribeStatus) {

        Specification<UserInfo> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicateList = Lists.newArrayList();
            if (!Strings.isNullOrEmpty(nickname)) {
                predicateList.add(criteriaBuilder.like(root.get("nickname"), "%" + nickname + "%"));
            }
            if (!Strings.isNullOrEmpty(realName)) {
                predicateList.add(criteriaBuilder.like(root.get("nickname"), "%" + realName + "%"));
            }
            if (!Strings.isNullOrEmpty(phone)) {
                predicateList.add(criteriaBuilder.like(root.get("phone"), "%" + phone + "%"));
            }
            if (!Strings.isNullOrEmpty(subscribeStatus)) {
                Integer subStatus = Integer.valueOf(subscribeStatus);
                predicateList.add(criteriaBuilder.equal(root.get("subscribeStatus"), subStatus));
            }
            int size = predicateList.size();
            return criteriaBuilder.and(predicateList.toArray(new Predicate[size]));
        };
        return userInfoRepository.findAll(specification, pageable);
    }

    @Override
    public Boolean checkToken(String token) {
        Integer userId = TokenUtil.getUserIdFromToken(token);
        UserInfo userInfo = userInfoRepository.getOne(userId);
        return userInfo != null;
    }

    @Override
    public Integer getEnterpriseIdFromUserId(Integer userId) {
        Integer enterpriseId = userInfoRepository.selectEnterpriseIdByUserId(userId);
        if (enterpriseId == null) {
            throw new UserNotHaveEnterpriseException();
        }
        return enterpriseId;
    }

    private String createToken(UserInfo userInfo) throws Exception {
        String tokenId = String.valueOf(System.currentTimeMillis()) + "-" + String.valueOf(userInfo.getUserId());

        Map<String, Object> claims = Maps.newHashMap();
        claims.put(Const.UserAuthJwtTokenClaims.USER_ID, userInfo.getUserId());
        claims.put(Const.UserAuthJwtTokenClaims.OPENID, userInfo.getUserId());
        claims.put(Const.UserAuthJwtTokenClaims.UNIONID, userInfo.getUnionid());
        claims.put(Const.UserAuthJwtTokenClaims.PHONE, userInfo.getPhone());

        String subject = userInfo.getNickname();

        // 创建时间的毫秒数-用户ID + claims + 用户的昵称
        return TokenUtil.createJWT(tokenId, claims, subject);
    }


    private UserInfo saveUserFromWxMpUser(WxMpUser wxMpUser) {
        String openId = wxMpUser.getOpenId();
        UserInfo userInfo = this.findByOpenid(openId);
        if (userInfo == null) {
            // 该用户没授权过或者没有改用户的信息
            userInfo = new UserInfo();
            userInfo.setOpenid(openId);
        }
        if (userInfo.getPhone() == null) {
            userInfo.setIsInfoComplete(InfoCompleteStatusEnum.NO.getCode());
        }
        userInfo.setUnionid(wxMpUser.getUnionId())
                .setSex(wxMpUser.getSex())
                .setProvince(wxMpUser.getProvince())
                .setCity(wxMpUser.getCity())
                .setCountry(wxMpUser.getCountry())
                .setPrivilege(GsonUtil.objectToJson(wxMpUser.getPrivileges()))
                .setNickname(wxMpUser.getNickname())
                .setHeadimgurl(wxMpUser.getHeadImgUrl())
                .setSubscribeStatus(UserSubscribeEnum.SUBSCRIBE.getCode());
        return this.save(userInfo);
    }
}
