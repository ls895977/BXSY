package com.maoyongxin.myapplication.ui.fgt.message.act.bean;

import java.util.List;

public class FindAndSearchBean {

    /**
     * code : 200
     * info : [{"userId":"10071","userName":"神秘大侠",
     * "longitude":"103.861412","latitude":"30.781709",
     * "createDate":null,"note":"彼信是什么地方",
     * "headImg":"b32ccf7467bf4157a7a3c46be41403c3.jpg",
     * "userPhone":"00000000000","sex":"2","vipNum":"0"}]
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
         * userId : 10071
         * userName : 神秘大侠
         * longitude : 103.861412
         * latitude : 30.781709
         * createDate : null
         * note : 彼信是什么地方
         * headImg : b32ccf7467bf4157a7a3c46be41403c3.jpg
         * userPhone : 00000000000
         * sex : 2
         * vipNum : 0
         */

        private String userId;
        private String userName;
        private String longitude;
        private String latitude;
        private Object createDate;
        private String note;
        private String headImg;
        private String userPhone;
        private String sex;
        private String vipNum;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public Object getCreateDate() {
            return createDate;
        }

        public void setCreateDate(Object createDate) {
            this.createDate = createDate;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getVipNum() {
            return vipNum;
        }

        public void setVipNum(String vipNum) {
            this.vipNum = vipNum;
        }
    }
}
