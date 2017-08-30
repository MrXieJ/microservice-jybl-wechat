package com.github.binarywang.demo.wechat.entity;

/**
 create table biology_info(
 wechat_id varchar(300) not null,
 tch varchar(50) not null,
fbg varchar(50) not null,
tg varchar(50) not null,
hdl varchar(50) not null,
ldl varchar(50) not null,
 primary key(wechat_id)
 )engine=INNODB default charset=utf8;
*/


public class BiologyCheckEntity {

    private String wechat_id;//患者微信id
    private String tch;//总胆固醇
    private String fbg;//空腹血糖
    private String tg;//甘三油脂
    private String hdl;//高密度脂蛋白
    private String ldl;//低密度脂蛋白

    public BiologyCheckEntity() {
    }

    public BiologyCheckEntity(String wechat_id, String tch, String fbg, String tg, String hdl, String ldl) {
        this.wechat_id = wechat_id;
        this.tch = tch;
        this.fbg = fbg;
        this.tg = tg;
        this.hdl = hdl;
        this.ldl = ldl;
    }

    public String getWechat_id() {
        return wechat_id;
    }

    public void setWechat_id(String wechat_id) {
        this.wechat_id = wechat_id;
    }

    public String getTch() {
        return tch;
    }

    public void setTch(String tch) {
        this.tch = tch;
    }

    public String getFbg() {
        return fbg;
    }

    public void setFbg(String fbg) {
        this.fbg = fbg;
    }

    public String getTg() {
        return tg;
    }

    public void setTg(String tg) {
        this.tg = tg;
    }

    public String getHdl() {
        return hdl;
    }

    public void setHdl(String hdl) {
        this.hdl = hdl;
    }

    public String getLdl() {
        return ldl;
    }

    public void setLdl(String ldl) {
        this.ldl = ldl;
    }
}
