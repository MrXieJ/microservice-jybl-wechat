package scut.jiayibilin.wechat.controller.feign.mysql.patient;
import scut.jiayibilin.wechat.entity.*;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * Created by jie on 2017/7/31.
 * 远程调用eureka-paient-detail服务下的接口
 */
@FeignClient(name="eureka-server-mysql", url="http://mrxiej.ngrok.wendal.cn/api-mysql")
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
    /*
    * 调用远程服务下通过服务id列表返回服务接口
    * */
    @RequestMapping(value="/service/findbylist",method=RequestMethod.GET)
    List<ServiceEntity> findservice(List<Integer> idlist);
    /*
    * 调用远程服务下通过患者微信号查找购买的服务接口
    * */
    @RequestMapping(value="/patient/findmyservice",method = RequestMethod.GET)
    List<PurchasedServiceEntity> findmyservice(String wechat_id);
    /*
    * 调用远程服务下的购买服务接口,传入患者的openid和值为service主键id的list
    * */
    @RequestMapping(value="/patient/buyservice",method = RequestMethod.POST)
    String buyservice(@RequestParam("wechat_id") String wechat_id, @RequestParam("servicelist") List<Integer> servicelist,@RequestParam("phone")String phone);

    /*
    *调用远程接口下更新过期服务包接口,定时进行
     */
    @RequestMapping(value = "/patient/service/expired" ,method = RequestMethod.POST)
    String updatePurchasedServiceExpired();
    /*
    * 调用远程服务下的重复购买服务接口
    * */
    @RequestMapping(value="/patient/buyserviceagain",method = RequestMethod.POST)
    String buyserviceagain(@RequestParam("wechat_id") String wechat_id, @RequestParam("serviceid") String serviceid,@RequestParam("phone")String phone);

    /*
    * 调用远程服务下的患者评价医生接口
    * */
    @RequestMapping(value="/patient/evaluate",method = RequestMethod.POST)
    String evaluatedoctor(EvaluationEntity evaluationEntity);

    /*
    * 调用远程服务下的查找具体患者未读消息接口
    * */
    @RequestMapping(value="/patient/message/getunread" ,method= RequestMethod.GET)
    List<MessageRemindEntity> GetMessageUnread(String wechat_id);

    /*
    * 调用远程服务下的将具体消息设置为已读接口
    * */
    @RequestMapping(value="/patient/message/setread" ,method= RequestMethod.POST)
    String SetMessageRead(@RequestParam("wechat_id") String wechat_id, @RequestParam("message_id") String message_id);

    /*
    * 调用远程服务下的患者建议接口
    * */
    @RequestMapping(value="/patient/contact/suggest",method = RequestMethod.POST)
    String Suggest(SuggestionEntity suggestionEntity);



    /*
    *调用远程服务下的患者留言板接口
     */
    @RequestMapping(value = "/patient/messageboard/set",method = RequestMethod.POST)
    String setMessageBoard(MessageBoardEntity messageBoardEntity);

    /*
    *调用远程服务下的获取留言板最新回复的接口
     */
    @RequestMapping(value = "/patient/messageboard/getpatientnewestmessage", method = RequestMethod.GET)
    List<MessageBoardEntity> getNewestMessageBoardList(@RequestParam("wechat_id") String wechat_id);


    /*
    *调用远程服务下的获取一个留言板及其回复
     */
    @RequestMapping(value = "/patient/messageboard/getonemessage" ,method = RequestMethod.GET)
    List<MessageBoardEntity> getOneMessageAndReply(@RequestParam("id") int id,@RequestParam("who") int who);

    /*
    *调用远程接口下的删除留言板接口
     */
    @RequestMapping(value = "/patient/messageboard/delete",method = RequestMethod.POST)
    String deletePatientMessageBoard(@RequestParam("id")int id,@RequestParam("who") int who);

    /*
    *调用远程接口下的获取患者未读医生群发消息数量
     */
    @RequestMapping(value = "/patient/groupreceiving/unread")
     int getGroupReceivingUnread(@RequestParam("wechat_id")String wechat_id);

    /*
    *调用远程接口下的获取患者的医生群发消息
     */
    @RequestMapping(value = "/patient/groupreceiving/list")
    List<PatientGroupReceivingEntity> getPatientGroupReceiving(@RequestParam("wechat_id")String wechat_id);

    /*
    *调用远程接口下的删除患者的医生群发消息
     */
    @RequestMapping(value = "/patient/groupreceiving/delete",method = RequestMethod.POST)
    String deleteGroupReceiving(@RequestParam("id") int id);
    /*
   *调用远程接口下的设置患者的医生群发消息为已读
    */
    @RequestMapping(value = "/patient/groupreceiving/setread",method = RequestMethod.POST)
    String setGroupReceivingRead(@RequestParam("id") int id);
    /*
   *调用远程接口下的获取患者的一个医生群发消息的详细信息
    */
    @RequestMapping(value = "/patient/groupreceiving/get",method = RequestMethod.GET)
    PatientGroupReceivingEntity getGroupReceiving(@RequestParam("id") int id);
}
