package com.maoyongxin.myapplication.bean;

public class MessageBean {
    private String title;//表示通知标题
    private String builder_id;//表示通知栏样式 IDpriority表示通知栏展示优先级，默认为 0，范围为 -2～2 ，其他值将会被忽略而采用默认值
    private String category;//表示通知栏条目过滤或排序，完全依赖 rom 厂商对 category 的处理策略
    private String style;//表示通知栏样式类型，默认为 0，还有1，2，3可选，用来指定选择哪种通知栏样式，其他值无效。有三种可选分别为 bigText=1，Inbox=2，bigPicture=3
    private String alert_type;//表示通知提醒方式， 可选范围为 -1～7 ，对应 Notification.DEFAULT_ALL = -1 或者 Notification.DEFAULT_SOUND = 1， Notification.DEFAULT_VIBRATE = 2，
    private String big_text;//表示大文本通知栏样式，当 style = 1 时可用，内容会被通知栏以大文本的形式展示出来，支持 api 16 以上的 rom
    private String inbox;//表示文本条目通知栏样式，接受一个数组，当 style = 2 时可用，数组的每个 key 对应的 value 会被当作文本条目逐条展示，支持 api 16 以上的 rom
    private String big_pic_path;//表示大图片通知栏样式，当 style = 3 时可用，可以是网络图片 url，或本地图片的 path，目前支持 .jpg 和 .png 后缀的图片。图片内容会被通知栏以大图片的形式展示出来。如果是 http／https 的 url，会自动下载；如果要指定开发者准备的本地图片就填 sdcard 的相对路径，支持 api 16 以上的 rom
    private String large_icon;//表示通知栏大图标，图标路径可以是以 http 或 https 开头的网络图片，如："http:jiguang.cn/logo.png"，图标大小不超过 30k； 也可以是位于 drawable 资源文件夹的图标路径，如："R.drawable.lg_icon"；
    private String intent;//表示扩展字段，接受一个数组，自定义 Key/value 信息以供业务使用
    private String extras;//表示扩展字段，接受一个数组，自定义 Key/value 信息以供业务使用

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBuilder_id() {
        return builder_id;
    }

    public void setBuilder_id(String builder_id) {
        this.builder_id = builder_id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getAlert_type() {
        return alert_type;
    }

    public void setAlert_type(String alert_type) {
        this.alert_type = alert_type;
    }

    public String getBig_text() {
        return big_text;
    }

    public void setBig_text(String big_text) {
        this.big_text = big_text;
    }

    public String getInbox() {
        return inbox;
    }

    public void setInbox(String inbox) {
        this.inbox = inbox;
    }

    public String getBig_pic_path() {
        return big_pic_path;
    }

    public void setBig_pic_path(String big_pic_path) {
        this.big_pic_path = big_pic_path;
    }

    public String getLarge_icon() {
        return large_icon;
    }

    public void setLarge_icon(String large_icon) {
        this.large_icon = large_icon;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public String getExtras() {
        return extras;
    }

    public void setExtras(String extras) {
        this.extras = extras;
    }
}
