package com.zzc.ss.repository;

import com.zzc.ss.entity.UserJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author JianGuo
 * on 2018/8/15
 * description:
 */
public interface UserJobRepository extends JpaRepository<UserJob, Integer>, JpaSpecificationExecutor<UserJob> {


    /**
     * 根据用户ID和工作ID 查找申请记录
     * @param userId
     * @param jobId
     * @return
     */
    UserJob findByUserIdAndJobId(Integer userId, Integer jobId);


}
