package com.maoyongxin.myapplication.bean;

public class LoDIngImagBean {

    /**
     * code : 200
     * info : {"id":"15","img_v":"106","img_t":"appLogin图片","img":"http://st.3dgogo.com//uploads/photos/st_photos/20181105/493363570cb51c88fbedcd7c2fd6c9e8.png","url":"www.3dgogo.com","title":"","description":"","record_state":"1","sequence":"0000000000","create_at":"1534833167","update_at":"1541384421","css":""}
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
         * id : 15
         * img_v : 106
         * img_t : appLogin图片
         * img : http://st.3dgogo.com//uploads/photos/st_photos/20181105/493363570cb51c88fbedcd7c2fd6c9e8.png
         * url : www.3dgogo.com
         * title :
         * description :
         * record_state : 1
         * sequence : 0000000000
         * create_at : 1534833167
         * update_at : 1541384421
         * css :
         */

        private String id;
        private String img_v;
        private String img_t;
        private String img;
        private String url;
        private String title;
        private String description;
        private String record_state;
        private String sequence;
        private String create_at;
        private String update_at;
        private String css;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImg_v() {
            return img_v;
        }

        public void setImg_v(String img_v) {
            this.img_v = img_v;
        }

        public String getImg_t() {
            return img_t;
        }

        public void setImg_t(String img_t) {
            this.img_t = img_t;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getRecord_state() {
            return record_state;
        }

        public void setRecord_state(String record_state) {
            this.record_state = record_state;
        }

        public String getSequence() {
            return sequence;
        }

        public void setSequence(String sequence) {
            this.sequence = sequence;
        }

        public String getCreate_at() {
            return create_at;
        }

        public void setCreate_at(String create_at) {
            this.create_at = create_at;
        }

        public String getUpdate_at() {
            return update_at;
        }

        public void setUpdate_at(String update_at) {
            this.update_at = update_at;
        }

        public String getCss() {
            return css;
        }

        public void setCss(String css) {
            this.css = css;
        }
    }
}
