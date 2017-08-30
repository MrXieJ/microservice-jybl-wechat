package com.github.binarywang.demo.wechat.controller.feign.mysql.healthmanage;

import com.github.binarywang.demo.wechat.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by jie on 2017/8/30.
 */
@CrossOrigin
@RestController
@RequestMapping(value="/healthmanage")
public class HealthManageController {

    @Autowired
    private   JsonResult jsonResult;
    @Autowired
    private   HealthManageClient healthManageClient;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /*
    * 2.3 保存或者更新健康检查信息
    * */
    @CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.GET,
            RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS,
            RequestMethod.HEAD, RequestMethod.PUT, RequestMethod.PATCH}, origins="*")
    @RequestMapping(value="/healthcheck/save",method = RequestMethod.POST)
    public JsonResult SaveHealthCheck(HttpServletRequest request){
        String result="error";
        String wechat_id=request.getParameter("wechat_id");
        int height=Integer.valueOf(request.getParameter("height")).intValue();
        float weight=Float.valueOf(request.getParameter("weight")).floatValue();
        String diabetes=request.getParameter("diabetes");
        String chd=request.getParameter("chd");
        String stroke=request.getParameter("stroke");
        String hypertension=request.getParameter("hypertension");
        String other_history=request.getParameter("other_history");
        String family_history=request.getParameter("family_history");
        String smoke=request.getParameter("smoke");
        String smoking=request.getParameter("smoking");
        String drink=request.getParameter("drink");
        String drinking=request.getParameter("drinking");
        HealthCheckEntity healthCheckEntity=new HealthCheckEntity( wechat_id, height,  weight,  diabetes,  chd,  stroke,  hypertension,  other_history,  family_history,  smoke,  smoking,  drink,  drinking);
        try{
        result=healthManageClient.SaveHealthTable(healthCheckEntity);
        }
        catch(Exception e){
            jsonResult.setErrorcode("10020");
            jsonResult.setMessage("there is an exception while saving healthcheck . exception:"+e.getMessage());
            jsonResult.setData(null);
            this.logger.error("保存健康检查信息时出现异常");
            return jsonResult;
        }
        if(result.equals("success")){
            jsonResult.setErrorcode("0");
            jsonResult.setMessage("saving healthchecktable success");
            jsonResult.setData(null);
            this.logger.info("成功保存健康检查信息");
            return jsonResult;
        }else{
            jsonResult.setErrorcode("10021");
            jsonResult.setMessage("saving healthchecktable error in database");
            jsonResult.setData(null);
            this.logger.error("保存健康检查信息时数据库服务发生错误");
            return jsonResult;
        }
    }
    /*
    *2.4 保存生化检查信息
    * */
    @CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.GET,
            RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS,
            RequestMethod.HEAD, RequestMethod.PUT, RequestMethod.PATCH}, origins="*")
    @RequestMapping(value="/biologycheck/save",method = RequestMethod.POST)
    public JsonResult SaveBiologyCheck(HttpServletRequest request){
        String result="error";
        String wechat_id=request.getParameter("wechat_id");
        String tch=request.getParameter("date");
        String fbg=request.getParameter("time");
        String tg=request.getParameter("systolic_pressure");
        String hdl=request.getParameter("diastolic_pressure");
        String ldl=request.getParameter("rhr");
        BiologyCheckEntity biologyCheckEntity=new BiologyCheckEntity( wechat_id,  tch,  fbg,  tg,  hdl,  ldl);
        try{
           result=healthManageClient.SaveBiologyTable(biologyCheckEntity);
        }catch(Exception e){
            jsonResult.setErrorcode("10022");
            jsonResult.setMessage("there is an exception while saving biologycheck . exception:"+e.getMessage());
            jsonResult.setData(null);
            this.logger.error("保存生化检查时出现异常");
            return jsonResult;
        }
        if(result.equals("success")){
            jsonResult.setErrorcode("0");
            jsonResult.setMessage("saving biologychecktable success");
            jsonResult.setData(null);
            this.logger.info("成功保存生化检查信息");
            return jsonResult;
        }else{
            jsonResult.setErrorcode("10023");
            jsonResult.setMessage("saving biologychecktable error in database");
            jsonResult.setData(null);
            this.logger.error("保存生化检查信息时数据库服务发生错误");
            return jsonResult;
        }
    }
    /*
    *2.5 保存血压心率信息
    * */
    @CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.GET,
            RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS,
            RequestMethod.HEAD, RequestMethod.PUT, RequestMethod.PATCH}, origins="*")
    @RequestMapping(value="/bloodcheck/save",method = RequestMethod.POST)
    public JsonResult SaveBloodCheck(HttpServletRequest request){
        String result="error";
        String wechat_id=request.getParameter("wechat_id");
        String date=request.getParameter("wechat_id");
        String time=request.getParameter("wechat_id");
        int systolic_pressure=Integer.valueOf(request.getParameter("wechat_id")).intValue();
        int diastolic_pressure=Integer.valueOf(request.getParameter("wechat_id")).intValue();
        int rhr=Integer.valueOf(request.getParameter("wechat_id")).intValue();
        BloodPressureEntity bloodPressureEntity=new BloodPressureEntity( wechat_id,  date,  time,  systolic_pressure,  diastolic_pressure,  rhr);
        try{
            result=healthManageClient.SaveBloodPressureTable(bloodPressureEntity);
        }catch(Exception e){
            jsonResult.setErrorcode("10024");
            jsonResult.setMessage("there is an exception while saving bloodcheck . exception:"+e.getMessage());
            jsonResult.setData(null);
            this.logger.error("保存血压心率时出现异常");
            return jsonResult;
        }
        if(result.equals("success")){
            jsonResult.setErrorcode("0");
            jsonResult.setMessage("saving bloodchecktable success");
            jsonResult.setData(null);
            this.logger.info("成功保存血压心率信息");
            return jsonResult;
        }else{
            jsonResult.setErrorcode("10025");
            jsonResult.setMessage("saving bloodchecktable error in database");
            jsonResult.setData(null);
            this.logger.error("保存血压心率信息时数据库服务发生错误");
            return jsonResult;
        }
    }
    /*
    *2.6 查找血压心率历史记录
    * @Param  timearea:时间区间（一周，一个月）
    * @Param  time:具体时间（晨起，睡前）
    * */
    @CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.GET,
            RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS,
            RequestMethod.HEAD, RequestMethod.PUT, RequestMethod.PATCH}, origins="*")
    @RequestMapping(value="/bloodcheck/get",method = RequestMethod.GET)
    public JsonResult FindBloodHistory(String timearea,String time){
        List<BloodPressureEntity> bloodPressurelist=null;
        try{
            bloodPressurelist=healthManageClient.GetBloodPressureTable(timearea,time);
        }catch(Exception e){
            jsonResult.setErrorcode("10026");
            jsonResult.setMessage("there is an exception while getting bloodcheck history . exception:"+e.getMessage());
            jsonResult.setData(null);
            this.logger.error("查找血压心率历史记录时出现异常");
            return jsonResult;
        }
        jsonResult.setErrorcode("0");
        jsonResult.setMessage("get bloodcheck history success");
        jsonResult.setData(bloodPressurelist);
        this.logger.error("成功获取血压心率历史记录");
        return jsonResult;
    }

    /*
    * 2.7 保存心电表
    * */
    @CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.GET,
            RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS,
            RequestMethod.HEAD, RequestMethod.PUT, RequestMethod.PATCH}, origins="*")
    @RequestMapping(value="/cardiogram/save",method = RequestMethod.POST)
    public JsonResult SaveCardiogram(HttpServletRequest request){
        String result="error";
        String wechat_id=request.getParameter("wechat_id");
        String cardiogram=request.getParameter("cardiogram");
        String date=request.getParameter("date");
        String remark=request.getParameter("remark");
        CardiogramEntity cardiogramEntity=new CardiogramEntity(wechat_id, cardiogram, date, remark);
        try{
            result=healthManageClient.SaveCardiogramTable(cardiogramEntity);
        }catch(Exception e){
            jsonResult.setErrorcode("10027");
            jsonResult.setMessage("there is an exception while saving cardiogram . exception:"+e.getMessage());
            jsonResult.setData(null);
            this.logger.error("保存心电表时出现异常");
            return jsonResult;
        }
        if(result.equals("success")){
            jsonResult.setErrorcode("0");
            jsonResult.setMessage("saving cardiogramtable success");
            jsonResult.setData(null);
            this.logger.info("成功保存心电表信息");
            return jsonResult;
        }else{
            jsonResult.setErrorcode("10028");
            jsonResult.setMessage("saving cardiogramktable error in database");
            jsonResult.setData(null);
            this.logger.error("保存心电表时数据库服务发生错误");
            return jsonResult;
        }
    }

    /*
    *2.8 生成风险评估报告
    * */
    @CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.GET,
            RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS,
            RequestMethod.HEAD, RequestMethod.PUT, RequestMethod.PATCH}, origins="*")
    @RequestMapping(value="/report/generate",method = RequestMethod.POST)
    public JsonResult GenerateReport(@RequestParam("wechat_id") String wechat_id){

        return jsonResult;
    }

    /*
    *2.9 查找所有的风险评估报告
    * */
    @CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.GET,
            RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS,
            RequestMethod.HEAD, RequestMethod.PUT, RequestMethod.PATCH}, origins="*")
    @RequestMapping(value="/report/getall",method = RequestMethod.GET)
    public JsonResult GetAllReport(@RequestParam("wechat_id")String wechat_id){
        return jsonResult;
    }

    /*
    *3.0 查找最新的风险评估报告
    * */
    @CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.GET,
            RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS,
            RequestMethod.HEAD, RequestMethod.PUT, RequestMethod.PATCH}, origins="*")
    @RequestMapping(value="/report/getnewest",method = RequestMethod.GET)
    public JsonResult GetNewestReport(@RequestParam("wechat_id")String wechat_id){
        return jsonResult;
    }

    /*
    * 3.1 查找已经进行过风险评估的总人数
    * */
    @CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.GET,
            RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS,
            RequestMethod.HEAD, RequestMethod.PUT, RequestMethod.PATCH}, origins="*")
    @RequestMapping(value="/report/getnumber",method = RequestMethod.GET)
    public JsonResult GetReportNumber(){
        return jsonResult;
    }

    /*
    * 3.2 获取具体患者的未读消息
    * */
    @CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.GET,
            RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS,
            RequestMethod.HEAD, RequestMethod.PUT, RequestMethod.PATCH}, origins="*")
    @RequestMapping(value="/messageremind/getunread",method = RequestMethod.GET)
    public JsonResult GetMessage(@RequestParam("wechat_id") String wechat_id){
        return jsonResult;
    }

    /*
    * 3.3 将具体的消息设置为已读
    * */
    @CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.GET,
            RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS,
            RequestMethod.HEAD, RequestMethod.PUT, RequestMethod.PATCH}, origins="*")
    @RequestMapping(value="/messageremind/setread",method = RequestMethod.GET)
    public JsonResult SetMessage(@RequestParam("message_id") String message_id){
        return jsonResult;
    }
}
