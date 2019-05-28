package com.maoyongxin.myapplication.ui.fgt.connection.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.ui.fgt.connection.act.bean.SerViceProviderBean;

public class FgtSerViceProviderAdapter extends BaseQuickAdapter<SerViceProviderBean.InfoBean.DataBean, BaseViewHolder> {
    private Context context;

    public FgtSerViceProviderAdapter(Context context1) {
        super(R.layout.itemview_servicetitle);
        this.context = context1;
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, SerViceProviderBean.InfoBean.DataBean item) {
        viewHolder.setText(R.id.name, item.getResource_name());
        viewHolder.setText(R.id.item_id, item.getIntro());
//        .placeholder(R.mipmap.icon_x1)
        RequestOptions options = new RequestOptions();
        options.placeholder(R.mipmap.icon_x1);
        Glide.with(context).load("http://st.3dgogo.com/" + item.getImg()).apply(options).into((RoundedImageView) viewHolder.getView(R.id.zhihutitl_hader));
        //服务商列表适配器
    }
}