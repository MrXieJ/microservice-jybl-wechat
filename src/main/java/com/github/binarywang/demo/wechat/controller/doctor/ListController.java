package com.github.binarywang.demo.wechat.controller.doctor;

import com.github.binarywang.demo.wechat.entity.DoctorEntity;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 由公众号中的医生列表跳转到医生的个人信息页
 * Created by jie on 2017/8/8.
 */
@Controller
@RequestMapping(value="/doctor")
public class ListController {
    /*
    * 调用远程服务中的医生接口
    * */
    @Autowired
    DoctorClient doctorClient;
    /*
    * 记录日志
    * */
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    @RequestMapping(value="listtoinfo")
    public String listtoinfo(HttpServletRequest request,Model model){
        try {
            String doctor_phone= request.getParameter("openid");
            DoctorEntity doctorEntity=doctorClient.findbydoctorphone(doctor_phone);
            model.addAttribute("doctorinfo",doctorEntity);
            return "doctorview";
        }
        catch(Exception e){
            this.logger.warn(e.getMessage());
            return "responsemessage/error";
        }
    }
}
