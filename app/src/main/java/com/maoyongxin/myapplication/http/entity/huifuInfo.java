package com.maoyongxin.myapplication.http.entity;

import com.lykj.aextreme.afinal.utils.Debug;
import com.maoyongxin.myapplication.common.AppConfig;
import com.maoyongxin.myapplication.common.ComantUtils;

/**
 * Created by yusr on 2018/5/10.
 */

public class huifuInfo {

    private String pic;
    private String huifu_user;
    private String huatiId;
    private String huifu_time;
    private String huaticontent;
    private int huifuCai;
    private int huifuZan;
    private String huifuUserId;
    private String InfoType;
    private String parentusrId;
    private String parentname;
    private String comment_user_id;

    public void sethuifuInfo(String comment_user_id,String InfoType, String touxiang, String huifu_user, String huatiId, String huifu_time, String huaticontent, int huifuCai, int huifuZan, String huifuUserId, String parentname, String parentusrId) {
        this.pic = touxiang;
        this.huifu_user = huifu_user;
        this.huatiId = huatiId;
        this.huifu_time = huifu_time;
        this.huaticontent = huaticontent;

        this.huifuCai = huifuCai;
        this.huifuZan = huifuZan;

        this.huifuUserId = huifuUserId;
        this.InfoType = InfoType;

        this.parentname = parentname;
        this.parentusrId = parentusrId;
        this.comment_user_id=comment_user_id;
    }


    public String getPic() {
        String headurl = "";
        if (pic == null || pic.equals("")) {
            headurl = "";
        }
        else {

            if (pic.startsWith("uploads/minhader")) {
                headurl = ComantUtils.MyUrlHot1 + pic;
            }
           else if (pic.startsWith("http")) {
                headurl = pic;

            }
            else
            {
                headurl =AppConfig.sRootUrl+"/logincontroller/getHeadImg/"+ pic;
            }

        }


        return headurl;
    }

    public String getHuifu_user() {
        return this.huifu_user;
    }

    public String getHuatiId() {
        return this.huatiId;
    }

    public String getHuifu_time() {
        return this.huifu_time;
    }

    public String getHuaticontent() {
        return this.huaticontent;
    }

    public String getInfoType() {
        return this.InfoType;
    }

    public int getHuifuCai() {
        return this.huifuCai;
    }

    public int getHuifuZan() {
        return this.huifuZan;
    }

    public String getHuifuUserId() {
        return this.huifuUserId;
    }

    public String getParentusrId() {
        return this.parentusrId;
    }

    public String getParentname() {
        return this.parentname;
    }
   public String getComment_user_id(){
     return this.comment_user_id;
   }
}
