package com.github.binarywang.demo.wechat.controller.qrcode;

import com.github.binarywang.demo.wechat.entity.JsonResult;
import com.github.binarywang.demo.wechat.service.WxUrlService;
import com.github.binarywang.demo.wechat.service.qiniu.QiniuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by jie on 2017/8/17.
 */
@RestController
public class QrcodeGetController {
    @Autowired
    private WxUrlService wxUrlService;
    @Autowired
    private QiniuService qiniuService;
    @Autowired
    private JsonResult jsonResult;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /*
    * 2.2根据医生电话号码获取医生二维码的七牛云链接
    * 错误返回码10011,10012
    * */
    @CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.GET,
            RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS,
            RequestMethod.HEAD, RequestMethod.PUT, RequestMethod.PATCH}, origins="*")
    @RequestMapping(value="/qrcode/doctorget",method = RequestMethod.GET)
    public JsonResult getdoctorqrcodeurl(@RequestParam("phone") String phone){
        try {
            if (qiniuService.existfile("qrcode/doctor/doctor" + phone)) {
                this.logger.info("接收到获取医生二维码请求");
                jsonResult.setErrorcode("0");
                jsonResult.setMessage("get qrcode success");
                jsonResult.setData(wxUrlService.getImagebaseurl() + "/qrcode/doctor/doctor" + phone);

                return jsonResult;
            } else {
                jsonResult.setErrorcode("10011");
                jsonResult.setMessage("qrcode not exist");
                jsonResult.setData(null);
                return jsonResult;
            }
        }
        catch(Exception e){
            jsonResult.setErrorcode("10012");
            jsonResult.setMessage("get qrcode error caused by qiniu exception . exception:"+e.getMessage());
            jsonResult.setData(null);
            this.logger.error(e.getMessage());
            return jsonResult;
        }
    }
}
