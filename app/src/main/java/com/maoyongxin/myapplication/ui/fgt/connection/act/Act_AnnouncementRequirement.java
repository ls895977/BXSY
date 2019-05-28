package com.maoyongxin.myapplication.ui.fgt.connection.act;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lykj.aextreme.afinal.utils.Debug;
import com.lykj.aextreme.afinal.utils.MyToast;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.bean.EventMessage;
import com.maoyongxin.myapplication.common.BaseAct;
import com.maoyongxin.myapplication.common.ComantUtils;
import com.maoyongxin.myapplication.common.MyApplication;
import com.maoyongxin.myapplication.ui.fgt.connection.act.base.SerViceProviderGridBean;
import com.maoyongxin.myapplication.ui.fgt.connection.act.bean.ConnectionBean;
import com.maoyongxin.myapplication.ui.fgt.connection.act.bean.UserRequitBean;
import com.maoyongxin.myapplication.ui.fgt.connection.act.dialog.Dlg_SerViceProvicerButoom;
import com.maoyongxin.myapplication.ui.fgt.connection.adapter.AnnouncementRequirementAdapter;
import com.maoyongxin.myapplication.ui.fgt.connection.adapter.ServiceProviderGrdivewAdater;
import com.maoyongxin.myapplication.ui.fgt.connection.adapter.User_RequirementAdapter;
import com.maoyongxin.myapplication.ui.fgt.min.act.Act_UserEditNew;
import com.maoyongxin.myapplication.view.SodukuGridView;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

import static com.maoyongxin.myapplication.ui.fgt.connection.act.Act_ServicePublishing.subStr;

/**
 * 人脉=服务商=公告需求=搜索
 */
public class Act_AnnouncementRequirement extends BaseAct implements BaseQuickAdapter.OnItemClickListener{
    private EditText seacher;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView myRecyclerView;
    private View haderView;
    ServiceProviderGrdivewAdater grdivewAdater;
    List<SerViceProviderGridBean.InfoBean> gridData = new ArrayList<>();

    private SodukuGridView myGridview;

    private Dlg_SerViceProvicerButoom screenDlg;
    private String areaCode="510000",date="1";
    private String provice="510000";
    private String position="";
    private int position1;

    @Override
    protected void handlerPassMsg(int target, int target1, Object obj) {

    }

    @Override
    public int initLayoutId() {
        return R.layout.act_announcementrequirement;
    }

    private int page = 1;



    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        hideHeader();
        setOnClickListener(R.id.AnnouncementRequirement_fabu);
        setOnClickListener(R.id.AnnouncementRequirement_back);
        setOnClickListener(R.id.Announcement_screen);


