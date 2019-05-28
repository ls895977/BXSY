package com.maoyongxin.myapplication.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lykj.aextreme.afinal.utils.ACache;
import com.lykj.aextreme.afinal.utils.MyToast;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.bean.SendSmsAppBean;
import com.maoyongxin.myapplication.common.BaseAct;
import com.maoyongxin.myapplication.tool.AMUtils;
import com.maoyongxin.myapplication.tool.DownTimer;
import com.maoyongxin.myapplication.tool.DownTimerListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import okhttp3.Call;

/**
 * 注册
 */
public class Act_Register extends BaseAct implements DownTimerListener {
    TextView code;
    EditText phone, pwd, ppwd, nickName, et_code;
    String stPhone;
    private Gson gson;
    private ACache aCache;
    @Override
    public int initLayoutId() {
        return R.layout.act_register;
    }

    @Override
    public void initView() {
        hideHeader();
        aCache = ACache.get(this);
        getViewAndClick(R.id.register_bt);
        setOnClickListener(R.id.mylogin);
        code = getViewAndClick(R.id.tv_getcode);
        phone = getView(R.id.register_phone);
        pwd = getView(R.id.register_psd);
        ppwd = getView(R.id.register_ppsd);
        nickName = getView(R.id.register_nickName);
        et_code = getView(R.id.register_code);
    }

    @Override
    public void initData() {
        gson = new Gson();
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
            case R.id.register_bt://注册按钮
                doRegister();
                break;
            case R.id.tv_getcode:
                postBackCode();
                break;
            case R.id.mylogin:
                finish();
                break;
        }
    }

    /**
     * 验证码
     */
    boolean isBright = false;
    private SendSmsAppBean codeBean;

    public void postBackCode() {
        stPhone = phone.getText().toString();
        if (TextUtils.isEmpty(stPhone)) {
            MyToast.show(context, "手机号不能为空！");
            return;
        }
        if (!AMUtils.isMobile(stPhone) && isBright == false) {
            Toast.makeText(context, R.string.Illegal_phone_number, Toast.LENGTH_SHORT).show();
            return;
        }
        DownTimer downTimer = new DownTimer();
        downTimer.setListener(this);
        downTimer.startDown(60 * 1000);
        OkHttpUtils.post().url("http://st.3dgogo.com/index/send_sms/sendSmsApp.html")
                .addParams("phone", stPhone)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                Log.e("aa", "-------onSuccess--" + response);
                codeBean = gson.fromJson(response, SendSmsAppBean.class);
                if (codeBean.getCode() == 200) {
                    MyToast.show(context, codeBean.getMsg());
                }
            }
        });
    }

    /**
     * 倒计时返回类
     */
    @Override
    public void onTick(long millisUntilFinished) {
        code.setText(String.valueOf(millisUntilFinished / 1000) + "s后再试");
        isBright = false;
    }

    @Override
    public void onFinish() {
        code.setText(R.string.get_code);
        isBright = true;
    }

    String stPsd, stPPsd, stNickName, stCode;

    private void doRegister() {
        stNickName = nickName.getText().toString();
        stCode = et_code.getText().toString();
        stPhone = phone.getText().toString();
        stPsd = pwd.getText().toString();
        stPPsd = ppwd.getText().toString();
        if (TextUtils.isEmpty(stNickName)) {
            MyToast.show(context, "请输入昵称！");
            return;
        }
        if (TextUtils.isEmpty(stCode)) {
            MyToast.show(context, "请输入获取的验证码！");
            return;
        }
        if (TextUtils.isEmpty(stPhone)) {
            MyToast.show(context, "请输入手机号码！");
            return;
        }
        if (TextUtils.isEmpty(stPsd)) {
            MyToast.show(context, "请输入密码！");
            return;
        }
        if (TextUtils.isEmpty(stPPsd)) {
            MyToast.show(context, "请再次输入密码！");
            return;
        }
        if (!stPsd.equals(stPPsd)) {
            MyToast.show(context, "两次密码输入不一致！");
            return;
        }

        if (!stCode.equals(codeBean.getSmsCode() + "")) {
            MyToast.show(context, "验证码输入不正确！");
            return;
        }
        OkHttpUtils.post().url("http://st.3dgogo.com/index/registered/andUserApi.html")
                .addParams("userName", stNickName)
                .addParams("userPhone", stPhone)
                .addParams("note", "1")
                .addParams("password", stPsd)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject data = new JSONObject(response);
                    String msg = data.getString("msg");
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

                    int code = data.getInt("code");
                    if (code == 200) {

                        aCache.put("phone", data.getString("userId"));
                        aCache.put("pwd", stPPsd);

                        Intent intent = new Intent(context, Act_Regesite_Headim.class);
                        intent.putExtra("userId", data.getString("userId"));
                        intent.putExtra("nickname", stNickName);
                        intent.putExtra("userpsswd", stPPsd);
                        intent.putExtra("userphone", stPhone);

                        backdatatoserver(data.getString("userId"), stNickName, stPhone);



                        startActivity(intent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void backdatatoserver(final String uid, final String uname, final String telenum) {
        OkHttpUtils.post().url("http://st.3dgogo.com/index/action_log/setActionLogApi.html")
                .addParams("action_uid", uid)
                .addParams("action_user_name", uname)
                .addParams("action_type", "2")
                .addParams("action_ip", telenum)
                .addParams("action_remarks", "新用户注册")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(String response, int id) {
            }
        });
    }

    @Override
    protected void handlerPassMsg(int target, int target1, Object obj) {

    }
}
