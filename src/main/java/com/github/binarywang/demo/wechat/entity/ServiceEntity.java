package com.github.binarywang.demo.wechat.entity;


public class ServiceEntity {
	private int id;
	private String name;//名称
	private String price; //价格
	private int count;//次数
	private String duration;//期限
	private String content;//内容
	private String kind;//适用人群
	
	public ServiceEntity() {

	}
	public ServiceEntity(int id, String name, String price, int count, String duration, String content, String kind) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.count = count;
		this.duration = duration;
		this.content = content;
		this.kind = kind;
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
	

}
