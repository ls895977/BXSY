package com.maoyongxin.myapplication.ui.fgt.community.act;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseViewHolder;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.bean.JiGuangSharePlatformModel;
import com.maoyongxin.myapplication.common.BaseAct;
import com.maoyongxin.myapplication.http.callback.EntityCallBack;
import com.maoyongxin.myapplication.http.request.ReqCommunity;
import com.maoyongxin.myapplication.http.response.MyCommunityResponse;
import com.maoyongxin.myapplication.ui.fgt.message.act.strangerdetail.fgt.framdialog.ShareDialogFragment;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import cn.jiguang.share.android.api.JShareInterface;
import cn.jiguang.share.android.api.PlatActionListener;
import cn.jiguang.share.android.api.Platform;
import cn.jiguang.share.android.api.ShareParams;
import cn.jiguang.share.wechat.Wechat;
import cn.jiguang.share.wechat.WechatMoments;
import okhttp3.Call;

/**
 * 公司地址网页浏览
 */
public class Act_News_Web extends BaseAct  implements ShareDialogFragment.Listener  {
    ImageView imgClose;
    WebView webMyContent;
    RelativeLayout activityShowWebUrl;
    String weburl, companyname, communityId, targetUserId;
    TextView companyName;
    ImageView contactFwy;
    ImageView shareIcon;
    private ArrayList<JiGuangSharePlatformModel> list = new ArrayList<>();
    String shareimg;
    Bitmap shareBit;
    private String communityid="";
    private String webapi="http://st.3dgogo.com/index/enterprise_publicity/get_community_id_details_url_api.html?community_id=";
    @Override
    protected void handlerPassMsg(int target, int target1, Object obj) {

    }

    @Override
    public int initLayoutId() {
        return R.layout.act_news_web;
    }

    @Override
    public void initView() {
        hideHeader();
        imgClose = getViewAndClick(R.id.img_close);
        webMyContent = getView(R.id.web_myContent);
        activityShowWebUrl = getView(R.id.activity_show_web_url);
        companyName = getViewAndClick(R.id.company_name);
        contactFwy = getView(R.id.contact_fwy);
        shareIcon = getView(R.id.share_icon);

        setOnClickListener(R.id.img_close);
        setOnClickListener(R.id.contact_fwy);
        setOnClickListener(R.id.share_icon);
        setOnClickListener(R.id.company_name);

    }

    @Override
    public void initData() {
        targetUserId = getIntent().getStringExtra("targetUserId");



        //声明WebSettings子类
        WebSettings webSettings = webMyContent.getSettings();
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webMyContent.setInitialScale(5);
//如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        webMyContent.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
//设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

//缩放操作
        webSettings.setSupportZoom(false); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(false); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);


//其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); //关闭webview中缓存\
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        getBaseContext().deleteDatabase("WebView.db");
        getBaseContext().deleteDatabase("WebViewCache.db");
        webMyContent.clearCache(true);
        webMyContent.clearFormData();
        getCacheDir().delete();
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        webMyContent.getSettings().setDomStorageEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //主要用于平板，针对特定屏幕代码调整分辨率
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int mDensity = metrics.densityDpi;
        if (mDensity == 240) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (mDensity == 160) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        } else if (mDensity == 120) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
        } else if (mDensity == DisplayMetrics.DENSITY_XHIGH) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (mDensity == DisplayMetrics.DENSITY_TV) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webMyContent.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webMyContent.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (url.contains("tel:")) {
                    String mobile = url.substring(url.lastIndexOf("/") + 1);
                    Log.e("mobile----------->",mobile);
                    Intent mIntent = new Intent(Intent.ACTION_CALL);
                    Uri data = Uri.parse(mobile);
                    mIntent.setData(data);

                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        startActivity(mIntent);
                        //这个超连接,java已经处理了，webview不要处理
                        return true;
                    }else{
                        //申请权限
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE},1);
                        return true;
                    }
                }
                else {
                    view.loadUrl(url);
                }

                return true;
            }
        });

        getUserCommunity(targetUserId);
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
            case R.id.img_close:
                finish();
                break;

            case R.id.contact_fwy:

                Intent intent=new Intent(getActivity(),Act_Fwh_contact.class);
                intent.putExtra("communityId",communityId);
                intent.putExtra("targetUserId",targetUserId);
                startActivityForResult(intent,0);

                break;

            case R.id.share_icon:

                showShareDialog();
                break;

            case  R.id.company_name:
                finish();
                break;


        }
    }


    private void getxuanchuanurl() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpUtils.get().url("http://st.3dgogo.com/index/enterprise_publicity/get_qrcode_url_api.html")
                        .addParams("community_id", communityid)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                try {
                                    shareimg = new JSONObject(response).getString("url");
                                    targetUserId=new JSONObject(response).getString("targetuserId");
                                    getshareImg(shareimg);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
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

    private void shareWeChat() {
        try {
            JShareInterface.share(Wechat.Name, generateParams(), new PlatActionListener() {
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

    private void shareWeChatMoments() {
        JShareInterface.share(WechatMoments.Name, generateParams(), null);
    }

    private void showShareDialog() {
        list.clear();
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
        shareParams.setShareType(Platform.SHARE_IMAGE);
        shareParams.setImageData(shareBit);
        //shareParams.setImageUrl(shareimg);

        return shareParams;
    }

    private void getshareImg(final String share_img) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    shareBit = Glide.with(getActivity())
                            .asBitmap()
                            .load(share_img)
                            .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                            .get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private String  getUserCommunity(String userId) {

        ReqCommunity.getMyCommunity(context, "adapter", userId, new EntityCallBack<MyCommunityResponse>() {
            @Override
            public Class<MyCommunityResponse> getEntityClass() {
                return MyCommunityResponse.class;
            }

            @Override
            public void onSuccess(MyCommunityResponse resp) {
                if (resp.is200()) {


                    communityid=resp.obj.getCommunityId();
                    getxuanchuanurl();
                    webMyContent.loadUrl(webapi+communityid);
                } else {

                }
            }

            @Override
            public void onFailure(Throwable e) {

            }

            @Override
            public void onCancelled(Throwable e) {

            }

            @Override
            public void onFinished() {

            }
        });

        return   communityid;
    }
}
