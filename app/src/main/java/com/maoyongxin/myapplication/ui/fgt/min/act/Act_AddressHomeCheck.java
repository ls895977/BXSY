package com.maoyongxin.myapplication.ui.fgt.min.act;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jky.baselibrary.util.common.TimeUtil;
import com.jky.baselibrary.widget.TitleBar;
import com.lykj.aextreme.afinal.utils.Debug;
import com.lykj.aextreme.afinal.utils.MyToast;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.bean.MessageBean;
import com.maoyongxin.myapplication.common.BaseAct;
import com.maoyongxin.myapplication.common.MyApplication;
import com.maoyongxin.myapplication.http.callback.EntityCallBack;
import com.maoyongxin.myapplication.http.entity.CommunityListInfo;
import com.maoyongxin.myapplication.http.request.ReqCommunity;
import com.maoyongxin.myapplication.http.response.BaseResp;
import com.maoyongxin.myapplication.http.response.CommunityListResponse;
import com.maoyongxin.myapplication.http.response.CommunityRequestResponse;
import com.maoyongxin.myapplication.http.response.MyCommunityResponse;
import com.maoyongxin.myapplication.ui.fgt.min.adapter.NearbyCommunityListAdapter;
import com.maoyongxin.myapplication.view.NoScrollListView;
import com.maoyongxin.myapplication.view.SelectableRoundedImageView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

/**
 * 小区名称
 */
public class Act_AddressHomeCheck extends BaseAct {
    SelectableRoundedImageView imgMyCommunityIcon;
    TextView tvCommunityCreatTime;
    TextView tvNoCommunity;
    TextView tvCommunityNote;
    TextView tvCommunityAddress;
    LinearLayout lineMyCommunity;
    TextView tvNoNearbyCommunity;
    NoScrollListView lvNearbyCommunityList;
    TextView tvMyCommunityName;
    TitleBar TitleBarLike;
    TextView tvMyCommunityId;
    WebView wvbison;
    private List<CommunityListInfo.CommnunityList> commnunityLists;
    private String myCommunityIcon;
    private String myCommunityName;
    private String myCommunityNote;
    private String myCommunityAddress;
    private String myCommunityCreatTime;
    private String myCommunityId, url;
    private boolean hasCommunity;
    private String userid,adcode;


    @Override
    public int initLayoutId() {
        return R.layout.act_addresshomecheck;
    }

