package com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.fgt.fgt;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lykj.aextreme.afinal.utils.Debug;
import com.lykj.aextreme.afinal.utils.MyToast;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.bean.MessageBean;
import com.maoyongxin.myapplication.common.BaseFgt;
import com.maoyongxin.myapplication.common.MyApplication;
import com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.fgt.act.Act_TopicDetails;
import com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.fgt.adapter.HotiReviewsAdapter;
import com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.fgt.bean.HotReviewsBean;
import com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.fgt.bean.HotReviewsDelteBean;
import com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.fgt.bean.TopicDetailsDianZanBean;
import com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.fgt.bean.UserEvent;
import com.maoyongxin.myapplication.ui.fgt.connection.act.Act_AnnouncementReply;
import com.maoyongxin.myapplication.ui.fgt.min.act.Act_UserEditNew;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.rong.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 热门话题详情=热门评论
 */
public class Fgt_HotReviews extends BaseFgt implements BaseQuickAdapter.OnItemChildClickListener {
    private RecyclerView lv_huati;
    private String id, groupName, parentUserId, Groupid;
    ZLoadingDialog dialog;
    private RelativeLayout noViewData;
    Boolean haddata=false;

    @Override
    public int initLayoutId() {
        return R.layout.fgt_hotreviews;
    }

    @Override
    public void initView() {
        hideHeader();
        EventBus.getDefault().register(this);
        noViewData = getView(R.id.view_not);
        lv_huati = getView(R.id.lv_huati);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        lv_huati.setLayoutManager(linearLayoutManager);
        dialog = new ZLoadingDialog(getContext());
        dialog.setLoadingBuilder(Z_TYPE.ROTATE_CIRCLE)//设置类型
                .setLoadingColor(Color.DKGRAY)//颜色
                .setHintText("数据提交中...")
                .setHintTextSize(16) // 设置字体大小 dp
                .setHintTextColor(Color.DKGRAY)  // 设置字体颜色
                .setDurationTime(0.5) // 设置动画时间百分比 - 0.5倍
                .setDialogBackgroundColor(Color.parseColor("#CCffffff")); // 设置背景色，默认白色
    }

