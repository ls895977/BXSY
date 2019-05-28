package com.maoyongxin.myapplication.ui;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lykj.aextreme.afinal.utils.MyToast;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.common.BaseAct;
import com.maoyongxin.myapplication.common.MyApplication;
import com.maoyongxin.myapplication.http.callback.EntityCallBack;
import com.maoyongxin.myapplication.http.request.ReqAddFriends;
import com.maoyongxin.myapplication.http.request.ReqGroup;
import com.maoyongxin.myapplication.http.response.BaseResp;
import com.maoyongxin.myapplication.http.response.GroupResponse;
import com.maoyongxin.myapplication.ui.fgt.message.act.Act_StrangerDetail;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.util.Locale;

import io.rong.imlib.model.Conversation;
import okhttp3.Call;

/**
 * 会话界面
 */
public class Act_Chat extends BaseAct {
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
    public int initLayoutId() {
        return R.layout.activity_chat;
    }


    @Override
    public void initView() {
            hideHeader();
        String joined = "http://st.3dgogo.com/index/chatgroup/isUserAddChatgroupOrFriendApi.html";
        Intent intent = getIntent();
        conversationType = Conversation.ConversationType.valueOf(intent.getData()
                .getLastPathSegment().toUpperCase(Locale.US));//GROUP,PRIVATE
        groupId = intent.getData().getQueryParameter("targetId");
        userName = intent.getData().getQueryParameter("title");
        getgrouplist(joined);
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
        tv_joined.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (conversationType)
                {
                    case GROUP:
                        GroupAddRequest(groupId);
                        break;

                    case PRIVATE:
                        FriendAddRequest(groupId);
                        break;
                }



            }
        });
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

                Intent intent = new Intent(Act_Chat.this,Act_StrangerDetail.class);
                intent.putExtra("personId", groupId);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initData() {

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
    protected void handlerPassMsg(int target, int target1, Object obj) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction("finish");
        this.registerReceiver(receiver, filter);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(receiver);
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




    private void FriendAddRequest(final String Userid)
    {
        final EditText inputServer = new EditText(getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("您将添加对方为好友").setIcon(android.R.drawable.ic_input_add).setView(inputServer)
                .setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                ReqAddFriends.addFriends(getActivity(), getActivityTag(), MyApplication.getCurrentUserInfo().getUserId(), Userid, inputServer.getText().toString(), new EntityCallBack<BaseResp>() {
                    @Override
                    public Class<BaseResp> getEntityClass() {
                        return BaseResp.class;
                    }

                    @Override
                    public void onSuccess(BaseResp resp) {
                        if (resp.is200()) {
                            MyToast.show(context,"好友请求发送成功,请等待回复");
                            //   finish();
                        } else {
                            MyToast.show(context,(resp.msg));
                            //finish();
                        }
                    }

                    @Override
                    public void onFailure(Throwable e) {

                    }

                    @Override
                    public void onCancelled(Throwable e) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
            }
        });
        builder.show();
    }

    private void GroupAddRequest(final String GroupId)
    {      ReqGroup.getGroupInfo(this, getActivityTag(), GroupId, new EntityCallBack<GroupResponse>() {
        @Override
        public Class<GroupResponse> getEntityClass() {
            return GroupResponse.class;
        }

        @Override
        public void onSuccess(final GroupResponse resp) {
            if (resp.is200()) {

                final  String Hostid=resp.obj.getGroupHostId()+"";


                final EditText inputServer = new EditText(getActivity());
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("您将申请加入本群").setView(inputServer)
                        .setNegativeButton("取消", null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ReqGroup.joinGroup(getActivity(), getActivityTag(), MyApplication.getCurrentUserInfo().getUserId(), resp.obj.getGroupId() + "", inputServer.getText().toString(), new EntityCallBack<BaseResp>() {
                            @Override
                            public Class<BaseResp> getEntityClass() {
                                return BaseResp.class;
                            }

                            @Override
                            public void onSuccess(BaseResp resp) {
                                MyToast.show(context,resp.msg);
                                String userId=MyApplication.getCurrentUserInfo().getUserId()+"";
                                dealMessage(Hostid ,"3",userId+"",GroupId);
                                tv_joined.setVisibility(View.GONE);
                            }

                            @Override
                            public void onFailure(Throwable e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onCancelled(Throwable e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onFinished() {

                            }
                        });

                    }
                });
                builder.show();

            } else {

            }
        }

        @Override
        public void onFailure(Throwable e) {
            //lineSearchResult.setVisibility(View.GONE);
        }

        @Override
        public void onCancelled(Throwable e) {
            //lineSearchResult.setVisibility(View.GONE);
        }

        @Override
        public void onFinished() {

        }
    });}

    private void dealMessage(String HostId,String state, String joinuserId,String groupId) {
        ReqGroup.dealGroupMessages(getActivity(), "adapter", HostId, groupId, joinuserId, state, new EntityCallBack<BaseResp>() {
            @Override
            public Class<BaseResp> getEntityClass() {
                return BaseResp.class;
            }

            @Override
            public void onSuccess(BaseResp resp) {
                if (resp.is200()) {
                    Toast.makeText(getActivity(),"加入群成功",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable e) {
                Toast.makeText(getActivity(),"加入失败",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(Throwable e) {
                Toast.makeText(getActivity(),"取消加入",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinished() {

            }
        });

    }
}
