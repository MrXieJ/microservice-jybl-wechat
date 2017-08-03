package com.github.binarywang.demo.wechat.controller.patient;

import com.github.binarywang.demo.wechat.entity.PatientEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by jie on 2017/7/31.
 */
@RestController
@RequestMapping(value="/patient")
public class PatientController {
    @Autowired
    PatientClient patientClient;

    @RequestMapping (value="/save",method= RequestMethod.POST)
    public String SavaDetail(HttpServletRequest request){
        String wechat_id=request.getParameter("openid");
        PatientEntity patientEntity=patientClient.findByWechatid(wechat_id);
        String name= request.getParameter("name");
        String id_card= request.getParameter("id_card");
        String sex= request.getParameter("sex");
        String strage= request.getParameter("age");
        int age=Integer.valueOf(strage);
        String phone=request.getParameter("phone");
        String detailed_address=request.getParameter("detailed_address");
        String provSelect= request.getParameter("provSelect");
        String citySelect= request.getParameter("citySelect");
        String areaSelect= request.getParameter("areaSelect");
        String address=provSelect+citySelect+areaSelect;
        String headpic=request.getParameter("headImgUrl");

        if(patientEntity==null){
            patientEntity=new PatientEntity(name,id_card,sex,age,phone,address,detailed_address,wechat_id,headpic);
            return patientClient.SaveDetail(patientEntity);}
        else{
            patientEntity=new PatientEntity(name,id_card,sex,age,phone,address,detailed_address,wechat_id,headpic);
            return patientClient.updatepatient(patientEntity);
        }

}
}
