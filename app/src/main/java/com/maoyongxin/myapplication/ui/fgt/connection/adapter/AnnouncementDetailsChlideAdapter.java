package com.maoyongxin.myapplication.ui.fgt.connection.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
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
import com.maoyongxin.myapplication.ui.fgt.connection.act.bean.AnnouncementDetailsBean;

import java.util.List;
import java.util.regex.Pattern;


public class AnnouncementDetailsChlideAdapter extends BaseQuickAdapter<AnnouncementDetailsBean.InfoBean.DataBeanX.RowBean.DataBean, BaseViewHolder> {
    Context context;

    public AnnouncementDetailsChlideAdapter(@Nullable List<AnnouncementDetailsBean.InfoBean.DataBeanX.RowBean.DataBean> data, Context context1) {
        super(R.layout.item_announcementdetailschlide, data);
        context = context1;
    }

    @Override
    protected void convert(BaseViewHolder helper, AnnouncementDetailsBean.InfoBean.DataBeanX.RowBean.DataBean item) {
        if (item.getUserName().equals("null")) {
            helper.setText(R.id.userName, " :");
        } else {
            helper.setText(R.id.userName, item.getUserName());
        }
        if (item.getParentUserName() == null || item.getParentUserName().equals("null")) {
            helper.setText(R.id.myUserName, " :");
        } else {
            helper.setText(R.id.myUserName, item.getParentUserName() + "：");
        }
//        if (isBase64(item.getContent())) {
//            helper.setText(R.id.tv_context, item.getContent());
//        } else {
        helper.setText(R.id.tv_context, new String(Base64.decode(item.getContent().getBytes(), Base64.DEFAULT)));

//        }
    }
}
