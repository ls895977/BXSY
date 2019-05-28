package com.maoyongxin.myapplication.ui.fgt.min.act.bean;

public class UpdateUserInfoBean {

    /**
     * code : 200
     * operation : true
     * msg : 更新成功！
     * info : {"userName":"long55","userPhone":"15902844859","longitude":"0.000000","latitude":"0.000000","note":"","headImg":"","background_img":"uploads/minhader/20190318/6a099c1d818ebbe4c705679604e7643f.jpg","sex":"2","brithday":"0000-00-00","permanent_city":"","position":"","vipNum":"0"}
     */

    private int code;
    private boolean operation;
    private String msg;
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * userName : long55
         * userPhone : 15902844859
         * longitude : 0.000000
         * latitude : 0.000000
         * note :
         * headImg :
         * background_img : uploads/minhader/20190318/6a099c1d818ebbe4c705679604e7643f.jpg
         * sex : 2
         * brithday : 0000-00-00
         * permanent_city :
         * position :
         * vipNum : 0
         */

        private String userName;
        private String userPhone;
        private String longitude;
        private String latitude;
        private String note;
        private String headImg;
        private String background_img;
        private String sex;
        private String brithday;
        private String permanent_city;
        private String position;
        private String vipNum;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
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

        public String getBackground_img() {
            return background_img;
        }

        public void setBackground_img(String background_img) {
            this.background_img = background_img;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getBrithday() {
            return brithday;
        }

        public void setBrithday(String brithday) {
            this.brithday = brithday;
        }

        public String getPermanent_city() {
            return permanent_city;
        }

        public void setPermanent_city(String permanent_city) {
            this.permanent_city = permanent_city;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getVipNum() {
            return vipNum;
        }

        public void setVipNum(String vipNum) {
            this.vipNum = vipNum;
        }
    }
}
