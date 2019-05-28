package com.maoyongxin.myapplication.ui.fgt.community.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Base64;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.fgt.bean.HotReviewsBean;
import com.maoyongxin.myapplication.ui.fgt.connection.act.bean.AnnouncementDetailsBean;

import java.util.List;


public class HotiReviewsChlideAdapter extends BaseQuickAdapter<HotReviewsBean.InfoBean.DataBeanX.RowBean.DataBean, BaseViewHolder> {
    Context context;

    public HotiReviewsChlideAdapter(@Nullable List<HotReviewsBean.InfoBean.DataBeanX.RowBean.DataBean> data, Context context1) {
        super(R.layout.item_hotreviewschldide, data);
        context = context1;
    }

    @Override
    protected void convert(BaseViewHolder helper, HotReviewsBean.InfoBean.DataBeanX.RowBean.DataBean item) {
        if (item.getUserName()==null||item.getUserName().equals("null")) {
            helper.setText(R.id.userName, " :");
        } else {
            helper.setText(R.id.userName, item.getUserName()+":");
        }

        helper.setText(R.id.tv_context, new String(Base64.decode(item.getContent().getBytes(), Base64.DEFAULT)));

//        }
    }
}
