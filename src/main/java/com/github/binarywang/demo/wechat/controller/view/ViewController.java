package com.github.binarywang.demo.wechat.controller.view;

import com.github.binarywang.demo.wechat.controller.doctor.DoctorClient;
import com.github.binarywang.demo.wechat.controller.patient.PatientClient;
import com.github.binarywang.demo.wechat.entity.DoctorEntity;
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
import java.util.List;

/**
 * Created by jie on 2017/7/24.
 * 微信菜单页面跳转控制器
 */
@Controller
@RequestMapping(value = "/view")
public class ViewController {
    /*
    * 基本URL
    * */
    private String baseUrl = "http://mrxiej.ngrok.wendal.cn";
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
    /*
    * 调用doctor远程服务的接口类
    * */
    @Autowired
    DoctorClient doctorClient;
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    /*
    * 跳转到医生列表页面
    * */
    @RequestMapping(value="/doctorlist")
    public String doctorlist(HttpServletRequest request,Model model){
        String code = request.getParameter("code");
        this.logger.info("request的值：" + request.toString() + "\n");
        String state = request.getParameter("state");
        if (StringUtils.isAnyEmpty(code, state) || !"doctorlist".equals(state)) {
            String redirectUrl = myWxMpService.oauth2buildAuthorizationUrl(baseUrl + "/view/doctorlist", WxConsts.OAUTH2_SCOPE_USER_INFO, "doctorlist");
            this.logger.info("重定向页面：" + redirectUrl + "\n");
            return "redirect:" + redirectUrl;
        }
        WxMpUser wxMpUser = null;
        try {
            wxMpUser = myWxMpService.getWxMpUser(request);
        } catch (WxErrorException e) {
            e.printStackTrace();
            String redirectUrl = myWxMpService.oauth2buildAuthorizationUrl(baseUrl + "/view/doctorlist", WxConsts.OAUTH2_SCOPE_USER_INFO, "doctorlist");
            return "redirect:" + redirectUrl;
        }
        try{
            List<DoctorEntity> doctor = doctorClient.findalldoctor();
            String wechat_id = wxMpUser.getOpenId();
            model.addAttribute("doctorlist",doctor);
            model.addAttribute("openid", wechat_id);
            return "doctorlist";
        }
        catch(Exception e){
            this.logger.warn(e.getMessage());
            return "responsemessage/error";
        }
    }
    /*
    * 跳转到健康管理页面
    * */
    @RequestMapping(value="/health")
    public String healthservice(HttpServletRequest request,Model model){
        /* 首先构建微信认证url */
        String code = request.getParameter("code");
        this.logger.info("request的值：" + request.toString() + "\n");
        String state = request.getParameter("state");
        if (StringUtils.isAnyEmpty(code, state) || !"health".equals(state)) {
            String redirectUrl = myWxMpService.oauth2buildAuthorizationUrl(baseUrl + "/view/health", WxConsts.OAUTH2_SCOPE_USER_INFO, "health");
            this.logger.info("重定向页面：" + redirectUrl + "\n");
            return "redirect:" + redirectUrl;
        }
        WxMpUser wxMpUser = null;
        try {
            wxMpUser = myWxMpService.getWxMpUser(request);
        } catch (WxErrorException e) {
            e.printStackTrace();
            String redirectUrl = myWxMpService.oauth2buildAuthorizationUrl(baseUrl + "/view/health", WxConsts.OAUTH2_SCOPE_USER_INFO, "health");
            return "redirect:" + redirectUrl;
        }
        /*开始业务逻辑代码处理*/
        try{
            return "healthservice";
        }
        catch(Exception e){
            this.logger.warn(e.getMessage());
            return "responsemessage/error";
        }
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
            this.logger.info("重定向页面：" + redirectUrl + "\n");
            return "redirect:" + redirectUrl;
        }
        WxMpUser wxMpUser = null;
        try {
            wxMpUser = myWxMpService.getWxMpUser(request);
        } catch (WxErrorException e) {
            e.printStackTrace();
            String redirectUrl = myWxMpService.oauth2buildAuthorizationUrl(baseUrl + "/view/patient", WxConsts.OAUTH2_SCOPE_USER_INFO, "patient");
            return "redirect:" + redirectUrl;
        }
        /*获取用户微信号开始业务逻辑*/
        String wechat_id = wxMpUser.getOpenId();
        try {
            PatientEntity patientEntity = patientClient.findByWechatid(wechat_id);
            String headImgUrl = wxMpUser.getHeadImgUrl();
            model.addAttribute("headImgUrl", headImgUrl);
            model.addAttribute("openid", wechat_id);
            if (patientEntity == null) {
                return "patientsave";
            } else {
                model.addAttribute("name", patientEntity.getName());
                model.addAttribute("id_card", patientEntity.getId_card());
                model.addAttribute("sex", patientEntity.getSex());
                model.addAttribute("age", patientEntity.getAge());
                model.addAttribute("phone", patientEntity.getPhone());
                model.addAttribute("address", patientEntity.getAddress());
                model.addAttribute("detailed_address", patientEntity.getDetailed_address());
                model.addAttribute("head_pic", patientEntity.getHead_pic());

                return "patientinfo";
            }
        }
        /*
        * 处理异常并且打印日志
        * */
        catch(Exception e){
            this.logger.warn(e.getMessage());
            return "responsemessage/error";
        }
    }
    /*
    * 从患者个人信息页转入保存页
    * */
    @RequestMapping(value = "infotosave")
    public String infotosave(HttpServletRequest request, Model model) {
        /*
        *通过页面传过来的wechat_id进行业务逻辑处理
        */
        try {
            String wechat_id = request.getParameter("openid");
            PatientEntity patientEntity = patientClient.findByWechatid(wechat_id);
            model.addAttribute("headImgUrl", patientEntity.getHead_pic());
            model.addAttribute("openid", wechat_id);
            model.addAttribute("name", patientEntity.getName());
            model.addAttribute("id_card", patientEntity.getId_card());
            model.addAttribute("sex", patientEntity.getSex());
            model.addAttribute("age", patientEntity.getAge());
            model.addAttribute("phone", patientEntity.getPhone());
            model.addAttribute("address", patientEntity.getAddress());
            model.addAttribute("detailed_address", patientEntity.getDetailed_address());
            model.addAttribute("head_pic", patientEntity.getHead_pic());
            return "patientsave";
        }
        /*
        * 处理异常并且打印日志
        * */
        catch(Exception e){
            this.logger.warn(e.getMessage());
            return "responsemessage/error";
        }

    }

    /*
    * 跳转到我的医生页面
    * */
    @RequestMapping(value = "/mydoctor")
    public String mydoctor(HttpServletRequest request, Model model) {
        /*首先构建微信权限认证url*/
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        if (StringUtils.isAnyEmpty(code, state) || !"mydoctor".equals(state)) {
            String redirectUrl = myWxMpService.oauth2buildAuthorizationUrl(baseUrl + "/view/mydoctor", WxConsts.OAUTH2_SCOPE_USER_INFO, "mydoctor");
            this.logger.info("重定向页面：" + redirectUrl + "\n");
            return "redirect:" + redirectUrl;
        }
        WxMpUser wxMpUser = null;
        try {
            wxMpUser = myWxMpService.getWxMpUser(request);
        } catch (WxErrorException e) {
            e.printStackTrace();
            String redirectUrl = myWxMpService.oauth2buildAuthorizationUrl(baseUrl + "/view/mydoctor", WxConsts.OAUTH2_SCOPE_USER_INFO, "mydoctor");
            return "redirect:" + redirectUrl;
        }
        /*开始业务逻辑方面的代码*/
        String wechat_id = wxMpUser.getOpenId();
        String doctor_id = patientClient.findmydoctor(wechat_id);
        return "index";

    }

}
