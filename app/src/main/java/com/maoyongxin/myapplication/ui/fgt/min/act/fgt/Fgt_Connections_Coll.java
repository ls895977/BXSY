package com.maoyongxin.myapplication.ui.fgt.min.act.fgt;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.lykj.aextreme.afinal.utils.Debug;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.common.BaseFgt;
import com.maoyongxin.myapplication.common.MyApplication;
import com.maoyongxin.myapplication.ui.fgt.connection.act.bean.ConnectionBean;
import com.maoyongxin.myapplication.ui.fgt.connection.adapter.AnnouncementRequirementAdapter;
import com.maoyongxin.myapplication.ui.fgt.min.act.bean.Connections_CollBean;
import com.maoyongxin.myapplication.ui.fgt.min.adapter.Connections_CollAdapter;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 我的-收藏-人脉
 */
public class Fgt_Connections_Coll extends BaseFgt {
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView myRecyclerView;

    @Override
    public int initLayoutId() {
        return R.layout.fgt_connections_coll;
    }

    @Override
    public void initView() {
        hideHeader();
        myRecyclerView = getView(R.id.recyclerView);
    }

    @Override
    public void initData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        myRecyclerView.setLayoutManager(layoutManager);
        //内容跟随偏移
//        mRefreshLayout.setEnableHeaderTranslationContent(true);
//        //设置 Header 为 Material风格
//        mRefreshLayout.setRefreshHeader(new MaterialHeader(context).setShowBezierWave(false));
//        //设置 Footer 为 球脉冲
//        mRefreshLayout.setRefreshFooter(new BallPulseFooter(context).setSpinnerStyle(SpinnerStyle.Scale));
//        mRefreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
//            @Override
//            public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
//            }
//
//            @Override
//            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
////                page++;
////                postBackDtata();
//            }
//
//            @Override
//            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
////                page = 0;
////                datas.clear();
////                postBackDtata();
//            }
//        });
        myAdapter = new Connections_CollAdapter(context);
        myRecyclerView.setAdapter(myAdapter);
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


    private int page = 1;
    List<Connections_CollBean.InfoBean> datas = new ArrayList<>();
    private Connections_CollAdapter myAdapter;

    public void postBackDtata() {
        OkHttpUtils.post()
                .addParams("uid", MyApplication.getCurrentUserInfo().getUserId())
                .url("http://bisonchat.com/index/enterprise_info/get_collect_info.html").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                Gson gson = new Gson();
                Connections_CollBean bean = gson.fromJson(response, Connections_CollBean.class);
                for (int i = 0; i < bean.getInfo().size(); i++) {
                    datas.add(bean.getInfo().get(i));
                }
                myAdapter.setNewData(datas);
                myAdapter.notifyDataSetChanged();
            }
        });
    }
}
