package com.maoyongxin.myapplication.ui.fgt.message.act.bean;

import com.maoyongxin.myapplication.common.ComantUtils;

import java.util.List;

public class UserDynamicListBean {

    /**
     * code : 200
     * info : {"userId":"10069","userName":"彼信客服","longitude":"104.047127","latitude":"30.487548","createDate":"2018-01-11","note":"个人简介：","headImg":"6ce22be318c746ea8f86f421633683bc.jpg","background_img":"","userPhone":"13595062746","sex":"1","brithday":"","vipNum":"0","isAttention":true,"userInterest":[{"interestId":"1271","interestName":"3d"},{"interestId":"1273","interestName":"建模"}]}
     */

    private int code;
    private InfoBean info;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * userId : 10069
         * userName : 彼信客服
         * longitude : 104.047127
         * latitude : 30.487548
         * createDate : 2018-01-11
         * note : 个人简介：
         * headImg : 6ce22be318c746ea8f86f421633683bc.jpg
         * background_img :
         * userPhone : 13595062746
         * sex : 1
         * brithday :
         * vipNum : 0
         * isAttention : true
         * userInterest : [{"interestId":"1271","interestName":"3d"},{"interestId":"1273","interestName":"建模"}]
         */

        private String userId;
        private String userName;
        private String longitude;
        private String latitude;
        private String createDate;
        private String note;
        private String headImg;
        private String background_img;
        private String userPhone;
        private String sex;
        private String brithday;
        private String vipNum;
        private boolean isAttention;
        private List<UserInterestBean> userInterest;

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

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getHeadImg() {
            try
            {   if (headImg != null) {
                if (headImg.startsWith("uploads")) {
                    headImg = ComantUtils.MyUrlHot1 + headImg;
                }
                else if (headImg.startsWith("http"))
                {
                }
                else {
                    headImg = ComantUtils.MyUrlImageHader + headImg;
                }
            }}
            catch (Exception e)
            {
                e.printStackTrace();
            }

            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public String getBackground_img() {
            try {
                if (background_img.startsWith("http"))
                {

                }
                else if(background_img.startsWith("up"))
                {
                    background_img=  ComantUtils.MyUrlHot1 + background_img;
                }

                else {
                    headImg = ComantUtils.MyUrlImageHader + headImg;
                }

            }
            catch (Exception e)
            {
                return "";
            }

            return background_img;
        }

        public void setBackground_img(String background_img) {
            this.background_img = background_img;
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

        public String getBrithday() {
            return brithday;
        }

        public void setBrithday(String brithday) {
            this.brithday = brithday;
        }

        public String getVipNum() {
            return vipNum;
        }

        public void setVipNum(String vipNum) {
            this.vipNum = vipNum;
        }

        public boolean isIsAttention() {
            return isAttention;
        }

        public void setIsAttention(boolean isAttention) {
            this.isAttention = isAttention;
        }

        public List<UserInterestBean> getUserInterest() {
            return userInterest;
        }

        public void setUserInterest(List<UserInterestBean> userInterest) {
            this.userInterest = userInterest;
        }

        public static class UserInterestBean {
            /**
             * interestId : 1271
             * interestName : 3d
             */

            private String interestId;
            private String interestName;

            public String getInterestId() {
                return interestId;
            }

            public void setInterestId(String interestId) {
                this.interestId = interestId;
            }

            public String getInterestName() {
                return interestName;
            }

            public void setInterestName(String interestName) {
                this.interestName = interestName;
            }
        }
    }
}
