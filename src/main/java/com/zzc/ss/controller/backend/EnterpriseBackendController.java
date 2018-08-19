package com.zzc.ss.controller.backend;

import com.zzc.ss.common.Const;
import com.zzc.ss.common.ServerResponse;
import com.zzc.ss.entity.EnterpriseInfo;
import com.zzc.ss.service.EnterpriseService;
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

import java.util.List;

/**
 * @author JianGuo
 * on 2018/8/10
 * description:
 */
@RestController
@RequestMapping("backend/enterprise")
@Validated
@Api(description = "企业前端接口")
public class EnterpriseBackendController {


    @Autowired
    private EnterpriseService enterpriseService;


    @PutMapping("apply-success/{enterpriseId}")
    @ApiOperation(value = "审核通过")
    @ApiImplicitParam(name = "enterpriseId", value = "企业ID", dataType = "int", paramType = "path")
    public ServerResponse applySuccess(@PathVariable Integer enterpriseId) {
        enterpriseService.applySuccess(enterpriseId);
        return ServerResponse.createBySuccessMsg(Const.ExecuteResultMessage.OPERATE_SUCCESS);
    }

    @PutMapping("apply-fail/{enterpriseId}")
    @ApiOperation(value = "审核通过")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "enterpriseId", value = "企业ID", dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "reason", value = "未通过的原因", dataType = "string", paramType = "query")
    })
    public ServerResponse applyFail(@PathVariable Integer enterpriseId,
                                    @RequestParam("reason") String reason) {
        enterpriseService.applyFail(enterpriseId, reason);
        return ServerResponse.createBySuccessMsg(Const.ExecuteResultMessage.OPERATE_SUCCESS);
    }

    @GetMapping("list")
    @ApiOperation(value = "根据条件分页查询企业列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码，从0开始，默认为0", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "当前页容量，默认10条", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "fullName", value = "企业名称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "招聘信息状态", dataType = "int", paramType = "query")
    })
    public ServerResponse getEnterpriseList(@PageableDefault Pageable pageable,
                                            @RequestParam(value = "fullName", required = false, defaultValue = "") String enterpriseFullName,
                                            @RequestParam(value = "status", required = false, defaultValue = "") String status) {
        Page<EnterpriseInfo> page = enterpriseService.getEnterpriseList(pageable, enterpriseFullName, status);
        return ServerResponse.createBySuccess(Const.ExecuteResultMessage.OPERATE_SUCCESS, page);
    }

    @GetMapping("simple-list")
    @ApiOperation(value = "获取简单的企业信息集合，包括企业全称，ID，状态")
    public ServerResponse getSimpleList(){
        List<EnterpriseInfo> list = enterpriseService.getAllList();
        return ServerResponse.createBySuccess(Const.ExecuteResultMessage.OPERATE_SUCCESS, list);
    }


}
