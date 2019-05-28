package com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.bean;

import java.util.List;

public class ChatGroupUserInfoListBean {

    /**
     * code : 200
     * operation : true
     * info : {"member_sum":3,"data":[{"id":"1","userType":"1","groupId":"1","joinUserId":"10069","joinUserName":"彼信客服","joinUserNote":"群主","joinState":"3","joinDate":"2018-09-21 18:14:19","grouphostuserid":"10069","userName":"彼信客服","headImg":"6ce22be318c746ea8f86f421633683bc.jpg"},{"id":"4","userType":"3","groupId":"1","joinUserId":"10070","joinUserName":"rdhdj","joinUserNote":"","joinState":"3","joinDate":"2018-09-25 00:17:20","grouphostuserid":"10069","userName":"余小呆子","headImg":"bea77ec03126424684de45919b669469.jpg"},{"id":"5","userType":"3","groupId":"1","joinUserId":"10094","joinUserName":"西瓜轩财务","joinUserNote":"","joinState":"3","joinDate":"2018-09-29 16:59:38","grouphostuserid":"10069","userName":"西瓜轩财务","headImg":"a902e5d3d675465ca6bfefb22747b6c2.jpg"}]}
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
         * member_sum : 3
         * data : [{"id":"1","userType":"1","groupId":"1","joinUserId":"10069","joinUserName":"彼信客服","joinUserNote":"群主","joinState":"3","joinDate":"2018-09-21 18:14:19","grouphostuserid":"10069","userName":"彼信客服","headImg":"6ce22be318c746ea8f86f421633683bc.jpg"},{"id":"4","userType":"3","groupId":"1","joinUserId":"10070","joinUserName":"rdhdj","joinUserNote":"","joinState":"3","joinDate":"2018-09-25 00:17:20","grouphostuserid":"10069","userName":"余小呆子","headImg":"bea77ec03126424684de45919b669469.jpg"},{"id":"5","userType":"3","groupId":"1","joinUserId":"10094","joinUserName":"西瓜轩财务","joinUserNote":"","joinState":"3","joinDate":"2018-09-29 16:59:38","grouphostuserid":"10069","userName":"西瓜轩财务","headImg":"a902e5d3d675465ca6bfefb22747b6c2.jpg"}]
         */

        private int member_sum;
        private List<DataBean> data;

        public int getMember_sum() {
            return member_sum;
        }

        public void setMember_sum(int member_sum) {
            this.member_sum = member_sum;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * id : 1
             * userType : 1
             * groupId : 1
             * joinUserId : 10069
             * joinUserName : 彼信客服
             * joinUserNote : 群主
             * joinState : 3
             * joinDate : 2018-09-21 18:14:19
             * grouphostuserid : 10069
             * userName : 彼信客服
             * headImg : 6ce22be318c746ea8f86f421633683bc.jpg
             */

            private String id;
            private String userType;
            private String groupId;
            private String joinUserId;
            private String joinUserName;
            private String joinUserNote;
            private String joinState;
            private String joinDate;
            private String grouphostuserid;
            private String userName;
            private String headImg;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUserType() {
                return userType;
            }

            public void setUserType(String userType) {
                this.userType = userType;
            }

            public String getGroupId() {
                return groupId;
            }

            public void setGroupId(String groupId) {
                this.groupId = groupId;
            }

            public String getJoinUserId() {
                return joinUserId;
            }

            public void setJoinUserId(String joinUserId) {
                this.joinUserId = joinUserId;
            }

            public String getJoinUserName() {
                return joinUserName;
            }

            public void setJoinUserName(String joinUserName) {
                this.joinUserName = joinUserName;
            }

            public String getJoinUserNote() {
                return joinUserNote;
            }

            public void setJoinUserNote(String joinUserNote) {
                this.joinUserNote = joinUserNote;
            }

            public String getJoinState() {
                return joinState;
            }

            public void setJoinState(String joinState) {
                this.joinState = joinState;
            }

            public String getJoinDate() {
                return joinDate;
            }

            public void setJoinDate(String joinDate) {
                this.joinDate = joinDate;
            }

            public String getGrouphostuserid() {
                return grouphostuserid;
            }

            public void setGrouphostuserid(String grouphostuserid) {
                this.grouphostuserid = grouphostuserid;
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
        }
    }
}
