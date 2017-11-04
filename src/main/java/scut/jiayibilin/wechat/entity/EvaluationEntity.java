package scut.jiayibilin.wechat.entity;



/**
 * @author WANGKK
 
 create table evaluation (
 wechat_id varchar(300) not null,
 phone varchar(20) not null,
 datetime timestamp not null default CURRENT_TIMESTAMP,
 content varchar(300) not null,
 profession varchar(5),
 attitude varchar(5),
 speed varchar(5),
 isread tinyint default 0,
 grade varchar(5),
anonymity tinyint default 0,
 primary key(wechat_id,phone,datetime),
 foreign key (wechat_id) references patient_info (wechat_id),
 foreign key (phone) references doctor_info (phone)
 )engine=INNODB default charset=utf8;
 
 
 
 */
public class EvaluationEntity {

	private String wechat_id;//患者
	private String phone;//医生
	private String datetime;//评价时间
	private String content;//评价内容
	private String profession;//专业程度
	private String attitude;//回复态度
	private String speed;//回复速度
	private int    isread;//是否阅读
	private String grade;//总评分
	private int    anonymity;

	
	public EvaluationEntity() {
		
	}

	public EvaluationEntity(String wechat_id, String phone, String datetime, String content, String profession, String attitude, String speed, int isread, String grade, int anonymity) {
		this.wechat_id = wechat_id;
		this.phone = phone;
		this.datetime = datetime;
		this.content = content;
		this.profession = profession;
		this.attitude = attitude;
		this.speed = speed;
		this.isread = isread;
		this.grade = grade;
		this.anonymity = anonymity;
	}

	public String getWechat_id() {
		return wechat_id;
	}
	public void setWechat_id(String wechat_id) {
		this.wechat_id = wechat_id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	public String getAttitude() {
		return attitude;
	}
	public void setAttitude(String attitude) {
		this.attitude = attitude;
	}
	public String getSpeed() { 
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public int getIsread() {
		return isread;
	}

	public void setIsread(int isread) {
		this.isread = isread;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public int getAnonymity() {
		return anonymity;
	}

	public void setAnonymity(int anonymity) {
		this.anonymity = anonymity;
	}
}
