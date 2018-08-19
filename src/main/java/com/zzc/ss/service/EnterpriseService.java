package com.zzc.ss.service;

import com.zzc.ss.entity.EnterpriseInfo;
import com.zzc.ss.entity.JobInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author JianGuo
 * on 2018/8/10
 * description:
 */
public interface EnterpriseService {
    /**
     * 通过审核
     * @param enterpriseId
     * @return
     */
    Boolean applySuccess(Integer enterpriseId);

    /**
     * 审核不通过
     * @param enterpriseId
     * @param reason
     * @return
     */
    Boolean applyFail(Integer enterpriseId, String reason);

    /**
     * 根据条件查询企业列表
     * @param pageable
     * @param fullName
     * @param status
     * @return
     */
    Page<EnterpriseInfo> getEnterpriseList(Pageable pageable, String fullName, String status);

    /**
     * 保存企业信息
     *
     * @param userId
     * @param enterpriseInfo
     * @return
     */
    EnterpriseInfo save(Integer userId, EnterpriseInfo enterpriseInfo);

    /**
     * 前端 企业新增一个招聘信息
     * @param enterpriseId
     * @param jobInfo
     * @return
     */
    JobInfo enterpriseSaveJob(Integer enterpriseId, JobInfo jobInfo);

    /**
     * 前端 企业查询自己发布的招聘信息
     * @param enterpriseId
     * @return
     */
    List<JobInfo> getSelfJobList(Integer enterpriseId);

    /**
     * 获取包含主键ID，全称，状态字段的企业信息结合
     * @return
     */
    List<EnterpriseInfo> getSimpleList();

    /**
     * 获取所有
     * @return
     */
    List<EnterpriseInfo> getAllList();
}
