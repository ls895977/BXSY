package com.maoyongxin.myapplication.ui.fgt.min.act;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.common.BaseAct;
import com.maoyongxin.myapplication.http.entity.recordInfo;
import com.maoyongxin.myapplication.ui.fgt.min.adapter.recordListAdapter;
import com.maoyongxin.myapplication.view.SelectableRoundedImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 我的=服务号=团队成员=人脉记录
 */
public class Act_ComunityMateDetail extends BaseAct {
    SelectableRoundedImageView mateHeadImg;
    CardView mateAll;
    TextView callRecord;
    ListView callRecordList;
    TextView usrName;
    TextView telNumber;
    TextView emptyView;
    TextView btchat;
    private String personId;
    private String personHead;
    private String personusrename;
    private String personTel;
    private String sex;
    private String headImgUrl;
    private String userName;
    private List<recordInfo> recordInfos=new ArrayList<>();

    private static final int UPDATE = 0;
    private Handler handler;
    @Override
    protected void handlerPassMsg(int target, int target1, Object obj) {

    }

    @Override
    public int initLayoutId() {
        return R.layout.act_comunitymatedetail;
    }

    @Override
    public void initView() {
        hideHeader();
        mateHeadImg = getView(R.id.mateHeadImg);
        mateAll = getView(R.id.mateAll);
        callRecord = getView(R.id.callRecord);
        callRecordList = getView(R.id.callRecord_list);
        usrName = getView(R.id.usrName);
        telNumber = getView(R.id.tel_number);
        emptyView = getView(R.id.empty_view);
        btchat = getView(R.id.bt_chat);
        btchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RongIM.getInstance().startPrivateChat(getActivity(), personId,userName);
            }
        });

        final recordListAdapter recordListAdapter = new recordListAdapter(this, recordInfos);
        callRecordList.setAdapter(recordListAdapter);

        personId = getIntent().getStringExtra("personId");
        personTel = getIntent().getStringExtra("personTel");


        handler = new Handler() {


            @Override
            public void handleMessage(Message msg) {
                if (msg.what == UPDATE) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recordListAdapter.notifyDataSetInvalidated();
                        }
                    }, 10);


                }
                super.handleMessage(msg);
            }

        };

    }

    @Override
    public void initData() {
        setPersonInfo();

        get_recordList( getIntent().getStringExtra("personId"));
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
    private void setPersonInfo() {
        sex = getIntent().getStringExtra("personSex");
        headImgUrl = getIntent().getStringExtra("personHead");
        userName = getIntent().getStringExtra("personusrename");
        personTel = getIntent().getStringExtra("personTel");
        if (sex.equals("0")) {
            setDrawableBoy(usrName);
        } else if (sex.equals("1")) {
            setDrawableBoy(usrName);
        } else {
            setDrawableGirl(usrName);
        }
        telNumber.setText(personTel);
        usrName.setText(userName);
        if (headImgUrl.equals("")) {
            Glide.with(context).load(R.mipmap.user_head_img).into(mateHeadImg);
        } else {
            Glide.with(context).load(headImgUrl).into(mateHeadImg);
        }
    }


    private void setDrawableBoy(TextView view) {
        Drawable drawable = getResources().getDrawable(R.mipmap.icon_boy);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
        view.setCompoundDrawables(drawable, null, null, null);//画在左边
    }

    private void setDrawableGirl(TextView view) {
        Drawable drawable = getResources().getDrawable(R.mipmap.icon_girl);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
        view.setCompoundDrawables(drawable, null, null, null);//画在左边
    }


    private void get_recordList(final String companyName) {
//get自身服务器数据
        final String XkOne = "http://st.3dgogo.com/index/enterprise_info/get_msg_call_info/uid/";//https://way.jd.com/jindidata/detail_info?name=
        final String XkThere = ".html";//"&appkey=35be7935b960fc78b04ae16ae654193a";
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(XkOne + companyName + XkThere).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJsonXK(responseData);

                } catch (Exception e) {
                    // showMessagefail("解析失败");
                }
            }
        }).start();
    }

    private void parseJsonXK(String companyName) {
//post自身服务器数据
        int Result_code = 0;
        String cpnName;
        String cpnTele;
        String rcdTime;
        String teleInfo;
        String smgInfo;
        String scInfo;
        try {

            JSONObject jsonObject = new JSONObject(companyName);
            Result_code = jsonObject.getInt("code");
            if (Result_code == 200) {

                JSONArray data = jsonObject.getJSONObject("info").getJSONArray("row");
                for(int i=0;i<data.length();i++)
                {
                    JSONObject recordData=data.getJSONObject(i);

                    cpnName=recordData.getString("enterprise_name");
                    cpnTele=recordData.getString("phone");
                    rcdTime=recordData.getString("create_time");
                    teleInfo=recordData.getString("call");
                    smgInfo=recordData.getString("msg");
                    scInfo=recordData.getString("is_collect");

                    recordInfo rcdInfo=new recordInfo();
                    rcdInfo.setshanghuiInfo(cpnName,cpnTele,rcdTime,teleInfo,smgInfo,scInfo);
                    recordInfos.add(rcdInfo);
                    // Collections.reverse(recordInfos);
                }


                Message msg = new Message();
                msg.what = UPDATE;
                handler.sendMessage(msg);



            } else if (Result_code == 500) {



            }
        } catch (Exception e) {


        }


    }
}
