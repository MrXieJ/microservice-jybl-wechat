package scut.jiayibilin.wechat.entity;

/**
 create table message_board(
 id int auto_increment,
 wechat_id varchar(256) not null,
 phone varchar(32) not null,
 sender int not null,
 picture varchar(512),
 datetime timestamp not null default current_timestamp,
 content varchar(256) not null,
 isread int not null default 0,
 reply_id int not null default 0,
 primary key(id)
 )engine=INNODB default charset=utf8;
 */

public class MessageBoardEntity {
    private int id;//留言板ID
    private String wechat_id;//留言患者微信号
    private  String phone;//医生电话
    private int sender;//发送者 0代表患者 1代表医生
    private String picture;//图片
    private String datetime;//时间
    private String content;//描述
    private int isread;//是否已读
    private int reply_id;//回复的留言id

    private String name;
    private String sex;
    private int age;
    private String head_pic;

    public MessageBoardEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
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

    public int getIsread() {
        return isread;
    }

    public void setIsread(int isread) {
        this.isread = isread;
    }

    public int getReply_id() {
        return reply_id;
    }

    public void setReply_id(int reply_id) {
        this.reply_id = reply_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }
}
