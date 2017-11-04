package scut.jiayibilin.wechat.controller.feign.mysql.qrcode;

import com.github.binarywang.utils.qrcode.QrcodeUtils;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import scut.jiayibilin.wechat.entity.JsonResult;
import scut.jiayibilin.wechat.entity.QRParamsException;
import scut.jiayibilin.wechat.service.MyWxMpService;
import scut.jiayibilin.wechat.service.WxUrlService;
import scut.jiayibilin.wechat.service.aliyun.AliyunStorageService;

import java.io.*;

/**
 * Created by jie on 2017/10/13.
 */
@RestController
public class AliyunOssController {
    @Autowired
    JsonResult jsonResult;
    @Autowired
    MyWxMpService myWxMpService;
    @Autowired
    WxUrlService wxUrlService;
    @Autowired
    AliyunStorageService aliyunStorageService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /*
    * 根据医生电话号码生成二维码
    * */
    @CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.GET,
            RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS,
            RequestMethod.HEAD, RequestMethod.PUT, RequestMethod.PATCH}, origins="*")
    @ResponseBody
    @RequestMapping(value="/aliyun/qrcode/create")
    public JsonResult qrCodeCreateLogo(@RequestParam("phone")String phone) throws WxErrorException, QRParamsException,IOException {
        try{
            String url = myWxMpService.oauth2buildAuthorizationUrl(wxUrlService.getDoctorQrcodeurl()+"/"+phone, WxConsts.OAUTH2_SCOPE_USER_INFO, "qrcode");
            /*项目中获取Logo文件*/
            File logo = ResourceUtils.getFile("classpath:static/image/logo.png");
            /*二维码文件存放的本地路径*/
            byte[] code = QrcodeUtils.createQrcode(url, logo);
            /*七牛云上传本地文件*/
            aliyunStorageService.fileupload("qrcode/doctor"+phone+".jpg",code);
            jsonResult.setErrorcode("0");
            jsonResult.setMessage("generate qrcode and upload success");
            jsonResult.setData(null);
            this.logger.info("成功生成医生二维码并上传");
            return jsonResult;
        }
        catch (Exception e){
            jsonResult.setErrorcode("1");
            jsonResult.setMessage("there is an exception while uploadding qrcode . exception:"+e.getMessage());
            jsonResult.setData(null);
            this.logger.error("在上传医生二维码时发生异常"+e.getMessage());
            return jsonResult;
        }
    }
    /*
    * 获取医生二维啊的阿里云链接
    * */
    @CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.GET,
            RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS,
            RequestMethod.HEAD, RequestMethod.PUT, RequestMethod.PATCH}, origins="*")
    @RequestMapping(value="/aliyun/qrcode/get",method = RequestMethod.GET)
    @ResponseBody
    public JsonResult getdoctorqrcodeurl(@RequestParam("phone") String phone){
        try {
            if (aliyunStorageService.existfile("qrcode/doctor" + phone+".jpg")) {
                this.logger.info("接收到获取医生二维码请求");
                jsonResult.setErrorcode("0");
                jsonResult.setMessage("get aliyun qrcode success");
                jsonResult.setData(wxUrlService.getImagebaseurl() + "/qrcode/doctor" + phone+".jpg");

                return jsonResult;
            } else {
                jsonResult.setErrorcode("10011");
                jsonResult.setMessage("aliyun qrcode not exist");
                jsonResult.setData(null);
                return jsonResult;
            }
        }
        catch(Exception e){
            jsonResult.setErrorcode("10012");
            jsonResult.setMessage("get qrcode error caused by aliyun exception . exception:"+e.getMessage());
            jsonResult.setData(null);
            this.logger.error(e.getMessage());
            return jsonResult;
        }
    }
}
