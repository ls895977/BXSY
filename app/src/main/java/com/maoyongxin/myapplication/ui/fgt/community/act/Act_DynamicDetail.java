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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.lykj.aextreme.afinal.utils.Debug;
import com.makeramen.roundedimageview.RoundedImageView;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.bean.JiGuangSharePlatformModel;
import com.maoyongxin.myapplication.common.AppConfig;
import com.maoyongxin.myapplication.common.BaseAct;
import com.maoyongxin.myapplication.common.ComantUtils;
import com.maoyongxin.myapplication.common.MyApplication;
import com.maoyongxin.myapplication.http.callback.EntityCallBack;
import com.maoyongxin.myapplication.http.entity.PictureEntity;
import com.maoyongxin.myapplication.http.entity.huifuInfo;
import com.maoyongxin.myapplication.http.request.ReqCommunity;
import com.maoyongxin.myapplication.http.response.CountInfo;
import com.maoyongxin.myapplication.http.response.DynamicComment;
import com.maoyongxin.myapplication.http.response.MyCommunityResponse;
import com.maoyongxin.myapplication.ui.fgt.community.adapter.HuifuRecycle;
import com.maoyongxin.myapplication.ui.fgt.community.bean.UserDynamicPostBean;
import com.maoyongxin.myapplication.ui.fgt.community.bean.UserFriendsFollowApiBean;
import com.maoyongxin.myapplication.ui.fgt.community.bean.getUserDynamicDetailsApiBean;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.jiguang.share.android.api.JShareInterface;
import cn.jiguang.share.android.api.PlatActionListener;
import cn.jiguang.share.android.api.Platform;
import cn.jiguang.share.android.api.ShareParams;
import cn.jiguang.share.wechat.Wechat;
import cn.jiguang.share.wechat.WechatMoments;
import io.github.rockerhieu.emojicon.EmojiconTextView;
import okhttp3.Call;

import static android.view.View.GONE;

/**
 * 动态详情页
 */
public class Act_DynamicDetail extends BaseAct implements HuifuRecycle.onBckHuiFU , ShareDialogFragment.Listener{
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
    private Bitmap shareBit;
    private ArrayList<JiGuangSharePlatformModel> list = new ArrayList<>();
    private List<huifuInfo> huifuInfoList = new ArrayList<>();
    private String usrName, companyName, time, praiseNum, treadNum, commentNum, is_praise_tread, headImg, Contentdetail, CommunityId, videoString, parentUserId, huifuuserid, huifuusername;
    private int nzan, nplun;
    private GridDynamicPicAdapter adapter;
    private ArrayList<String> picsurl = new ArrayList<>();
    private List<PictureEntity> imgPics = new ArrayList<>();
    private SingleDynamicPicAdapter sigleAdapter;
    private DoubleDynamicPicAdapter doubleDynamicPicAdapter;
    private HuifuRecycle huifuAdapter;
    private String dynamicId, communityName = "";
    private TextView imgZan;
    private LinearLayout qiye;
    private RelativeLayout noViewData;
    private Boolean nodata=true;
    @Override
    public int initLayoutId() {
        return R.layout.act_dynamicdetail;
    }

    @Override
    public void initView() {
        hideHeader();
        noViewData=getView(R.id.view_not);
        dunamicheader = getViewAndClick(R.id.dunamicheader);
        dynamicName = getView(R.id.dynamic_name);
        qiye = getView(R.id.dynamicdetail_qiye);
        dynamicCompany = getView(R.id.dynamic_company);
        tvCreatTime = getView(R.id.tv_creatTime);
        dynamicContent = getView(R.id.dynamic_content);
        dynamicPics = getView(R.id.dynamic_pics);
        imgDelete = getView(R.id.img_delete);
        numplun = getView(R.id.numplun);
        numdzan = getView(R.id.numdzan);
        dynamicList = getView(R.id.dynamicList);
        btDynamic = getViewAndClick(R.id.bt_Dynamic);
        cvToDetail = getViewAndClick(R.id.cv_toDetail);
        llInfo = getView(R.id.ll_info);
        twoDynamicPics = getView(R.id.two_DynamicPics);
        singleImg = getView(R.id.single_img);
        collapsingtoolbar = getView(R.id.collapsingtoolbar);
        appBar = getView(R.id.appBar);
        imgZan = getView(R.id.zanImg);
        llZan = getView(R.id.ll_zan);
        llShare = getView(R.id.ll_share);
        huatiToolbar = getView(R.id.huati_toolbar);
        imgShare = getViewAndClick(R.id.img_share);
        numshare = getView(R.id.numshare);
        llShareview = getView(R.id.ll_shareview);
        allView = getView(R.id.all_view);
        llAppinfo = getView(R.id.ll_appinfo);
        showDetail = getViewAndClick(R.id.show_detail);
        imgPinlun = getViewAndClick(R.id.img_pinlun);
        dynamicList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        dynamicList.setItemAnimator(null);
        dynamicList.setHasFixedSize(true);
        dynamicList.setNestedScrollingEnabled(false);
        huifuAdapter = new HuifuRecycle(huifuInfoList, context, Act_DynamicDetail.this);
        dynamicList.setAdapter(huifuAdapter);
        setOnClickListener(R.id.ll_zan);
    }

