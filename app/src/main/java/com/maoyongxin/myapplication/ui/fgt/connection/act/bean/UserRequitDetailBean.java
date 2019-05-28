package com.maoyongxin.myapplication.ui.fgt.connection.act.bean;

import com.maoyongxin.myapplication.common.AppConfig;
import com.maoyongxin.myapplication.common.ComantUtils;

public class UserRequitDetailBean {

    /**
     * code : 200
     * operation : true
     * info : {"id":"2","uid":"10283","title":"菊花茶","content":"加vV","img":null,"classify_id":"5","indate":"2019-04-25 00:00:00","area":"北京市北京市东城区","detailed_area":"","area_code":null,"phone":"搬不搬","qq_account":"12654","create_time":"2019-04-27 14:56:50","update_time":"2019-04-27 14:56:50","is_show":"1","userName":"非你莫属","headImg":"http://qzapp.qlogo.cn/qzapp/101539710/AA20EFB8806C24BD3470158DEE0DDA90/100","classifyName":"企业招聘","createTime":"04月27日","notice_type":0,"isEnshrine":false}
     */

    private int code;
    private boolean operation;
    private InfoBean info;

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

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * id : 2
         * uid : 10283
         * title : 菊花茶
         * content : 加vV
         * img : null
         * classify_id : 5
         * indate : 2019-04-25 00:00:00
         * area : 北京市北京市东城区
         * detailed_area :
         * area_code : null
         * phone : 搬不搬
         * qq_account : 12654
         * create_time : 2019-04-27 14:56:50
         * update_time : 2019-04-27 14:56:50
         * is_show : 1
         * userName : 非你莫属
         * headImg : http://qzapp.qlogo.cn/qzapp/101539710/AA20EFB8806C24BD3470158DEE0DDA90/100
         * classifyName : 企业招聘
         * createTime : 04月27日
         * notice_type : 0
         * isEnshrine : false
         */

        private String id;
        private String uid;
        private String title;
        private String content;
        private Object img;
        private String classify_id;
        private String indate;
        private String area;
        private String detailed_area;
        private Object area_code;
        private String phone;
        private String qq_account;
        private String create_time;
        private String update_time;
        private String is_show;
        private String userName;
        private String headImg;
        private String classifyName;
        private String createTime;
        private int notice_type;
        private boolean isEnshrine;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Object getImg() {
            return img;
        }

        public void setImg(Object img) {
            this.img = img;
        }

        public String getClassify_id() {
            return classify_id;
        }

        public void setClassify_id(String classify_id) {
            this.classify_id = classify_id;
        }

        public String getIndate() {
            return indate;
        }

        public void setIndate(String indate) {
            this.indate = indate;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getDetailed_area() {
            return detailed_area;
        }

        public void setDetailed_area(String detailed_area) {
            this.detailed_area = detailed_area;
        }

        public Object getArea_code() {
            return area_code;
        }

        public void setArea_code(Object area_code) {
            this.area_code = area_code;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getQq_account() {
            return qq_account;
        }

        public void setQq_account(String qq_account) {
            this.qq_account = qq_account;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getIs_show() {
            return is_show;
        }

        public void setIs_show(String is_show) {
            this.is_show = is_show;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getHeadImg() {

            if (headImg.startsWith("uploads/minhader")) {
                headImg = ComantUtils.MyUrlHot1 + headImg;
            }
            else if (headImg.startsWith("http")) {

            }
            else
            {
                headImg =AppConfig.sRootUrl+"/logincontroller/getHeadImg/"+ headImg;
            }
            return headImg;



        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public String getClassifyName() {
            return classifyName;
        }

        public void setClassifyName(String classifyName) {
            this.classifyName = classifyName;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getNotice_type() {
            return notice_type;
        }

        public void setNotice_type(int notice_type) {
            this.notice_type = notice_type;
        }

        public boolean isIsEnshrine() {
            return isEnshrine;
        }

        public void setIsEnshrine(boolean isEnshrine) {
            this.isEnshrine = isEnshrine;
        }
    }
}
