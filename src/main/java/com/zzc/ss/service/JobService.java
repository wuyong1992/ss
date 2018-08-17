package com.zzc.ss.service;

import com.zzc.ss.entity.JobInfo;
import com.zzc.ss.vo.JobVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author JianGuo
 * on 2018/8/10
 * description:
 */
public interface JobService {

    /**
     * 通过job id 查找jobinfo
     * @param jobId
     * @return
     */
    JobInfo findJobInfoByJobId(Integer jobId);

    /**
     * 获取{@link JobVO}列表
     * @param userId
     * @param pageable
     * @param categoryId
     * @return
     */
    Page<JobVO> getJobVOListWithUserId(Integer userId, Pageable pageable, Integer categoryId);

    /**
     * 获取单个{@link JobVO}信息
     * @param userId
     * @param jobId
     * @return
     */
    JobVO getJobVOWithUserId(Integer userId, Integer jobId);

    /**
     * 新增或者更新某个工作信息
     * @param jobInfo
     * @return
     */
    JobInfo save(JobInfo jobInfo);

    /**
     * 根据工作ID删除工作
     * @param jobId
     */
    void deleteByJobId(Integer jobId);

    /**
     * 后端 获取招聘工作列表
     * @param pageable
     * @param jobCategoryId
     * @param enterpriseId
     * @param status
     * @return
     */
    Page<JobInfo> getJonInfoList(Pageable pageable, String jobCategoryId, String enterpriseId, String status);

}