    private boolean isPraise;
    private String userid, Is_anymit;

    @Override
    public void initData() {

        dynamicId = getIntent().getStringExtra("dynamicId");
        communityName = getIntent().getStringExtra("communityName");
        CommunityId = getIntent().getStringExtra("community_id");
        parentUserId = getIntent().getStringExtra("parentUserId");
        isPraise = getIntent().getBooleanExtra("isPraise", false);
        userid = getIntent().getStringExtra("userid");
        Is_anymit = getIntent().getStringExtra("Is_anymit");
        imgZan.setSelected(isPraise);
        getDynamicInfo();
        getDynamicCount();
        gethuatiList();

        getUserCommunity(userid);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDynamicCount();
        gethuatiList();
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
            case R.id.bt_Dynamic://点击回复

                Intent intent0 = new Intent(this, Act_Dynamic_huifu.class);
                intent0.putExtra("DynamicId", getIntent().getStringExtra("dynamicId"));
                intent0.putExtra("parentId", parentUserId);
                intent0.putExtra("userid", userid);
                intent0.putExtra("parentUserId", parentUserId);
                intent0.putExtra("CommunityId", CommunityId);
                intent0.putExtra("parentName", "");
                startActivity(intent0);
                break;
            case R.id.img_pinlun:
                Intent intent = new Intent(this, Act_Dynamic_huifu.class);
                intent.putExtra("DynamicId", getIntent().getStringExtra("dynamicId"));
                intent.putExtra("parentId", parentUserId);
                intent.putExtra("userid", userid);
                intent.putExtra("parentUserId", parentUserId);
                intent.putExtra("CommunityId", CommunityId);
                intent.putExtra("parentName", "");
                startActivity(intent);
                //动态回复
                break;

            case R.id.show_detail:
                tostranger();
                break;
            case R.id.dunamicheader:
                tostranger();
                break;
            case R.id.cv_toDetail:
                tostranger();
                break;

            case R.id.img_share:
                    showShareDialog();
                break;
        }
    }

    @Override
    protected void handlerPassMsg(int target, int target1, Object obj) {

    }

    /**
     * 获取头部信息
     */
    getUserDynamicDetailsApiBean bean;

    private void getDynamicInfo() {
        OkHttpUtils.get().url("http://st.3dgogo.com/index/user_dynamic/getUserDynamicDetailsApi.html")
                .addParams("dynamicId", dynamicId)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(String response, int id) {
                Gson gson = new Gson();
                bean = gson.fromJson(response, getUserDynamicDetailsApiBean.class);
                RequestOptions options = new RequestOptions();
                options.error(R.mipmap.huangye);

                    try{

                        if (Is_anymit == null||Is_anymit.equals("1")) {
                            dynamicName.setText(bean.getInfo().getUserName());
                            Glide.with(getActivity()).load(bean.getInfo().getHeadImg()).apply(options).into(dunamicheader);

                        } else {
                            Glide.with(getActivity()).load(R.mipmap.nimingyonghu).apply(options).into(dunamicheader);//匿名用户头像地址
                            dynamicName.setText("匿名用户");
                        }

                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();

                        Glide.with(getActivity()).load(R.mipmap.user_head_img).apply(options).into(dunamicheader);//匿名用户头像地址
                        dynamicName.setText("彼信用户");
                    }





                tvCreatTime.setText(bean.getInfo().getCreateTime());
                dynamicContent.setText(new String(Base64.decode(bean.getInfo().getDynamicContent().getBytes(), Base64.DEFAULT)));
                if (bean.getInfo().getImg() != null) {
                    for (int j = 0; j < bean.getInfo().getImg().size(); j++) {
                        PictureEntity pictureEntity = new PictureEntity(1);
                        pictureEntity.setImgUrl(bean.getInfo().getImg().get(j));
                        picsurl.add(bean.getInfo().getImg().get(j));
                        if (bean.getInfo().getVideo() != null && !bean.getInfo().getVideo().equals("")) {
                            pictureEntity.setType(2);
                            pictureEntity.setVideoUrl(bean.getInfo().getVideo());
                            pictureEntity.setTitle(bean.getInfo().getDynamicContent());
                        }
                        imgPics.add(pictureEntity);
                    }
                }
                switch (bean.getInfo().getImg().size()) {
                    case 1:
                        sigleAdapter = new SingleDynamicPicAdapter(getActivity(), imgPics, picsurl);
                        singleImg.setAdapter(sigleAdapter);
                        dynamicPics.setVisibility(GONE);
                        twoDynamicPics.setVisibility(GONE);
                        break;
                    case 2:
                        doubleDynamicPicAdapter = new DoubleDynamicPicAdapter(getActivity(), imgPics, picsurl);
                        twoDynamicPics.setAdapter(doubleDynamicPicAdapter);
                        dynamicPics.setVisibility(GONE);
                        singleImg.setVisibility(GONE);
                        break;
                    default:
                        adapter = new GridDynamicPicAdapter(getActivity(), imgPics, picsurl);
                        dynamicPics.setAdapter(adapter);
                        singleImg.setVisibility(GONE);
                        twoDynamicPics.setVisibility(GONE);
                        break;
                }
            }
        });
    }

    /**
     * 获取点赞的数量
     */
    private void getDynamicCount() {
        OkHttpUtils.post().url("http://st.3dgogo.com/index/user_dynamic/get_user_dynamic_post_num.html")
                .addParams("dynamicId", dynamicId)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(String response, int id) {
                final CountInfo countInfo = new Gson().fromJson(response, CountInfo.class);
                if (countInfo.getCode() == 200) {
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

    /**
     * 获取回复列表
     */
    private void gethuatiList() {//post服务器数据
        OkHttpUtils.post().url("http://st.3dgogo.com/index/user_dynamic/get_user_dynamic_post_api.html")
                .addParams("dynamicId", dynamicId)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(String response, int id) {
                huifuInfoList.clear();
                Gson gson = new Gson();
                UserDynamicPostBean bean = gson.fromJson(response, UserDynamicPostBean.class);
                if (bean.getCode() == 200) {
                    if( bean.getInfo().size()>0)
                    {

                        noViewData.setVisibility(GONE);
                        dynamicList.setVisibility(View.VISIBLE);
                        for (int i = 0; i < bean.getInfo().size(); i++) {
                            huifuInfo huifuinfo = new huifuInfo();
                            huifuinfo.sethuifuInfo(
                                    bean.getInfo().get(i).getParentId(),
                                    "dynamic_post_id",
                                    bean.getInfo().get(i).getUserImg(),
                                    bean.getInfo().get(i).getUserName(),
                                    bean.getInfo().get(i).getId(),
                                    bean.getInfo().get(i).getCreateTime(),
                                    bean.getInfo().get(i).getPostContent(),
                                    bean.getInfo().get(i).getTreadNum(),
                                    bean.getInfo().get(i).getPraiseNum(),
                                    bean.getInfo().get(i).getUserId(),
                                    bean.getInfo().get(i).getParentUserName(),
                                    bean.getInfo().get(i).getParentUserId());

                            huifuInfoList.add(huifuinfo);

                            nodata=false;
                        }





                        if (huifuAdapter == null) {
                            huifuAdapter = new HuifuRecycle(huifuInfoList, context, Act_DynamicDetail.this);
                            dynamicList.setAdapter(huifuAdapter);
                        } else {
                            huifuAdapter.notifyDataSetChanged();
                        }
                    }
                    else if(nodata)
                    {
                        noViewData.setVisibility(View.VISIBLE);
                        dynamicList.setVisibility(GONE);
                    }


                }
            }
        });
    }

    @Override
    public void onHuiFU(int position) {
        Intent intent = new Intent(context, Act_Dynamic_huifu.class);
        Debug.e("--------------userid==="+userid);
        intent.putExtra("userid", userid);
        intent.putExtra("DynamicId", dynamicId);
        intent.putExtra("parentId", huifuInfoList.get(position).getHuatiId());
        intent.putExtra("parentUserId", huifuInfoList.get(position).getHuifuUserId());
        intent.putExtra("CommunityId", CommunityId);

        intent.putExtra("parentName", "@" + huifuInfoList.get(position).getHuifu_user());
        context.startActivity(intent);

    }

    private void getUserCommunity(String userId) {
        ReqCommunity.getMyCommunity(context, "adapter", userId, new EntityCallBack<MyCommunityResponse>() {
            @Override
            public Class<MyCommunityResponse> getEntityClass() {
                return MyCommunityResponse.class;
            }

            @Override
            public void onSuccess(MyCommunityResponse resp) {
                if (resp.is200()) {
                    dynamicCompany.setText(resp.obj.getCommunityName());
                    qiye.setVisibility(View.VISIBLE);
                } else {
                    qiye.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Throwable e) {
                Debug.e("---------onFailure==" + e.getMessage());
                qiye.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(Throwable e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void  tostranger(){
        Intent intent2=new Intent(this,Act_StrangerDetail.class);
        intent2.putExtra("personId", userid+ "");
        startActivity(intent2);
    }


    private ShareParams generateParams() {
        ShareParams shareParams = new ShareParams();
        shareParams.setShareType(Platform.SHARE_WEBPAGE);
        shareParams.setTitle( (new String(Base64.decode(bean.getInfo().getDynamicContent().getBytes(), Base64.DEFAULT))));
        shareParams.setText("彼信商业社区：商业头条");

        shareParams.setImageData(shareBit);
        shareParams.setUrl("http://bisonchat.com/home/user_dynamic/appGambitDetails.html?dynamicId="+dynamicId);

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
                            .load(bean.getInfo().getHeadImg())
                            .into(300, 300)
                            .get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
