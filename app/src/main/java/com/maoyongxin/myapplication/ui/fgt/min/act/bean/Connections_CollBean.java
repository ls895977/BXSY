package com.maoyongxin.myapplication.ui.fgt.min.act.bean;

import java.util.List;

public class Connections_CollBean {

    /**
     * code : 200
     * count : 2
     * info : [{"id":"4","uid":"10069","createUid":"10069","name":"宜宾市建丰劳动服务有限公司","legalPersonName":"李洋","creditCode":null,"phoneNumber":"电话  14780899877","email":"邮箱  暂无数据","websiteList":"官网  ","businessScope":"建筑工程劳务分包（凭资质经营）。（依法须经批准的项目，经相关部门批准后方可开展经营活动）","rank_num":"0","createTime":"1548139890","updateTime":"1548139890"},{"id":"5","uid":"10069","createUid":"10069","name":"宜宾市鑫宜福建筑工程有限公司","legalPersonName":"冯建国","creditCode":null,"phoneNumber":"电话  13795809868","email":"邮箱  暂无数据","websiteList":"官网  ","businessScope":"土石方工程；公路工程；园林绿化工程；停车场服务；住宿服务；仓储服务、普通货运（前两项不含危险品）；市场管理；农业休闲观光旅游服务；花卉、盆景的培育及销售；建材、农产品销售。（依法须经批准的项目，经相关部门批准后方可开展经营活动）。","rank_num":"0","createTime":"1550419217","updateTime":"1550419217"}]
     */

    private int code;
    private int count;
    private List<InfoBean> info;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * id : 4
         * uid : 10069
         * createUid : 10069
         * name : 宜宾市建丰劳动服务有限公司
         * legalPersonName : 李洋
         * creditCode : null
         * phoneNumber : 电话  14780899877
         * email : 邮箱  暂无数据
         * websiteList : 官网
         * businessScope : 建筑工程劳务分包（凭资质经营）。（依法须经批准的项目，经相关部门批准后方可开展经营活动）
         * rank_num : 0
         * createTime : 1548139890
         * updateTime : 1548139890
         */

        private String id;
        private String uid;
        private String createUid;
        private String name;
        private String legalPersonName;
        private Object creditCode;
        private String phoneNumber;
        private String email;
        private String websiteList;
        private String businessScope;
        private String rank_num;
        private String createTime;
        private String updateTime;

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

        public String getCreateUid() {
            return createUid;
        }

        public void setCreateUid(String createUid) {
            this.createUid = createUid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLegalPersonName() {
            return legalPersonName;
        }

        public void setLegalPersonName(String legalPersonName) {
            this.legalPersonName = legalPersonName;
        }

        public Object getCreditCode() {
            return creditCode;
        }

        public void setCreditCode(Object creditCode) {
            this.creditCode = creditCode;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getWebsiteList() {
            return websiteList;
        }

        public void setWebsiteList(String websiteList) {
            this.websiteList = websiteList;
        }

        public String getBusinessScope() {
            return businessScope;
        }

        public void setBusinessScope(String businessScope) {
            this.businessScope = businessScope;
        }

        public String getRank_num() {
            return rank_num;
        }

        public void setRank_num(String rank_num) {
            this.rank_num = rank_num;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
