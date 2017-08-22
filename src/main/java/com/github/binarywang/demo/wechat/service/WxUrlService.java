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
    String baseurl="http://mrxiej.ngrok.wendal.cn/api-wechat";
    /*
    * imagebaseurl,七牛云链接
    * */
    String imagebaseurl="http://oubcp9t4y.bkt.clouddn.com";
    /*
    * 医生二维码访问后台的链接
    * */
    String doctorqrcodeurl=baseurl+"/qrcode/doctor";

    public String getImagebaseurl() {
        return imagebaseurl;
    }

    public void setImagebaseurl(String imagebaseurl) {
        this.imagebaseurl = imagebaseurl;
    }

    public  String getBaseurl() {
        return baseurl;
    }

    public  String getDoctorQrcodeurl() {
        return doctorqrcodeurl;
    }

}
