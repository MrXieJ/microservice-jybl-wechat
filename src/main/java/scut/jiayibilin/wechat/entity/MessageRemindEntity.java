package scut.jiayibilin.wechat.entity;
/**
 create table message_remind(
 id int not null auto_increment,
 title varchar(50) not null,
 target varchar(50) not null,
 remark varchar(50) not null,
 period varchar(50) not null,
 primary key (id)
)engine=INNODB default charset=utf8;
 */
public class MessageRemindEntity {
    private int id;
    private String title;
    private String target;
    private String remark;
    private String period;

    public MessageRemindEntity() {
    }

    public MessageRemindEntity(int id, String title, String target, String remark, String period) {
        this.id = id;
        this.title = title;
        this.target = target;
        this.remark = remark;
        this.period = period;
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

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}
