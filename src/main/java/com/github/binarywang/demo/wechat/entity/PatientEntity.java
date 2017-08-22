package com.github.binarywang.demo.wechat.entity;


import java.io.Serializable;

/**
 * Created by jie on 2017/7/31.
 */
public class PatientEntity implements Serializable{

    private String name;

    private String id_card;

    private String sex;

    private int age;

    private String phone;

    private String address;

    private String detailed_address;

    private String wechat_id;

    private String head_pic;

 public PatientEntity() {
 }

 public PatientEntity(String name, String id_card, String sex, int age, String phone, String address, String detailed_address, String wechat_id, String head_pic) {
  this.name = name;
  this.id_card = id_card;
  this.sex = sex;
  this.age = age;
  this.phone = phone;
  this.address = address;
  this.detailed_address = detailed_address;
  this.wechat_id = wechat_id;
  this.head_pic = head_pic;
 }


 public String getName() {
  return name;
 }

 public void setName(String name) {
  this.name = name;
 }

 public String getId_card() {
  return id_card;
 }

 public void setId_card(String id_card) {
  this.id_card = id_card;
 }

 public String getSex() {
  return sex;
 }

 public void setSex(String sex) {
  this.sex = sex;
 }

 public int getAge() {
  return age;
 }

 public void setAge(int age) {
  this.age = age;
 }

 public String getPhone() {
  return phone;
 }

 public void setPhone(String phone) {
  this.phone = phone;
 }

 public String getAddress() {
  return address;
 }

 public void setAddress(String address) {
  this.address = address;
 }

 public String getDetailed_address() {
  return detailed_address;
 }

 public void setDetailed_address(String detailed_address) {
  this.detailed_address = detailed_address;
 }

 public String getWechat_id() {
  return wechat_id;
 }

 public void setWechat_id(String wechat_id) {
  this.wechat_id = wechat_id;
 }

 public String getHead_pic() {
  return head_pic;
 }

 public void setHead_pic(String head_pic) {
  this.head_pic = head_pic;
 }
}
