package com.maoyongxin.myapplication.ui.fgt.min.act;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.common.BaseAct;
import com.maoyongxin.myapplication.common.BaseFgt;
import com.maoyongxin.myapplication.ui.fgt.Fgt_Community;
import com.maoyongxin.myapplication.ui.fgt.min.act.fgt.Fgt_Connections_Coll;
import com.maoyongxin.myapplication.ui.fgt.min.act.fgt.Fgt_company_Coll;
import com.maoyongxin.myapplication.view.ViewPagerDoubleIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的收藏
 */
public class Act_MyCollection extends BaseAct {
    private List<Button> btnList=new ArrayList<>();
    ViewPager viewPager;
    List<BaseFgt> fgtDatas = new ArrayList<>();
    private ViewPagerDoubleIndicator linTopindicator;

    @Override
    protected void handlerPassMsg(int target, int target1, Object obj) {

    }

    @Override
    public int initLayoutId() {
        return R.layout.act_mycollection;
    }

    @Override
    public void initView() {
        hideHeader();
        setOnClickListener(R.id.friends_back);
        linTopindicator = getView(R.id.linTopindicator);
        viewPager = getView(R.id.remmai_viewpager);
        btnList.add((Button)getViewAndClick(R.id.btn_renmai1));
        btnList.add((Button) getViewAndClick(R.id.btn_renmai_list1));
        fgtDatas.add(new Fgt_Connections_Coll());
        fgtDatas.add(new Fgt_company_Coll());

    }

    private int prePosition = 0;

    @Override
    public void initData() {
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                linTopindicator.scroll(position, positionOffset);
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
    }

    @Override
    public void requestData() {

    }

    @Override
    public void updateUI() {

    }

    @Override
    public void onViewClick(View v) {
            switch (v.getId()){
                case R.id.friends_back:
                    finish();
                    break;
            }
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return fgtDatas.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fgtDatas.get(position);
        }
    }


}
