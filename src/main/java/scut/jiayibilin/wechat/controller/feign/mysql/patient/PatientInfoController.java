package scut.jiayibilin.wechat.controller.feign.mysql.patient;

import me.chanjar.weixin.common.bean.WxJsapiSignature;
import scut.jiayibilin.wechat.controller.feign.mysql.doctor.DoctorClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import scut.jiayibilin.wechat.entity.*;
import scut.jiayibilin.wechat.service.MyWxMpService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jie on 2017/8/21.
 */
@CrossOrigin
@RestController
@RequestMapping(value="/patientinfo")
public class PatientInfoController {
    @Autowired
    private  PatientClient patientClient;
    @Autowired
    private DoctorClient doctorClient;
    @Autowired
    private JsonResult jsonResult;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /*
    * 1.1保存患者信息
    * */
    @CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.GET,
            RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS,
            RequestMethod.HEAD, RequestMethod.PUT, RequestMethod.PATCH}, origins="*")
    @RequestMapping(value="/save",method = RequestMethod.POST)
    public JsonResult patientsave(HttpServletRequest request){
    try{
        String wechat_id = request.getParameter("wechat_id");
        String name = request.getParameter("name");
        String id_card = request.getParameter("id_card");
        String sex = request.getParameter("sex");
        String strage = request.getParameter("age");
        int age = Integer.valueOf(strage);
        String phone = request.getParameter("phone");
        String detailed_address = request.getParameter("detailed_address");
        String address = request.getParameter("address");
        String headpic = request.getParameter("headpic");
        PatientEntity patientEntity = new PatientEntity(name, id_card, sex, age, phone, address, detailed_address, wechat_id, headpic);
        patientClient.SaveDetail(patientEntity);
        jsonResult.setErrorcode("0");
        jsonResult.setMessage("save patientinfo success");
        jsonResult.setData(null);
        this.logger.info("成功保存患者信息");
    }
    catch(Exception e){
        jsonResult.setErrorcode("10001");
        jsonResult.setMessage("there is an exception while saving patientinfo . exception:"+e.getMessage());
        jsonResult.setData(null);
        this.logger.error("保存患者信息出错"+e.getMessage());
        return jsonResult;
    }
    return jsonResult;
    }
    /*
    * 1.2更新患者信息
    * */
    @CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.GET,
            RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS,
            RequestMethod.HEAD, RequestMethod.PUT, RequestMethod.PATCH}, origins="*")
    @RequestMapping(value="/update",method = RequestMethod.POST)
    public JsonResult patientupdate(HttpServletRequest request){
        try{
            String wechat_id = request.getParameter("wechat_id");
            String name = request.getParameter("name");
            String id_card = request.getParameter("id_card");
            String sex = request.getParameter("sex");
            String strage = request.getParameter("age");
            int age = Integer.valueOf(strage);
            String phone = request.getParameter("phone");
            String detailed_address = request.getParameter("detailed_address");
            String address = request.getParameter("address");
            String headpic = request.getParameter("headpic");
            PatientEntity patientEntity = new PatientEntity(name, id_card, sex, age, phone, address, detailed_address, wechat_id, headpic);
            patientClient.updatepatient(patientEntity);
            jsonResult.setErrorcode("0");
            jsonResult.setMessage("update patientinfo success");
            jsonResult.setData(null);
            this.logger.info("成功更新患者信息");
        }
        catch(Exception e){
            jsonResult.setData(null);
            jsonResult.setMessage("there is an exception while updating patientinfo . exception:"+e.getMessage());
            jsonResult.setErrorcode("10002");
            this.logger.error("更新患者信息出错",e.getMessage());
            return jsonResult;
        }
        return jsonResult;
    }
    /*
    * 1.3根据患者微信号获取患者所有信息
    * */
    @CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.GET,
            RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS,
            RequestMethod.HEAD, RequestMethod.PUT, RequestMethod.PATCH}, origins="*")
    @RequestMapping(value="/get",method = RequestMethod.GET)
    public JsonResult patientinfo(@RequestParam("wechat_id") String wechat_id){
        PatientEntity patientEntity=null;
        try {
            patientEntity = patientClient.findByWechatid(wechat_id);
            if(patientEntity==null) {
                jsonResult.setErrorcode("10003");
                jsonResult.setMessage("patient don't exist");
                jsonResult.setData(null);
                this.logger.info("获取患者信息时，所查询的患者不存在");
            }else {
                jsonResult.setErrorcode("0");
                jsonResult.setMessage("get patientinfo success");
                jsonResult.setData(patientEntity);
                this.logger.info("成功获取患者信息");
            }
        }
        catch (Exception e){
            jsonResult.setErrorcode("10004");
            jsonResult.setMessage("there is an exception while getting patientinfo . exception:"+e.getMessage());
            jsonResult.setData(null);
            this.logger.error("获取患者信息失败",e.getMessage());
        }
        return jsonResult;
    }
    /*
    * 1.4根据微信号获取我的医生
    * */
    @CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.GET,
            RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS,
            RequestMethod.HEAD, RequestMethod.PUT, RequestMethod.PATCH}, origins="*")
    @RequestMapping(value="/doctor/get",method = RequestMethod.GET)
    public JsonResult getmydoctor(@RequestParam("wechat_id") String wechat_id){
        DoctorEntity doctorEntity=null;
        try{
            String phone=patientClient.findmydoctor(wechat_id);
            if(phone==null) {
                jsonResult.setErrorcode("10005");
                jsonResult.setMessage("doctor don't exist");
                jsonResult.setData(null);
                this.logger.info("获取我的医生信息时，所查询我的医生不存在");
            }
            doctorEntity=doctorClient.findbydoctorphone(phone);
                jsonResult.setErrorcode("0");
                jsonResult.setMessage("get mydoctor success");
                jsonResult.setData(doctorEntity);
                this.logger.info("成功获取我的医生信息");
        }
        catch(Exception e){
            jsonResult.setErrorcode("10006");
            jsonResult.setMessage("there is an exception while getting mydoctor . exception:"+e.getMessage());
            jsonResult.setData(doctorEntity);
            this.logger.error("获取我的医生时发生异常"+e.getMessage());
        }
        return jsonResult;
    }
    /*
    * 1.5根据服务主键列表返回服务实体list
    * */
    @CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.GET,
            RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS,
            RequestMethod.HEAD, RequestMethod.PUT, RequestMethod.PATCH}, origins="*")
    @RequestMapping(value="/service/getbylist",method=RequestMethod.GET)
    public JsonResult getservice(@RequestParam("servicelist") String servicelist){
        List<ServiceEntity> list=null;
       try{
            List<String>  templist = Arrays.asList(servicelist.split(","));
            List<Integer> intlist= new ArrayList<>();
            for(String str : templist) {
                Integer i =Integer.valueOf(str);
                intlist.add(i);
            }
            list=patientClient.findservice(intlist);
            jsonResult.setErrorcode("0");
            jsonResult.setMessage("get service by servicelist success");
            jsonResult.setData(list);
            this.logger.info("成功根据服务list获取服务");
       }
       catch(Exception e){
           jsonResult.setErrorcode("10013");
           jsonResult.setMessage("there is an exception while getting servicelist by idlist . exception:"+e.getMessage());
           jsonResult.setData(null);
            this.logger.error("根据服务list获取服务发生异常"+e.getMessage());
        }
        return jsonResult;
    }
    /*
    * 1.6根据患者微信号获取我购买的服务
    * */
    @CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.GET,
            RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS,
            RequestMethod.HEAD, RequestMethod.PUT, RequestMethod.PATCH}, origins="*")
    @RequestMapping(value="/service/get",method=RequestMethod.GET)
    public JsonResult getmyservice(@RequestParam("wechat_id")String wechat_id){
        List<PurchasedServiceEntity> list=null;
        try{
            list=patientClient.findmyservice(wechat_id);
            jsonResult.setErrorcode("0");
            jsonResult.setMessage("get myservice success");
            jsonResult.setData(list);
            this.logger.info("成功获取我购买的服务");
        }
        catch(Exception e){
            jsonResult.setErrorcode("10014");
            jsonResult.setMessage("there is an exception while getting myservice . exception:"+e.getMessage());
            jsonResult.setData(null);
            this.logger.error("获取我购买的服务失败"+e.getMessage());
        }
        return jsonResult;
    }
    /*
    * 1.7根据患者openid和服务主键list购买服务
    * */
    @CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.GET,
            RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS,
            RequestMethod.HEAD, RequestMethod.PUT, RequestMethod.PATCH}, origins="*")
    @RequestMapping(value="/service/buy",method = RequestMethod.POST)
    public JsonResult buyservice(@RequestParam("wechat_id") String wechat_id,@RequestParam("servicelist") String servicelist,@RequestParam("phone")String phone){
        try{
            List<String>  templist = Arrays.asList(servicelist.split(","));
            List<Integer> intlist= new ArrayList<>();
            for(String str : templist) {
                Integer i =Integer.valueOf(str);
                intlist.add(i);
            }
            String result=patientClient.buyservice(wechat_id,intlist,phone);
            if(result.equals("success")){
                jsonResult.setErrorcode("0");
                jsonResult.setMessage("buyservice success");
                jsonResult.setData(null);
            }else if(result.equals("watch")) {
                jsonResult.setErrorcode("1");
                jsonResult.setMessage("patient has watched doctor .");
                jsonResult.setData(null);
            }else{
                    jsonResult.setErrorcode("10012");
                    jsonResult.setMessage("there is an exception in mysql while buying service .");
                    jsonResult.setData(null);
                }
        }
        catch(Exception e){
            this.logger.error("购买服务时发生异常"+e.getMessage());
            jsonResult.setErrorcode("10013");
            jsonResult.setMessage("there is an exception while buying service . exception:"+e.getMessage());
            jsonResult.setData(null);
        }
        return jsonResult;
    }
    /*
    * 3.5根据患者openid和服务主键id重复购买服务
    * */
    @CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.GET,
            RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS,
            RequestMethod.HEAD, RequestMethod.PUT, RequestMethod.PATCH}, origins="*")
    @RequestMapping(value="/service/buyagain",method = RequestMethod.POST)
    public JsonResult buyserviceagain(@RequestParam("wechat_id") String wechat_id,@RequestParam("serviceid") String serviceid,@RequestParam("phone")String phone){
        try{
            String result=patientClient.buyserviceagain(wechat_id,serviceid,phone);
            if(result.equals("success")){
                jsonResult.setErrorcode("0");
                jsonResult.setMessage("buyservice again success");
                jsonResult.setData(null);
            }else{
                jsonResult.setErrorcode("10012");
                jsonResult.setMessage("there is an exception in mysql while buying service .");
                jsonResult.setData(null);
            }
        }
        catch(Exception e){
            this.logger.error("重新购买服务时发生异常"+e.getMessage());
            jsonResult.setErrorcode("10013");
            jsonResult.setMessage("there is an exception while buying service . exception:"+e.getMessage());
            jsonResult.setData(null);
        }
        return jsonResult;
    }
    /*
    *患者评价医生
    * */
    @CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.GET,
            RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS,
            RequestMethod.HEAD, RequestMethod.PUT, RequestMethod.PATCH}, origins="*")
    @RequestMapping(value="/evaluate",method = RequestMethod.POST)
    public JsonResult evaluate(@RequestBody EvaluationEntity evaluationEntity){
        try{
            String result=patientClient.evaluatedoctor(evaluationEntity);
            if(result.equals("success")){
                jsonResult.setErrorcode("0");
                jsonResult.setMessage("evaluate success");
                jsonResult.setData(null);
            }else{
                jsonResult.setErrorcode("10001");
                jsonResult.setMessage("there is an error in mysql while evaluating .");
                jsonResult.setData(null);
            }
        }
        catch(Exception e){
            this.logger.error("评价时发生异常"+e.getMessage());
            jsonResult.setErrorcode("10001");
            jsonResult.setMessage("there is an exception while evaluating . exception:"+e.getMessage());
            jsonResult.setData(null);
        }
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
    *联系页面提交建议
    * */
    @CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.GET,
            RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS,
            RequestMethod.HEAD, RequestMethod.PUT, RequestMethod.PATCH}, origins="*")
    @RequestMapping(value="/contact/suggest",method = RequestMethod.POST)
    public JsonResult contact_sug(@RequestBody SuggestionEntity suggestionEntity){
        try{
            String result=patientClient.Suggest(suggestionEntity);
            this.logger.info(result);
            if(result.equals("success")){
                jsonResult.setErrorcode("0");
                jsonResult.setMessage("suggest success");
                jsonResult.setData(null);
            }else if(result.equals("count")){
                jsonResult.setErrorcode("1");
                jsonResult.setMessage("error by suggestion count more than 3 times one day .");
                jsonResult.setData(null);
            }else{
                jsonResult.setErrorcode("10001");
                jsonResult.setMessage("there is an exception by mysql while suggesting .");
                jsonResult.setData(null);
            }
        }
        catch(Exception e){
            this.logger.error("建议时发生异常"+e.getMessage());
            jsonResult.setErrorcode("10001");
            jsonResult.setMessage("there is an exception while suggesting . exception:"+e.getMessage());
            jsonResult.setData(null);
        }
        return jsonResult;
    }



    /*
    *添加留言和回复
     */
    @CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.GET,
            RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS,
            RequestMethod.HEAD, RequestMethod.PUT, RequestMethod.PATCH}, origins="*")
    @RequestMapping(value="/messageboard/set",method = RequestMethod.POST)
    public JsonResult setMessageBoard(@RequestBody MessageBoardEntity messageBoardEntity){
        try{
            String result = patientClient.setMessageBoard(messageBoardEntity);
            this.logger.info(result);
            if(result.equals("success")){
                jsonResult.setErrorcode("0");
                jsonResult.setMessage("set message board success");
                jsonResult.setData(null);
            }else if(result.equals("error")){
                jsonResult.setErrorcode("1");
                jsonResult.setMessage("set message board error");
                jsonResult.setData(null);
            }else{
                jsonResult.setErrorcode("10001");
                jsonResult.setMessage("there is an exception by mysql while setting message board");
                jsonResult.setData(null);
            }
        }catch(Exception e){
            this.logger.error("设置留言板时发生异常"+e.getMessage());
            jsonResult.setErrorcode("10001");
            jsonResult.setMessage("there is an exception while  setting message board. exception:"+e.getMessage());
            jsonResult.setData(null);
        }
        return jsonResult;
    }


    /*
   *获取患者最新留言和回复
    */
    @CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.GET,
            RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS,
            RequestMethod.HEAD, RequestMethod.PUT, RequestMethod.PATCH}, origins="*")
    @RequestMapping(value="/messageboard/getnewestmessagwe",method = RequestMethod.GET)
    public JsonResult getMessageBoardNewest(@RequestParam("wechat_id") String wechat_id){
        try{
            List<MessageBoardEntity> list=patientClient.getNewestMessageBoardList(wechat_id);
            jsonResult.setErrorcode("0");
            jsonResult.setMessage("get messageboard  success");
            jsonResult.setData(list);
            this.logger.info("成功获取患者的留言板列表");
        }catch(Exception e){
            this.logger.error("获取留言板时发生异常"+e.getMessage());
            jsonResult.setErrorcode("10001");
            jsonResult.setMessage("there is an exception while  getting message board. exception:"+e.getMessage());
            jsonResult.setData(null);
        }
        return jsonResult;
    }


    /*
    *获取患者一个留言板及其回复
     */
    @CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.GET,
            RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS,
            RequestMethod.HEAD, RequestMethod.PUT, RequestMethod.PATCH}, origins="*")
    @RequestMapping(value="/messageboard/getone",method = RequestMethod.GET)
    public JsonResult getOneMessageBoard(@RequestParam int id){
        try{
            List<MessageBoardEntity> list=patientClient.getOneMessageAndReply(id);
            jsonResult.setErrorcode("0");
            jsonResult.setMessage("get one messageboard and reply  success");
            jsonResult.setData(list);
            this.logger.info("成功获取患者的一个留言板及其回复信息");
        }catch(Exception e){
            this.logger.error("获取留言板及其回复时发生异常"+e.getMessage());
            jsonResult.setErrorcode("10001");
            jsonResult.setMessage("there is an exception while  getting one message board.and reply exception:"+e.getMessage());
            jsonResult.setData(null);
        }
        return jsonResult;
    }


    /*
    *设置留言回复
     */
