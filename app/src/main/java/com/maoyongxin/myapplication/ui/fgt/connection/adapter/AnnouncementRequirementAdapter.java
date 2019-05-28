package com.maoyongxin.myapplication.ui.fgt.connection.adapter;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.ui.fgt.connection.act.bean.ConnectionBean;

public class AnnouncementRequirementAdapter extends BaseQuickAdapter<ConnectionBean.InfoBean.DataBean, BaseViewHolder> {
    private Context context;

    public AnnouncementRequirementAdapter(Context context1) {
        super(R.layout.ite_announcementrequirement);
        this.context = context1;
    }

    @Override
    protected void convert(BaseViewHolder viewHolder,ConnectionBean.InfoBean.DataBean item) {
        viewHolder.setText(R.id.name, item.getTitle());
        viewHolder.setText(R.id.item_id, item.getNotice_title());


            viewHolder.setText(R.id.publishadress, (CharSequence) item.getArea_code());

            viewHolder.setText(R.id.publishtime,item.getNotice_date());

    }
}