package com.maoyongxin.myapplication.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lykj.aextreme.afinal.utils.ACache;
import com.lykj.aextreme.afinal.utils.Debug;
import com.lykj.aextreme.afinal.utils.MyToast;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.bean.LoginBean;
import com.maoyongxin.myapplication.bean.SendSmsAppBean;
import com.maoyongxin.myapplication.common.BaseAct;
import com.maoyongxin.myapplication.common.MyApplication;
import com.maoyongxin.myapplication.http.callback.EntityCallBack;
import com.maoyongxin.myapplication.http.entity.LocationInfo;
import com.maoyongxin.myapplication.http.entity.UserInfoService;
import com.maoyongxin.myapplication.http.request.ReqRefreshToken;
import com.maoyongxin.myapplication.http.request.ReqUserServer;
import com.maoyongxin.myapplication.http.response.LoginResponse;
import com.maoyongxin.myapplication.http.response.RefreshTokenResponse;
import com.maoyongxin.myapplication.tool.AMUtils;
import com.maoyongxin.myapplication.tool.DownTimer;
import com.maoyongxin.myapplication.tool.DownTimerListener;
import com.maoyongxin.myapplication.tool.clickanimview.BamTextView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import okhttp3.Call;

public class Acti_findpasswd extends BaseAct   implements DownTimerListener {
    private EditText phonenum,bregister_code, newpasswd,newpsswdagain;
    private BamTextView next_bt;
    private String stPhone;
    private Gson gson;
    private TextView getcode;
    private String smsCode;
    private ProgressDialog mProgressDialog;
    private String userId,pswNum;
    private ACache aCache;
    @Override
    public void initView() {
        phonenum=getView(R.id.find_phone);
        getcode=getViewAndClick(R.id.btv_getcode) ;
        bregister_code=getView(R.id.bregister_code);
        next_bt=getViewAndClick(R.id.next_bt);
        bregister_code=getView(R.id.bregister_code);
        aCache = ACache.get(this);
        newpasswd=getView(R.id.newpasswd);
        newpsswdagain=getView(R.id.newpasswdagain);
    }

    @Override
    public void initData() {

        mProgressDialog = new ProgressDialog(this, android.R.style.Theme_DeviceDefault_Dialog);
        mProgressDialog.setCancelable(true);

    }

    @Override
    public void requestData() {

    }

    @Override
    public void updateUI() {

    }

    @Override
    public void onViewClick(View v) {
        switch (v.getId())
        {
            case R.id.btv_getcode :
                postBackCode();
                break;

            case R.id.next_bt:


                setNewpasswdserver();


                break;

        }
    }

    @Override
    public int initLayoutId() {
        return(R.layout.activity_acti_findpasswd);
    }

    @Override
    protected void handlerPassMsg(int target, int target1, Object obj) {

    }
    boolean isBright = false;
    private SendSmsAppBean codeBean;
    @Override
    public void onTick(long millisUntilFinished) {
        getcode.setText(String.valueOf(millisUntilFinished / 1000) + "s后再试");
        if (String.valueOf(millisUntilFinished / 1000).equals("0"))
        {
            getcode.setClickable(true);
        }
        isBright = false;
    }

    @Override
    public void onFinish() {

    }

    public void postBackCode() {
        if (getcode.isClickable())
        {
            stPhone = phonenum.getText().toString();
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
            try{
                OkHttpUtils.post().url("http://bisonchat.com/index/send_sms/sendSmsApp.html")
                        .addParams("phone", stPhone)
                        .build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("aa", "-------onSuccess--" + response);

                        try {

                            JSONObject data = new JSONObject(response);
                            if (data.getInt("code") == 200) {
                                MyToast.show(getActivity(),data.getString("msg"));

                                smsCode=data.getInt("smsCode")+"";




                            } else if (data.getInt("code") == 500) {


                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        MyToast.show(getActivity(),"发送成功请稍等");
                        getcode.setClickable(false);

                    }
                });
            }
            catch (Exception e)
            {

            }

        }

