package com.maoyongxin.myapplication.ui.fgt.message.act.strangerdetail.fgt;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.jky.baselibrary.util.common.TimeUtil;
import com.lykj.aextreme.afinal.utils.Debug;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.common.BaseFgt;
import com.maoyongxin.myapplication.common.ComantUtils;
import com.maoyongxin.myapplication.common.MyApplication;
import com.maoyongxin.myapplication.http.callback.EntityCallBack;
import com.maoyongxin.myapplication.http.entity.HuatiInfo1;
import com.maoyongxin.myapplication.http.request.ReqCommunity;
import com.maoyongxin.myapplication.http.response.CommunityUsersResponse;
import com.maoyongxin.myapplication.http.response.MyCommunityResponse;
import com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.base.GroupHuatiBase;
import com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.fgt.act.Act_TopicDetails;
import com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.fgt.adapter.GroupHuatiAdapter;
import com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.fgt.fgt.HeaderViewPagerFragment;
import com.maoyongxin.myapplication.ui.fgt.community.bean.DynamicBase;
import com.maoyongxin.myapplication.ui.fgt.community.bean.DynamicBean;
import com.maoyongxin.myapplication.ui.fgt.message.act.Act_StrangerDetail;
import com.maoyongxin.myapplication.ui.fgt.message.act.bean.UidGambitListBean;
import com.maoyongxin.myapplication.ui.fgt.message.act.strangerdetail.fgt.adapter.CommunityPersonListAdapter;
import com.maoyongxin.myapplication.ui.fgt.message.act.strangerdetail.fgt.adapter.DongtaiAdapter;
import com.maoyongxin.myapplication.view.SelectableRoundedImageView;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * 个人--圈子
 */
public class CommunityFragment extends HeaderViewPagerFragment implements BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemClickListener {
    RecyclerView myRecyclerView;
    private SmartRefreshLayout mRefreshLayout;
    private RelativeLayout nodataview;
    private Boolean nodata=true;

    @Override
    public int initLayoutId() {
        return R.layout.communityfragment;
    }

    private String groupid;

    @Override
    public void initView() {
        hideHeader();
        myRecyclerView = getView(R.id.recyclerView);
        mRefreshLayout = getView(R.id.refreshLayout);
        nodataview = getView(R.id.view_not);
    }

    ZLoadingDialog dialog;

    @Override
    public void initData() {
        groupid = getActivity().getIntent().getStringExtra("Groupid");
        dialog = new ZLoadingDialog(context);
        dialog.setLoadingBuilder(Z_TYPE.ROTATE_CIRCLE)//设置类型
                .setLoadingColor(Color.DKGRAY)//颜色
                .setHintText("数据加载中...")
                .setHintTextSize(16) // 设置字体大小 dp
                .setHintTextColor(Color.DKGRAY)  // 设置字体颜色
                .setDurationTime(0.5) // 设置动画时间百分比 - 0.5倍
                .setDialogBackgroundColor(Color.parseColor("#CCffffff")); // 设置背景色，默认白色
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        myRecyclerView.setLayoutManager(layoutManager);
        //内容跟随偏移
        mRefreshLayout.setEnableHeaderTranslationContent(true);
        //设置 Header 为 Material风格
        mRefreshLayout.setRefreshHeader(new MaterialHeader(context).setShowBezierWave(false));
        //设置 Footer 为 球脉冲
        mRefreshLayout.setRefreshFooter(new BallPulseFooter(context).setSpinnerStyle(SpinnerStyle.Scale));
        mRefreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                postBackDtata();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 0;
                datas.clear();
                postBackDtata();
            }
        });
        postBackDtata();
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
    public void sendMsg(int flag, Object obj) {

    }

    /**
     * 获取圈子数据
     */
    private GroupHuatiAdapter adapter;
    private int page = 1;
    List<GroupHuatiBase> datas = new ArrayList<>();

    public void postBackDtata() {
        OkHttpUtils.get().url(ComantUtils.MyUrl + ComantUtils.getUidGambitList)
                .addParams("group_id", groupid + "")
                .addParams("uid", getActivity().getIntent().getStringExtra("personId"))
                .addParams("page", page + "")
                .addParams("myuid", MyApplication.getCurrentUserInfo().getUserId() + "")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                nodataview.setVisibility(View.VISIBLE);
                mRefreshLayout.setVisibility(View.GONE);
            }

            @Override
            public void onResponse(String response, int id) {
                Gson gson = new Gson();
                HuatiInfo1 huatiInfo1 = gson.fromJson(response, HuatiInfo1.class);
                if (huatiInfo1.getCode() != 200) {
                    return;
                }
                setAdapter(huatiInfo1);
            }
        });
        myRecyclerView.setAdapter(adapter);
    }

    GroupHuatiBase base;

    public void setAdapter(HuatiInfo1 info1) {

        if(info1.getInfo().getData().size()!=0)
        {

            nodataview.setVisibility(View.GONE);
            mRefreshLayout.setVisibility(View.VISIBLE);


            for (int i = 0; i < info1.getInfo().getData().size(); i++) {
                if (info1.getInfo().getData().get(i).getIs_top().equals("1")) {
                    base = new GroupHuatiBase(GroupHuatiBase.THREE_ZHIDING);
                } else {
                    base = new GroupHuatiBase(GroupHuatiBase.LEFT_BIG);
                }
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
                datas.add(base);
                nodata=false;
            }
            if (adapter == null) {
                adapter = new GroupHuatiAdapter(datas, getContext(), "");
                adapter.setOnItemClickListener(this);
                adapter.setOnItemChildClickListener(this);
                myRecyclerView.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
        }

        else if(nodata)
        {

            nodataview.setVisibility(View.VISIBLE);
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

        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent();
        intent.putExtra("id", datas.get(position).getId());
        intent.putExtra("groupName", "");
        intent.putExtra("Groupid", getActivity().getIntent().getStringExtra("Groupid"));
        intent.putExtra("parentUserId", datas.get(position).getUid() + "");
        intent.setClass(getContext(), Act_TopicDetails.class);
        startActivity(intent);
    }

    /**
     * 删除自己发过的记录
     *
     * @param huatiId
     * @param position
     */
    private void deleteMyDynamic(final String huatiId, final int position) {
        dialog.show();
        OkHttpUtils.post().url("http://st.3dgogo.com/index/chatgroup_gambit/delete_chatgroup_gambit")
                .addParams("id", huatiId)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                dialog.dismiss();
            }

            @Override
            public void onResponse(String response, int id) {
                dialog.dismiss();
                Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
                datas.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public View getScrollableView() {
        return myRecyclerView;
    }
}