    @Override
    public void initData() {
        id = getActivity().getIntent().getStringExtra("id");
        groupName = getActivity().getIntent().getStringExtra("groupName");
        parentUserId = getActivity().getIntent().getStringExtra("parentUserId");
        Groupid = getActivity().getIntent().getStringExtra("Groupid");
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

    /**
     * 订阅的过程中，默认是在主线程中用到的
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UserEvent event) {
        if (event.getType().equals("0")) {
            if (event.getSatus().equals("1")) {//下拉刷新
                datas.clear();
                page = 1;
                getHuattiList(id);
            } else if (event.getSatus().equals("2")) {//上拉加载更多
                page++;
                getHuattiList(id);
            }
        }

    }

    List<HotReviewsBean.InfoBean.DataBeanX> datas = new ArrayList<>();
    int page = 1;
    HotiReviewsAdapter adapter;

    private void getHuattiList(String id) {
        OkHttpUtils.post()
                .addParams("page", page + "")
                .addParams("gambit_id", id)
                .addParams("parent_id", "0")
                .addParams("myuid", MyApplication.getCurrentUserInfo().getUserId())
                .addParams("per_page", "15")
                .url("http://bisonchat.com/index/chatgroup_gambit/get_respond.html")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        noViewData.setVisibility(View.VISIBLE);
                        lv_huati.setVisibility(View.GONE);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        HotReviewsBean bean = gson.fromJson(response, HotReviewsBean.class);
                        if(bean.getInfo().getData().size()>0 )
                        {

                                 haddata=true;

                                noViewData.setVisibility(View.GONE);
                                lv_huati.setVisibility(View.VISIBLE);




                            for (int i = 0; i < bean.getInfo().getData().size(); i++) {
                                datas.add(bean.getInfo().getData().get(i));
                            }
                            if (adapter == null) {
                                adapter = new HotiReviewsAdapter(getContext(), groupName, parentUserId);
                                adapter.setOnItemChildClickListener(Fgt_HotReviews.this);
                                adapter.setNewData(datas);
                                lv_huati.setAdapter(adapter);
                            } else {
                                adapter.notifyDataSetChanged();
                            }
                        }
                        else if(!haddata)
                        {

                            noViewData.setVisibility(View.VISIBLE);
                            lv_huati.setVisibility(View.GONE);
                        }


                    }
                });
    }

    @Override
    public void sendMsg(int flag, Object obj) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //注销注册
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.item_huifu://回复

                if (MyApplication.getCurrentUserInfo().getUserPhone()!=null&&!MyApplication.getCurrentUserInfo().getUserPhone().equals(""))
                {
                    Intent intent = new Intent();
                    intent.putExtra("title", "HotReviews");
                    intent.putExtra("gambit_id", datas.get(position).getGambit_id());
                    intent.putExtra("group_id", Groupid);
                    intent.putExtra("parent_id", datas.get(position).getId() + "");
                    intent.putExtra("parent_uid", datas.get(position).getUid() + "");
                    intent.setClass(context, Act_AnnouncementReply.class);
                    startActivityForResult(intent, 10);
                }

                else
                {
                    edit_userphone();
                }


                break;
            case R.id.item_delte://删除
                dialog.show();
                delateItem(datas.get(position).getId(), position);
                break;
            case R.id.zanImg://点赞
                view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.zan_anim));
                if (datas.get(position).isPraise()) {
                    CancelLikeApi(position);
                } else {
                    setLikeApi(position);
                }
                break;
        }
    }

    private void delateItem(String id, final int position) {
        OkHttpUtils.post()
                .addParams("id", id)
                .url("http://bisonchat.com/index/chatgroup_gambit/delete_chatgroup_respond")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        dialog.dismiss();
                        Gson gson = new Gson();
                        HotReviewsDelteBean bean = gson.fromJson(response, HotReviewsDelteBean.class);
                        if (bean.getCode() == 200) {
                            MyToast.show(context, bean.getMsg());
                            datas.remove(position);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == 10) {
            datas.clear();
            page = 1;
            getHuattiList(id);
            return;
        }

    }

    /**
     * 取消点赞
     */
    private String data_id;

    private void CancelLikeApi(final int potiong) {
        dialog.show();
        OkHttpUtils.post()
                .addParams("user_id", MyApplication.getCurrentUserInfo().getUserId() + "")
                .addParams("data_id", datas.get(potiong).getId())
                .addParams("data_type", "4")
                .url("http://bisonchat.com/index/praise/setCancelLikeApi.html").build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        dialog.dismiss();
                        Gson gson = new Gson();
                        TopicDetailsDianZanBean bean = gson.fromJson(response, TopicDetailsDianZanBean.class);
                        if (bean.isOperation()) {
                            datas.get(potiong).setPraise(false);
                            datas.get(potiong).setPraise_num(datas.get(potiong).getPraise_num() - 1);
                            adapter.notifyDataSetChanged();
                            MyToast.show(context, bean.getMsg());
                        }
                    }
                });
    }

    /**
     * 点赞
     */
    private void setLikeApi(final int potiong) {
        dialog.show();
        OkHttpUtils.post()
                .addParams("user_id", MyApplication.getCurrentUserInfo().getUserId() + "")
                .addParams("data_id", datas.get(potiong).getId())
                .addParams("data_type", "4")
                .addParams("type", "1")
                .url("http://bisonchat.com/index/praise/setLikeApi.html").build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        dialog.dismiss();
                        Gson gson = new Gson();
                        TopicDetailsDianZanBean bean = gson.fromJson(response, TopicDetailsDianZanBean.class);
                        if (bean.isOperation()) {
                            datas.get(potiong).setPraise_num(datas.get(potiong).getPraise_num() + 1);
                            datas.get(potiong).setPraise(true);
                            adapter.notifyDataSetChanged();
                            MyToast.show(context, bean.getMsg());
                            postPushMessage(datas.get(potiong).getUid());
                        }
                    }
                });
    }

    /**
     * 推送消息
     * alias 指定推送人id
     * content推送消息内容
     */
    public void postPushMessage(String UserId) {
        Debug.e("--------------UserId===" + UserId);
        MessageBean bean = new MessageBean();
        bean.setTitle("点赞消息！");
//        bean.setTitle("回复信息");
        Gson gson = new Gson();
        String android_notification = gson.toJson(bean);
        OkHttpUtils.get().url("http://bisonchat.com/index/jpush/aliasPushApi")
                .addParams("alias", UserId)
                .addParams("content", "您的好友：" + MyApplication.getCurrentUserInfo().getUserName() + "为您点了一个'赞'")
//                .addParams("content", "您的好友：" + MyApplication.getCurrentUserInfo().getUserName() + "为您回复了一个消息")
                .addParams("android_notification", android_notification)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Debug.e("---------------onError==" + e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                Debug.e("---------------onResponse==" + response);
            }
        });
    }


    private void edit_userphone()
    {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());

        builder.setTitle("提示")
                .setMessage("根据《中华人民共和国网络安全法》的相关规定，发布信息需要您进行手机认证，我们将全力保障您的信息安全，除认证认证外，绝不另作他用！")
                .setNegativeButton("暂不认证", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                    }
                })
                .setPositiveButton("手机认证", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivityForResult(Act_UserEditNew.class, MyApplication.GETMY_USERINFO);

                    }
                }).show();
    }
}
