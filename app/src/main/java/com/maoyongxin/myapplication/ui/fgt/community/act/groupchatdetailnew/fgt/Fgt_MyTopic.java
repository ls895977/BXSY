package com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.fgt;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lykj.aextreme.afinal.utils.Debug;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.common.MyApplication;
import com.maoyongxin.myapplication.http.entity.HuatiInfo1;
import com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.bean.MyTopicBean;
import com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.fgt.adapter.Fgt_MyTopicAdapter;
import com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.fgt.bean.UserEvent;
import com.maoyongxin.myapplication.ui.fgt.community.bean.DynamicHaderBean;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.jiguang.share.android.api.JShareInterface;
import cn.jiguang.share.wechat.WechatMoments;
import io.rong.eventbus.EventBus;
import okhttp3.Call;

/**
 * 社区=热门社区=我的
 */
public class Fgt_MyTopic extends HeaderViewPagerFragment implements BaseQuickAdapter.OnItemChildClickListener {
    private RecyclerView my_huati;
    private SmartRefreshLayout mRefreshLayout;
    DynamicHaderBean.InfoBean.HotChatgroupBean bean;
    private RelativeLayout noViewData;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fgt_mytopic, container, false);
        initView(view);
        initData();
        return view;
    }

    private int page = 1;

    public void initView(View view) {
        EventBus.getDefault().register(this);
        noViewData = view.findViewById(R.id.view_not);
        bean = (DynamicHaderBean.InfoBean.HotChatgroupBean) getActivity().getIntent().getSerializableExtra("bean");
        my_huati = view.findViewById(R.id.my_huati);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        my_huati.setLayoutManager(mLinearLayoutManager);
        mRefreshLayout = view.findViewById(R.id.refreshLayout);
        //内容跟随偏移
        mRefreshLayout.setEnableHeaderTranslationContent(true);
        //设置 Header 为 Material风格
        mRefreshLayout.setRefreshHeader(new MaterialHeader(getContext()).setShowBezierWave(false));
        //设置 Footer 为 球脉冲
        mRefreshLayout.setRefreshFooter(new BallPulseFooter(getContext()).setSpinnerStyle(SpinnerStyle.Scale));
        mRefreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                if (getActivity().getIntent().getStringExtra("title") != null) {
                    bean = (DynamicHaderBean.InfoBean.HotChatgroupBean) getActivity().getIntent().getSerializableExtra("bean");
                    getHuattiList(bean.getGroupId());
                } else {
                    getHuattiList(getActivity().getIntent().getStringExtra("groupId"));
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 0;
                datas.clear();
                if (getActivity().getIntent().getStringExtra("title") != null) {
                    bean = (DynamicHaderBean.InfoBean.HotChatgroupBean) getActivity().getIntent().getSerializableExtra("bean");
                    getHuattiList(bean.getGroupId());
                } else {
                    getHuattiList(getActivity().getIntent().getStringExtra("groupId"));
                }
            }
        });
    }

    public void initData() {
        if (getActivity().getIntent().getStringExtra("title") != null) {
            bean = (DynamicHaderBean.InfoBean.HotChatgroupBean) getActivity().getIntent().getSerializableExtra("bean");
            getHuattiList(bean.getGroupId());
        } else {
            getHuattiList(getActivity().getIntent().getStringExtra("groupId"));
        }
    }

    @Override
    public View getScrollableView() {
        return my_huati;
    }

    Fgt_MyTopicAdapter adapter;
    Gson gson = new Gson();
    List<MyTopicBean.InfoBean.DataBean> datas = new ArrayList<>();

    private void getHuattiList(String GroupId) {
        OkHttpUtils.post()
                .addParams("page", page + "")
                .addParams("group_id", GroupId)
                .addParams("uid", MyApplication.getCurrentUserInfo().getUserId())
                .url("http://st.3dgogo.com/index/chatgroup_gambit/getUidGambitList/").build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        noViewData.setVisibility(View.VISIBLE);
                        mRefreshLayout.setVisibility(View.GONE);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MyTopicBean huatiInfo1 = gson.fromJson(response, MyTopicBean.class);
                        if (huatiInfo1.getCode() != 200) {
                            return;
                        }
                        for (int i = 0; i < huatiInfo1.getInfo().getData().size(); i++) {
                            datas.add(huatiInfo1.getInfo().getData().get(i));
                        }
                        if (adapter == null) {

                            String groupname;
                            if (bean == null) {
                                groupname = getActivity().getIntent().getStringExtra("groupName");
                            } else {
                                groupname = bean.getGroupName();
                            }
                            adapter = new Fgt_MyTopicAdapter(getContext(), groupname);
                            adapter.setOnItemChildClickListener(Fgt_MyTopic.this);
                            adapter.setNewData(datas);
                            my_huati.setAdapter(adapter);
                        } else {
                            adapter.notifyDataSetChanged();
                        }
                        if (datas.size() == 0) {
                            noViewData.setVisibility(View.VISIBLE);
                            mRefreshLayout.setVisibility(View.GONE);
                        }
                    }
                });
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
        switch (view.getId()) {
            case R.id.img_delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("提示").setMessage("你确定要删除这条动态么？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteMyDynamic(datas.get(position).getId(), position);
                            }
                        }).setNegativeButton("取消", null).show();
                break;
            case R.id.img_share://分享
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setTitle("分享").setIcon(R.mipmap.wechat_moment)
                        .setPositiveButton("朋友圈", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getContext(), "正在准备分享...", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("微信好友", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getContext(), "正在准备分享...", Toast.LENGTH_SHORT).show();

                    }
                });
                break;
        }
    }

    private void deleteMyDynamic(final String huatiId, final int position) {
        OkHttpUtils.post().url("http://st.3dgogo.com/index/chatgroup_gambit/delete_chatgroup_gambit")
                .addParams("id", huatiId)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(String response, int id) {
                Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
                datas.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 订阅的过程中，默认是在主线程中用到的
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UserEvent event) {
        if (event.getType().equals("10") && event.getSatus().equals("10")) {
            page = 1;
            datas.clear();
            getHuattiList(bean.getGroupId());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //注销注册
        EventBus.getDefault().unregister(this);
    }
}
