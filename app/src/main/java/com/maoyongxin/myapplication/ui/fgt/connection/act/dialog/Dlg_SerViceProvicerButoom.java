package com.maoyongxin.myapplication.ui.fgt.connection.act.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.lykj.aextreme.afinal.common.BaseDialog;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.common.MyApplication;
import com.maoyongxin.myapplication.tool.LocationTool;

import butterknife.OnClick;

public class Dlg_SerViceProvicerButoom extends BaseDialog implements DialogInterface.OnClickListener ,LocationTool.onBackLoCtion{
    OnClick onClick;

    private String AreaCode="510100";
    private String data="1";
    private String Provice="510000";
    private   LocationTool locationTool;
    @Override
    public void backAdd(AMapLocation location) {

        Provice=location.getProvince();
        AreaCode=location.getCity();
        locationTool.stopLocation();
    }

    private  OnClickListenning onClickListenning;
    public Dlg_SerViceProvicerButoom(Context context,OnClickListenning onClickListenning) {
        super(context);
        this.onClickListenning=onClickListenning;

    }
    TextView all_area, my_provice, mycity, all_day,there_day,today;

    @Override
    protected int initLayoutId() {
        return R.layout.dlg_serviceprovicerbutoom;
    }

    @Override
    protected void initWindow() {
        windowDeploy(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
    }

    @Override
    protected void initView() {
        setOnClickListener(R.id.cancer);
        setOnClickListener(R.id.center);
        setOnClickListener(R.id.all_area);
        all_area = getViewAndClick(R.id.all_area);



        my_provice = getViewAndClick(R.id.my_provice);
        mycity = getViewAndClick(R.id.mycity);

        mycity.setSelected(true);

        all_day = getViewAndClick(R.id.all_day);

        there_day = getViewAndClick(R.id.there_day);

        today = getViewAndClick(R.id.today);
        today.setSelected(true);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.cancer:
                dismiss();
                break;
            case R.id.center://确认

                RtnAreacode();
                dismiss();


                break;
            case R.id.mycity:
                mycity.setSelected(true);
                my_provice.setSelected(false);
                all_area.setSelected(false);
                AreaCode=AreaCode;

                break;
            case R.id.my_provice:

                mycity.setSelected(false);
                my_provice.setSelected(true);
                all_area.setSelected(false);

                AreaCode=Provice;

                break;
            case R.id.all_area:

                mycity.setSelected(false);
                my_provice.setSelected(false);
                all_area.setSelected(true);
                AreaCode="";
                break;
            case R.id.today:


                today.setSelected(true);
                there_day.setSelected(false);
                all_day.setSelected(false);
                data="1";


                break;

            case R.id.there_day:

                today.setSelected(false);
                there_day.setSelected(true);
                all_day.setSelected(false);
                data="2";

                break;

            case R.id.all_day:

                today.setSelected(false);
                there_day.setSelected(false);
                all_day.setSelected(true);
                data="3";

                break;
        }
    }



    public interface OnClickListenning{

         public void getAreaCode(String AreaCode, String data);


    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

        onClickListenning.getAreaCode(AreaCode,data);


    }

    private void RtnAreacode()
    {


        onClickListenning.getAreaCode(AreaCode,data);


    }
}
