package com.zzc.ss.controller.frontend;

import com.zzc.ss.common.Const;
import com.zzc.ss.common.ServerResponse;
import com.zzc.ss.entity.UserInfo;
import com.zzc.ss.service.UserService;
import com.zzc.ss.utils.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author JianGuo
 * on 2018/8/10
 * description:
 */
@RestController
@RequestMapping("frontend/user")
@Validated
@Api(description = "用户前端接口")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("save")
    @ApiOperation(value = "更新用户信息")
    public ServerResponse save(@RequestHeader(name = "Authorization") String token,
                               @RequestBody UserInfo userInfo) {
        Integer userId = TokenUtil.getUserIdFromToken(token);
        if (!userId.equals(userInfo.getUserId())) {
            return ServerResponse.createByErrorMsg("当前用户与微信账号不匹配");
        }
        UserInfo save = userService.save(userInfo);
        return ServerResponse.createBySuccess(Const.ExecuteResultMessage.SAVE_SUCCESS, save);
    }


    @GetMapping()
    @ApiOperation(value = "获取用户的信息")
    public ServerResponse<UserInfo> getUserInfo(@RequestHeader(name = "Authorization") String token) {
        Integer userId = TokenUtil.getUserIdFromToken(token);
        UserInfo userInfo = userService.getUserInfoByUserId(userId);
        return ServerResponse.createBySuccess(Const.ExecuteResultMessage.QUERY_SUCCESS, userInfo);
    }

    @PostMapping("apply-job/{jobId}")
    @ApiOperation(value = "申请工作")
    @ApiImplicitParam(name = "jobId", value = "工作ID", required = true, dataTypeClass = Integer.class, paramType = "path")
    public ServerResponse applyJob(@RequestHeader(name = "Authorization") String token,
                                   @PathVariable String jobId) {
        Integer userId = TokenUtil.getUserIdFromToken(token);
        Integer intJobId = Integer.parseInt(jobId);
        userService.applyJob(userId, intJobId);
        return ServerResponse.createBySuccessMsg(Const.ExecuteResultMessage.APPLY_JOB_SUBMIT_SUCCESS);
    }

    @PutMapping("check-token")
    @ApiOperation(value = "判断token的有效性")
    public ServerResponse checkToken(@RequestHeader(name = "Authorization") String token){
        Boolean result = userService.checkToken(token);
        if (!result) {
            return ServerResponse.createByErrorMsg(Const.ExecuteResultMessage.TOKEN_EXPIRE);
        }
        return ServerResponse.createBySuccessMsg(Const.ExecuteResultMessage.TOKEN_VALID);
    }


}
