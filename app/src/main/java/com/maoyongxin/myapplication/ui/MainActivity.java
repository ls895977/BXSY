package com.maoyongxin.myapplication.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import cn.jpush.android.api.JPushInterface;
import io.reactivex.functions.Consumer;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lykj.aextreme.afinal.utils.Debug;
import com.lykj.aextreme.afinal.utils.MyToast;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.common.AppConfig;
import com.maoyongxin.myapplication.common.BaseAct;
import com.maoyongxin.myapplication.common.BaseFgt;
import com.maoyongxin.myapplication.common.ComantUtils;
import com.maoyongxin.myapplication.common.MyApplication;
import com.maoyongxin.myapplication.http.callback.EntityCallBack;
import com.maoyongxin.myapplication.http.entity.LocationInfo;
import com.maoyongxin.myapplication.http.request.ReqFindUserById;
import com.maoyongxin.myapplication.http.request.ReqGroup;
import com.maoyongxin.myapplication.http.request.ReqUserServer;
import com.maoyongxin.myapplication.http.response.BaiduPushResponse;
import com.maoyongxin.myapplication.http.response.GroupResponse;
import com.maoyongxin.myapplication.http.response.LoginResponse;
import com.maoyongxin.myapplication.permission.RxPermissions;
import com.maoyongxin.myapplication.tool.AndroidBug54971Workaround;
import com.maoyongxin.myapplication.tool.MyLocation;
import com.maoyongxin.myapplication.tool.PollingService;
import com.maoyongxin.myapplication.tool.PollingUtils;
import com.maoyongxin.myapplication.tool.SpUtils;
import com.maoyongxin.myapplication.tool.TagAliasOperatorHelper;
import com.maoyongxin.myapplication.ui.actadapter.MainPagerAdapter;
import com.maoyongxin.myapplication.ui.fgt.Fgt_Community;
import com.maoyongxin.myapplication.ui.fgt.Fgt_Connection;
import com.maoyongxin.myapplication.ui.fgt.Fgt_Message;
import com.maoyongxin.myapplication.ui.fgt.Fgt_Min;
import com.maoyongxin.myapplication.ui.fgt.message.act.Act_StrangerDetail;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.message.InformationNotificationMessage;
import okhttp3.Call;

import static com.maoyongxin.myapplication.tool.TagAliasOperatorHelper.ACTION_SET;
import static com.maoyongxin.myapplication.ui.fgt.connection.act.Act_ServicePublishing.subStr;

