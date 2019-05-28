package com.maoyongxin.myapplication.ui.fgt.connection.act;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lykj.aextreme.afinal.utils.Debug;
import com.lykj.aextreme.afinal.utils.MyToast;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.bean.MessageBean;
import com.maoyongxin.myapplication.common.BaseAct;
import com.maoyongxin.myapplication.common.ComantUtils;
import com.maoyongxin.myapplication.common.MyApplication;
import com.maoyongxin.myapplication.ui.fgt.connection.act.bean.AddUserNoticePostBean;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.subsciber.IProgressDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import io.github.rockerhieu.emojicon.EmojiconEditText;
import okhttp3.Call;

import static com.zhouyou.http.EasyHttp.getContext;

/**
 * 公告需求=回复=及话题回复
 */
public class Act_AnnouncementReply extends BaseAct {
    private EmojiconEditText neirong;
    ZLoadingDialog dialog;

    @Override
    protected void handlerPassMsg(int target, int target1, Object obj) {

    }

    @Override
    public int initLayoutId() {
        return R.layout.act_announcementreply;
    }

    @Override
    public void initView() {
        hideHeader();
        setOnClickListener(R.id.friends_back);
        neirong = getView(R.id.ServicePublishing_Context);
        setOnClickListener(R.id.service_coomit);
        dialog = new ZLoadingDialog(this);
        dialog.setLoadingBuilder(Z_TYPE.ROTATE_CIRCLE)//设置类型
                .setLoadingColor(Color.DKGRAY)//颜色
                .setHintText("数据加载中...")
                .setHintTextSize(16) // 设置字体大小 dp
                .setHintTextColor(Color.DKGRAY)  // 设置字体颜色
                .setDurationTime(0.5) // 设置动画时间百分比 - 0.5倍
                .setDialogBackgroundColor(Color.parseColor("#CCffffff")); // 设置背景色，默认白色
    }

    private String parent_id, parent_uid, user_notice_id, notice_type;

    @Override
    public void initData() {
        if (getIntent().getStringExtra("title") != null && getIntent().getStringExtra("title").equals("HotReviews")) {//热门话题详情=热门评论
            parent_id = getIntent().getStringExtra("parent_id");
            parentUserId = getIntent().getStringExtra("parent_uid");
            gambit_id = getIntent().getStringExtra("gambit_id");
            group_id = getIntent().getStringExtra("group_id");
            return;
        }
        if (getIntent().getStringExtra("parent_id") != null) {//子评论
            parent_id = getIntent().getStringExtra("parent_id");
            parent_uid = getIntent().getStringExtra("parent_uid");
        } else {
            parent_id = "0";
            parent_uid = "0";
        }
        if (getIntent().getStringExtra("title") != null && getIntent().getStringExtra("title").equals("JournalismDetails")) {//从新闻过来的子评论
            id1 = getIntent().getStringExtra("id1");
            return;
        }
        notice_type = getIntent().getStringExtra("notice_type");
        user_notice_id = getIntent().getStringExtra("user_notice_id");
    }

    @Override
    public void requestData() {

    }

    @Override
    public void updateUI() {

    }

