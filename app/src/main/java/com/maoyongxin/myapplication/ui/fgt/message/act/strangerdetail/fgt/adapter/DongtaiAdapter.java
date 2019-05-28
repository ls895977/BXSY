package com.maoyongxin.myapplication.ui.fgt.message.act.strangerdetail.fgt.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.common.ComantUtils;
import com.maoyongxin.myapplication.common.MyApplication;
import com.maoyongxin.myapplication.http.callback.EntityCallBack;
import com.maoyongxin.myapplication.http.request.ReqCommunity;
import com.maoyongxin.myapplication.http.response.MyCommunityResponse;
import com.maoyongxin.myapplication.ui.fgt.community.act.Act_News_Web;
import com.maoyongxin.myapplication.ui.fgt.community.act.Act_ShowImageLIst;
import com.maoyongxin.myapplication.ui.fgt.community.adapter.DynamicImageAdapter;
import com.maoyongxin.myapplication.ui.fgt.community.bean.DynamicBase;
import com.maoyongxin.myapplication.view.AntGrideVIew;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

import static android.view.View.GONE;

public class DongtaiAdapter extends BaseMultiItemQuickAdapter<DynamicBase, BaseViewHolder> {
    private Context context;
    private String webapi = "http://st.3dgogo.com/index/enterprise_publicity/get_community_id_details_url_api.html?community_id=";

    public DongtaiAdapter(List<DynamicBase> data, Context context1) {
        super(data);
        this.context = context1;
        addItemType(DynamicBase.THREE_SMALL, R.layout.item_dong1);//3张5张图片显示布局
        addItemType(DynamicBase.RIGHT_BIG, R.layout.item_dong2);//2张4张图片显示布局
        addItemType(DynamicBase.LEFT_BIG, R.layout.item_dong3);//一张图片显示或一个视频
    }

    @Override
    protected void convert(final BaseViewHolder helper, final DynamicBase item) {
        switch (helper.getItemViewType()) {
            case DynamicBase.THREE_SMALL://3张5张图片显示布局
                DynamicImageAdapter gridViewAdpater = new DynamicImageAdapter(context, item.getImg());//多图片显示
                AntGrideVIew grideVIew = helper.getView(R.id.gv_DynamicPics);
                grideVIew.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent1 = new Intent();
                        intent1.setClass(context, Act_ShowImageLIst.class);
                        intent1.putExtra("idnext", position);
                        intent1.putStringArrayListExtra("imagList", (ArrayList<String>) item.getImg());
                        context.startActivity(intent1);
                    }
                });
                grideVIew.setAdapter(gridViewAdpater);
                break;
            case DynamicBase.RIGHT_BIG://2张4张图片显示布局
                AntGrideVIew grideVIew1 = helper.getView(R.id.gv_DynamicPics);
                grideVIew1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent1 = new Intent();
                        intent1.setClass(context, Act_ShowImageLIst.class);
                        intent1.putExtra("idnext", position);
                        intent1.putStringArrayListExtra("imagList", (ArrayList<String>) item.getImg());
                        context.startActivity(intent1);
                    }
                });
                DynamicImageAdapter gridViewAdpater1 = new DynamicImageAdapter(context, item.getImg());//多图片显示
                grideVIew1.setAdapter(gridViewAdpater1);
                break;
            case DynamicBase.LEFT_BIG://一张图片显示
                if (item.getVideo().equals("")) {
                    helper.getView(R.id.dynamic_video).setVisibility(View.GONE);
                } else {
                    helper.getView(R.id.dynamic_video).setVisibility(View.VISIBLE);
                }
                if (item.getImg() != null) {
                    if (item.getImg().get(0).contains("uploads")) {
                        Glide.with(context).load(ComantUtils.MyUrlHot1 + item.getImg().get(0)).into((ImageView) helper.getView(R.id.item_videoImage));
                    } else {
                        Glide.with(context).load(ComantUtils.BigImgUrl + item.getImg().get(0)).into((ImageView) helper.getView(R.id.item_videoImage));
                    }
                }
                helper.addOnClickListener(R.id.all_view);
                break;
        }
        Boolean joined = false;
        RequestOptions options = new RequestOptions();
        options.error(R.mipmap.huangye);
        options.placeholder(R.mipmap.huangye);
        Glide.with(context).load(item.getHeadImg())
                .apply(options)
                .into((ImageView) helper.getView(R.id.img_square_header));
        helper.addOnClickListener(R.id.follow_button);//关注点击事件
        helper.setText(R.id.tv_square_person_name, item.getUserName())//昵称
