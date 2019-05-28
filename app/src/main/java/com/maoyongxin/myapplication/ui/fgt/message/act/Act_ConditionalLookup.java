package com.maoyongxin.myapplication.ui.fgt.message.act;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lykj.aextreme.afinal.utils.MyToast;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.common.BaseAct;
import com.maoyongxin.myapplication.tool.TimeAddTool;

import java.util.ArrayList;
import java.util.List;

/**
 * 条件查找好友
 */
public class Act_ConditionalLookup extends BaseAct implements TimeAddTool.onBackAddr, TimeAddTool.onBackItem2 {
    private TextView[] choseSex = new TextView[3];
    private TextView tvAddr, tv_age;
    private EditText search, company_name, position;

    @Override
    protected void handlerPassMsg(int target, int target1, Object obj) {

    }

    @Override
    public int initLayoutId() {
        return R.layout.act_conditionallookup;
    }

    @Override
    public void initView() {
        hideHeader();
        setOnClickListener(R.id.friends_back);
        setOnClickListener(R.id.findButton);
        choseSex[0] = getViewAndClick(R.id.tv1);
        choseSex[1] = getViewAndClick(R.id.tv2);
        choseSex[2] = getViewAndClick(R.id.tv3);
        setOnClickListener(R.id.choseCity);
        setOnClickListener(R.id.chose_age);
        tvAddr = getView(R.id.tv_add);
        tv_age = getView(R.id.tv_age);
        search = getView(R.id.search);
        company_name = getView(R.id.company_name);
        position = getView(R.id.position);
        setSex(0);
    }

    @Override
    public void initData() {


    }

    @Override
    public void requestData() {

    }

    @Override
    public void updateUI() {

    }

    private String sex = "1";
    private List<String> age1 = new ArrayList<>();
    private List<String> age2 = new ArrayList<>();
    private String permanent_city="";

    @Override
    public void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.friends_back:
                finish();
                break;
            case R.id.tv1:
                setSex(0);
                sex = "1";
                break;
            case R.id.tv2:
                setSex(1);
                sex = "2";
                break;
            case R.id.tv3:
                setSex(2);
                sex = "0";
                break;
            case R.id.choseCity:
                new TimeAddTool().showPickerView(this, Act_ConditionalLookup.this);
                break;
            case R.id.chose_age:
                for (int i = 1; i < 100; i++) {
                    age1.add(i + "");
                    age2.add(i + "");
                }
                new TimeAddTool().initNoLinkOptionsPicker(this, age1, age2, Act_ConditionalLookup.this);
                break;
            case R.id.findButton://查找
                if (TextUtils.isEmpty(search.getText().toString())) {
                    MyToast.show(context, "请输入您要查找的昵称或账号");
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("search", search.getText().toString());
                intent.putExtra("company_name", company_name.getText().toString());
                intent.putExtra("sex", sex);
                intent.putExtra("permanent_city", permanent_city);
                intent.putExtra("position", position.getText().toString());
                setResult(10, intent);
                finish();
                break;
        }
    }

    /**
     * 设置性别选择状态
     */
    private int intSex=1;

    public void setSex(int idnext) {
        choseSex[idnext].setSelected(true);
        choseSex[intSex].setSelected(false);
        intSex = idnext;
    }

    @Override
    public void backAddr(String options1, String options2, String options3) {
        tvAddr.setText(options1 + options2 + options3);
        permanent_city = options1 + options2 + options3;
    }

    @Override
    public void backItem(int options, int options1) {
        tv_age.setText(age1.get(options) + "-" + age2.get(options1));
    }
}
