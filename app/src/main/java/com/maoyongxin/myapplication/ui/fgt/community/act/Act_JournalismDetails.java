package com.maoyongxin.myapplication.ui.fgt.community.act;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import com.maoyongxin.myapplication.tool.UtilsTool;
import com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.fgt.bean.UserEvent;
import com.maoyongxin.myapplication.ui.fgt.community.adapter.JournalismDetailsAdapter;
import com.maoyongxin.myapplication.ui.fgt.community.bean.JournalismDetailsBean;
import com.maoyongxin.myapplication.ui.fgt.community.bean.UserFriendsFollowApiBean;
import com.maoyongxin.myapplication.ui.fgt.community.bean.plbean;
import com.maoyongxin.myapplication.ui.fgt.connection.act.Act_AnnouncementReply;
import com.maoyongxin.myapplication.ui.fgt.connection.act.bean.AnnouncementDetailsBean;
import com.maoyongxin.myapplication.ui.fgt.connection.adapter.AnnouncementDetailsAdapter;
import com.maoyongxin.myapplication.ui.fgt.message.act.strangerdetail.fgt.framdialog.ShareDialogFragment;
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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jiguang.share.android.api.JShareInterface;
import cn.jiguang.share.android.api.PlatActionListener;
import cn.jiguang.share.android.api.Platform;
import cn.jiguang.share.android.api.ShareParams;
import cn.jiguang.share.wechat.Wechat;
import cn.jiguang.share.wechat.WechatMoments;
import io.rong.eventbus.EventBus;
import okhttp3.Call;

/**
 * 新闻详情
 */
public class Act_JournalismDetails extends BaseAct implements BaseQuickAdapter.OnItemChildClickListener, ShareDialogFragment.Listener {
    ImageView touxiang;
    TextView title,tv_pubtime,tv_newsource;
    TextView time;
    TextView peice;
    TextView contant;
    ImageView image;
    TextView pinglun;
    TextView newsTitle,notice_sender;
    RoundedImageView newsImg;
    TextView liuyana;
    RecyclerView recycle;
    EditText kuang;
    Button send;
    RelativeLayout activityDetaliAzh;
    WebView idk;
    TextView notice_title1;
    LinearLayout ll_topview,rl_consignee_info;
    ImageView fenxiang;
    RoundedImageView notice_image;
    RelativeLayout view_not;
    private Bitmap shareBit;
    View view ;
    private String id1,readNum;
    private String uid, news_Title;
    private String news_Img;
    private List<plbean.ObjBean.NewsCommentListBean> newsCommentList;
    private String content, tvdateTime;
    private TextView dateTime;
    private SmartRefreshLayout smartRefreshLayout;
    private Boolean nodata=true;
    private ArrayList<JiGuangSharePlatformModel> list = new ArrayList<>();
    private String newstitle,newsubtitle,shareImgUrl;

    @Override
    protected void handlerPassMsg(int target, int target1, Object obj) {

    }

