package com.maoyongxin.myapplication.ui.fgt.community.bean;

public class UserFriendsFollowApiBean {

    /**
     * code : 200
     * operation : true
     * msg : 关注成功！
     */

    private int code;
    private boolean operation;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isOperation() {
        return operation;
    }

    public void setOperation(boolean operation) {
        this.operation = operation;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
