package com.maoyongxin.myapplication.ui.fgt.community.act;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.bean.JiGuangSharePlatformModel;
import com.maoyongxin.myapplication.common.AppConfig;
import com.maoyongxin.myapplication.common.BaseAct;
import com.maoyongxin.myapplication.http.entity.PictureEntity;
import com.maoyongxin.myapplication.http.entity.huifuInfo;
import com.maoyongxin.myapplication.ui.fgt.community.adapter.HuifuRecycle;
import com.maoyongxin.myapplication.ui.fgt.community.bean.CountInfo;
import com.maoyongxin.myapplication.ui.fgt.community.bean.DynamicComment;
import com.maoyongxin.myapplication.ui.fgt.message.act.Act_StrangerDetail;
import com.maoyongxin.myapplication.ui.fgt.message.act.strangerdetail.fgt.adapter.DoubleDynamicPicAdapter;
import com.maoyongxin.myapplication.ui.fgt.message.act.strangerdetail.fgt.adapter.GridDynamicPicAdapter;
import com.maoyongxin.myapplication.ui.fgt.message.act.strangerdetail.fgt.adapter.SingleDynamicPicAdapter;
import com.maoyongxin.myapplication.ui.fgt.message.act.strangerdetail.fgt.framdialog.ShareDialogFragment;
import com.maoyongxin.myapplication.view.AntGrideVIew;
import com.maoyongxin.myapplication.view.SelectableRoundedImageView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.jiguang.share.android.api.JShareInterface;
import io.github.rockerhieu.emojicon.EmojiconTextView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 评论页
 */
public class Act_Comment_Detail extends BaseAct {
    private String huifuUser;
    private String huifuTime;
    private String huifuCoutent;
    private String huifuId;
    private String huifuUserHead;
    private int huifuZan;
    private int huifuCai;
    private String huifuUserId;
    private int UPDATETWO = 1;
    private List<huifuInfo> huifuInfoList = new ArrayList<>();
    private String usrName, companyName, time, praiseNum, treadNum, commentNum, is_praise_tread, headImg, Contentdetail, CommunityId, videoString, parentUserId, huifuuserid, huifuusername;
    private int nzan, nplun;
    private GridDynamicPicAdapter adapter;
    private ArrayList<String> picsurl = new ArrayList<>();
    private List<PictureEntity> imgPics = new ArrayList<>();
    private SingleDynamicPicAdapter sigleAdapter;
    private DoubleDynamicPicAdapter doubleDynamicPicAdapter;
    private List<DynamicComment> dynamicCommentList;
    private HuifuRecycle huifuAdapter;
    private ArrayList<JiGuangSharePlatformModel> list = new ArrayList<>();
    String shareimg;
    Bitmap shareBit;
    private String DynamicInfo = "http://st.3dgogo.com/index/user_dynamic/get_user_dynamic_post_api.html";
    private String APi_zan = "http://st.3dgogo.com/index/user_dynamic/set_user_praise_num_api.html";
    private Handler handler;
    private int UPDATEZAN = 0;
    private String dynamicId, communityName;
    private int updateone = 5;
    List<String> imglist = new ArrayList<String>();
    private int type = 1;
    RoundedImageView dunamicheader;
    TextView dynamicName;
    TextView dynamicCompany;
    TextView tvCreatTime;
    EmojiconTextView dynamicContent;
    AntGrideVIew dynamicPics;
    ImageView imgDelete;
    TextView numplun;
    TextView numdzan;
    RecyclerView dynamicList;
    CardView btDynamic;
    CardView cvToDetail;
    LinearLayout llInfo;
    AntGrideVIew twoDynamicPics;
    AntGrideVIew singleImg;
    CollapsingToolbarLayout collapsingtoolbar;
    AppBarLayout appBar;
    ImageView imgZan;
    LinearLayout llZan;
    LinearLayout llShare;
    Toolbar huatiToolbar;
    ImageView imgShare;
    TextView numshare;
    LinearLayout llShareview;
    CoordinatorLayout allView;
    LinearLayout llAppinfo;
    TextView showDetail;
    ImageView imgPinlun;

    @Override
    protected void handlerPassMsg(int target, int target1, Object obj) {

    }

    @Override
    public int initLayoutId() {
        return R.layout.act_comment_detail;
    }

