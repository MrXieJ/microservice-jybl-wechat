package scut.jiayibilin.wechat.entity;

/*
create table patient_group_receiving(
id int not null auto_increment,
phone varchar(31) not null default '',
wechat_id varchar(255) not null default '',
content varchar(255) not null default '',
datetime timestamp default current_timestamp,
isread int not null default 0,
delete_status int not null default 0,
primary key(id)
)engine=INNODB default charset=utf8;
 */

public class PatientGroupReceivingEntity {
    private int id;
    private String phone;//医生电话
    private String wechat_id;//患者微信号
    private String content;//内容
    private String datetime;//时间
    private int isread;//是否已读
    private int delete_status;//是否删除

    private String head_pic;//医生头像
    private  String name;//医生姓名

    public PatientGroupReceivingEntity() {
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

    public int getIsread() {
        return isread;
    }

    public void setIsread(int isread) {
        this.isread = isread;
    }

    public int getDelete_status() {
        return delete_status;
    }

    public void setDelete_status(int delete_status) {
        this.delete_status = delete_status;
    }

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
