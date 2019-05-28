package com.maoyongxin.myapplication.ui.fgt.community.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lykj.aextreme.afinal.utils.Debug;
import com.makeramen.roundedimageview.RoundedImageView;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.common.ComantUtils;
import com.maoyongxin.myapplication.common.MyApplication;
import com.maoyongxin.myapplication.http.callback.EntityCallBack;
import com.maoyongxin.myapplication.http.request.ReqCommunity;
import com.maoyongxin.myapplication.http.response.MyCommunityResponse;
import com.maoyongxin.myapplication.tool.UtilsTool;
import com.maoyongxin.myapplication.ui.fgt.community.act.Act_News_Web;
import com.maoyongxin.myapplication.ui.fgt.community.act.Act_ShowImageLIst;
import com.maoyongxin.myapplication.ui.fgt.community.bean.DynamicBase;
import com.maoyongxin.myapplication.view.AntGrideVIew;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import cn.jiguang.share.android.api.JShareInterface;
import cn.jiguang.share.android.api.PlatActionListener;
import cn.jiguang.share.android.api.Platform;
import cn.jiguang.share.android.api.ShareParams;
import cn.jiguang.share.wechat.Wechat;
import cn.jiguang.share.wechat.WechatMoments;
import okhttp3.Call;

import static android.view.View.GONE;

public class DynamicAdapter extends BaseMultiItemQuickAdapter<DynamicBase, BaseViewHolder> {
    private Context context;
    private Bitmap Sharebit;


    public DynamicAdapter(List<DynamicBase> data, Context context1) {
        super(data);
        this.context = context1;
        addItemType(DynamicBase.THREE_SMALL, R.layout.item_dynamic_gridview);//3张5张图片显示布局
        addItemType(DynamicBase.RIGHT_BIG, R.layout.item_dynamic_gridviewnum);//2张4张图片显示布局
        addItemType(DynamicBase.LEFT_BIG, R.layout.item_dynamic_img);//一张图片显示或一个视频
    }

