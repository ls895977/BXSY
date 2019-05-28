package com.maoyongxin.myapplication.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jky.baselibrary.util.common.TimeUtil;
import com.jky.baselibrary.widget.TitleBar;
import com.lykj.aextreme.afinal.utils.MyToast;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.common.AppTitleBarActivity;
import com.maoyongxin.myapplication.common.BaseAct;
import com.maoyongxin.myapplication.common.MyApplication;
import com.maoyongxin.myapplication.http.callback.EntityCallBack;
import com.maoyongxin.myapplication.http.request.ReqCommunity;
import com.maoyongxin.myapplication.http.response.CommunityJoinResponse;
import com.maoyongxin.myapplication.http.response.MyCommunityResponse;
import com.maoyongxin.myapplication.ui.fgt.connection.adapter.CommunityRequestListAdapter;
import com.maoyongxin.myapplication.ui.fgt.min.act.Act_AddressHomeCheck;
import com.maoyongxin.myapplication.ui.fgt.min.act.Act_TeamInformation;

import butterknife.BindView;

import static com.zhouyou.http.EasyHttp.getContext;

public class CommunityMessageActivity extends BaseAct {

    private    TitleBar TitleBarCMD;
    private  TextView tvNoManagerRequest;
    private    ListView lvManagerMessage;
    private  LinearLayout lineManagerRequest;
    private    TextView tvNoJoinRequest;
    private  ListView lvJoinMessage;
    private  LinearLayout lineRequest;


    private boolean isSuper;
    private String communityId="";


    @Override
    public void initView() {

        TitleBarCMD=getView(R.id.TitleBar_CMD);


        tvNoManagerRequest=getView(R.id.tv_noManagerRequest);
        lvManagerMessage=getView(R.id.lv_ManagerMessage);
        lineManagerRequest=getView(R.id.line_managerRequest);
        tvNoJoinRequest=getView(R.id.tv_noJoinRequest);
        lvJoinMessage=getView(R.id.lv_joinMessage);

        lineRequest=getView(R.id.line_Request);



        if (isSuper) {
            lineManagerRequest.setVisibility(View.VISIBLE);
        } else {
            lineManagerRequest.setVisibility(View.GONE);
        }







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
    public void initData() {
        communityId = getIntent().getStringExtra("communityId");

        if (getIntent().getStringExtra("communityId").equals("root"))
        {

            getMyOwnCommunity();

        }

        else
        {
            if (getIntent().getStringExtra("type").equals("superManager")) {
                isSuper = true;
            } else if (getIntent().getStringExtra("type").equals("manager")) {
                isSuper = false;
            }

            if (isSuper) {
                getSuperRequest();
            }
            getJoinRequest();
        }



    }

    @Override
    protected void handlerPassMsg(int target, int target1, Object obj) {

    }

    @Override
    public int initLayoutId() {
        return R.layout.activity_community_message;
    }



    private void getSuperRequest() {
        ReqCommunity.getSuperRequest(this, getActivityTag(), MyApplication.getCurrentUserInfo().getUserId(), communityId, new EntityCallBack<CommunityJoinResponse>() {
            @Override
            public Class<CommunityJoinResponse> getEntityClass() {
                return CommunityJoinResponse.class;
            }

            @Override
            public void onSuccess(CommunityJoinResponse resp) {
                if (resp.is200()) {
                    if (resp.obj.getRequestList().size() == 0) {
                        tvNoManagerRequest.setVisibility(View.VISIBLE);
                        lvManagerMessage.setVisibility(View.GONE);
                    } else {
                        tvNoManagerRequest.setVisibility(View.GONE);
                        lvManagerMessage.setVisibility(View.VISIBLE);
                    }
                    CommunityRequestListAdapter superAdapter = new CommunityRequestListAdapter(resp.obj.getRequestList(), CommunityMessageActivity.this, false);
                    superAdapter.setOnRefreshListener(new CommunityRequestListAdapter.OnRefreshListener() {
                        @Override
                        public void refresh() {
                            getSuperRequest();
                        }
                    });
                    lvManagerMessage.setAdapter(superAdapter);
                } else {
                    MyToast.show(getActivity(),resp.msg);
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

    private void getJoinRequest() {
        ReqCommunity.getAskRequest(this, getActivityTag(), MyApplication.getCurrentUserInfo().getUserId(), communityId, new EntityCallBack<CommunityJoinResponse>() {
            @Override
            public Class<CommunityJoinResponse> getEntityClass() {
                return CommunityJoinResponse.class;
            }

            @Override
            public void onSuccess(CommunityJoinResponse resp) {
                if (resp.is200()) {
                    if (resp.obj.getRequestList().size() == 0) {
                        tvNoJoinRequest.setVisibility(View.VISIBLE);
                        lvJoinMessage.setVisibility(View.GONE);
                    } else {
                        tvNoJoinRequest.setVisibility(View.GONE);
                        lvJoinMessage.setVisibility(View.VISIBLE);
                    }
                    CommunityRequestListAdapter joinAdapter = new CommunityRequestListAdapter(resp.obj.getRequestList(), CommunityMessageActivity.this, true);
                    joinAdapter.setOnRefreshListener(new CommunityRequestListAdapter.OnRefreshListener() {
                        @Override
                        public void refresh() {
                            getJoinRequest();
                        }
                    });
                    lvJoinMessage.setAdapter(joinAdapter);
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


    private void getMyOwnCommunity() {
        ReqCommunity.getMyCommunity(getActivity(), getClass().getSimpleName(), MyApplication.getCurrentUserInfo().getUserId(), new EntityCallBack<MyCommunityResponse>() {
            @Override
            public Class<MyCommunityResponse> getEntityClass() {
                return MyCommunityResponse.class;
            }

            @Override
            public void onSuccess(MyCommunityResponse resp) {


                    communityId= resp.obj.getCommunityId();


                    getSuperRequest();

                getJoinRequest();

            }

            @Override
            public void onFailure(Throwable e) {
                communityId="";
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
