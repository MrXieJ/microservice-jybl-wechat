package scut.jiayibilin.wechat.entity;

/**
 * @author WANGKK
服务包建表
create table service(
id int not null auto_increment,
name varchar(100) not null,
price varchar(20) not null,
count int  not null,
duration varchar(20) not null,
content varchar(300),
kind varchar(20),
risk_level_id int default 1,
status int not null default 1,
time timestamp not null default current_timestamp on update current_timestamp,
primary key(id)
)engine=INNODB default charset=utf8;


 */
public class ServiceEntity {
	private int id;
	private String name;//名称
	private String price; //价格
	private int count;//次数
	private String duration;//期限
	private String content;//内容
	private String kind;//适用人群
	private int risk_level_id;//风险等级
	private String time;//更新时间
	private int status;


	public ServiceEntity() {

	}

	public ServiceEntity(int id, String name, String price, int count, String duration, String content, String kind, int risk_levelid, String time, int status) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.count = count;
		this.duration = duration;
		this.content = content;
		this.kind = kind;
		this.risk_level_id = risk_levelid;
		this.time = time;
		this.status = status;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getRisk_levelid() {
		return risk_level_id;
	}

	public void setRisk_levelid(int risk_levelid) {
		this.risk_level_id = risk_levelid;
	}
}
