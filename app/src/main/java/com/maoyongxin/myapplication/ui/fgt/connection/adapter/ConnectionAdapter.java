package com.maoyongxin.myapplication.ui.fgt.connection.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lykj.aextreme.afinal.utils.Debug;
import com.makeramen.roundedimageview.RoundedImageView;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.ui.fgt.community.bean.JournalismBean;
import com.maoyongxin.myapplication.ui.fgt.connection.act.bean.ConnectionBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class ConnectionAdapter extends BaseQuickAdapter<ConnectionBean.InfoBean.DataBean, BaseViewHolder> {
    Context context;
    public ConnectionAdapter(@Nullable List<ConnectionBean.InfoBean.DataBean> data, Context context1) {
        super(R.layout.item_connection, data);
        context = context1;
    }

    @Override
    protected void convert(BaseViewHolder helper, ConnectionBean.InfoBean.DataBean item) {
        helper.setText(R.id.classifyName, item.getClassifyName())
                .setText(R.id.title, item.getTitle())
                .setText(R.id.content, item.getNotice_title());
    }
}
