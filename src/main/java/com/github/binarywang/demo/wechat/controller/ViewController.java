package com.github.binarywang.demo.wechat.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;


/**
 * Created by jie on 2017/7/24.
 * 微信菜单页面跳转控制器
 */
@Controller
@RequestMapping(value="/view")
public class ViewController {
    /*
    * 基本URL
    * */
    private String baseUrl="http://mrxiej.ngrok.wendal.cn";
    /*
    * 获取一些必要的微信参数类
    * */
    @Autowired
    private MyWxMpService myWxMpService;
    /*
    * 调用远程服务的接口类
    * */
    @Autowired
    PatientClient patientClient;
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    /*
    * 从个人信息页转入保存页
    * */
    @RequestMapping(value="infotosave")
    public String infotosave(HttpServletRequest request,Model model){
        String wechat_id=request.getParameter("openid");
        PatientEntity patientEntity=patientClient.findByWechatid(wechat_id);
        model.addAttribute("headImgUrl", patientEntity.getHead_pic());
        model.addAttribute("openid", wechat_id);
        model.addAttribute("name",patientEntity.getName());
        model.addAttribute("id_card",patientEntity.getId_card());
        model.addAttribute("sex",patientEntity.getSex());
        model.addAttribute("age",patientEntity.getAge());
        model.addAttribute("phone",patientEntity.getPhone());
        model.addAttribute("address",patientEntity.getAddress());
        model.addAttribute("detailed_address",patientEntity.getDetailed_address());
        model.addAttribute("head_pic",patientEntity.getHead_pic());
        return "patientsave";
    }
    /**
     *跳转到相应的个人信息显示页面和个人信息保存页面
     */

    @RequestMapping(value="/patient")
    public String test(HttpServletRequest request, Model model) {

        String code = request.getParameter("code");
        this.logger.info("request的值：" + request.toString() + "\n");
        String state = request.getParameter("state");
        if (StringUtils.isAnyEmpty(code, state) || !"patient".equals(state)) {
            String redirectUrl = myWxMpService.oauth2buildAuthorizationUrl(baseUrl+"/view/patient", WxConsts.OAUTH2_SCOPE_USER_INFO, "patient");
            this.logger.info("重定向页面：" + redirectUrl + "\n");
            return "redirect:" + redirectUrl;
        }
        WxMpUser wxMpUser = null;
        try {
            wxMpUser = myWxMpService.getWxMpUser(request);
        } catch (WxErrorException e) {
            e.printStackTrace();
            String redirectUrl = myWxMpService.oauth2buildAuthorizationUrl(baseUrl+"/view/patient", WxConsts.OAUTH2_SCOPE_USER_INFO, "patient");
            this.logger.info("再次重定向页面：" + redirectUrl + "\n");
            return "redirect:" + redirectUrl;
        }
        String wechat_id = wxMpUser.getOpenId();
        PatientEntity patientEntity=patientClient.findByWechatid(wechat_id);
        String headImgUrl=wxMpUser.getHeadImgUrl();
        model.addAttribute("headImgUrl", headImgUrl);
        model.addAttribute("openid", wechat_id);
        if(patientEntity==null)
        {
            return "patientsave";
        }
        else{
            model.addAttribute("name",patientEntity.getName());
            model.addAttribute("id_card",patientEntity.getId_card());
            model.addAttribute("sex",patientEntity.getSex());
            model.addAttribute("age",patientEntity.getAge());
            model.addAttribute("phone",patientEntity.getPhone());
            model.addAttribute("address",patientEntity.getAddress());
            model.addAttribute("detailed_address",patientEntity.getDetailed_address());
            model.addAttribute("head_pic",patientEntity.getHead_pic());
            return "patientinfo";
        }
    }

}
