package com.maoyongxin.myapplication.http.request;

import android.content.Context;

import com.maoyongxin.myapplication.http.ApiMgr;
import com.maoyongxin.myapplication.http.callback.EntityCallBack;
import com.maoyongxin.myapplication.http.response.PoiTypeResponse;

/**
 * Created by maoyongxin on 2017/12/1.
 */

public class ReqPoiList extends Req {
    public static void getPoiList(Context context, String httpTag,
                                  final EntityCallBack<PoiTypeResponse> callBack) {
        request(context,getCommonReqBuilder(ApiMgr.GET_POITYPE, httpTag)
                .build(), new EntityCallBack<PoiTypeResponse>() {
            @Override
            public Class<PoiTypeResponse> getEntityClass() {
                return PoiTypeResponse.class;
            }

            @Override
            public void onSuccess(PoiTypeResponse resp) {
                callBack.onSuccess(resp);
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
