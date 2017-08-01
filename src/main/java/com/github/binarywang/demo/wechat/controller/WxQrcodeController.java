package com.github.binarywang.demo.wechat.controller;

import com.github.binarywang.demo.wechat.entity.QRParamsException;
import com.github.binarywang.utils.qrcode.QrcodeUtils;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpQrcodeService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * Created by jie on 2017/7/22.
 */
@RestController
@RequestMapping(value="/wechat/qrcode")
public class WxQrcodeController implements WxMpQrcodeService{
    @Autowired
    private WxMpService wxService;
    /*
    * 生成不带Logo的二维码
    * */
    @RequestMapping("/createwithoutlogo")
    public void qrCodeCreate(HttpServletResponse response) throws WxErrorException {
        String url="http://mrxiej.ngrok.wendal.cn/Xinyijia/test/testQrCode";
        byte[] code = QrcodeUtils.createQrcode(url, null);
        response.setHeader("Content-Type", "image/jpeg");
        try {
            response.getOutputStream().write(code);
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*
    * 生成带Logo的医生二维码
    * */
    @RequestMapping(value="/createwithlogo")
    public void qrCodeCreateLogo(HttpServletResponse response,@RequestParam("doctorid")String doctorid) throws WxErrorException, QRParamsException {
        String url = "http://mrxiej.ngrok.wendal.cn/Xinyijia/test/qrcode/"+doctorid;
        File logo = new File("D:/logo.jpg");
        byte[] code = QrcodeUtils.createQrcode(url, logo);
        response.setHeader("Content-Type", "image/jpeg");
        try {
            response.getOutputStream().write(code);
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
/*      QRCodeParams params = new QRCodeParams();
        params.setTxt("http://mrxiej.ngrok.wendal.cn/Xinyijia/test/testQrCode");
        params.setFilePath("D:/");
        params.setFileName("temp.jpg");
        QRCodeUtil.generateQRImage(params);

        QRCodeParams params2 = new QRCodeParams();
        params2.setTxt("http://mrxiej.ngrok.wendal.cn/Xinyijia/test/testQrCode");
        params2.setFilePath("D:/");
        params2.setFileName("temp2.jpg");
        params2.setOnColor(0xFFFF0000);
        params2.setOffColor(0xFFFFFFFF);
        params2.setWidth(400);
        params2.setHeight(400);
        params2.setLogoPath("D:/logo.jpg");
        QRCodeUtil.generateQRImage(params2);*/
    /*
    * 换取临时二维码ticket
    * */
    @Override
    @PostMapping("/createTmp")
    public WxMpQrCodeTicket qrCodeCreateTmpTicket(int sceneId, Integer expireSeconds) throws WxErrorException {
        return wxService.getQrcodeService().qrCodeCreateTmpTicket(sceneId,expireSeconds);
    }
    /*
    * 换取永久二维码ticket
    * */
    @Override
    @PostMapping("/createLast/int")
    public WxMpQrCodeTicket qrCodeCreateLastTicket(int sceneId) throws WxErrorException {
        return wxService.getQrcodeService().qrCodeCreateLastTicket(sceneId);
    }

    /*
    *通过字符类型的sceneStr生成Qrcode
    * */
    @Override
    @PostMapping("/createLast/string")
    public WxMpQrCodeTicket qrCodeCreateLastTicket(String sceneStr) throws WxErrorException {
        return wxService.getQrcodeService().qrCodeCreateLastTicket(sceneStr);
    }

    /*
    * 通过ticket获取二维码
    * */
    @Override
    @RequestMapping("/getQrCode")
    public File qrCodePicture(WxMpQrCodeTicket ticket) throws WxErrorException {
        return wxService.getQrcodeService().qrCodePicture(ticket);
    }

    /*
    * 通过ticket和needshortUrl获取短url
    * */
    @Override
    @RequestMapping("/getQrCodeUrl")
    public String qrCodePictureUrl(String ticket, boolean needShortUrl) throws WxErrorException {
        return wxService.getQrcodeService().qrCodePictureUrl(ticket,needShortUrl);
    }

    /*
    * 通过ticket获取二维码
    * */
    @Override
    @RequestMapping("/getQrCodeUrl/ticket")
    public String qrCodePictureUrl(String ticket) throws WxErrorException {
        return wxService.getQrcodeService().qrCodePictureUrl(ticket);
    }


}
