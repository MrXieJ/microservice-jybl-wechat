package com.github.binarywang.demo.wechat.controller.patient;

import com.github.binarywang.demo.wechat.entity.PatientEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * Created by jie on 2017/7/31.
 */
@Controller(value="/patient")
public class PatientController {
    @Autowired
    PatientClient patientClient;
    @RequestMapping(value="/savedetail")
    public String SavaDetail(@Valid PatientEntity patientEntity){
        return patientClient.SaveDetail(patientEntity);
    }
}
