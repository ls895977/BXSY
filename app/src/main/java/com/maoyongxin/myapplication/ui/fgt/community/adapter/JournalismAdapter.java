package com.maoyongxin.myapplication.ui.fgt.community.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.ui.fgt.community.bean.JournalismBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.Call;


public class JournalismAdapter extends BaseQuickAdapter<JournalismBean.InfoBean.DataBean, BaseViewHolder> {
    Context context;

    public JournalismAdapter(@Nullable List<JournalismBean.InfoBean.DataBean> data, Context context1) {
        super(R.layout.outlist, data);
        context = context1;
    }

    @Override
    protected void convert(BaseViewHolder helper, JournalismBean.InfoBean.DataBean item) {
        Glide.with(context).load("http://118.24.2.164:8083/news/" + item.getImg() + "").into((RoundedImageView)

        helper.getView(R.id.notice_image));
        helper.setText(R.id.notice_title1, item.getTitle()).setText(R.id.notice_sender1,item.getCreate_time() );

        helper.setText(R.id.notice_sender, item.getRead_num());
    }

    public String timeStamp2Date(long time, String format) {
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(time));
    }

}
