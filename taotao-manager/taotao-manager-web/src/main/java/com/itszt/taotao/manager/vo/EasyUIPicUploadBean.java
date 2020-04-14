package com.itszt.taotao.manager.vo;

import java.io.Serializable;

public class EasyUIPicUploadBean implements Serializable {

    private Integer error;   //上传成功 返回0    失败返回1
    private String  url;     //上传成功 返回  nginx展示图片 的 url
    private String message;     //提示信息


    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}