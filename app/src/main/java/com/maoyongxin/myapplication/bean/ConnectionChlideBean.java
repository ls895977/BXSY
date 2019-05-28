package com.maoyongxin.myapplication.bean;

import java.util.List;

public class ConnectionChlideBean {


    /**
     * code : 200
     * info : [{"id":"5","uid":"10267","title":"请帮我设计两张海报","content":"我的需求是：app的宣传海报，有人能帮我做吗 谢谢","img":null,"classify_id":"7","indate":"2019-05-08 00:00:00","area":"成都市","detailed_area":"http://bisonchat.com/uploads/minhader/20190419/2375ee2ff95747426de4484d6842507a.jpeg","area_code":null,"phone":"18180802828","qq_account":"800","create_time":"1556799731","update_time":"1556799731","is_show":"1","userName":"来一瓶92年的哇哈哈","headImg":"uploads/minhader/20190419/2375ee2ff95747426de4484d6842507a.jpeg","classifyName":"企业需求","createTime":"1970年01月01日","notice_type":0,"read_num":"6"}]
     */

    private int code;
    private List<InfoBean> info;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * id : 5
         * uid : 10267
         * title : 请帮我设计两张海报
         * content : 我的需求是：app的宣传海报，有人能帮我做吗 谢谢
         * img : null
         * classify_id : 7
         * indate : 2019-05-08 00:00:00
         * area : 成都市
         * detailed_area : http://bisonchat.com/uploads/minhader/20190419/2375ee2ff95747426de4484d6842507a.jpeg
         * area_code : null
         * phone : 18180802828
         * qq_account : 800
         * create_time : 1556799731
         * update_time : 1556799731
         * is_show : 1
         * userName : 来一瓶92年的哇哈哈
         * headImg : uploads/minhader/20190419/2375ee2ff95747426de4484d6842507a.jpeg
         * classifyName : 企业需求
         * createTime : 1970年01月01日
         * notice_type : 0
         * read_num : 6
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
        private String read_num;

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

        public String getRead_num() {
            return read_num;
        }

        public void setRead_num(String read_num) {
            this.read_num = read_num;
        }
    }
}
