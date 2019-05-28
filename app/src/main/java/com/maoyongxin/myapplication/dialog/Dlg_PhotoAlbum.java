package com.maoyongxin.myapplication.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.lykj.aextreme.afinal.common.BaseDialog;
import com.maoyongxin.myapplication.R;

/**
 * Created by lishan on 2017/12/22.
 */

public class Dlg_PhotoAlbum extends BaseDialog {
    OnClick onClick;

    public Dlg_PhotoAlbum(Context context, OnClick click) {
        super(context);
        this.onClick = click;
    }

    @Override
    protected int initLayoutId() {
        return R.layout.dlg_photoalbum;
    }

    @Override
    protected void initWindow() {
        windowDeploy(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
    }

    @Override
    protected void initView() {
        setOnClickListener(R.id.phonto_image);
        setOnClickListener(R.id.phonto_video);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.phonto_image://图片
                onClick.image();
                dismiss();
                break;
            case R.id.phonto_video://视频
                onClick.video();
                dismiss();
                break;
        }
    }






    public interface OnClick {
        void image();

        void video();
    }


}
