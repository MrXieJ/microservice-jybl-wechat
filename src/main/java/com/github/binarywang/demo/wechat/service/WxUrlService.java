package com.github.binarywang.demo.wechat.service;
import org.springframework.stereotype.Service;
/**
 * Created by jie on 2017/8/3.
 * 用来获取基本的url
 */
@Service
public class WxUrlService {
    /*
    * 定义基本的url
    * */
    String baseurl="http://mrxiej.ngrok.wendal.cn";
    /*
    * imagebaseurl,七牛云链接
    * */
    String imagebaseurl="http://oubcp9t4y.bkt.clouddn.com";
    /*
    * 二维码访问链接
    * */
    String qrcodeurl=imagebaseurl+"/qrcode/doctor";

    public String getImagebaseurl() {
        return imagebaseurl;
    }

    public void setImagebaseurl(String imagebaseurl) {
        this.imagebaseurl = imagebaseurl;
    }

    public  String getBaseurl() {
        return baseurl;
    }

    public  String getQrcodeurl() {
        return qrcodeurl;
    }

}
