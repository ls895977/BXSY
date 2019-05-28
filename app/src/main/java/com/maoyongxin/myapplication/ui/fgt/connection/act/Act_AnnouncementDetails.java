package com.maoyongxin.myapplication.ui.fgt.connection.act;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lykj.aextreme.afinal.utils.Debug;
import com.lykj.aextreme.afinal.utils.MyToast;
import com.makeramen.roundedimageview.RoundedImageView;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.bean.JiGuangSharePlatformModel;
import com.maoyongxin.myapplication.common.BaseAct;
import com.maoyongxin.myapplication.common.ComantUtils;
import com.maoyongxin.myapplication.common.MyApplication;
import com.maoyongxin.myapplication.permission.RxPermissions;
import com.maoyongxin.myapplication.ui.fgt.community.bean.UserFriendsFollowApiBean;
import com.maoyongxin.myapplication.ui.fgt.connection.act.base.DelateMeaageBean;
import com.maoyongxin.myapplication.ui.fgt.connection.act.bean.AnnouncementDetailsBean;
import com.maoyongxin.myapplication.ui.fgt.connection.act.bean.ConnectionBean;
import com.maoyongxin.myapplication.ui.fgt.connection.act.bean.UserNoticeDetailsApiBean;
import com.maoyongxin.myapplication.ui.fgt.connection.act.bean.UserRequitDetailBean;
import com.maoyongxin.myapplication.ui.fgt.connection.adapter.AnnouncementDetailsAdapter;
import com.maoyongxin.myapplication.ui.fgt.message.act.Act_StrangerDetail;
import com.maoyongxin.myapplication.ui.fgt.message.act.strangerdetail.fgt.framdialog.ShareDialogFragment;
import com.maoyongxin.myapplication.ui.fgt.min.act.Act_UserEditNew;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import org.json.JSONObject;
import org.w3c.dom.Text;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import cn.jiguang.share.android.api.JShareInterface;
import cn.jiguang.share.android.api.PlatActionListener;
import cn.jiguang.share.android.api.Platform;
import cn.jiguang.share.android.api.ShareParams;
import cn.jiguang.share.wechat.Wechat;
import cn.jiguang.share.wechat.WechatMoments;
import io.rong.imkit.RongIM;
import okhttp3.Call;

/**
 * 公告需求详情
 */
public class Act_AnnouncementDetails extends BaseAct implements BaseQuickAdapter.OnItemChildClickListener, ShareDialogFragment.Listener {
    private TextView collection,fb_user_phone;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView myRecyclerView;

    private String user_notice_id;
    private View haderView;
    private List<AnnouncementDetailsBean.InfoBean.DataBeanX> datas = new ArrayList<>();
    private TextView classifyName, classTitle, content, area, phone, tvQQ, num, tvDeate, toWeb,fb_user_name;
    private RelativeLayout empty_view;
    private String classify_id;
    private ArrayList<JiGuangSharePlatformModel> list = new ArrayList<>();
    Bitmap shareBit;
    private Boolean nodatasinfo=true;
    private RoundedImageView userhead;
    private String targetuserId;
    private String targetusername;
    private String targetuserphone;
    private RxPermissions rxPermissions;
    private TextView fb_createtime;
    private LinearLayout ll_topcard;
    @Override
    protected void handlerPassMsg(int target, int target1, Object obj) {

    }

    @Override
    public int initLayoutId() {
        return R.layout.act_announcementdetails;
    }

    @Override
    public void initView() {
        hideHeader();
        classify_id = getIntent().getStringExtra("classify_id");
        user_notice_id = getIntent().getStringExtra("user_notice_id");
        collection = getViewAndClick(R.id.AnnouncementDetails_collection);




        setOnClickListener(R.id.AnnouncementDetails_share);
        setOnClickListener(R.id.AnnouncementDetails_fabu);
        setOnClickListener(R.id.AnnouncementDetails_huifu);
        setOnClickListener(R.id.friends_back);

        mRefreshLayout = getView(R.id.refreshLayout);
        myRecyclerView = getView(R.id.recyclerView);
        haderView = LayoutInflater.from(this).inflate(R.layout.view_announcementdetails_hader, null);
        empty_view=getView(haderView,R.id.empty_view);
        classifyName = getView(haderView, R.id.classifyName);
        classTitle = getView(haderView, R.id.title);
        content = getViewAndClick(haderView, R.id.content);
        area = getView(haderView, R.id.area);
        tvDeate = getViewAndClick(haderView, R.id.gonggao_delate);
        phone = getView(haderView, R.id.phone);
        tvQQ = getView(haderView, R.id.qq_account);
        num = getView(haderView, R.id.tv_zongtaoshu);
        toWeb = getViewAndClick(haderView, R.id.toWeb);
        userhead=getViewAndClick(haderView,R.id.fb_head);
        fb_user_name=getViewAndClick(haderView,R.id.fb_user_name);
        fb_user_phone=getViewAndClick(haderView,R.id.fb_user_phone);
        fb_createtime=getView(haderView,R.id.fbtime);
        ll_topcard=getView(haderView,R.id.ll_topcard);
    }

