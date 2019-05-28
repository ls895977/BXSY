package com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.fgt.bean;

import com.maoyongxin.myapplication.common.AppConfig;
import com.maoyongxin.myapplication.common.ComantUtils;

public class TopicDetailsHaderBean {

    /**
     * code : 200
     * info : {"id":"30","uid":"10084","group_id":"1",
     * "title":"今天又收到一批商标局新发的证书，扫描存到后立即送给小主们",
     * "content":"5LuK5aSp5Y+I5pS25Yiw5LiA5om55ZWG5qCH5bGA5paw5Y+R55qE6K+B5Lmm77yM5omr5o+P5a2Y\n5Yiw5ZCO56uL5Y2z6YCB57uZ5bCP5Li75Lus8J+klw==\n",
     * "img":"http://st.3dgogo.com/uploads/photos/order_tracking_photos/20181022/9af8d32e0013f28c9a96c4822d7ae6a9.jpg",
     * "post_num":"58","praise_num":"32","tread_num":"0","create_time":"2018年10月22日",
     * "update_time":"1540192890","lately_time":"1545567685","is_praise_tread":"1","is_top":"0",
     * "userName":"成都佳运天成知识产权",
     * "headImg":"http://118.24.2.164:8089/logincontroller/getHeadImg/c36707e1f34340e29e2fc6233863dcbd.jpg"}
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
         * id : 30
         * uid : 10084
         * group_id : 1
         * title : 今天又收到一批商标局新发的证书，扫描存到后立即送给小主们
         * content : 5LuK5aSp5Y+I5pS25Yiw5LiA5om55ZWG5qCH5bGA5paw5Y+R55qE6K+B5Lmm77yM5omr5o+P5a2Y
         5Yiw5ZCO56uL5Y2z6YCB57uZ5bCP5Li75Lus8J+klw==
         * img : http://st.3dgogo.com/uploads/photos/order_tracking_photos/20181022/9af8d32e0013f28c9a96c4822d7ae6a9.jpg
         * post_num : 58
         * praise_num : 32
         * tread_num : 0
         * create_time : 2018年10月22日
         * update_time : 1540192890
         * lately_time : 1545567685
         * is_praise_tread : 1
         * is_top : 0
         * userName : 成都佳运天成知识产权
         * headImg : http://118.24.2.164:8089/logincontroller/getHeadImg/c36707e1f34340e29e2fc6233863dcbd.jpg
         */

        private String id;
        private String uid;
        private String group_id;
        private String title;
        private String content;
        private String img;
        private String post_num;
        private String praise_num;
        private String tread_num;
        private String create_time;
        private String update_time;
        private String lately_time;
        private String is_praise_tread;
        private String is_top;
        private String userName;
        private String headImg;

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

        public String getGroup_id() {
            return group_id;
        }

        public void setGroup_id(String group_id) {
            this.group_id = group_id;
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

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getPost_num() {
            return post_num;
        }

        public void setPost_num(String post_num) {
            this.post_num = post_num;
        }

        public String getPraise_num() {
            return praise_num;
        }

        public void setPraise_num(String praise_num) {
            this.praise_num = praise_num;
        }

        public String getTread_num() {
            return tread_num;
        }

        public void setTread_num(String tread_num) {
            this.tread_num = tread_num;
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

        public String getLately_time() {
            return lately_time;
        }

        public void setLately_time(String lately_time) {
            this.lately_time = lately_time;
        }

        public String getIs_praise_tread() {
            return is_praise_tread;
        }

        public void setIs_praise_tread(String is_praise_tread) {
            this.is_praise_tread = is_praise_tread;
        }

        public String getIs_top() {
            return is_top;
        }

        public void setIs_top(String is_top) {
            this.is_top = is_top;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getHeadImg() {
            if (headImg.startsWith("uploads"))
            {
                headImg= ComantUtils.MyUrlHot1 +headImg;
            }
            else if (headImg.startsWith("http"))
            {

            }
            else
            {
                headImg= AppConfig.sRootUrl + "/logincontroller/getHeadImg/"+headImg;
            }


            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }
    }
}
