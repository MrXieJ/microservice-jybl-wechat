package scut.jiayibilin.wechat.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by jie on 2017/8/18.
 */
@Component
public class JsonResult implements Serializable{
    private  String errorcode;
    private  String message;
    private  Object data;

    public JsonResult() {
    }

    public JsonResult(String errorcode, String message, Object data) {
        this.errorcode = errorcode;
        this.message = message;
        this.data = data;
    }

    public String getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
