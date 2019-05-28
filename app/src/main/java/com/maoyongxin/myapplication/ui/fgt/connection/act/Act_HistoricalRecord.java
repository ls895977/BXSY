package com.maoyongxin.myapplication.ui.fgt.connection.act;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.common.BaseAct;
import com.maoyongxin.myapplication.common.MyApplication;
import com.maoyongxin.myapplication.http.entity.recordInfo;
import com.maoyongxin.myapplication.ui.fgt.connection.act.bean.get_msg_call_infoBean;
import com.maoyongxin.myapplication.ui.fgt.connection.adapter.recordListAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import okhttp3.Call;

/**
 * 人脉=企业=历史记录
 */
public class Act_HistoricalRecord extends BaseAct {
    ListView callRecordList;
    private TextView usrName,user_id;
    private RoundedImageView mateHeadImg;
    private ImageView sex;
    private String  targetuserid;
    @Override
    protected void handlerPassMsg(int target, int target1, Object obj) {

    }

    @Override
    public int initLayoutId() {
        return R.layout.act_historicalrecord;
    }

    @Override
    public void initView() {
        hideHeader();
        setOnClickListener(R.id.friends_back);
        setOnClickListener(R.id.bt_chat);
        callRecordList = getView(R.id.callRecord_list);
        usrName = getView(R.id.usrName);
        mateHeadImg = getView(R.id.mateHeadImg);
        sex = getView(R.id.sex);
        user_id=getView(R.id.user_id);
    }

    @Override
    public void initData() {
        postBackData();

        if (MyApplication.getCurrentUserInfo().getSex().equals("0")) {
            sex.setImageResource(R.mipmap.icon_nan);
        } else if (MyApplication.getCurrentUserInfo().getSex().equals("1")) {
            sex.setImageResource(R.mipmap.icon_nan);
        } else {
            sex.setImageResource(R.mipmap.icon_nv);
        }
        usrName.setText(MyApplication.getCurrentUserInfo().getUserName());
        if (MyApplication.getCurrentUserInfo().getHeadImg().equals("")) {
            Glide.with(context).load(R.mipmap.user_head_img).into(mateHeadImg);
        } else {
            Glide.with(context).load(MyApplication.getCurrentUserInfo().getHeadImg()).into(mateHeadImg);
        }
        user_id.setText("ID: "+MyApplication.getCurrentUserInfo().getUserId());
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
            case R.id.bt_chat://消息
                RongIM.getInstance().startPrivateChat(getActivity(), MyApplication.getCurrentUserInfo().getUserId(), MyApplication.getCurrentUserInfo().getUserName());
                break;
        }
    }

    private List<recordInfo> recordInfos = new ArrayList<>();
    private recordListAdapter adapter;

    private void postBackData() {
        targetuserid=getIntent().getStringExtra("targetuserid");
        OkHttpUtils.post().url("http://bisonchat.com/index/enterprise_info/get_msg_call_info.html")
                .addParams("uid",targetuserid)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(String response, int id) {
                Gson gson = new Gson();
                get_msg_call_infoBean bean = gson.fromJson(response, get_msg_call_infoBean.class);
                for (int i = 0; i < bean.getInfo().getRow().size(); i++) {
                    recordInfo info = new recordInfo();
                    info.setshanghuiInfo(bean.getInfo().getRow().get(i).getEnterprise_name(),
                            bean.getInfo().getRow().get(i).getPhone(),
                            bean.getInfo().getRow().get(i).getCreate_time(),
                            bean.getInfo().getRow().get(i).getCall(),
                            bean.getInfo().getRow().get(i).getMsg(),
                            bean.getInfo().getRow().get(i).getIs_collect());
                    recordInfos.add(info);
                }
                if (adapter == null) {
                    adapter = new recordListAdapter(getApp(), recordInfos);
                    callRecordList.setAdapter(adapter);
                } else {
                    callRecordList.setAdapter(adapter);
                }
            }
        });
    }
}
