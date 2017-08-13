package com.github.binarywang.demo.wechat.controller.wechat;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.impl.AbstractWxMpServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jie on 2017/7/27.
 */
@RestController
@RequestMapping(value="/wechat/auth")
public class WxOAuth2Controller {
    @Autowired
    AbstractWxMpServiceImpl abstractWxMpService;
    /**
     * <pre>
     * 构造第三方使用网站应用授权登录的url
     * 详情请见: <a href="https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419316505&token=&lang=zh_CN">网站应用微信登录开发指南</a>
     * URL格式为：https://open.weixin.qq.com/connect/qrconnect?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect
     * </pre>
     *
     * @param redirectURI 用户授权完成后的重定向链接，无需urlencode, 方法内会进行encode
     * @param scope       应用授权作用域，拥有多个作用域用逗号（,）分隔，网页应用目前仅填写snsapi_login即可
     * @param state       非必填，用于保持请求和回调的状态，授权请求后原样带回给第三方。该参数可用于防止csrf攻击（跨站请求伪造攻击），建议第三方带上该参数，可设置为简单的随机数加session进行校验
     * @return url
     */
    @GetMapping (value="/buildurl")
    String buildQrConnectUrl(String redirectURI, String scope, String state)
    {
        return abstractWxMpService.buildQrConnectUrl(redirectURI,scope,state);
    }

    /**
     * <pre>
     * 构造oauth2授权的url连接
     * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=网页授权获取用户基本信息
     * </pre>
     *
     * @param redirectURI 用户授权完成后的重定向链接，无需urlencode, 方法内会进行encode
     * @return url
     */
    @GetMapping(value="/buildo2url")
    String oauth2buildAuthorizationUrl(String redirectURI, String scope, String state){
        return abstractWxMpService.oauth2buildAuthorizationUrl(redirectURI, scope, state);
    }

    /**
     * <pre>
     * 用code换取oauth2的access token
     * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=网页授权获取用户基本信息
     * </pre>
     */
    @GetMapping(value="/gettoken")
    WxMpOAuth2AccessToken oauth2getAccessToken(String code) throws WxErrorException{
        return abstractWxMpService.oauth2getAccessToken(code);
    }

    /**
     * <pre>
     * 刷新oauth2的access token
     * </pre>
     */
    @PostMapping(value="/refreshtoken")
    WxMpOAuth2AccessToken oauth2refreshAccessToken(String refreshToken) throws WxErrorException{
        return abstractWxMpService.oauth2refreshAccessToken(refreshToken);
    }

    /**
     * <pre>
     * 用oauth2获取用户信息, 当前面引导授权时的scope是snsapi_userinfo的时候才可以
     * </pre>
     *
     * @param lang zh_CN, zh_TW, en
     */
    @GetMapping(value="/getinfo")
    WxMpUser oauth2getUserInfo(WxMpOAuth2AccessToken oAuth2AccessToken, String lang) throws WxErrorException{
        return abstractWxMpService.oauth2getUserInfo(oAuth2AccessToken, lang);
    }

    /**
     * <pre>
     * 验证oauth2的access token是否有效
     * </pre>
     */
    @GetMapping(value="/validatetoken")
    boolean oauth2validateAccessToken(WxMpOAuth2AccessToken oAuth2AccessToken){
        return abstractWxMpService.oauth2validateAccessToken(oAuth2AccessToken);
    }
}
