package com.maoyongxin.myapplication.ui.fgt.message.act;

import android.view.View;

import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.common.BaseAct;
import com.maoyongxin.myapplication.common.MyApplication;
import com.maoyongxin.myapplication.http.entity.allGroupinfo;
import com.maoyongxin.myapplication.ui.fgt.message.adapter.GridView_allgrouplist_adapter;
import com.maoyongxin.myapplication.view.AntGrideVIew;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 消息=通讯录=我的群聊=查看更多的群组
 */
public class Act_AllGroupList extends BaseAct {
    AntGrideVIew gvAllgrouplist;
    private GridView_allgrouplist_adapter gridView_allgrouplist_adapter;
    private List<allGroupinfo> grouplist = new ArrayList<>();

    @Override
    protected void handlerPassMsg(int target, int target1, Object obj) {

    }

    @Override
    public int initLayoutId() {
        return R.layout.act_allgrouplist;
    }

    @Override
    public void initView() {
        hideHeader();
        gvAllgrouplist = getView(R.id.gv_allgrouplist);
    }

    @Override
    public void initData() {
        getallGroupList();
    }

    @Override
    public void requestData() {

    }

    @Override
    public void updateUI() {

    }

    @Override
    public void onViewClick(View v) {

    }

    private void getallGroupList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpUtils.get().url("http://st.3dgogo.com/index/chatgroup/get_all_chatgroup_api.html")
                        .addParams("userid", MyApplication.getCurrentUserInfo().getUserId())
                        .build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }
                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject data = new JSONObject(response);

                            JSONArray datadetail = data.getJSONArray("info");

                            for (int i = 0; i < datadetail.length(); i++) {
                                allGroupinfo datainfo = new allGroupinfo();
                                JSONObject newList = datadetail.getJSONObject(i);
                                datainfo.setGroupId(newList.getString("groupId"));
                                datainfo.setGroupName(newList.getString("groupName"));
                                datainfo.setImage(newList.getString("image"));
                                datainfo.setGroupNote(newList.getString("groupNote"));
                                grouplist.add(datainfo);
                            }
                            gridView_allgrouplist_adapter = new GridView_allgrouplist_adapter(getActivity(), grouplist);
                            gvAllgrouplist.setAdapter(gridView_allgrouplist_adapter);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                });


            }
        }).start();
    }
}
