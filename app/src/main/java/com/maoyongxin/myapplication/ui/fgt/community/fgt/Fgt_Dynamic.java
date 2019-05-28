package com.maoyongxin.myapplication.ui.fgt.community.fgt;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.SharedElementCallback;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lykj.aextreme.afinal.utils.Debug;
import com.lykj.aextreme.afinal.utils.MyToast;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.bean.EventMessage;
import com.maoyongxin.myapplication.bean.MessageBean;
import com.maoyongxin.myapplication.common.BaseFgt;
import com.maoyongxin.myapplication.common.ComantUtils;
import com.maoyongxin.myapplication.common.MyApplication;
import com.maoyongxin.myapplication.dialog.Dlg_PhotoAlbum;
import com.maoyongxin.myapplication.http.callback.EntityCallBack;
import com.maoyongxin.myapplication.http.request.ReqMyDynamicList;
import com.maoyongxin.myapplication.http.response.BaseResp;
import com.maoyongxin.myapplication.permission.RxPermissions;
import com.maoyongxin.myapplication.tool.PollingUtils;
import com.maoyongxin.myapplication.tool.UtilsTool;
import com.maoyongxin.myapplication.tool.utils.BlurUtil;
import com.maoyongxin.myapplication.ui.Act_businessActive;
import com.maoyongxin.myapplication.ui.Act_new_comment;
import com.maoyongxin.myapplication.ui.fgt.community.act.Act_DynamicDetail;
import com.maoyongxin.myapplication.ui.fgt.community.act.Act_GroupChatDetailNew;
import com.maoyongxin.myapplication.ui.fgt.community.act.Act_PublishPictures;
import com.maoyongxin.myapplication.ui.fgt.community.act.Act_ShowImageLIst;
import com.maoyongxin.myapplication.ui.fgt.community.act.Act_UploadVideo;
import com.maoyongxin.myapplication.ui.fgt.community.act.Act_Video;
import com.maoyongxin.myapplication.ui.fgt.community.act.Act_VideoRecording;
import com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.fgt.act.Act_TopicDetails;
import com.maoyongxin.myapplication.ui.fgt.community.adapter.DynamicAdapter;
import com.maoyongxin.myapplication.ui.fgt.community.bean.DynamicBase;
import com.maoyongxin.myapplication.ui.fgt.community.bean.DynamicBean;
import com.maoyongxin.myapplication.ui.fgt.community.bean.DynamicBean1;
import com.maoyongxin.myapplication.ui.fgt.community.bean.DynamicHaderBean;
import com.maoyongxin.myapplication.ui.fgt.community.bean.UserFriendsFollowApiBean;
import com.maoyongxin.myapplication.ui.fgt.community.view.WarpLinearLayout;
import com.maoyongxin.myapplication.ui.fgt.message.act.Act_StrangerDetail;
import com.maoyongxin.myapplication.ui.fgt.min.act.Act_AddressHomeCheck;
import com.maoyongxin.myapplication.ui.fgt.min.act.Act_Recharge;
import com.maoyongxin.myapplication.ui.fgt.min.act.Act_UserEditNew;
import com.maoyongxin.myapplication.ui.service.PollingService;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.functions.Consumer;
import okhttp3.Call;

import static android.app.Activity.RESULT_OK;

/**
 * 动态
 */
public class Fgt_Dynamic extends BaseFgt implements BaseQuickAdapter.OnItemChildClickListener, Dlg_PhotoAlbum.OnClick, BaseQuickAdapter.OnItemClickListener {
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView myRecyclerView;
    private Gson gson;
    private Dlg_PhotoAlbum photoAlbum;
    private RxPermissions rxPermissions;
    private EditText inpu;
    private TextView cd_pop;
    private String msmcount = "0";
    private LinearLayout ll_pop;
    private com.maoyongxin.myapplication.view.SelectableRoundedImageView userheadview;
    private BlurUtil blurUtil;
    private  ImageView iv_popup_window_back;
    private RelativeLayout rl_popup_window;
    private TextView busineseeactiview,tptopic;
    @Override
    public int initLayoutId() {
        return R.layout.fgt_dynamic;
    }

    @Override
    public void setEnterSharedElementCallback(SharedElementCallback callback) {
        super.setEnterSharedElementCallback(callback);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }


