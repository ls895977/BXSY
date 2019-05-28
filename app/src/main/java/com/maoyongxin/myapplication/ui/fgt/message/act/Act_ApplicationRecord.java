package com.maoyongxin.myapplication.ui.fgt.message.act;

import android.view.View;
import android.widget.TextView;

import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.common.BaseAct;
import com.maoyongxin.myapplication.common.MyApplication;
import com.maoyongxin.myapplication.http.callback.EntityCallBack;
import com.maoyongxin.myapplication.http.entity.RequestListInfo;
import com.maoyongxin.myapplication.http.request.ReqGetRequestList;
import com.maoyongxin.myapplication.http.response.RequestListResponse;
import com.maoyongxin.myapplication.ui.fgt.message.adapter.RequestListAdapter;
import com.maoyongxin.myapplication.view.mylistview.LoadListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 申请记录
 */
public class Act_ApplicationRecord extends BaseAct {

    private LoadListView lvFriendsMsg;

    @Override
    protected void handlerPassMsg(int target, int target1, Object obj) {

    }

    @Override
    public int initLayoutId() {
        return R.layout.act_applicationrecord;
    }

    @Override
    public void initView() {
        hideHeader();
        setOnClickListener(R.id.ApplicationRecord_add);
        setOnClickListener(R.id.ApplicationRecord_back);
        lvFriendsMsg = getView(R.id.lv_friendsMsg);
    }

    @Override
    public void initData() {
        getRequestList();
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
            case R.id.ApplicationRecord_add://添加好友

                startAct(Act_FindAndSearch.class);
                break;
            case R.id.ApplicationRecord_back:
                finish();
                break;
        }
    }

    private void getRequestList() {
        ReqGetRequestList.getList(this, getActivityTag(), MyApplication.getCurrentUserInfo().getUserId(), new EntityCallBack<RequestListResponse>() {
            @Override
            public Class<RequestListResponse> getEntityClass() {
                return RequestListResponse.class;
            }

            @Override
            public void onSuccess(RequestListResponse resp) {
                if (resp.is200()) {
                    List<RequestListInfo.RequestList> requestListInfos;
                    requestListInfos = resp.obj.getRequestList();
                    if (requestListInfos.size() != 0) {
                        RequestListAdapter adapter = new RequestListAdapter(Act_ApplicationRecord.this, requestListInfos);
                        lvFriendsMsg.setAdapter(adapter);
                        lvFriendsMsg.setVisibility(View.VISIBLE);
                        adapter.setOnFreshListener(new RequestListAdapter.OnFreshListener() {
                            @Override
                            public void fresh() {
                                getRequestList();
                            }
                        });
                        lvFriendsMsg.setReflashInterface(new LoadListView.RLoadListener() {
                            @Override
                            public void onRefresh() {
                                getRequestList();
                            }
                        });
                        lvFriendsMsg.setInterface(new LoadListView.ILoadListener() {
                            @Override
                            public void onLoad() {
                                return;
                            }
                        });
                    } else {
                        lvFriendsMsg.setVisibility(View.GONE);
                    }
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
