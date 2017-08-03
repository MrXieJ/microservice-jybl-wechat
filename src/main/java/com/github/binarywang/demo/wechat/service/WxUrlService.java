package com.github.binarywang.demo.wechat.service;

import org.springframework.boot.autoconfigure.domain.EntityScan;
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

    String qrcodeurl=baseurl+"/qrcode";

    public  String getBaseurl() {
        return baseurl;
    }

    public  String getQrcodeurl() {
        return qrcodeurl;
    }

}
