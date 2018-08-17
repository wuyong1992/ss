package com.zzc.ss.service;

import com.zzc.ss.entity.JobCategory;

import java.util.List;

/**
 * @author JianGuo
 * on 2018/8/16
 * description:
 */
public interface JobCategoryService {


    /**
     * 新增或者更新工作分类
     * @param jobCategory
     * @return
     */
    JobCategory save(JobCategory jobCategory);

    /**
     * 根据job分类ID删除单个
     * @param categoryId
     */
    void deleteByCategoryId(Integer categoryId);

    /**
     * 获取所有的工作分类
     * @return
     */
    List<JobCategory> getAllCategoryList();

    /**
     * 前端获取显示状态的工作分类
     * @return
     */
    List<JobCategory> getCategoryList();
}
