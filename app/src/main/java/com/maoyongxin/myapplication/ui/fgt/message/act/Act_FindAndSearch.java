package com.maoyongxin.myapplication.ui.fgt.message.act;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lykj.aextreme.afinal.utils.Debug;
import com.lykj.aextreme.afinal.utils.MyToast;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.common.BaseAct;
import com.maoyongxin.myapplication.common.ComantUtils;
import com.maoyongxin.myapplication.common.MyApplication;
import com.maoyongxin.myapplication.http.callback.EntityCallBack;
import com.maoyongxin.myapplication.http.entity.StrangerInfo;
import com.maoyongxin.myapplication.http.request.ReqFindUserById;
import com.maoyongxin.myapplication.http.request.ReqGetStrangerList;
import com.maoyongxin.myapplication.http.response.LoginResponse;
import com.maoyongxin.myapplication.http.response.StrangerResponse;
import com.maoyongxin.myapplication.ui.fgt.community.bean.DynamicBase;
import com.maoyongxin.myapplication.ui.fgt.community.bean.DynamicBean;
import com.maoyongxin.myapplication.ui.fgt.message.act.bean.FindAndSearchBean;
import com.maoyongxin.myapplication.ui.fgt.message.adapter.FindAndSearchAdapter;
import com.maoyongxin.myapplication.ui.fgt.message.adapter.StrangerPersonAdapter;
import com.maoyongxin.myapplication.view.ClearWriteEditText;
import com.maoyongxin.myapplication.view.SelectableRoundedImageView;
import com.maoyongxin.myapplication.view.mylistview.LoadListView;
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
import okhttp3.Call;

/**
 * 添加
 */
public class Act_FindAndSearch extends BaseAct implements BaseQuickAdapter.OnItemChildClickListener {
    View viewHader;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView myRecyclerView;
    private EditText search;
    private TextView tv_status;

    @Override
    protected void handlerPassMsg(int target, int target1, Object obj) {

    }
//act好友查找
    @Override
    public int initLayoutId() {
        return R.layout.act_find_and_search;
    }

    @Override
    public void initView() {
        hideHeader();
        setOnClickListener(R.id.friends_back);
        viewHader = LayoutInflater.from(context).inflate(R.layout.view_findandsearch, null);
        setOnClickListener(viewHader, R.id.tv_addByTiaoJian);
        search = getView(viewHader, R.id.search);
        mRefreshLayout = getView(R.id.refreshLayout);
        myRecyclerView = getView(R.id.recyclerView);
        tv_status = getView(viewHader, R.id.tv_status);
    }

    private FindAndSearchAdapter andSearchAdapter;
    ZLoadingDialog dialog;

    @Override
    public void initData() {
        dialog = new ZLoadingDialog(this);
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
        andSearchAdapter = new FindAndSearchAdapter(datas, this);
        andSearchAdapter.setOnItemChildClickListener(this);
        andSearchAdapter.addHeaderView(viewHader);
        myRecyclerView.setAdapter(andSearchAdapter);
        postBackDtata();
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (search.getText().toString().length() > 0) {
                    stSearch = search.getText().toString();
                    if (stSearch != null) {
                        findPostSearch();
                    }


                }
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
            case R.id.friends_back:
                finish();
                break;
            case R.id.tv_addByTiaoJian://按条件查找好友
                startActivityForResult(Act_ConditionalLookup.class, 10);
                break;
        }
    }

    int page = 0;
    private List<StrangerInfo.UserList> list = new ArrayList<>();
    private List<StrangerInfo.UserList> datas = new ArrayList<>();

    /**
     * 可能感兴趣的人
     */
    public void postBackDtata() {
        tv_status.setText("可能感兴趣的人");
        ReqGetStrangerList.getList(Act_FindAndSearch.this, getActivityTag(), MyApplication.getCurrentUserInfo().getUserId(), page + "", new EntityCallBack<StrangerResponse>() {
            @Override
            public Class<StrangerResponse> getEntityClass() {
                return StrangerResponse.class;
            }

            @Override
            public void onSuccess(StrangerResponse resp) {
                if (resp.is200()) {
                    list = resp.obj.getUserList();
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setStatus(true);
                        datas.add(list.get(i));
                    }
                    andSearchAdapter.notifyDataSetChanged();
                } else {
                    MyToast.show(getActivity(), resp.msg);
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

    /**
     * 此界面搜索好友
     */
    private String stSearch = "", company_name = "", sex = "", permanent_city = "", position = "";

    public void findPostSearch() {
        datas.clear();
        tv_status.setText("查找到的好友");
        OkHttpUtils.post().url(ComantUtils.MyUrl + "/user/search_user_api.html")
                .addParams("search", stSearch)
                .addParams("company_name", company_name)
                .addParams("sex", sex)
                .addParams("permanent_city", permanent_city)
                .addParams("position", position)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                dialog.dismiss();
            }

            @Override
            public void onResponse(String response, int id) {
                dialog.dismiss();
                Gson gson = new Gson();
                FindAndSearchBean bean = gson.fromJson(response, FindAndSearchBean.class);
                if (bean.getInfo().size() == 0) {
                    return;
                }
                for (int i = 0; i < bean.getInfo().size(); i++) {
                    StrangerInfo.UserList infoBean = new StrangerInfo.UserList();

                    StrangerInfo.UserList.User user = new StrangerInfo.UserList.User();
                    infoBean.setStatus(true);
                    user.setHeadImg(bean.getInfo().get(i).getHeadImg());
                    user.setUserName(bean.getInfo().get(i).getUserName());
                    user.setUserId(Integer.valueOf(bean.getInfo().get(i).getUserId()));
                    infoBean.setUser(user);
                    datas.add(infoBean);
                }
                andSearchAdapter.notifyDataSetChanged();

            }
        });
    }
    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.find_add://添加好友
                Intent intent = new Intent();
                intent.putExtra("personId", datas.get(position).getUser().getUserId() + "");
                startAct(intent, Act_StrangerDetail.class);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == 10) {
            stSearch = data.getStringExtra("search");
            company_name = data.getStringExtra("company_name");
            sex = data.getStringExtra("sex");
            permanent_city = data.getStringExtra("permanent_city");
            position = data.getStringExtra("position");
            dialog.show();
            findPostSearch();
            return;
        }
    }
}