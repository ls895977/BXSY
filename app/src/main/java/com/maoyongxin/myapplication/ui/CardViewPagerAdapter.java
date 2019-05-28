package com.maoyongxin.myapplication.ui;

import android.app.FragmentTransaction;
import android.content.Context;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.lykj.aextreme.afinal.utils.MyToast;
import com.maoyongxin.myapplication.tool.FragmentCallBack;
import com.maoyongxin.myapplication.ui.fgt.community.act.Act_JournalismDetails;
import com.zhouyou.http.callback.CallBack;


import java.util.List;

import javax.security.auth.callback.Callback;

/**
 * Created by liushaochen on 2019/3/4.
 */
public class CardViewPagerAdapter extends FragmentPagerAdapter    {
    private   Context context;
    private FragmentManager mManager;  //创建FragmentManager
    private List<CardFragment> mList; //创建一个List<Fragment>
    private int flag = 0;
    private FragmentCallBack fragmentCallBack;

    //定义构造带两个参数
    public CardViewPagerAdapter(FragmentManager fm, List<CardFragment> list,FragmentCallBack fragmentCallBack , int flag,Context context) {
        super(fm);
        this.mManager = fm;
        this.mList = list;
        this.fragmentCallBack=fragmentCallBack;
        this.flag=flag;
        this.context=context;
    }

    @Override
    public Fragment getItem(int arg0) {
        if (mList == null || mList.size() == 0) {
            return null;
        }

        return mList.get(arg0); //返回第几个fragment
    }



    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return "title";
    }

    @Override
    public int getCount() {
        if (mList == null) {
            return 0;
        }
        return mList.size(); //总共有多少个fragment
    }

}
