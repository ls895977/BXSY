package com.maoyongxin.myapplication.bean;

import java.util.List;

public class PublisherBean {

    /**
     * code : 200
     * info : {"total":1,"per_page":15,"current_page":1,"last_page":1,"data":[{"id":"7","uid":"10069","notice_id":"8","notice_type":"0","create_time":"2019-03-31 11:27:58","update_time":"2019-03-31 11:27:58","row":{"id":"8","uid":"10069","title":"测试囖吧","content":"超级照镜子来来来","img":null,"classify_id":"7","indate":"2019-04-16 00:00:00","area":"浙江省杭州市拱墅区","detailed_area":"","area_code":null,"phone":"2548556555","qq_account":"89556","create_time":"2019-02-16 23:18:33","update_time":"2019-02-16 23:18:33","is_show":"1","userName":"彼信客服","classifyName":"企业求助","createTime":"02月16日","notice_type":0,"isEnshrine":true},"createTime":"03月31日"}]}
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
         * data : [{"id":"7","uid":"10069","notice_id":"8","notice_type":"0","create_time":"2019-03-31 11:27:58","update_time":"2019-03-31 11:27:58","row":{"id":"8","uid":"10069","title":"测试囖吧","content":"超级照镜子来来来","img":null,"classify_id":"7","indate":"2019-04-16 00:00:00","area":"浙江省杭州市拱墅区","detailed_area":"","area_code":null,"phone":"2548556555","qq_account":"89556","create_time":"2019-02-16 23:18:33","update_time":"2019-02-16 23:18:33","is_show":"1","userName":"彼信客服","classifyName":"企业求助","createTime":"02月16日","notice_type":0,"isEnshrine":true},"createTime":"03月31日"}]
         */

        private int total;
        private int per_page;
        private int current_page;
        private int last_page;
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

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id : 7
             * uid : 10069
             * notice_id : 8
             * notice_type : 0
             * create_time : 2019-03-31 11:27:58
             * update_time : 2019-03-31 11:27:58
             * row : {"id":"8","uid":"10069","title":"测试囖吧","content":"超级照镜子来来来","img":null,"classify_id":"7","indate":"2019-04-16 00:00:00","area":"浙江省杭州市拱墅区","detailed_area":"","area_code":null,"phone":"2548556555","qq_account":"89556","create_time":"2019-02-16 23:18:33","update_time":"2019-02-16 23:18:33","is_show":"1","userName":"彼信客服","classifyName":"企业求助","createTime":"02月16日","notice_type":0,"isEnshrine":true}
             * createTime : 03月31日
             */

            private String id;
            private String uid;
            private String notice_id;
            private String notice_type;
            private String create_time;
            private String update_time;
            private RowBean row;
            private String createTime;

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

            public String getNotice_id() {
                return notice_id;
            }

            public void setNotice_id(String notice_id) {
                this.notice_id = notice_id;
            }

            public String getNotice_type() {
                return notice_type;
            }

            public void setNotice_type(String notice_type) {
                this.notice_type = notice_type;
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

            public RowBean getRow() {
                return row;
            }

            public void setRow(RowBean row) {
                this.row = row;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public static class RowBean {
                /**
                 * id : 8
                 * uid : 10069
                 * title : 测试囖吧
                 * content : 超级照镜子来来来
                 * img : null
                 * classify_id : 7
                 * indate : 2019-04-16 00:00:00
                 * area : 浙江省杭州市拱墅区
                 * detailed_area :
                 * area_code : null
                 * phone : 2548556555
                 * qq_account : 89556
                 * create_time : 2019-02-16 23:18:33
                 * update_time : 2019-02-16 23:18:33
                 * is_show : 1
                 * userName : 彼信客服
                 * classifyName : 企业求助
                 * createTime : 02月16日
                 * notice_type : 0
                 * isEnshrine : true
                 */

                private String id;
                private String uid;
                private String title;
                private String content;
                private Object img;
                private String classify_id;
                private String indate;
                private String area;
                private String detailed_area;
                private Object area_code;
                private String phone;
                private String qq_account;
                private String create_time;
                private String update_time;
                private String is_show;
                private String userName;
                private String classifyName;
                private String createTime;
                private int notice_type;
                private boolean isEnshrine;

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

                public Object getImg() {
                    return img;
                }

                public void setImg(Object img) {
                    this.img = img;
                }

                public String getClassify_id() {
                    return classify_id;
                }

                public void setClassify_id(String classify_id) {
                    this.classify_id = classify_id;
                }

                public String getIndate() {
                    return indate;
                }

                public void setIndate(String indate) {
                    this.indate = indate;
                }

                public String getArea() {
                    return area;
                }

                public void setArea(String area) {
                    this.area = area;
                }

                public String getDetailed_area() {
                    return detailed_area;
                }

                public void setDetailed_area(String detailed_area) {
                    this.detailed_area = detailed_area;
                }

                public Object getArea_code() {
                    return area_code;
                }

                public void setArea_code(Object area_code) {
                    this.area_code = area_code;
                }

                public String getPhone() {
                    return phone;
                }

                public void setPhone(String phone) {
                    this.phone = phone;
                }

                public String getQq_account() {
                    return qq_account;
                }

                public void setQq_account(String qq_account) {
                    this.qq_account = qq_account;
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

                public String getClassifyName() {
                    return classifyName;
                }

                public void setClassifyName(String classifyName) {
                    this.classifyName = classifyName;
                }

                public String getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
                }

                public int getNotice_type() {
                    return notice_type;
                }

                public void setNotice_type(int notice_type) {
                    this.notice_type = notice_type;
                }

                public boolean isIsEnshrine() {
                    return isEnshrine;
                }

                public void setIsEnshrine(boolean isEnshrine) {
                    this.isEnshrine = isEnshrine;
                }
            }
        }
    }
}
