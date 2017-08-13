package com.github.binarywang.demo.wechat.controller.patient;

import com.github.binarywang.demo.wechat.entity.PatientEntity;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;


/**
 * Created by jie on 2017/7/31.
 */
@Controller
@RequestMapping(value="/patient")
public class InfoController {
    @Autowired
    PatientClient patientClient;
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    /*
    * 保存或者修改患者的个人信息
    * */
    @RequestMapping (value="/save",method= RequestMethod.POST)
    public String SavaDetail(HttpServletRequest request){
        try {
            String wechat_id = request.getParameter("openid");
            PatientEntity patientEntity = patientClient.findByWechatid(wechat_id);
            String name = request.getParameter("name");
            String id_card = request.getParameter("id_card");
            String sex = request.getParameter("sex");
            String strage = request.getParameter("age");
            int age = Integer.valueOf(strage);
            String phone = request.getParameter("phone");
            String detailed_address = request.getParameter("detailed_address");
            String provSelect = request.getParameter("provSelect");
            String citySelect = request.getParameter("citySelect");
            String areaSelect = request.getParameter("areaSelect");
            String address = provSelect + citySelect + areaSelect;
            String headpic = request.getParameter("headImgUrl");

            if (patientEntity == null) {
                patientEntity = new PatientEntity(name, id_card, sex, age, phone, address, detailed_address, wechat_id, headpic);
                patientClient.SaveDetail(patientEntity);
                return "responsemessage/success";
            } else {
                patientEntity = new PatientEntity(name, id_card, sex, age, phone, address, detailed_address, wechat_id, headpic);
                patientClient.updatepatient(patientEntity);
                return "responsemessage/success";
            }
        }
        catch(Exception e){
            this.logger.warn(e.getMessage());
            return "responsemessage/error";
        }

}
}
