package com.maoyongxin.myapplication.ui.fgt.min.act;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.common.BaseAct;
import com.maoyongxin.myapplication.common.MyApplication;
import com.maoyongxin.myapplication.http.callback.EntityCallBack;
import com.maoyongxin.myapplication.http.entity.FollowListInfo;
import com.maoyongxin.myapplication.http.request.ReqGetFollowList;
import com.maoyongxin.myapplication.http.response.FollowListResponse;
import com.maoyongxin.myapplication.ui.fgt.min.adapter.recycle_myfollowlistAdapter;

import java.util.List;

public class Act_Follow extends BaseAct {
    RecyclerView rcMyLikeList;
    private List<FollowListInfo.FollowList> followLists;
    private recycle_myfollowlistAdapter adapter;

    @Override
    protected void handlerPassMsg(int target, int target1, Object obj) {

    }

    @Override
    public int initLayoutId() {
        return R.layout.act_follow;
    }

    @Override
    public void initView() {
        hideHeader();
        setOnClickListener(R.id.follow_back);
        rcMyLikeList = getView(R.id.rc_myLike_list);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rcMyLikeList.setLayoutManager(mLinearLayoutManager);
        rcMyLikeList.setItemAnimator(null);
        rcMyLikeList.setHasFixedSize(true);
        rcMyLikeList.setNestedScrollingEnabled(false);
    }

    @Override
    public void initData() {
        getMyFollowList();
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
            case R.id.follow_back:
                finish();
                break;
        }
    }

    private void getMyFollowList() {
        ReqGetFollowList.getList(this, getClass().getSimpleName(), MyApplication.getCurrentUserInfo().getUserId(), new EntityCallBack<FollowListResponse>() {
            @Override
            public Class<FollowListResponse> getEntityClass() {
                return FollowListResponse.class;
            }

            @Override
            public void onSuccess(final FollowListResponse resp) {
                if (resp.is200()) {
                    followLists = resp.obj.getFollowList();
                    adapter = new recycle_myfollowlistAdapter(followLists, context);
                    rcMyLikeList.setAdapter(adapter);
                } else {
                    adapter.notifyDataSetChanged();
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
