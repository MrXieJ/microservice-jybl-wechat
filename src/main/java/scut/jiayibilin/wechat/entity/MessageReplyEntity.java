package scut.jiayibilin.wechat.entity;

/**
 create table message_reply(
 id int auto_increment,
 phone varchar(20),
 wechat_id varchar(300),
 name varchar(20) not null,
 head_pic varchar(300) not null,
 content varchar(300) not null,
 datetime timestamp not null default current_timestamp,
 reply_id int not null default 0,
 isread int not null default 0,
 primary key(id),
 foreign key(reply_id) references message_board(id)
 )engine=INNODB default charset=utf8;

 */

public class MessageReplyEntity {
    private int id;
    private String phone;
    private String wechat_id;
    private String name;
    private String head_pic;
    private String content;
    private String datetime;
    private int reply_id;
    private int isread;//是否已读

    public MessageReplyEntity() {
    }

    public MessageReplyEntity(int id, String phone, String wechat_id, String name, String head_pic, String content, String datetime, int reply_id) {
        this.id = id;
        this.phone = phone;
        this.wechat_id = wechat_id;
        this.name = name;
        this.head_pic = head_pic;
        this.content = content;
        this.datetime = datetime;
        this.reply_id = reply_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWechat_id() {
        return wechat_id;
    }

    public void setWechat_id(String wechat_id) {
        this.wechat_id = wechat_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public int getReply_id() {
        return reply_id;
    }

    public void setReply_id(int reply_id) {
        this.reply_id = reply_id;
    }

    public int getIsread() {
        return isread;
    }

    public void setIsread(int isread) {
        this.isread = isread;
    }
}
