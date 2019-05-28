package com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.fgt.act;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.lykj.aextreme.afinal.utils.Debug;
import com.lykj.aextreme.afinal.utils.MyToast;
import com.lzy.widget.HeaderViewPager;
import com.makeramen.roundedimageview.RoundedImageView;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.bean.JiGuangSharePlatformModel;
import com.maoyongxin.myapplication.bean.MessageBean;
import com.maoyongxin.myapplication.common.AppConfig;
import com.maoyongxin.myapplication.common.BaseAct;
import com.maoyongxin.myapplication.common.BaseFgt;
import com.maoyongxin.myapplication.common.ComantUtils;
import com.maoyongxin.myapplication.common.MyApplication;
import com.maoyongxin.myapplication.tool.StatusBarUtil;
import com.maoyongxin.myapplication.tool.StringUtils;
import com.maoyongxin.myapplication.tool.UtilsTool;
import com.maoyongxin.myapplication.ui.fgt.community.act.Act_PublishPictures;
import com.maoyongxin.myapplication.ui.fgt.community.act.Act_ShowImageLIst;
import com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.bean.IsLikeApiBean;
import com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.bean.ItemImageBean;
import com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.fgt.bean.TopicDetailsDianZanBean;
import com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.fgt.bean.TopicDetailsHaderBean;
import com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.fgt.bean.UserEvent;
import com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.fgt.fgt.Fgt_HotReviews;
import com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.fgt.fgt.Fgt_MyHt;
import com.maoyongxin.myapplication.ui.fgt.message.act.strangerdetail.fgt.framdialog.ShareDialogFragment;
import com.maoyongxin.myapplication.ui.fgt.min.act.Act_UserEditNew;
import com.maoyongxin.myapplication.view.ViewPagerDoubleIndicator;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.sendtion.xrichtext.RichTextView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.callback.OnUrlClickListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.rong.eventbus.EventBus;
import okhttp3.Call;

/**
 * 动态=话题（详情）页
 */
public class Act_TopicDetails extends BaseAct implements  ShareDialogFragment.Listener {


    private SmartRefreshLayout mRefreshLayout;
    private ViewPager viewPager;
    private ViewPagerDoubleIndicator tabs;
    private List<TextView> btnList = new ArrayList<>();
    Toolbar toolbar;
    private List<BaseFgt> fragments = new ArrayList<>();
    LinearLayout pagerHeader;
    ZLoadingDialog dialog;
    private RoundedImageView haderImage, img;
    private EmojiconTextView ninckName, tvContext, emotv_title;
    private TextView tv_groupName, date_Time;
    private EditText etContext;
    private ImageView delate,toppic_shareIcon;
    private LinearLayout et_new_content;
    private LinearLayout topill_1;
    private TextView zanImg;
    private int position=0;
    private List<Integer> imgIndex= new ArrayList<>();
    private ArrayList<JiGuangSharePlatformModel> list = new ArrayList<>();
    private Bitmap shareBit;
    private String gambitId="";
    @Override
    protected void handlerPassMsg(int target, int target1, Object obj) {

    }

    @Override
    public int initLayoutId() {
        return R.layout.act_topicdetails;
    }

    private TextView remen, my;

    @Override
    public void initView() {
        hideHeader();
        zanImg = getViewAndClick(R.id.zanImg);
        topill_1 = getView(R.id.topill_1);
        toolbar = getView(R.id.toolbar);
        et_new_content = getView(R.id.et_new_content);
        tabs = getView(R.id.tabs);
        viewPager = getView(R.id.viewPager);
        mRefreshLayout = getView(R.id.refreshLayout);
        pagerHeader = getView(R.id.pagerHeader);
        remen = getViewAndClick(R.id.remen);
        my = getViewAndClick(R.id.my);
        setOnClickListener(R.id.TopicDetails_back);
        delate = getViewAndClick(R.id.img_delete);
        btnList.add(remen);//话题列表，
        btnList.add(my);//我的话题
        fragments.add(new Fgt_HotReviews());
        fragments.add(new Fgt_MyHt());
        haderImage = getView(R.id.mine_header);
        ninckName = getView(R.id.nickName);
        img = getView(R.id.image);
        tvContext = getView(R.id.context);
        tv_groupName = getView(R.id.tv_groupName);
        emotv_title = getView(R.id.emotv_title);
        date_Time = getView(R.id.date_Time);
        etContext = getView(R.id.emtv_response_holer);
        toppic_shareIcon=getViewAndClick(R.id.toppic_shareIcon);
        setOnClickListener(R.id.bt_response_holer);
    }

