package com.maoyongxin.myapplication.ui.fgt.message.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Base64;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lykj.aextreme.afinal.utils.Debug;
import com.makeramen.roundedimageview.RoundedImageView;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.http.entity.StrangerInfo;
import com.maoyongxin.myapplication.ui.fgt.connection.act.bean.AnnouncementDetailsBean;

import java.util.List;


public class FindAndSearchAdapter extends BaseQuickAdapter<StrangerInfo.UserList, BaseViewHolder> {
    Context context;

    public FindAndSearchAdapter(@Nullable List<StrangerInfo.UserList> data, Context context1) {
        super(R.layout.item_findandsearch, data);
        context = context1;
    }

    @Override
    protected void convert(BaseViewHolder helper, StrangerInfo.UserList item) {
        helper.addOnClickListener(R.id.find_add);
        RequestOptions options = new RequestOptions();
        options.error(R.drawable.tupian);
        Glide.with(context).load(item.getUser().getHeadImg()).apply(options).into((RoundedImageView) helper.getView(R.id.mine_header));
        helper.setText(R.id.findandsearch_name, item.getUser().getUserName());
        if (item.getHobbyList() != null) {
            helper.setText(R.id.findandsearch_Context, item.getHobbyList().get(0).getInterestName());
        } else {
            helper.setText(R.id.findandsearch_Context, "");
        }
        helper.getView(R.id.find_add).setSelected(item.isStatus());
    }
}
