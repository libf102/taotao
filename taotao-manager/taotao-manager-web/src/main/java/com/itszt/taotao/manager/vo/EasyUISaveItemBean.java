package com.itszt.taotao.manager.vo;

public class EasyUISaveItemBean {

    private String message;
    private Integer status;

    public static final int STATUS_OK=200;
    public static final int STATUS_ERROR=500;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
