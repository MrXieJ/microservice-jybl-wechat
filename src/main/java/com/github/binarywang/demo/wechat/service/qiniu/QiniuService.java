package com.github.binarywang.demo.wechat.service.qiniu;

import com.github.binarywang.demo.wechat.service.WxUrlService;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Created by jie on 2017/8/7.
 * 使用七牛云做图片服务器
 */
@Service
public class QiniuService {
    @Autowired
    private WxUrlService wxUrlService;
    /*
    * 上传本地文件到七牛云,可覆盖
    * */
    public boolean fileupload(String filepath,String filename){
        //生成华南区的配置对象
        Configuration cfg = new Configuration(Zone.zone2());
        /*生成一个上传对象*/
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "AZSmkZL5bF1zNLWR0ixsxOG5BY1j_RhZe49dcnK5";
        String secretKey = "qMvDnUXKnMej9TkoYssL2ijbnFzCkcuNQQm5B6Sf";
        String bucket = "image";
        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = filepath;
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = filename;

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
            return true;
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                return false;
            }
            return false;
        }
    }
    /*
    * 获取二维码的七牛云链接
    * */
    public String getdoctorqrcodeurl(String filename){
        return wxUrlService.getQrcodeurl()+"/"+filename;
    }
}