package com.maoyongxin.myapplication.ui.fgt.message.act;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;

import com.lykj.aextreme.afinal.utils.Debug;

import com.lykj.aextreme.afinal.utils.MyToast;
import com.lzy.widget.HeaderViewPager;
import com.makeramen.roundedimageview.RoundedImageView;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.common.BaseAct;

import com.maoyongxin.myapplication.common.ComantUtils;
import com.maoyongxin.myapplication.common.MyApplication;

import com.maoyongxin.myapplication.http.callback.EntityCallBack;
import com.maoyongxin.myapplication.http.request.ReqRefreshToken;
import com.maoyongxin.myapplication.http.response.RefreshTokenResponse;
import com.maoyongxin.myapplication.tool.utils.BlurUtil;
import com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.fgt.fgt.HeaderViewPagerFragment;
import com.maoyongxin.myapplication.ui.fgt.community.bean.DynamicBase;

import com.maoyongxin.myapplication.ui.fgt.community.bean.UserFriendsFollowApiBean;
import com.maoyongxin.myapplication.ui.fgt.message.act.bean.UserDynamicListBean;
import com.maoyongxin.myapplication.ui.fgt.message.act.strangerdetail.fgt.CommunityFragment;
import com.maoyongxin.myapplication.ui.fgt.message.act.strangerdetail.fgt.DongtaiFragment;
import com.maoyongxin.myapplication.view.ViewPagerDoubleIndicator;
import com.sendtion.xrichtext.GlideApp;
import com.yanzhenjie.loading.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import io.rong.eventbus.EventBus;
import io.rong.imkit.RongIM;
import jp.wasabeef.glide.transformations.BlurTransformation;
import okhttp3.Call;

/**
 * 个人动态
 */
public class Act_StrangerDetail extends BaseAct {
    RoundedImageView stranger_header;
    TextView tvStrangerName;
    TextView btnLike;
    Button btnDynamic;
    Button btnCommunity;
    TextView tvStrangerAddress, xingqu1, xingqu2;
    ViewPagerDoubleIndicator linTopindicator;
    ViewPager vpMyviewPager;
    TextView btnContact, note;
    private String personId;
    private LatLng personLatlng;
    private String sex;
    private String headImgUrl;
    private String userName;
    private List<Button> btnList;
    private List<HeaderViewPagerFragment> fragList;
    private int prePosition = 0;
    private TextView tvSex;
    private ImageView mygrid_bg;
    private HeaderViewPager scrollableLayout;
    Bitmap shareBit;
    Bitmap blurBitmap;
    private byte[] bytes;
    private ImageView mygrid_blur;
    private BlurUtil blurUtil;
    private Boolean noblursed=true;
    @Override
    protected void handlerPassMsg(int target, int target1, Object obj) {

    }

    @Override
    public int initLayoutId() {
        return R.layout.act_stranger_detail;
    }


    @Override
    public void initView() {

        hideHeader();

        setOnClickListener(R.id.stranger_back);
        mygrid_blur=getView(R.id.mygrid_blur);
        mygrid_bg = getView(R.id.mygrid_bg);
        note = getView(R.id.note);
        scrollableLayout = getView(R.id.scrollableLayout);
        stranger_header = getView(R.id.stranger_header);
        tvStrangerName = getView(R.id.tv_stranger_name);
        btnLike = getViewAndClick(R.id.btn_like);
        tvSex = getView(R.id.stranger_sex);
        btnDynamic = getViewAndClick(R.id.btn_dynamic);
        btnCommunity = getViewAndClick(R.id.btn_community);
        tvStrangerAddress = getView(R.id.tv_stranger_address);
        linTopindicator = getView(R.id.lin_topindicator);
        vpMyviewPager = getView(R.id.vp_myviewPager);
        btnContact = getViewAndClick(R.id.btn_contact);
        xingqu2 = getView(R.id.xingqu2);
        xingqu1 = getView(R.id.xingqu1);



    }

    public String getPersonId() {
        return personId;
    }

    private ZLoadingDialog dialog;

