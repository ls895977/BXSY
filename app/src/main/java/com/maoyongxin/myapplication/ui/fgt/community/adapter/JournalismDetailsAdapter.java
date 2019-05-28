package com.maoyongxin.myapplication.ui.fgt.community.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lykj.aextreme.afinal.utils.Debug;
import com.makeramen.roundedimageview.RoundedImageView;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.common.ComantUtils;
import com.maoyongxin.myapplication.common.MyApplication;
import com.maoyongxin.myapplication.ui.fgt.community.bean.JournalismDetailsBean;
import com.maoyongxin.myapplication.ui.fgt.connection.act.bean.AnnouncementDetailsBean;
import com.maoyongxin.myapplication.ui.fgt.connection.adapter.AnnouncementDetailsChlideAdapter;

import java.util.List;


public class JournalismDetailsAdapter extends BaseQuickAdapter<JournalismDetailsBean.InfoBean.DataBeanX, BaseViewHolder> {
    Context context;

    public JournalismDetailsAdapter(@Nullable List<JournalismDetailsBean.InfoBean.DataBeanX> data, Context context1) {
        super(R.layout.item_journalismdetails, data);
        context = context1;
    }

    @Override
    protected void convert(BaseViewHolder helper, JournalismDetailsBean.InfoBean.DataBeanX item) {
        Debug.e("------------"+item.getHeadImg());
        Glide.with(context).load(item.getHeadImg()).into((RoundedImageView) helper.getView(R.id.mine_header));
        helper.setText(R.id.nicke_Name, item.getUserName())
                .setText(R.id.tvContent, new String(Base64.decode(item.getContent().getBytes(), Base64.DEFAULT)))
                .setText(R.id.praise_num, item.getPraise_num())
                .setText(R.id.create_time, item.getCreate_time());
       helper.getView(R.id.zanImg).setSelected(item.isIsPraise());
       helper.addOnClickListener(R.id.zanImg);
        helper.addOnClickListener(R.id.item_huifu);
        if (item.getUid().equals(MyApplication.getCurrentUserInfo().getUserId())) {
            helper.getView(R.id.item_delte).setVisibility(View.VISIBLE);
            helper.addOnClickListener(R.id.item_delte);
        } else {
            helper.getView(R.id.item_delte).setVisibility(View.GONE);
        }
        if (item.getRow().getData().size() == 0) {
            helper.getView(R.id.itemMessage).setVisibility(View.GONE);
            return;
        }
        RecyclerView myReclder = helper.getView(R.id.my_itemRecyclerview);
        helper.getView(R.id.itemMessage).setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        myReclder.setLayoutManager(layoutManager);
        JournalismDetailsChlideAdapter adapter = new JournalismDetailsChlideAdapter(item.getRow().getData(), context);
        myReclder.setAdapter(adapter);
    }
}