    @Override
    public void initView() {
        hideHeader();
        dunamicheader = getView(R.id.dunamicheader);
        dynamicName = getView(R.id.dynamic_name);
        dynamicCompany = getView(R.id.dynamic_company);
        tvCreatTime = getView(R.id.tv_creatTime);
        dynamicContent = getView(R.id.dynamic_content);
        dynamicPics = getView(R.id.dynamic_pics);
        imgDelete = getView(R.id.img_delete);
        numplun = getView(R.id.numplun);
        numdzan = getView(R.id.numdzan);
        dynamicList = getView(R.id.dynamicList);
        btDynamic = getView(R.id.bt_Dynamic);
        cvToDetail = getView(R.id.cv_toDetail);
        llInfo = getView(R.id.ll_info);
        twoDynamicPics = getView(R.id.two_DynamicPics);
        singleImg = getView(R.id.single_img);
        collapsingtoolbar = getView(R.id.collapsingtoolbar);
        appBar = getView(R.id.appBar);
        imgZan = getView(R.id.img_zan);
        llZan = getView(R.id.ll_zan);
        llShare = getView(R.id.ll_share);
        huatiToolbar = getView(R.id.huati_toolbar);
        imgShare = getView(R.id.img_share);
        numshare = getView(R.id.numshare);
        llShareview = getView(R.id.ll_shareview);
        allView = getView(R.id.all_view);
        llAppinfo = getView(R.id.ll_appinfo);
        showDetail = getView(R.id.show_detail);
        imgPinlun = getView(R.id.img_pinlun);
        gethuatiList();
        getDynamicCount();
    }

