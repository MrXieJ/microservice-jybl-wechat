package scut.jiayibilin.wechat.controller.Schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import scut.jiayibilin.wechat.controller.feign.mysql.healthmanage.HealthManageClient;

/**
 * Created by jie on 2017/10/17.
 */
@Component
public class PatientMessage {
    @Autowired
    HealthManageClient healthManageClient;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Scheduled(cron="0 39 16 * * ?")
    public void executeFileDownLoadTask() {
        healthManageClient.MessageGenerate();
        this.logger.info("成功生成每天的患者消息");
    }
}