    int prePosition = 0;
    private String id, groupName;

    @Override
    public void initData() {
        groupName = getIntent().getStringExtra("groupName");
        id = getIntent().getStringExtra("id");
        dialog = new ZLoadingDialog(this);
        dialog.setLoadingBuilder(Z_TYPE.ROTATE_CIRCLE)//设置类型
                .setLoadingColor(Color.DKGRAY)//颜色
                .setHintText("数据获取中...")
                .setHintTextSize(16) // 设置字体大小 dp
                .setHintTextColor(Color.DKGRAY)  // 设置字体颜色
                .setDurationTime(0.5) // 设置动画时间百分比 - 0.5倍
                .setDialogBackgroundColor(Color.parseColor("#CCffffff")); // 设置背景色，默认白色
//        内容跟随偏移
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
                EventBus.getDefault().post(new UserEvent(prePosition + "", "2"));
                refreshLayout.finishLoadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                EventBus.getDefault().post(new UserEvent(prePosition + "", "1"));
                refreshLayout.finishRefresh();
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tabs.scroll(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                btnList.get(prePosition).setTextColor(Color.parseColor("#4D4D4D"));
                btnList.get(position).setTextColor(Color.parseColor("#2F60F3"));
                prePosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(0);
        viewPager.setAdapter(new ContentAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(0);
        postHader();
       getshareImg();
    }

    @Override
    public void requestData() {

    }

    @Override
    public void updateUI() {

    }

    @Override
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.remen:
                viewPager.setCurrentItem(0);
                break;
            case R.id.my:
                viewPager.setCurrentItem(1);
                break;
            case R.id.TopicDetails_back:
                finish();
                break;
            case R.id.bt_response_holer://发送按钮


                if (MyApplication.getCurrentUserInfo().getUserPhone()!=null&&!MyApplication.getCurrentUserInfo().getUserPhone().equals(""))
                {
                    sendMessage();
                }

                else
                {
                    edit_userphone();
                }



                break;
            case R.id.img_delete://删除 这条动态
                deleteMyDynamic(id);
                break;
            case R.id.zanImg://点赞
                view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.zan_anim));
                if (zanImg.isSelected()) {//取消点赞
                    CancelLikeApi();
                } else {//点赞
                    setLikeApi();
                }


                break;

            case R.id.toppic_shareIcon:

