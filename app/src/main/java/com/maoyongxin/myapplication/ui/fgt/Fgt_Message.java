package com.maoyongxin.myapplication.ui.fgt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lykj.aextreme.afinal.utils.Debug;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.bean.ChatGroupBean;
import com.maoyongxin.myapplication.bean.SealConst;
import com.maoyongxin.myapplication.bean.SealSearchConversationResult;
import com.maoyongxin.myapplication.common.BaseFgt;
import com.maoyongxin.myapplication.common.MyApplication;
import com.maoyongxin.myapplication.db.DBManager;
import com.maoyongxin.myapplication.db.Friend;
import com.maoyongxin.myapplication.db.FriendDao;
import com.maoyongxin.myapplication.db.Groups;
import com.maoyongxin.myapplication.db.GroupsDao;
import com.maoyongxin.myapplication.http.callback.EntityCallBack;
import com.maoyongxin.myapplication.http.entity.RequestListInfo;
import com.maoyongxin.myapplication.http.request.ReqFindUserById;
import com.maoyongxin.myapplication.http.request.ReqGetRequestList;
import com.maoyongxin.myapplication.http.request.ReqGroup;
import com.maoyongxin.myapplication.http.response.GroupResponse;
import com.maoyongxin.myapplication.http.response.LoginResponse;
import com.maoyongxin.myapplication.http.response.RequestListResponse;
import com.maoyongxin.myapplication.tool.CommonUtils;
import com.maoyongxin.myapplication.ui.fgt.message.Popu.MorePopWindow;
import com.maoyongxin.myapplication.ui.fgt.message.act.Act_FriendMessages;
import com.maoyongxin.myapplication.ui.fgt.message.act.Act_MyFriends;
import com.maoyongxin.myapplication.ui.fgt.message.adapter.ConversationListAdapterEx;
import com.maoyongxin.myapplication.ui.fgt.message.adapter.MessageAdapter;
import com.maoyongxin.myapplication.view.SelectableRoundedImageView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.rong.imageloader.core.ImageLoader;
import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imkit.model.UIConversation;
import io.rong.imkit.userInfoCache.RongUserInfoManager;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.SearchConversationResult;
import io.rong.imlib.model.UserInfo;
import io.rong.message.InformationNotificationMessage;
import okhttp3.Call;

/**
 * 消息
 */
public class Fgt_Message extends BaseFgt implements BaseQuickAdapter.OnItemClickListener {
    private TextView btn_conversation_list;
    private ViewPager vpViewPager;
    private ImageView img_Search;
    private List<TextView> btnList;
    private List<Fragment> fragList;
    private int prePosition = 0;
    private Uri headUri;
    private ImageView nameBook;
    private boolean isDebug;
    private EditText input;
    private RecyclerView messageRecylcerview;
    /**
     * 会话列表的fragment
     */
    private ConversationListFragment mConversationListFragment = null;
    private Conversation.ConversationType[] mConversationsTypes = null;//第一界面
    private List<SearchConversationResult> mSearchConversationResultsList;
    private ArrayList<SearchConversationResult> mSearchConversationResultsArrayList;

    @Override
    public int initLayoutId() {
        return R.layout.fgt_message;
    }

    private String mFilterString;
    private MessageAdapter adapter;

