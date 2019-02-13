package com.activiti.base.entity.common;

import java.io.Serializable;

/**
 * 返回值
 **/
public class ResultMessage implements Serializable {
    private static final long serialVersionUID = 5842943701087198922L;
    private int code;
    private String msg;
    private Object data;

    public ResultMessage() { 
    }

    public ResultMessage(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResultMessage(int code, String msg) {
        this.code = code;
        this.msg = msg;

    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


}
