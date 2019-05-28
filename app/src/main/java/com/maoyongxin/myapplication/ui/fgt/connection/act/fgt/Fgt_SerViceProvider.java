package com.maoyongxin.myapplication.ui.fgt.connection.act.fgt;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.bean.EventMessage;
import com.maoyongxin.myapplication.ui.fgt.community.act.Act_News_Web;
import com.maoyongxin.myapplication.ui.fgt.connection.act.base.SerViceProviderGridBean;
import com.maoyongxin.myapplication.ui.fgt.connection.act.base.ViewPagerFragment;
import com.maoyongxin.myapplication.ui.fgt.connection.act.bean.MessageEvent;
import com.maoyongxin.myapplication.ui.fgt.connection.act.bean.SerViceProviderBean;
import com.maoyongxin.myapplication.ui.fgt.connection.adapter.FgtSerViceProviderAdapter;
import com.maoyongxin.myapplication.ui.fgt.connection.adapter.ServiceProviderGrdivewAdater;
import com.maoyongxin.myapplication.view.SodukuGridView;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class Fgt_SerViceProvider extends ViewPagerFragment implements  OnRefreshListener, OnLoadMoreListener {
    private SodukuGridView myGridview;
    ServiceProviderGrdivewAdater grdivewAdater;
    List<SerViceProviderGridBean.InfoBean> gridData = new ArrayList<>();
    private RecyclerView myRecyclerView;

    SmartRefreshLayout refreshLayout;
    List<SerViceProviderBean.InfoBean.DataBean> data = new ArrayList<>();
    FgtSerViceProviderAdapter adapter;
    private Boolean nodatainfo=true;
    private TextView tv_Service_Number, tv_Personal;
    private Gson gson = new Gson();
    private RelativeLayout grandempty;

    private String position="";
    private int position1=0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            data.clear();
            EventBus.getDefault().register(this);
            rootView = inflater.inflate(R.layout.fgt_serviceprovider, container, false);
            init(rootView);
        }
        return rootView;
    }

    private View serviceHader;

    public void init(View rootView) {
        isCommunity = getArguments().getString("type");
        myRecyclerView = rootView.findViewById(R.id.zhihutitle_myRecyclerview);
        refreshLayout = rootView.findViewById(R.id.refreshLayout);





        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
        //内容跟随偏移
        refreshLayout.setEnableHeaderTranslationContent(true);
        //设置 Header 为 Material风格
        refreshLayout.setRefreshHeader(new MaterialHeader(getContext()).setShowBezierWave(false));
        //设置 Footer 为 球脉冲
        refreshLayout.setRefreshFooter(new BallPulseFooter(getContext()).setSpinnerStyle(SpinnerStyle.Scale));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        myRecyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        adapter = new FgtSerViceProviderAdapter(getContext());

        serviceHader = LayoutInflater.from(getContext()).inflate(R.layout.item_serviceprovider, null);
        myGridview = serviceHader.findViewById(R.id.my_Gridview);

        grandempty=serviceHader.findViewById(R.id.grandempty);
        adapter.addHeaderView(serviceHader);
        myRecyclerView.setAdapter(adapter);






        grdivewAdater = new ServiceProviderGrdivewAdater(getContext(), gridData);
        myGridview.setAdapter(grdivewAdater);
      //  myGridview.setOnItemClickListener(this);
        postGridViewData();
        postListViewData(isCommunity, classify_id);
    }

    public void postGridViewData() {
        OkHttpUtils.post().url("http://st.3dgogo.com/index/classify/getClassifyApi.html").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                SerViceProviderGridBean gridBean = gson.fromJson(response, SerViceProviderGridBean.class);
                for (int i = 0; i < gridBean.getInfo().size(); i++) {
                    SerViceProviderGridBean.InfoBean bean = gridBean.getInfo().get(i);
                    if (i == 0) {
                        bean.setIsof(true);
                    }
                    gridData.add(bean);
                }
                grdivewAdater.notifyDataSetChanged();
            }
        });
    }


/*
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        data.clear();
        gridData.get(position1).setIsof(false);
        gridData.get(position).setIsof(true);
        position1 = position;
        grdivewAdater.notifyDataSetChanged();

        classify_id = gridData.get(position).getId();
        postListViewData(isCommunity, classify_id);

        nodatainfo=true;



    }

*/
    @Subscribe
    public void onEventMainThread(EventMessage eventMessage) {
        switch (eventMessage.getType()) {

            case 3:

                position = eventMessage.getData().toString();

                data.clear();


                gridData.get(position1).setIsof(false);
                gridData.get(Integer.valueOf(position).intValue()).setIsof(true);
                position1 = Integer.valueOf(position).intValue();
                grdivewAdater.notifyDataSetChanged();
                classify_id = gridData.get(Integer.valueOf(position).intValue()).getId();
                nodatainfo=true;
                postListViewData(isCommunity, classify_id);

                break;

        }


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void XXX(MessageEvent messageEvent) {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {   //加载完成
        page++;
        postListViewData(isCommunity, classify_id);
        refreshLayout.finishLoadMore();
        //                refreshLayout.finishLoadMoreWithNoMoreData();  //全部加载完成,没有数据了调用此方法
    }


    @Override
    public void onRefresh(RefreshLayout refreshLayout) { //刷新完成
        page = 1;
        data.clear();

        postListViewData(isCommunity, classify_id);
        refreshLayout.finishRefresh();

        nodatainfo=true;
    }

    private String isCommunity = "0", classify_id = "20";
    private int page = 1;

    public void postListViewData(String isCommunity, String classify_id) {
        OkHttpUtils.post().url("http://st.3dgogo.com/index/user_resource/getUserResourceListApi.html")
                .addParams("isCommunity", isCommunity)
                .addParams("classify_id", classify_id)
                .addParams("page", page + "")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                SerViceProviderBean gridBean = gson.fromJson(response, SerViceProviderBean.class);
                if (gridBean.getCode() != 200) {



                    return;
                }

                    if (gridBean.getInfo().getData().size()>0)
                    {
                        grandempty.setVisibility(View.GONE);

                        for (int i = 0; i < gridBean.getInfo().getData().size(); i++) {

                            SerViceProviderBean.InfoBean.DataBean bean = gridBean.getInfo().getData().get(i);
                            data.add(bean);
                        }
                        nodatainfo=false;
                        adapter.setNewData(data);
                        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                Log.e("aa", "----主item-------" + position);

                                Intent intent =new Intent(getContext(),Act_News_Web.class);

                                intent.putExtra("communityId",data.get(position).getCommunity_id());
                                intent.putExtra("targetUserId",data.get(position).getUid());
                                intent.putExtra("url","http://st.3dgogo.com/index/enterprise_publicity/get_community_id_details_url_api.html?community_id="+data.get(position).getCommunity_id());
                                getActivity().startActivity(intent);
                            }
                        });
                        adapter.notifyDataSetChanged();




                    }


                if (nodatainfo)
                {
                    grandempty.setVisibility(View.VISIBLE);
                }



            }
        });
    }

}
