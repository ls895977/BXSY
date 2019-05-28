package com.maoyongxin.myapplication.ui.fgt.community.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Base64;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.ui.fgt.community.bean.JournalismDetailsBean;
import com.maoyongxin.myapplication.ui.fgt.connection.act.bean.AnnouncementDetailsBean;

import java.util.List;


public class JournalismDetailsChlideAdapter extends BaseQuickAdapter<JournalismDetailsBean.InfoBean.DataBeanX.RowBean.DataBean, BaseViewHolder> {
    Context context;

    public JournalismDetailsChlideAdapter(@Nullable List<JournalismDetailsBean.InfoBean.DataBeanX.RowBean.DataBean> data, Context context1) {
        super(R.layout.item_journalismdetailschlide, data);
        context = context1;
    }
    @Override
    protected void convert(BaseViewHolder helper, JournalismDetailsBean.InfoBean.DataBeanX.RowBean.DataBean item) {
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
        helper.setText(R.id.tv_context, new String(Base64.decode(item.getContent().getBytes(), Base64.DEFAULT)));
    }
}