//    @CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.GET,
//            RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS,
//            RequestMethod.HEAD, RequestMethod.PUT, RequestMethod.PATCH}, origins="*")
//    @RequestMapping(value="/messageboard/setreply",method = RequestMethod.POST)
//    public JsonResult setMessageReply(@RequestBody MessageReplyEntity messageReplyEntity){
//        try {
//            String result = patientClient.setMessageReply(messageReplyEntity);
//            this.logger.info(result);
//            if (result.equals("success")) {
//                jsonResult.setErrorcode("0");
//                jsonResult.setMessage("set message board reply success");
//                jsonResult.setData(null);
//            } else if (result.equals("error")) {
//                jsonResult.setErrorcode("1");
//                jsonResult.setMessage("set message board reply error");
//                jsonResult.setData(null);
//            }
//        }catch(Exception e){
//                this.logger.error("建议时发生异常"+e.getMessage());
//                jsonResult.setErrorcode("10001");
//                jsonResult.setMessage("there is an exception while suggesting . exception:"+e.getMessage());
//                jsonResult.setData(null);
//            }
//        return jsonResult;
//    }

    /*
    *获取医生回复
     */
    @CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.GET,
            RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS,
            RequestMethod.HEAD, RequestMethod.PUT, RequestMethod.PATCH}, origins="*")
    @RequestMapping(value="/messageboard/getreply",method = RequestMethod.POST)
    public MessageReplyEntity getMessageReply(@RequestBody MessageReplyEntity messageReplyEntity){
return null;
    }
}
