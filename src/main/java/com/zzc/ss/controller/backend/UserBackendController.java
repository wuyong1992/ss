package com.zzc.ss.controller.backend;

import com.zzc.ss.common.Const;
import com.zzc.ss.common.ServerResponse;
import com.zzc.ss.entity.UserInfo;
import com.zzc.ss.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JianGuo
 * on 2018/8/10
 * description:
 */
@RestController
@RequestMapping("backend/user")
@Validated
@Api(description = "用户后端接口")
public class UserBackendController {


    @Autowired
    private UserService userService;


    @GetMapping("list")
    @ApiOperation(value = "根据条件分页查询用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码，从0开始，默认为0", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "当前页容量，默认10条", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "nickname", value = "用户昵称", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "realName", value = "用户真实姓名", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "phone", value = "用户手机号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "subscribeStatus", value = "关注状态", dataType = "int", paramType = "query"),
    })
    public ServerResponse<Page<UserInfo>> getUserList(@PageableDefault(page = 0, size = 10, sort = "sort,desc") Pageable pageable,
                                      @RequestParam(value = "nickname",required = false,defaultValue = "")String nickname,
                                      @RequestParam(value = "realName",required = false,defaultValue = "")String realName,
                                      @RequestParam(value = "phone",required = false,defaultValue = "")String phone,
                                      @RequestParam(value = "subscribeStatus",required = false,defaultValue = "")String subscribeStatus){
        Page<UserInfo> page = userService.getUserList(pageable, nickname, realName, phone, subscribeStatus);
        return ServerResponse.createBySuccess(Const.ExecuteResultMessage.QUERY_SUCCESS, page);
    }


}
