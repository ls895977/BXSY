package com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.bean;

import java.util.List;

public class MyTopicBean {

    /**
     * code : 200
     * info : {"total":1,"per_page":15,"current_page":1,"last_page":1,"next_page":1,"data":[{"id":"31","uid":"10069","title":"提供免费app设计服务","content":"5oiR5Lus5a62KOW9vOS/oSnmmK/kuJPpl6jlgZrova/ku7bnmoTvvIzmnInpnIDopoHnmoTmnIvl\nj4vlj6/ku6Xmib7miJHku6zluK7kvaDku6zlgZphcHDorr7orqHov5nkuIDlnZco5YWN6LS5KfCf\nmITwn5iE8J+YhOKciA==\n","post_num":"0","create_time":"2018年10月22日","img":"http://st.3dgogo.com/uploads/photos/order_tracking_photos/20181022/b9dc6a1716c67fb9fc95e48ac8ee084b.jpg","praise_num":"2","tread_num":"0","is_top":"0","userName":"彼信客服","headImg":"6ce22be318c746ea8f86f421633683bc.jpg","communityUrl":"http://bisonchat.com/index/enterprise_publicity/get_community_id_details_url_api.html?community_id=2","communityId":"2","communityName":"彼信商业社区"}]}
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
         * total : 1
         * per_page : 15
         * current_page : 1
         * last_page : 1
         * next_page : 1
         * data : [{"id":"31","uid":"10069","title":"提供免费app设计服务","content":"5oiR5Lus5a62KOW9vOS/oSnmmK/kuJPpl6jlgZrova/ku7bnmoTvvIzmnInpnIDopoHnmoTmnIvl\nj4vlj6/ku6Xmib7miJHku6zluK7kvaDku6zlgZphcHDorr7orqHov5nkuIDlnZco5YWN6LS5KfCf\nmITwn5iE8J+YhOKciA==\n","post_num":"0","create_time":"2018年10月22日","img":"http://st.3dgogo.com/uploads/photos/order_tracking_photos/20181022/b9dc6a1716c67fb9fc95e48ac8ee084b.jpg","praise_num":"2","tread_num":"0","is_top":"0","userName":"彼信客服","headImg":"6ce22be318c746ea8f86f421633683bc.jpg","communityUrl":"http://bisonchat.com/index/enterprise_publicity/get_community_id_details_url_api.html?community_id=2","communityId":"2","communityName":"彼信商业社区"}]
         */

        private int total;
        private int per_page;
        private int current_page;
        private int last_page;
        private int next_page;
        private List<DataBean> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPer_page() {
            return per_page;
        }

        public void setPer_page(int per_page) {
            this.per_page = per_page;
        }

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public int getLast_page() {
            return last_page;
        }

        public void setLast_page(int last_page) {
            this.last_page = last_page;
        }

        public int getNext_page() {
            return next_page;
        }

        public void setNext_page(int next_page) {
            this.next_page = next_page;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id : 31
             * uid : 10069
             * title : 提供免费app设计服务
             * content : 5oiR5Lus5a62KOW9vOS/oSnmmK/kuJPpl6jlgZrova/ku7bnmoTvvIzmnInpnIDopoHnmoTmnIvl
             j4vlj6/ku6Xmib7miJHku6zluK7kvaDku6zlgZphcHDorr7orqHov5nkuIDlnZco5YWN6LS5KfCf
             mITwn5iE8J+YhOKciA==

             * post_num : 0
             * create_time : 2018年10月22日
             * img : http://st.3dgogo.com/uploads/photos/order_tracking_photos/20181022/b9dc6a1716c67fb9fc95e48ac8ee084b.jpg
             * praise_num : 2
             * tread_num : 0
             * is_top : 0
             * userName : 彼信客服
             * headImg : 6ce22be318c746ea8f86f421633683bc.jpg
             * communityUrl : http://bisonchat.com/index/enterprise_publicity/get_community_id_details_url_api.html?community_id=2
             * communityId : 2
             * communityName : 彼信商业社区
             */

            private String id;
            private String uid;
            private String title;
            private String content;
            private String post_num;
            private String create_time;
            private String img;
            private String praise_num;
            private String tread_num;
            private String is_top;
            private String userName;
            private String headImg;
            private String communityUrl;
            private String communityId;
            private String communityName;

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

            public String getPost_num() {
                return post_num;
            }

            public void setPost_num(String post_num) {
                this.post_num = post_num;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
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
                return headImg;
            }

            public void setHeadImg(String headImg) {
                this.headImg = headImg;
            }

            public String getCommunityUrl() {
                return communityUrl;
            }

            public void setCommunityUrl(String communityUrl) {
                this.communityUrl = communityUrl;
            }

            public String getCommunityId() {
                return communityId;
            }

            public void setCommunityId(String communityId) {
                this.communityId = communityId;
            }

            public String getCommunityName() {
                return communityName;
            }

            public void setCommunityName(String communityName) {
                this.communityName = communityName;
            }
        }
    }
}
