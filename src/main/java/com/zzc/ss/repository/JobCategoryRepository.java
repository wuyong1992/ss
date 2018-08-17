package com.zzc.ss.repository;

import com.zzc.ss.entity.JobCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author JianGuo
 * on 2018/8/10
 * description:
 */
public interface JobCategoryRepository extends JpaRepository<JobCategory,Integer> {


    /**
     * 根据装填获取分类集合
     * @param status
     * @return
     */
    List<JobCategory> findAllByStatus(Integer status);


}
