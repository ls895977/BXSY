package com.maoyongxin.myapplication.ui.fgt.connection.act;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.common.BaseAct;
import com.maoyongxin.myapplication.ui.fgt.connection.act.bean.SerViceProviderBean;
import com.maoyongxin.myapplication.ui.fgt.connection.adapter.FgtSerViceProviderAdapter;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import static com.zhouyou.http.EasyHttp.getContext;

/**
 * 服务商=服务商=搜索
 */
public class Act_ServiceSearch extends BaseAct implements OnRefreshListener, OnLoadMoreListener {
    private ImageView back;
    private RecyclerView myRecyclerView;
    SmartRefreshLayout refreshLayout;
    List<SerViceProviderBean.InfoBean.DataBean> data = new ArrayList<>();
    FgtSerViceProviderAdapter adapter;
    private Gson gson = new Gson();
    private EditText sechaertv;
    @Override
    protected void handlerPassMsg(int target, int target1, Object obj) {

    }

    @Override
    public int initLayoutId() {
        return R.layout.act_servicesearch;
    }

    @Override
    public void initView() {
        hideHeader();
        setOnClickListener(R.id.ServiceProvider_back);
        myRecyclerView = findViewById(R.id.zhihutitle_myRecyclerview);
        refreshLayout = findViewById(R.id.refreshLayout);
        sechaertv = findViewById(R.id.ServiceSearch_seacher);
        //内容跟随偏移
        refreshLayout.setEnableHeaderTranslationContent(true);
        //设置 Header 为 Material风格
        refreshLayout.setRefreshHeader(new MaterialHeader(getContext()).setShowBezierWave(false));
        //设置 Footer 为 球脉冲
        refreshLayout.setRefreshFooter(new BallPulseFooter(getContext()).setSpinnerStyle(SpinnerStyle.Scale));
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        sechaertv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(sechaertv.getText().toString().length()>0){
                    postsecharViewData(sechaertv.getText().toString());
                }else {
                    postsecharViewData("gg");
                }
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
            switch (v.getId()){
                case R.id.ServiceProvider_back:
                    finish();
                    break;
            }
    }

    public void postsecharViewData(String search) {
        data.clear();
        OkHttpUtils.post().url("http://st.3dgogo.com/index/user_resource/searchUserResourceApi.html")
                .addParams("search", search)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                SerViceProviderBean gridBean = gson.fromJson(response, SerViceProviderBean.class);
                for (int i = 0; i < gridBean.getInfo().getData().size(); i++) {
                    SerViceProviderBean.InfoBean.DataBean bean = gridBean.getInfo().getData().get(i);
                    data.add(bean);
                }
                if (adapter == null) {
                    adapter = new FgtSerViceProviderAdapter(getApplicationContext());
                    adapter.setNewData(data);
                    adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            Log.e("aa", "----主item-------" + position);
                        }
                    });
                    myRecyclerView.setAdapter(adapter);
                } else {
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        refreshLayout.finishLoadMore();
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        refreshLayout.finishRefresh();
    }
}
