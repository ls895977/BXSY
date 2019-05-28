package com.maoyongxin.myapplication.http.entity;


import com.maoyongxin.myapplication.common.AppConfig;
import com.maoyongxin.myapplication.common.ComantUtils;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Created by maoyongxin on 2017/12/1.
 */

public class UserInfoService {

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
    private String background_img;

    public String getBackground_img() {

            if(background_img==null||background_img.equals("")){
                background_img= "";
            }
            else
            {
                if (background_img.startsWith("uploads/minhader")) {
                    background_img=ComantUtils.MyUrlHot1+background_img;
                }
                else if (background_img.startsWith("http")) {
                    background_img=background_img;
                }
                else {

                    background_img =AppConfig.sRootUrl+"/logincontroller/getHeadImg/"+ background_img;
                }

            }

        return background_img;
    }

    public void setBackground_img(String background_img) {
        this.background_img = background_img;
    }
    private String permanent_city;

    public String getPermanent_city() {
        return permanent_city;
    }

    public void setPermanent_city(String permanent_city) {
        this.permanent_city = permanent_city;
    }
    private String position;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * userId : 10029
     * userName : 昵称
     * longitude : null
     * latitude : null
     * token : j4hr8L3wcohFhEakekQlt9pSRhamXgLNKSGtRUbLwkBa6V8cMezoFMVEue/ktSljQ5OKoRVDZrsnqw5SK36czw==
     * note : 备注
     * headImg : null
     * userPhone : null
     * sex : null
     * brithday : null
     * vipNum : null
     */
    private String createDate;
    private String friendNum;
    private String userId;
    private String userName;
    private String longitude="103.860635";
    private String latitude="30.778693";
    private String token;
    private String note;
    private String headImg;
    private String userPhone;
    private String sex;
    private String brithday;
    private String vipNum;
    private Boolean serverhead=true;



    public void  setFriendNum(String friendNum){
        this.friendNum=friendNum;
    }
    public String getFriendNum(){
        return friendNum;
    }
    public String getUserId() {
        String useruserId="";
        try
        {
            useruserId=this.userId;
        }
        catch (Exception e)
        {
            useruserId="";
        }
        return useruserId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        String userNames="";
        try
        {
             userNames=this.userName;
        }
        catch (Exception e)
        {
            userNames=this.userName;
        }
        return  userNames;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }



    public String getHeadImg() {
          String headurl="";
        if(serverhead)
        {
            if(headImg==null||headImg.equals("")){
                headurl= "";
            }
            else
             {
                if (headImg.startsWith("uploads/minhader")) {
                    headurl=ComantUtils.MyUrlHot1+headImg;
                }
                else if (headImg.startsWith("http")) {
                    headurl=headImg;
                }
                else {

                    headurl =AppConfig.sRootUrl+"/logincontroller/getHeadImg/"+ headImg;
                }

            }
        }
        else
        {
            headurl=headImg;
        }
        return headurl;
    }


    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }
    public void setServerhead(Boolean isserver){
        this.serverhead=isserver;
    }

    public String getUserPhone() {
        if(userPhone==null){
            return "";
        }
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getSex() {

        if(sex==null||sex.equals(""))
        {

            return "0";

        }



        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBrithday() {
        if(brithday==null||brithday.equals("")){
            return "";
        }else{
//            return getStrTime(brithday);
            return brithday;
        }
    }

    public void setBrithday(String brithday) {
        this.brithday = brithday;
    }

    public String getVipNum() {
        return vipNum;
    }

    public void setVipNum(String vipNum) {
        this.vipNum = vipNum;
    }
    //时间戳转字符串
//    public static String getStrTime(String timeStamp){
//        String timeString = null;
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
//        long  l = Long.valueOf(timeStamp);
//        timeString = sdf.format(new Date(l));//单位秒
//        return timeString;
//    }
}
