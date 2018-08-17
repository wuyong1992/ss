package com.zzc.ss.controller.frontend;

import com.google.common.base.Strings;
import com.zzc.ss.common.Const;
import com.zzc.ss.common.ServerResponse;
import com.zzc.ss.entity.JobInfo;
import com.zzc.ss.service.JobService;
import com.zzc.ss.utils.TokenUtil;
import com.zzc.ss.vo.JobVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author JianGuo
 * on 2018/8/10
 * description:
 */
@RestController
@RequestMapping("frontend/job")
@Validated
@Api(description = "招聘工作前端接口")
public class JobController {

    @Autowired
    private JobService jobService;


    @GetMapping("list")
    @ApiOperation(value = "获取招聘信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "auth token", required = true, dataTypeClass = String.class, paramType = "header"),
            @ApiImplicitParam(name = "page", value = "当前页码，从0开始，默认为0", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "当前页容量，默认10条", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "categoryId", value = "工作分类ID", dataType = "int", paramType = "query")
    })
    public ServerResponse<Page<JobVO>> jobVOList(@RequestHeader(name = "Authorization") String token,
                                                 @PageableDefault(page = 0, size = 10, sort = "sort,desc") Pageable pageable,
                                                 @RequestParam(value = "categoryId", required = false, defaultValue = "") String categoryId) {
        Integer userId = TokenUtil.getUserIdFromToken(token);
        Integer jobCategoryId = null;
        if (Strings.isNullOrEmpty(categoryId)) {
            jobCategoryId = Integer.valueOf(categoryId);
        }
        Page<JobVO> page = jobService.getJobVOListWithUserId(userId, pageable,jobCategoryId);
        return ServerResponse.createBySuccess(Const.ExecuteResultMessage.QUERY_SUCCESS, page);
    }

    @GetMapping("{jobId}")
    @ApiOperation(value = "获取单个job信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "auth token", required = true, dataTypeClass = String.class, paramType = "header"),
            @ApiImplicitParam(name = "jobId", value = "job ID", required = true, dataTypeClass = Integer.class, paramType = "path")
    })
    public ServerResponse<JobVO> getJobVO(@RequestHeader(name = "Authorization") String token,
                                          @PathVariable String jobId) {
        Integer userId = TokenUtil.getUserIdFromToken(token);
        Integer intJobId = Integer.parseInt(jobId);
        JobVO vo = jobService.getJobVOWithUserId(userId, intJobId);
        return ServerResponse.createBySuccess(Const.ExecuteResultMessage.QUERY_SUCCESS, vo);
    }




}
