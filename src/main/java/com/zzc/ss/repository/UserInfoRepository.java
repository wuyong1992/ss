package com.zzc.ss.repository;

import com.zzc.ss.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * @author JianGuo
 * on 2018/8/10
 * description:
 */
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer>, JpaSpecificationExecutor<UserInfo> {

    /**
     * 根据OPENID查找用户
     *
     * @param openid
     * @return
     */
    UserInfo findByOpenid(String openid);


    /**
     * 从用户ID中获取企业ID
     * @param userId
     * @return
     */
    @Query("select u.enterpriseId from UserInfo u where u.userId = ?1")
    Integer selectEnterpriseIdByUserId(Integer userId);
}
