package scut.jiayibilin.wechat.controller.Schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import scut.jiayibilin.wechat.controller.feign.mysql.healthmanage.HealthManageClient;
import scut.jiayibilin.wechat.controller.feign.mysql.patient.PatientClient;

/**
 * Created by jie on 2017/10/17.
 */
@Component
public class PatientMessage {
//    @Autowired
//    HealthManageClient healthManageClient;
//    @Autowired
//    PatientClient patientClient;
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//    @Scheduled(cron="0 20 0-23 * * ?")
//    public void executeFileDownLoadTask() {
//        healthManageClient.MessageGenerate();
//        this.logger.info("成功生成每天的患者消息");
//    }
//    @Scheduled(cron = "0 22 0-23 * * ?")
//    public void updatePurchasedServiceExpired(){
//       try{
//        String result = patientClient.updatePurchasedServiceExpired();
//           if(result.equals("success")){
//        this.logger.info("成功更新过期服务包");
//           }else{
//        this.logger.info("更新过期服务包失败");
//           }
//       }catch (Exception e){
//        this.logger.info("更新过期服务包出现问题" + e.toString());
//       }
//    }
}