    @Override
    public void initView() {
        hideHeader();
        messageRecylcerview = getView(R.id.message_recyclerView);
        input = getViewAndClick(R.id.messagpe_input);
        input.setCursorVisible(false);
        btn_conversation_list = getView(R.id.btn_conversation_list);
        nameBook = getView(R.id.nameBook);
        img_Search = getView(R.id.img_search);
        img_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MorePopWindow morePopWindow = new MorePopWindow(getActivity());
                morePopWindow.showPopupWindow(img_Search);
            }
        });
        vpViewPager = getView(R.id.vp_myviewPager);
        nameBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Act_MyFriends.class);
                startActivity(intent);
            }
        });
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //                RongIMClient.getInstance().searchMessages( Conversation.ConversationType.PRIVATE,
//                        "", mFilterString, 0, 0, new RongIMClient.ResultCallback<List<Message>>() {
//                            @Override
//                            public void onSuccess(List<Message> messages) {
//                                Debug.e("----------onSuccess==="+messages.size());
//                            }
//
//                            @Override
//                            public void onError(RongIMClient.ErrorCode errorCode) {
//                                Debug.e("----------onError==="+errorCode.getMessage());
//                            }
//                        });
            }

            @Override
            public void afterTextChanged(Editable s) {
                /*
             mFilterString = s.toString();
                mSearchConversationResultsList = new ArrayList<>();
              mSearchConversationResultsArrayList = new ArrayList<>();
                /**
                 * 搜索聊天内容
                 */
                /*
              if (mFilterString.length() == 0) {
                    vpViewPager.setVisibility(View.VISIBLE);
                  messageRecylcerview.setVisibility(View.GONE);
                    return;
                }
                vpViewPager.setVisibility(View.GONE);
                messageRecylcerview.setVisibility(View.VISIBLE);
                messageRecylcerview.setLayoutManager(new LinearLayoutManager(context));
                RongIMClient.getInstance().searchConversations(mFilterString,
                        new Conversation.ConversationType[]{Conversation.ConversationType.PRIVATE, Conversation.ConversationType.GROUP},
                        new String[]{"RC:TxtMsg", "RC:ImgTextMsg", "RC:FileMsg"}, new RongIMClient.ResultCallback<List<SearchConversationResult>>() {
                            @Override
                            public void onSuccess(List<SearchConversationResult> searchConversationResults) {
                                mSearchConversationResultsList = searchConversationResults;
                                mSearchConversationResultsArrayList.clear();
                                for (SearchConversationResult result : searchConversationResults) {
                                    mSearchConversationResultsArrayList.add(result);
                                }

                                if (adapter == null) {
                                    adapter = new MessageAdapter(mSearchConversationResultsArrayList, getContext());
                                    adapter.setOnItemClickListener(Fgt_Message.this);
                                    messageRecylcerview.setAdapter(adapter);
                                } else {
                                    adapter.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onError(RongIMClient.ErrorCode e) {
                            }
                        });
                */
            }
        });
    }

    @Override
    public void initData() {
        headUri = Uri.parse(MyApplication.getCurrentUserInfo().getHeadImg());
        btnList = new ArrayList<>();
        btnList.add(btn_conversation_list);
        fragList = new ArrayList<>();
        Fragment conversationList = initConversationList();
        fragList.add(conversationList);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    refreshSystemInfo();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        timer.schedule(task, 5000, 5000);
        shownewFragment();
        listItemClick();
    }

    private void listItemClick() {
        RongIM.setConversationListBehaviorListener(new MyConversationListBehaviorListener());
    }

    @Override
    public void onResume() {
        super.onResume();


        refreshList();
    }

    /**
     * 刷新当前消息列表头像
     */
    private void refreshList() {
        RongIM.getInstance().getConversationList(new RongIMClient.ResultCallback<List<Conversation>>() {
            @Override
            public void onSuccess(final List<Conversation> conversations) {
                if (conversations == null) {
                    return;
                }
                for (int i = 0; i < conversations.size(); i++) {
                    if (conversations.get(i).getConversationType() == Conversation.ConversationType.PRIVATE) {
                        ReqFindUserById.findUser(getActivity(), getActivity().getClass().getSimpleName(),
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
                                getActivity().getClass().getSimpleName(),
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
                                            String groupName = resp.obj.getGroupName() + "";    ///TODO
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
        refreshSystemInfo();
    }

    //设置融云用户信息
    private void setRongUserInfo(final String id, final String name, final Uri uri) {
        if (RongIM.getInstance() != null) {
            RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
                @Override
                public UserInfo getUserInfo(String s) {

                    return new UserInfo(id, name, uri);
                }
            }, true);
        }
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
            case R.id.messagpe_input:
                input.setCursorVisible(true);
                break;
        }
    }

    @Override
    public void sendMsg(int flag, Object obj) {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        RongIM.getInstance().startPrivateChat(getContext(), mSearchConversationResultsArrayList.get(position).getConversation().getSenderUserId(), mSearchConversationResultsArrayList.get(position).getConversation().getConversationTitle());
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private List<Fragment> list;

        public ViewPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.list = list;
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(0);

        }

        @Override
        public int getCount() {
            return list.size();
        }
    }

    /**
     * 展示fragment
     */
    private void shownewFragment() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        ViewPagerAdapter vpAdapter = new ViewPagerAdapter(fm, fragList);
        vpAdapter.notifyDataSetChanged();
        vpViewPager.setAdapter(vpAdapter);
        /**
         * 指示器联动
         */
        vpViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                btnList.get(prePosition).setTextColor(Color.parseColor("#565656"));
                btnList.get(position).setTextColor(Color.parseColor("#999999"));
                prePosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class MyConversationListBehaviorListener implements RongIM.ConversationListBehaviorListener {
        /**
         * 当点击会话头像后执行。
         *
         * @param context
         * @param conversationType
         * @param s
         * @return
         */
        @Override
        public boolean onConversationPortraitClick(Context context, Conversation.ConversationType conversationType, String s) {
            return true;
        }

        @Override
        public boolean onConversationPortraitLongClick(Context context, Conversation.ConversationType conversationType, String s) {
            return true;
        }

        @Override
        public boolean onConversationLongClick(Context context, View view, UIConversation uiConversation) {
            return true;
        }

        /**
         * 点击会话列表中的 item 时执行。
         *
         * @param context
         * @param view
         * @param uiConversation
         * @return
         */
        @Override
        public boolean onConversationClick(Context context, View view, UIConversation uiConversation) {
            Conversation.ConversationType type = uiConversation.getConversationType();
            if (type == Conversation.ConversationType.SYSTEM) {
                context.startActivity(new Intent(context, Act_FriendMessages.class));
                return true;
            }
            return false;
        }
    }

    private void refreshSystemInfo() {
        ReqGetRequestList.getList(getContext(), getTag(), MyApplication.getCurrentUserInfo().getUserId(), new EntityCallBack<RequestListResponse>() {
            @Override
            public Class<RequestListResponse> getEntityClass() {
                return RequestListResponse.class;
            }

            @Override
            public void onSuccess(RequestListResponse resp) {
                if (resp.is200()) {
                    List<RequestListInfo.RequestList> requestListInfos;
                    requestListInfos = resp.obj.getRequestList();
                    if (requestListInfos.size() != 0) {
                        for (int i = 0; i < requestListInfos.size(); i++) {
                            if (!MyApplication.getCurrentUserInfo().getUserId().equals(requestListInfos.get(i).getUserId())) {
                                if (requestListInfos.get(i).getRequestState().equals("1")) {
                                    RongIM.getInstance().getRongIMClient().insertMessage(Conversation.ConversationType.SYSTEM,
                                            requestListInfos.get(0).getUserId(),
                                            null, InformationNotificationMessage.obtain("好友申请"),
                                            null);
                                }
                            }

                        }
                    }
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


    private Fragment initConversationList() {
        if (mConversationListFragment == null) {
            ConversationListFragment listFragment = new ConversationListFragment();
            ConversationListAdapterEx adapterEx = new ConversationListAdapterEx(RongContext.getInstance());
            listFragment.setAdapter(adapterEx);
            Uri uri;
            if (isDebug) {
                uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                        .appendPath("conversationlist")
                        .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "true") //设置私聊会话是否聚合显示
                        .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")//群组
                        .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")//公共服务号
                        .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")//订阅号
                        .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")//系统
                        .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "true")
                        .build();
                mConversationsTypes = new Conversation.ConversationType[]{Conversation.ConversationType.PRIVATE,
                        Conversation.ConversationType.GROUP,
                        Conversation.ConversationType.PUBLIC_SERVICE,
                        Conversation.ConversationType.APP_PUBLIC_SERVICE,
                        Conversation.ConversationType.SYSTEM,
                        Conversation.ConversationType.DISCUSSION
                };

            } else {
                uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                        .appendPath("conversationlist")
                        .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
                        .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//群组
                        .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")//公共服务号
                        .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")//订阅号
                        .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")//系统
                        .build();
                mConversationsTypes = new Conversation.ConversationType[]{Conversation.ConversationType.PRIVATE,
                        Conversation.ConversationType.GROUP,
                        Conversation.ConversationType.PUBLIC_SERVICE,
                        Conversation.ConversationType.APP_PUBLIC_SERVICE,
                        Conversation.ConversationType.SYSTEM

                };
            }
            listFragment.setUri(uri);
            mConversationListFragment = listFragment;
            return listFragment;
        } else {
            return mConversationListFragment;
        }
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
                Gson gson = new Gson();
                ChatGroupBean bean = gson.fromJson(response, ChatGroupBean.class);
                if (bean.getCode() == 200) {
                    String url = "";
                    try {
                        url = bean.getImage();
                        setRongGroupInfo(groupId,
                                groipName,
                                Uri.parse(url)
                        );
                        RongIM.getInstance().refreshGroupInfoCache(new Group(groupId, groipName, Uri.parse(url)));
                    } catch (Exception e) {
                        url = "";
                    }


                } else if (bean.getCode() == 500) {
                    Toast.makeText(getActivity(), "抱歉，修改失败！请再次提交", Toast.LENGTH_SHORT).show();
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

}
