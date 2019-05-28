package com.maoyongxin.myapplication.ui.fgt.community.bean;

import com.maoyongxin.myapplication.common.ComantUtils;

import java.util.List;

public class JournalismDetailsBean {

    /**
     * code : 200
     * operation : true
     * info : {"total":6,"per_page":15,"current_page":1,"last_page":1,"data":[{"id":"21","content":"评论测试啦啦啦","create_time":"2019-02-28 14:35:44","uid":"10069","news_id":"159","parent_id":"0","parent_uid":"0","praise_num":"0","is_show":"1","userName":"彼信客服","headImg":"6ce22be318c746ea8f86f421633683bc.jpg","createTime":"3小时前","isPraise":false,"parentUserName":"","parentUserHeadImg":"","row":{"total":1,"data":[{"id":"22","content":"评论测试啦啦啦","create_time":"2019-02-28 14:37:55","uid":"10073","news_id":"159","parent_id":"21","parent_uid":"10069","praise_num":"0","is_show":"1","userName":"long","headImg":"d4649b99a9184987a57905ec19d37b83.jpg","createTime":"3小时前","parentUserName":"彼信客服","parentUserHeadImg":"6ce22be318c746ea8f86f421633683bc.jpg"}]}}]}
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
         * total : 6
         * per_page : 15
         * current_page : 1
         * last_page : 1
         * data : [{"id":"21","content":"评论测试啦啦啦","create_time":"2019-02-28 14:35:44","uid":"10069","news_id":"159","parent_id":"0","parent_uid":"0","praise_num":"0","is_show":"1","userName":"彼信客服","headImg":"6ce22be318c746ea8f86f421633683bc.jpg","createTime":"3小时前","isPraise":false,"parentUserName":"","parentUserHeadImg":"","row":{"total":1,"data":[{"id":"22","content":"评论测试啦啦啦","create_time":"2019-02-28 14:37:55","uid":"10073","news_id":"159","parent_id":"21","parent_uid":"10069","praise_num":"0","is_show":"1","userName":"long","headImg":"d4649b99a9184987a57905ec19d37b83.jpg","createTime":"3小时前","parentUserName":"彼信客服","parentUserHeadImg":"6ce22be318c746ea8f86f421633683bc.jpg"}]}}]
         */

        private int total;
        private int per_page;
        private int current_page;
        private int last_page;
        private List<DataBeanX> data;

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

        public List<DataBeanX> getData() {
            return data;
        }

        public void setData(List<DataBeanX> data) {
            this.data = data;
        }

        public static class DataBeanX {
            /**
             * id : 21
             * content : 评论测试啦啦啦
             * create_time : 2019-02-28 14:35:44
             * uid : 10069
             * news_id : 159
             * parent_id : 0
             * parent_uid : 0
             * praise_num : 0
             * is_show : 1
             * userName : 彼信客服
             * headImg : 6ce22be318c746ea8f86f421633683bc.jpg
             * createTime : 3小时前
             * isPraise : false
             * parentUserName :
             * parentUserHeadImg :
             * row : {"total":1,"data":[{"id":"22","content":"评论测试啦啦啦","create_time":"2019-02-28 14:37:55","uid":"10073","news_id":"159","parent_id":"21","parent_uid":"10069","praise_num":"0","is_show":"1","userName":"long","headImg":"d4649b99a9184987a57905ec19d37b83.jpg","createTime":"3小时前","parentUserName":"彼信客服","parentUserHeadImg":"6ce22be318c746ea8f86f421633683bc.jpg"}]}
             */
            private String id;
            private String content;
            private String create_time;
            private String uid;
            private String news_id;
            private String parent_id;
            private String parent_uid;
            private String praise_num;
            private String is_show;
            private String userName;
            private String headImg;
            private String createTime;
            private boolean isPraise;
            private String parentUserName;
            private String parentUserHeadImg;
            private RowBean row;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getNews_id() {
                return news_id;
            }

            public void setNews_id(String news_id) {
                this.news_id = news_id;
            }

            public String getParent_id() {
                return parent_id;
            }

            public void setParent_id(String parent_id) {
                this.parent_id = parent_id;
            }

            public String getParent_uid() {
                return parent_uid;
            }

            public void setParent_uid(String parent_uid) {
                this.parent_uid = parent_uid;
            }

            public String getPraise_num() {
                return praise_num;
            }

