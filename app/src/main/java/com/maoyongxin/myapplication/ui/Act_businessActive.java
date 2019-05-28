package com.maoyongxin.myapplication.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.common.BaseAct;
import com.maoyongxin.myapplication.common.BaseFgt;
import com.maoyongxin.myapplication.ui.actadapter.MainPagerAdapter;
import com.maoyongxin.myapplication.ui.fgt.community.fgt.Fgt_businessact;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.BezierPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.WrapPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Act_businessActive extends BaseAct {


    private static final String[] CHANNELS = new String[]{"精选", "发布会","行业峰会", "培训","政企对接" };
    private List<String> mDataList = Arrays.asList(CHANNELS);
    private ViewPager mViewPager;
    private MainPagerAdapter mainPagerAdapter;
    List<BaseFgt> fgtDatas = new ArrayList<>();
    private TextView business_mine;

    @Override
    public void initData() {

        initMagicIndicator8();

    }

    @Override
    public void requestData() {

    }

    @Override
    public void updateUI() {

    }

    @Override
    public void onViewClick(View v) {

        switch (v.getId())
        {
            case R.id.business_mine:

                startActivity(new Intent(getActivity(),act_fb_business.class));

                break;
        }

    }

    @Override
    public void initView() {
        mViewPager=getView(R.id.view_pager);
        business_mine=getViewAndClick(R.id.business_mine);



        for(int i=0;i<CHANNELS.length;i++)
        {
            fgtDatas.add(new Fgt_businessact());
        }

        mainPagerAdapter=  new MainPagerAdapter(getSupportFragmentManager(), fgtDatas);
        mViewPager.setAdapter(mainPagerAdapter);

    }

    @Override
    public int initLayoutId() {
        return R.layout.activity_act_business_active;
    }

    @Override
    protected void handlerPassMsg(int target, int target1, Object obj) {

    }
    private void initMagicIndicator8() {


        MagicIndicator magicIndicator = (MagicIndicator) findViewById(R.id.lindicator);
        magicIndicator.setBackgroundColor(Color.WHITE);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setTextSize(18);
                simplePagerTitleView.setNormalColor(Color.GRAY);
                simplePagerTitleView.setSelectedColor(Color.BLACK);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                BezierPagerIndicator indicator = new BezierPagerIndicator(context);
                indicator.setColors(Color.parseColor("#ff4a42"), Color.parseColor("#fcde64"), Color.parseColor("#73e8f4"), Color.parseColor("#76b0ff"), Color.parseColor("#c683fe"));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);

    }


}
