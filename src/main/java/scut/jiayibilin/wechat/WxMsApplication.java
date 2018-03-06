package scut.jiayibilin.wechat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Jie
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@EnableScheduling
public class WxMsApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(WxMsApplication.class, args);
    }
}
