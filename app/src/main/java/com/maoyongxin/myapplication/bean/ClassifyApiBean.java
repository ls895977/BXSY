package com.maoyongxin.myapplication.bean;

import java.util.List;

public class ClassifyApiBean {
    /**
     * code : 200
     * info : [{"id":5,"name":"企业招聘"},{"id":6,"name":"企业采购"},{"id":7,"name":"企业求助"},{"id":8,"name":"企业专卖"},{"id":9,"name":"企业其它"}]
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
         * id : 5
         * name : 企业招聘
         */

        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
