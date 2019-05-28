package com.maoyongxin.myapplication.ui.fgt.connection.act.bean;

import com.maoyongxin.myapplication.common.ComantUtils;

import java.util.List;

public class AnnouncementDetailsBean {

    /**
     * code : 200
     * operation : true
     * info : {"total":9,"per_page":15,"current_page":1,"last_page":1,
     * "data":[{"id":"22","parent_id":"0","user_notice_id":"1","uid":"10073",
     * "parent_uid":"0","content":"测测试试","praise_num":"0",
     * "create_time":"2019-02-19 19:53:10","update_time":"2019-02-19 19:53:10",
     * "is_show":"1","userName":"long","headImg":"d4649b99a9184987a57905ec19d37b83.jpg",
     * "createTime":"2小时前","isPraise":false,"parentUserName":"","parentUserHeadImg":"",
     * "row":{"total":2,"data":[{"id":"26","parent_id":"22","user_notice_id":"10069","uid":"10069",
     * "parent_uid":"0","content":"哈哈！","praise_num":"0","create_time":"2019-02-19 20:26:50",
     * "update_time":"2019-02-19 20:26:50","is_show":"1","userName":"彼信客服",
     * "headImg":"6ce22be318c746ea8f86f421633683bc.jpg","createTime":"1小时前","parentUserName":"",
     * "parentUserHeadImg":""},{"id":"25","parent_id":"22","user_notice_id":"10069","uid":"10069",
     * "parent_uid":"0","content":"你好","praise_num":"0","create_time":"2019-02-19 20:22:45","update_time":"2019-02-19 20:22:45","is_show":"1","userName":"彼信客服","headImg":"6ce22be318c746ea8f86f421633683bc.jpg","createTime":"1小时前","parentUserName":"","parentUserHeadImg":""}]}},{"id":"5","parent_id":"0","user_notice_id":"1","uid":"10069","parent_uid":"0","content":"子评论成功了么？","praise_num":"0","create_time":"2019-02-18 15:02:23","update_time":"2019-02-18 15:02:23","is_show":"1","userName":"彼信客服","headImg":"6ce22be318c746ea8f86f421633683bc.jpg","createTime":"昨天 15:02","isPraise":false,"parentUserName":"","parentUserHeadImg":"","row":{"total":2,"data":[{"id":"20","parent_id":"5","user_notice_id":"1","uid":"10069","parent_uid":"10069","content":"二级评论测试。","praise_num":"0","create_time":"2019-02-19 18:58:05","update_time":"2019-02-19 18:58:05","is_show":"1","userName":"彼信客服","headImg":"6ce22be318c746ea8f86f421633683bc.jpg","createTime":"3小时前","parentUserName":"彼信客服","parentUserHeadImg":"6ce22be318c746ea8f86f421633683bc.jpg"},{"id":"7","parent_id":"5","user_notice_id":"1","uid":"10069","parent_uid":"10069","content":"二级评论测试。","praise_num":"0","create_time":"2019-02-18 15:33:18","update_time":"2019-02-18 15:33:18","is_show":"1","userName":"彼信客服","headImg":"6ce22be318c746ea8f86f421633683bc.jpg","createTime":"昨天 15:33","parentUserName":"彼信客服","parentUserHeadImg":"6ce22be318c746ea8f86f421633683bc.jpg"}]}}]}
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
         * total : 9
         * per_page : 15
         * current_page : 1
         * last_page : 1
         * data : [{"id":"22","parent_id":"0","user_notice_id":"1","uid":"10073","parent_uid":"0","content":"测测试试","praise_num":"0","create_time":"2019-02-19 19:53:10","update_time":"2019-02-19 19:53:10","is_show":"1","userName":"long","headImg":"d4649b99a9184987a57905ec19d37b83.jpg","createTime":"2小时前","isPraise":false,"parentUserName":"","parentUserHeadImg":"","row":{"total":2,"data":[{"id":"26","parent_id":"22","user_notice_id":"10069","uid":"10069","parent_uid":"0","content":"哈哈！","praise_num":"0","create_time":"2019-02-19 20:26:50","update_time":"2019-02-19 20:26:50","is_show":"1","userName":"彼信客服","headImg":"6ce22be318c746ea8f86f421633683bc.jpg","createTime":"1小时前","parentUserName":"","parentUserHeadImg":""},{"id":"25","parent_id":"22","user_notice_id":"10069","uid":"10069","parent_uid":"0","content":"你好","praise_num":"0","create_time":"2019-02-19 20:22:45","update_time":"2019-02-19 20:22:45","is_show":"1","userName":"彼信客服","headImg":"6ce22be318c746ea8f86f421633683bc.jpg","createTime":"1小时前","parentUserName":"","parentUserHeadImg":""}]}},{"id":"5","parent_id":"0","user_notice_id":"1","uid":"10069","parent_uid":"0","content":"子评论成功了么？","praise_num":"0","create_time":"2019-02-18 15:02:23","update_time":"2019-02-18 15:02:23","is_show":"1","userName":"彼信客服","headImg":"6ce22be318c746ea8f86f421633683bc.jpg","createTime":"昨天 15:02","isPraise":false,"parentUserName":"","parentUserHeadImg":"","row":{"total":2,"data":[{"id":"20","parent_id":"5","user_notice_id":"1","uid":"10069","parent_uid":"10069","content":"二级评论测试。","praise_num":"0","create_time":"2019-02-19 18:58:05","update_time":"2019-02-19 18:58:05","is_show":"1","userName":"彼信客服","headImg":"6ce22be318c746ea8f86f421633683bc.jpg","createTime":"3小时前","parentUserName":"彼信客服","parentUserHeadImg":"6ce22be318c746ea8f86f421633683bc.jpg"},{"id":"7","parent_id":"5","user_notice_id":"1","uid":"10069","parent_uid":"10069","content":"二级评论测试。","praise_num":"0","create_time":"2019-02-18 15:33:18","update_time":"2019-02-18 15:33:18","is_show":"1","userName":"彼信客服","headImg":"6ce22be318c746ea8f86f421633683bc.jpg","createTime":"昨天 15:33","parentUserName":"彼信客服","parentUserHeadImg":"6ce22be318c746ea8f86f421633683bc.jpg"}]}}]
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
             * id : 22
             * parent_id : 0
             * user_notice_id : 1
             * uid : 10073
             * parent_uid : 0
             * content : 测测试试
             * praise_num : 0
             * create_time : 2019-02-19 19:53:10
             * update_time : 2019-02-19 19:53:10
             * is_show : 1
             * userName : long
             * headImg : d4649b99a9184987a57905ec19d37b83.jpg
             * createTime : 2小时前
             * isPraise : false
             * parentUserName :
             * parentUserHeadImg :
             * row : {"total":2,"data":[{"id":"26","parent_id":"22","user_notice_id":"10069","uid":"10069","parent_uid":"0","content":"哈哈！","praise_num":"0","create_time":"2019-02-19 20:26:50","update_time":"2019-02-19 20:26:50","is_show":"1","userName":"彼信客服","headImg":"6ce22be318c746ea8f86f421633683bc.jpg","createTime":"1小时前","parentUserName":"","parentUserHeadImg":""},{"id":"25","parent_id":"22","user_notice_id":"10069","uid":"10069","parent_uid":"0","content":"你好","praise_num":"0","create_time":"2019-02-19 20:22:45","update_time":"2019-02-19 20:22:45","is_show":"1","userName":"彼信客服","headImg":"6ce22be318c746ea8f86f421633683bc.jpg","createTime":"1小时前","parentUserName":"","parentUserHeadImg":""}]}
             */

