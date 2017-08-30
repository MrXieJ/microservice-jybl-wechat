package com.github.binarywang.demo.wechat.controller.feign.mysql.healthmanage;

import com.github.binarywang.demo.wechat.entity.*;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by jie on 2017/8/30.
 */
@FeignClient("eureka-server-mysql")
public interface HealthManageClient {
    /*
    * 调用远程服务下的保存健康信息表接口
    * */
    @RequestMapping(value="/healthmanage/savehealthtable" ,method= RequestMethod.POST)
    String SaveHealthTable(HealthCheckEntity healCheckEntity);

    /*
    * 调用远程服务下的保存生化检查表接口
    * */
    @RequestMapping(value="/healthmanage/savebiologytable" ,method= RequestMethod.POST)
    String SaveBiologyTable(BiologyCheckEntity biologyCheckEntity);

    /*
    * 调用远程服务下的保存血压心率表接口
    * */
    @RequestMapping(value="/healthmanage/savebloodpressuretable" ,method= RequestMethod.POST)
    String SaveBloodPressureTable(BloodPressureEntity bloodPressureEntity);

    /*
    * 调用远程服务下的查找血压心率记录接口
    * @Param  timearea:时间区间（一周，一个月）
    * @Param  time:具体时间（晨起，睡前）
    * */
    @RequestMapping(value="/healthmanage/getbloodpressuretable" ,method= RequestMethod.GET)
    List<BloodPressureEntity> GetBloodPressureTable(String timearea,String time);

    /*
    * 调用远程服务下的保存心电表接口
    * */
    @RequestMapping(value="/healthmanage/savecardiogramtable" ,method= RequestMethod.POST)
    String SaveCardiogramTable(CardiogramEntity cardiogramEntity);

    /*
    * 调用远程服务下的生成风险评估报告接口
    * */
    @RequestMapping(value="/healthmanage/generatereport" ,method= RequestMethod.POST)
    String GenerateReport(String openid);

    /*
    * 调用远程服务下的查找所有风险评估报告接口
    * */
    @RequestMapping(value="/healthmanage/getallreport" ,method= RequestMethod.GET)
    List<RiskReportEntity> GetAllReport(String openid);

    /*
    * 调用远程服务下的查找最新的风险评估报告接口
    * */
    @RequestMapping(value="/healthmanage/getnewestreport" ,method= RequestMethod.GET)
    RiskReportEntity GetNewestReport(String openid);

    /*
    * 调用远程服务下的查找风险评估人数接口
    * */
    @RequestMapping(value="/healthmanage/getreportnumber" ,method= RequestMethod.GET)
    Integer GetReportNumber();
}
