package com.maoyongxin.myapplication.ui.fgt.community.adapter;

import android.content.Context;

import com.amap.api.services.help.Tip;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.maoyongxin.myapplication.R;

public class LocationAdapter extends BaseQuickAdapter<Tip, BaseViewHolder> {
    private Context context;

    public LocationAdapter(Context context1) {
        super(R.layout.item_location);
        this.context = context1;

    }

    @Override
    protected void convert(BaseViewHolder helper, Tip item) {
        helper.setText(R.id.location_name, item.getName())
                .setText(R.id.location_addr, item.getAddress());

    }
}