    @Override
    public int initLayoutId() {
        return R.layout.act_journalismdetails;
    }
    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);

        // Postpone the shared element return transition.
        postponeEnterTransition();

        // TODO: Call the "scheduleStartPostponedTransition()" method
        // above when you know for certain that the shared element is
        // ready for the transition to begin.
    }

    @Override
    public void initView() {

        Window window = getWindow();

        window.setSharedElementsUseOverlay(true);


        hideHeader();
        dialog = new ZLoadingDialog(context);
        dialog.setLoadingBuilder(Z_TYPE.ROTATE_CIRCLE)//设置类型
                .setLoadingColor(Color.DKGRAY)//颜色
                .setHintText("数据加载中...")
                .setHintTextSize(16) // 设置字体大小 dp
                .setHintTextColor(Color.DKGRAY)  // 设置字体颜色
                .setDurationTime(0.5) // 设置动画时间百分比 - 0.5倍
                .setDialogBackgroundColor(Color.parseColor("#CCffffff")); // 设置背景色，默认白色
        setOnClickListener(R.id.friends_back);
        tv_pubtime=getView(R.id.tv_pubtime);
        ll_topview=getView(R.id.ll_topview);
        tv_newsource=getView(R.id.tv_newsource);
        view=getView(R.id.middleLine);
        rl_consignee_info=getView(R.id.rl_consignee_info);
        notice_image=getView(R.id.notice_image);
        kuang = getView(R.id.kuang);
        send = getViewAndClick(R.id.send);
        pinglun = getView(R.id.pinglun);
        recycle = getView(R.id.recycle);
        view_not=getView(R.id.view_not);
        activityDetaliAzh = getView(R.id.activity_detali_azh);

        notice_sender=getView(R.id.notice_sender);
        smartRefreshLayout = getView(R.id.refreshLayout11111);
        setOnClickListener(R.id.journalismdetailse_back);


        notice_title1=getView(R.id.notice_title1);
        liuyana = getView(R.id.liuyana);
        touxiang = getView(R.id.touxiang);
        dateTime = getView(R.id.dateTime);
        title = getView(R.id.title);
        time = getView(R.id.time);
        peice = getView(R.id.peice);
        contant = getView(R.id.contant);
        image = getView(R.id.image);
        newsTitle = getView(R.id.newsTitle);
        newsImg = getView(R.id.news_img);
        idk = getView(R.id.idk);
        fenxiang = getView(R.id.fenxiang);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();


        readNum=bundle.getString("readNum");
        id1 = bundle.getString("id1");
        uid = bundle.getString("uid");
        tvdateTime = bundle.getString("dateTime");
      //  news_Title = bundle.getString("newstitle");
        content = bundle.getString("content");
        shareImgUrl = bundle.getString("news_Img");
        dateTime.setText(tvdateTime);
        notice_sender.setText(readNum);
        tv_pubtime.setText("时间：" + tvdateTime);

        newstitle=bundle.getString("newstitle");
        notice_title1.setText( newstitle);

        Glide.with(getActivity()).load(shareImgUrl).into(notice_image);

        tv_newsource.setText("彼信商业头条" );

        WebSettings webSettings = idk.getSettings();
        webSettings.setJavaScriptEnabled(true);
        idk.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(),"屏蔽了弹框",Toast.LENGTH_SHORT).show();
                    }
                });


                return true;
            }
        });
        idk.loadDataWithBaseURL(null, "<html><head><style>img {width:100%;height:auto;margin:auto;}</style></head><body>" + content + "</body></html>", "text/html", "utf-8", null);

        view_not.setVisibility(View.VISIBLE);
        recycle.setVisibility(View.GONE);


       supportStartPostponedEnterTransition();
        readNumfresh(id1);

        fenxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showShareDialog();
            }
        });
    }

    ZLoadingDialog dialog;
    private int page = 1;

    @Override
    public void initData() {
        dialog = new ZLoadingDialog(this);
        dialog.setLoadingBuilder(Z_TYPE.ROTATE_CIRCLE)//设置类型
                .setLoadingColor(Color.DKGRAY)//颜色
                .setHintText("数据加载中...")
                .setHintTextSize(16) // 设置字体大小 dp
                .setHintTextColor(Color.DKGRAY)  // 设置字体颜色
                .setDurationTime(0.5) // 设置动画时间百分比 - 0.5倍
                .setDialogBackgroundColor(Color.parseColor("#CCffffff")); // 设置背景色，默认白色
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recycle.setLayoutManager(layoutManager);
        //内容跟随偏移
        smartRefreshLayout.setEnableHeaderTranslationContent(true);
        //设置 Header 为 Material风格
        smartRefreshLayout.setRefreshHeader(new MaterialHeader(context).setShowBezierWave(false));
        //设置 Footer 为 球脉冲
        smartRefreshLayout.setRefreshFooter(new BallPulseFooter(context).setSpinnerStyle(SpinnerStyle.Scale));
        smartRefreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                postBackData();
                refreshLayout.finishLoadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                data.clear();
                postBackData();
                refreshLayout.finishRefresh();
            }
        });
        adapter = new JournalismDetailsAdapter(data, this);
        dialog.show();
        postBackData();
        kuang.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (kuang.getText().toString().length() > 0) {
                    send.setVisibility(View.VISIBLE);
                    fenxiang.setVisibility(View.GONE);
                } else {
                    send.setVisibility(View.GONE);
                    fenxiang.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private JournalismDetailsAdapter adapter;

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
            case R.id.journalismdetailse_back:
                finish();
                break;
            case R.id.send://发送
                postMessage();
                break;
        }
    }

    private void request(final int type) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id1);
        map.put("content", getURLEncoderString("<img src= '" + news_Img + "'/>" + content));
        OkHttpUtils.post().url("http://360.d1.natapp.cc/Index/AddIntex").params(map).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                String url = "http://360.d1.natapp.cc/Index/index?id=" + response;
                switch (type) {
                    case 0:
                        shareWeChat(url);
                        break;
                    case 1:
                        shareWeChatMoments(url);
                        break;
                }

            }
        });
    }

    private void shareWeChatMoments(String url) {
        JShareInterface.share(WechatMoments.Name, generateParams(url), null);
    }

    private void shareWeChat(String url) {
        try {
            JShareInterface.share(Wechat.Name, generateParams(url), new PlatActionListener() {
                @Override
                public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                    Log.d("---", "onComplete:" + i);
                }

                @Override
                public void onError(Platform platform, int i, int i1, Throwable throwable) {
                    Log.e("----", "platform:" + i + "____" + platform.getName() + throwable.getMessage());
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

    private ShareParams generateParams(String url) {
        ShareParams shareParams = new ShareParams();
        shareParams.setShareType(Platform.SHARE_WEBPAGE);
        shareParams.setTitle(news_Title);
        shareParams.setUrl(url);
        shareParams.setText(getIntent().getStringExtra("source"));
        Resources res = Act_JournalismDetails.this.getResources();
        Bitmap bmp = BitmapFactory.decodeResource(res, R.mipmap.app_icon);
        shareParams.setImageData(bmp);
//        shareParams.setImagePath(news_Img);
        return shareParams;
    }

    public String getURLEncoderString(String str) {//url编码
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 返回评论列表
     */
    List<JournalismDetailsBean.InfoBean.DataBeanX> data = new ArrayList<>();

    private void postBackData() {
        String url = ComantUtils.MyUrlHot + "/news_comment/getNewsCommentListApi";
        OkHttpUtils.post().url(url)
                .addParams("newsId", id1 + "")
                .addParams("myuid", MyApplication.getCurrentUserInfo().getUserId())
                .addParams("page", page + "")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Debug.e("----------onError===" + e.getMessage());
                dialog.dismiss();
            }

            @Override
            public void onResponse(String response, int id) {
                Debug.e("----------response===" + response);
                dialog.dismiss();
                Gson gson = new Gson();
                JournalismDetailsBean bean = gson.fromJson(response, JournalismDetailsBean.class);
                if(bean.getInfo().getData().size()>0)
                {
                    nodata=false;
                    view_not.setVisibility(View.GONE);
                    recycle.setVisibility(View.VISIBLE);
                    for (int i = 0; i < bean.getInfo().getData().size(); i++) {
                        data.add(bean.getInfo().getData().get(i));
                    }
                    liuyana.setText("评论 ( " + data.size() + " )");
                    pinglun.setText("" + data.size());
                    recycle.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    adapter.setOnItemChildClickListener(Act_JournalismDetails.this);
                }

                else if(nodata)
                {
                    view_not.setVisibility(View.VISIBLE);
                    recycle.setVisibility(View.GONE);
                }


            }
        });
    }

    /**
     * 发送评论
     */
    public void postMessage() {
        if (TextUtils.isEmpty(kuang.getText().toString())) {
            return;
        }
        dialog.show();
        String url = ComantUtils.MyUrlHot + "/news_comment/setNewsCommentApi";
        String strBase64 = Base64.encodeToString(kuang.getText().toString().getBytes(), Base64.DEFAULT);
        OkHttpUtils.post().url(url)
                .addParams("uid", MyApplication.getCurrentUserInfo().getUserId())
                .addParams("news_id", id1)
                .addParams("content", strBase64)
                .addParams("parent_id", "0")
                .addParams("parent_uid", "0")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                dialog.dismiss();
            }

            @Override
            public void onResponse(String response, int id) {
                kuang.setText("");
                page = 1;
                data.clear();
                postBackData();
            }
        });
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.item_delte://删除
                postBackData(position);
                break;
            case R.id.item_huifu://回复
                if (data.get(position).getUid().equals(MyApplication.getCurrentUserInfo().getUserId())) {
                    MyToast.show(context, "自己不能对自己评论！");
                } else {
                    Intent intent = new Intent();
                    intent.setClass(context, Act_AnnouncementReply.class);
                    intent.putExtra("title", "JournalismDetails");
                    intent.putExtra("id1", id1);
                    intent.putExtra("parent_id", data.get(position).getId() + "");
                    intent.putExtra("parent_uid", data.get(position).getUid() + "");
                    startActivityForResult(intent, 10);
                }
                break;
            case R.id.zanImg://点赞
                view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.zan_anim));
                if (data.get(position).isIsPraise()) {
                    setCancelLikeApi(position, MyApplication.getCurrentUserInfo().getUserId(), data.get(position).getId());
                } else {
                    dianzan(position, MyApplication.getCurrentUserInfo().getUserId(), data.get(position).getId());
                }
                break;
        }
    }

    /**
     * 删除新闻评论信息接口
     */
    private void postBackData(int position) {
        dialog.show();
        String url = ComantUtils.MyUrlHot + "/news_comment/deleteNewsCommentApi.html";
        OkHttpUtils.post().url(url)
                .addParams("id", data.get(position).getId())
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                dialog.dismiss();
            }

            @Override
            public void onResponse(String response, int id) {
                page = 1;
                data.clear();
                postBackData();


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data1) {
        super.onActivityResult(requestCode, resultCode, data1);
        if (requestCode == 10 && resultCode == 10) {
            page = 1;
            data.clear();
            postBackData();
        }

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
                    data.get(position).setIsPraise(true);
                    data.get(position).setPraise_num(String.valueOf(Integer.valueOf(data.get(position).getPraise_num()) + 1));
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
                Debug.e("----onError-" + call.toString());
                dialog.dismiss();
            }

            @Override
            public void onResponse(String response, int id) {
                dialog.dismiss();
                UserFriendsFollowApiBean bean = gson.fromJson(response, UserFriendsFollowApiBean.class);
                if (bean.getCode() == 200) {
                    data.get(position).setIsPraise(false);
                    data.get(position).setPraise_num(String.valueOf(Integer.valueOf(data.get(position).getPraise_num()) - 1));
                    adapter.notifyDataSetChanged();
                    MyToast.show(context, bean.getMsg());
                }
            }
        });
    }

    private void readNumfresh(String newsId) {//点赞

        OkHttpUtils.post().url("http://bisonchat.com/index/news/setReadNumApi.html" )
                .addParams("id", newsId)

                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Debug.e("----onError-" + call.toString());

            }

            @Override
            public void onResponse(String response, int id) {


            }
        });
    }


    private ShareParams generateParams() {
        ShareParams shareParams = new ShareParams();
        shareParams.setShareType(Platform.SHARE_WEBPAGE);
        shareParams.setTitle( newstitle);
        shareParams.setText("彼信商业社区：商业头条");

        shareParams.setImageData(shareBit);
        shareParams.setUrl( "http://bisonchat.com/home/news/appDetails.html?id="+id1);

        return shareParams;
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
    private void showShareDialog() {
        list.clear();
        getshareImg();
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
    private void getshareImg() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    shareBit = Glide.with(getActivity())
                            .asBitmap()
                            .load(shareImgUrl)
                            .into(300, 300)
                            .get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
