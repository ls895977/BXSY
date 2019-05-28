package com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.fgt.adapter;

import android.content.Context;
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
import com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.fgt.bean.HotReviewsBean;
import com.maoyongxin.myapplication.ui.fgt.community.adapter.HotiReviewsChlideAdapter;
import com.maoyongxin.myapplication.ui.fgt.connection.act.bean.AnnouncementDetailsBean;
import com.maoyongxin.myapplication.ui.fgt.connection.adapter.AnnouncementDetailsChlideAdapter;

public class HotiReviewsAdapter extends BaseQuickAdapter<HotReviewsBean.InfoBean.DataBeanX, BaseViewHolder> {
    private Context context;
    private String groupName;
    String parentUserId;

    public HotiReviewsAdapter(Context context1, String groupName1, String parentUserId1) {
        super(R.layout.item_announcementdetails);
        this.context = context1;
        groupName = groupName1;
        parentUserId = parentUserId1;
    }

    private static boolean showis = true;

    public void setShowis(boolean showis) {
        this.showis = showis;
    }

    @Override
    protected void convert(BaseViewHolder helper, HotReviewsBean.InfoBean.DataBeanX item) {
        Glide.with(context).load(item.getHeadImg()).into((RoundedImageView) helper.getView(R.id.mine_header));
        helper.setText(R.id.nicke_Name, item.getUserName())
                .setText(R.id.tvContent, item.getContent())
                .setText(R.id.praise_num, item.getPraise_num()+"")
                .setText(R.id.create_time, item.getCreate_time());
        TextView zanImg = helper.getView(R.id.zanImg);
        if (item.getContent().equals("")) {
            helper.setText(R.id.tvContent, "");
        } else {
            try {
                helper.setText(R.id.tvContent, new String(Base64.decode(item.getContent().getBytes(), Base64.DEFAULT)));
            } catch (Exception s) {
            }
        }
        helper.getView(R.id.zanImg).setSelected(item.isPraise());
        helper.addOnClickListener(R.id.zanImg);
        if (item.isPraise()) {
            zanImg.setSelected(true);
        } else {
            zanImg.setSelected(false);
        }
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
        HotiReviewsChlideAdapter adapter = new HotiReviewsChlideAdapter(item.getRow().getData(), context);
        myReclder.setAdapter(adapter);
        if(showis){
            helper.getView(R.id.item_huifu).setVisibility(View.VISIBLE);
            helper.getView(R.id.item_delte).setVisibility(View.VISIBLE);
            helper.getView(R.id.lli_zan).setVisibility(View.VISIBLE);
        }else {
            helper.getView(R.id.item_huifu).setVisibility(View.GONE);
            helper.getView(R.id.lli_zan).setVisibility(View.GONE);
            helper.getView(R.id.item_delte).setVisibility(View.GONE);
        }
    }
}