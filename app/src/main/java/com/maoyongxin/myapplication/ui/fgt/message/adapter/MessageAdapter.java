package com.maoyongxin.myapplication.ui.fgt.message.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lykj.aextreme.afinal.utils.ConstUtils;
import com.lykj.aextreme.afinal.utils.Debug;
import com.lykj.aextreme.afinal.utils.TimeUtils;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.view.SelectableRoundedImageView;

import java.util.List;

import io.rong.imlib.model.SearchConversationResult;

public class MessageAdapter extends BaseQuickAdapter<SearchConversationResult, BaseViewHolder> {
    private Context context;

    public MessageAdapter(@Nullable List<SearchConversationResult> data, Context context1) {
        super(R.layout.item_message, data);
        context = context1;
    }
    @Override
    protected void convert(BaseViewHolder helper, SearchConversationResult item) {
        Glide.with(context).load(item.getConversation().getPortraitUrl()).into((SelectableRoundedImageView) helper.getView(R.id.img_requestUserHead));
        helper.setText(R.id.tv_requestUserName, item.getConversation().getConversationTitle());
        helper.setText(R.id.tv_time, TimeUtils.getDateMoth(item.getConversation().getLatestMessage().getDestructTime(), "月"));
//        Debug.e("------------"+ item.getConversation().getLatestMessage().getMentionedInfo().getMentionedContent());
    }
}
