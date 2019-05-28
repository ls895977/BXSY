package com.maoyongxin.myapplication.ui.fgt.community.fgt;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.common.BaseFgt;
import com.maoyongxin.myapplication.common.ComantUtils;
import com.maoyongxin.myapplication.ui.fgt.community.act.Act_JournalismDetails;
import com.maoyongxin.myapplication.ui.fgt.community.adapter.JournalismAdapter;
import com.maoyongxin.myapplication.ui.fgt.community.bean.JournalismBean;
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
 * 新闻
 */
public class Fgt_businessact extends BaseFgt implements BaseQuickAdapter.OnItemClickListener {
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView myRecyclerView;
    private JournalismAdapter adapter;
    private View haderView;
    @Override
    public int initLayoutId() {
        return R.layout.gt_businessact;
    }

    @Override
    public void initView() {
        hideHeader();
        mRefreshLayout = getView(R.id.refreshLayout);
        myRecyclerView = getView(R.id.recycle1);
    }

    @Override
    public void initData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        myRecyclerView.setLayoutManager(layoutManager);
        //内容跟随偏移
        mRefreshLayout.setEnableHeaderTranslationContent(true);
        //设置 Header 为 Material风格
        mRefreshLayout.setRefreshHeader(new MaterialHeader(context).setShowBezierWave(false));
        //设置 Footer 为 球脉冲
        mRefreshLayout.setRefreshFooter(new BallPulseFooter(context).setSpinnerStyle(SpinnerStyle.Scale));
        mRefreshLayout.setEnableRefresh(true);//启用刷新
        mRefreshLayout.setEnableLoadMore(true);//启用加载
        mRefreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                postBackDtata();
                refreshLayout.finishLoadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 0;
                datas.clear();
                postBackDtata();
                refreshLayout.finishRefresh();
            }
        });
        adapter = new JournalismAdapter(datas, context);

        haderView = LayoutInflater.from(getActivity()).inflate(R.layout.business_hader, null);




        adapter.addHeaderView(haderView);
        adapter.setOnItemClickListener(this);
        myRecyclerView.setAdapter(adapter);
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
    List<JournalismBean.InfoBean.DataBean> datas = new ArrayList<>();
    public void postBackDtata() {
        OkHttpUtils.post().url(ComantUtils.MyUrlHot + ComantUtils.Community_New)
                .addParams("page", page + "")
                .addParams("per_page", 10 + "")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(String response, int id) {
                Gson gson = new Gson();

                JournalismBean bean = gson.fromJson(response, JournalismBean.class);

                for (int i = 0; i < bean.getInfo().getData().size(); i++) {
                    datas.add(bean.getInfo().getData().get(i));
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent sPayrecordActivity = new Intent();
        sPayrecordActivity.putExtra("id1", datas.get(position).getId() + "");
        sPayrecordActivity.putExtra("uid", datas.get(position).getUid() + "");
        sPayrecordActivity.putExtra("newstitle", datas.get(position).getTitle() + "");
        sPayrecordActivity.putExtra("content", datas.get(position).getContent() + "");
        sPayrecordActivity.putExtra("source", datas.get(position).getSource() + "");
        sPayrecordActivity.putExtra("news_Img", "http://118.24.2.164:8083/news/" + datas.get(position).getImg());
        sPayrecordActivity.putExtra("dateTime",datas.get(position).getCreate_time());
        startAct(sPayrecordActivity, Act_JournalismDetails.class);
    }

    public int dip2px(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5);
    }
}
