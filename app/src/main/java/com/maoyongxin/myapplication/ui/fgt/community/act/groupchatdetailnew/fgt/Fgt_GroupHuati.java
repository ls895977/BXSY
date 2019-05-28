package com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.fgt;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lykj.aextreme.afinal.utils.Debug;
import com.lykj.aextreme.afinal.utils.MyToast;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.bean.MessageBean;
import com.maoyongxin.myapplication.common.BaseFgt;
import com.maoyongxin.myapplication.common.MyApplication;
import com.maoyongxin.myapplication.http.entity.HuatiInfo1;
import com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.base.GroupHuatiBase;
import com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.fgt.act.Act_TopicDetails;
import com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.fgt.adapter.GroupHuatiAdapter;
import com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.fgt.bean.TopicDetailsDianZanBean;
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
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.rong.eventbus.EventBus;
import okhttp3.Call;

/**
 * 热门话题
 */
public class Fgt_GroupHuati extends HeaderViewPagerFragment implements BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemClickListener {
    private RecyclerView lv_huati;
    DynamicHaderBean.InfoBean.HotChatgroupBean bean;
    private SmartRefreshLayout mRefreshLayout;
    private RelativeLayout noViewLayout;
    private Boolean nodata=true;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fgt_grouphuati, container, false);
        initView(view);
        initData();
        return view;
    }

    private ZLoadingDialog dialog;

    public void initView(View view) {
        EventBus.getDefault().register(this);
        noViewLayout = view.findViewById(R.id.view_not);
        dialog = new ZLoadingDialog(getContext());
        dialog.setLoadingBuilder(Z_TYPE.ROTATE_CIRCLE)//设置类型
                .setLoadingColor(Color.DKGRAY)//颜色
                .setHintText("数据提交中...")
                .setHintTextSize(16) // 设置字体大小 dp
                .setHintTextColor(Color.DKGRAY)  // 设置字体颜色
                .setDurationTime(0.5) // 设置动画时间百分比 - 0.5倍
                .setDialogBackgroundColor(Color.parseColor("#CCffffff")); // 设置背景色，默认白色
        lv_huati = view.findViewById(R.id.lv_huati);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        lv_huati.setLayoutManager(mLinearLayoutManager);
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
                page = 1;
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

    private GroupHuatiAdapter adapter;
    int page = 1;
    Gson gson = new Gson();

    private void getHuattiList(String GroupId) {
        OkHttpUtils.post()
                .addParams("page", page + "")
                .addParams("myuid", MyApplication.getCurrentUserInfo().getUserId())
                .addParams("group_id", GroupId + "")
                .url("http://st.3dgogo.com/index/chatgroup_gambit/get_gambit")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        mRefreshLayout.finishLoadMore();
                        mRefreshLayout.finishRefresh();
                        noViewLayout.setVisibility(View.VISIBLE);
                        mRefreshLayout.setVisibility(View.GONE);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        HuatiInfo1 huatiInfo1 = gson.fromJson(response, HuatiInfo1.class);
                        if (huatiInfo1.getCode() != 200) {
                            return;
                        }
                        setAdapter(huatiInfo1);
                        mRefreshLayout.finishLoadMore();
                        mRefreshLayout.finishRefresh();
                    }
                });
    }

    List<GroupHuatiBase> datas = new ArrayList<>();

    @Override
    public View getScrollableView() {
        return lv_huati;
    }

    GroupHuatiBase base;

    public void setAdapter(HuatiInfo1 info1) {
        for (int i = 0; i < info1.getInfo().getData().size(); i++) {
            if (info1.getInfo().getData().get(i).getIs_top().equals("1")) {
                base = new GroupHuatiBase(GroupHuatiBase.THREE_ZHIDING);
            } else {
                base = new GroupHuatiBase(GroupHuatiBase.LEFT_BIG);
            }
            base.setPraise(info1.getInfo().isPraise());
            base.setId(info1.getInfo().getData().get(i).getId());
            base.setUid(info1.getInfo().getData().get(i).getUid());
            base.setTitle(info1.getInfo().getData().get(i).getTitle());
            base.setContent(info1.getInfo().getData().get(i).getContent());
            base.setPost_num(info1.getInfo().getData().get(i).getPost_num());
            base.setCreate_time(info1.getInfo().getData().get(i).getCreate_time());
            base.setImg(info1.getInfo().getData().get(i).getImg());
            base.setPraise_num(info1.getInfo().getData().get(i).getPraise_num());
            base.setTread_num(info1.getInfo().getData().get(i).getTread_num());
            base.setIs_top(info1.getInfo().getData().get(i).getIs_top());
            base.setUserName(info1.getInfo().getData().get(i).getUserName());
            base.setHeadImg(info1.getInfo().getData().get(i).getHeadImg());
            base.setPraise(info1.getInfo().getData().get(i).isPraise());
            datas.add(base);
            nodata=false;
        }
        if (adapter == null) {
            String groupname;
            if (bean == null) {
                groupname = getActivity().getIntent().getStringExtra("groupName");
            } else {
                groupname = bean.getGroupName();
            }
            adapter = new GroupHuatiAdapter(datas, getContext(), groupname);
            adapter.setOnItemClickListener(this);
            adapter.setOnItemChildClickListener(this);
            lv_huati.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
        if (datas.size() == 0&&nodata) {
            noViewLayout.setVisibility(View.VISIBLE);
            mRefreshLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
        switch (view.getId()) {
            case R.id.img_delete://删除
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("提示").setMessage("你确定要删除这条动态么？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteMyDynamic(datas.get(position).getId(), position);
                            }
                        }).setNegativeButton("取消", null).show();
                break;
            case R.id.zanImg://点赞
                view.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.zan_anim));
                if (datas.get(position).isPraise()) {
                    CancelLikeApi(position);
                } else {
                    setLikeApi(position);
                }
                break;

        }
    }

    /**
     * 删除自己发过的记录
     *
     * @param huatiId
     * @param position
     */
    private void deleteMyDynamic(final String huatiId, final int position) {
        OkHttpUtils.post().url("http://st.3dgogo.com/index/chatgroup_gambit/delete_chatgroup_gambit")
                .addParams("id", huatiId)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                getActivity().setResult(30);
                Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
                datas.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent();
        intent.putExtra("id", datas.get(position).getId());
        intent.putExtra("groupName", bean.getGroupName());
        intent.putExtra("Groupid", bean.getGroupId() + "");
        intent.putExtra("parentUserId", datas.get(position).getUid() + "");
        intent.setClass(getContext(), Act_TopicDetails.class);
        startActivity(intent);
    }

    /**
     * 订阅的过程中，默认是在主线程中用到的
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UserEvent event) {
        if (event.getType().equals("10") && event.getSatus().equals("10")) {
            page = 1;
            datas.clear();
            getActivity().setResult(30);
            getHuattiList(bean.getGroupId());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //注销注册
        EventBus.getDefault().unregister(this);
    }

    /**
     * 取消点赞
     */
    private void CancelLikeApi(final int potiong) {
        dialog.show();
        OkHttpUtils.post()
                .addParams("user_id", MyApplication.getCurrentUserInfo().getUserId() + "")
                .addParams("data_id", datas.get(potiong).getId())
                .addParams("data_type", "3")
                .url("http://bisonchat.com/index/praise/setCancelLikeApi.html").build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        dialog.dismiss();
                        Gson gson = new Gson();
                        TopicDetailsDianZanBean bean = gson.fromJson(response, TopicDetailsDianZanBean.class);
                        if (bean.isOperation()) {
                            datas.get(potiong).setPraise(false);
                            datas.get(potiong).setPraise_num(String.valueOf(Integer.valueOf(datas.get(potiong).getPraise_num()) - 1));
                            adapter.notifyDataSetChanged();
                            MyToast.show(getActivity(), bean.getMsg());
                        }
                    }
                });
    }

    /**
     * 点赞
     */
    private void setLikeApi(final int potiong) {
        dialog.show();
        OkHttpUtils.post()
                .addParams("user_id", MyApplication.getCurrentUserInfo().getUserId() + "")
                .addParams("data_id", datas.get(potiong).getId())
                .addParams("data_type", "3")
                .addParams("type", "1")
                .url("http://bisonchat.com/index/praise/setLikeApi.html").build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        dialog.dismiss();
                        Gson gson = new Gson();
                        TopicDetailsDianZanBean bean = gson.fromJson(response, TopicDetailsDianZanBean.class);
                        if (bean.isOperation()) {
                            postPushMessage(datas.get(potiong).getUid());
                            datas.get(potiong).setPraise(true);
                            datas.get(potiong).setPraise_num(String.valueOf(Integer.valueOf(datas.get(potiong).getPraise_num()) + 1));
                            adapter.notifyDataSetChanged();
                            MyToast.show(getActivity(), bean.getMsg());
                        }
                    }
                });
    }

    /**
     * 推送消息
     * alias 指定推送人id
     * content推送消息内容
     */
    public void postPushMessage(String UserId) {
        Debug.e("--------------UserId===" + UserId);
        MessageBean bean = new MessageBean();
        bean.setTitle("点赞消息！");
//        bean.setTitle("回复信息");
        Gson gson = new Gson();
        String android_notification = gson.toJson(bean);
        OkHttpUtils.get().url("http://bisonchat.com/index/jpush/aliasPushApi")
                .addParams("alias", UserId)
                .addParams("content", "您的好友：" + MyApplication.getCurrentUserInfo().getUserName() + "为您点了一个'赞'")
//                .addParams("content", "您的好友：" + MyApplication.getCurrentUserInfo().getUserName() + "为您回复了一个消息")
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
