package scut.jiayibilin.wechat.entity;
/*
create table risk_level (
id int auto_increment,
risk_level varchar(32) not null,
content varchar(256) not null,
primary key(id)
 )engine=INNODB default charset=utf8;
 */
public class RiskLevelEntity {
    private int id;
    private String level;
    private String conclusion;

    public RiskLevelEntity() {
    }

    public RiskLevelEntity(int id, String level, String conclusion) {
        this.id = id;
        this.level = level;
        this.conclusion = conclusion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }
}
