package com.maoyongxin.myapplication.ui.fgt.message.act.strangerdetail.fgt;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lykj.aextreme.afinal.utils.Debug;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.bean.MessageBean;
import com.maoyongxin.myapplication.common.BaseFgt;
import com.maoyongxin.myapplication.common.ComantUtils;
import com.maoyongxin.myapplication.common.MyApplication;
import com.maoyongxin.myapplication.http.callback.EntityCallBack;
import com.maoyongxin.myapplication.http.entity.MyDynamicListInfo;
import com.maoyongxin.myapplication.http.request.ReqMyDynamicList;
import com.maoyongxin.myapplication.http.response.MyDongTaiListResponse;
import com.maoyongxin.myapplication.ui.fgt.community.act.Act_DynamicDetail;
import com.maoyongxin.myapplication.ui.fgt.community.act.Act_ShowImageLIst;
import com.maoyongxin.myapplication.ui.fgt.community.act.Act_Video;
import com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.fgt.fgt.HeaderViewPagerFragment;
import com.maoyongxin.myapplication.ui.fgt.community.adapter.DynamicAdapter;
import com.maoyongxin.myapplication.ui.fgt.community.bean.DynamicBase;
import com.maoyongxin.myapplication.ui.fgt.community.bean.DynamicBean;
import com.maoyongxin.myapplication.ui.fgt.community.bean.UserFriendsFollowApiBean;
import com.maoyongxin.myapplication.ui.fgt.message.act.Act_StrangerDetail;
import com.maoyongxin.myapplication.ui.fgt.message.act.strangerdetail.fgt.adapter.DongtaiAdapter;
import com.maoyongxin.myapplication.ui.fgt.message.act.strangerdetail.fgt.adapter.SquareAdapter;
import com.maoyongxin.myapplication.view.mylistview.LoadListView;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * 个人——动态-列表
 */
public class DongtaiFragment extends HeaderViewPagerFragment implements BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemClickListener {
    SmartRefreshLayout mRefreshLayout;
    RecyclerView myRecyclerView;

    @Override
    public int initLayoutId() {
        return R.layout.fgt_dongtai;
    }

    private RelativeLayout nodataview;

    @Override
    public void initView() {
        hideHeader();
        nodataview = getView(R.id.view_not);
//        mRefreshLayout = getView(R.id.refreshLayout);
        myRecyclerView = getView(R.id.recyclerView);
    }

    ZLoadingDialog dialog;

