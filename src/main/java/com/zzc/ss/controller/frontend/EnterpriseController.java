package com.zzc.ss.controller.frontend;

import com.zzc.ss.common.Const;
import com.zzc.ss.common.ServerResponse;
import com.zzc.ss.entity.EnterpriseInfo;
import com.zzc.ss.entity.JobInfo;
import com.zzc.ss.service.EnterpriseService;
import com.zzc.ss.service.JobService;
import com.zzc.ss.service.UserService;
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

    @Autowired
    private UserService userService;

    @GetMapping()
    @ApiOperation(value = "获取企业信息")
    public ServerResponse<EnterpriseInfo> getEnterpriseInfo(@RequestHeader(name = "Authorization") String token){
        Integer userId = TokenUtil.getUserIdFromToken(token);
        EnterpriseInfo enterpriseInfo = enterpriseService.getEnterpriseInfoByUserId(userId);
        return ServerResponse.createBySuccess(Const.ExecuteResultMessage.QUERY_SUCCESS, enterpriseInfo);
    }



    @PostMapping()
    @ApiOperation(value = "新增或者修改公司信息")
    public ServerResponse save(@RequestHeader(name = "Authorization") String token,
                               @RequestBody EnterpriseInfo enterpriseInfo) {
        Integer userId = TokenUtil.getUserIdFromToken(token);
        EnterpriseInfo save = enterpriseService.save(userId, enterpriseInfo);
        return ServerResponse.createBySuccess(Const.ExecuteResultMessage.APPLY_ENTERPRISE_SUBMIT_SUCCESS, save);
    }


    @PostMapping("save-job")
    @ApiOperation(value = "企业创建一个招聘信息，无论更新还是新增，状态都归为审核中")
    public ServerResponse saveJob(@RequestHeader(name = "Authorization") String token,
                                  @RequestBody JobInfo jobInfo) {
        Integer userId = TokenUtil.getUserIdFromToken(token);
        Integer enterpriseId = userService.getEnterpriseIdFromUserId(userId);
        JobInfo result = enterpriseService.enterpriseSaveJob(enterpriseId, jobInfo);
        return ServerResponse.createBySuccess(Const.ExecuteResultMessage.JOB_SAVE_SUCCESS, result);
    }


    @GetMapping("job-list")
    @ApiOperation(value = "企业查询自己创建的招聘信息")
    public ServerResponse getSelfJobList(@RequestHeader(name = "Authorization") String token) {
        // TODO: 2018/8/16 分页查询
        Integer userId = TokenUtil.getUserIdFromToken(token);
        Integer enterpriseId = userService.getEnterpriseIdFromUserId(userId);
        List<JobInfo> list = enterpriseService.getSelfJobList(enterpriseId);
        return ServerResponse.createBySuccess(Const.ExecuteResultMessage.QUERY_SUCCESS, list);
    }

}