    @Override
    public void initView() {
        hideHeader();

        userid=getIntent().getStringExtra("userid");
        adcode=getIntent().getStringExtra("adcode");

        imgMyCommunityIcon = getView(R.id.img_myCommunityIcon);
        tvCommunityCreatTime = getView(R.id.tv_communityCreatTime);
        tvNoCommunity = getView(R.id.tv_noCommunity);
        tvCommunityNote = getView(R.id.tv_communityNote);
        tvCommunityAddress = getView(R.id.tv_communityAddress);
        lineMyCommunity = getView(R.id.line_myCommunity);
        tvNoNearbyCommunity = getView(R.id.tv_noNearbyCommunity);
        lvNearbyCommunityList = getView(R.id.lv_nearbyCommunityList);
        tvMyCommunityName = getView(R.id.tv_myCommunityName);
        TitleBarLike = getView(R.id.TitleBar_like);
        tvMyCommunityId = getView(R.id.tv_myCommunityId);
        wvbison = getView(R.id.wv_bison);


        TitleBarLike.setOnTitleBarClickListener(new TitleBar.OnClickListener() {
            @Override
            public boolean onClick(int function) {
                if (function == TitleBar.FUNCTION_RIGHT_TEXT) {

                    ReqCommunity.getMyCreatRequest(getActivity(), getActivityTag(), MyApplication.getCurrentUserInfo().getUserId(), new EntityCallBack<CommunityRequestResponse>() {
                        @Override
                        public Class<CommunityRequestResponse> getEntityClass() {
                            return CommunityRequestResponse.class;
                        }

                        @Override
                        public void onSuccess(CommunityRequestResponse resp) {
                            if (resp.is200()) {
                                if (resp.obj.getCommunityRequestList().size() == 0) {
                                    startActivity(new Intent(getActivity(), activity_creat_community.class));
                                } else {
                                    MyToast.show(getActivity(),"对不起，你已经提交过创建请求或者你已经有自己的服务号了，无需再次提交");
                                }
                            }
                        }

                        @Override
                        public void onFailure(Throwable e) {
                            startActivity(new Intent(getActivity(), activity_creat_community.class));
                        }

                        @Override
                        public void onCancelled(Throwable e) {

                        }

                        @Override
                        public void onFinished() {

                        }
                    });

                } else if (function == TitleBar.FUNCTION_LEFT_TEXT) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(Act_AddressHomeCheck.this);

                    builder.setTitle("提示").setMessage("一个账号只能加入一个团队，可以退出后加入其它团队").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final EditText inputServer = new EditText(Act_AddressHomeCheck.this);
                            AlertDialog.Builder builder = new AlertDialog.Builder(Act_AddressHomeCheck.this);
                            builder.setMessage("请输入服务号ID").setView(inputServer).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ReqCommunity.joinCommunity(Act_AddressHomeCheck.this, getActivityTag(), MyApplication.getCurrentUserInfo().getUserId(), inputServer.getText().toString(), "", new EntityCallBack<BaseResp>() {
                                        @Override
                                        public Class<BaseResp> getEntityClass() {
                                            return BaseResp.class;
                                        }

                                        @Override
                                        public void onSuccess(BaseResp resp) {
                                            MyToast.show(Act_AddressHomeCheck.this,resp.msg);
                                            postPushMessage(inputServer.getText().toString());
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
                                }
                            }).setNegativeButton("取消", null).show();

                        }
                    }).setNegativeButton("取消", null).show();
                }
                return false;
            }
        });
    }

    @Override
    public void initData() {
        myCommunityIcon = "";
        hasCommunity = false;
        url="http://www.bisonchat.com/home/introduce/index.html";
        getMyOwnCommunity("10070");
        getNearbyCommunityList();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//白底黑字+沉浸式导航栏
        initWebView(wvbison);
    }
    private void initWebView(WebView webView) {
        //声明WebSettings子类
        WebSettings webSettings = webView.getSettings();
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.setInitialScale(5);
//如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
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
        webView.clearCache(true);
        webView.clearFormData();
        getCacheDir().delete();
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        webView.getSettings().setDomStorageEnabled(true);
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
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        wvbison.loadUrl(url);

    }
    @Override
    public void requestData() {

    }

    @Override
    public void updateUI() {

    }

    @Override
    public void onViewClick(View v) {

    }

    @Override
    protected void handlerPassMsg(int target, int target1, Object obj) {
    }
    private void getMyOwnCommunity(String userId) {
        ReqCommunity.getMyCommunity(this, getActivityTag(), userId, new EntityCallBack<MyCommunityResponse>() {
            @Override
            public Class<MyCommunityResponse> getEntityClass() {
                return MyCommunityResponse.class;
            }

            @Override
            public void onSuccess(MyCommunityResponse resp) {
                if (resp.is200()) {
                    lineMyCommunity.setVisibility(View.VISIBLE);
                    tvNoCommunity.setVisibility(View.GONE);
                    if (resp.obj.getCommunityImg() != null && !resp.obj.getCommunityImg().equals("")) {
                        Glide.with(context).load(resp.obj.getCommunityImg()).into(imgMyCommunityIcon);
                        myCommunityIcon = resp.obj.getCommunityImg();
                    } else {
                        Glide.with(context).load(R.mipmap.jingying).into(imgMyCommunityIcon);
                    }
                    tvMyCommunityName.setText(resp.obj.getCommunityName());
                    tvCommunityAddress.setText(resp.obj.getAddress());
                    tvCommunityCreatTime.setText(TimeUtil.getFormatYMDFromMillis(resp.obj.getCreatTime(), "yyyy-MM-dd"));

                    myCommunityAddress = resp.obj.getAddress();
                    myCommunityName = resp.obj.getCommunityName();

                    myCommunityCreatTime = TimeUtil.getFormatYMDFromMillis(resp.obj.getCreatTime(), "yyyy-MM-dd");
                    myCommunityId = resp.obj.getCommunityId() + "";
                    tvMyCommunityId.setText("服务号ID" + myCommunityId);
                    hasCommunity = true;

                } else {
                    lineMyCommunity.setVisibility(View.GONE);
                    tvNoCommunity.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Throwable e) {
                lineMyCommunity.setVisibility(View.GONE);
                tvNoCommunity.setVisibility(View.VISIBLE);

            }

            @Override
            public void onCancelled(Throwable e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void getNearbyCommunityList() {
        ReqCommunity.getNearbyCommunityList(this, getActivityTag(), adcode, new EntityCallBack<CommunityListResponse>() {
            @Override
            public Class<CommunityListResponse> getEntityClass() {
                return CommunityListResponse.class;
            }

            @Override
            public void onSuccess(CommunityListResponse resp) {
                if (resp.is200()) {
                    if (resp.obj.getCommnunityList().size() == 0) {
                        tvNoNearbyCommunity.setVisibility(View.VISIBLE);
                        lvNearbyCommunityList.setVisibility(View.GONE);
                    } else {
                        tvNoNearbyCommunity.setVisibility(View.GONE);
                        lvNearbyCommunityList.setVisibility(View.VISIBLE);
                        commnunityLists = resp.obj.getCommnunityList();
                        NearbyCommunityListAdapter adapter = new NearbyCommunityListAdapter(commnunityLists,context);
                        lvNearbyCommunityList.setAdapter(adapter);
                    }
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
    }
    private void postPushMessage(final String dynamcid) {
        MessageBean bean = new MessageBean();
        bean.setTitle("服务号申请");
        Gson gson = new Gson();
        String android_notification = gson.toJson(bean);
        OkHttpUtils.get().url("http://bisonchat.com/index/jpush/communityAdminUserPushApi")
                .addParams("communityId", dynamcid)
                .addParams("content", "用户：" + MyApplication.getCurrentUserInfo().getUserName() + "申请加入您的服务号团队")
                .addParams("android_notification", android_notification)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Debug.e("---------------onError==" + e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                Debug.e("---------------communityId==" + dynamcid);
                Debug.e("---------------content==" +  MyApplication.getCurrentUserInfo().getUserName() + "申请加入您的服务号团队");

            }
        });
    }
}
