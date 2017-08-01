package com.github.binarywang.demo.wechat.controller.patient;

import com.github.binarywang.demo.wechat.entity.PatientEntity;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by jie on 2017/7/31.
 */
@FeignClient("eureka-patient-detail")
public interface PatientClient {
    @GetMapping("/patient/savedetail")
    String SaveDetail(PatientEntity patientEntity);
}
