package com.maoyongxin.myapplication.ui.fgt.connection.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.bean.ConnectionChlideBean;
import com.maoyongxin.myapplication.ui.fgt.connection.act.bean.ConnectionBean;

import java.util.List;


public class ConnectionChlideAdapter extends BaseQuickAdapter<ConnectionChlideBean.InfoBean, BaseViewHolder> {
    Context context;

    public ConnectionChlideAdapter(@Nullable List<ConnectionChlideBean.InfoBean> data, Context context1) {
        super(R.layout.item_connection, data);
        context = context1;
    }

    @Override
    protected void convert(BaseViewHolder helper, ConnectionChlideBean.InfoBean item) {
        helper.setText(R.id.classifyName, item.getClassifyName())
                .setText(R.id.title, item.getTitle())
                .setText(R.id.content, item.getContent())
                .setText(R.id.contentTime,"截至日期 "+item.getIndate())
                .setText(R.id.checktimes,"查阅 "+item.getRead_num()+" 次");

    }
}
