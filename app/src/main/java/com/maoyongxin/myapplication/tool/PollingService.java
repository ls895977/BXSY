package com.maoyongxin.myapplication.tool;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.maoyongxin.myapplication.bean.EventMessage;
import com.maoyongxin.myapplication.common.MyApplication;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;
import okhttp3.Call;

public class PollingService extends Service {
    public static final String ACTION = "com.maoyongxin.myapplication.ui.PollingService";

    private Notification mNotification;
    private NotificationManager mManager;
    public static final String EVNET_TAG = "EventService";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("后台", "信息轮询开始");
        new PollingThread().start();
        Log.d("后台", "信息轮询开始");
        return super.onStartCommand(intent, flags, startId);


    }

    class PollingThread extends Thread {
        @Override
        public void run() {

            try {

                String uid = "";
                if (MyApplication.getCurrentUserInfo() == null || MyApplication.getCurrentUserInfo().getUserId() == null) {
                    return;
                }
                uid = MyApplication.getCurrentUserInfo().getUserId();

                if (!uid.equals("")) {
                    Log.d("后台", "即将接受"+uid+"用户的信息");
                    Getrepeat();
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("后台", "用户id错误");
            }


        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void Getrepeat() {

        String api_adress = "http://st.3dgogo.com/index/msg_alert/getMsgAlertCountApi.html";
        Log.d("后台", "准备接受回复信息");
        OkHttpUtils.get().url(api_adress)
                .addParams("uid1", MyApplication.getCurrentUserInfo().getUserId() + "")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {

                try {
                    JSONObject data = new JSONObject(response);


                    if (data.getInt("code") == 200) {

                        EventBus.getDefault().post(new EventMessage<String>(2, data.getString("count")));
                        Log.d("后台", "收到回复信息: ");
                    } else if (data.getInt("code") == 500) {

                        EventBus.getDefault().post(new EventMessage<String>(1, ""));
                        Log.d("后台", "没有收到回复 ");
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("后台", "信息接受失败 ");
                }

            }
        });
    }
}
