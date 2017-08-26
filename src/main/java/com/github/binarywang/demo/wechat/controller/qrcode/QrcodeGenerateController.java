package com.github.binarywang.demo.wechat.controller.qrcode;

import com.github.binarywang.demo.wechat.entity.JsonResult;
import com.github.binarywang.demo.wechat.entity.QRParamsException;
import com.github.binarywang.demo.wechat.service.MyWxMpService;
import com.github.binarywang.demo.wechat.service.WxUrlService;
import com.github.binarywang.demo.wechat.service.qiniu.QiniuService;
import com.github.binarywang.demo.wechat.utils.CreateFileUtil;
import com.github.binarywang.utils.qrcode.QrcodeUtils;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpQrcodeService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.*;

/**
 * Created by jie on 2017/7/22.
 */
@Controller
public class QrcodeGenerateController implements WxMpQrcodeService{
    @Autowired
    private WxUrlService wxUrlService;
    @Autowired
    private WxMpService wxService;
    @Autowired
    private MyWxMpService myWxMpService;
    @Autowired
    private QiniuService qiniuService;
    @Autowired
    private JsonResult jsonResult;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /*
    * 2.1根据医生电话号码生成医生二维码,并上传到七牛云
    * */
    @CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.GET,
            RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS,
            RequestMethod.HEAD, RequestMethod.PUT, RequestMethod.PATCH}, origins="*")
    @ResponseBody
    @RequestMapping(value="/qrcode/create")
    public JsonResult qrCodeCreateLogo(@RequestParam("phone")String phone) throws WxErrorException, QRParamsException,IOException {
        try{
            CreateFileUtil createFileUtil=new CreateFileUtil();
            String url = myWxMpService.oauth2buildAuthorizationUrl(wxUrlService.getDoctorQrcodeurl()+"/"+phone, WxConsts.OAUTH2_SCOPE_USER_INFO, "qrcode");
            /*logo文件存放的本地路径*/
            File logo = new File("G:/wechat-image/Logo/logo.jpg");
            /*二维码文件存放的本地路径*/
            String qrcodepath="G:/wechat-image/qrcode/doctor"+phone+".jpg";
            /*如果不存在则创建文件*/
            createFileUtil.createFile(qrcodepath);
            /*根据url和Logo生成二维码的二进制数组*/
            byte[] code = QrcodeUtils.createQrcode(url, logo);
            File file = new File(qrcodepath);
            OutputStream output = new FileOutputStream(file);
            BufferedOutputStream bufferedOutput = new BufferedOutputStream(output);
            bufferedOutput.write(code);
            /*七牛云上传本地文件*/
            qiniuService.fileupload("G:\\wechat-image\\qrcode\\doctor"+phone+".jpg","qrcode/doctor/doctor"+phone);
            jsonResult.setErrorcode("0");
            jsonResult.setMessage("generate qrcode and upload success");
            jsonResult.setData(null);
            this.logger.info("成功生成医生二维码并上传");
            return jsonResult;
        }
        catch (Exception e){
            jsonResult.setErrorcode("10010");
            jsonResult.setMessage("there is an exception while uploadding qrcode . exception:"+e.getMessage());
            jsonResult.setData(null);
            this.logger.error("在上传医生二维码时发生异常"+e.getMessage());
            return jsonResult;
        }
    }
    /*
    * 生成临时二维码
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
