package com.maoyongxin.myapplication.ui;

import android.Manifest;
import android.animation.Animator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.google.gson.Gson;
import com.lykj.aextreme.afinal.utils.ACache;
import com.lykj.aextreme.afinal.utils.Debug;
import com.lykj.aextreme.afinal.utils.MyToast;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.bean.LoginBean;
import com.maoyongxin.myapplication.bean.RegisterSFBean;
import com.maoyongxin.myapplication.common.BaseAct;
import com.maoyongxin.myapplication.common.MyApplication;
import com.maoyongxin.myapplication.http.callback.EntityCallBack;
import com.maoyongxin.myapplication.http.entity.LocationInfo;
import com.maoyongxin.myapplication.http.entity.UserInfoService;
import com.maoyongxin.myapplication.http.request.ReqRefreshToken;
import com.maoyongxin.myapplication.http.request.ReqUserServer;
import com.maoyongxin.myapplication.http.response.LoginResponse;
import com.maoyongxin.myapplication.http.response.RefreshTokenResponse;
import com.maoyongxin.myapplication.permission.RxPermissions;
import com.maoyongxin.myapplication.qq.BaseUiListener;
import com.maoyongxin.myapplication.qq.QQUserBean;
import com.maoyongxin.myapplication.tool.MyLocation;
import com.maoyongxin.myapplication.tool.UtilsTool;
import com.maoyongxin.myapplication.view.NbButton;
import com.maoyongxin.myapplication.wxapi.EventBusCarrier;
import com.tencent.connect.common.Constants;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.functions.Consumer;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import okhttp3.Call;

import static android.content.Context.MODE_PRIVATE;

public class Act_Login extends BaseAct implements BaseUiListener.BackQQData {
    private ImageView igheadview;
    private EditText mPhoneEdit, mPasswordEdit;
    private String phoneNum;
    private String pswNum;
    private ProgressDialog mProgressDialog;
    private TextView mRegister;
    private NbButton nbButtonLogin;
    private ACache aCache;

    @Override
    public int initLayoutId() {
        return R.layout.act_login;
    }

    private RxPermissions rxPermissions;

    @Override
    public void initView() {
        hideHeader();
        aCache = ACache.get(this);
        rxPermissions = new RxPermissions(getActivity());
        oprxPermissions();
        nbButtonLogin = getViewAndClick(R.id.login_bt);
        getViewAndClick(R.id.de_login_wx);
        mPhoneEdit = getView(R.id.de_login_phone);
        mPasswordEdit = getView(R.id.de_login_password);
        igheadview = getView(R.id.igheadview);
        mRegister = getViewAndClick(R.id.de_login_register);
        getViewAndClick(R.id.de_login_forgot);
        setOnClickListener(R.id.de_login_qq);
        mRegister.setOnClickListener(this);
        mProgressDialog = new ProgressDialog(context, android.R.style.Theme_DeviceDefault_Light_Dialog);
        mProgressDialog.setCancelable(false);
    }

