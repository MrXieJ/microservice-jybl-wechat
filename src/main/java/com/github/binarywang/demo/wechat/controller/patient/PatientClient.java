package com.github.binarywang.demo.wechat.controller.patient;

import com.github.binarywang.demo.wechat.entity.PatientEntity;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by jie on 2017/7/31.
 * 远程调用eureka-paient-detail服务下的接口
 */
@FeignClient("eureka-patient-detail")
public interface PatientClient {
    /*
    * 调用远程服务下的保存患者个人信息接口
    * */
    @RequestMapping (value="/patient/savedetail" ,method= RequestMethod.POST)
    String SaveDetail(PatientEntity patientEntity);
    /*
    * 调用远程服务下的通过微信号查找患者接口
    * */
    @RequestMapping(value="/patient/findbywechatid",method= RequestMethod.GET)
    PatientEntity findByWechatid(String wechat_id);
    /*
    * 调用远程服务下的更新特定患者信息接口
    * */
    @RequestMapping(value="/patient/updatepatient",method=RequestMethod.POST)
    String updatepatient(PatientEntity patientEntity);
}