        else
        {
            MyToast.show(getActivity(),"发送成功请稍等");
        }
        /*
         */
    }


    private void setNewpasswdserver()
    {
        if(newpasswd.getText()!=null&&!newpasswd.getText().equals(""))
        {
            if(newpsswdagain.getText()!=null&&!newpasswd.getText().equals(""))
            {

                if(!newpsswdagain.getText().equals(newpasswd.getText()))
                {
                    if(stPhone!=null&&!stPhone.equals(""))
                    {
                        if(bregister_code.getText()!=null&&!bregister_code.getText().equals("")&&!bregister_code.equals(smsCode))
                        {
                            try {
                                OkHttpUtils.post().url("http://bisonchat.com/index/user/retrievePasswordApi.html")
                                        .addParams("userPhone", stPhone + "")
                                        .addParams("password", newpasswd.getText().toString())
                                        .addParams("tpassword", newpsswdagain.getText().toString())
                                        .build().execute(new StringCallback() {
                                    @Override
                                    public void onError(Call call, Exception e, int id) {

                                    }

                                    @Override
                                    public void onResponse(String response, int id) {

                                        try {

                                            JSONObject data = new JSONObject(response);
                                            if (data.getInt("code") == 200) {
                                                MyToast.show(getActivity(),data.getString("msg"));
                                                if(data.getBoolean("operation"))
                                                {
                                                    MyToast.show(getActivity(),"修改成功");
                                                    userId=data.getString("userId");
                                                    aCache.put("phone", userId);
                                                    aCache.put("pwd", newpasswd.getText().toString());
                                                    doLogin();
                                                }
                                                else{
                                                    MyToast.show(getActivity(),"操作失败，原因"+data.getString("msg"));
                                                }
                                            } else if (data.getInt("code") == 500) {


                                            }

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                            catch (Exception e)
                            {
                                MyToast.show(getActivity(),"网络请求失败");
                            }
                        }
                        else
                        {
                            MyToast.show(getActivity(),"验证码输入有误");
                        }
                    }
                    else
                    {
                        MyToast.show(getActivity(),"手机号码不能为空");
                    }

                }
                else
                {
                    MyToast.show(getActivity(),"两次密码不一致");
                }

            }
            else
            {
                MyToast.show(getActivity(),"确认密码不能为空");
            }

        }
        else
        {
            MyToast.show(getActivity(),"密码不能为空");
        }
    }


    UserInfoService resp;
    private void doLogin() {
        mProgressDialog.show();
        if (aCache.getAsString("phone") != null) {
            userId = aCache.getAsString("phone");
            pswNum = aCache.getAsString("pwd");
        } else {
            userId = "";
            pswNum = "";
        }
        OkHttpUtils.post().url("http://bisonchat.com/index/user_login/login.html")
                .addParams("userId", userId)
                .addParams("password", pswNum)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(String response, int id) {
                Gson gson = new Gson();
                LoginBean loginBean = gson.fromJson(response, LoginBean.class);
                if (loginBean.isOperation() == false) {
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), Act_Login.class);
                    startActivity(intent);
                    finish();
                    return;
                }
                aCache.put("phone", userId);
                aCache.put("pwd", pswNum);

                MyApplication.setLogNum(userId);
                MyApplication.setLogPsw(pswNum);
                MyApplication.setMyPassword(pswNum);
                resp = new UserInfoService();
                resp.setUserId(loginBean.getUserInfo().getUserId());
                resp.setUserName(loginBean.getUserInfo().getUserName());
                resp.setLongitude(loginBean.getUserInfo().getLongitude());
                resp.setLatitude(loginBean.getUserInfo().getLatitude());
                resp.setToken(loginBean.getUserInfo().getBrithday());
                resp.setCreateDate(loginBean.getUserInfo().getCreateDate());
                resp.setUserPhone(loginBean.getUserInfo().getUserPhone());
                resp.setVipNum(loginBean.getUserInfo().getVipNum());
                resp.setNote(loginBean.getUserInfo().getNote());
                resp.setHeadImg(loginBean.getUserInfo().getHeadImg());
                resp.setBackground_img(loginBean.getUserInfo().getBackground_img());
                resp.setSex(loginBean.getUserInfo().getSex());
                resp.setBrithday(loginBean.getUserInfo().getBrithday());
                resp.setPermanent_city(loginBean.getUserInfo().getPermanent_city());
                resp.setPosition(loginBean.getUserInfo().getPosition());
                MyApplication.setCurrentUserInfo(resp);

                connect(getApplicationContext(), resp.getToken(), resp.getUserId());
                backdatatoserver();


            }
        });
    }
    private void backdatatoserver() {
        OkHttpUtils.post().url("http://bisonchat.com/index/action_log/setActionLogApi.html")
                .addParams("action_uid", userId)
                .addParams("action_user_name", MyApplication.getCurrentUserInfo().getUserName())
                .addParams("action_type", "1")
                .addParams("action_remarks", "APP内登陆")
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
    public void connect(final Context context, String token, final String userId) {
        RongIM.connect(token, new RongIMClient.ConnectCallback() {

            /**
             * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
             * 2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
             */
            @Override
            public void onTokenIncorrect() {
                ReqRefreshToken.refreshToken(context, getActivityTag(), userId, new EntityCallBack<RefreshTokenResponse>() {
                    @Override
                    public Class<RefreshTokenResponse> getEntityClass() {
                        return RefreshTokenResponse.class;
                    }

                    @Override
                    public void onSuccess(RefreshTokenResponse resp) {
                        if (resp.is200()) {
                            connect(context, resp.obj.getToken(), resp.obj.getUserId() + "");
                        } else {
                            MyToast.show(context, resp.msg);
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
             * 连接融云成功
             *
             * @param userid 当前 token 对应的用户 id
             */
            @Override
            public void onSuccess(String userid) {
                if (MyApplication.getMyCurrentLocation() == null) {

                    MyToast.show(context, "登录成功");
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("userId", userId);
                    MyApplication.setMyCurrentLocation( new LocationInfo());
                    mProgressDialog.dismiss();
                    startActivity(intent);
                    finish();

                } else {
                    MyToast.show(context, "登录成功");
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("userId", userId);
                    mProgressDialog.dismiss();
                    startActivity(intent);
                    finish();
                }
            }

            /**
             * 连接融云失败
             * @param errorCode 错误码，可到官网 查看错误码对应的注释
             */
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Debug.e("-----onError--ErrorCode：" + errorCode.getValue());
            }
        });

    }

}