    @Override
    protected void convert(final BaseViewHolder helper, final DynamicBase Dynamicitem) {
        String communtid;

        switch (helper.getItemViewType()) {
            case DynamicBase.THREE_SMALL://3张5张图片显示布局
                DynamicImageAdapter gridViewAdpater = new DynamicImageAdapter(context, Dynamicitem.getImg());//多图片显示
                AntGrideVIew grideVIew = helper.getView(R.id.gv_DynamicPics);
                grideVIew.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent1 = new Intent();
                        intent1.setClass(context, Act_ShowImageLIst.class);
                        intent1.putExtra("idnext", position);
                        intent1.putStringArrayListExtra("imagList", (ArrayList<String>) Dynamicitem.getImg());
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
                        intent1.putStringArrayListExtra("imagList", (ArrayList<String>) Dynamicitem.getImg());
                        context.startActivity(intent1);
                    }
                });
                DynamicImageAdapter gridViewAdpater1 = new DynamicImageAdapter(context, Dynamicitem.getImg());//多图片显示
                grideVIew1.setAdapter(gridViewAdpater1);
                break;
            case DynamicBase.LEFT_BIG://一张图片显示
                if (Dynamicitem.getVideo().equals("")) {
                    helper.getView(R.id.dynamic_video).setVisibility(View.GONE);
                } else {
                    helper.getView(R.id.dynamic_video).setVisibility(View.VISIBLE);
                }
                if (Dynamicitem.getImg() != null) {
                    if (Dynamicitem.getImg().get(0).contains("uploads")) {
                        Glide.with(context).load(ComantUtils.MyUrlHot1 + Dynamicitem.getImg().get(0)).into((ImageView) helper.getView(R.id.item_videoImage));
                    } else {
                        Glide.with(context).load(ComantUtils.BigImgUrl + Dynamicitem.getImg().get(0)).into((ImageView) helper.getView(R.id.item_videoImage));
                    }
                }
                helper.addOnClickListener(R.id.all_view);
                break;
        }
        RequestOptions options = new RequestOptions();
        options.error(R.mipmap.huangye);
        options.placeholder(R.mipmap.huangye);

        helper.addOnClickListener(R.id.follow_button);//关注点击事件
        try {
            if(Dynamicitem.getIs_anymit()==null||Dynamicitem.getIs_anymit().equals("1"))
            {
                helper.setText(R.id.tv_square_person_name, Dynamicitem.getUserName());//昵称
                Glide.with(context).load(Dynamicitem.getHeadImg()).into((ImageView) helper.getView(R.id.img_square_header));

            }
            else if(Dynamicitem.getIs_anymit().equals("0"))
            {
                helper.setText(R.id.tv_square_person_name, "匿名用户");//昵称
                Glide.with(context).load(R.mipmap.nimingyonghu).into((ImageView) helper.getView(R.id.img_square_header));

            }

        } catch (Exception s) {
            s.printStackTrace();

            helper.setText(R.id.tv_square_person_name, "彼信用户");//昵称
            Glide.with(context).load(R.mipmap.user_head_img).into((ImageView) helper.getView(R.id.img_square_header));
        }


        helper.setText(R.id.tv_creatTime, Dynamicitem.getCreateTime())//时间
                .setText(R.id.tv_square_msgTitle, new String(Base64.decode(Dynamicitem.getDynamicContent().getBytes(), Base64.DEFAULT)) )//内容
                .setText(R.id.numPL, Dynamicitem.getCommentNum())//评论
                .setText(R.id.numZan, Dynamicitem.getPraiseNum());//点赞
        if (Dynamicitem.getUserId().equals(MyApplication.getCurrentUserInfo().getUserId())) {
            helper.getView(R.id.img_delete).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.img_delete).setVisibility(GONE);
        }
        if (Dynamicitem.isAttention()) {//关注设置
            helper.getView(R.id.follow_button).setSelected(false);
        } else {
            helper.getView(R.id.follow_button).setSelected(true);
        }
        helper.getView(R.id.zanImg).setSelected(Dynamicitem.isPraise());
        getUserCommunity(Dynamicitem.getUserId(), helper);

        //事件
        helper.addOnClickListener(R.id.img_square_header);//头像
        helper.addOnClickListener(R.id.follow_button);//关注
        helper.addOnClickListener(R.id.img_delete);//删除
        helper.addOnClickListener(R.id.ll_zan);//点赞
        helper.addOnClickListener(R.id.id2);//评论
        helper.getView(R.id.ic_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getbitmp(Dynamicitem.getHeadImg());

                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setTitle("分享")
                        .setIcon(R.mipmap.wechat_moment)
                        .setNegativeButton("微信好友", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                shareWeChat(helper, Dynamicitem);
                            }
                        });
                builder1.setPositiveButton("朋友圈", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        shareWeChatMoments(helper, Dynamicitem);
                    }
                });
                builder1.show();

            }
        });//分享
        helper.getView(R.id.tv_userAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Act_News_Web.class);

                intent.putExtra("url", Dynamicitem.getCommunityUrl());
                intent.putExtra("company_name", ((TextView) helper.getView(R.id.tv_userAdd)).getText().toString());
                intent.putExtra("communityId", Dynamicitem.getCommunityId());
                intent.putExtra("targetUserId", Dynamicitem.getUserId());
              //  backdatatoserver(Dynamicitem.getCommunityId());
                context.startActivity(intent);

            }
        });//跳转公司

    }

   private void  getUserCommunity(String userId, final BaseViewHolder holder) {

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

        return   ;
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

    private void shareWeChat(BaseViewHolder holder, DynamicBase item) {
        try {

            JShareInterface.share(Wechat.Name, generateParams(holder, item), new PlatActionListener() {
                @Override
                public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                    Log.d("aa", "------onComplete:" + i);
                }

                @Override
                public void onError(Platform platform, int i, int i1, Throwable throwable) {
                    Log.e("aa", "-------platform:" + i + "____" + platform.getName() + throwable.getMessage());
                }

                @Override
                public void onCancel(Platform platform, int i) {
                    Log.e("aa", "-------onCancel:" + i);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private ShareParams generateParams(BaseViewHolder holder,final  DynamicBase item) {




        ShareParams shareParams = new ShareParams();
        shareParams.setShareType(Platform.SHARE_WEBPAGE);
        shareParams.setTitle(new String(Base64.decode(item.getDynamicContent(), Base64.DEFAULT)) );
        shareParams.setText("彼信商业社区："+item.getUserName());

        shareParams.setImageData(Sharebit);
        shareParams.setUrl( "http://bisonchat.com/home/user_dynamic/appGambitDetails.html?dynamicId="+item.getDynamicId());
        return shareParams;


    }

    private void shareWeChatMoments(BaseViewHolder holder, DynamicBase item) {
        JShareInterface.share(WechatMoments.Name, generateParams(holder, item), null);
    }

    private Bitmap convertViewToBitmap(BaseViewHolder viewHolder) {
        viewHolder.getView(R.id.all_view).setDrawingCacheEnabled(true);
        viewHolder.getView(R.id.ll_share).setVisibility(View.VISIBLE);
        viewHolder.getView(R.id.all_view).buildDrawingCache();  //启用DrawingCache并创建位图
        Bitmap bitmap = Bitmap.createBitmap(viewHolder.getView(R.id.all_view).getDrawingCache()); //创建一个DrawingCache的拷贝，因为DrawingCache得到的位图在禁用后会被回收
        viewHolder.getView(R.id.ll_share).setVisibility(GONE);
        viewHolder.getView(R.id.all_view).setDrawingCacheEnabled(false);

        return bitmap;
    }


private  void getbitmp(final String  headstring  )
{

    new Thread(new Runnable() {
        @Override
        public void run() {
            try {

                Sharebit = Glide.with(context)
                        .asBitmap()
                        .load(headstring)
                        .into(300,300)
                        .get();


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }).start();

}

}
