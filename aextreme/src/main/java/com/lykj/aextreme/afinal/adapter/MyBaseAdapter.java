package com.lykj.aextreme.afinal.adapter;

import android.content.Context;
import android.view.View;

import java.util.List;

public abstract class MyBaseAdapter<T> extends BaseAdapter<T> {
	private List<T> datas;

	public MyBaseAdapter(Context context, List<T> datas) {
		super(context);
		this.datas = datas;
	}

	public void setList(List<T> datas) {
		this.datas = datas;
	}

	public void refresh(List<T> datas) {
		this.datas = datas;
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return datas == null ? 0 : datas.size();
	}

	@Override
	public T getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void setOnClickListener(View v,int position) {
		if (getListener() != null) {
			v.setOnClickListener(new ViewOnClick(getListener(), getItem(position), position));
		}
	}
}
