package com.zzc.ss.controller.backend;

import com.zzc.ss.common.Const;
import com.zzc.ss.common.ServerResponse;
import com.zzc.ss.entity.Manage;
import com.zzc.ss.service.ManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JianGuo
 * on 2018/8/10
 * description:
 * todo 暂时不动
 */
@RestController
@RequestMapping("backend/manage")
@Validated
@Api(description = "管理员后端接口")
public class ManageBackendController {

    @Autowired
    private ManageService manageService;


    @PostMapping("login")
    @ApiOperation(value = "登录")
    public ServerResponse login(@RequestBody Manage manage) {
        String token = manageService.login(manage.getAccount(), manage.getPassword());
        if (token == null) {
            return ServerResponse.createByErrorMsg(Const.ExecuteResultMessage.TOKEN_CREATE_ERROR);
        }
        return ServerResponse.createBySuccess(Const.ExecuteResultMessage.LOGIN_SUCCESS, token);
    }



}
