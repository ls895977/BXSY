package com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.fgt.fgt;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lykj.aextreme.afinal.utils.MyToast;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.common.BaseFgt;
import com.maoyongxin.myapplication.common.MyApplication;
import com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.fgt.act.Act_TopicDetails;
import com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.fgt.adapter.HotiReviewsAdapter;
import com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.fgt.bean.HotReviewsBean;
import com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.fgt.bean.HotReviewsDelteBean;
import com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.fgt.fgt.adapter.MyHtAdapter;
import com.maoyongxin.myapplication.ui.fgt.community.bean.DynamicHaderBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 热门话题详情=我的评论
 */
public class Fgt_MyHt extends BaseFgt implements BaseQuickAdapter.OnItemChildClickListener {
    private RecyclerView lv_huati;
    ZLoadingDialog dialog;
    private RelativeLayout notViewData;

    @Override
    public int initLayoutId() {
        return R.layout.fgt_myht;
    }

    @Override
    public void initView() {
        hideHeader();
        notViewData = getView(R.id.view_not);
        lv_huati = getView(R.id.lv_huati);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        lv_huati.setLayoutManager(mLinearLayoutManager);
        lv_huati.setItemAnimator(null);
        lv_huati.setHasFixedSize(true);
        lv_huati.setNestedScrollingEnabled(false);
        dialog = new ZLoadingDialog(getContext());
        dialog.setLoadingBuilder(Z_TYPE.ROTATE_CIRCLE)//设置类型
                .setLoadingColor(Color.DKGRAY)//颜色
                .setHintText("数据提交中...")
                .setHintTextSize(16) // 设置字体大小 dp
                .setHintTextColor(Color.DKGRAY)  // 设置字体颜色
                .setDurationTime(0.5) // 设置动画时间百分比 - 0.5倍
                .setDialogBackgroundColor(Color.parseColor("#CCffffff")); // 设置背景色，默认白色

    }

    private static String id, Groupname, groupimg, Groupid, parentUserId;
    DynamicHaderBean.InfoBean.HotChatgroupBean bean;

    @Override
    public void initData() {
        bean = (DynamicHaderBean.InfoBean.HotChatgroupBean) getActivity().getIntent().getSerializableExtra("bean");
        id = getActivity().getIntent().getStringExtra("id");
        Groupname = getActivity().getIntent().getStringExtra("groupName");
        groupimg = getActivity().getIntent().getStringExtra("picUrl");
        Groupid = getActivity().getIntent().getStringExtra("Groupid");
        parentUserId = getActivity().getIntent().getStringExtra("parentUserId");
        getHuattiList(id);
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

    @Override
    public void sendMsg(int flag, Object obj) {

    }

    int page = 1;
    Gson gson = new Gson();
    private MyHtAdapter adapter;
    List<HotReviewsBean.InfoBean.DataBeanX> datas = new ArrayList<>();

    private void getHuattiList(final String Id) {
        OkHttpUtils.post()
                .addParams("page", page + "")
                .addParams("gambit_id", Id)
                .addParams("uid", MyApplication.getCurrentUserInfo().getUserId())
                .url("http://bisonchat.com/index/chatgroup_gambit/getUidGambitPostList").build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        notViewData.setVisibility(View.VISIBLE);
                        lv_huati.setVisibility(View.GONE);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        HotReviewsBean bean = gson.fromJson(response, HotReviewsBean.class);
                        for (int i = 0; i < bean.getInfo().getData().size(); i++) {
                            datas.add(bean.getInfo().getData().get(i));
                        }
                        if (adapter == null) {
                            adapter = new MyHtAdapter(getContext(), Groupname, parentUserId);
                            adapter.setOnItemChildClickListener(Fgt_MyHt.this);
                            adapter.setNewData(datas);
                            adapter.setShowis(false);
                            lv_huati.setAdapter(adapter);
                        } else {
                            adapter.notifyDataSetChanged();
                        }
                        if (datas.size() == 0) {
                            notViewData.setVisibility(View.VISIBLE);
                            lv_huati.setVisibility(View.GONE);
                        }
                    }
                });
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.item_huifu://回复
                MyToast.show(context, "您不能对自己进行回复！");
                break;
            case R.id.item_delte://删除
                dialog.show();
//                delateItem(datas.get(position).getId(), position);
                break;
        }
    }

//    private void delateItem(String id, final int position) {
//        OkHttpUtils.post()
//                .addParams("id", id)
//                .url("http://bisonchat.com/index/chatgroup_gambit/delete_chatgroup_respond")
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        dialog.dismiss();
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                        dialog.dismiss();
//                        Gson gson = new Gson();
//                        HotReviewsDelteBean bean = gson.fromJson(response, HotReviewsDelteBean.class);
//                        if (bean.getCode() == 200) {
//                            MyToast.show(context, bean.getMsg());
//                            datas.remove(position);
//                            adapter.notifyDataSetChanged();
//                        }
//                    }
//                });
//    }

}
