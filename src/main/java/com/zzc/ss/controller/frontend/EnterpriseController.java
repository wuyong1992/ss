package com.zzc.ss.controller.frontend;

import com.zzc.ss.common.Const;
import com.zzc.ss.common.ServerResponse;
import com.zzc.ss.entity.EnterpriseInfo;
import com.zzc.ss.entity.JobInfo;
import com.zzc.ss.service.EnterpriseService;
import com.zzc.ss.service.JobService;
import com.zzc.ss.utils.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author JianGuo
 * on 2018/8/10
 * description:
 */
@RestController
@RequestMapping("frontend/enterprise")
@Validated
@Api(description = "企业前端接口")
public class EnterpriseController {

    @Autowired
    private EnterpriseService enterpriseService;


    @PostMapping()
    @ApiOperation(value = "新增或者修改公司信息")
    @ApiImplicitParam(name = "token", value = "auth token", required = true, dataTypeClass = String.class, paramType = "header")
    public ServerResponse save(@RequestHeader(name = "Authorization") String token,
                               @RequestBody EnterpriseInfo enterpriseInfo) {
        Integer userId = TokenUtil.getUserIdFromToken(token);
        EnterpriseInfo save = enterpriseService.save(userId, enterpriseInfo);
        return ServerResponse.createBySuccess(Const.ExecuteResultMessage.APPLY_ENTERPRISE_SUBMIT_SUCCESS, save);
    }


    @PostMapping("save-job")
    @ApiOperation(value = "企业创建一个招聘信息，无论更新还是新增，状态都归为审核中")
    @ApiImplicitParam(name = "token", value = "auth token", required = true, dataTypeClass = String.class, paramType = "header")
    public ServerResponse saveJob(@RequestHeader(name = "Authorization") String token,
                                  @RequestBody JobInfo jobInfo) {
        Integer enterpriseId = TokenUtil.getEnterpriseIdFromToken(token);
        JobInfo result = enterpriseService.enterpriseSaveJob(enterpriseId, jobInfo);
        return ServerResponse.createBySuccess(Const.ExecuteResultMessage.SAVE_SUCCESS, result);
    }


    @GetMapping("job-list")
    @ApiOperation(value = "企业查询自己创建的招聘信息")
    @ApiImplicitParam(name = "token", value = "auth token", required = true, dataTypeClass = String.class, paramType = "header")
    public ServerResponse getSelfJobList(@RequestHeader(name = "Authorization") String token) {
        // TODO: 2018/8/16 分页查询
        Integer enterpriseId = TokenUtil.getEnterpriseIdFromToken(token);
        List<JobInfo> list = enterpriseService.getSelfJobList(enterpriseId);
        return ServerResponse.createBySuccess(Const.ExecuteResultMessage.QUERY_SUCCESS, list);
    }

}
