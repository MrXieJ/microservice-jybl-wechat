package com.github.binarywang.demo.wechat.controller.wechat;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpUserService;
import me.chanjar.weixin.mp.bean.WxMpUserQuery;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by jie on 2017/7/27.
 */
@RestController
@RequestMapping(value="/wechat/user")
public class WxUserController implements WxMpUserService {
    @Autowired
    WxMpService wxMpService;
    @Override
    /**
     * <pre>
     * 设置用户备注名
     * 详情请见: http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140838&token=&lang=zh_CN
     * http请求方式: POST（请使用https协议）
     * 接口地址：https://api.weixin.qq.com/cgi-bin/user/info/updateremark?access_token=ACCESS_TOKEN
     * </pre>
     *
     * @param openid 用户openid
     * @param remark 备注名
     */
    @PostMapping(value="/updateremark")
    public void userUpdateRemark(String openid, String remark) throws WxErrorException {
     wxMpService.getUserService().userUpdateRemark(openid,remark);
    }
    /**
     * <pre>
     * 获取用户基本信息（语言为默认的zh_CN 简体）
     * 详情请见: http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140839&token=&lang=zh_CN
     * http请求方式: GET
     * 接口地址：https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
     * </pre>
     *
     * @param openid 用户openid
     */
    @Override
    @GetMapping(value="/getbyid")
    public WxMpUser userInfo(String openid) throws WxErrorException {
        return wxMpService.getUserService().userInfo(openid);
    }
    /**
     * <pre>
     * 获取用户基本信息
     * 详情请见: http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140839&token=&lang=zh_CN
     * http请求方式: GET
     * 接口地址：https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
     * </pre>
     *
     * @param openid 用户openid
     * @param lang   语言，zh_CN 简体(默认)，zh_TW 繁体，en 英语
     */
    @Override
    @GetMapping(value="/getbyidandlan")
    public WxMpUser userInfo(String openid, String lang) throws WxErrorException {
        return wxMpService.getUserService().userInfo(openid, lang);
    }
    /**
     * <pre>
     * 获取用户基本信息列表
     * 开发者可通过该接口来批量获取用户基本信息。最多支持一次拉取100条。
     * 详情请见: http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140839&token=&lang=zh_CN
     * http请求方式: POST
     * 接口地址：https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=ACCESS_TOKEN
     * </pre>
     *
     * @param openids 用户openid列表
     */
    @Override
    @GetMapping(value="/getlist")
    public List<WxMpUser> userInfoList(List<String> openids) throws WxErrorException {
        return wxMpService.getUserService().userInfoList(openids);
    }
    /**
     * <pre>
     * 获取用户基本信息列表
     * 开发者可通过该接口来批量获取用户基本信息。最多支持一次拉取100条。
     * 详情请见: http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140839&token=&lang=zh_CN
     * http请求方式: POST
     * 接口地址：https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=ACCESS_TOKEN
     * </pre>
     *
     * @param userQuery 详细查询参数
     */
    @Override
    @GetMapping(value="/getlistbyquery")
    public List<WxMpUser> userInfoList(WxMpUserQuery userQuery) throws WxErrorException {
        return wxMpService.getUserService().userInfoList(userQuery);
    }
    /**
     * <pre>
     * 获取用户列表
     * 公众号可通过本接口来获取帐号的关注者列表，关注者列表由一串OpenID（加密后的微信号，每个用户对每个公众号的OpenID是唯一的）组成。一次拉取调用最多拉取10000个关注者的OpenID，可以通过多次拉取的方式来满足需求。
     * 详情请见: http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140840&token=&lang=zh_CN
     * http请求方式: GET（请使用https协议）
     * 接口地址：https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID
     * </pre>
     *
     * @param nextOpenid 可选，第一个拉取的OPENID，null为从头开始拉取
     */
    @Override
    @GetMapping(value="/getbynextid")
    public WxMpUserList userList(String nextOpenid) throws WxErrorException {
        return wxMpService.getUserService().userList(nextOpenid);
    }
}
