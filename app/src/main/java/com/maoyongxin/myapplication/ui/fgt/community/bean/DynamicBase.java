package com.maoyongxin.myapplication.ui.fgt.community.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.maoyongxin.myapplication.common.AppConfig;
import com.maoyongxin.myapplication.common.ComantUtils;
import com.maoyongxin.myapplication.common.MyApplication;

import java.util.List;

/**
 * @author: lujialei
 * @date: 2018/10/18
 * @describe:
 */


public class DynamicBase implements MultiItemEntity {

    public static final int THREE_SMALL = 1;
    public static final int THREE_ZHIDING = 2;
    public static final int RIGHT_BIG = 3;
    public static final int LEFT_BIG = 4;
    private int type;

    public DynamicBase(int type) {
        this.type = type;
    }

    /**
     * dynamicId : 106
     * createTime : 昨天 17:11
     * <p>
     * dynamicContent : 5o2i5ZWG5o2i6LWE5rqQ5o2i5Lq66ISJ
     * userId : 10218
     * praiseNum : 0
     * treadNum : 0
     * commentNum : 0
     * is_praise_tread : 1
     * userName : 彼信♪草原兄弟
     * headImg : b008b7742e044f12a6a72930a93ed711.jpg
     * img : ["63a4bee4f9ac4a52b987bfd64d8a8902.jpg","a8b861ab451e4173ac3b367b6f5ccf21.jpg"]
     * video :
     * communityUrl :
     * communityId : 4
     * communityName : 成都佳运天成知识产权
     */
    private boolean isAttention;
    private String dynamicId;

    public boolean isPraise() {
        return isPraise;
    }

    public void setPraise(boolean praise) {
        isPraise = praise;
    }

    private boolean isPraise;

    public boolean isAttention() {
        return isAttention;
    }

    public void setAttention(boolean attention) {
        isAttention = attention;
    }

    private String createTime;
    private String dynamicContent;
    private String userId;
    private String praiseNum;
    private String treadNum;
    private String commentNum;
    private String is_praise_tread;
    private String userName;
    private String headImg;
    private String video;
    private String communityUrl;
    private String communityId;
    private String communityName;
    private List<String> img;
    private String group_id;
    private String is_anymit;

    public String getIs_anymit() {
        try{
            if (is_anymit==null||is_anymit.equals(""))
            {
                is_anymit="1";
            }

            else if (is_anymit.equals("0"))
            {
                is_anymit=is_anymit;
            }
        }
        catch (Exception e)
        {
            is_anymit="0";
        }

        return is_anymit;
    }

    public void setIs_anymit(String is_anymit) {
        this.is_anymit = is_anymit;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getDynamicId() {
        return dynamicId;
    }

    public void setDynamicId(String dynamicId) {
        this.dynamicId = dynamicId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDynamicContent() {
        return dynamicContent;
    }

    public void setDynamicContent(String dynamicContent) {
        this.dynamicContent = dynamicContent;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPraiseNum() {
        return praiseNum;
    }

    public void setPraiseNum(String praiseNum) {
        this.praiseNum = praiseNum;
    }

    public String getTreadNum() {
        return treadNum;
    }

    public void setTreadNum(String treadNum) {
        this.treadNum = treadNum;
    }

    public String getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(String commentNum) {
        this.commentNum = commentNum;
    }

    public String getIs_praise_tread() {
        return is_praise_tread;
    }

    public void setIs_praise_tread(String is_praise_tread) {
        this.is_praise_tread = is_praise_tread;
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
        }
        else {
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

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getCommunityUrl() {
        return communityUrl;
    }

    public void setCommunityUrl(String communityUrl) {
        this.communityUrl = communityUrl;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public List<String> getImg() {
        return img;
    }

    public void setImg(List<String> img) {
        this.img = img;
    }

    @Override
    public int getItemType() {
        return type;
    }

}
