package com.maoyongxin.myapplication.bean;

public class LoginBean {

    /**
     * code : 200
     * operation : true
     * msg : 登录成功！
     * userInfo : {"userId":"10073","userName":"long55","longitude":"103.857535","latitude":"30.776511","token":"nxS22DZrWP7WcK+9HD0JISljKYe6/0MOv4IScfpnDWQzVvJoe6lADv9CDncJ87NqEbIV4RpL7R8XR8N1D7ESXA==","createDate":"2018-09-23","userPhone":"15902844859","vipNum":"0","note":"","headImg":"d4649b99a9184987a57905ec19d37b83.jpg","background_img":"","sex":"2","brithday":"1990-12-31","permanent_city":"","position":""}
     */

    private int code;
    private boolean operation;
    private String msg;
    private UserInfoBean userInfo;

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

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }

    public static class UserInfoBean {
        /**
         * userId : 10073
         * userName : long55
         * longitude : 103.857535
         * latitude : 30.776511
         * token : nxS22DZrWP7WcK+9HD0JISljKYe6/0MOv4IScfpnDWQzVvJoe6lADv9CDncJ87NqEbIV4RpL7R8XR8N1D7ESXA==
         * createDate : 2018-09-23
         * userPhone : 15902844859
         * vipNum : 0
         * note :
         * headImg : d4649b99a9184987a57905ec19d37b83.jpg
         * background_img :
         * sex : 2
         * brithday : 1990-12-31
         * permanent_city :
         * position :
         */

        private String userId;
        private String userName;
        private String longitude;
        private String latitude;
        private String token;
        private String createDate;
        private String userPhone;
        private String vipNum;
        private String note;
        private String headImg;
        private String background_img;
        private String sex;
        private String brithday;
        private String permanent_city;
        private String position;

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

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
        }

        public String getVipNum() {
            return vipNum;
        }

        public void setVipNum(String vipNum) {
            this.vipNum = vipNum;
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
    }
}
