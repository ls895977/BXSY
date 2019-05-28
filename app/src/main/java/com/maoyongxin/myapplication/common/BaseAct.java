package com.maoyongxin.myapplication.common;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.lykj.aextreme.afinal.common.BaseActivity;
import com.lykj.aextreme.afinal.utils.StatusBarColorUtil;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.bean.EventMessage;
import com.maoyongxin.myapplication.tool.AndroidBug54971Workaround;
import com.maoyongxin.myapplication.tool.StatusBarUtil;
import com.maoyongxin.myapplication.ui.MainActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;

public abstract class BaseAct extends BaseActivity {
    public Looper looper = Looper.myLooper();
    public boolean myFinsh = false;
    private View decorView;

    protected abstract void handlerPassMsg(int target, int target1, Object obj);

    private View bottomview;
    public MyHandler myHandler = new MyHandler(looper);

    public class MyHandler extends Handler {
        public MyHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            handlerPassMsg(msg.what, msg.arg1, msg.obj);
        }
    }

    protected InputMethodManager inputMethodManager;
    public Bundle mSavedInstanceState;

    @Override
    protected void onDestroy() {

            super.onDestroy();
            EventBus.getDefault().unregister(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTranslucentStatus(false);
       // EventBus.getDefault().register(this);
        mSavedInstanceState = savedInstanceState;
        super.onCreate(savedInstanceState);
    }


    @Subscribe
    public void onEventMainThread(EventMessage eventMessage) {
        switch (eventMessage.getType()) {
            case 5:
                break;
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        ComantUtils.myFinsh = false;
        ImmersionBar.with(this)
                .transparentStatusBar()
                .statusBarDarkFont(true)
                .navigationBarEnable(false)
                .hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)
                .init();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public SharedPreferences getPreferences(int mode) {
        return super.getPreferences(mode);
    }

    /**
     * 初始化头,默认返回按钮
     *
     * @param title 标题
     * @param right 右边图标
     */
    protected void initHeaderBack(@StringRes int title, @DrawableRes int right) {
//        setHeaderLeft(R.mipmap.icon_hui);
        setHeaderRight(right);
        setTitle(title);
    }

    protected void initchatTitle(CharSequence title, @DrawableRes int right) {
//        setHeaderLeft(R.mipmap.icon_hui);
        setHeaderRight(right);
        setTitle(title);
    }

    protected void setLeftTitle() {
//        setHeaderLeft(R.mipmap.icon_hui);
        setTitle("");
    }

    protected void setTitleback(int title) {
//        setHeaderLeft(R.mipmap.icon_hui);
        setTitle(title);
//        setToolbarBack(R.color.gray1);
    }

    /**
     * 初始化头,默认返回按钮
     *
     * @param title 标题
     * @param right 右边文字
     */
    protected void initHeaderBackTxt(@StringRes int title, @StringRes int right) {
//        setHeaderLeft(R.mipmap.icon_hui);
        setHeaderRightTxt(right);
        setTitle(title);
//        setToolbarBack(R.color.gray1);
    }

    public void setTitle1(CharSequence title) {
        setTitle(title);
//        setHeaderLeft(R.mipmap.icon_hui);
    }

    public String getActivityTag() {
        return getClass().getSimpleName();
    }

    public BaseActivity getActivity() {
        return this;
    }

    /**
     * 180115 隐藏 魅族、Nexus、华为等底部的虚拟导航按键，避免遮挡内容
     *
     * @param activity 需要隐藏底部导航按键的Activity
     */
    public void hideBottomUIMenu(Activity activity) {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT < 19) { // lower api
            View v = activity.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else if (Build.VERSION.SDK_INT >= 19) {
            View decorView = activity.getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View
                    .SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            decorView.setSystemUiVisibility(uiOptions);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        MIUISetStatusBarLightMode(this.getWindow(), true);
        FlymeSetStatusBarLightMode(this.getWindow(), true);
    }
    public static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    public static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }
}
