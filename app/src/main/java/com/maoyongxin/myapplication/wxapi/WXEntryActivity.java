package com.maoyongxin.myapplication.wxapi;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.lykj.aextreme.afinal.utils.Debug;
import com.maoyongxin.myapplication.common.MyApplication;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import okhttp3.Call;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private static final int RETURN_MSG_TYPE_LOGIN = 1;
    private static final String secret = "e5f659e6c9b7155ca9e7ad52e3c6a753";
    private IWXAPI api;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, MyApplication.WEIXIN_APP_ID, false);
        api.handleIntent(getIntent(), this);
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    //app发送消息给微信，处理返回消息的回调
    @Override
    public void onResp(BaseResp resp) {
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                break;
            case BaseResp.ErrCode.ERR_OK:
                switch (resp.getType()) {
                    case RETURN_MSG_TYPE_LOGIN:
                        //拿到了微信返回的code,立马再去请求access_token
                        String code = ((SendAuth.Resp) resp).code;
                        //就在这个地方，用网络库什么的或者自己封的网络api，发请求去咯，注意是get请求
                        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                                "appid=" + MyApplication.WEIXIN_APP_ID +
                                "&secret=" + secret +
                                "&code=" + code +
                                "&grant_type=authorization_code";
                        postAccess_token(url);
                        break;
                }
                break;
        }
    }

    Gson gson = new Gson();

    public void postAccess_token(String url) {
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(String response, int id) {
                AccessTokenBean bean = gson.fromJson(response, AccessTokenBean.class);
                String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + bean.getAccess_token() + "&openid=" + bean.getOpenid();
                postUserBean(url, bean.getOpenid());
            }
        });
    }

    public void postUserBean(String url, final String openid) {
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(String response, int id) {
                WXUserBean userBean = gson.fromJson(response, WXUserBean.class);
                EventBusCarrier eventBusCarrier = new EventBusCarrier();
                eventBusCarrier.setOpenid(openid);
                eventBusCarrier.setUserBean(userBean);
                EventBus.getDefault().post(eventBusCarrier); //普通事件发布
                finish();
            }
        });
    }
}