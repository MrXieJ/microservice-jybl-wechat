package scut.jiayibilin.wechat.controller.feign.mysql.doctor;

import scut.jiayibilin.wechat.entity.DoctorEntity;
import scut.jiayibilin.wechat.entity.JsonResult;
import scut.jiayibilin.wechat.entity.ServiceEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by jie on 2017/8/18.
 */
@CrossOrigin
@RestController
@RequestMapping(value="/doctor")
public class DoctorInfoController {
    @Autowired
    private DoctorClient doctorClient;
    @Autowired
    private JsonResult jsonResult;
    /*打印日志*/
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /*
    * 1.8根据医生手机号码获取医生实体类
    * */
    @CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.GET,
            RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS,
            RequestMethod.HEAD, RequestMethod.PUT, RequestMethod.PATCH}, origins="*")
    @RequestMapping(value="/get",method = RequestMethod.GET)
    public JsonResult GetDoctor(@RequestParam("phone") String phone){
        DoctorEntity doctorEntity=null;
        try {
             doctorEntity = doctorClient.findbydoctorphone(phone);
             if(doctorEntity==null) {
                jsonResult.setErrorcode("10005");
                jsonResult.setMessage("doctor don't exist");
                jsonResult.setData(null);
                this.logger.info("根据手机号码获取医生信息时，所查询的医生不存在");
             }else {
                jsonResult.setErrorcode("0");
                jsonResult.setMessage("get patientinfo success");
                jsonResult.setData(doctorEntity);
                this.logger.info("成功根据手机号码获取医生信息");
            }
        }
        catch (Exception e){
            jsonResult.setErrorcode("10007");
            jsonResult.setMessage("there is an exception while getting doctorinfo by phone\n"+e.getMessage());
            jsonResult.setData(null);
            this.logger.error("获取医生信息时发生异常",e.getMessage());
        }
        return jsonResult;
    }
    /*
    * 1.9获取医生列表
    * */
    @CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.GET,
            RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS,
            RequestMethod.HEAD, RequestMethod.PUT, RequestMethod.PATCH}, origins="*")
    @RequestMapping(value="/getlist",method = RequestMethod.GET)
    public JsonResult GetDoctorList(){
        List<DoctorEntity> doctorlist=null;
        try {
            doctorlist = doctorClient.findalldoctor();
            jsonResult.setErrorcode("0");
            jsonResult.setMessage("get doctorlist success");
            jsonResult.setData(doctorlist);
            this.logger.info("成功获取医生列表信息");
        }
        catch (Exception e){
            jsonResult.setErrorcode("10008");
            jsonResult.setMessage("there is an exception while getting doctorlist . exception:"+e.getMessage());
            jsonResult.setData(null);
            this.logger.error("获取医生列表时发生异常",e.getMessage());
        }
        return jsonResult;
    }
    /*
    * 2.0获取平台提供的所有服务包
    * */
    @CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.GET,
            RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS,
            RequestMethod.HEAD, RequestMethod.PUT, RequestMethod.PATCH}, origins="*")
    @RequestMapping(value="/service/get",method = RequestMethod.GET)
    public JsonResult GetService(){
        List<ServiceEntity> list=null;
        try {
            list = doctorClient.findallservice();
            jsonResult.setErrorcode("0");
            jsonResult.setMessage("get servicelist success");
            jsonResult.setData(list);
            this.logger.info("成功获取所有服务");
        }
        catch(Exception e){
            jsonResult.setErrorcode("10009");
            jsonResult.setMessage("there is an exception while getting servicelist . exception:"+e.getMessage());
            jsonResult.setData(null);
            this.logger.error("获取服务失败"+e.getMessage());
        }
        return jsonResult;
    }
}
