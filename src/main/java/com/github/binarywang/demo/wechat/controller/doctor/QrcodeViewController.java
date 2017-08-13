package com.github.binarywang.demo.wechat.controller.doctor;

import com.github.binarywang.demo.wechat.controller.patient.PatientClient;
import com.github.binarywang.demo.wechat.entity.DoctorEntity;
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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * Created by jie on 2017/8/3.
 */
@Controller
@RequestMapping(value="/qrcode")
public class QrcodeViewController {
    /*
    * 基本URL
    * */
    private String baseUrl = "http://mrxiej.ngrok.wendal.cn";
    @Autowired
    private MyWxMpService myWxMpService;
    /*
    * 远程服务调用需要用到的工具类
    * */
    @Autowired
    PatientClient patientClient;
    @Autowired
    DoctorClient doctorClient;
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    /*
    * 根据医生二维码跳转到医生主页
    * */
    @RequestMapping(value="/{phone}")
    public String qrcode(HttpServletRequest request,@PathVariable String phone, Model model){
        try {
            /* 首先构建微信认证url */
            String code = request.getParameter("code");
            this.logger.info("request的值：" + request.toString() + "\n");
            String state = request.getParameter("state");
            if (StringUtils.isAnyEmpty(code, state) || !"qrcode".equals(state)) {
                String redirectUrl = myWxMpService.oauth2buildAuthorizationUrl(baseUrl + "/qrcode/"+phone, WxConsts.OAUTH2_SCOPE_USER_INFO, "qrcode");
                this.logger.info("重定向页面：" + redirectUrl + "\n");
                return "redirect:" + redirectUrl;
            }
            WxMpUser wxMpUser = null;
            try {
                wxMpUser = myWxMpService.getWxMpUser(request);
            } catch (WxErrorException e) {
                e.printStackTrace();
                String redirectUrl = myWxMpService.oauth2buildAuthorizationUrl(baseUrl + "/qrcode/"+phone, WxConsts.OAUTH2_SCOPE_USER_INFO, "qrcode");
                return "redirect:" + redirectUrl;
            }
                /*患者关注医生*/
                String wechat_id = wxMpUser.getOpenId();
                if(patientClient.findmydoctor(wechat_id)==null)
                {
                    patientClient.watchdoctor(wechat_id, phone);
                }
                DoctorEntity doctorEntity = doctorClient.findbydoctorphone(phone);
                model.addAttribute(doctorEntity);
                return "doctorinfo";
        }
        catch(Exception e){
            this.logger.warn(e.getMessage());
            return "responsemessage/error";
        }
    }
    /*
    *根据医生id返回二维码Url
    */
    @ResponseBody
    @RequestMapping(value="findbyid")
    public String findbydoctorid(String doctorid) {
        File temp=new File("G:/qrcode/doctor"+doctorid+".jpg");
        if(temp.exists()){
            return "125.216.243.165:1000/qrcode/doctor"+doctorid+".jpg";
        }
        else{
            return "该医生二维码还未生成，请前往后台生成二维码";
        }
    }
}
