package com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.base;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.maoyongxin.myapplication.common.AppConfig;
import com.maoyongxin.myapplication.common.ComantUtils;

import java.io.Serializable;
import java.util.List;


public class GroupHuatiBase implements MultiItemEntity , Serializable {
    public static final int LEFT_BIG = 1;
    public static final int THREE_ZHIDING = 2;
    public static final int THREE_SMALL = 3;
    public static final int RIGHT_BIG = 4;
    private int type;
    public GroupHuatiBase(int type) {
        this.type = type;
    }
    @Override
    public int getItemType() {
        return type;
    }

    /**
     * id : 37
     * uid : 10071
     * title : 关于创业锦囊话题
     * content : MeOAgeWIhuS6q+WIm+S4mueahOe7j+mqjOe7meWkp+WutuW4ruWKqeaWsOeahOWIm+S4muiAhemB
     * v+W8gOWIm+S4mumZt+mYsQoy44CB5YiG5Lqr5Lmf5Y+v5Lul6K6w5b2V5LiL6Ieq5bex5Yib5Lia
     * 54q25oCB77yM55WZ5LiL576O5aW955qE5Zue5b+GCjPjgIHlpJrlpJrlj5HluIPplKblm4rlj6/k
     * u6Xmj5DljYfkvIHkuJrmm53lhYnnjoflk6bwn5iE8J+YhPCfmIQ=
     * <p>
     * post_num : 6
     * create_time : 2018年12月05日
     * img :
     * praise_num : 1
     * tread_num : 0
     * is_top : 1
     * userName : 神秘大侠
     * headImg : b32ccf7467bf4157a7a3c46be41403c3.jpg
     */
    private boolean isPraise;

    public boolean isPraise() {
        return isPraise;
    }

    public void setPraise(boolean praise) {
        isPraise = praise;
    }

    private String id;
    private String uid;
    private String title;
    private String content;
    private String post_num;
    private String create_time;
    private String img;
    private String praise_num;
    private String tread_num;
    private String is_top;
    private String userName;
    private String headImg;

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

    public String getPost_num() {
        return post_num;
    }

    public void setPost_num(String post_num) {
        this.post_num = post_num;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPraise_num() {
        return praise_num;
    }

    public void setPraise_num(String praise_num) {
        this.praise_num = praise_num;
    }

    public String getTread_num() {
        return tread_num;
    }

    public void setTread_num(String tread_num) {
        this.tread_num = tread_num;
    }

    public String getIs_top() {
        return is_top;
    }

    public void setIs_top(String is_top) {
        this.is_top = is_top;
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
            if (headImg.startsWith("uploads/minhader")) {
                headurl = ComantUtils.MyUrlHot1 + headImg;
            } else if (headImg.startsWith("http")) {
                headurl = headImg;
            } else {
                headurl = AppConfig.sRootUrl + "/logincontroller/getHeadImg/" + headImg;
            }
        }
        return headurl;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }
}
