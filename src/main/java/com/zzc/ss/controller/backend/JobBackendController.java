package com.zzc.ss.controller.backend;

import com.zzc.ss.common.Const;
import com.zzc.ss.common.ServerResponse;
import com.zzc.ss.entity.JobInfo;
import com.zzc.ss.service.JobService;
import com.zzc.ss.vo.JobVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author JianGuo
 * on 2018/8/10
 * description:
 */
@RestController
@RequestMapping("backend/job")
@Validated
@Api(description = "招聘工作后端接口")
public class JobBackendController {

    @Autowired
    private JobService jobService;


    @PostMapping("")
    @ApiOperation(value = "新增或者更新工作")
    public ServerResponse save(@RequestBody JobInfo jobInfo) {
        JobInfo result = jobService.save(jobInfo);
        return ServerResponse.createBySuccess(Const.ExecuteResultMessage.OPERATE_SUCCESS, result);
    }

    @DeleteMapping("{jobId}")
    @ApiOperation(value = "根据jobId删除某个工作")
    @ApiImplicitParam(name = "jobId", value = "工作分类ID", dataType = "int", paramType = "path")
    public ServerResponse deleteByJobId(@PathVariable Integer jobId) {
        jobService.deleteByJobId(jobId);
        return ServerResponse.createBySuccessMsg(Const.ExecuteResultMessage.OPERATE_SUCCESS);
    }


    @GetMapping("list")
    @ApiOperation(value = "根据条件分页查询招聘工作列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码，从0开始，默认为0", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "当前页容量，默认10条", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "当前页容量，默认10条", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "jobCategoryId", value = "工作分类ID", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "enterpriseId", value = "发布该招聘的企业ID", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "招聘信息状态", dataType = "int", paramType = "query")
    })
    public ServerResponse<Page<JobVO>> getJonInfoList(@PageableDefault(sort = {"sort"},direction = Sort.Direction.DESC) Pageable pageable,
                                         @RequestParam(value = "jobCategoryId", required = false, defaultValue = "") String jobCategoryId,
                                         @RequestParam(value = "enterpriseId", required = false, defaultValue = "") String enterpriseId,
                                         @RequestParam(value = "status", required = false, defaultValue = "") String status) {
        Page<JobVO> page = jobService.getJonInfoList(pageable, jobCategoryId, enterpriseId, status);
        return ServerResponse.createBySuccess(Const.ExecuteResultMessage.OPERATE_SUCCESS, page);
    }

    @PutMapping("apply-success/{jobId}")
    @ApiOperation(value = "审核通过某个招聘信息")
    @ApiImplicitParam(name = "jobId", value = "招聘信息ID", dataType = "int", paramType = "path")
    public ServerResponse applySuccess(@PathVariable Integer jobId){
        jobService.applySuccessByJobId(jobId);
        return ServerResponse.createBySuccessMsg(Const.ExecuteResultMessage.OPERATE_SUCCESS);
    }

    @PutMapping("apply-fail/{jobId}")
    @ApiOperation(value = "驳回某个招聘信息")
    @ApiImplicitParam(name = "jobId", value = "招聘信息ID", dataType = "int", paramType = "path")
    public ServerResponse applyFail(@PathVariable Integer jobId){
        jobService.applyFailByJobId(jobId);
        return ServerResponse.createBySuccessMsg(Const.ExecuteResultMessage.OPERATE_SUCCESS);
    }

    @PutMapping("hide/{jobId}")
    @ApiOperation(value = "驳回某个招聘信息")
    @ApiImplicitParam(name = "jobId", value = "招聘信息ID", dataType = "int", paramType = "path")
    public ServerResponse hide(@PathVariable Integer jobId){
        jobService.hideByJobId(jobId);
        return ServerResponse.createBySuccessMsg(Const.ExecuteResultMessage.OPERATE_SUCCESS);
    }



}