    @Override
    public void initData() {
        blurUtil = new BlurUtil(this);

        personId = getIntent().getStringExtra("personId");
        dialog = new ZLoadingDialog(this);
        dialog.setLoadingBuilder(Z_TYPE.ROTATE_CIRCLE)//设置类型
                .setLoadingColor(Color.DKGRAY)//颜色
                .setHintText("数据加载中...")
                .setHintTextSize(16) // 设置字体大小 dp
                .setHintTextColor(Color.DKGRAY)  // 设置字体颜色
                .setDurationTime(0.5) // 设置动画时间百分比 - 0.5倍
                .setDialogBackgroundColor(Color.parseColor("#CCffffff")); // 设置背景色，默认白色
        btnList = new ArrayList<Button>();
        btnList.add(btnDynamic);
        btnList.add(btnCommunity);
        fragList = new ArrayList<>();
        fragList.add(new DongtaiFragment());
        fragList.add(new CommunityFragment());
        if (getIntent().getBooleanExtra("Attention", false)) {
            btnLike.setSelected(false);
            btnLike.setTextColor(getResources().getColor(R.color.white));
            btnLike.setText("关注");
        } else {
            btnLike.setSelected(true);
            btnLike.setTextColor(getResources().getColor(R.color.black));
            btnLike.setText("已关注");
        }
        showFragment();
        postBackHader();
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
            case R.id.stranger_back:
                finish();
                break;
            case R.id.btn_contact://聊天
                if (MyApplication.getCurrentUserInfo().getUserId().equals(personId)) {
                    MyToast.show(context, "自己暂不能与自己聊天！");
                    return;
                }

                RongIM.getInstance().startPrivateChat(getActivity(), personId, tvStrangerName.getText().toString());
                break;
            case R.id.btn_like:
                if (btnLike.isSelected()) {
                    setCancelUserFriendsFollowApi(MyApplication.getCurrentUserInfo().getUserId(), personId);
                } else {
                    setUserFriendsFollowApi(MyApplication.getCurrentUserInfo().getUserId(), personId);
                }
                break;
            case R.id.btn_dynamic://动态
                vpMyviewPager.setCurrentItem(0);
                break;
            case R.id.btn_community://圈子
                vpMyviewPager.setCurrentItem(1);
                break;
        }
    }
    /**
     * 展示fragment
     */
    private void showFragment() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        ViewPagerAdapter vpAdapter = new ViewPagerAdapter(fm, fragList);
        vpAdapter.notifyDataSetChanged();
        vpMyviewPager.setAdapter(vpAdapter);
        /**
         * 指示器联动
         */
        vpMyviewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                linTopindicator.scroll(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                btnList.get(position).setTextColor(Color.parseColor("#2F60F3"));
                btnList.get(prePosition).setTextColor(Color.parseColor("#252830"));
                prePosition = position;
                if (scrollableLayout != null) {
                    scrollableLayout.setCurrentScrollableContainer(fragList.get(position));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vpMyviewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (scrollableLayout != null) {
                    scrollableLayout.setCurrentScrollableContainer(fragList.get(position));
                }
            }
        });
        scrollableLayout.setCurrentScrollableContainer(fragList.get(0));
        scrollableLayout.setOnScrollListener(new HeaderViewPager.OnScrollListener() {
            @Override
            public void onScroll(int currentY, int maxY) {

                if (noblursed)
                {
                    blurUtil.clickBlurImg(mygrid_blur,8
                    );
                    noblursed=false;
                }



                //让头部具有差速动画,如果不需要,可以不用设置
                mygrid_blur.setTranslationY(currentY / 10);
                //动态改变标题栏的透明度,注意转化为浮点型

                float alpha = 1.0f * currentY / maxY;

                mygrid_blur.setAlpha(alpha);
                mygrid_bg.setAlpha(1-alpha);



            }
        });
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private List<HeaderViewPagerFragment> list;

        public ViewPagerAdapter(FragmentManager fm, List<HeaderViewPagerFragment> list) {
            super(fm);
            this.list = list;
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return list.get(0);
            } else {
                return list.get(1);
            }
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }

    String myuid;

    /**
     * 获取顶部信息
     */
    public void postBackHader() {
        if (personId.equals(MyApplication.getCurrentUserInfo().getUserId())) {
            myuid = "";
        } else {
            myuid = MyApplication.getCurrentUserInfo().getUserId();
        }
        dialog.show();
        OkHttpUtils.get().url(ComantUtils.MyUrl + ComantUtils.getUserInfoApi)
                .addParams("userId", personId)
                .addParams("myuid", myuid)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                dialog.dismiss();
            }

            @Override
            public void onResponse(String response, int id) {
                dialog.dismiss();
                Gson gson = new Gson();
                UserDynamicListBean bean = gson.fromJson(response, UserDynamicListBean.class);
                if (bean.getInfo().getSex() != null) {
                    if (bean.getInfo().getSex().equals("1")) {//男
                        tvSex.setSelected(true);
                    } else if (bean.getInfo().getSex().equals("0")) {//女
                        tvSex.setSelected(false);
                    }
                } else {
                    tvSex.setSelected(true);
                }

                tvStrangerName.setText(bean.getInfo().getUserName());
                RequestOptions options = new RequestOptions();
                options.error(R.mipmap.user_head_img); //异常时候显示的图片
                options.placeholder(R.mipmap.user_head_img);//加载成功前显示的图片
                options.fallback(R.mipmap.user_head_img); //url为空的时候,显示的图片


                RequestOptions options1 = new RequestOptions();
                options1.error(R.mipmap.icon_bg1); //异常时候显示的图片
                options1.placeholder(R.mipmap.icon_bg1);//加载成功前显示的图片
                options1.fallback(R.mipmap.icon_bg1); //url为空的时候,显示的图片



                Glide.with(context).load(bean.getInfo().getHeadImg()).into(stranger_header);





                    Glide.with(Act_StrangerDetail.this)
                            .setDefaultRequestOptions(options1)
                            .load(bean.getInfo().getBackground_img()).into(mygrid_bg);

                    Glide.with(Act_StrangerDetail.this)
                            .setDefaultRequestOptions(options1)
                            .load(bean.getInfo().getBackground_img()).into(mygrid_blur);











                tvStrangerAddress.setText("会员ID" + bean.getInfo().getUserId());
                note.setText(bean.getInfo().getNote());
                if (bean.getInfo().getUserInterest().size() == 1) {
                    xingqu1.setText(bean.getInfo().getUserInterest().get(0).getInterestName());
                }
                if (bean.getInfo().getUserInterest().size() == 2) {
                    xingqu2.setText(bean.getInfo().getUserInterest().get(1).getInterestName());
                }
            }
        });
    }

    private String statsu = "";

    /**
     * 关注
     */
    private void setUserFriendsFollowApi(String user_id, String followUserId) {//点赞
        OkHttpUtils.post().url(ComantUtils.MyUrlHot + ComantUtils.setUserFriendsFollowApi)
                .addParams("userId", user_id)
                .addParams("followUserId", followUserId)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(String response, int id) {
                Gson gson = new Gson();
                UserFriendsFollowApiBean bean = gson.fromJson(response, UserFriendsFollowApiBean.class);
                if (bean.getCode() == 200) {
                    statsu = "1";
                    MyToast.show(context, "关注成功！");
                    btnLike.setSelected(true);
                    btnLike.setTextColor(getResources().getColor(R.color.black));
                    btnLike.setText("已关注");
                    EventBus.getDefault().post(new DynamicBase(1));
                }
            }
        });
    }

    /**
     * 取消关注
     */
    private void setCancelUserFriendsFollowApi(String user_id, String followUserId) {//点赞
        OkHttpUtils.post().url(ComantUtils.MyUrlHot + ComantUtils.setUserFriendsFollowApi)
                .addParams("userId", user_id)
                .addParams("followUserId", followUserId)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(String response, int id) {
                Gson gons = new Gson();
                UserFriendsFollowApiBean bean = gons.fromJson(response, UserFriendsFollowApiBean.class);
                if (bean.getCode() == 200) {
                    statsu = "0";
                    EventBus.getDefault().post(new DynamicBase(0));
                    btnLike.setSelected(false);
                    btnLike.setTextColor(getResources().getColor(R.color.white));
                    btnLike.setText("关注");
                    MyToast.show(context, "取消关注成功！");
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (TextUtils.isEmpty(statsu)) {
            finish();
        } else {
            Debug.e("------onKeyDown-----" + statsu);
            Intent intent = new Intent();
            intent.putExtra("statsu", statsu);
            setResult(20, intent);
            finish();
        }
        return true;
    }





}
