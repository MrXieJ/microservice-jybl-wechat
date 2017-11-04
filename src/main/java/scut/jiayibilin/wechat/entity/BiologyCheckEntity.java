package scut.jiayibilin.wechat.entity;

public class BiologyCheckEntity {

    private String wechat_id;//患者微信id

//总胆固醇
    private String tch;
    private String tch_time;//总胆固醇时间
    private String fbg;//空腹血糖
    private String fbg_time;//空腹血糖时间
    private String tg;//甘三油脂
    private String tg_time;//甘三油脂时间
    private String hdl;//高密度脂蛋白
    private String hdl_time;//高密度脂蛋白时间
    private String ldl;//低密度脂蛋白
    private String ldl_time;//低密度脂蛋白时间

    public BiologyCheckEntity() {
    }

    public BiologyCheckEntity(String wechat_id, String tch, String tch_time, String fbg, String fbg_time, String tg, String tg_time, String hdl, String hdl_time, String ldl, String ldl_time) {
        this.wechat_id = wechat_id;
        this.tch = tch;
        this.tch_time = tch_time;
        this.fbg = fbg;
        this.fbg_time = fbg_time;
        this.tg = tg;
        this.tg_time = tg_time;
        this.hdl = hdl;
        this.hdl_time = hdl_time;
        this.ldl = ldl;
        this.ldl_time = ldl_time;
    }

    public String getWechat_id() {
        return wechat_id;
    }

    public void setWechat_id(String wechat_id) {
        this.wechat_id = wechat_id;
    }

    public String getTch() {
        return tch;
    }

    public void setTch(String tch) {
        this.tch = tch;
    }

    public String getTch_time() {
        return tch_time;
    }

    public void setTch_time(String tch_time) {
        this.tch_time = tch_time;
    }

    public String getFbg() {
        return fbg;
    }

    public void setFbg(String fbg) {
        this.fbg = fbg;
    }

    public String getFbg_time() {
        return fbg_time;
    }

    public void setFbg_time(String fbg_time) {
        this.fbg_time = fbg_time;
    }

    public String getTg() {
        return tg;
    }

    public void setTg(String tg) {
        this.tg = tg;
    }

    public String getTg_time() {
        return tg_time;
    }

    public void setTg_time(String tg_time) {
        this.tg_time = tg_time;
    }

    public String getHdl() {
        return hdl;
    }

    public void setHdl(String hdl) {
        this.hdl = hdl;
    }

    public String getHdl_time() {
        return hdl_time;
    }

    public void setHdl_time(String hdl_time) {
        this.hdl_time = hdl_time;
    }

    public String getLdl() {
        return ldl;
    }

    public void setLdl(String ldl) {
        this.ldl = ldl;
    }

    public String getLdl_time() {
        return ldl_time;
    }

    public void setLdl_time(String ldl_time) {
        this.ldl_time = ldl_time;
    }
}
