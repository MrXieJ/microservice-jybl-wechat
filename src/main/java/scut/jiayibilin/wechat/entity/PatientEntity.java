package scut.jiayibilin.wechat.entity;



/**
create table patient_info(
wechat_id varchar(300) not null,
name varchar(20),
id_card char(18),
sex char(3),
age int,
phone char(20),
address varchar(100),
detailed_address varchar(100),
head_pic varchar(300),
 kind varchar(20),
 prob float,
 datetime timestamp default current_timestamp,
primary key (wechat_id)
)engine=INNODB default charset=utf8;
*/
public class PatientEntity implements java.io.Serializable{


	private static final long serialVersionUID = 1572885392206876566L;

	private String name;//姓名

    private String id_card;//身份证号码

    private String sex;//性别

    private int age;//年龄

    private String phone;//电话

    private String address;//地址

    private String detailed_address;//详细地址

    private String wechat_id;//微信id

    private String head_pic;//头像

    private String kind;//人群类型

    private float  prob;//风险概率

    private String datetime;//关注时间

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

 public PatientEntity() {
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

 public String getKind() {
  return kind;
 }

 public void setKind(String kind) {
  this.kind = kind;
 }

 public float getProb() {
  return prob;
 }

 public void setProb(float prob) {
  this.prob = prob;
 }

 public String getDatetime() {
  return datetime;
 }

 public void setDatetime(String datetime) {
  this.datetime = datetime;
 }
}
