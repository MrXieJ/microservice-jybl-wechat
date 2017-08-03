package com.github.binarywang.demo.wechat.controller.doctorqrcode;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;

/**
 * Created by jie on 2017/8/3.
 */
@Controller
@RequestMapping(value="/qrcode")
public class QrcodeController {
    /*
    * 根据医生二维码跳转到医生主页
    * */
    @RequestMapping(value="/{doctorid}")
    public String qrcode(@PathVariable String doctorid, Model model){
        model.addAttribute("doctorid",doctorid);
        return"doctorinfo";
    }
    /*
    *根据医生id返回二维码Url
    */
    @ResponseBody
    @RequestMapping(value="findbyid")
    public String findbydoctorid(String doctorid) {
        File temp=new File("G:/qrcode/doctor"+doctorid+".jpg");
        if(temp.exists()){
            return "125.216.243.165:1000/qrcode/doctor"+doctorid+".jpg";
        }
        else{
            return "该医生二维码还未生成，请前往后台生成二维码";
        }
    }
}
