package com.zzc.ss.controller.frontend;

import com.zzc.ss.common.Const;
import com.zzc.ss.common.ServerResponse;
import com.zzc.ss.entity.JobCategory;
import com.zzc.ss.service.JobCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author JianGuo
 * on 2018/8/14
 * description:
 */
@RestController
@RequestMapping("frontend/user")
@Validated
@Api(description = "工作分类前端接口")
public class JobCategoryController {

    @Autowired
    private JobCategoryService jobCategoryService;

    @GetMapping("list")
    @ApiOperation(value = "前端获取工作分类集合")
    public ServerResponse<List<JobCategory>> getJobCategoryList(){
        List<JobCategory> list = jobCategoryService.getCategoryList();
        return ServerResponse.createBySuccess(Const.ExecuteResultMessage.QUERY_SUCCESS, list);
    }

}
