package scut.jiayibilin.wechat.entity;

/**
 create table risk_report(
 wechat_id varchar(300) not null,
 count int  not null,
 time date not null,
 prob float not null,
 risk_level_id int not null default 1,
 primary key(wechat_id,time)
 )engine=INNODB default charset=utf8;
 */
public class RiskReportEntity {
    private String wechat_id;//患者微信号id
    private int count;//评估次数
    private String time;//评估时间
    private float prob;//患病概率
    private int risk_level_id;//危险层次id
    private String content;//详细内容
    private String risk_level;//危险层次



    public RiskReportEntity() {
    }

    public RiskReportEntity(String wechat_id, int count, String time, float prob, int risk_level_id, String content, String risk_level) {
        this.wechat_id = wechat_id;
        this.count = count;
        this.time = time;
        this.prob = prob;
        this.risk_level_id = risk_level_id;
        this.content = content;
        this.risk_level = risk_level;
    }

    public RiskReportEntity(String wechat_id, int count, String time, float prob, int risk_level_id) {
        this.wechat_id = wechat_id;
        this.count = count;
        this.time = time;
        this.prob = prob;
        this.risk_level_id = risk_level_id;
    }

    public String getWechat_id() {
        return wechat_id;
    }

    public void setWechat_id(String wechat_id) {
        this.wechat_id = wechat_id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getProb() {
        return prob;
    }

    public void setProb(float prob) {
        this.prob = prob;
    }

    public int getRisk_level_id() {
        return risk_level_id;
    }

    public void setRisk_level_id(int risk_level_id) {
        this.risk_level_id = risk_level_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRisk_level() {
        return risk_level;
    }

    public void setRisk_level(String risk_level) {
        this.risk_level = risk_level;
    }
}
