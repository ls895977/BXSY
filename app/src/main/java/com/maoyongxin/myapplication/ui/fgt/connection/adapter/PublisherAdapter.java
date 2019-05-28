package com.maoyongxin.myapplication.ui.fgt.connection.adapter;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.lykj.aextreme.afinal.utils.Debug;
import com.makeramen.roundedimageview.RoundedImageView;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.bean.Company_collChlideBean;
import com.maoyongxin.myapplication.bean.PublisherBean;

public class PublisherAdapter extends BaseQuickAdapter<PublisherBean.InfoBean.DataBean, BaseViewHolder> {
    private Context context;

    public PublisherAdapter(Context context1) {
        super(R.layout.item_publisher);
        this.context = context1;
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, PublisherBean.InfoBean.DataBean item) {
        Gson gson = new Gson();
        viewHolder.setText(R.id.name, item.getRow().getTitle());
        viewHolder.setText(R.id.item_id, item.getRow().getContent());
        RequestOptions options = new RequestOptions();
        options.placeholder(R.mipmap.icon_x1);
        Glide.with(context).load(item.getRow().getImg()).apply(options).into((RoundedImageView) viewHolder.getView(R.id.zhihutitl_hader));
    }
}