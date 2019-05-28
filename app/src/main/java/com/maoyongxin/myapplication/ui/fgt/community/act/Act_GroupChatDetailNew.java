package com.maoyongxin.myapplication.ui.fgt.community.act;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lykj.aextreme.afinal.utils.Debug;
import com.lykj.aextreme.afinal.utils.MyUtil;
import com.lzy.widget.HeaderViewPager;
import com.makeramen.roundedimageview.RoundedImageView;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.common.BaseAct;
import com.maoyongxin.myapplication.common.ComantUtils;
import com.maoyongxin.myapplication.common.MyApplication;
import com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.bean.ChatGroupUserInfoListBean;
import com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.fgt.Fgt_GroupHuati;
import com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.fgt.Fgt_MyTopic;
import com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.fgt.HeaderViewPagerFragment;
import com.maoyongxin.myapplication.ui.fgt.community.bean.DynamicHaderBean;
import com.maoyongxin.myapplication.ui.fgt.min.act.Act_UserEditNew;
import com.maoyongxin.myapplication.view.SelectableRoundedImageView;
import com.maoyongxin.myapplication.view.ViewPagerDoubleIndicator;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import io.github.rockerhieu.emojicon.EmojiconTextView;
import io.rong.imkit.RongIM;
import io.rong.imkit.model.GroupUserInfo;
import okhttp3.Call;

/**
 * 社区动态=创业锦囊
 */
public class Act_GroupChatDetailNew extends BaseAct {
    public List<HeaderViewPagerFragment> fragments;
    private List<TextView> btnList = new ArrayList<>();
    private HeaderViewPager scrollableLayout;
    private TextView remen, my, tvContext, chengyuan;
    private ViewPagerDoubleIndicator linTopindicator;
    DynamicHaderBean.InfoBean.HotChatgroupBean bean;
    private ImageView hader;
    private EmojiconTextView title;
    private LinearLayout ll_Htfb;
    private View titleBar;
    private RoundedImageView roundedImageView1, roundedImageView2, roundedImageView3, roundedImageView4;
    private View status_bar_fix;

    @Override
    protected void handlerPassMsg(int target, int target1, Object obj) {

    }

    private RoundedImageView roudimg_head;
    private CardView cardroudimg_head;

    @Override
    public int initLayoutId() {
        return R.layout.act_groupchatdetailnew;
    }

    private View titleBar_Bg;

