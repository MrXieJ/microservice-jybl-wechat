package com.github.binarywang.demo.wechat.controller.patient;
import com.github.binarywang.demo.wechat.entity.PatientEntity;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Created by jie on 2017/7/31.
 * 远程调用eureka-paient-detail服务下的接口
 */
@FeignClient("eureka-server-mysql")
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
    /*
    * 调用远程服务下患者关注医生接口
    * */
    @RequestMapping(value="/patient/watchdoctor",method = RequestMethod.POST)
    String watchdoctor(@RequestParam("wechat_id") String wechat_id, @RequestParam("phone") String phone);
    /*
    * 调用远程服务下通过患者微信号查找关注的医生电话接口
    * */
    @RequestMapping(value="/patient/findmydoctor",method = RequestMethod.GET)
    String findmydoctor(String wechat_id);

}