    @Override
    public void initData() {
        EventBus.getDefault().register(this);  //事件的注册
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
            case R.id.login_bt:
                phoneNum = mPhoneEdit.getText().toString();
                pswNum = mPasswordEdit.getText().toString();
                if (TextUtils.isEmpty(phoneNum)) {
                    MyToast.show(context, "请输入账号");
                    return;
                }
                if (TextUtils.isEmpty(pswNum)) {
                    MyToast.show(context, "请输入密码");
                    return;
                }
                if (pswNum.length() < 6) {
                    MyToast.show(context, "密码必须大于6位");
                    return;
                }
                doLogin();
                break;
            case R.id.de_login_register://注册
                startAct(Act_Register.class);
                break;
            case R.id.de_login_forgot://找回密码
                startAct(Acti_findpasswd.class);
                break;
            case R.id.de_login_wx://微信登录
                try {
                    loginWX();
                } catch (Exception e) {
                }
                break;
            case R.id.de_login_qq://QQ登录
                loginQQ();
                break;
        }
    }

    @Override
    protected void handlerPassMsg(int target, int target1, Object obj) {

    }

    public SharedPreferences getSp(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("userInfo", MODE_PRIVATE);
        return sharedPreferences;
    }

    /**
     * 融云登录
     */
    UserInfoService resp;

    private void doLogin() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                /**
                 * 延时执行的代码
                 */
                nbButtonLogin.setClickable(false);
                OkHttpUtils.post().url("http://bisonchat.com/index/user_login/login.html")
                        .addParams("userId", phoneNum)
                        .addParams("password", pswNum)
                        .build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        e.printStackTrace();
                        nbButtonLogin.setClickable(true);
                        //mProgressDialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (response.contains("亲！账户或密码错误！")) {
                            MyToast.show(context, "亲！账户或密码错误！");

                            nbButtonLogin.setClickable(true);
                            return;
                        }
                        Gson gson = new Gson();
                        LoginBean loginBean = gson.fromJson(response, LoginBean.class);
                        aCache.put("phone", phoneNum);
                        aCache.put("pwd", pswNum);
                        MyApplication.setLogNum(phoneNum);
                        MyApplication.setLogPsw(pswNum);
                        MyApplication.setMyPassword(pswNum);
                        resp = new UserInfoService();
                        resp.setUserId(loginBean.getUserInfo().getUserId());

                        resp.setLongitude(loginBean.getUserInfo().getLongitude());
                        resp.setLatitude(loginBean.getUserInfo().getLatitude());

                        resp.setToken(loginBean.getUserInfo().getBrithday());
                        resp.setCreateDate(loginBean.getUserInfo().getCreateDate());
                        resp.setUserPhone(loginBean.getUserInfo().getUserPhone());
                        resp.setVipNum(loginBean.getUserInfo().getVipNum());
                        resp.setNote(loginBean.getUserInfo().getNote());
                        resp.setHeadImg(loginBean.getUserInfo().getHeadImg());
                        resp.setUserName(loginBean.getUserInfo().getUserName());
                        resp.setSex(loginBean.getUserInfo().getSex());
                        resp.setBrithday(loginBean.getUserInfo().getBrithday());
                        resp.setPermanent_city(loginBean.getUserInfo().getPermanent_city());
                        resp.setPosition(loginBean.getUserInfo().getPosition());
                        MyApplication.setCurrentUserInfo(resp);

                        connect(context, resp.getToken(), resp.getUserId());
                        backdatatoserver();
                        mProgressDialog.dismiss();
                    }
                });


            }
        }).start();

    }

    /**
     * <p>连接服务器，在整个应用程序全局，只需要调用一次，需在 {@linkiminit(Context)} 之后调用。</p>
     * <p>如果调用此接口遇到连接失败，SDK 会自动启动重连机制进行最多10次重连，分别是1, 2, 4, 8, 16, 32, 64, 128, 256, 512秒后。
     * 在这之后如果仍没有连接成功，还会在当检测到设备网络状态变化时再次进行重连。</p>
     *
     * @param token 从服务端获取的用户身份令牌（Token）。
     * @return RongIM  客户端核心类的实例。
     */
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
                            nbButtonLogin.setClickable(true);
                            dolginBisonchat(userId);
                        }
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        dolginBisonchat(userId);
                        e.printStackTrace();
                    }

                    @Override
                    public void onCancelled(Throwable e) {

                        e.printStackTrace();
                    }

                    @Override
                    public void onFinished() {

                    }
                });
            }

            /**
             * 连接融云成功
             * @param userid 当前 token 对应的用户 id
             */
            @Override
            public void onSuccess(String userid) {
                Debug.e("-------------融云登陆成功！");
                if (MyApplication.getMyCurrentLocation() == null) {
                    nbButtonLogin.setClickable(true);
                    MyToast.show(context, "获取位置信息失败，已使用默认位置登录");

                    MyApplication.setMyCurrentLocation(new LocationInfo());
                    dolginBisonchat(userid);
                } else {
                    dolginBisonchat(userid);
                }
            }

            /**
             * 连接融云失败
             * @param errorCode 错误码，可到官网 查看错误码对应的注释
             */
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.d("LoginActivity", "--onError--ErrorCode：" + errorCode);
            }
        });
    }

    private android.os.Handler handler = new android.os.Handler();
    private Animator animator;

    private void backdatatoserver() {
        OkHttpUtils.post().url("http://st.3dgogo.com/index/action_log/setActionLogApi.html")
                .addParams("action_uid", phoneNum)
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

    private static IWXAPI WXapi;

    /**
     * 微信 登录
     */
    public void loginWX() {
//        try {
//            if (!UtilsTool.isWeixinAvilible(this)) {
//                Toast.makeText(getActivity(), "您还未安装微信客户端", Toast.LENGTH_SHORT).show();
//                return;
//            }
        WXapi = WXAPIFactory.createWXAPI(this, MyApplication.WEIXIN_APP_ID, true);
        WXapi.registerApp(MyApplication.WEIXIN_APP_ID);
        //微信登录
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "diandi_wx_login";
        //像微信发送请求
        WXapi.sendReq(req);
//        } catch (Exception e) {
//            MyToast.show(getActivity(), "本机微信版本不支持，请注册账号或者qq登录");
//        }
//先判断是否安装微信APP,按照微信的说法，目前移动应用上微信登录只提供原生的登录方式，需要用户安装微信客户端才能配合使用。

    }

    String USericon;
    String userName;

    // 微信成功返回参数
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleEvent(EventBusCarrier carrier) {
        userName = carrier.getUserBean().getNickname();
        String openid = (String) carrier.getOpenid();
        USericon = (String) carrier.getUserBean().getHeadimgurl();
        getOpenidApi(openid, "", USericon);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this); //解除注册
        super.onDestroy();
    }

    /**
     * 判
     * 断是否绑定oping
     *
     * @param wx_openid
     * @param qq_openid
     */
    Gson gson = new Gson();

    public void getOpenidApi(final String wx_openid, final String qq_openid, final String userICon) {
        userIcon1 = userICon;
        OkHttpUtils.post().url("http://st.3dgogo.com/index/user/getOpenidApi/")
                .addParams("wx_openid", wx_openid)
                .addParams("qq_openid", qq_openid)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                if (response.contains("null")) {//没有绑定
                    Debug.e("-----------绑定微信---" + userICon + "------userName===" + userName);
                    bindOpenApi(wx_openid, qq_openid, userICon);
                } else if (response.contains("200")) {//绑定
                    RegisterSFBean bean = gson.fromJson(response, RegisterSFBean.class);
                    phoneNum = bean.getInfo().getUserId();
                    pswNum = bean.getInfo().getPassword();
                    doLogin();
                }
            }
        });
    }

    /**
     * 绑定
     *
     * @param wx_openid
     * @param qq_openid
     * @param userIcon
     */
    public void bindOpenApi(final String wx_openid, final String qq_openid, final String userIcon) {
        OkHttpUtils.post().url("http://st.3dgogo.com/index/user/bindingOpenidApi.html")
                .addParams("wx_openid", wx_openid)
                .addParams("qq_openid", qq_openid)
                .addParams("userName", userName)
                .addParams("headImg", userIcon)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                if (response.contains("null")) {
                } else if (response.contains("200")) {
                    RegisterSFBean bean = gson.fromJson(response, RegisterSFBean.class);
                    phoneNum = bean.getInfo().getUserId();
                    pswNum = bean.getInfo().getPassword();
                    mPhoneEdit.setText(phoneNum + "");
                    mPasswordEdit.setText(pswNum + "");
                    MyToast.show(context, "第三方软件登陆中");
                    doLogin();
                }
            }
        });
    }

    private String userIcon1;
    /**
     * QQ 登录
     */
    Tencent mTencent;

    public void loginQQ() {
        mTencent = Tencent.createInstance("101539710", getApplicationContext());
        if (!mTencent.isSessionValid()) {
            mTencent.login(this, "all", new BaseUiListener(mTencent, getApplicationContext(), this));
        }
    }

    //QQ登录返回结果
    @Override
    public void backQQData(QQUserBean qqUserBean, String UserId) {
        userName = qqUserBean.getNickname();
        getOpenidApi("", UserId, qqUserBean.getFigureurl_2());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Tencent.onActivityResultData(requestCode, resultCode, data, new BaseUiListener(mTencent, getApplicationContext(), this));
        if (requestCode == Constants.REQUEST_API) {
            if (resultCode == Constants.REQUEST_LOGIN) {
                Tencent.handleResultData(data, new BaseUiListener(mTencent, getApplicationContext(), this));
            }
        }
    }

    /**
     * 打开权限
     */
    public void oprxPermissions() {
        rxPermissions
                .request(Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) {
                        if (aBoolean) {
                            MyLocation myLocation = new MyLocation(getApp());
                            myLocation.startLocate(new MyLocation.setLocation() {
                                @Override
                                public void onLocationChanged(AMapLocation aMapLocation) {
                                    //可在中解析amapLocation获取相应内容。
                                    LocationInfo myCurrentLocation = new LocationInfo();
                                    myCurrentLocation.setaMapLocation(aMapLocation);
                                    myCurrentLocation.setAddress(aMapLocation.getAddress());
                                    myCurrentLocation.setLatitude(aMapLocation.getLatitude() + "");
                                    myCurrentLocation.setLngtitude(aMapLocation.getLongitude() + "");
                                    myCurrentLocation.setCityName(aMapLocation.getCity());
                                    myCurrentLocation.setAdCode(aMapLocation.getAdCode());
                                    MyApplication.setMyCurrentLocation(myCurrentLocation);
                                }
                            });
                        } else {
                            Toast.makeText(getActivity(), "开启位置后，可以获得更精准的数据哦", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void dolginBisonchat(final String userId) {
        nbButtonLogin.startAnim();
        // MyToast.show(context, "登录成功");
        nbButtonLogin.startAnim();
        int xc = (nbButtonLogin.getLeft() + nbButtonLogin.getRight()) / 2;
        int yc = (nbButtonLogin.getTop() + nbButtonLogin.getBottom()) / 2;
        animator = ViewAnimationUtils.createCircularReveal(nbButtonLogin, xc, yc, 0, 1111);
        animator.setDuration(100);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.putExtra("userId", userId + "");
                        startActivity(intent);
                        overridePendingTransition(R.anim.scale_in, R.anim.anim_out);
                        finish();
                    }
                }, 1000);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }
}
