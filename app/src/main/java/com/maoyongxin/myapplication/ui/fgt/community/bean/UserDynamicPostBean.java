package com.maoyongxin.myapplication.ui.fgt.community.bean;

import java.util.List;

public class UserDynamicPostBean {

    /**
     * code : 200
     * info : [{"id":"67","dynamicId":"83","parentId":"0","createTime":"2018-12-21 09:35:11","postContent":"5biF5rCU55qE5qih5Z6L77yB\n","userId":"10073","praiseNum":0,"treadNum":0,"commentNum":"0","parentUserId":"10076","is_praise_tread":"0","userName":"long","userImg":"http://118.24.2.164:8089/logincontroller/getHeadImg/d4649b99a9184987a57905ec19d37b83.jpg","parentUserName":"IEW","parentUserImg":"http://118.24.2.164:8089/logincontroller/getHeadImg/4a34713093f14be99f6341acc5e1f293.JPG","times":"2018年12月21日"},{"id":"52","dynamicId":"83","parentId":"83","createTime":"2018-12-18 14:46:56","postContent":"5pyJ5py65Lya5Y+v5Lul5ZCI5L2c5LiL8J+YrA==\n","userId":"10076","praiseNum":0,"treadNum":0,"commentNum":"0","parentUserId":"10070","is_praise_tread":"0","userName":"IEW","userImg":"http://118.24.2.164:8089/logincontroller/getHeadImg/4a34713093f14be99f6341acc5e1f293.JPG","parentUserName":"余小呆子","parentUserImg":"http://118.24.2.164:8089/logincontroller/getHeadImg/bea77ec03126424684de45919b669469.jpg","times":"2018年12月18日"},{"id":"51","dynamicId":"83","parentId":"83","createTime":"2018-12-18 14:46:39","postContent":"6L+Y6ZyA6KaB5p2o5oC75o+Q5L6b5ZCO5aSE55CG77yM5omN5a6M576O\n","userId":"10076","praiseNum":0,"treadNum":0,"commentNum":"0","parentUserId":"10075","is_praise_tread":"0","userName":"IEW","userImg":"http://118.24.2.164:8089/logincontroller/getHeadImg/4a34713093f14be99f6341acc5e1f293.JPG","parentUserName":"博士杨","parentUserImg":"http://118.24.2.164:8089/logincontroller/getHeadImg/0cc116f8eb2b4fc39964d921868ef92e.jpg","times":"2018年12月18日"},{"id":"46","dynamicId":"83","parentId":"0","createTime":"2018-12-18 14:44:11","postContent":"5Y6J5a6z\n","userId":"10080","praiseNum":0,"treadNum":0,"commentNum":"0","parentUserId":"10076","is_praise_tread":"0","userName":"min","userImg":"http://118.24.2.164:8089/logincontroller/getHeadImg/5bae82727cc646cdac382a4a32ad5e0a.jpg","parentUserName":"IEW","parentUserImg":"http://118.24.2.164:8089/logincontroller/getHeadImg/4a34713093f14be99f6341acc5e1f293.JPG","times":"2018年12月18日"},{"id":"43","dynamicId":"83","parentId":"0","createTime":"2018-12-18 14:43:38","postContent":"M0TmiZPljbDlsLHmmK/mlrnkvr/pq5jmlYjnjofjgII=\n","userId":"10075","praiseNum":0,"treadNum":0,"commentNum":"0","parentUserId":"10076","is_praise_tread":"0","userName":"博士杨","userImg":"http://118.24.2.164:8089/logincontroller/getHeadImg/0cc116f8eb2b4fc39964d921868ef92e.jpg","parentUserName":"IEW","parentUserImg":"http://118.24.2.164:8089/logincontroller/getHeadImg/4a34713093f14be99f6341acc5e1f293.JPG","times":"2018年12月18日"},{"id":"38","dynamicId":"83","parentId":"0","createTime":"2018-12-18 14:41:01","postContent":"55yf5qOS\n","userId":"10070","praiseNum":0,"treadNum":0,"commentNum":"0","parentUserId":"10076","is_praise_tread":"0","userName":"余小呆子","userImg":"http://118.24.2.164:8089/logincontroller/getHeadImg/bea77ec03126424684de45919b669469.jpg","parentUserName":"IEW","parentUserImg":"http://118.24.2.164:8089/logincontroller/getHeadImg/4a34713093f14be99f6341acc5e1f293.JPG","times":"2018年12月18日"},{"id":"36","dynamicId":"83","parentId":"0","createTime":"2018-12-18 14:40:55","postContent":"5pWI5p6c5LiN6ZSZ\n","userId":"10070","praiseNum":0,"treadNum":0,"commentNum":"0","parentUserId":"10076","is_praise_tread":"0","userName":"余小呆子","userImg":"http://118.24.2.164:8089/logincontroller/getHeadImg/bea77ec03126424684de45919b669469.jpg","parentUserName":"IEW","parentUserImg":"http://118.24.2.164:8089/logincontroller/getHeadImg/4a34713093f14be99f6341acc5e1f293.JPG","times":"2018年12月18日"}]
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
         * id : 67
         * dynamicId : 83
         * parentId : 0
         * createTime : 2018-12-21 09:35:11
         * postContent : 5biF5rCU55qE5qih5Z6L77yB

         * userId : 10073
         * praiseNum : 0
         * treadNum : 0
         * commentNum : 0
         * parentUserId : 10076
         * is_praise_tread : 0
         * userName : long
         * userImg : http://118.24.2.164:8089/logincontroller/getHeadImg/d4649b99a9184987a57905ec19d37b83.jpg
         * parentUserName : IEW
         * parentUserImg : http://118.24.2.164:8089/logincontroller/getHeadImg/4a34713093f14be99f6341acc5e1f293.JPG
         * times : 2018年12月21日
         */

        private String id;
        private String dynamicId;
        private String parentId;
        private String createTime;
        private String postContent;
        private String userId;
        private int praiseNum;
        private int treadNum;
        private String commentNum;
        private String parentUserId;
        private String is_praise_tread;
        private String userName;
        private String userImg;
        private String parentUserName;
        private String parentUserImg;
        private String times;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDynamicId() {
            return dynamicId;
        }

        public void setDynamicId(String dynamicId) {
            this.dynamicId = dynamicId;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getPostContent() {
            return postContent;
        }

        public void setPostContent(String postContent) {
            this.postContent = postContent;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public int getPraiseNum() {
            return praiseNum;
        }

        public void setPraiseNum(int praiseNum) {
            this.praiseNum = praiseNum;
        }

        public int getTreadNum() {
            return treadNum;
        }

        public void setTreadNum(int treadNum) {
            this.treadNum = treadNum;
        }

        public String getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(String commentNum) {
            this.commentNum = commentNum;
        }

        public String getParentUserId() {
            return parentUserId;
        }

        public void setParentUserId(String parentUserId) {
            this.parentUserId = parentUserId;
        }

        public String getIs_praise_tread() {
            return is_praise_tread;
        }

        public void setIs_praise_tread(String is_praise_tread) {
            this.is_praise_tread = is_praise_tread;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserImg() {
            return userImg;
        }

        public void setUserImg(String userImg) {
            this.userImg = userImg;
        }

        public String getParentUserName() {
            return parentUserName;
        }

        public void setParentUserName(String parentUserName) {
            this.parentUserName = parentUserName;
        }

        public String getParentUserImg() {
            return parentUserImg;
        }

        public void setParentUserImg(String parentUserImg) {
            this.parentUserImg = parentUserImg;
        }

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }
    }
}
