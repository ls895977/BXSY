package com.maoyongxin.myapplication.ui.actadapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.maoyongxin.myapplication.common.BaseFgt;

import java.util.ArrayList;
import java.util.List;

/**
 * 主界面adpater
 */
public class MainPagerAdapter extends FragmentPagerAdapter {
    private List<BaseFgt> fgtData;

    public MainPagerAdapter(FragmentManager fm, List<BaseFgt> fgtData1) {
        super(fm);
        this.fgtData = fgtData1;
    }

    @Override
    public Fragment getItem(int position) {
        return fgtData.get(position);
    }

    @Override
    public int getCount() {
        return fgtData.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
    }
}
