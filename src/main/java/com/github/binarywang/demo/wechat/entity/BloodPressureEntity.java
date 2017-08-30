package com.github.binarywang.demo.wechat.entity;

/**
 create table blood_pressure(
 wechat_id varchar(300) not null,
 date date not null,
 time varchar(10) not null,
 systolic_pressure int not null,
 diastolic_pressure int not null,
 rhr int not null,
 primary key(wechat_id)
 )engine=INNODB default charset=utf8;
 */

public class BloodPressureEntity {
    private String wechat_id;//患者微信号id
    private String date;//日期
    private String time;//时间
    private int systolic_pressure;//收缩压
    private int diastolic_pressure;//舒张压
    private int rhr;//静息心率

    public BloodPressureEntity() {
    }

    public BloodPressureEntity(String wechat_id, String date, String time, int systolic_pressure, int diastolic_pressure, int rhr) {
        this.wechat_id = wechat_id;
        this.date = date;
        this.time = time;
        this.systolic_pressure = systolic_pressure;
        this.diastolic_pressure = diastolic_pressure;
        this.rhr = rhr;
    }

    public String getWechat_id() {
        return wechat_id;
    }

    public void setWechat_id(String wechat_id) {
        this.wechat_id = wechat_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getSystolic_pressure() {
        return systolic_pressure;
    }

    public void setSystolic_pressure(int systolic_pressure) {
        this.systolic_pressure = systolic_pressure;
    }

    public int getDiastolic_pressure() {
        return diastolic_pressure;
    }

    public void setDiastolic_pressure(int diastolic_pressure) {
        this.diastolic_pressure = diastolic_pressure;
    }

    public int getRhr() {
        return rhr;
    }

    public void setRhr(int rhr) {
        this.rhr = rhr;
    }
}