    private int page = 1;
    private AnnouncementDetailsAdapter adapter;
    ZLoadingDialog dialog;

    @Override
    public void initData() {
        collection.setSelected(false);
        rxPermissions = new RxPermissions(getActivity());
        dialog = new ZLoadingDialog(this);
        dialog.setLoadingBuilder(Z_TYPE.ROTATE_CIRCLE)//设置类型
                .setLoadingColor(Color.DKGRAY)//颜色
                .setHintText("数据加载中...")
                .setHintTextSize(16) // 设置字体大小 dp
                .setHintTextColor(Color.DKGRAY)  // 设置字体颜色
                .setDurationTime(0.5) // 设置动画时间百分比 - 0.5倍
                .setDialogBackgroundColor(Color.parseColor("#CCffffff")); // 设置背景色，默认白色
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        myRecyclerView.setLayoutManager(layoutManager);
        //内容跟随偏移
        mRefreshLayout.setEnableHeaderTranslationContent(true);
        //设置 Header 为 Material风格
        mRefreshLayout.setRefreshHeader(new MaterialHeader(context).setShowBezierWave(false));
        //设置 Footer 为 球脉冲
        mRefreshLayout.setRefreshFooter(new BallPulseFooter(context).setSpinnerStyle(SpinnerStyle.Scale));
        mRefreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                postBackDtata();
                refreshLayout.finishLoadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                datas.clear();
                postBackDtata();
                refreshLayout.finishRefresh();
            }
        });
        adapter = new AnnouncementDetailsAdapter(datas, this);
        adapter.setOnItemChildClickListener(this);
        adapter.addHeaderView(haderView);
        myRecyclerView.setAdapter(adapter);
        posthaderBack();
    }

    @Override
    public void requestData() {

    }

    @Override
    public void updateUI() {

    }

    @Override
    public void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.friends_back:
                finish();
                break;
            case R.id.AnnouncementDetails_collection://收藏
                if (collection.isSelected()) {
                    Canceshoucnag(bean.getInfo().getId());
                } else {
                    shouchang(user_notice_id + "", notice_type + "");
                }
                break;
            case R.id.AnnouncementDetails_share://分享

                showShareDialog();
                break;
            case R.id.AnnouncementDetails_fabu://发布需求

                RongIM.getInstance().startPrivateChat(getActivity(), targetuserId, targetusername);

                break;
            case R.id.AnnouncementDetails_huifu://回复
                Intent intent = new Intent();
                intent.setClass(context, Act_AnnouncementReply.class);
                intent.putExtra("notice_type", notice_type);
                intent.putExtra("user_notice_id", user_notice_id);
                startActivityForResult(intent, 10);
                break;
            case R.id.gonggao_delate://公告删除
                delateGongGao();
                break;
            case R.id.content:
                /*             判断跳不跳网页
                Intent intent1 = new Intent();
                intent1.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(bean.getInfo().getDetail_url());//此处填链接
                intent1.setData(content_url);
                startActivity(intent1);
                */
                break;


            case R.id.toWeb:

                Intent intent2 = new Intent();
                intent2.setAction("android.intent.action.VIEW");
                Uri content_url2 = Uri.parse(bean2.getInfo().getDetail_url());//此处填链接
                intent2.setData(content_url2);
                startActivity(intent2);

                break;


            case R.id.fb_user_phone:

                        rxPermissions.request(Manifest.permission.CALL_PHONE).subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if (aBoolean) {
                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_CALL);
                                    intent.setData(Uri.parse("tel:" + targetuserphone));

                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getActivity(), "请打开拨打电话权限", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        break;


            case R.id.fb_head:

                Intent intent3 = new Intent(getActivity(), Act_StrangerDetail.class);
                intent3.putExtra("personId",bean.getInfo().getUid());
                startActivity(intent3);

                break;
        }
    }

    /**
     * 获取评论列表
     */
    public void postBackDtata() {
        OkHttpUtils.get().url(ComantUtils.MyUrlHot + ComantUtils.getUserNoticePostListApi)
                .addParams("myuid", MyApplication.getCurrentUserInfo().getUserId())
                .addParams("user_notice_id", user_notice_id)
                .addParams("notice_type", notice_type)
                .addParams("page", page + "")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(String response, int id) {
                Gson gson = new Gson();


                AnnouncementDetailsBean bean = gson.fromJson(response, AnnouncementDetailsBean.class);
                try
                {
                    if(0<bean.getInfo().getData().size())
                    {

                        nodatasinfo=false;
                        empty_view.setVisibility(View.GONE);

                        for (int i = 0; i < bean.getInfo().getData().size(); i++) {
                            datas.add(bean.getInfo().getData().get(i));
                        }

                        num.setText("(" + datas.size() + ")");
                        adapter.setNewData(datas);
                        adapter.notifyDataSetChanged();
                    }

                    else if(nodatasinfo)
                    {
                        empty_view.setVisibility(View.VISIBLE);


                    }
                }
                catch (Exception e)
                {

                }


            }
        });
    }

    /**
     * 获取公告头部信息
     */
    UserRequitDetailBean bean;

    UserNoticeDetailsApiBean bean2;
    private String notice_type;

    public void posthaderBack() {
        OkHttpUtils.post().url(ComantUtils.MyUrlHot + ComantUtils.getUserNoticeDetailsApi)
                .addParams("id", user_notice_id)
                .addParams("classify_id", classify_id)
                .addParams("myuid", MyApplication.getCurrentUserInfo().getUserId())
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(String response, int id) {
                Gson gson = new Gson();
                try
                {
                    JSONObject obj = new JSONObject(response);
                    if (!obj.getJSONObject("info").isNull("notice_title"))
                    {
                     //   MyToast.show(getActivity(),"政府公告类型");
                        ll_topcard.setVisibility(View.GONE);
                        bean2=gson.fromJson(response,UserNoticeDetailsApiBean.class);

                        collection.setSelected(bean2.getInfo().isEnshrine());
                        classifyName.setText(bean2.getInfo().getClassifyName());
                        classTitle.setText(bean2.getInfo().getTitle());
                        content.setText(bean2.getInfo().getNotice_title());
                        tvDeate.setVisibility(View.VISIBLE);
                    }
                    else if(!obj.getJSONObject("info").isNull("title"))
                    {
                    //    MyToast.show(getActivity(),"企业采购类型");
                        bean = gson.fromJson(response, UserRequitDetailBean.class);
                        postBackDtata();
                        try {


                            notice_type = bean.getInfo().getNotice_type() + "";

                            fb_user_name.setText(bean.getInfo().getUserName());
                            targetuserId=bean.getInfo().getUid();
                            targetusername=bean.getInfo().getUserName();
                            targetuserphone=bean.getInfo().getPhone();

                            Glide.with(getActivity()).load(bean.getInfo().getHeadImg()).into(userhead);





                            classifyName.setText(bean.getInfo().getClassifyName());
                            classTitle.setText(bean.getInfo().getTitle());

                            area.setText("地址：" + bean.getInfo().getArea());
                            content.setText(bean.getInfo().getContent());
                            tvQQ.setText("预算：" + bean.getInfo().getQq_account());
                            fb_createtime.setText(bean.getInfo().getCreateTime());
                            if (bean.getInfo().getUid().equals(MyApplication.getCurrentUserInfo().getUserId())) {
                                tvDeate.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                tvDeate.setVisibility(View.GONE);
                            }

                                toWeb.setVisibility(View.GONE);


                        } catch (Exception e) {
                        }
                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }


            }
        });
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.item_huifu:
                if (datas.get(position).getUid().equals(MyApplication.getCurrentUserInfo().getUserId())) {
                    MyToast.show(context, "自己不能对自己评论！");
                } else {
                    Intent intent = new Intent();
                    intent.setClass(context, Act_AnnouncementReply.class);
                    intent.putExtra("user_notice_id", user_notice_id);
                    intent.putExtra("notice_type", notice_type);
                    intent.putExtra("parent_id", datas.get(position).getId() + "");
                    intent.putExtra("parent_uid", datas.get(position).getUid() + "");
                    startActivityForResult(intent, 10);
                }
                break;
            case R.id.item_delte://自己评论的数据删除
                delateData(position);
                break;
            case R.id.zanImg:
                view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.zan_anim));
                if (datas.get(position).isIsPraise()) {
                    setCancelLikeApi(position, MyApplication.getCurrentUserInfo().getUserId(), datas.get(position).getId());
                } else {
                    dianzan(position, MyApplication.getCurrentUserInfo().getUserId(), datas.get(position).getId());
                }

                break;

            case R.id.user_top:
                Intent intent5 = new Intent(getActivity(), Act_StrangerDetail.class);
                intent5.putExtra("personId",datas.get(position).getUid());
                startActivity(intent5);

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == 10) {//刷新评论
            datas.clear();
            page = 1;
            postBackDtata();
        }
    }

    /**
     * 删除自己评论条目
     */
    public void delateData(int position) {
        dialog.show();
        OkHttpUtils.post().url(ComantUtils.MyUrlHot + ComantUtils.deleteUserNoticePost)
                .addParams("id", datas.get(position).getId() + "")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                dialog.dismiss();
            }

            @Override
            public void onResponse(String response, int id) {
                Gson gson = new Gson();
                DelateMeaageBean bean = gson.fromJson(response, DelateMeaageBean.class);
                if (bean.isOperation()) {
                    datas.clear();
                    page = 1;
                    postBackDtata();
                }
                MyToast.show(context, bean.getMsg());
                dialog.dismiss();
            }
        });
    }

    /**
     * 删除自己公告条目
     */
    public void delateGongGao() {
        dialog.show();
        OkHttpUtils.post().url(ComantUtils.MyUrlHot + ComantUtils.deleteUserNoticeApi)
                .addParams("id", user_notice_id)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                dialog.dismiss();
            }

            @Override
            public void onResponse(String response, int id) {
                Gson gson = new Gson();
                DelateMeaageBean bean = gson.fromJson(response, DelateMeaageBean.class);
                if (bean.isOperation()) {
                    setResult(20);
                    finish();
                }
                MyToast.show(context, bean.getMsg());
                dialog.dismiss();
            }
        });
    }


    /**
     * 点赞
     * data_type  1动态点赞 2动态评论点赞  3话题点赞 4话题评论点赞
     */
    String LikeApiType = "1";
    Gson gson = new Gson();

    private void dianzan(final int position, String user_id, String data_id) {//点赞
        dialog.show();
        OkHttpUtils.post().url(ComantUtils.MyUrlHot + ComantUtils.setLikeApi)
                .addParams("user_id", user_id)
                .addParams("data_id", data_id)
                .addParams("data_type", "6")
                .addParams("type", LikeApiType)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                dialog.dismiss();
            }

            @Override
            public void onResponse(String response, int id) {
                dialog.dismiss();
                UserFriendsFollowApiBean bean = gson.fromJson(response, UserFriendsFollowApiBean.class);
                if (bean.getCode() == 200) {
                    datas.get(position).setIsPraise(true);
                    datas.get(position).setPraise_num(String.valueOf(Integer.valueOf(datas.get(position).getPraise_num()) + 1));
                    adapter.notifyDataSetChanged();
                    MyToast.show(context, bean.getMsg());
                }
            }
        });
    }

    /**
     * 取消点赞
     * data_type  1动态点赞 2动态评论点赞  3话题点赞 4话题评论点赞
     */
    private void setCancelLikeApi(final int position, String user_id, String data_id) {//点赞
        dialog.show();
        OkHttpUtils.post().url(ComantUtils.MyUrlHot + ComantUtils.setCancelLikeApi)
                .addParams("user_id", user_id)
                .addParams("data_id", data_id)
                .addParams("data_type", "6")
                .addParams("type", LikeApiType)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                dialog.dismiss();
            }

            @Override
            public void onResponse(String response, int id) {
                dialog.dismiss();
                UserFriendsFollowApiBean bean = gson.fromJson(response, UserFriendsFollowApiBean.class);
                if (bean.getCode() == 200) {
                    datas.get(position).setIsPraise(false);
                    datas.get(position).setPraise_num(String.valueOf(Integer.valueOf(datas.get(position).getPraise_num()) - 1));
                    adapter.notifyDataSetChanged();
                    MyToast.show(context, bean.getMsg());
                }
            }
        });
    }


    /**
     * 公告收藏
     */
    private void shouchang(String notice_id, String notice_type) {
        dialog.show();
        OkHttpUtils.post().url("http://bisonchat.com/index/notice_enshrine/addNoticeEnshrineApi.html")
                .addParams("notice_id", notice_id)
                .addParams("notice_type", notice_type)
                .addParams("uid", MyApplication.getCurrentUserInfo().getUserId())
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                dialog.dismiss();
            }

            @Override
            public void onResponse(String response, int id) {
                dialog.dismiss();
                UserFriendsFollowApiBean bean = gson.fromJson(response, UserFriendsFollowApiBean.class);
                if (bean.getCode() == 200) {
                    collection.setSelected(true);
                    MyToast.show(context, bean.getMsg());
                }
            }
        });
    }

    /**
     * 取消shoucang
     */
    private void Canceshoucnag(String id) {
        dialog.show();
        OkHttpUtils.post().url("http://bisonchat.com/index/notice_enshrine/deleteUserNoticeApi.html")
                .addParams("notice_id", user_notice_id)
                .addParams("notice_type", notice_type)
                .addParams("myuid", MyApplication.getCurrentUserInfo().getUserId())
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                dialog.dismiss();
            }

            @Override
            public void onResponse(String response, int id) {
                dialog.dismiss();
                UserFriendsFollowApiBean bean = gson.fromJson(response, UserFriendsFollowApiBean.class);
                if (bean.getCode() == 200) {
                    collection.setSelected(false);
                    MyToast.show(context, bean.getMsg());
                }
            }
        });
    }


    private void shareWeChat() {
        try {
            JShareInterface.share(Wechat.Name, generateParams(), new PlatActionListener() {
                @Override
                public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                }

                @Override
                public void onError(Platform platform, int i, int i1, Throwable throwable) {
                }

                @Override
                public void onCancel(Platform platform, int i) {
                    Log.e("---", "onCancel:" + i);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void shareWeChatMoments() {
        JShareInterface.share(WechatMoments.Name, generateParams(), null);

    }

    private void showShareDialog() {
        list.clear();

        getshareImg(bean.getInfo().getHeadImg());
        List<String> successPlatform = JShareInterface.getPlatformList();

        for (String s : successPlatform) {
            //Log.e("---", "===>>" + s + "===>>" + JShareInterface.isClientValid(s));
            JShareInterface.isClientValid(s);
            if (s.equals("WechatFavorite")) {
                continue;
            }
            list.add(new JiGuangSharePlatformModel(s));

        }
        ShareDialogFragment dialogFragment = ShareDialogFragment.newInstance(list);
        dialogFragment.show(getSupportFragmentManager(), "tag");
    }

    private ShareParams generateParams() {




        ShareParams shareParams = new ShareParams();
        shareParams.setShareType(Platform.SHARE_WEBPAGE);
        shareParams.setTitle("["+bean.getInfo().getClassifyName()+"]"+bean.getInfo().getTitle());
        shareParams.setText(
                "用户：" +bean.getInfo().getUserName()+"  发布于 "+bean.getInfo().getCreateTime()+"\n"+
                "预算："+bean.getInfo().getQq_account()+"\n"+
                "数据来自：彼信商业社区\n");

        shareParams.setImageData(shareBit);
        shareParams.setUrl("http://bisonchat.com/home/notice/appNoticeDetails.html?notice_type=0&notice_id="+bean.getInfo().getId());

        return shareParams;
    }

    private void getshareImg(final String   userheadurl) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    shareBit = Glide.with(getActivity())
                            .asBitmap()
                            .load(userheadurl)
                            .into(300,300)
                            .get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onSharePlatformClicked(int position) {

        Toast.makeText(this, "正在准备分享...", Toast.LENGTH_SHORT).show();
        JiGuangSharePlatformModel model = list.get(position);

        switch (model.getPlatFormType()) {
            case WE_CHAT:
                shareWeChat();
                break;
            case WE_CHAT_MOMNETS:
                shareWeChatMoments();
                break;
        }


    }

    private void edit_userphone()
    {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());

        builder.setTitle("提示")
                .setMessage("根据《中华人民共和国网络安全法》的相关规定，发布信息需要您进行手机认证，我们将全力保障您的信息安全，除认证认证外，绝不另作他用！")
                .setNegativeButton("暂不认证", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                    }
                })
                .setPositiveButton("手机认证", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivityForResult(Act_UserEditNew.class, MyApplication.GETMY_USERINFO);

                    }
                }).show();
    }



    public static boolean isPhoneNumber(String phoneNo) {
        if (TextUtils.isEmpty(phoneNo)) {
            return false;
        }

        if (phoneNo.length() == 11) {
            for (int i = 0; i < 11; i++) {
                if (!PhoneNumberUtils.isISODigit(phoneNo.charAt(i))) {
                    return false;
                }
            }
            Pattern p = Pattern.compile("^((13[^4,\\D])" + "|(134[^9,\\D])" +
                    "|(14[5,7])" +
                    "|(15[^4,\\D])" +
                    "|(17[3,6-8])" +
                    "|(18[0-9]))\\d{8}$");
            Matcher m = p.matcher(phoneNo);
            return m.matches();
        }
        {

        }
        return false;
    }


}
