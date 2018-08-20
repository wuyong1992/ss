package com.zzc.ss.controller.backend;

import com.zzc.ss.common.Const;
import com.zzc.ss.common.ServerResponse;
import com.zzc.ss.service.UserJobService;
import com.zzc.ss.vo.UserJobVO;
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
 * on 2018/8/16
 * description:
 */
@RestController
@RequestMapping("backend/user-job")
@Validated
@Api(description = "用户求职申请后台管理接口")
public class UserJobBackendController {


    @Autowired
    private UserJobService userJobService;


    @GetMapping("list")
    @ApiOperation(value = "根据条件分页查询求职列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码，从0开始，默认为0", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "当前页容量，默认10条", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "realName", value = "求职用户真实姓名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "phone", value = "求职用户手机号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "enterpriseFullName", value = "发布招聘的公司名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "求职状态", dataType = "int", paramType = "query"),
    })
    public ServerResponse<Page<UserJobVO>> getUserList(@PageableDefault(sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                                                       @RequestParam(value = "realName", required = false, defaultValue = "") String realName,
                                                       @RequestParam(value = "phone", required = false, defaultValue = "") String phone,
                                                       @RequestParam(value = "enterpriseFullName", required = false, defaultValue = "") String enterpriseFullName,
                                                       @RequestParam(value = "status", required = false, defaultValue = "") String status) {
        Page<UserJobVO> page = userJobService.getUserJobVOList(pageable, realName, phone, enterpriseFullName, status);
        return ServerResponse.createBySuccess(Const.ExecuteResultMessage.QUERY_SUCCESS, page);
    }


    @PutMapping("apply-success/{id}")
    @ApiOperation(value = "求职申请审核通过")
    @ApiImplicitParam(name = "id", value = "user-job 的主键ID", dataType = "int", paramType = "path")
    public ServerResponse applySuccess(@PathVariable Integer id) {
        userJobService.applySuccess(id);
        return ServerResponse.createBySuccessMsg(Const.ExecuteResultMessage.OPERATE_SUCCESS);
    }

    @PutMapping("apply-fail/{id}")
    @ApiOperation(value = "拒绝求职申请")
    @ApiImplicitParam(name = "id", value = "user-job 的主键ID", dataType = "int", paramType = "path")
    public ServerResponse applyFail(@PathVariable Integer id) {
        userJobService.applyFail(id);
        return ServerResponse.createBySuccessMsg(Const.ExecuteResultMessage.OPERATE_SUCCESS);
    }

}