    @Override
    public void initData() {
        dialog = new ZLoadingDialog(context);
        dialog.setLoadingBuilder(Z_TYPE.ROTATE_CIRCLE)//设置类型
                .setLoadingColor(Color.DKGRAY)//颜色
                .setHintText("数据加载中...")
                .setHintTextSize(16) // 设置字体大小 dp
                .setHintTextColor(Color.DKGRAY)  // 设置字体颜色
                .setDurationTime(0.5) // 设置动画时间百分比 - 0.5倍
                .setDialogBackgroundColor(Color.parseColor("#CCffffff")); // 设置背景色，默认白色
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        myRecyclerView.setLayoutManager(layoutManager);
        postBackDtata();
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

    /**
     * 获取个人动态数据
     */
    private DongtaiAdapter adapter;
    private int page = 1;
    List<DynamicBase> datas = new ArrayList<>();

    public void postBackDtata() {
        OkHttpUtils.get().url(ComantUtils.MyUrl + ComantUtils.getUidUserDynamicListApi)
                .addParams("myuid", getActivity().getIntent().getStringExtra("personId"))
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                nodataview.setVisibility(View.VISIBLE);
                myRecyclerView.setVisibility(View.GONE);
            }

            @Override
            public void onResponse(String response, int id) {
                Gson gson = new Gson();
                DynamicBean bean = gson.fromJson(response, DynamicBean.class);
                if (bean.getCode() != 200) {
                    return;
                }
                for (int i = 0; i < bean.getInfo().getData().size(); i++) {
                    if (bean.getInfo().getData().get(i).getImg().size() == 2 || bean.getInfo().getData().get(i).getImg().size() == 4) {
                        setDataBean(bean.getInfo().getData().get(i), DynamicBase.RIGHT_BIG);
                    } else if (bean.getInfo().getData().get(i).getImg().size() <= 1) {
                        setDataBean(bean.getInfo().getData().get(i), DynamicBase.LEFT_BIG);
                    } else {
                        setDataBean(bean.getInfo().getData().get(i), DynamicBase.THREE_SMALL);
                    }
                }
                adapter.setOnItemClickListener(DongtaiFragment.this);
                adapter.setOnItemChildClickListener(DongtaiFragment.this);
                adapter.notifyDataSetChanged();
                if (datas.size() == 0) {
                    nodataview.setVisibility(View.VISIBLE);
                    myRecyclerView.setVisibility(View.GONE);
                }
            }
        });
        // 默认提供5种方法（渐显、缩放、从下到上，从左到右、从右到左）
        adapter = new DongtaiAdapter(datas, context);
        myRecyclerView.setAdapter(adapter);
    }

    /**
     * 设置数据
     *
     * @param bean
     * @param type
     */
    public void setDataBean(DynamicBean.InfoBean.DataBean bean, int type) {
        DynamicBase databen = new DynamicBase(type);
        databen.setAttention(bean.isAttention());
        databen.setPraise(bean.isPraise());
        databen.setDynamicId(bean.getDynamicId());
        databen.setCreateTime(bean.getCreateTime());
        databen.setDynamicContent(bean.getDynamicContent());
        databen.setUserId(bean.getUserId());
        databen.setPraiseNum(bean.getPraiseNum());
        databen.setTreadNum(bean.getTreadNum());
        databen.setCommentNum(bean.getCommentNum());
        databen.setIs_praise_tread(bean.getIs_praise_tread());
        databen.setUserName(bean.getUserName());
        databen.setHeadImg(bean.getHeadImg());
        databen.setVideo(bean.getVideo());
        databen.setCommunityUrl(bean.getCommunityUrl());
        if (bean.getImg().size() != 0) {
            databen.setImg(bean.getImg());
        }
        datas.add(databen);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.ll_zan://点赞
                if (datas.get(position).isPraise()) {
                    setCancelLikeApi(position, MyApplication.getCurrentUserInfo().getUserId(), datas.get(position).getDynamicId());
                } else {
                    dianzan(position, MyApplication.getCurrentUserInfo().getUserId(), datas.get(position).getDynamicId());
                }
                break;
            case R.id.id2://评论
                Intent intent2 = new Intent();
                intent2.putExtra("dynamicId", datas.get(position).getDynamicId() + "");
                intent2.putExtra("communityName", datas.get(position).getCommunityName());
                intent2.putExtra("community_id", datas.get(position).getDynamicId() + "");
                intent2.putExtra("parentUserId", datas.get(position).getUserId() + "");
                intent2.putExtra("Is_anymit", datas.get(position).getIs_anymit() + "");
                startAct(intent2, Act_DynamicDetail.class);
                break;
            case R.id.all_view://点击大图或视频
                if (datas.get(position).getVideo().equals("")) {
                    Intent intent1 = new Intent();
                    intent1.putStringArrayListExtra("imagList", (ArrayList<String>) datas.get(position).getImg());
                    startAct(intent1, Act_ShowImageLIst.class);
                } else {
                    Intent intent1 = new Intent();
                    intent1.putExtra("type", 2);
                    intent1.putExtra("resource", datas.get(position).getVideo());
                    if (datas.get(position).getImg() != null) {
                        intent1.putExtra("thumb", datas.get(position).getImg().get(0));
                    }
                    intent1.putExtra("title", datas.get(position).getUserName());
                    startAct(intent1, Act_Video.class);
                }
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    String LikeApiType = "1";

    /**
     * 取消点赞
     * data_type  1动态点赞 2动态评论点赞  3话题点赞 4话题评论点赞
     */
    private void setCancelLikeApi(final int position, String user_id, String data_id) {//点赞
        dialog.show();
        OkHttpUtils.post().url(ComantUtils.MyUrlHot + ComantUtils.setCancelLikeApi)
                .addParams("user_id", user_id)
                .addParams("data_id", data_id)
                .addParams("data_type", "1")
                .addParams("type", LikeApiType)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                dialog.dismiss();
                Debug.e("----onError-" + call.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                dialog.dismiss();
                Gson gson = new Gson();
                UserFriendsFollowApiBean bean = gson.fromJson(response, UserFriendsFollowApiBean.class);
                if (bean.getCode() == 200) {
                    datas.get(position).setPraise(false);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * 点赞
     * data_type  1动态点赞 2动态评论点赞  3话题点赞 4话题评论点赞
     */
    private void dianzan(final int position, String user_id, String data_id) {//点赞
        dialog.show();
        OkHttpUtils.post().url(ComantUtils.MyUrlHot + ComantUtils.setLikeApi)
                .addParams("user_id", user_id)
                .addParams("data_id", data_id)
                .addParams("data_type", "1")
                .addParams("type", LikeApiType)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                dialog.dismiss();
            }

            @Override
            public void onResponse(String response, int id) {
                dialog.dismiss();
                Gson gson = new Gson();
                UserFriendsFollowApiBean bean = gson.fromJson(response, UserFriendsFollowApiBean.class);
                if (bean.getCode() == 200) {
                    datas.get(position).setPraise(true);
                    adapter.notifyDataSetChanged();
                    postPushMessage(position);
                }
            }
        });
    }

    @Override
    public View getScrollableView() {
        return myRecyclerView;
    }

    /**
     * 推送消息
     * alias 指定推送人id
     * content推送消息内容
     */
    public void postPushMessage(int position) {
        MessageBean bean = new MessageBean();
        bean.setTitle("点赞消息！");
        Gson gson = new Gson();
        String android_notification = gson.toJson(bean);
        OkHttpUtils.get().url("http://bisonchat.com/index/jpush/aliasPushApi")
                .addParams("alias", datas.get(position).getUserId())
                .addParams("content", "您的好友：" + MyApplication.getCurrentUserInfo().getUserName() + "为您点了一个'赞'")
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
}
