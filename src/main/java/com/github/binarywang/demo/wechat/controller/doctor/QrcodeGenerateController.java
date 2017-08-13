package com.github.binarywang.demo.wechat.controller.doctor;
import com.github.binarywang.demo.wechat.entity.QRParamsException;
import com.github.binarywang.demo.wechat.service.WxUrlService;
import com.github.binarywang.demo.wechat.utils.CreateFileUtil;
import com.github.binarywang.utils.qrcode.QrcodeUtils;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpQrcodeService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by jie on 2017/7/22.
 */
@Controller
@RequestMapping(value="/doctor/qrcode")
public class QrcodeGenerateController implements WxMpQrcodeService{
    @Autowired
    private WxUrlService wxUrlService;
    @Autowired
    private WxMpService wxService;
    /*
    * 生成带Logo的医生二维码
    * */
    @ResponseBody
    @RequestMapping(value="/create")
    public String qrCodeCreateLogo(@RequestParam("phone")String phone) throws WxErrorException, QRParamsException,IOException {
        String url = wxUrlService.getQrcodeurl()+"/"+phone;
        File logo = new File("G:/logo/logo.jpg");
        String qrcodepath="G:/qrcode/doctor"+phone+".jpg";
        if( CreateFileUtil.createFile(qrcodepath) ){
        byte[] code = QrcodeUtils.createQrcode(url, logo);
        File file = new File(qrcodepath);
        OutputStream output = new FileOutputStream(file);
        BufferedOutputStream bufferedOutput = new BufferedOutputStream(output);
        bufferedOutput.write(code);
        return "成功生成医生二维码文件";
        }
        else {
            return "该医生二维码文件已存在";
        }
    }
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
