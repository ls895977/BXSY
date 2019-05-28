package com.maoyongxin.myapplication.ui.fgt.connection.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.maoyongxin.myapplication.ui.fgt.connection.act.fgt.Fgt_SerViceProvider;

import java.util.ArrayList;

/**
 *
 */


public class SerViceProviderPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> list;
    private ArrayList<String> titleList;

    public SerViceProviderPagerAdapter(FragmentManager fm) {
        super(fm);
        list = new ArrayList<>();
        Fgt_SerViceProvider fragment = new Fgt_SerViceProvider();
        Bundle args = new Bundle();
        args.putString("type", "1");
        fragment.setArguments(args);
        list.add(fragment);
        Fgt_SerViceProvider fragment1 = new Fgt_SerViceProvider();
        Bundle args1 = new Bundle();
        args1.putString("type", "0");
        fragment1.setArguments(args1);
        list.add(fragment1);
        titleList = new ArrayList<>();
        titleList.add("服务号");
        titleList.add("个人");
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }


}