            public void setPraise_num(String praise_num) {
                this.praise_num = praise_num;
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
                String imgUrl="";
                if (headImg == null || headImg.equals("")) {
                    imgUrl = "";
                } else {
                    if (headImg.contains("uploads/minhader")) {
                        imgUrl = ComantUtils.MyUrlHot1 + headImg;
                    }
                    if (headImg.contains("http://qzapp.qlogo.cn")) {
                        imgUrl=headImg;
                    } else if (headImg.contains("http://thirdwx.qlogo.cn")) {
                        imgUrl=headImg;
                    }
                }
                return imgUrl;
            }

            public void setHeadImg(String headImg) {
                this.headImg = headImg;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public boolean isIsPraise() {
                return isPraise;
            }

            public void setIsPraise(boolean isPraise) {
                this.isPraise = isPraise;
            }

            public String getParentUserName() {
                return parentUserName;
            }

            public void setParentUserName(String parentUserName) {
                this.parentUserName = parentUserName;
            }

            public String getParentUserHeadImg() {
                return parentUserHeadImg;
            }

            public void setParentUserHeadImg(String parentUserHeadImg) {
                this.parentUserHeadImg = parentUserHeadImg;
            }

            public RowBean getRow() {
                return row;
            }

            public void setRow(RowBean row) {
                this.row = row;
            }

            public static class RowBean {
                /**
                 * total : 1
                 * data : [{"id":"22","content":"评论测试啦啦啦","create_time":"2019-02-28 14:37:55","uid":"10073","news_id":"159","parent_id":"21","parent_uid":"10069","praise_num":"0","is_show":"1","userName":"long","headImg":"d4649b99a9184987a57905ec19d37b83.jpg","createTime":"3小时前","parentUserName":"彼信客服","parentUserHeadImg":"6ce22be318c746ea8f86f421633683bc.jpg"}]
                 */

                private int total;
                private List<DataBean> data;

                public int getTotal() {
                    return total;
                }

                public void setTotal(int total) {
                    this.total = total;
                }

                public List<DataBean> getData() {
                    return data;
                }

                public void setData(List<DataBean> data) {
                    this.data = data;
                }

                public static class DataBean {
                    /**
                     * id : 22
                     * content : 评论测试啦啦啦
                     * create_time : 2019-02-28 14:37:55
                     * uid : 10073
                     * news_id : 159
                     * parent_id : 21
                     * parent_uid : 10069
                     * praise_num : 0
                     * is_show : 1
                     * userName : long
                     * headImg : d4649b99a9184987a57905ec19d37b83.jpg
                     * createTime : 3小时前
                     * parentUserName : 彼信客服
                     * parentUserHeadImg : 6ce22be318c746ea8f86f421633683bc.jpg
                     */

                    private String id;
                    private String content;
                    private String create_time;
                    private String uid;
                    private String news_id;
                    private String parent_id;
                    private String parent_uid;
                    private String praise_num;
                    private String is_show;
                    private String userName;
                    private String headImg;
                    private String createTime;
                    private String parentUserName;
                    private String parentUserHeadImg;

                    public String getId() {
                        return id;
                    }

                    public void setId(String id) {
                        this.id = id;
                    }

                    public String getContent() {
                        return content;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    public String getCreate_time() {
                        return create_time;
                    }

                    public void setCreate_time(String create_time) {
                        this.create_time = create_time;
                    }

                    public String getUid() {
                        return uid;
                    }

                    public void setUid(String uid) {
                        this.uid = uid;
                    }

                    public String getNews_id() {
                        return news_id;
                    }

                    public void setNews_id(String news_id) {
                        this.news_id = news_id;
                    }

                    public String getParent_id() {
                        return parent_id;
                    }

                    public void setParent_id(String parent_id) {
                        this.parent_id = parent_id;
                    }

                    public String getParent_uid() {
                        return parent_uid;
                    }

                    public void setParent_uid(String parent_uid) {
                        this.parent_uid = parent_uid;
                    }

                    public String getPraise_num() {
                        return praise_num;
                    }

                    public void setPraise_num(String praise_num) {
                        this.praise_num = praise_num;
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

                    public String getCreateTime() {
                        return createTime;
                    }

                    public void setCreateTime(String createTime) {
                        this.createTime = createTime;
                    }

                    public String getParentUserName() {
                        return parentUserName;
                    }

                    public void setParentUserName(String parentUserName) {
                        this.parentUserName = parentUserName;
                    }

                    public String getParentUserHeadImg() {
                        return parentUserHeadImg;
                    }

                    public void setParentUserHeadImg(String parentUserHeadImg) {
                        this.parentUserHeadImg = parentUserHeadImg;
                    }
                }
            }
        }
    }
}
