package com.zzc.ss.repository;

import com.zzc.ss.entity.JobInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author JianGuo
 * on 2018/8/10
 * description:
 */
public interface JobInfoRepository extends JpaRepository<JobInfo,Integer>, JpaSpecificationExecutor<JobInfo> {


    /**
     * 根据工作分类ID来清除工作中的工作分类ID
     * @param categoryId
     */
    @Query("update JobInfo j set j.jobCategoryId = null where j.jobCategoryId = ?1")
    void clearCategoryId(Integer categoryId);

    /**
     * 根据企业ID查询招聘信息列表
     * @param enterpriseId
     * @return
     */
    List<JobInfo> findAllByEnterpriseId(Integer enterpriseId);
}
