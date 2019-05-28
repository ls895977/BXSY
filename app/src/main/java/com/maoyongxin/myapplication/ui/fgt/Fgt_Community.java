package com.maoyongxin.myapplication.ui.fgt;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.common.BaseFgt;
import com.maoyongxin.myapplication.ui.ExpressCardActivity;
import com.maoyongxin.myapplication.ui.fgt.community.fgt.Fgt_Dynamic;
import com.maoyongxin.myapplication.ui.fgt.community.fgt.Fgt_Express_news;
import com.maoyongxin.myapplication.ui.fgt.community.fgt.Fgt_Journalism;

import com.maoyongxin.myapplication.view.ViewPagerDoubleIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * 社区
 */
public class Fgt_Community extends BaseFgt {

    List<BaseFgt> fgtDatas = new ArrayList<>();

    private ViewPagerDoubleIndicator linTopindicator;
    ViewPager viewPager;
    private List<Button> btnList;
    private Button btn_renmai_list;
    private Button btn_renmai;





    @Override
    public int initLayoutId() {
        return R.layout.fgt_community;
    }

    @Override
    public void initView() {
        hideHeader();
        linTopindicator = getView(R.id.linTopindicator);
        viewPager = getView(R.id.remmai_viewpager);
        btn_renmai_list = getViewAndClick(R.id.btn_renmai_list);
        btn_renmai = getViewAndClick(R.id.btn_renmai);
    }

    @Override
    public void initData() {


       fgtDatas.add(new Fgt_Dynamic());
       fgtDatas.add(new Fgt_Express_news());


        btnList = new ArrayList<>();
        btnList.add(btn_renmai);
        btnList.add(btn_renmai_list);
        btnList.get(0).setTextColor(Color.parseColor("#4D4D4D"));
        btnList.get(0).setTextColor(Color.parseColor("#2F60F3"));
        viewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
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
    private int prePosition = 0;
    @Override
    public void requestData() {

    }

    @Override
    public void updateUI() {

    }

    @Override
    public void onViewClick(View v) {
            switch (v.getId()){
                case R.id.btn_renmai:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.btn_renmai_list:
                    viewPager.setCurrentItem(1);
                    break;

            }
    }

    @Override
    public void sendMsg(int flag, Object obj) {

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
