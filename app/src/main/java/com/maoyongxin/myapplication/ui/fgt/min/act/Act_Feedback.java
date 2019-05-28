package com.maoyongxin.myapplication.ui.fgt.min.act;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.common.BaseAct;
import com.maoyongxin.myapplication.common.MyApplication;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * 反馈意见
 */
public class Act_Feedback extends BaseAct {
    private EditText et_content;

    @Override
    protected void handlerPassMsg(int target, int target1, Object obj) {

    }

    @Override
    public int initLayoutId() {
        return R.layout.act_feedback;
    }

    @Override
    public void initView() {
        hideHeader();
        setOnClickListener(R.id.feedback_back);
        setOnClickListener(R.id.bt_Sendmessage);
        et_content = getView(R.id.et_content);
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
        switch (v.getId()) {
            case R.id.feedback_back:
                finish();
                break;
            case R.id.bt_Sendmessage://提交
                sendmsgtoserver();
                break;
        }
    }

    private void sendmsgtoserver() {
        if (TextUtils.isDigitsOnly(et_content.getText().toString())) {
            Toast.makeText(context, "老铁，请填写反馈信息！", Toast.LENGTH_SHORT).show();
            return;
        }

        OkHttpUtils.post().url("http://st.3dgogo.com/index/couple_back/setCoupleBackApi.html")
                .addParams("uid", MyApplication.getCurrentUserInfo().getUserId())
                .addParams("content", et_content.getText().toString())
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(context, "老铁，谢谢你的反馈", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                Toast.makeText(context, "老铁，谢谢你的反馈", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }
}
