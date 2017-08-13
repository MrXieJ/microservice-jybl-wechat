package com.github.binarywang.demo.wechat.controller.doctor;

import com.github.binarywang.demo.wechat.controller.patient.PatientClient;
import com.github.binarywang.demo.wechat.entity.DoctorEntity;
import com.github.binarywang.demo.wechat.service.MyWxMpService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by jie on 2017/8/8.
 */
@FeignClient("eureka-server-mysql")
public interface DoctorClient {
    /*
    * 调用远程服务下通过医生id号返回医生实体接口
    * */
    @RequestMapping(value="/doctor/findbyphone",method = RequestMethod.GET)
    DoctorEntity findbydoctorphone(@RequestParam("phone") String phone);
    /*
    * 调用远程服务下返回全部医生接口
    * */
    @RequestMapping(value="/doctor/findall",method = RequestMethod.GET)
    List<DoctorEntity> findalldoctor();
}
