package com.maoyongxin.myapplication.ui.fgt.community.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.common.ComantUtils;
import com.maoyongxin.myapplication.ui.fgt.community.bean.DynamicBase;

import java.util.ArrayList;
import java.util.List;

public class DynamicImageAdapter extends BaseAdapter {
    private List<String> mNameList;
    private LayoutInflater mInflater;
    private Context mContext;
    LinearLayout.LayoutParams params;
    public DynamicImageAdapter(Context context, List<String> nameList) {
        mNameList = nameList;
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }
    public int getCount() {
        return mNameList.size();
    }

    public Object getItem(int position) {
        return mNameList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ItemViewTag viewTag;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_dynamiclmageadapter, null);
            viewTag = new ItemViewTag((RoundedImageView) convertView.findViewById(R.id.grid_icon));
            convertView.setTag(viewTag);
        } else {
            viewTag = (ItemViewTag) convertView.getTag();
        }
        if ( mNameList.get(position).contains("uploads")) {
            Glide.with(convertView)
                    .load(ComantUtils.MyUrlHot1 + mNameList.get(position))

                    .into(viewTag.mIcon);
        } else {
            Glide.with(convertView)

                    .load(ComantUtils.BigImgUrl + mNameList.get(position))

                    .into(viewTag.mIcon);
        }
        return convertView;
    }

    class ItemViewTag {
        protected RoundedImageView mIcon;

        public ItemViewTag(RoundedImageView icon) {
            this.mIcon = icon;
        }
    }

}