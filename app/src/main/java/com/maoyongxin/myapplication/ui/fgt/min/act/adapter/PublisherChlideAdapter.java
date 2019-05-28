package com.maoyongxin.myapplication.ui.fgt.min.act.adapter;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.bean.PublisherBean;
import com.maoyongxin.myapplication.ui.fgt.min.act.bean.PublisherChlideBean;

public class PublisherChlideAdapter extends BaseQuickAdapter<PublisherChlideBean.InfoBean.DataBean, BaseViewHolder> {
    private Context context;

    public PublisherChlideAdapter(Context context1) {
        super(R.layout.item_publisher);
        this.context = context1;
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, PublisherChlideBean.InfoBean.DataBean item) {
        Gson gson = new Gson();
        viewHolder.setText(R.id.name, item.getUid());
        viewHolder.setText(R.id.item_id, item.getNotice_type());
        RequestOptions options = new RequestOptions();
        options.placeholder(R.mipmap.icon_x1);

    }
}