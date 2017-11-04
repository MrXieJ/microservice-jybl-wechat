package scut.jiayibilin.wechat.controller.view;

import scut.jiayibilin.wechat.controller.feign.mysql.patient.PatientClient;
import scut.jiayibilin.wechat.service.MyWxMpService;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jie on 2017/8/3.
 */
@Controller
public class DoctorViewController {
    /*
    * 基本URL
    * */
    private String baseUrl = "http://mrxiej.ngrok.wendal.cn/api-wechat";

    @Autowired
    private MyWxMpService myWxMpService;
    /*
    * 远程服务调用需要用到的工具类
    * */
    @Autowired
    PatientClient patientClient;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /*
    * 根据医生二维码跳转到医生主页
    * */
    @RequestMapping(value="/qrcode/doctor/{phone}")
    public String qrcode(HttpServletRequest request,@PathVariable String phone){
        try {
            /* 首先构建微信认证url */
            String code = request.getParameter("code");
            this.logger.info("request的值：" + request.toString() + "\n");
            WxMpUser wxMpUser = null;
            try {
                wxMpUser = myWxMpService.getWxMpUser(request);
            } catch (WxErrorException e) {
                e.printStackTrace();
                String redirectUrl = myWxMpService.oauth2buildAuthorizationUrl(baseUrl + "/qrcode/doctor/"+phone, WxConsts.OAUTH2_SCOPE_USER_INFO, "qrcode");
                this.logger.info("微信认证重定向页面获取用户信息：" + redirectUrl + "\n");
                return "redirect:" + redirectUrl;
            }
            String wechat_id = wxMpUser.getOpenId();

//            跳转到医生个人主页并且携带医生电话号码参数
            return "redirect:" + baseUrl+"/static/html/doctorinfo.html?phone="+phone+"&wechat_id="+wechat_id;
        }
        catch(Exception e){
            this.logger.warn(e.getMessage());
            return "redirect:" + baseUrl+"/static/html/error.html";
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
        return "redirect:" + baseUrl+"/static/html/mydoctor.html?code="+code;
    }

}
