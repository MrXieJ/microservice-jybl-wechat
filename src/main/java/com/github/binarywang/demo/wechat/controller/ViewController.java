package com.github.binarywang.demo.wechat.controller;

import com.github.binarywang.demo.wechat.service.MyWxMpService;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;


/**
 * Created by jie on 2017/7/24.
 */
@Controller
@RequestMapping(value="/view")
public class ViewController {
    @Autowired
    private MyWxMpService myWxMpService;

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     *个人信息页跳转
     */

    @RequestMapping(value="/person")
    public String test(HttpServletRequest request, Model model) {

        String code = request.getParameter("code");
        this.logger.info("request的值：" + request.toString() + "\n");
        String state = request.getParameter("state");
        String errcode = request.getParameter("errcode");
        if (StringUtils.isAnyEmpty(code, state) || !"person".equals(state)) {
            String redirectUrl = myWxMpService.oauth2buildAuthorizationUrl("http://mrxiej.ngrok.wendal.cn/Xinyijia/view/person", WxConsts.OAUTH2_SCOPE_USER_INFO, "person");
            this.logger.info("重定向页面：" + redirectUrl + "\n");
            return "redirect:" + redirectUrl;
        }
        WxMpUser wxMpUser = null;
        try {
            wxMpUser = myWxMpService.getWxMpUser(request);
        } catch (WxErrorException e) {
            e.printStackTrace();
            String redirectUrl = myWxMpService.oauth2buildAuthorizationUrl("http://mrxiej.ngrok.wendal.cn/Xinyijia/view/person", WxConsts.OAUTH2_SCOPE_USER_INFO, "person");
            this.logger.info("再次重定向页面：" + redirectUrl + "\n");
            return "redirect:" + redirectUrl;
        }
        String openId = wxMpUser.getOpenId();
        String headImgUrl=wxMpUser.getHeadImgUrl();
        model.addAttribute("headImgUrl", headImgUrl);

        model.addAttribute("openid", openId);
        return "person1";
    }

    /*
    * 二维码测试页面
    * */
    @RequestMapping(value="/qrcode/{doctorid}")
    public String qrcode(@PathVariable String doctorid,Model model){
        model.addAttribute("doctorid",doctorid);
        return"testdoctor";
    }
}
