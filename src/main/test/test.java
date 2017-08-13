package com.github.binarywang.demo.wechat.test;

import com.github.binarywang.demo.wechat.service.WxUrlService;
import com.github.binarywang.demo.wechat.service.qiniu.QiniuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jie on 2017/8/9.
 */
@RestController
public class test {
    @Autowired
    QiniuService qiniuService;
    @Autowired
    WxUrlService wxUrlService;
    @RequestMapping(value="/test/qiniu")
    public String test(){
        if(qiniuService.fileupload("G:\\qrcode\\doctor137.jpg","doctor137.jpg")){
            return "success";
        }
        return "false";
    }
    @RequestMapping(value="/test/image")
    public String test(@RequestParam("filename") String filename){
        return wxUrlService.getImagebaseurl()+"/"+filename;
    }
}
