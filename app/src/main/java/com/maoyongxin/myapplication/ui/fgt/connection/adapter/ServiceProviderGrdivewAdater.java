package com.maoyongxin.myapplication.ui.fgt.connection.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lykj.aextreme.afinal.utils.MyToast;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.bean.EventMessage;
import com.maoyongxin.myapplication.ui.fgt.community.adapter.GridImageAdapter;
import com.maoyongxin.myapplication.ui.fgt.connection.act.base.SerViceProviderGridBean;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class ServiceProviderGrdivewAdater extends BaseAdapter {
    private List<SerViceProviderGridBean.InfoBean> mDrawableList;
    private LayoutInflater mInflater;
    private Context mContext;

    public ServiceProviderGrdivewAdater(Context context, List<SerViceProviderGridBean.InfoBean> drawableList) {
        mDrawableList = drawableList;
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return mDrawableList.size();
    }

    public Object getItem(int position) {
        return mDrawableList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ItemViewTag viewTag;

     //   EventBus.getDefault().register(this);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_serviceprovidergrid, null);
            viewTag = new ItemViewTag(convertView);
            convertView.setTag(viewTag);
        } else {
            viewTag = (ItemViewTag) convertView.getTag();
        }
        SerViceProviderGridBean.InfoBean item = mDrawableList.get(position);
        viewTag.mName.setText(item.getName());
        viewTag.mName.setSelected(item.isIsof());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EventBus.getDefault().post(new EventMessage<String>(3,position+""));
            }
        });





        return convertView;
    }

    class ItemViewTag {
        protected TextView mName;

        public ItemViewTag(View view) {
            mName = view.findViewById(R.id.item_zhihugridviewname);
        }
    }

}