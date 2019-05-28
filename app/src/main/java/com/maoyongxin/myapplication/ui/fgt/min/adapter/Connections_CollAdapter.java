package com.maoyongxin.myapplication.ui.fgt.min.adapter;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.ui.fgt.connection.act.bean.ConnectionBean;
import com.maoyongxin.myapplication.ui.fgt.min.act.bean.Connections_CollBean;

public class Connections_CollAdapter extends BaseQuickAdapter<Connections_CollBean.InfoBean, BaseViewHolder> {
    private Context context;

    public Connections_CollAdapter(Context context1) {
        super(R.layout.item_connection_poi_list);
        this.context = context1;
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, Connections_CollBean.InfoBean item) {
        viewHolder.setText(R.id.tv_poiName, item.getName())
                .setText(R.id.tv_poiType, item.getBusinessScope())
                .setText(R.id.tv_poiAddress, "");

    }
}