package com.maoyongxin.myapplication.ui.fgt.message.act.bean;

import java.util.List;

public class ChatGroupUserBean {

    /**
     * code : 200
     * info : [{"groupId":"1","groupName":"创业锦囊","image":"http://st.3dgogo.com/uploads/photos/order_tracking_photos/20180921/36caf27585396de6177ef24832c5f898.jpg"},{"groupId":"4","groupName":"12把","image":"http://st.3dgogo.com/uploads/photos/order_tracking_photos/20181128/c2618170ce68536edd70d7483db60974.jpg"}]
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
         * groupId : 1
         * groupName : 创业锦囊
         * image : http://st.3dgogo.com/uploads/photos/order_tracking_photos/20180921/36caf27585396de6177ef24832c5f898.jpg
         */

        private String groupId;
        private String groupName;
        private String image;

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
