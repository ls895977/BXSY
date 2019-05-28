package com.maoyongxin.myapplication.ui.fgt.min.act.fgt;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.lykj.aextreme.afinal.utils.Debug;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.bean.PublisherBean;
import com.maoyongxin.myapplication.common.BaseFgt;
import com.maoyongxin.myapplication.common.MyApplication;
import com.maoyongxin.myapplication.ui.fgt.connection.adapter.PublisherAdapter;
import com.maoyongxin.myapplication.ui.fgt.min.act.adapter.PublisherChlideAdapter;
import com.maoyongxin.myapplication.ui.fgt.min.act.bean.PublisherChlideBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 我的-收藏-公告需求
 */
public class Fgt_company_Coll extends BaseFgt {
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

        myAdapter = new PublisherChlideAdapter(context);
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
    List<PublisherChlideBean.InfoBean.DataBean> datas = new ArrayList<>();
    private PublisherChlideAdapter myAdapter;

    public void postBackDtata() {
        OkHttpUtils.post()
                .addParams("myuid", MyApplication.getCurrentUserInfo().getUserId())
                .addParams("page", page + "")
                .addParams("per_page", "10")
                .url("http://bisonchat.com/index/notice_enshrine/getNoticeEnshrineListApi.html").build().execute(new StringCallback() {

            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(String response, int id) {
                Gson gson = new Gson();
                PublisherChlideBean bean = gson.fromJson(response, PublisherChlideBean.class);
                for (int i = 0; i < bean.getInfo().getData().size(); i++) {
                    datas.add(bean.getInfo().getData().get(i));
                    page++;
                }
                myAdapter.setNewData(datas);
                myAdapter.notifyDataSetChanged();
            }
        });
    }
}
