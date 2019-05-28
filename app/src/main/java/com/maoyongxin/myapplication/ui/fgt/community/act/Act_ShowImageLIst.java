package com.maoyongxin.myapplication.ui.fgt.community.act;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.common.BaseAct;
import com.maoyongxin.myapplication.ui.fgt.community.adapter.VpAdapter;
import com.maoyongxin.myapplication.ui.fgt.community.fgt.Fgt_Item;

import java.util.ArrayList;

public class Act_ShowImageLIst extends BaseAct {
    private ArrayList<String> arrayimgs;
    private ViewPager viewPager;
    private int choseMoren = 0;

    @Override
    protected void handlerPassMsg(int target, int target1, Object obj) {

    }

    @Override
    public int initLayoutId() {
        return R.layout.act_showimagelist;
    }

    @Override
    public void initView() {
        hideHeader();
        viewPager = getView(R.id.img_switcher);
        setOnClickListener(R.id.myImageBack);
    }

    @Override
    public void initData() {
        choseMoren = getIntent().getIntExtra("idnext",0);
        arrayimgs = getIntent().getStringArrayListExtra("imagList");
        ArrayList<Fragment> list = new ArrayList<>();
        for (int i = 0; i < arrayimgs.size(); i++) {
            Fgt_Item itemFragment = new Fgt_Item();
            Bundle bundle = new Bundle();
            bundle.putString("key", arrayimgs.get(i));
            itemFragment.setArguments(bundle);
            list.add(itemFragment);
        }
        VpAdapter vpAdapter = new VpAdapter(getSupportFragmentManager(), list);
        viewPager.setAdapter(vpAdapter);
        viewPager.setCurrentItem(choseMoren);
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
            case R.id.myImageBack:
                finish();
                break;
        }
    }
}
