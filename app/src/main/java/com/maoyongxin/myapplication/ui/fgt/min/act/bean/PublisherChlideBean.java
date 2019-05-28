package com.maoyongxin.myapplication.ui.fgt.min.act.bean;

import java.util.List;

public class PublisherChlideBean {

    /**
     * code : 200
     * info : {"total":1,"per_page":10,"current_page":1,"last_page":1,"data":[{"id":"7","uid":"10069","notice_id":"8","notice_type":"0","create_time":"2019-03-31 11:27:58","update_time":"2019-03-31 11:27:58","row":null,"createTime":"03月31日"}]}
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
         * per_page : 10
         * current_page : 1
         * last_page : 1
         * data : [{"id":"7","uid":"10069","notice_id":"8","notice_type":"0","create_time":"2019-03-31 11:27:58","update_time":"2019-03-31 11:27:58","row":null,"createTime":"03月31日"}]
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
             * row : null
             * createTime : 03月31日
             */

            private String id;
            private String uid;
            private String notice_id;
            private String notice_type;
            private String create_time;
            private String update_time;
            private Object row;
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

            public Object getRow() {
                return row;
            }

            public void setRow(Object row) {
                this.row = row;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }
        }
    }
}
