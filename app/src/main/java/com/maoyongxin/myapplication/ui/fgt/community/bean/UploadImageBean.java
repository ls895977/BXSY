package com.maoyongxin.myapplication.ui.fgt.community.bean;

import java.util.List;

public class UploadImageBean {

    /**
     * code : true
     * msg : 上传成功！
     * url : ["uploads/aa/20190128/7ed99ad8d5f0f2cacb67454158730d36.jpeg"]
     */

    private boolean code;
    private String msg;
    private List<String> url;

    public boolean isCode() {
        return code;
    }

    public void setCode(boolean code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<String> getUrl() {
        return url;
    }

    public void setUrl(List<String> url) {
        this.url = url;
    }
}
