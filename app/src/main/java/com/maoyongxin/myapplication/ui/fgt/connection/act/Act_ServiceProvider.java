package com.maoyongxin.myapplication.ui.fgt.connection.act;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.common.BaseAct;
import com.maoyongxin.myapplication.ui.fgt.connection.act.dialog.Dlg_SerViceProvicerButoom;
import com.maoyongxin.myapplication.ui.fgt.connection.adapter.SerViceProviderPagerAdapter;
import com.maoyongxin.myapplication.ui.fgt.min.act.Act_AddressHomeCheck;
import com.maoyongxin.myapplication.view.ViewPagerDoubleIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * 人脉-服务商
 */
public class Act_ServiceProvider extends BaseAct {
    private ViewPagerDoubleIndicator linTopindicator;
    ViewPager viewPager;
    private Button btn_renmai_list;
    private Button btn_renmai;
    private List<Button> btnList;
    private Dlg_SerViceProvicerButoom screenDlg;
    private TextView service_tv_help;
    //服务号列表
    @Override
    protected void handlerPassMsg(int target, int target1, Object obj) {

    }

    @Override
    public int initLayoutId() {
        return R.layout.act_serviceprovider;
    }

    @Override
    public void initView() {
        hideHeader();
        screenDlg = new Dlg_SerViceProvicerButoom(this, new Dlg_SerViceProvicerButoom.OnClickListenning() {
            @Override
            public void getAreaCode(String AreaCode, String data) {

            }
        });
                setOnClickListener(R.id.ServiceProvider_back);
        setOnClickListener(R.id.ServiceProvider_seacher);
        setOnClickListener(R.id.ServiceProvider_screen);
        service_tv_help=getViewAndClick(R.id.service_tv_help);
        linTopindicator = getView(R.id.linTopindicator);
        viewPager = findViewById(R.id.view_pager);
        btn_renmai_list = getViewAndClick(R.id.btn_renmai_list);
        btn_renmai = getViewAndClick(R.id.btn_renmai);
        btnList = new ArrayList<>();
        btnList.add(btn_renmai);
        btnList.add(btn_renmai_list);
        btnList.get(0).setTextColor(Color.parseColor("#4D4D4D"));
        btnList.get(0).setTextColor(Color.parseColor("#2F60F3"));
    }

    private int prePosition = 0;

    @Override
    public void initData() {
        viewPager.setAdapter(new SerViceProviderPagerAdapter(getSupportFragmentManager()));
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
        switch (v.getId()) {
            case R.id.ServiceProvider_back:
                finish();
                break;
            case R.id.ServiceProvider_seacher://搜索
                startAct(Act_ServiceSearch.class);
                break;
            case R.id.ServiceProvider_screen://筛选
                screenDlg.show();

                break;
            case R.id.btn_renmai:
                viewPager.setCurrentItem(0);
                break;
            case R.id.btn_renmai_list:
                viewPager.setCurrentItem(1);
                break;


            case R.id.service_tv_help:
                context.startActivity(new Intent(context, Act_AddressHomeCheck.class));
                break;

        }
    }
}
