package com.maoyongxin.myapplication.ui.fgt.min.act;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;

import com.lykj.aextreme.afinal.utils.ACache;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.common.BaseAct;
import com.maoyongxin.myapplication.tool.CompressPhotoUtils;
import com.maoyongxin.myapplication.tool.SpUtils;
import com.maoyongxin.myapplication.ui.Act_Login;

/**
 * 我的=账号设置
 */
public class Act_AccountSettings extends BaseAct {
    private ACache aCache;

    @Override
    protected void handlerPassMsg(int target, int target1, Object obj) {

    }

    @Override
    public int initLayoutId() {
        return R.layout.act_accountsettings;
    }

    @Override
    public void initView() {
        hideHeader();
        aCache = ACache.get(this);
        setOnClickListener(R.id.account_back);
        setOnClickListener(R.id.btn_doLogout);
    }

    @Override
    public void initData() {

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
            case R.id.account_back:
                finish();
                break;
            case R.id.btn_doLogout:
                aCache.clear();
                SpUtils.putBoolean(Act_AccountSettings.this, "isFirst", true);
                CompressPhotoUtils.deleteFolderFile("mnt/sdcard/situ/", true);//清空图片操作缓存
                SharedPreferences preferences = Act_AccountSettings.this.getSharedPreferences("first_open", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("is_first_open", true);
                editor.commit();
                startActClear(Act_Login.class);
                finish();
                break;
        }
    }

    public void makeSpNoAuto(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("userInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isAuto", false);
        editor.putString("psw", null);
        editor.putString("num", null);
        editor.commit();
    }
}
