package com.github.binarywang.demo.wechat.controller.view;

import com.github.binarywang.demo.wechat.controller.doctor.DoctorClient;
import com.github.binarywang.demo.wechat.controller.patient.PatientClient;
import com.github.binarywang.demo.wechat.entity.PatientEntity;
import com.github.binarywang.demo.wechat.service.MyWxMpService;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by jie on 2017/7/24.
 * 微信菜单页面跳转控制器
 */
@Controller
@RequestMapping(value = "/view")
public class MainViewController {
    /*
    * 基本URL
    * */
    private String baseUrl = "http://mrxiej.ngrok.wendal.cn/api-wechat";
    /*
    * 获取一些必要的微信参数类
    * */
    @Autowired
    private MyWxMpService myWxMpService;
    /*
    * 调用patient远程服务的接口类
    * */
    @Autowired
    PatientClient patientClient;

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    /*
    * 跳转到佳医比邻介绍页面
    * */
    @RequestMapping(value="/about")
    public String about(){
        return "responsemessage/test";
    }
    /*
    * 跳转到联系我们页面
    * */
    @RequestMapping(value="/contactus")
    public String contactus(HttpServletRequest request){
        String code = request.getParameter("code");
        this.logger.info("request的值：" + request.toString() + "\n");
        String state = request.getParameter("state");
        if (StringUtils.isAnyEmpty(code, state) || !"contactus".equals(state)) {
           String redirectUrl = myWxMpService.oauth2buildAuthorizationUrl(baseUrl + "/view/contactus", WxConsts.OAUTH2_SCOPE_USER_INFO, "contactus");
            this.logger.info("重定向微信认证页面获取code：" + redirectUrl + "\n");
            return "redirect:" + redirectUrl;
        }
        return "redirect:" + baseUrl+"/static/html/contactus.html?code="+code;
    }
    /*
    * 跳转到医生列表页面
    * */
    @RequestMapping(value="/doctorlist")
    public String doctorlist(HttpServletRequest request){
        String code = request.getParameter("code");
        this.logger.info("request的值：" + request.toString() + "\n");
        String state = request.getParameter("state");
        if (StringUtils.isAnyEmpty(code, state) || !"doctorlist".equals(state)) {
            String redirectUrl = myWxMpService.oauth2buildAuthorizationUrl(baseUrl + "/view/doctorlist", WxConsts.OAUTH2_SCOPE_USER_INFO, "doctorlist");
            this.logger.info("重定向微信认证页面获取code：" + redirectUrl + "\n");
            return "redirect:" + redirectUrl;
        }
            return "redirect:" + baseUrl+"/static/html/doctorlist.html?code="+code;
    }
    /*
    * 跳转到健康管理页面
    * */
    @RequestMapping(value="/healthmanage")
    public String healthservice(HttpServletRequest request){
        /* 首先构建微信认证url */
        String code = request.getParameter("code");
        this.logger.info("request的值：" + request.toString() + "\n");
        String state = request.getParameter("state");
        if (StringUtils.isAnyEmpty(code, state) || !"health".equals(state)) {
            String redirectUrl = myWxMpService.oauth2buildAuthorizationUrl(baseUrl + "/view/healthmanage", WxConsts.OAUTH2_SCOPE_USER_INFO, "health");
            this.logger.info("重定向微信认证页面获取code：" + redirectUrl + "\n");
            return "redirect:" + redirectUrl;
        }
        WxMpUser wxMpUser = null;
        try {
            wxMpUser = myWxMpService.getWxMpUser(request);
        } catch (WxErrorException e) {
            e.printStackTrace();
            String redirectUrl = myWxMpService.oauth2buildAuthorizationUrl(baseUrl + "/view/healthmanage", WxConsts.OAUTH2_SCOPE_USER_INFO, "health");
            this.logger.info("再次重定向页面获取微信用户信息：" + redirectUrl + "\n");
            return "redirect:" + redirectUrl;
        }
        String wechat_id = wxMpUser.getOpenId();
        return "redirect:" + baseUrl+"/html/healmanage.html?appid="+wechat_id;
//        return "redirect:" + "http://mrxiej.ngrok.wendal.cn/static/html/healmanage.html?code="+code;
    }
    /**
     * 跳转到患者相应的个人信息显示页面和个人信息保存页面
     */
    @RequestMapping(value = "/patient")
    public String patientinfo(HttpServletRequest request, Model model) {
        /* 首先构建微信认证url */
        String code = request.getParameter("code");
        this.logger.info("request的值：" + request.toString() + "\n");
        String state = request.getParameter("state");
        if (StringUtils.isAnyEmpty(code, state) || !"patient".equals(state)) {
            String redirectUrl = myWxMpService.oauth2buildAuthorizationUrl(baseUrl + "/view/patient", WxConsts.OAUTH2_SCOPE_USER_INFO, "patient");
            this.logger.info("重定向页面获取code：" + redirectUrl + "\n");
            return "redirect:" + redirectUrl;
        }
        WxMpUser wxMpUser = null;
        try {
            wxMpUser = myWxMpService.getWxMpUser(request);
        } catch (WxErrorException e) {
            e.printStackTrace();
            String redirectUrl = myWxMpService.oauth2buildAuthorizationUrl(baseUrl + "/view/patient", WxConsts.OAUTH2_SCOPE_USER_INFO, "patient");
            this.logger.info("再次重定向页面获取微信用户信息：" + redirectUrl + "\n");
            return "redirect:" + redirectUrl;
        }
        /*获取用户微信号判断页面跳转*/
        String wechat_id = wxMpUser.getOpenId();
        String headimg =wxMpUser.getHeadImgUrl();
        try {
            PatientEntity patientEntity = patientClient.findByWechatid(wechat_id);
            if (patientEntity == null) {
                /*
                * 跳转到用户保存页面
                * */
                return  "redirect:" + baseUrl+"/static/html/personSave.html?appid="+wechat_id+"&headimg="+headimg;
            } else {
                /*
                * 跳转到用户信息显示页面
                * */
                return  "redirect:" + baseUrl+"/static/html/personInfo.html?appid="+wechat_id+"&headimg="+headimg;
            }
        }
        /*
        * 处理异常并且打印日志
        * */
        catch(Exception e){
            this.logger.warn(e.getMessage());
            return  "redirect:" + baseUrl+"/static/html/error.html";
        }
    }






}
