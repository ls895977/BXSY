package com.maoyongxin.myapplication.ui.fgt.community.fgt;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.common.BaseFgt;
import com.maoyongxin.myapplication.common.ComantUtils;
import com.maoyongxin.myapplication.ui.fgt.community.view.MatrixImageView;

public class Fgt_Item extends BaseFgt {
    @Override
    public int initLayoutId() {
        return R.layout.fgt_item;
    }

    @Override
    public void initView() {
        hideHeader();
    }

    PhotoView iv;

    @Override
    public void initData() {
        iv = getView(R.id.iv_item);
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            String url = bundle.getString("key");
            if (url.startsWith("http"))
            {
                Glide.with(context).load(url).into(iv);
            }
            else if (url.contains("uploads")) {
                Glide.with(context).load(ComantUtils.MyUrlHot1 + url).into(iv);
            } else {
                Glide.with(context).load(ComantUtils.BigImgUrl + url).into(iv);
            }
        }
    }

    @Override
    public void requestData() {

    }

    @Override
    public void updateUI() {

    }

    @Override
    public void onViewClick(View v) {

    }

    @Override
    public void sendMsg(int flag, Object obj) {

    }
}
