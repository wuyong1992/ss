package com.zzc.ss.service;

import com.zzc.ss.entity.UserInfo;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author JianGuo
 * on 2018/8/10
 * description:
 */
public interface UserService {

    /**
     * 新增或者更新用户
     *
     * @param userInfo
     * @return
     */
    UserInfo save(UserInfo userInfo);


    /**
     * 根据OPENID 查找用户
     *
     * @param openid
     * @return
     */
    UserInfo findByOpenid(String openid);

    /**
     * 微信用户关注
     *
     * @param wxMpUser
     */
    void subscribe(WxMpUser wxMpUser);

    /**
     * 用户取消关注
     *
     * @param openId
     */
    void unsubscribeByOpenid(String openId);


    /**
     * 微信网页授权，获取用户信息并返回token
     *
     * @param wxMpUser
     * @return
     */
    String wechatWebAuthAndReturnToken(WxMpUser wxMpUser);

    /**
     * 申请工作
     *
     * @param userId 用户ID
     * @param jobId  工作ID
     */
    void applyJob(Integer userId, Integer jobId);

    /**
     * 通过用户ID查找用户
     * @param userId
     * @return
     */
    UserInfo getUserInfoByUserId(Integer userId);

    /**
     * 根据条件分页查询用户列表
     * @param pageable
     * @param nickname
     * @param realName
     * @param phone
     * @param subscribeStatus
     * @return
     */
    Page<UserInfo> getUserList(Pageable pageable, String nickname, String realName, String phone, String subscribeStatus);

    /**
     * 校验token有效性
     * @param token
     * @return
     */
    Boolean checkToken(String token);

    /**
     * 从用户ID中获取企业ID
     * @param userId
     * @return
     */
    Integer getEnterpriseIdFromUserId(Integer userId);
}