    @Override
    public void initView() {
        hideHeader();
        setOnClickListener(R.id.groupchat_back);
        ll_Htfb = getView(R.id.ll_hutifbu);
        hader = getView(R.id.pagerHeader);
        title = getView(R.id.tv_groupName);
        tvContext = getView(R.id.tv_groupjianjie);
        setOnClickListener(R.id.jrql);
        setOnClickListener(R.id.emo_back);
        titleBar = findViewById(R.id.titleBar);
        titleBar_Bg = titleBar.findViewById(R.id.bg);
        //当状态栏透明后，内容布局会上移，这里使用一个和状态栏高度相同的view来修正内容区域
        status_bar_fix = titleBar.findViewById(R.id.status_bar_fix);
        status_bar_fix.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, MyUtil.getStatusHeight(this)));
        titleBar_title = titleBar.findViewById(R.id.title);
        roudimg_head = titleBar.findViewById(R.id.roudimg_head);
        //当状态栏透明后，内容布局会上移，这里使用一个和状态栏高度相同的view来修正内容区域
        cardroudimg_head = (CardView) titleBar.findViewById(R.id.cardroudimg_head);
        cardroudimg_head.setAlpha(0);
        status_bar_fix.setAlpha(0);
        titleBar_title.setAlpha(0);
        cardroudimg_head.setAlpha(0);
        tv_groupName = (EmojiconTextView) findViewById(R.id.tv_groupName);
        chengyuan = getView(R.id.hader_chenyuan);
        roundedImageView1 = getView(R.id.had1);
        roundedImageView2 = getView(R.id.had2);
        roundedImageView3 = getView(R.id.had3);
        roundedImageView4 = getView(R.id.had4);
        linTopindicator = getView(R.id.linTopindicator);
        scrollableLayout = getView(R.id.scrollableLayout);
        bean = (DynamicHaderBean.InfoBean.HotChatgroupBean) getActivity().getIntent().getSerializableExtra("bean");
        if (getIntent().getStringExtra("title") != null) {
            Glide.with(context).load("http://bisonchat.com/" + bean.getImage()).into(hader);
            title.setText(bean.getGroupName());
            tvContext.setText(bean.getGroupNote());
        } else {
            title.setText(getIntent().getStringExtra("groupName"));
            Glide.with(context).load(getIntent().getStringExtra("picUrl")).into(hader);
        }
        setOnClickListener(R.id.fbht);
    }

    private EmojiconTextView titleBar_title, tv_groupName;

    @Override
    public void initData() {
        titleBar_Bg.setAlpha(0);
        remen = getViewAndClick(R.id.remen);
        my = getViewAndClick(R.id.my);
        btnList.add(remen);//话题列表，
        btnList.add(my);//我的话题
        fragments = new ArrayList<>();
        fragments.add(new Fgt_GroupHuati());
        fragments.add(new Fgt_MyTopic());
        Glide.with(getApplication()).load("http://bisonchat.com/" + bean.getImage()).into(roudimg_head);
        //tab标签和内容viewpager
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (scrollableLayout != null) {
                    scrollableLayout.setCurrentScrollableContainer(fragments.get(position));
                }
            }
        });
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                linTopindicator.scroll(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    ll_Htfb.setVisibility(View.VISIBLE);
                } else {
                    ll_Htfb.setVisibility(View.GONE);
                }
                if (scrollableLayout != null) {
                    scrollableLayout.setCurrentScrollableContainer(fragments.get(position));
                }
                btnList.get(prePosition).setTextColor(Color.parseColor("#4D4D4D"));
                btnList.get(position).setTextColor(Color.parseColor("#2F60F3"));
                prePosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        scrollableLayout.setOnScrollListener(new HeaderViewPager.OnScrollListener() {
            @Override
            public void onScroll(int currentY, int maxY) {
                //让头部具有差速动画,如果不需要,可以不用设置
                hader.setTranslationY(currentY / 2);
                //动态改变标题栏的透明度,注意转化为浮点型
                float alpha = 1.0f * currentY / maxY;
                titleBar_Bg.setAlpha(alpha);
                //注意头部局的颜色也需要改变
                status_bar_fix.setAlpha(alpha);
                titleBar_title.setAlpha(alpha);
                cardroudimg_head.setAlpha(alpha);
                titleBar_title.setText(bean.getGroupName());
                tv_groupName.setText(bean.getGroupName());
            }
        });
        scrollableLayout.setCurrentScrollableContainer(fragments.get(0));
        postBackHadimage();
    }

    int prePosition = 0;

    @Override
    public void requestData() {

    }

    @Override
    public void updateUI() {

    }

    @Override
    public void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.groupchat_back:
            case R.id.emo_back:
                finish();
                break;
            case R.id.remen:

                break;
            case R.id.my:

                break;
            case R.id.jrql://进入群聊

                if (MyApplication.getCurrentUserInfo().getUserPhone()!=null&&!MyApplication.getCurrentUserInfo().getUserPhone().equals(""))
                {
                    String gropName;
                    if (getIntent().getStringExtra("title") != null) {
                        GroupId = bean.getGroupId();
                        gropName = bean.getGroupName();
                    } else {
                        GroupId = getIntent().getStringExtra("groupId");
                        gropName = getIntent().getStringExtra("groupName");
                    }
                    RongIM.getInstance().startGroupChat(getActivity(), GroupId, gropName);

                    RongIM.getInstance().refreshGroupUserInfoCache(new GroupUserInfo(GroupId,gropName,MyApplication.getCurrentUserInfo().getUserId() ));


                }
                else
                {
                    Fabuhuati();
                }


                break;
            case R.id.fbht://发布话题

                if (MyApplication.getCurrentUserInfo().getUserPhone()!=null&&!MyApplication.getCurrentUserInfo().getUserPhone().equals(""))
                {

                    if (getIntent().getStringExtra("title") != null) {
                        GroupId = bean.getGroupId();
                    } else {
                        GroupId = getIntent().getStringExtra("groupId");
                    }
                    Intent intent = new Intent();
                    intent.setClass(context, Act_TopicPublishing.class);
                    intent.putExtra("group_id", GroupId);
                    startActivityForResult(intent, 10);
                }
                else
                {
                    Fabuhuati();
                }



                break;
        }
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
    }

    private String GroupId;

    public void postBackHadimage() {
        if (getIntent().getStringExtra("title") != null) {
            GroupId = bean.getGroupId();
        } else {
            GroupId = getIntent().getStringExtra("groupId");

        }
        OkHttpUtils.post().url(ComantUtils.MyUrlHot + ComantUtils.ChatgroupUserInfoList)
                .addParams("groupId", GroupId + "")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                e.printStackTrace();

            }

            @Override
            public void onResponse(String response, int id) {
                Gson gons = new Gson();
                ChatGroupUserInfoListBean bean = gons.fromJson(response, ChatGroupUserInfoListBean.class);
                chengyuan.setText(bean.getInfo().getData().size() + "名成员");
                if (bean.getInfo().getData().size() > 0) {
                    roundedImageView1.setVisibility(View.VISIBLE);
                    roundedImageView2.setVisibility(View.GONE);
                    roundedImageView3.setVisibility(View.GONE);
                    roundedImageView4.setVisibility(View.GONE);
                    Glide.with(context).load(ComantUtils.MyUrlImageHader + bean.getInfo().getData().get(0).getHeadImg()).into(roundedImageView1);
                }
                if (bean.getInfo().getData().size() > 1) {
                    roundedImageView1.setVisibility(View.VISIBLE);
                    roundedImageView2.setVisibility(View.VISIBLE);
                    roundedImageView3.setVisibility(View.GONE);
                    roundedImageView4.setVisibility(View.GONE);
                    Glide.with(context).load(ComantUtils.MyUrlImageHader + bean.getInfo().getData().get(1).getHeadImg()).into(roundedImageView2);
                }
                if (bean.getInfo().getData().size() > 2) {
                    roundedImageView1.setVisibility(View.VISIBLE);
                    roundedImageView2.setVisibility(View.VISIBLE);
                    roundedImageView3.setVisibility(View.VISIBLE);
                    roundedImageView4.setVisibility(View.GONE);
                    Glide.with(context).load(ComantUtils.MyUrlImageHader + bean.getInfo().getData().get(2).getHeadImg()).into(roundedImageView3);
                }
                if (bean.getInfo().getData().size() > 3) {
                    roundedImageView1.setVisibility(View.VISIBLE);
                    roundedImageView2.setVisibility(View.VISIBLE);
                    roundedImageView3.setVisibility(View.VISIBLE);
                    roundedImageView4.setVisibility(View.VISIBLE);
                    Glide.with(context).load(ComantUtils.MyUrlImageHader + bean.getInfo().getData().get(3).getHeadImg()).into(roundedImageView4);
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        return super.onKeyDown(keyCode, event);
        finish();
        return true;
    }


    private void Fabuhuati()
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


}