    @Override
    public void initView() {
        hideHeader();
        blurUtil = new BlurUtil(this);
        rxPermissions = new RxPermissions(getActivity());
        photoAlbum = new Dlg_PhotoAlbum(getContext(), Fgt_Dynamic.this);
        mRefreshLayout = getView(R.id.refreshLayout);
        myRecyclerView = getView(R.id.recyclerView);
        ll_pop = getViewAndClick(R.id.ll_pop);
        userheadview = getView(R.id.roudimgHead);
        iv_popup_window_back=getViewAndClick(R.id.iv_popup_window_back);
        cd_pop = getView(R.id.cd_pop);
        rl_popup_window=getViewAndClick(R.id.rl_popup_window);

        busineseeactiview=getViewAndClick(R.id.busineseeactiview);
        tptopic=getViewAndClick(R.id.tptopic);


        inpu = getViewAndClick(R.id.search);
        inpu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String neirong = inpu.getText().toString();
                if (neirong.length() == 0) {
                    postBackDtata();
                    return;
                }
                postBacksearchDtata(neirong);
            }

            @Override
            public void afterTextChanged(Editable s) {
                String neirong = inpu.getText().toString();
                if (neirong.length() == 0) {
                    postBackDtata();
                    return;
                }
                postBacksearchDtata(neirong);

            }
        });
        inpu.setCursorVisible(false);
        setOnClickListener(R.id.img_dongtai_floating);
        gson = new Gson();
    }

    ZLoadingDialog dialog;

    @Override
    public void initData() {


        new Thread(new Runnable() {
            @Override
            public void run() {
                PollingUtils.startPollingService(getContext(), 1, PollingService.class, PollingService.ACTION);

                Log.d("service", "后台service启动");


            }
        }).start();


        dialog = new ZLoadingDialog(context);
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
                page = 0;
                datas.clear();
                postCommunityAndGambitHotListApi();
                postBackDtata();
                refreshLayout.finishRefresh();
            }
        });
        // 默认提供5种方法（渐显、缩放、从下到上，从左到右、从右到左）
        adapter = new DynamicAdapter(datas, context);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        adapter.setOnItemClickListener(this);
        adapter.setOnItemChildClickListener(this);
        setView();
        myRecyclerView.setAdapter(adapter);
        postBackDtata();
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
            case R.id.img_dongtai_floating://发布动态
                photoAlbum.show();


          //     blurUtil.fgt_clickPopupWindow(rl_popup_window, iv_popup_window_back);

                break;
            case R.id.search:
                inpu.setCursorVisible(true);
                break;

            case R.id.ll_pop:
                 Intent intent = new Intent(getActivity(), Act_new_comment.class);

                   startActivity(intent);
                  ll_pop.setVisibility(View.GONE);
                break;

            case R.id.busineseeactiview:
                startActivity(new Intent(getActivity(), Act_businessActive.class));
                break;
        }
    }

    @Override
    public void sendMsg(int flag, Object obj) {

    }

    /**
     * 获取动态数据
     */
    private DynamicAdapter adapter;
    private int page = 1;
    List<DynamicBase> datas = new ArrayList<>();

    public void postBackDtata() {
        OkHttpUtils.get().url(ComantUtils.MyUrl + ComantUtils.UserDynamicList)
                .addParams("page", page + "")
                .addParams("myuid", MyApplication.getCurrentUserInfo().getUserId())
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(String response, int id) {
                Gson gson = new Gson();
                DynamicBean bean = gson.fromJson(response, DynamicBean.class);
                if (bean.getCode() != 200) {
                    return;
                }
                for (int i = 0; i < bean.getInfo().getData().size(); i++) {
                    if (bean.getInfo().getData().get(i).getImg().size() == 2 || bean.getInfo().getData().get(i).getImg().size() == 4) {
                        setDataBean(bean.getInfo().getData().get(i), DynamicBase.RIGHT_BIG);
                    } else if (bean.getInfo().getData().get(i).getImg().size() <= 1) {
                        setDataBean(bean.getInfo().getData().get(i), DynamicBase.LEFT_BIG);
                    } else {
                        setDataBean(bean.getInfo().getData().get(i), DynamicBase.THREE_SMALL);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 设置数据
     *
     * @param bean
     * @param type
     */
    public void setDataBean(DynamicBean.InfoBean.DataBean bean, int type) {
        DynamicBase databen = new DynamicBase(type);
        databen.setAttention(bean.isAttention());
        databen.setPraise(bean.isPraise());
        databen.setDynamicId(bean.getDynamicId());
        databen.setCreateTime(bean.getCreateTime());
        databen.setDynamicContent(bean.getDynamicContent());
        databen.setUserId(bean.getUserId());
        databen.setPraiseNum(bean.getPraiseNum());
        databen.setTreadNum(bean.getTreadNum());
        databen.setCommentNum(bean.getCommentNum());
        databen.setIs_praise_tread(bean.getIs_praise_tread());
        databen.setUserName(bean.getUserName());
        databen.setHeadImg(bean.getHeadImg());
        databen.setVideo(bean.getVideo());
        databen.setCommunityUrl(bean.getCommunityUrl());
        databen.setGroup_id(bean.getGroup_id());
        databen.setCommunityId(bean.getCommunityId());
        databen.setIs_anymit(bean.getIs_anonymity());
        if (bean.getImg().size() != 0) {
            databen.setImg(bean.getImg());
        }
        datas.add(databen);
    }

    WarpLinearLayout linearLayout;
    private LinearLayout horizontalScrollView;

    public void setView() {
        horizontalScrollView = getView(R.id.view_dynamic_hader_HorizontalScrollView);
        linearLayout = getView(R.id.view_dynamic_hader_WarpLinearLayout);
        postCommunityAndGambitHotListApi();
    }

    /**
     * 社区热门设置
     */
    DynamicHaderBean bean;

    public void postCommunityAndGambitHotListApi() {
        OkHttpUtils.get().url(ComantUtils.MyUrlHot + ComantUtils.getCommunityAndGambitHotListApi)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(String response, int id) {
                Gson gson = new Gson();
                bean = gson.fromJson(response, DynamicHaderBean.class);
                if (bean.getCode() != 200) {
                    return;
                }
                horizontalScrollView.removeAllViews();
                for (int i = 0; i < bean.getInfo().getHotChatgroup().size(); i++) {
                    View myView = LayoutInflater.from(context).inflate(R.layout.dynamic_hotchatgroup_view, null);
                    ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(dip2px(132), dip2px(66));
                    myView.setLayoutParams(params);
                    myView.setOnClickListener(hotChatgroupOnClick);
                    myView.setTag(i);
                    TextView title = myView.findViewById(R.id.hotchagroup_title);
                    title.setText(bean.getInfo().getHotChatgroup().get(i).getGroupName());
                    TextView num = myView.findViewById(R.id.hotchagroup_num);
                    num.setText(bean.getInfo().getHotChatgroup().get(i).getIntegralNum());
                    ImageView img = myView.findViewById(R.id.imageView1);
                    RequestOptions options = new RequestOptions();
                    options.error(R.mipmap.huangye);
                    options.placeholder(R.mipmap.huangye);
                    Glide.with(context).load("http://bisonchat.com/" + bean.getInfo().getHotChatgroup().get(i).getImage())
                            .apply(options)
                            .into(img);

                    Glide.with(context).load(MyApplication.getCurrentUserInfo().getHeadImg()).into(userheadview);


                    horizontalScrollView.addView(myView);
                }
                linearLayout.removeAllViews();
                for (int i = 0; i < bean.getInfo().getHotGambitList().size(); i++) {
                    if (i < 5) {
                        View view = LayoutInflater.from(context).inflate(R.layout.dynamic_hotgambitlist_view, null);
                        view.setOnClickListener(hotGambitListOnClick);
                        view.setTag(i);
                        TextView tv = view.findViewById(R.id.myhorizontalScrollViewview_Tv);
                        switch (i) {
                            case 0:
                                tv.setBackgroundResource(R.drawable.icon_gambitlist1);
                                break;
                            case 1:
                                tv.setBackgroundResource(R.drawable.icon_gambitlist2);
                                break;
                            case 2:
                                tv.setBackgroundResource(R.drawable.icon_gambitlist3);
                                break;
                            case 3:
                                tv.setBackgroundResource(R.drawable.icon_gambitlist4);
                                break;
                            case 4:
                                tv.setBackgroundResource(R.drawable.icon_gambitlist5);
                                break;
                        }
                        if(( UtilsTool.jieMiByte64(bean.getInfo().getHotGambitList().get(i).getTitle()).length()>13)){
                            String data =UtilsTool.jieMiByte64(bean.getInfo().getHotGambitList().get(i).getTitle())+"...";
                            tv.setText("# " + data);
                        }
                        else{

                            String data =UtilsTool.jieMiByte64(bean.getInfo().getHotGambitList().get(i).getTitle())+"...";
                            tv.setText("# " + UtilsTool.jieMiByte64(bean.getInfo().getHotGambitList().get(i).getTitle()));
                        }


                        linearLayout.addView(view);
                    }
                }
            }
        });
    }

    /**
     * 热门社区点击事件
     */
    View.OnClickListener hotChatgroupOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int indext = (int) v.getTag();
            Intent intent = new Intent();
            intent.putExtra("bean", bean.getInfo().getHotChatgroup().get(indext));
            intent.putExtra("title", "dynamic");
            intent.setClass(context, Act_GroupChatDetailNew.class);
            startActivityForResult(intent, 30);
        }
    };
    /**
     * 热门话题点击事件
     */
    View.OnClickListener hotGambitListOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int indext = (int) v.getTag();
            Intent intent = new Intent();
            intent.putExtra("id", bean.getInfo().getHotGambitList().get(indext).getId());
            intent.putExtra("groupName", bean.getInfo().getHotGambitList().get(indext).getGroupName()+"");
            intent.putExtra("Groupid", bean.getInfo().getHotGambitList().get(indext).getGroup_id() + "");
            intent.putExtra("parentUserId", bean.getInfo().getHotGambitList().get(indext).getUid() + "");
            startAct(intent, Act_TopicDetails.class);
        }
    };

    public int dip2px(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent();
        intent.putExtra("dynamicId", datas.get(position).getDynamicId() + "");
        intent.putExtra("communityName", datas.get(position).getCommunityName());
        intent.putExtra("community_id", datas.get(position).getCommunityId() + "");
        intent.putExtra("parentUserId", datas.get(position).getUserId() + "");
        intent.putExtra("isPraise", datas.get(position).isPraise());
        intent.putExtra("userid", datas.get(position).getUserId() + "");
        intent.putExtra("Is_anymit",datas.get(position).getIs_anymit()+"");



        startAct(intent, Act_DynamicDetail.class);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
        switch (view.getId()) {
            case R.id.img_delete://删除
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("提示").setMessage("你确定要删除这条动态么？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteMyDynamic(datas.get(position).getDynamicId() + "");
                            }
                        }).setNegativeButton("取消", null).show();
                break;
            case R.id.ll_zan://点赞
                view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.zan_anim));
                if (datas.get(position).isPraise()) {
                    setCancelLikeApi(position, MyApplication.getCurrentUserInfo().getUserId(), datas.get(position).getDynamicId());
                } else {
                    dianzan(position, MyApplication.getCurrentUserInfo().getUserId(), datas.get(position).getDynamicId());
                }
                break;
            case R.id.img_square_header://头像
                guanzhuposition = position;
