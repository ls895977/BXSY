package com.jky.baselibrary.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

/**
 * SingleTypeBaseAdapter for ListView, GridView;
 *
 * @see android.widget.ListView
 * @see android.widget.GridView
 */
public abstract class SingleTypeBaseAdapter<T> extends BaseAdapter {

    protected Context mContext;
    protected ArrayList<T> mData;

    public SingleTypeBaseAdapter(Context context, ArrayList<T> data) {
        mContext = context;
        mData = data;
    }

    public Context getContext() {
        return mContext;
    }

    public ArrayList<T> getData() {
        return mData;
    }

    protected abstract int getItemLayout();

    protected abstract Class<? extends BaseViewHolder> getViewHolderClass();

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(getItemLayout(), parent, false);
            try {
                Constructor constructor = getViewHolderClass().getConstructor(Context.class, mData.getClass(), BaseAdapter.class);
                constructor.setAccessible(true);
                holder = (BaseViewHolder) constructor.newInstance(mContext, mData, this);
                holder.initView(convertView);
                convertView.setTag(holder);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        holder = (BaseViewHolder) convertView.getTag();
        holder.initState();
        holder.fillData(position);
        return convertView;
    }
}
