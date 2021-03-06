package com.zzc.ss.wechat.controller;

import com.zzc.ss.service.UserService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * created by JianGuo
 * on 2018/3/28
 * description:
 */
@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatWebAuthController {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private UserService userService;

    @Value("${authUrl}")
    private String authUrl;

    @Value("${authRedirectUrl}")
    private String authRedirectUrl;

    /**
     * 构造用户授权url
     *
     * @return 重定向到授权页面
     */
    @RequestMapping("web-auth")
    public ModelAndView authorize() {
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(authUrl, WxConsts.OAuth2Scope.SNSAPI_USERINFO, "");
        log.info("【微信网页授权 -- 获取code，redirectUrl={}】", redirectUrl);
        return new ModelAndView("redirect:" + redirectUrl);
    }

    /**
     * 用户授权
     * 重定向回学生中心
     * @param code 点击同意授权微信端发回来的code
     * @return
     */
    @GetMapping("auth-user-info")
    @ResponseBody
    public ModelAndView userInfo(@RequestParam("code") String code) {

        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = null;
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("【微信网页授权异常 e:{}】", e.getMessage());
        }
        if (wxMpOAuth2AccessToken == null) {
            // TODO: 2018/4/20 重定向会授权错误页面
            return null;
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();
        String unionId = wxMpOAuth2AccessToken.getUnionId();
        log.info("openId：{}", openId);
        log.info("unionId：{}", unionId);
        log.info("token：{}", wxMpOAuth2AccessToken);
        WxMpUser wxMpUser = null;
        try {
            wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
        } catch (WxErrorException e) {
            log.error("【微信网页授权异常 获取用户信息异常 e:{}】", e.getMessage());
        }
        if (wxMpUser == null) {
            log.error("【获取用户权限异常】");
            // TODO: 2018/4/20 重定向会授权错误页面
            return null;
        }
        log.debug("wxMpUser:{}", wxMpUser.toString());

        String jwt = userService.wechatWebAuthAndReturnToken(wxMpUser);
        if (jwt == null) {
            log.error("【授权失败 ---  token创建失败】");
            // TODO: 2018/4/12 重定向到错误页
        }

        String redirectUrl = authRedirectUrl + "?token=" + jwt;
        log.debug("redirectUrl:{}", redirectUrl);
        return new ModelAndView("redirect:" + redirectUrl);
    }


   /* @RequestMapping("auth")
    public ServerResponse wechatAuth(HttpServletRequest request) {
        log.info("前端跳转过来的url:{}", request.getRequestURL());
        return ServerResponse.createBySuccess("abc");
    }

    @RequestMapping("auth2")
    public ModelAndView wechatAuth2(HttpServletRequest request) {
        log.info("前端跳转过来的url:{}", request.getRequestURL());
        return new ModelAndView("redirect:/wechat/auth");
    }*/

}