//                if (datas.get(position).getIs_anymit() != null && datas.get(position).getIs_anymit().equals("1")) {
                Intent intent = new Intent();
                intent.putExtra("personId", datas.get(position).getUserId() + "");
                intent.putExtra("Attention", datas.get(position).isAttention());
                intent.putExtra("Groupid", datas.get(position).getGroup_id() + "");
                intent.putExtra("Is_anymit", datas.get(position).getIs_anymit() + "");
                intent.setClass(context, Act_StrangerDetail.class);
                startActivityForResult(intent, 20);
//                } else {
//                    MyToast.show(context, "匿名发布不可查看");
//                }
                break;
            case R.id.id2://评论
                Intent intent2 = new Intent();
                intent2.putExtra("dynamicId", datas.get(position).getDynamicId() + "");
                intent2.putExtra("communityName", datas.get(position).getCommunityName());
                intent2.putExtra("community_id", datas.get(position).getDynamicId() + "");
                intent2.putExtra("parentUserId", datas.get(position).getUserId() + "");



                startAct(intent2, Act_DynamicDetail.class);
                break;
            case R.id.follow_button://关注
                if (datas.get(position).isAttention()) {
                    setUserFriendsFollowApi(position, MyApplication.getCurrentUserInfo().getUserId(), datas.get(position).getUserId());
                } else {
                    setCancelUserFriendsFollowApi(position, MyApplication.getCurrentUserInfo().getUserId(), datas.get(position).getUserId());
                }
                break;
            case R.id.all_view://点击大图或视频
                if (datas.get(position).getVideo().equals("")) {
                    Intent intent1 = new Intent();
                    intent1.putStringArrayListExtra("imagList", (ArrayList<String>) datas.get(position).getImg());
                    startAct(intent1, Act_ShowImageLIst.class);
                } else {
                    Intent intent1 = new Intent();
                    intent1.putExtra("type", 2);
                    intent1.putExtra("resource", datas.get(position).getVideo());
                    if (datas.get(position).getImg() != null) {
                        intent1.putExtra("thumb", datas.get(position).getImg().get(0));
                    }
                    intent1.putExtra("title", datas.get(position).getUserName());
                    startAct(intent1, Act_Video.class);
                }
                break;
        }
    }

    /**
     * 动态列表删除
     *
     * @param dynamicId
     */
    private void deleteMyDynamic(final String dynamicId) {
        ReqMyDynamicList.deleteMyDynamic(context, "adapter", MyApplication.getCurrentUserInfo().getUserId(), dynamicId, new EntityCallBack<BaseResp>() {
            @Override
            public Class<BaseResp> getEntityClass() {
                return BaseResp.class;
            }

            @Override
            public void onSuccess(BaseResp resp) {
                if (resp.is200()) {
                    for (int i = 0; i < datas.size(); i++) {
                        if (dynamicId.equals(datas.get(i).getDynamicId())) {
                            datas.remove(i);
                            adapter.notifyDataSetChanged();
                        }
                    }
                    MyToast.show(context, resp.getMsg());
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

    /**
     * 点赞
     * data_type  1动态点赞 2动态评论点赞  3话题点赞 4话题评论点赞
     */
    String LikeApiType = "1";

    private void dianzan(final int position, final String user_id, String data_id) {//点赞
        dialog.show();
        OkHttpUtils.post().url(ComantUtils.MyUrlHot + ComantUtils.setLikeApi)
                .addParams("user_id", user_id)
                .addParams("data_id", data_id)
                .addParams("data_type", "1")
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
                    datas.get(position).setPraise(true);
                    datas.get(position).setPraiseNum(String.valueOf(Integer.valueOf(datas.get(position).getPraiseNum()) + 1));
                    adapter.notifyDataSetChanged();
                    MyToast.show(context, bean.getMsg());
                    postPushMessage(position);
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
                .addParams("data_type", "1")
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
                    datas.get(position).setPraise(false);
                    datas.get(position).setPraiseNum(String.valueOf(Integer.valueOf(datas.get(position).getPraiseNum()) - 1));
                    adapter.notifyDataSetChanged();
                    MyToast.show(context, bean.getMsg());
                }
            }
        });
    }

    /**
     * 关注
     */
    private void setUserFriendsFollowApi(final int position, String user_id, String followUserId) {//点赞
        dialog.show();
        OkHttpUtils.post().url(ComantUtils.MyUrlHot + ComantUtils.setUserFriendsFollowApi)
                .addParams("userId", user_id)
                .addParams("followUserId", followUserId)
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
                    MyToast.show(context, "关注成功！");
                    datas.get(position).setAttention(false);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * 取消关注
     */
    private void setCancelUserFriendsFollowApi(final int position, String user_id, String followUserId) {//点赞
        dialog.show();
        OkHttpUtils.post().url(ComantUtils.MyUrlHot + ComantUtils.setUserFriendsFollowApi)
                .addParams("userId", user_id)
                .addParams("followUserId", followUserId)
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
                    datas.get(position).setAttention(true);
                    adapter.notifyDataSetChanged();
                    MyToast.show(context, "取消关注成功！");
                }
            }
        });
    }

    @Override
    public void image() {//点击图片

        if (MyApplication.getCurrentUserInfo().getUserPhone()!=null&&!MyApplication.getCurrentUserInfo().getUserPhone().equals(""))
        {
            rxPermissions
                    .request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
                            , Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {
                            if (aBoolean) {
                                startActivityForResult(Act_PublishPictures.class, 10);
                            } else {
                                Toast.makeText(getActivity(), "请打开拍照权限", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

        else
        {

            edit_userphone();



        }
    }

    @Override
    public void video() {//点击视频

        if (MyApplication.getCurrentUserInfo().getUserPhone()!=null&&!MyApplication.getCurrentUserInfo().getUserPhone().equals(""))

        {  rxPermissions
                .request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
                        , Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.RECORD_AUDIO)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            startActivityForResult(Act_VideoRecording.class, 100);
                        } else {
                            Toast.makeText(getActivity(), "请打开拍照权限", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        }
        else
        {
            edit_userphone();
        }

    }

    @Subscribe
    public void onEventMainThread(EventMessage eventMessage) {
        switch (eventMessage.getType()) {
            case 1:
                ll_pop.setVisibility(View.GONE);

                break;
            case 2:

                msmcount = eventMessage.getData().toString();
                ll_pop.setVisibility(View.VISIBLE);
                cd_pop.setText(msmcount);
                Log.d("后台", "收到eventbus 消息传递");
                break;
            case 3:

                break;
        }


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                String file = data.getStringExtra("output");
                Intent intent = new Intent();
                intent.putExtra("file", file);
                intent.setClass(getContext(), Act_UploadVideo.class);
                startActivityForResult(intent, 10);
            }
            if (resultCode == 10) {
                page = 0;
                datas.clear();
                postBackDtata();
            }
        }
        if (requestCode == 10 && resultCode == 10) {
            page = 0;
            datas.clear();
            postBackDtata();
        }
        if (requestCode == 20 && resultCode == 20) {
            String Status = data.getStringExtra("statsu");
            if (Status.equals("0")) {//关注
                datas.get(guanzhuposition).setAttention(true);
            } else {//没有关注
                datas.get(guanzhuposition).setAttention(false);
            }
            adapter.notifyDataSetChanged();
        }
        if (requestCode == 30 && resultCode == 30) {
            postCommunityAndGambitHotListApi();
        }

    }

    private int guanzhuposition;

    /**
     * 获取动态搜索数据
     */
    public void postBacksearchDtata(String search) {
        OkHttpUtils.get().url("http://bisonchat.com/index/index/searchDynamicAndGambitAndUserAndCommunityListApi.html")
                .addParams("search", search + "")
                .addParams("myuid", MyApplication.getCurrentUserInfo().getUserId())
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String userDynamicList = jsonObject.getString("userDynamicList");
                    String gambitList = jsonObject.getString("gambitList");
                    String communityList = jsonObject.getString("communityList");
                    Gson gson = new Gson();
                    DynamicBean bean = gson.fromJson(userDynamicList, DynamicBean.class);
                    if (bean.getCode() != 200) {
                        return;
                    }
                    for (int i = 0; i < bean.getInfo().getData().size(); i++) {
                        if (bean.getInfo().getData().get(i).getImg().size() == 2 || bean.getInfo().getData().get(i).getImg().size() == 4) {
                            setDataBean(bean.getInfo().getData().get(i), DynamicBase.RIGHT_BIG);
                        } else if (bean.getInfo().getData().get(i).getImg().size() <= 1) {
                            setDataBean(bean.getInfo().getData().get(i), DynamicBase.LEFT_BIG);
                        } else {
                            setDataBean(bean.getInfo().getData().get(i), DynamicBase.THREE_SMALL);
                        }
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 推送消息
     * alias 指定推送人id
     * content推送消息内容
     */
    public void postPushMessage(int position) {
        MessageBean bean = new MessageBean();
        bean.setTitle("点赞消息！");
        Gson gson = new Gson();
        String android_notification = gson.toJson(bean);



        OkHttpUtils.get().url("http://bisonchat.com/index/jpush/aliasPushApi")
                .addParams("alias", datas.get(position).getUserId())
                .addParams("content", "用户：" + MyApplication.getCurrentUserInfo().getUserName() + "为您点了一个赞")
                .addParams("android_notification", android_notification)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Debug.e("---------------onError==" + e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                Debug.e("---------------onResponse==" + response);
            }
        });
    }




private void edit_userphone()
{
    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

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



}


