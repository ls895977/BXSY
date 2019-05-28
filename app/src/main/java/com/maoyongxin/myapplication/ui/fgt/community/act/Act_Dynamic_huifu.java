package com.maoyongxin.myapplication.ui.fgt.community.act;

import android.content.Context;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lykj.aextreme.afinal.utils.Debug;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.bean.MessageBean;
import com.maoyongxin.myapplication.common.BaseAct;
import com.maoyongxin.myapplication.common.MyApplication;
import com.maoyongxin.myapplication.http.entity.huifuInfo;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.BindView;
import io.github.rockerhieu.emojicon.EmojiconEditText;
import io.github.rockerhieu.emojicon.EmojiconGridFragment;
import io.github.rockerhieu.emojicon.EmojiconsFragment;
import io.github.rockerhieu.emojicon.emoji.Emojicon;
import okhttp3.Call;

/**
 * 回复窗口
 */
public class Act_Dynamic_huifu extends BaseAct implements EmojiconsFragment.OnEmojiconBackspaceClickedListener, EmojiconGridFragment.OnEmojiconClickedListener {
    TextView IbDynamic;
    EditText edDynamicDetail;
    TextView tvParent;
    EmojiconEditText ed_huifu_d;
    FrameLayout moj_view;
    RelativeLayout moj_btn;
    ImageView ic_close;
    @Override
    public int initLayoutId() {
        return R.layout.act_dynamic_huifu;
    }

    @Override
    public void initView() {
        hideHeader();
        IbDynamic = getViewAndClick(R.id.Ib_Dynamic);
        edDynamicDetail = getView(R.id.ed_Dynamic_detail);
        tvParent = getView(R.id.tv_parent);
        ed_huifu_d = getView(R.id.ed_huifu_d);
        moj_view = getView(R.id.moj_view);
        moj_btn = getViewAndClick(R.id.moj_btn);
        ic_close=getViewAndClick(R.id.ic_close);
    }

    private String dynamicId, uid, uName, panrentId, parentUserId, toUser, personId, CommunityId, parentName, parentId;
    private String userid;

    @Override
    public void initData() {
        parentUserId = getIntent().getStringExtra("parentUserId");
        dynamicId = getIntent().getStringExtra("DynamicId");
        CommunityId = getIntent().getStringExtra("CommunityId");
        parentId = getIntent().getStringExtra("parentId");
        userid = getIntent().getStringExtra("userid");
        uid = MyApplication.getCurrentUserInfo().getUserId() + "";
        uName = MyApplication.getCurrentUserInfo().getUserName() + "";
        tvParent.setText(getIntent().getStringExtra("parentName"));
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.moj_view, EmojiconsFragment.newInstance(false))
                .commit();
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
            case R.id.Ib_Dynamic:
                setIbQuerenhuifu();
                break;
            case R.id.moj_btn:
                if (moj_view.getVisibility() == View.GONE) {
                    moj_view.setVisibility(View.VISIBLE);
                } else {
                    moj_view.setVisibility(View.GONE);
                }


                break;

            case R.id.ic_close:
                finish();
                break;

        }
    }

    @Override
    protected void handlerPassMsg(int target, int target1, Object obj) {

    }


    private void setIbQuerenhuifu() {//标准接口请求+
        String s = ed_huifu_d.getText().toString();
        String strBase64 = Base64.encodeToString(s.getBytes(), Base64.DEFAULT);
        OkHttpUtils.post().url("http://st.3dgogo.com/index/user_dynamic/set_user_dynamic_post.html")
                .addParams("dynamicId", dynamicId)//动态id
                .addParams("parentId", parentId)//一级评论时为0
                .addParams("postContent", strBase64)//内容
                .addParams("userId", uid)//回复者id
                .addParams("parentUserId", parentUserId)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    postPushMessage(userid);
                    Toast.makeText(context, "回复成功", Toast.LENGTH_SHORT).show();
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, "回复失败", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    @Override
    public void onEmojiconClicked(Emojicon emojicon) {
        EmojiconsFragment.input(ed_huifu_d, emojicon);
    }

    @Override
    public void onEmojiconBackspaceClicked(View v) {
        EmojiconsFragment.backspace(ed_huifu_d);
    }


    /**
     * 推送消息
     * alias 指定推送人id
     * content推送消息内容
     */
    public void postPushMessage(String UserId) {
        Debug.e("--------------UserId===" + UserId);
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
