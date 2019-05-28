package com.maoyongxin.myapplication.common;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.jky.baselibrary.util.image.glide.GlideCircleTransform;
import com.lykj.aextreme.afinal.common.BaseApplication;
import com.maoyongxin.myapplication.http.entity.LocationInfo;
import com.maoyongxin.myapplication.http.entity.UserInfoService;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhouyou.http.EasyHttp;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import cn.jiguang.share.android.api.JShareInterface;
import cn.jiguang.share.android.api.PlatformConfig;
import io.rong.imageloader.cache.disc.impl.UnlimitedDiskCache;
import io.rong.imageloader.cache.disc.naming.Md5FileNameGenerator;
import io.rong.imageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import io.rong.imageloader.core.DisplayImageOptions;
import io.rong.imageloader.core.ImageLoader;
import io.rong.imageloader.core.ImageLoaderConfiguration;
import io.rong.imageloader.core.assist.ImageScaleType;
import io.rong.imageloader.core.assist.QueueProcessingType;
import io.rong.imageloader.core.download.BaseImageDownloader;
import io.rong.imkit.RongIM;

public class MyApplication extends BaseApplication {
    public static final String WEIXIN_APP_ID = "wxb0657547b9176773";
    private static final String WEIXIN_APP_SECRET = "e1bf85e8ddf5550e9b623ba6082dff95";
    public static final int NORESULT = 0;
    public static final int ISREGISTER_OK = 1;
    public static final int POISEARCH_OK = 2;
    public static final int POISEARCH_LIST_OK = 3;
    public static final int UPLOAD_INTERESTOK = 4;
    public static final int GETMY_USERINFO = 5;
    public static final int GETMY_USERINFO_OK = 6;
    public static final int NOCHANGE_USERINFO = 7;
    private static MyApplication app;
    private static LocationInfo myCurrentLocation;
    private static OnAddressGetListener addressGetListener;
    private static UserInfoService sCurrentUserInfo;
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        EasyHttp.init(this);
        //初始化环境
        AppConfig.initEnvironment();
        sCircleTransform = new GlideCircleTransform(this);
        //Android N以上版本拍照相册Uri问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        //初始化xUtils
        org.xutils.x.Ext.init(this);
        org.xutils.x.Ext.setDebug(AppConfig.DEBUG);
        sCircleTransform = new GlideCircleTransform(this);
        RongIM.init(this);
        initLocationInfo();
        initUniversalImageLoader();
        closeAndroidPDialog();
        initJiguangShare();
        regToWx();//微信

    }
    // IWXAPI 是第三方app和微信通信的openApi接口
    private IWXAPI api;
    private void regToWx() {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, WEIXIN_APP_ID, true);

        // 将应用的appId注册到微信
        api.registerApp(WEIXIN_APP_ID);
        //建议动态监听微信启动广播进行注册到微信
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // 将该app注册到微信
                api.registerApp(WEIXIN_APP_ID);
            }
        }, new IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP));
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static IWXAPI mWxApi;
    /**
     * 微信
     */
    private void registerToWX() {
        //第二个参数是指你应用在微信开放平台上的AppID
        mWxApi = WXAPIFactory.createWXAPI(this, WEIXIN_APP_ID, false);
        // 将该app注册到微信
        mWxApi.registerApp(WEIXIN_APP_ID);
    }
    private void initUniversalImageLoader() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(new ColorDrawable(Color.parseColor("#f0f0f0")))
                .resetViewBeforeLoading(true)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        int memClass = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE))
                .getMemoryClass();
        int memCacheSize = 1024 * 1024 * memClass / 8;

        File cacheDir = new File(Environment.getExternalStorageDirectory().getPath() + "/jiecao/cache");
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .threadPoolSize(3)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .memoryCache(new UsingFreqLimitedMemoryCache(memCacheSize))
                .memoryCacheSize(memCacheSize)
                .diskCacheSize(50 * 1024 * 1024)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .diskCache(new UnlimitedDiskCache(cacheDir))
                .imageDownloader(new BaseImageDownloader(this, 5 * 1000, 30 * 1000))
                .defaultDisplayImageOptions(options)
                .build();
        ImageLoader.getInstance().init(config);
    }

    public static LocationInfo getMyCurrentLocation() {
        if (myCurrentLocation == null) {
            return null;
        } else {
            return myCurrentLocation;
        }
    }

    public static void setMyCurrentLocation(LocationInfo myCurrentLocation) {
        MyApplication.myCurrentLocation = myCurrentLocation;
    }
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    //可在中解析amapLocation获取相应内容。
                    myCurrentLocation = new LocationInfo();
                    myCurrentLocation.setaMapLocation(aMapLocation);
                    myCurrentLocation.setCityCode(aMapLocation.getCityCode());
                    myCurrentLocation.setAdCode(aMapLocation.getAdCode());
                    myCurrentLocation.setAddress(aMapLocation.getAddress());
                    myCurrentLocation.setLatitude(aMapLocation.getLatitude() + "");
                    myCurrentLocation.setLngtitude(aMapLocation.getLongitude() + "");
                    myCurrentLocation.setCityName(aMapLocation.getCity());
                    myCurrentLocation.setAdCode(aMapLocation.getAdCode());
                    setMyCurrentLocation(myCurrentLocation);
                    addressGetListener.getAddress(true);
                } else {
                    //    com.lykj.aextreme.afinal.utils.Debug.e("--------定位失败时---"+aMapLocation.getErrorCode());
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    //    Log.d("locErro", "\"AmapError\", \"location Error, ErrCode:\" + aMapLocation.getErrorCode() + \", errInfo:\" + aMapLocation.getErrorInfo(): ");
                    addressGetListener.getAddress(true);
                    setMyCurrentLocation(new LocationInfo());
                }
            }
        }
    };

    public void initLocationInfo() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        initLocationOption();
        //启动定位
        mLocationClient.startLocation();
    }
    public void startLocation (){
        //启动定位
        mLocationClient.startLocation();
    }

    private void initLocationOption() {
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
        // 该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //获取最近3s内精度最高的一次定位结果：
        // 设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        //mLocationOption.setOnceLocationLatest(true);
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption.setInterval(1000);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否允许模拟位置,默认为true，允许模拟位置
        // mLocationOption.setMockEnable(true);
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        // mLocationOption.setHttpTimeOut(20000);
        //关闭缓存机制
        //mLocationOption.setLocationCacheEnable(false);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
    }

    private static MyApplication sApplication;

    public static MyApplication getInstance() {
        return sApplication;
    }

    /* 广播 */
    public static   String BROADCAST_BAD_TOKEN = "BROADCAST_BAD_TOKEN";

    public static MyApplication getApp() {
        return app;
    }

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    public static String logNum;
    public static String logPsw;
    public static String updatemsg;
    public static String mDownloadUrl;
    private static String myPassword;
    public static void setmDownloadUrl(String DownloadUrl) {
        MyApplication.mDownloadUrl = DownloadUrl;
    }

    public static String getmDownloadUrl() {
        return mDownloadUrl;
    }


    public static void setUpdatemsg(String UpdateMsg) {
        MyApplication.updatemsg = UpdateMsg;
    }

    public static String getUpdatemsg() {
        return updatemsg;
    }


    public static String getLogNum() {
        return logNum;
    }

    public static void setLogNum(String logNum) {
        MyApplication.logNum = logNum;
    }

    public static String getLogPsw() {
        return logPsw;
    }

    public static void setLogPsw(String logPsw) {
        MyApplication.logPsw = logPsw;
    }

    public static String getMyPassword() {
        return myPassword;
    }

    public static void setMyPassword(String myPassword) {
        MyApplication.myPassword = myPassword;
    }

    public static void setOnAddressGetListener(OnAddressGetListener onAddressGetListener) {
        MyApplication.addressGetListener = onAddressGetListener;
    }

    public interface OnAddressGetListener {
        void getAddress(boolean isGetAddress);
    }

    private static GlideCircleTransform sCircleTransform;
    public static void setCurrentUserInfo(UserInfoService currentUserInfo) {
        MyApplication.sCurrentUserInfo = currentUserInfo;
    }
    public static GlideCircleTransform getCircleTransform() {
        return sCircleTransform;
    }
    public static UserInfoService getCurrentUserInfo() {
        return sCurrentUserInfo;
    }

    /**
     * 解决androidP 第一次打开程序出现莫名弹窗
     * 弹窗内容“detected problems with api ”
     */
    private void closeAndroidPDialog(){
        try {
            Class aClass = Class.forName("android.content.pm.PackageParser$Package");
            Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
            declaredConstructor.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
            declaredMethod.setAccessible(true);
            Object activityThread = declaredMethod.invoke(null);
            Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initJiguangShare() {
        JShareInterface.setDebugMode(true);
        PlatformConfig platformConfig = new PlatformConfig()
//                .setQQ(QQ_APP_ID, QQ_APP_KEY)
                .setWechat(WEIXIN_APP_ID, WEIXIN_APP_SECRET);
//                .setSinaWeibo(WEIBO_APP_KEY, WEIXIN_APP_SECRET, WEIBO_REDIRECT_URL);
        JShareInterface.init(this, platformConfig);

        List<String> successPlatform = JShareInterface.getPlatformList();
        for (String s : successPlatform) {
            //Log.e("---", "===>>" + s);
        }
    }




}
