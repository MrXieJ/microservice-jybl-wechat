package scut.jiayibilin.wechat.entity;
/**
 create table message_list(
 id int auto_increment,
 wechat_id varchar(300) not null,
 message_id int not null,
 isread int not null default 0,
 datetime timestamp not null default current_timestamp,
 primary key(id)
 )engine=INNODB default charset=utf8;

alter table message_list add index index_wechat_id (wechat_id);
 */
public class MessageListEntity {
    private int id;
    private String wechat_id;//患者微信号
    private int message_id;//消息id
    private int isread;//是否已读
    private String datetime;//消息时间
    private String title;//标题
    private String target;//目标
    private String remark;//备注
    private int period;//周期(天)

    public MessageListEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getWechat_id() {
        return wechat_id;
    }

    public void setWechat_id(String wechat_id) {
        this.wechat_id = wechat_id;
    }

    public int getMessage_id() {
        return message_id;
    }

    public void setMessage_id(int message_id) {
        this.message_id = message_id;
    }

    public int getIsread() {
        return isread;
    }

    public void setIsread(int isread) {
        this.isread = isread;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