    @Override
    public void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.friends_back:
                finish();
                break;
            case R.id.service_coomit://提交
                if (getIntent().getStringExtra("title") != null && getIntent().getStringExtra("title").equals("HotReviews")) {//热门话题详情=热门评论
                    postHoTMessage();
                    return;
                }
                if (getIntent().getStringExtra("title") != null && getIntent().getStringExtra("title").equals("JournalismDetails")) {//从新闻过来的子评论
                    postMessage();
                    return;
                } else {
                    sedMessage();
                }
                break;
        }
    }

    public void sedMessage() {
        if (TextUtils.isEmpty(neirong.getText().toString())) {
            MyToast.show(context, "您还未填写要回复的内容！");
            return;
        }
        dialog.show();
        OkHttpUtils.post().url(ComantUtils.MyUrlHot + ComantUtils.addUserNoticePostApi)
                .addParams("uid", MyApplication.getCurrentUserInfo().getUserId())
                .addParams("user_notice_id", user_notice_id)
                .addParams("notice_type", notice_type)
                .addParams("content", setEncryption(neirong.getText().toString()))
                .addParams("parent_id", parent_id)
                .addParams("parent_uid", parent_uid)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                dialog.dismiss();
            }

            @Override
            public void onResponse(String response, int id) {
                Gson gson = new Gson();
                AddUserNoticePostBean bean = gson.fromJson(response, AddUserNoticePostBean.class);
                if (bean.getCode() == 200) {
                    if (bean.isOperation()) {
                        setResult(10);
                        finish();
                        postPushMessage(parent_uid);
                        MyToast.show(context, bean.getMsg());
                    } else {
                        MyToast.show(context, bean.getMsg());
                    }
                }
                dialog.dismiss();
            }
        });
    }

    /**
     * 加密
     * oldWord：需要加密的文字/比如密码
     */
    String encodeWord;

    public String setEncryption(String oldWord) {
        encodeWord = Base64.encodeToString(oldWord.getBytes(), Base64.DEFAULT);
        return encodeWord;
    }

    /**
     * 发送评论
     */
    String id1;

    public void postMessage() {
        if (TextUtils.isEmpty(neirong.getText().toString())) {
            return;
        }
        dialog.show();
        String url = ComantUtils.MyUrlHot + "/news_comment/setNewsCommentApi";
        OkHttpUtils.post().url(url)
                .addParams("uid", MyApplication.getCurrentUserInfo().getUserId())
                .addParams("news_id", id1)
                .addParams("content", setEncryption(neirong.getText().toString()))
                .addParams("parent_id", parent_id)
                .addParams("parent_uid", parent_uid)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                dialog.dismiss();
            }

            @Override
            public void onResponse(String response, int id) {
                Debug.e("---onResponse--------" + response);
                postPushMessage(parent_uid);
                setResult(10);
                finish();
            }
        });
    }

    /**
     * 添加话题评论数据==发送评论
     */
    private String gambit_id, parentUserId, group_id;

    public void postHoTMessage() {
        if (TextUtils.isEmpty(neirong.getText().toString())) {
            return;
        }
        dialog.show();
        String url = "http://bisonchat.com/index/chatgroup_gambit/set_respond.html";
        OkHttpUtils.post().url(url)
                .addParams("uid", MyApplication.getCurrentUserInfo().getUserId())
                .addParams("parent_id", parent_id)
                .addParams("gambit_id", gambit_id)
                .addParams("content", setEncryption(neirong.getText().toString()))
                .addParams("parentUserId", parentUserId)
                .addParams("group_id", group_id)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                dialog.dismiss();
            }

            @Override
            public void onResponse(String response, int id) {
                postPushMessage(parentUserId);
                setResult(10);
                finish();
            }
        });
    }

    /**
     * 推送消息
     * alias 指定推送人id
     * content推送消息内容
     */
    public void postPushMessage(String UserId) {
        Debug.e("---------------推送消息=="+UserId);
        MessageBean bean = new MessageBean();
//        bean.setTitle("点赞消息！");
        bean.setTitle("回复信息");
        Gson gson = new Gson();
        String android_notification = gson.toJson(bean);
        OkHttpUtils.get().url("http://bisonchat.com/index/jpush/aliasPushApi")
                .addParams("alias", UserId)
//                .addParams("content", "您的好友：" + MyApplication.getCurrentUserInfo().getUserName() + "为您点了一个'赞'")
                .addParams("content", "您的好友：" + MyApplication.getCurrentUserInfo().getUserName() + "为您回复了一个消息")
                .addParams("android_notification", android_notification)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Debug.e("---------------onError==" + e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                Debug.e("---------------onResponse==" + response);
            }
        });
    }

}
