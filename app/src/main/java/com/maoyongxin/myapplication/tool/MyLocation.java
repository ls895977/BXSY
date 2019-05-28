package com.maoyongxin.myapplication.tool;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemClock;
import android.util.Log;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.lykj.aextreme.afinal.utils.Debug;

/**
 * 项目名称：XFKJ_Android_Project
 * 类描述：简单的封装定位请求
 * 创建人：YCM
 * 创建时间：2016/7/6 11:02
 * 修改人：Administrator
 * 修改时间：2016/7/6 11:02
 * 修改备注：
 */
public class MyLocation implements AMapLocationListener {
    Context context;
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private Intent alarmIntent = null;
    private PendingIntent alarmPi = null;
    private AlarmManager alarm = null;
    private setLocation location;

    public MyLocation(Context context) {
        this.context = context;
        locationClient = new AMapLocationClient(context);
    }

    /**
     * 单次定位
     */
    public void startSingleLocate(setLocation location) {
        this.location = location;
        locationOption = new AMapLocationClientOption();
        // 设置定位模式为高精度模式
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        locationOption.setOnceLocation(true);//设置单次定位
        // 设置定位监听
        locationClient.setLocationListener(this);
        locationClient.setLocationOption(locationOption);
        locationClient.startLocation();
    }

    /**
     * 循环定位
     */
    public void startLocate(setLocation location) {
        this.location = location;
        int alarmInterval = 5;
        locationOption = new AMapLocationClientOption();
        // 设置定位模式为高精度模式
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        // 设置定位监听
        locationClient.setLocationListener(this);
        // 创建Intent对象，action为LOCATION
        alarmIntent = new Intent();
        alarmIntent.setAction("LOCATION");
        IntentFilter ift = new IntentFilter();
        // 定义一个PendingIntent对象，PendingIntent.getBroadcast包含了sendBroadcast的动作。
        // 也就是发送了action 为"LOCATION"的intent
        alarmPi = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
        // AlarmManager对象,注意这里并不是new一个对象，Alarmmanager为系统级服务
        alarm = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        //动态注册一个广播
        IntentFilter filter = new IntentFilter();
        filter.addAction("LOCATION");
        context.registerReceiver(alarmReceiver, filter);
        locationClient.setLocationOption(locationOption);
        locationClient.startLocation();
        if (null != alarm) {
            //设置一个闹钟，2秒之后每隔一段时间执行启动一次定位程序
            alarm.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 2 * 1000,
                    alarmInterval * 1000, alarmPi);
        }
    }

    //    设置定位回调
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                location.onLocationChanged(aMapLocation);
            } else {
                Log.e("message", "定位失败！请检查定位参数！");
            }
        }
    }

    private BroadcastReceiver alarmReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("LOCATION")) {
                if (null != locationClient) {
                    locationClient.startLocation();
                }
            }
        }
    };

    public void onDestroy() {
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }

        if (null != alarmReceiver) {
            try {
                context.unregisterReceiver(alarmReceiver);
            } catch (Exception message) {
                Debug.e("重复注销广播");
            }
            alarmReceiver = null;
        }
    }

    public void stopLocation() {
        // 停止定位
        locationClient.stopLocation();
        //停止定位的时候取消闹钟
        if (null != alarm) {
            alarm.cancel(alarmPi);
        }
    }


    public interface setLocation {
        void onLocationChanged(AMapLocation aMapLocation);
    }
}
