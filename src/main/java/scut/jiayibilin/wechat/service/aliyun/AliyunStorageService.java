package scut.jiayibilin.wechat.service.aliyun;

import com.aliyun.oss.OSSClient;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

/**
 * Created by jie on 2017/10/13.
 */
@Service
public class AliyunStorageService {
    public boolean fileupload(String filename,byte[] content){
        try{
        // endpoint以杭州为例，其它region请按实际情况填写
        String endpoint = "oss-cn-shenzhen.aliyuncs.com";
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建
        String accessKeyId = "LTAIbteonN24n3it";
        String accessKeySecret = "RZY6bgLo44qZT5juDI2Xw0r0bjNovH";
        // 创建OSSClient实例
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId,accessKeySecret);

        ossClient.putObject("jybl-photo", filename, new ByteArrayInputStream(content));
        // 关闭client
        ossClient.shutdown();}
        catch (Exception e){
            return false;
        }
        return true;
    }
    /*
   * 根据文件名判断阿里云服务器上是否存在相应的文件
   * */
    public boolean existfile(String filename){
        // endpoint以杭州为例，其它region请按实际情况填写
        String endpoint = "oss-cn-shenzhen.aliyuncs.com";
    // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建
        String accessKeyId = "LTAIbteonN24n3it";
        String accessKeySecret = "RZY6bgLo44qZT5juDI2Xw0r0bjNovH";
    // 创建OSSClient实例
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    // Object是否存在
        boolean found = ossClient.doesObjectExist("jybl-photo", filename);
    // 关闭client
        ossClient.shutdown();
        return found;
    }
}
