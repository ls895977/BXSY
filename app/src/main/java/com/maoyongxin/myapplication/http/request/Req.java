package com.maoyongxin.myapplication.http.request;

import android.content.Context;
import android.content.Intent;

import com.jky.baselibrary.http.BaseHttpMgr;
import com.jky.baselibrary.http.Request;
import com.jky.baselibrary.http.callback.HttpCommonCallback;
import com.jky.baselibrary.http.response.StringResponse;
import com.jky.baselibrary.util.common.GsonUtil;
import com.lykj.aextreme.afinal.utils.Debug;
import com.maoyongxin.myapplication.common.MyApplication;
import com.maoyongxin.myapplication.http.ApiMgr;
import com.maoyongxin.myapplication.http.callback.EntityCallBack;
import com.maoyongxin.myapplication.http.manager.Managers;
import com.maoyongxin.myapplication.http.response.BaseResp;


/**
 * Created by dingke on 2017/8/3.
 */

public class Req {
    public static Request.Builder getCommonReqBuilder(int api, String httpTag) {

        return new Request.Builder()
                .url(ApiMgr.getApiUrl(api))
                .method(BaseHttpMgr.POST)
                .tag(httpTag);
    }

    public static Request.Builder getCommonReqBuilder(String api, String httpTag) {
        return new Request.Builder()
                .url(api)
                .method(BaseHttpMgr.POST)
                .tag(httpTag);
    }

    public static void request(Context context, Request request, final EntityCallBack callBack) {
        Managers.getHttpMgr().stringRequest(context, request, new HttpCommonCallback<StringResponse>() {
            @Override
            public void onSuccess(StringResponse resp) {
                BaseResp t = (BaseResp) GsonUtil.json2Entity(resp.getString(), callBack.getEntityClass());
                if (t.isBadToken())
                    MyApplication.getInstance().sendBroadcast(new Intent(MyApplication.BROADCAST_BAD_TOKEN));
                callBack.onSuccess(t);
            }

            @Override
            public void onFailure(Throwable e) {
                callBack.onFailure(e);
            }

            @Override
            public void onCancelled(Throwable e) {
                callBack.onCancelled(e);
            }

            @Override
            public void onFinished() {
                callBack.onFinished();
            }
        });
    }
}
