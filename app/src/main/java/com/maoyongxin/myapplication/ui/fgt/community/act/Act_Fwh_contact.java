package com.maoyongxin.myapplication.ui.fgt.community.act;


import android.content.Intent;
import android.os.Bundle;


import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.common.BaseAct;
import com.maoyongxin.myapplication.common.MyApplication;
import com.maoyongxin.myapplication.http.callback.EntityCallBack;
import com.maoyongxin.myapplication.http.request.ReqCommunity;
import com.maoyongxin.myapplication.http.response.CommunityUsersResponse;
import com.maoyongxin.myapplication.ui.fgt.message.act.strangerdetail.fgt.adapter.CommunityPersonListAdapter;
import com.maoyongxin.myapplication.ui.fgt.min.act.Act_ComunityMateDetail;

import io.rong.imkit.RongIM;


public class Act_Fwh_contact extends BaseAct{

    private ListView fuwuhaoList;
    private  String Communityid;
    private String Targetuserid;
    private TextView img_back;

    @Override
    public int initLayoutId() {
        return R.layout.activity_act__fwh_contact;
    }

    @Override
    public void initView() {

        fuwuhaoList=getView(R.id.lv_fuwuhao);
        img_back=getViewAndClick(R.id.img_back);

        img_back.setOnClickListener(this);


    }

    @Override
    public void initData() {

        Communityid=getIntent().getStringExtra("communityId");
        Targetuserid=getIntent().getStringExtra("targetUserId");


    }

    @Override
    public void updateUI() {

    }

    @Override
    protected void handlerPassMsg(int target, int target1, Object obj) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public void requestData() {

    }

    @Override
    public void onViewClick(View v) {

    switch (v.getId())
    {
        case  R.id.img_back:
            finish();
            break;
    }


    }



    @Override
    protected void onResume() {
        super.onResume();
        getMyCommunityPerson();
    }

    private void getMyCommunityPerson() {
       // progressDialog.show();
        ReqCommunity.getMyCommunityPersons(this, getActivityTag(), Targetuserid, Communityid, new EntityCallBack<CommunityUsersResponse>() {
            @Override
            public Class<CommunityUsersResponse> getEntityClass() {
                return CommunityUsersResponse.class;
            }

            @Override
            public void onSuccess(final CommunityUsersResponse resp) {
              //  progressDialog.dismiss();
                if (resp.is200()) {
                    if (resp.obj.getSuperManagerUserId().equals(MyApplication.getCurrentUserInfo().getUserId())) {//超管

                    } else {
                        if (resp.obj.getManagerUserId().size() != 0) {
                            for (int i = 0; i < resp.obj.getManagerUserId().size(); i++) {
                                if (resp.obj.getManagerUserId().get(i).equals(MyApplication.getCurrentUserInfo().getUserId())) {

                                    break;
                                } else {

                                }
                            }
                        } else {

                        }
                    }
                    CommunityPersonListAdapter    adapter = new CommunityPersonListAdapter(Communityid, resp.obj.getSuperManagerUserId(), resp.obj.getManagerUserId(), resp.obj.getUserList(), context, false);
                    fuwuhaoList.setAdapter(adapter);
                    adapter.setOnRefreshListener(new CommunityPersonListAdapter.OnRefreshListener() {
                        @Override
                        public void refresh() {
                            getMyCommunityPerson();
                        }
                    });



                }
            }

            @Override
            public void onFailure(Throwable e) {
                //progressDialog.dismiss();
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

