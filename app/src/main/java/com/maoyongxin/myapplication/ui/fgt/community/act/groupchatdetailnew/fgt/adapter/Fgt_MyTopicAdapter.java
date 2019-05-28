package com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.fgt.adapter;

import android.content.Context;
import android.util.Base64;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lykj.aextreme.afinal.utils.Debug;
import com.makeramen.roundedimageview.RoundedImageView;
import com.maoyongxin.myapplication.R;
import com.chad.library.adapter.base.BaseViewHolder;
import com.maoyongxin.myapplication.common.ComantUtils;
import com.maoyongxin.myapplication.common.MyApplication;
import com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.bean.MyTopicBean;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


/**
 * 社区=热门社区=我的
 */
public class Fgt_MyTopicAdapter extends BaseQuickAdapter<MyTopicBean.InfoBean.DataBean, BaseViewHolder> {
    private Context context;
    private String groupName;

    public Fgt_MyTopicAdapter(Context context1, String groupName1) {
        super(R.layout.item_group_huati);
        this.context = context1;
        groupName = groupName1;
    }

    @Override
    protected void convert(BaseViewHolder helper, MyTopicBean.InfoBean.DataBean item) {
        if (item.getHeadImg() != null && !item.getHeadImg().equals("null")) {
            if (item.getHeadImg() != null && item.getHeadImg().contains("uploads/minhader")) {
                Glide.with(context).load(ComantUtils.MyUrlHot1 + item.getHeadImg())
                        .into((ImageView) helper.getView(R.id.img_huatiuser_head));
            } else if (item.getHeadImg().contains("http://thirdwx.qlogo.cn")) {
                Glide.with(context).load(item.getHeadImg()).into((ImageView) helper.getView(R.id.img_huatiuser_head));
            } else {
                Glide.with(context).load(ComantUtils.MyUrlImageHader + item.getHeadImg())
                        .into((ImageView) helper.getView(R.id.img_huatiuser_head));
            }
            if (item.getHeadImg() != null && item.getHeadImg().contains("http://qzapp.qlogo.cn")) {
                Glide.with(context).load(item.getHeadImg()).into((ImageView) helper.getView(R.id.img_huatiuser_head));
            }
        }
//        Glide.with(context).load(ComantUtils.MyUrlImageHader + item.getHeadImg()).into((RoundedImageView) helper.getView(R.id.img_huatiuser_head));
        helper.setText(R.id.tv_huati_user, item.getUserName())
                .setText(R.id.tv_huati_time, item.getCreate_time())
//                .setText(R.id.tv_huati_content, new String(Base64.decode(item.getContent().getBytes(), Base64.DEFAULT)))
                .setText(R.id.numZan, item.getPraise_num())
                .setText(R.id.tv_groupName, "#" + groupName);

        final String contentImg = item.getImg();
        if (!contentImg.equals("")) {
            helper.getView(R.id.content_img).setVisibility(VISIBLE);
            Glide.with(context).load(item.getImg()).into((ImageView) helper.getView(R.id.content_img));
        } else {
            helper.getView(R.id.content_img).setVisibility(GONE);
        }
        if (item.getUid().equals(MyApplication.getCurrentUserInfo().getUserId())) {
            helper.getView(R.id.img_delete).setVisibility(VISIBLE);
        } else {
            helper.getView(R.id.img_delete).setVisibility(GONE);
        }
        helper.addOnClickListener(R.id.img_delete);
        helper.addOnClickListener(R.id.img_share);
    }
}