            private String id;
            private String parent_id;
            private String user_notice_id;
            private String uid;
            private String parent_uid;
            private String content;
            private String praise_num;
            private String create_time;
            private String update_time;
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

            public String getParent_id() {
                return parent_id;
            }

            public void setParent_id(String parent_id) {
                this.parent_id = parent_id;
            }

            public String getUser_notice_id() {
                return user_notice_id;
            }

            public void setUser_notice_id(String user_notice_id) {
                this.user_notice_id = user_notice_id;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getParent_uid() {
                return parent_uid;
            }

            public void setParent_uid(String parent_uid) {
                this.parent_uid = parent_uid;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getPraise_num() {
                return praise_num;
            }

            public void setPraise_num(String praise_num) {
                this.praise_num = praise_num;
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

                String headurl = "";
                if (headImg == null || headImg.equals("")) {
                    headurl = "";
                } else {
                    if (headImg.contains("uploads/minhader")) {
                        headurl = ComantUtils.MyUrlHot1 + headImg;
                    }
                    if (headImg.contains("http://qzapp.qlogo.cn")) {
                        headurl = headImg;
                    } else if (headImg.contains("http://thirdwx.qlogo.cn")) {
                        headurl = headImg;
                    }

                }

                return headurl;
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
                 * total : 2
                 * data : [{"id":"26","parent_id":"22","user_notice_id":"10069","uid":"10069","parent_uid":"0","content":"哈哈！","praise_num":"0","create_time":"2019-02-19 20:26:50","update_time":"2019-02-19 20:26:50","is_show":"1","userName":"彼信客服","headImg":"6ce22be318c746ea8f86f421633683bc.jpg","createTime":"1小时前","parentUserName":"","parentUserHeadImg":""},{"id":"25","parent_id":"22","user_notice_id":"10069","uid":"10069","parent_uid":"0","content":"你好","praise_num":"0","create_time":"2019-02-19 20:22:45","update_time":"2019-02-19 20:22:45","is_show":"1","userName":"彼信客服","headImg":"6ce22be318c746ea8f86f421633683bc.jpg","createTime":"1小时前","parentUserName":"","parentUserHeadImg":""}]
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
                     * id : 26
                     * parent_id : 22
                     * user_notice_id : 10069
                     * uid : 10069
                     * parent_uid : 0
                     * content : 哈哈！
                     * praise_num : 0
                     * create_time : 2019-02-19 20:26:50
                     * update_time : 2019-02-19 20:26:50
                     * is_show : 1
                     * userName : 彼信客服
                     * headImg : 6ce22be318c746ea8f86f421633683bc.jpg
                     * createTime : 1小时前
                     * parentUserName :
                     * parentUserHeadImg :
                     */

                    private String id;
                    private String parent_id;
                    private String user_notice_id;
                    private String uid;
                    private String parent_uid;
                    private String content;
                    private String praise_num;
                    private String create_time;
                    private String update_time;
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

                    public String getParent_id() {
                        return parent_id;
                    }

                    public void setParent_id(String parent_id) {
                        this.parent_id = parent_id;
                    }

                    public String getUser_notice_id() {
                        return user_notice_id;
                    }

                    public void setUser_notice_id(String user_notice_id) {
                        this.user_notice_id = user_notice_id;
                    }

                    public String getUid() {
                        return uid;
                    }

                    public void setUid(String uid) {
                        this.uid = uid;
                    }

                    public String getParent_uid() {
                        return parent_uid;
                    }

                    public void setParent_uid(String parent_uid) {
                        this.parent_uid = parent_uid;
                    }

                    public String getContent() {
                        return content;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    public String getPraise_num() {
                        return praise_num;
                    }

                    public void setPraise_num(String praise_num) {
                        this.praise_num = praise_num;
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
                        String headurl = "";
                        if (headImg == null || headImg.equals("")) {
                            headurl = "";
                        } else {
                            if (headImg.contains("uploads/minhader")) {
                                headurl = ComantUtils.MyUrlHot1 + headImg;
                            }
                            if (headImg.contains("http://qzapp.qlogo.cn")) {
                                headurl = headImg;
                            } else if (headImg.contains("http://thirdwx.qlogo.cn")) {
                                headurl = headImg;
                            }

                        }
                        return headurl;
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