public class MainActivity extends BaseAct implements ViewPager.OnPageChangeListener {
    private List<BaseFgt> fgtData = new ArrayList<>();
    private ViewPager mViewPager;
    private MainPagerAdapter mainPagerAdapter;
    private TextView[] title = new TextView[4];
    private String picurl;
    private TextView msg_number;
    private LinearLayout mainbutton;
    @Override
    protected void handlerPassMsg(int target, int target1, Object obj) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  AndroidBug54971Workarou23
        // 0nd.assistActivity(findViewById(android.R.id.content));
    }

    @Override
    public int initLayoutId() {
        return R.layout.activity_main;

    }

    @Override
    protected void onResume() {
        super.onResume();
        getMsgNumber();
     //   refreshList();
        refreshMsg();
    }

    @Override
    public void initView() {
        msg_number = getView(R.id.msg_number);
        mViewPager = getView(R.id.main_viewpager);

        title[0] = getViewAndClick(R.id.tab_mesg);//消息
        title[1] = getViewAndClick(R.id.tab_community);//社区
        title[2] = getViewAndClick(R.id.tab_connection);//人脉
        title[3] = getViewAndClick(R.id.tab_min);//我的

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void initData() {
        setAlias();
        fgtData.add(new Fgt_Message());
        fgtData.add(new Fgt_Community());
        fgtData.add(new Fgt_Connection());
        fgtData.add(new Fgt_Min());


       // backdatatoserver();
        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), fgtData);
        mViewPager.setAdapter(mainPagerAdapter);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setOnPageChangeListener(this);
        title[0].setSelected(true);




        //判断是否第一次进入，true 则插入一条欢迎消息
        Boolean isFirst = SpUtils.getBoolean(this, "isFirst", true);
        if (isFirst) {
            RongIM.getInstance().getRongIMClient().insertMessage(Conversation.ConversationType.PRIVATE,
                    "10069",

                    null, InformationNotificationMessage.obtain("欢迎加入彼信商业社区"), null);
            isFirst = false;
            SpUtils.putBoolean(this, "isFirst", false);
        }
        setRongUserInfo(getIntent().getStringExtra("userId"),
                MyApplication.getCurrentUserInfo().getUserName() + "",
                Uri.parse(MyApplication.getCurrentUserInfo().getHeadImg() + ""));

        RongIM.setConversationClickListener(new RongIM.ConversationClickListener() {
            @Override
            public boolean onUserPortraitClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo, String s) {
                getMsgNumber();

                refreshMsg();
                return false;
            }

            @Override
            public boolean onUserPortraitLongClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo, String s) {
                return false;
            }

            @Override
            public boolean onMessageClick(Context context, View view, Message message) {
                return false;
            }

            @Override
            public boolean onMessageLinkClick(Context context, String s, Message message) {
                return false;
            }

            @Override
            public boolean onMessageLongClick(Context context, View view, Message message) {
                return false;
            }
        });

        RongIM.setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageListener() {
            @Override
            public boolean onReceived(Message message, int i) {
                getMsgNumber();
                refreshList();
                refreshMsg();
                return false;
            }
            public void refreshList() {
                RongIM.getInstance().getConversationList(new RongIMClient.ResultCallback<List<Conversation>>() {
                    @Override
                    public void onSuccess(final List<Conversation> conversations) {
                        if (conversations == null) {
                            return;
                        }
                        for (int i = 0; i < conversations.size(); i++) {
                            if (conversations.get(i).getConversationType() == Conversation.ConversationType.PRIVATE) {
                                ReqFindUserById.findUser(context, getActivity().getClass().getSimpleName(),
                                        conversations.get(i).getTargetId(), new EntityCallBack<LoginResponse>() {
                                            @Override
                                            public Class<LoginResponse> getEntityClass() {
                                                return LoginResponse.class;
                                            }

                                            @Override
                                            public void onSuccess(LoginResponse resp) {

                                                String id = resp.obj.getUserId();
                                                setRongUserInfo(
                                                        id, resp.obj.getUserName(),
                                                        Uri.parse(resp.obj.getHeadImg()));

                                                RongIM.getInstance().refreshUserInfoCache(new UserInfo(id, resp.obj.getUserName(),
                                                        Uri.parse(resp.obj.getHeadImg())));

                                                RongIM.getInstance().setMessageAttachedUserInfo(true);
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
                            } else if (conversations.get(i).getConversationType() == Conversation.ConversationType.GROUP) {
                                ReqGroup.getGroupInfo(getActivity(),
                                        getActivityTag(),
                                        conversations.get(i).getTargetId(),
                                        new EntityCallBack<GroupResponse>() {
                                            @Override
                                            public Class<GroupResponse> getEntityClass() {
                                                return GroupResponse.class;
                                            }

                                            @Override
                                            public void onSuccess(GroupResponse resp) {
                                                if (resp.is200()) {
                                                    String id = resp.obj.getGroupId() + "";

                                                    String groupName = resp.obj.getGroupId() + "";
                                                    getgroupurl(id, groupName);
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

                        }
                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {

                    }
                });
            }
        });

        RongIM.setConversationBehaviorListener(new RongIM.ConversationBehaviorListener() {
            @Override
            public boolean onUserPortraitClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo) {
                Intent in = new Intent(context, Act_StrangerDetail.class);
                in.putExtra("personId", userInfo.getUserId());
                context.startActivity(in);
                return false;
            }

            @Override
            public boolean onUserPortraitLongClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo) {
                return false;
            }

            @Override
            public boolean onMessageClick(Context context, View view, Message message) {
                return false;
            }

            @Override
            public boolean onMessageLinkClick(Context context, String s) {
                return false;
            }

            @Override
            public boolean onMessageLongClick(Context context, View view, Message message) {
                return false;
            }
        });




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
            case R.id.tab_mesg://消息
                mViewPager.setCurrentItem(0, false);
                break;
            case R.id.tab_community://社区
                mViewPager.setCurrentItem(1, false);
                break;
            case R.id.tab_connection://人脉
                mViewPager.setCurrentItem(2, false);
                break;
            case R.id.tab_min://我的
                mViewPager.setCurrentItem(3, false);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setCurrent(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public int currentTabIndex = 0;

    public void setCurrent(int indext) {
        title[currentTabIndex].setSelected(false);
        title[indext].setSelected(true);
        currentTabIndex = indext;
    }

    //设置融云用户信息
    private void setRongUserInfo(final String id, final String name, final Uri uri) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (RongIM.getInstance() != null) {
                    RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
                        @Override
                        public UserInfo getUserInfo(String s) {

                            return new UserInfo(id, name, uri);
                        }
                    }, true);
                }
            }
        }).start();

    }

    private void getgroupurl(final String groupId, final String groipName) {
        OkHttpUtils.post().url("http://st.3dgogo.com/index/chatgroup_gambit/get_chatgroup_image.html")
                .addParams("group_id", groupId)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {

                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getInt("code") == 200) {

                        picurl = jsonObject.getString("image");
                        setRongGroupInfo(groupId,
                                groipName,
                                Uri.parse(picurl)
                        );
                        RongIM.getInstance().refreshGroupInfoCache(new Group(groupId, groipName, Uri.parse(picurl)));

                    } else if (jsonObject.getInt("code") == 500) {

                        Toast.makeText(getActivity(), "抱歉，修改失败！请再次提交", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void setRongGroupInfo(final String groupId, final String groupName, final Uri uri) {
        if (RongIM.getInstance() != null) {
            RongIM.setGroupInfoProvider(new RongIM.GroupInfoProvider() {
                @Override
                public Group getGroupInfo(String s) {
                    return new Group(groupId, groupName, uri);
                }
            }, true);
        }
    }

    private void getMsgNumber() {
        RongIMClient.getInstance().getTotalUnreadCount(new RongIMClient.ResultCallback<Integer>() {
            @Override
            public void onSuccess(Integer integer) {
                Log.i("wei", integer + "");
                if (integer == 0) {
                    msg_number.setVisibility(View.GONE);
                } else if (integer > 99) {
                    msg_number.setVisibility(View.VISIBLE);
                    msg_number.setText("99");
                } else {
                    msg_number.setVisibility(View.VISIBLE);
                    msg_number.setText(String.valueOf(integer));
                }

            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
    }


    public void refreshMsg() {
        RongIM.getInstance().clearMessages(Conversation.ConversationType.NONE, this.getClass()
                .getSimpleName());
        RongIM.getInstance().removeConversation(Conversation.ConversationType.NONE, this.getClass().getSimpleName());
    }

    private static int sequence;

    //使用Webview的时候，返回键没有重写的时候会直接关闭程序，这时候其实我们要其执行的知识回退到上一步的操作
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //这是一个监听用的按键的方法，keyCode 监听用户的动作，如果是按了返回键，同时Webview要返回的话，WebView执行回退操作，因为mWebView.canGoBack()返回的是一个Boolean类型，所以我们把它返回为true
        if (ComantUtils.myFinsh) {
            finish();
        } else {
            MyToast.show(context, "您确定要退出App吗？");
            ComantUtils.myFinsh = true;
            return true;
        }
        return true;
    }

    // 设置 tags
    Set<String> tags = new HashSet<>();
    private void setAlias() {
        tags.add(MyApplication.getCurrentUserInfo().getUserId());
        try{
            tags.add( subStr(MyApplication.getMyCurrentLocation().getAdCode(), 2) + "0000");
            tags.add( subStr(MyApplication.getMyCurrentLocation().getAdCode(), 4) + "00");
            tags.add(MyApplication.getMyCurrentLocation().getAdCode());
        }
      catch (Exception e)
      {
          e.printStackTrace();
          tags.add( "510000");
          tags.add( "510100");
          tags.add("510117");
      }

        Debug.e("---------------alias==" +   MyApplication.getCurrentUserInfo().getUserId());
        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
        tagAliasBean.action = ACTION_SET;
        tagAliasBean.tags = tags;
        sequence++;
        tagAliasBean.alias = MyApplication.getCurrentUserInfo().getUserId();
        tagAliasBean.isAliasAction = true;
        TagAliasOperatorHelper.getInstance().handleAction(context.getApplicationContext(), sequence, tagAliasBean);
        ReqUserServer.uploadBaiduPushInfo(this,
                "id",
                Long.parseLong(MyApplication.getCurrentUserInfo().getUserId()),
                JPushInterface.getRegistrationID(this),
                AppConfig.DEVICE_TYPE, new EntityCallBack<BaiduPushResponse>() {
                    @Override
                    public Class<BaiduPushResponse> getEntityClass() {
                        return BaiduPushResponse.class;
                    }

                    @Override
                    public void onSuccess(BaiduPushResponse resp) {
                        Log.d("---", "success");
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

    private void backdatatoserver() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpUtils.post().url("http://st.3dgogo.com/index/action_log/setActionLogApi.html")
                        .addParams("action_uid", MyApplication.getCurrentUserInfo().getUserId())
                        .addParams("action_user_name", MyApplication.getCurrentUserInfo().getUserName())
                        .addParams("action_type", "1")
                        .addParams("action_remarks", "APP内登陆")
                        .build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(String response, int id) {

                    }
                });

            }
        }).start();
    }



}
