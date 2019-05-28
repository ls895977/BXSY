package com.maoyongxin.myapplication.ui.fgt.community.act;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.common.BaseAct;
import com.maoyongxin.myapplication.ui.fgt.community.adapter.LocationAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 所在位置
 */
public class Act_Location extends BaseAct implements Inputtips.InputtipsListener, BaseQuickAdapter.OnItemClickListener {
    int page = 0;
    private EditText search;
    private RecyclerView myRecyclerView;
    private String curAddr = "";

    @Override
    protected void handlerPassMsg(int target, int target1, Object obj) {

    }

    @Override
    public int initLayoutId() {
        return R.layout.act_location;
    }

    View viewHader;

    @Override
    public void initView() {
        hideHeader();
        curAddr = getIntent().getStringExtra("curAddr");
        viewHader = LayoutInflater.from(this).inflate(R.layout.view_location_hader, null);
        search = getView(R.id.search);
        setOnClickListener(R.id.friends_back);
        setOnClickListener(viewHader, R.id.location_noAddr);
        myRecyclerView = getView(R.id.myRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        myRecyclerView.setLayoutManager(layoutManager);
        adapter = new LocationAdapter(this);
        adapter.setOnItemClickListener(this);
        adapter.addHeaderView(viewHader);
        myRecyclerView.setAdapter(adapter);
    }


    @Override
    public void initData() {
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                datas.clear();
                doSearchQuery(search.getText().toString());
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
            case R.id.friends_back:
                finish();
                break;
            case R.id.location_noAddr://不显示地址
                Intent intent = new Intent();
                intent.putExtra("name","不显示地址");
                setResult(10, intent);
                finish();
                break;
        }
    }

    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery(String keyWord) {
        InputtipsQuery inputquery = new InputtipsQuery(keyWord, curAddr);
        inputquery.setCityLimit(true);
        Inputtips inputTips = new Inputtips(Act_Location.this, inputquery);
        inputTips.setInputtipsListener(this);
        inputTips.requestInputtipsAsyn();
    }

    LocationAdapter adapter;
    List<Tip> datas = new ArrayList<>();

    @Override
    public void onGetInputtips(List<Tip> tipList, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            for (int i = 0; i < tipList.size(); i++) {
                datas.add(tipList.get(i));
            }
            adapter.setNewData(datas);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent();
        intent.putExtra("name",datas.get(position).getName());
        intent.putExtra("addr",datas.get(position).getAddress());
        setResult(10, intent);
        finish();
    }
}
