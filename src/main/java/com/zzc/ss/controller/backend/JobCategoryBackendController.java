package com.zzc.ss.controller.backend;

import com.zzc.ss.common.Const;
import com.zzc.ss.common.ServerResponse;
import com.zzc.ss.entity.JobCategory;
import com.zzc.ss.service.JobCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author JianGuo
 * on 2018/8/14
 * description:
 */
@RestController
@RequestMapping("backend/job-category")
@Validated
@Api(description = "工作分类后端接口")
public class JobCategoryBackendController {

    @Autowired
    private JobCategoryService jobCategoryService;


    @PostMapping()
    @ApiOperation(value = "新增或者更新工作分类")
    public ServerResponse save(@RequestBody JobCategory jobCategory) {
        JobCategory result = jobCategoryService.save(jobCategory);
        return ServerResponse.createBySuccess(Const.ExecuteResultMessage.SAVE_SUCCESS, result);
    }

    @DeleteMapping("{categoryId}")
    @ApiOperation(value = "根据分类ID删除单个分类")
    @ApiImplicitParam(name = "categoryId", value = "工作分类ID", dataType = "int", paramType = "path")
    public ServerResponse deleteByCategoryId(@PathVariable Integer categoryId){
        jobCategoryService.deleteByCategoryId(categoryId);
        return ServerResponse.createBySuccessMsg(Const.ExecuteResultMessage.OPERATE_SUCCESS);
    }

    @GetMapping("list")
    @ApiOperation(value = "获取所有分类信息")
    public ServerResponse<List<JobCategory>> getCategoryList(){
        List<JobCategory> list = jobCategoryService.getAllCategoryList();
        return ServerResponse.createBySuccess(Const.ExecuteResultMessage.OPERATE_SUCCESS, list);
    }

    @GetMapping("{categoryId}")
    @ApiOperation(value = "获取单个分类信息")
    public ServerResponse<JobCategory> getCategoryList(@PathVariable Integer categoryId){
       JobCategory jobCategory = jobCategoryService.getById(categoryId);
        return ServerResponse.createBySuccess(Const.ExecuteResultMessage.OPERATE_SUCCESS, jobCategory);
    }


















}
