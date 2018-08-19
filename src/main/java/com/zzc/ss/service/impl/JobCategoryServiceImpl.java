package com.zzc.ss.service.impl;

import com.zzc.ss.entity.JobCategory;
import com.zzc.ss.enums.JobCategoryStatusEnum;
import com.zzc.ss.exception.JobCategoryNotExistException;
import com.zzc.ss.repository.JobCategoryRepository;
import com.zzc.ss.repository.JobInfoRepository;
import com.zzc.ss.service.JobCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author JianGuo
 * on 2018/8/16
 * description:
 */
@Service("jobCategoryService")
public class JobCategoryServiceImpl implements JobCategoryService {

    @Autowired
    private JobCategoryRepository jobCategoryRepository;

    @Autowired
    private JobInfoRepository jobInfoRepository;

    @Override
    public JobCategory save(JobCategory jobCategory) {
        return jobCategoryRepository.save(jobCategory);
    }

    @Override
    public void deleteByCategoryId(Integer categoryId) {
        jobCategoryRepository.deleteById(categoryId);

        jobInfoRepository.clearCategoryId(categoryId);
    }

    @Override
    public List<JobCategory> getAllCategoryList() {
        return jobCategoryRepository.findAll(new Sort(Sort.Direction.DESC, "sort"));
    }

    @Override
    public List<JobCategory> getCategoryList() {
        return jobCategoryRepository.findAllByStatus(JobCategoryStatusEnum.ENABLED.getCode());
    }

    @Override
    public JobCategory getById(Integer categoryId) {
        JobCategory jobCategory = jobCategoryRepository.getOne(categoryId);
        if (jobCategory == null) {
            throw new JobCategoryNotExistException();
        }
        return jobCategory;
    }

}