        screenDlg = new Dlg_SerViceProvicerButoom(this, new Dlg_SerViceProvicerButoom.OnClickListenning() {
            @Override
            public void getAreaCode(String AreaCode, String data) {
                areaCode=AreaCode;
                date=data;

            }
        });
                seacher = getView(R.id.AnnouncementRequirement_seacher);
        mRefreshLayout = getView(R.id.refreshLayout);
        myRecyclerView = getView(R.id.recyclerView);
      //  Announcement_screen=getViewAndClick(R.id.Announcement_screen);
    }

    @Override
    public void initData() {
        seacher.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String stSeacher = seacher.getText().toString();
                search = stSeacher;
                page = 1;
                datas.clear();
                postBackDtata();
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        myRecyclerView.setLayoutManager(layoutManager);
        //内容跟随偏移
        mRefreshLayout.setEnableHeaderTranslationContent(true);
        //设置 Header 为 Material风格
        mRefreshLayout.setRefreshHeader(new MaterialHeader(context).setShowBezierWave(false));
        //设置 Footer 为 球脉冲
        mRefreshLayout.setRefreshFooter(new BallPulseFooter(context).setSpinnerStyle(SpinnerStyle.Scale));
        mRefreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                postBackDtata();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                datas.clear();
                postBackDtata();
            }
        });
        haderView = LayoutInflater.from(context).inflate(R.layout.view_announcementrequirement, null);
        myGridview = getView(haderView, R.id.my_Gridview);
        myAdapter = new User_RequirementAdapter(context);
        myAdapter.setOnItemClickListener(this);
        myAdapter.addHeaderView(haderView);
        myRecyclerView.setAdapter(myAdapter);
        grdivewAdater = new ServiceProviderGrdivewAdater(this, gridData);
        myGridview.setAdapter(grdivewAdater);

        postBackDtata();
        postGridViewData();
    }
    @Subscribe
    public void onEventMainThread(EventMessage eventMessage) {
        switch (eventMessage.getType()) {

            case 3:

                position = eventMessage.getData().toString();

                datas.clear();
                page = 1;

                gridData.get(position1).setIsof(false);
                gridData.get(Integer.valueOf(position).intValue()).setIsof(true);
                position1 = Integer.valueOf(position).intValue();
                grdivewAdater.notifyDataSetChanged();
                classify_id = gridData.get(Integer.valueOf(position).intValue()).getId();
                postBackDtata();

                break;

        }


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
            case R.id.AnnouncementRequirement_fabu://发布


                if(MyApplication.getCurrentUserInfo().getUserPhone()!=null&&!MyApplication.getCurrentUserInfo().getUserPhone().equals(""))
                {
                    startActivityForResult(Act_ServicePublishing.class, 10);
                }
                else
                {
                    edit_userphone();
                }
                break;
            case R.id.AnnouncementRequirement_back:
                finish();

            case R.id.Announcement_screen:
                screenDlg.show();


                break;
        }
    }
    List<UserRequitBean.InfoBean.DataBean> datas = new ArrayList<>();
    private User_RequirementAdapter myAdapter;
    private String search = "";
    private String classify_id = "7";//进去信息板块 默认的信息类型
    UserRequitBean bean;
    /*
     * 获取列表
     */
    public void postBackDtata() {

        try
        {
            if (!MyApplication.getMyCurrentLocation().getCityCode().equals(""))
            {
                areaCode=subStr(MyApplication.getMyCurrentLocation().getAdCode(), 2) + "0000";
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        OkHttpUtils.get().url(ComantUtils.MyUrlHot + ComantUtils.searchUserNoticeList)
                .addParams("page", page + "")
                .addParams("search", search + "")
                .addParams("classify_id", classify_id)
                .addParams("adcode",areaCode+"")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
            }
            @Override
            public void onResponse(String response, int id) {
                Gson gson = new Gson();
                bean = gson.fromJson(response, UserRequitBean.class);
                for (int i = 0; i < bean.getInfo().getData().size(); i++) {
                    datas.add(bean.getInfo().getData().get(i));
                }
                myAdapter.setNewData(datas);
                myAdapter.notifyDataSetChanged();
                if (bean.getInfo().getData().size() == 0) {
          
                } else {
                    mRefreshLayout.finishLoadMore();
                    mRefreshLayout.finishRefresh();
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == 10) {
            page = 1;
            datas.clear();
            postBackDtata();
        }
        if (requestCode == 10 && resultCode == 20) {
            page = 1;
            datas.clear();
            postBackDtata();
        }
    }

    Intent intent;

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        intent = new Intent();
        intent.putExtra("user_notice_id", datas.get(position).getId() + "");
        intent.putExtra("classify_id", classify_id);
        intent.setClass(context, Act_AnnouncementDetails.class);
        startActivityForResult(intent, 10);
    }





    public void postGridViewData() {
        OkHttpUtils.post().url("http://bisonchat.com/index/user_notice/getAddNoticeClassifyListApi.html").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                Gson gson = new Gson();
                SerViceProviderGridBean gridBean = gson.fromJson(response, SerViceProviderGridBean.class);
                for (int i = 0; i < gridBean.getInfo().size(); i++) {
                    SerViceProviderGridBean.InfoBean bean = gridBean.getInfo().get(i);
                    if (i == 0) {
                        bean.setIsof(true);
                    }
                    gridData.add(bean);
                }
                grdivewAdater.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
