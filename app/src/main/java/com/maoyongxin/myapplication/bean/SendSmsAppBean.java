package com.maoyongxin.myapplication.bean;

public class SendSmsAppBean {


    /**
     * code : 200
     * msg : 短信发送成功!
     * smsCode : 31992
     */

    private int code;
    private String msg;
    private int smsCode;

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

    public int getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(int smsCode) {
        this.smsCode = smsCode;
    }
}
