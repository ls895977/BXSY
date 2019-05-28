package com.maoyongxin.myapplication.ui.fgt.community.bean;

public class UploadVideoBean {

    /**
     * code : true
     * msg : 上传成功！
     * url : uploads/dynamicVideo/20190128/81eabc53b5f934f0fbb21d2ba29dbccd.mp4
     */

    private boolean code;
    private String msg;
    private String url;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