//                        .setText(R.id.tv_creatTime, TimeUtil.cmpLastMillisDesc(Long.parseLong(item.getCreateTime())))//时间
                .setText(R.id.tv_creatTime, item.getCreateTime())//时间
                .setText(R.id.tv_square_msgTitle, new String(Base64.decode(item.getDynamicContent().getBytes(), Base64.DEFAULT)))//内容
                .setText(R.id.numPL, item.getCommentNum())//评论
                .setText(R.id.numZan, item.getPraiseNum());//点赞

        if (item.getUserId().equals(MyApplication.getCurrentUserInfo().getUserId())) {
            helper.getView(R.id.img_delete).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.img_delete).setVisibility(GONE);
        }
        if (item.isAttention()) {//关注设置
            helper.getView(R.id.follow_button).setSelected(false);
        } else {
            helper.getView(R.id.follow_button).setSelected(true);
        }
        if (item.isPraise()) {
            helper.getView(R.id.zanImg).setSelected(true);
        } else {
            helper.getView(R.id.zanImg).setSelected(false);
        }
        getUserCommunity(item.getUserId(), helper);
        //事件
        helper.addOnClickListener(R.id.img_square_header);//头像
        helper.addOnClickListener(R.id.follow_button);//关注
        helper.addOnClickListener(R.id.img_delete);//删除
        helper.addOnClickListener(R.id.ll_zan);//点赞
        helper.addOnClickListener(R.id.id2);//评论
        helper.addOnClickListener(R.id.id1);//转发
        helper.getView(R.id.tv_userAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(context, Act_News_Web.class);
                intent.putExtra("url", item.getCommunityUrl() + "");
                intent.putExtra("company_name", ((TextView) helper.getView(R.id.tv_userAdd)).getText().toString());
                intent.putExtra("communityId", item.getDynamicId());
                intent.putExtra("targetUserId", item.getUserId());
                context.startActivity(intent);
                backdatatoserver(item.getDynamicId());
            }
        });//跳转公司

    }

    private void getUserCommunity(String userId, final BaseViewHolder holder) {
        ReqCommunity.getMyCommunity(context, "adapter", userId, new EntityCallBack<MyCommunityResponse>() {
            @Override
            public Class<MyCommunityResponse> getEntityClass() {
                return MyCommunityResponse.class;
            }

            @Override
            public void onSuccess(MyCommunityResponse resp) {
                if (resp.is200()) {
                    holder.setText(R.id.tv_userAdd, resp.obj.getCommunityName());
                    holder.getView(R.id.ll_community).setVisibility(View.VISIBLE);

                } else {
                    holder.getView(R.id.ll_community).setVisibility(View.GONE);
                    holder.setText(R.id.tv_userAdd, "暂未开通服务号");

                }
            }

            @Override
            public void onFailure(Throwable e) {
                holder.setText(R.id.tv_userAdd, "暂未开通服务号");
                holder.getView(R.id.ll_community).setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(Throwable e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void backdatatoserver(final String comm) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpUtils.post().url("http://st.3dgogo.com/index/enterprise_publicity_statistics/setPublicityNumApi.html")
                        .addParams("communityId", comm)
                        .addParams("nick_name", MyApplication.getCurrentUserInfo().getUserName())
                        .addParams("type", "1")
                        .addParams("uid", MyApplication.getCurrentUserInfo().getUserId())

                        .build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(String response, int id) {

                    }
                });

            }
        }).start();
    }
}