                showShareDialog();
                break;


        }
    }

    /**
     * 删除此条动态
     *
     * @param huatiId
     */
    private void deleteMyDynamic(final String huatiId) {
        OkHttpUtils.post().url("http://st.3dgogo.com/index/chatgroup_gambit/delete_chatgroup_gambit")
                .addParams("id", huatiId)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                dialog.dismiss();
            }

            @Override
            public void onResponse(String response, int id1) {
                dialog.dismiss();
                setResult(20);
                Toast.makeText(context, "删除成功！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 内容页的适配器
     */
    private class ContentAdapter extends FragmentPagerAdapter {

        public ContentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    /**
     * 获取头部信息
     */
    String apiUrl = "http://st.3dgogo.com/index/chatgroup_gambit/get_gambit_details/id/";
    TopicDetailsHaderBean topicDetailsHaderBean;

    public void postHader() {
        dialog.show();
        OkHttpUtils.post().url(apiUrl + id)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                dialog.dismiss();
            }

            @Override
            public void onResponse(String response, int id) {
                dialog.dismiss();
                Gson gson = new Gson();
                topicDetailsHaderBean = gson.fromJson(response, TopicDetailsHaderBean.class);
                RequestOptions options = new RequestOptions();
                ninckName.setText(topicDetailsHaderBean.getInfo().getUserName());
                options.centerCrop();
                options.error(R.drawable.tupian);
                options.placeholder(R.drawable.tupian);
                RequestOptions options1 = new RequestOptions();
                options1.error(R.mipmap.default_group);
                options1.centerCrop();

                Glide.with(getActivity()).load(topicDetailsHaderBean.getInfo().getImg()).apply(options1).into(img);

                Glide.with(getActivity()).load(topicDetailsHaderBean.getInfo().getHeadImg()).into(haderImage);


             //  Glide.with(context).load(topicDetailsHaderBean.getInfo().getHeadImg()).apply(options1).into(haderImage);


                data_id = topicDetailsHaderBean.getInfo().getId();
                choseZanStatus();//获取当前点赞状态
                try {
                    if (UtilsTool.jieMiByte64(topicDetailsHaderBean.getInfo().getContent()).contains("http")) {

                        et_new_content.setVisibility(View.VISIBLE);
                        topill_1.setVisibility(View.GONE);


                        List<ItemImageBean> imageBeans = new ArrayList<>();
                        final  List<String> contentimgs=new ArrayList<>();

                        String[] ling = UtilsTool.jieMiByte64(topicDetailsHaderBean.getInfo().getContent()).split("</h3>");
                        for (int i = 0; i < ling.length; i++) {
                             ItemImageBean bean1 = new ItemImageBean();
                            String[] img = ling[i].split("=");
                            if (img.length > 1) {
                                String[] img2 = img[1].split("/>");
                                String[] img3 = img2[0].split("\"");
                                bean1.setUrl(img3[1]);
                            } else {
                                bean1.setUrl("");
                            }
                            String[] title = ling[i].split("<h3>");
                            if (title.length > 1) {
                                bean1.setStContext(title[1]);
                            } else {
                                bean1.setStContext("");
                            }
                            imageBeans.add(bean1);
                        }
                        for ( int i = 0; i < imageBeans.size(); i++) {
                         final   View view = LayoutInflater.from(context).inflate(R.layout.view_item_details, null);
                            EmojiconTextView title = getView(view, R.id.context);
                            RoundedImageView hader = getView(view, R.id.image);



                            if (imageBeans.get(i).getStContext().equals("")) {
                                title.setText("");
                            } else {
                                title.setText(imageBeans.get(i).getStContext());
                            }
                            if (imageBeans.get(i).getUrl().equals("")) {
                                hader.setVisibility(View.GONE);
                            } else
                                {
                                hader.setVisibility(View.VISIBLE);
                                Glide.with(context).load(imageBeans.get(i).getUrl()).into(hader);

                                contentimgs.add(imageBeans.get(i).getUrl());
                                hader.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Intent intent1 = new Intent();
                                        intent1.setClass(context, Act_ShowImageLIst.class);

                                        intent1.putStringArrayListExtra("imagList", (ArrayList<String>) contentimgs);
                                        context.startActivity(intent1);


                                    }
                                });
                            }
                            et_new_content.addView(view);
                        }
                    } else {
                        et_new_content.setVisibility(View.GONE);
                        try {
                            tvContext.setText(new String(Base64.decode(topicDetailsHaderBean.getInfo().getContent().getBytes(), Base64.DEFAULT)));
                        } catch (Exception e) {
                        }
                        topill_1.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                }
                tv_groupName.setText(groupName);
                if (topicDetailsHaderBean.getInfo().getTitle().equals("")) {
                    emotv_title.setVisibility(View.GONE);
                } else {
                    emotv_title.setVisibility(View.VISIBLE);
                    try {
                        emotv_title.setText(UtilsTool.jieMiByte64(topicDetailsHaderBean.getInfo().getTitle()));
                    } catch (Exception e) {
                    }
                }
                date_Time.setText(topicDetailsHaderBean.getInfo().getCreate_time());
                if (topicDetailsHaderBean.getInfo().getUid().equals(MyApplication.getCurrentUserInfo().getUserId())) {
                    delate.setVisibility(View.VISIBLE);
                } else {
                    delate.setVisibility(View.GONE);
                }
            }
        });

    }


    /**
     * 回复
     */
    String groupid, parentUserId;

    private void sendMessage() {
        parentUserId = getIntent().getStringExtra("parentUserId");
        groupid = getIntent().getStringExtra("Groupid");
        String s = etContext.getText().toString();
        if (TextUtils.isEmpty(s)) {
            MyToast.show(context, "请输入您要回复的内容！");
            return;
        }
        String str = Base64.encodeToString(s.getBytes(), Base64.DEFAULT);

        dialog.show();
        OkHttpUtils.post()
                .addParams("gambit_id", id)
                .addParams("group_id", groupid)
                .addParams("uid", MyApplication.getCurrentUserInfo().getUserId() + "")
                .addParams("parent_id", "0")
                .addParams("parentUserId", parentUserId)
                .addParams("content", str)
                .url("http://st.3dgogo.com/index/chatgroup_gambit/set_respond").build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        dialog.dismiss();

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        dialog.dismiss();
                        etContext.setText("");
                        postPushMessageUser(topicDetailsHaderBean.getInfo().getUid());
                        Toast.makeText(Act_TopicDetails.this, "回复成功", Toast.LENGTH_SHORT).show();
                        EventBus.getDefault().post(new UserEvent(prePosition + "", "1"));
                    }
                });
    }

    /**
     * 异步方式显示数据
     *
     * @param html
     */
    private void showDataSync(final String html, final RichTextView et_new_content) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) {
                showEditData(emitter, html);
            }
        })
                //.onBackpressureBuffer()
                .subscribeOn(Schedulers.io())//生产事件在io
                .observeOn(AndroidSchedulers.mainThread())//消费事件在UI线程
                .subscribe(new Observer<String>() {
                    @Override
                    public void onComplete() {
//                        if (et_new_content != null) {
//                            //在图片全部插入完毕后，再插入一个EditText，防止最后一张图片后无法插入文字
//                            et_new_content.addEditTextAtIndex(et_new_content.getLastIndex(), "");
//                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(String text) {
                        if (et_new_content != null) {
                            if (text.contains("<img") && text.contains("src=")) {
//                                //imagePath可能是本地路径，也可能是网络地址
                                String imagePath = StringUtils.getImgSrc(text);
//                                //插入空的EditText，以便在图片前后插入文字
//                                et_new_content.addEditTextAtIndex(et_new_content.getLastIndex(), "");
                                et_new_content.addImageViewAtIndex(et_new_content.getLastIndex(), imagePath);
                            } else {
                                et_new_content.addTextViewAtIndex(et_new_content.getLastIndex(), text);
                            }
                        }
                    }
                });
    }

    /**
     * 显示数据
     */
    protected void showEditData(ObservableEmitter<String> emitter, String html) {
        try {
            List<String> textList = StringUtils.cutStringByImgTag(html);
            for (int i = 0; i < textList.size(); i++) {
                String text = textList.get(i);
                emitter.onNext(text);
            }
            emitter.onComplete();
        } catch (Exception e) {
            e.printStackTrace();
            emitter.onError(e);
        }
    }

    /**
     * 获取当前详情点赞状态
     */
    private void choseZanStatus() {
        OkHttpUtils.get()
                .addParams("user_id", MyApplication.getCurrentUserInfo().getUserId() + "")
                .addParams("data_id", data_id)
                .addParams("data_type", "4")
                .url("http://bisonchat.com/index/praise/isLikeApi").build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        IsLikeApiBean bean = gson.fromJson(response, IsLikeApiBean.class);
                        if (bean.isOperation()) {
                            zanImg.setSelected(bean.isOperation());
                        }

                    }
                });
    }

    /**
     * 取消点赞
     */
    private String data_id;

    private void CancelLikeApi() {
        dialog.show();
        OkHttpUtils.post()
                .addParams("user_id", MyApplication.getCurrentUserInfo().getUserId() + "")
                .addParams("data_id", data_id)
                .addParams("data_type", "4")
                .url("http://bisonchat.com/index/praise/setCancelLikeApi.html").build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        dialog.dismiss();
                        Gson gson = new Gson();
                        TopicDetailsDianZanBean bean = gson.fromJson(response, TopicDetailsDianZanBean.class);
                        if (bean.isOperation()) {
                            zanImg.setSelected(false);
                            MyToast.show(context, bean.getMsg());
                        }
                    }
                });
    }

    /**
     * 点赞
     */
    private void setLikeApi() {
        dialog.show();
        OkHttpUtils.post()
                .addParams("user_id", MyApplication.getCurrentUserInfo().getUserId() + "")
                .addParams("data_id", data_id)
                .addParams("data_type", "4")
                .addParams("type", "1")
                .url("http://bisonchat.com/index/praise/setLikeApi.html").build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        dialog.dismiss();
                        Gson gson = new Gson();
                        TopicDetailsDianZanBean bean = gson.fromJson(response, TopicDetailsDianZanBean.class);
                        if (bean.isOperation()) {

                            postPushMessage(topicDetailsHaderBean.getInfo().getUid());
                            zanImg.setSelected(bean.isOperation());
                            MyToast.show(context, bean.getMsg());
                        }
                    }
                });
    }

    /**
     * 推送消息
     * alias 指定推送人id
     * content推送消息内容
     */
    public void postPushMessage(String UserId) {
        MessageBean bean = new MessageBean();
        bean.setTitle("点赞消息！");
//        bean.setTitle("回复信息");
        Gson gson = new Gson();
        String android_notification = gson.toJson(bean);
        OkHttpUtils.get().url("http://bisonchat.com/index/jpush/aliasPushApi")
                .addParams("alias", UserId)
                .addParams("content", "您的好友：" + MyApplication.getCurrentUserInfo().getUserName() + "为您点了一个'赞'")
//                .addParams("content", "您的好友：" + MyApplication.getCurrentUserInfo().getUserName() + "为您回复了一个消息")
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


    /**
     * 发送消息推送消息
     * alias 指定推送人id
     * content推送消息内容
     */
    public void postPushMessageUser(String UserId) {
        MessageBean bean = new MessageBean();
        bean.setTitle("回复信息");
        Gson gson = new Gson();
        String android_notification = gson.toJson(bean);
        OkHttpUtils.get().url("http://bisonchat.com/index/jpush/aliasPushApi")
                .addParams("alias", UserId)
                .addParams("content", "您的好友：" + MyApplication.getCurrentUserInfo().getUserName() + "为您回复了一个消息")
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

    private ShareParams generateParams() {
        ShareParams shareParams = new ShareParams();
        shareParams.setShareType(Platform.SHARE_WEBPAGE);
        shareParams.setTitle(UtilsTool.jieMiByte64(topicDetailsHaderBean.getInfo().getTitle()));
        shareParams.setText("彼信商业社区："+topicDetailsHaderBean.getInfo().getUserName());
      
        shareParams.setImageData(shareBit);
        shareParams.setUrl("http://bisonchat.com/home/chatgroup_gambit/appGambitDetails.html?gambit_id="+id);

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
                            .load(topicDetailsHaderBean.getInfo().getHeadImg())
                            .into(300, 300)
                            .get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
