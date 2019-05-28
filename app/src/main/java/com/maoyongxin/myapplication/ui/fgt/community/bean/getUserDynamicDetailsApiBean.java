package com.maoyongxin.myapplication.ui.fgt.community.bean;

import com.bumptech.glide.Glide;
import com.maoyongxin.myapplication.common.ComantUtils;

import java.util.List;

public class getUserDynamicDetailsApiBean {

    /**
     * code : 200
     * info : {"dynamicId":"107","createTime":"01月18日","dynamicContent":"M0TmiZPljbDljp/lnovvvIzmibnph4/lrprliLbljJbpppbppbDph5HlsZ7liLblk4E=\n","userId":"10076","praiseNum":"22","treadNum":"0","commentNum":"0","is_praise_tread":"1","userName":"IEW","headImg":"4a34713093f14be99f6341acc5e1f293.JPG","img":["uploads/dynamic/8c9f043fcd1f46f1a7ab1d4882f8fff2.jpg","uploads/dynamic/6c5de04d2e4c4aeb8f97a19d23bb9e68.jpg","uploads/dynamic/9eae0d49182e46a7a0603974447372bd.jpg"],"video":""}
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
         * dynamicId : 107
         * createTime : 01月18日
         * dynamicContent : M0TmiZPljbDljp/lnovvvIzmibnph4/lrprliLbljJbpppbppbDph5HlsZ7liLblk4E=

         * userId : 10076
         * praiseNum : 22
         * treadNum : 0
         * commentNum : 0
         * is_praise_tread : 1
         * userName : IEW
         * headImg : 4a34713093f14be99f6341acc5e1f293.JPG
         * img : ["uploads/dynamic/8c9f043fcd1f46f1a7ab1d4882f8fff2.jpg","uploads/dynamic/6c5de04d2e4c4aeb8f97a19d23bb9e68.jpg","uploads/dynamic/9eae0d49182e46a7a0603974447372bd.jpg"]
         * video :
         */

        private String dynamicId;
        private String createTime;
        private String dynamicContent;
        private String userId;
        private String praiseNum;
        private String treadNum;
        private String commentNum;
        private String is_praise_tread;
        private String userName;
        private String headImg;
        private String video;
        private List<String> img;

        public String getDynamicId() {
            return dynamicId;
        }

        public void setDynamicId(String dynamicId) {
            this.dynamicId = dynamicId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getDynamicContent() {
            return dynamicContent;
        }

        public void setDynamicContent(String dynamicContent) {
            this.dynamicContent = dynamicContent;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getPraiseNum() {
            return praiseNum;
        }

        public void setPraiseNum(String praiseNum) {
            this.praiseNum = praiseNum;
        }

        public String getTreadNum() {
            return treadNum;
        }

        public void setTreadNum(String treadNum) {
            this.treadNum = treadNum;
        }

        public String getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(String commentNum) {
            this.commentNum = commentNum;
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

        public String getHeadImg() {

            if (headImg.startsWith("http")) {

            } else if (headImg.startsWith("uploads/minhader")) {
                headImg=ComantUtils.MyUrlHot1 + headImg;
            } else {
                headImg=ComantUtils.MyUrlImageHader + headImg;
            }



            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public List<String> getImg() {
            return img;
        }

        public void setImg(List<String> img) {
            this.img = img;
        }
    }
}
