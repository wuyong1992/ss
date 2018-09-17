package com.zzc.ss.controller.wechat;

import com.zzc.ss.common.ServerResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuyong
 * @since 下午3:14 2018/9/17
 */
@RestController
@RequestMapping("wechat")
@Api(description = "对接微信接口")
public class WeChatController {

    @Autowired
    private WxMpService wxMpService;


    @GetMapping("jssdk/signature")
    @ApiOperation(value = "获取微信网页授权签名")
    @ApiImplicitParam(value = "请求获取签名的链接地址url")
    public ServerResponse getJsSDKSignature(@RequestParam("url") String url){
        WxJsapiSignature jsapiSignature = null;
        try {
            jsapiSignature = wxMpService.createJsapiSignature(url);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return ServerResponse.createBySuccess(jsapiSignature);
    }




}
