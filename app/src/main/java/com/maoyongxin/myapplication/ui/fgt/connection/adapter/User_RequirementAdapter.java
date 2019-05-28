package com.maoyongxin.myapplication.ui.fgt.connection.adapter;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.ui.fgt.connection.act.bean.ConnectionBean;
import com.maoyongxin.myapplication.ui.fgt.connection.act.bean.UserRequitBean;

public class User_RequirementAdapter extends BaseQuickAdapter<UserRequitBean.InfoBean.DataBean, BaseViewHolder> {
    private Context context;

    public User_RequirementAdapter(Context context1) {
        super(R.layout.user_requirement);
        this.context = context1;
    }

    @Override
    protected void convert(BaseViewHolder viewHolder,UserRequitBean.InfoBean.DataBean item) {
        viewHolder.setText(R.id.re_title, item.getTitle());
        viewHolder.setText(R.id.requ_content, "查看次数："+item.getRead_num());

            viewHolder.setText(R.id.re_type,item.getClassifyName());
            if (item.getQq_account()!=null&&!item.getQq_account().equals(""))
            {
                viewHolder.setGone(R.id.requr_money,true);
                viewHolder.setText(R.id.requr_money, "￥："+item.getQq_account());
            }
            else
            {
                   viewHolder.setGone(R.id.requr_money,false);
            }

            viewHolder.setText(R.id.requuirt_time,item.getCreateTime());



        Glide.with(context).load(item.getHeadImg()).into((RoundedImageView) viewHolder.getView(R.id.re_user_headimg));

    }
}