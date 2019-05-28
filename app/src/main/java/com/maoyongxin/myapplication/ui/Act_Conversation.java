package com.maoyongxin.myapplication.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 融云会话页面
 */
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.common.BaseAct;
import com.maoyongxin.myapplication.common.MyApplication;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.util.Locale;

import io.rong.imlib.model.Conversation;
import okhttp3.Call;

public class Act_Conversation extends BaseAct {
    private ImageView img_back_go, img_editGroup, img_editPrivate;
    private TextView title, tv_joined;
    private Conversation.ConversationType conversationType;
    private String groupId;
    private String userName;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };
    @Override
    protected void handlerPassMsg(int target, int target1, Object obj) {

    }

    @Override
    public int initLayoutId() {
        return R.layout.act_conversation;
    }

    @Override
    public void initView() {
        hideHeader();
        tv_joined = (TextView) findViewById(R.id.tv_joined);
        img_back_go = (ImageView) findViewById(R.id.img_back_go);
        img_editGroup = (ImageView) findViewById(R.id.img_editGroup);
        img_editPrivate = (ImageView) findViewById(R.id.img_editPrivate);
        title = (TextView) findViewById(R.id.tv_title);
        img_back_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (conversationType.equals(Conversation.ConversationType.GROUP)) {
            img_editGroup.setVisibility(View.VISIBLE);
            img_editPrivate.setVisibility(View.GONE);
            title.setText(userName);
        } else {
            img_editGroup.setVisibility(View.GONE);
            img_editPrivate.setVisibility(View.VISIBLE);
            title.setText(userName);
            if (
                    MyApplication.getCurrentUserInfo().getUserId().equals(groupId)) {
                img_editPrivate.setVisibility(View.GONE);
            } else {
                img_editPrivate.setVisibility(View.VISIBLE);
            }
        }
        img_editGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(ConversationActivity.this, GroupDetailActivity.class);
//                intent.putExtra("groupId", groupId);
//                intent.putExtra("hostId", "");
//                startActivity(intent);
            }
        });
        img_editPrivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent intent = new Intent(ConversationActivity.this, UserDetailActivity.class);
//                Intent intent = new Intent(ConversationActivity.this, StrangerDetailActivity.class);
//                intent.putExtra("personId", groupId);
//                startActivity(intent);
            }
        });
    }

    @Override
    public void initData() {
        String joined = "http://st.3dgogo.com/index/chatgroup/isUserAddChatgroupOrFriendApi.html";
        Intent intent = getIntent();
        conversationType = Conversation.ConversationType.valueOf(intent.getData()
                .getLastPathSegment().toUpperCase(Locale.US));//GROUP,PRIVATE
        groupId = intent.getData().getQueryParameter("targetId");
        userName = intent.getData().getQueryParameter("title");
        getgrouplist(joined);
    }

    @Override
    public void requestData() {

    }

    @Override
    public void updateUI() {

    }

    @Override
    public void onViewClick(View v) {

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(receiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction("finish");
        this.registerReceiver(receiver, filter);
    }

    private void getgrouplist(final String joined)//标准post线程
    {

        OkHttpUtils.post().url(joined)
                .addParams("type", conversationType.toString())
                .addParams("targetId",groupId.toString())
                .addParams("userId", MyApplication.getCurrentUserInfo().getUserId())
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                e.printStackTrace();

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int data = jsonObject.getInt("code");
                    if (data == 200) {

                    } else if (data == 500) {
                        switch (conversationType) {
                            case GROUP:
                                tv_joined.setVisibility(View.VISIBLE);
                                tv_joined.setText("您还没有加入群组");
                                break;
                            case PRIVATE:
                                tv_joined.setVisibility(View.VISIBLE);
                                tv_joined.setText("你们还不是好友");
                                break;


                        }
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
    }
}