    @Override
    public void initData() {
        dynamicId = getIntent().getStringExtra("dynamicId");
        communityName = getIntent().getStringExtra("communityName");
        CommunityId = getIntent().getStringExtra("community_id");
        parentUserId = getIntent().getStringExtra("parentUserId");
        getDynamicInfo();
        btDynamic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Act_Comment_Detail.this, Act_Dynamic_huifu.class);
                intent.putExtra("DynamicId", getIntent().getStringExtra("dynamicId"));
                intent.putExtra("parentId", "DynamicId");
                intent.putExtra("parentUserId", parentUserId);
                intent.putExtra("CommunityId", CommunityId);
                startActivity(intent);
                //动态回复
            }
        });

        llZan.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                imgZan.setImageResource(R.mipmap.gooded_big);
                dianzna("dynamic_id", dynamicId + "");
            }
        });

        llShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showShareDialog();
            }
        });

        showDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Act_StrangerDetail.class);
                intent.putExtra("personId", parentUserId);
                startActivity(intent);
            }
        });

    }

    private void showShareDialog() {
        list.clear();
        List<String> successPlatform = JShareInterface.getPlatformList();

        for (String s : successPlatform) {

            JShareInterface.isClientValid(s);
            if (s.equals("WechatFavorite")) {
                continue;
            }
            list.add(new JiGuangSharePlatformModel(s));

        }
        ShareDialogFragment dialogFragment = ShareDialogFragment.newInstance(list);
        dialogFragment.show(getSupportFragmentManager(), "tag");
    }

    @Override
    public void requestData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        gethuatiList();
        getDynamicCount();
    }

    @Override
    public void updateUI() {

    }

    @Override
    public void onViewClick(View v) {

    }

    private void gethuatiList() {//post服务器数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                try {
                    RequestBody requestBody = new FormBody.Builder()
                            .add("dynamicId", dynamicId)
                            .build();
                    Request request = new Request.Builder()
                            .url("http://st.3dgogo.com/index/user_dynamic/get_user_dynamic_post_api.html")
                            .post(requestBody)
                            .build();


                    try {
                        Call call = okHttpClient.newCall(request);
                        //判断请求是否成功
                        try {
                            Response response = call.execute();

                            try {

                                JSONObject jsonObject = new JSONObject(response.body().string());

                                if (jsonObject.getInt("code") == 200) {
                                    JSONArray data = jsonObject.getJSONArray("info");
                                    huifuInfoList.clear();
                                    for (int i = 0; i < data.length(); i++) {
                                        huifuInfo huifuinfo = new huifuInfo();
                                        JSONObject hfJsong = data.getJSONObject(i);

                                        huifuUser = parse_Value(hfJsong, "userName");
                                        huifuCoutent = parse_Value(hfJsong, "postContent");
                                        huifuTime = parse_Value(hfJsong, "times");
                                        huifuId = parse_Value(hfJsong, "id");
                                        huifuUserHead = parse_Value(hfJsong, "userImg");
                                        huifuUserId = parse_Value(hfJsong, "userId");

                                        huifuCai = hfJsong.getInt("treadNum");
                                        huifuZan = hfJsong.getInt("praiseNum");

                                        huifuuserid = parse_Value(hfJsong, "parentUserId");
                                        huifuusername = parse_Value(hfJsong, "parentUserName");

                                        huifuinfo.sethuifuInfo("","dynamic_post_id", huifuUserHead, huifuUser, huifuId, huifuTime, huifuCoutent, huifuCai, huifuZan, huifuUserId, huifuusername, huifuuserid);
                                        huifuInfoList.add(huifuinfo);
                                        Log.d("---", huifuInfoList.size() + "");

                                    }

                                    Message msg = new Message();
                                    msg.what = UPDATETWO;
                                    handler.sendMessage(msg);

                                } else if (jsonObject.getInt("code") == 500) {

                                    //  isUseable=false;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();


                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    } catch (Exception e) {
                        e.printStackTrace();

                    }


                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }).start();

    }

    private String parse_Value(JSONObject data, String value) {
        String com_value = "";
        if (data.has(value)) {
            try {
                com_value = data.getString(value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return com_value;
    }

    private void getDynamicCount() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .addInterceptor(loggingInterceptor).build();

                RequestBody requestBody = new FormBody.Builder()
                        .add("dynamicId", dynamicId)
                        .build();
                final Request request = new Request.Builder()
                        .url("http://st.3dgogo.com/index/user_dynamic/get_user_dynamic_post_num.html")
                        .post(requestBody)
                        .build();

                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String content = response.body().string();
                        //  Log.d("------", content);
                        final CountInfo countInfo = new Gson().fromJson(content, CountInfo.class);
                        if (countInfo.getCode() == 200) {
                            Log.d("---", "请求点赞数量失败");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    nzan = countInfo.getInfo().getPraiseNum();
                                    nplun = countInfo.getInfo().getCommentNum();
                                    numdzan.setText(nzan + "");
                                    numplun.setText(nplun + "");
                                }
                            });
                        }
                    }
                });
            }
        }).start();
    }

    private void getDynamicInfo() {
        OkHttpUtils.get().url("http://st.3dgogo.com/index/user_dynamic/getUserDynamicDetailsApi.html")
                .addParams("dynamicId", dynamicId)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject date = new JSONObject(response).getJSONObject("info");


                    usrName = parse_Value(date, "userName");
                    companyName = parse_Value(date, "companyName");
                    time = parse_Value(date, "createTime");
                    praiseNum = parse_Value(date, "praiseNum");
                    treadNum = parse_Value(date, "companyName");
                    commentNum = parse_Value(date, "commentNum");
                    is_praise_tread = parse_Value(date, "is_praise_tread");
                    headImg = parse_Value(date, "headImg");
                    Contentdetail = parse_Value(date, "dynamicContent");
                    videoString = parse_Value(date, "video");

                    if (headImg.startsWith("http")) {
                        Glide.with(getActivity()).load(headImg).into(dunamicheader);
                    } else {
                        //  headImg= AppConfig.sRootUrl+"/logincontroller/getHeadImg/"+headImg;
                        Glide.with(getActivity()).load(AppConfig.sRootUrl + "/logincontroller/getHeadImg/" + headImg).into(dunamicheader);
                    }
                    dynamicName.setText(usrName);
                    tvCreatTime.setText(time);


                    dynamicContent.setText(new String(Base64.decode(Contentdetail.getBytes(), Base64.DEFAULT)));
                    dynamicCompany.setText(communityName);


                    String img = date.getString("img");

                    String[] a = img.split(",");

                    for (int i = 0; i < a.length; i++) {
                        String b = a[i].trim();
                        imglist.add(b);
                    }


                    if (imglist != null) {

                        for (int j = 0; j < imglist.size(); j++) {
                            PictureEntity pictureEntity = new PictureEntity(1);
                            pictureEntity.setImgUrl(imglist.get(j));
                            picsurl.add(imglist.get(j));
                            if (videoString != null && !videoString.equals("")) {
                                pictureEntity.setType(2);
                                pictureEntity.setVideoUrl(videoString);
                                pictureEntity.setTitle(Contentdetail);
                            }
                            imgPics.add(pictureEntity);
                        }

                        Message msg = new Message();
                        msg.what = updateone;
                        handler.sendMessage(msg);

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void dianzna(final String zanType, final String dianzanID) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                        .build();
                try {

                    RequestBody requestBody = new FormBody.Builder()
                            .add("type", "1")
                            .add("user_id", "")
                            .add("zdm", zanType)
                            .add(zanType, dianzanID)
                            .build();

                    Request request = new Request.Builder()
                            .url(APi_zan)
                            .post(requestBody)
                            .build();
                    try {
                        Call call = okHttpClient.newCall(request);
                        //判断请求是否成功
                        Looper.prepare();
                        try {
                            Response response = call.execute();

                            try {

                                JSONObject jsonObject = new JSONObject(response.body().string());

                                if (jsonObject.getInt("code") == 200) {
                                    getDynamicCount();

                                } else if (jsonObject.getInt("code") == 500) {


                                }
                            } catch (Exception e) {
                                e.printStackTrace();


                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Looper.loop();

                    } catch (Exception e) {
                        e.printStackTrace();

                    }


                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }).start();

    }
}
