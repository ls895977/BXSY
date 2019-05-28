package com.maoyongxin.myapplication.ui.fgt.message.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.http.entity.mygroupList;
import com.maoyongxin.myapplication.view.SelectableRoundedImageView;

import java.util.List;

/**
 * Created by maoyongxin on 2018/1/10.
 */

public class GroupAdapter extends BaseAdapter {
    private Context context;
    private List<mygroupList> list;
    private LayoutInflater inflater;
    public GroupAdapter(List<mygroupList> list, Context context) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder Holder;
        if (convertView == null) {
            Holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.group_item_new, null);
            Holder.tvTitle = (TextView) convertView.findViewById(R.id.groupname);
            Holder.mImageView = (SelectableRoundedImageView) convertView.findViewById(R.id.groupuri);
            Holder.groupId=(TextView)convertView.findViewById(R.id.group_id);

            convertView.setTag(Holder);
        } else {
            Holder = (ViewHolder) convertView.getTag();
        }

        Holder.tvTitle.setText(list.get(position).getGroupName());
        Holder.groupId.setText("商会号："+list.get(position).getGroupId());
        Glide.with(context).load(list.get(position).getImage()).into(Holder.mImageView);


        return convertView;
    }

    class ViewHolder {
        /**
         * 昵称
         */
        TextView tvTitle;
        /**
         * 头像
         */
        SelectableRoundedImageView mImageView;
        /**
         * userId
         */
        TextView groupId;
    }



}
