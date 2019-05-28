package com.maoyongxin.myapplication.ui.fgt.connection.act;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import com.lykj.aextreme.afinal.utils.MyToast;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.common.BaseAct;
import com.maoyongxin.myapplication.common.ComantUtils;
import com.maoyongxin.myapplication.common.MyApplication;

import com.maoyongxin.myapplication.http.entity.PoiInfoBean;
import com.maoyongxin.myapplication.permission.RxPermissions;

import com.maoyongxin.myapplication.ui.fgt.connection.act.bean.CompanyBean;

import com.maoyongxin.myapplication.ui.fgt.min.act.Act_AddressHomeCheck;
import com.maoyongxin.myapplication.ui.fgt.min.act.Act_Recharge;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.functions.Consumer;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.zhouyou.http.EasyHttp.getContext;

/**
 * 企业详情
 */
public class Act_CompanyDetails extends BaseAct {
    Button concle;
    PoiInfoBean bean;
    CompanyBean bean1;
    private String collcted="0";
    private String call="0";
    private String msgsend="0";
    private String phonenumber="0000000000";
    private  Boolean  vipstatu;
    private Boolean isUseable = false;
    private String searchableApi="http://st.3dgogo.com/index/user/is_get_enterprise_num.html?uid=";
    private Boolean secendGetted=true;
    @Override
    protected void handlerPassMsg(int target, int target1, Object obj) {

    }

    @Override
    public int initLayoutId() {
        return R.layout.act_companydetails;
    }

    private TextView tv_companyName, companyadress, tv_poiAddress, email, Website, TypeDes, name;
    private TextView phone;
    private RxPermissions rxPermissions;
    ZLoadingDialog dialog;
    private  Button bt_rtn_err;

    @Override
    public void initView() {
        hideHeader();
        dialog = new ZLoadingDialog(this);
        dialog.setLoadingBuilder(Z_TYPE.ROTATE_CIRCLE)//设置类型
                .setLoadingColor(Color.DKGRAY)//颜色
                .setHintText("数据加载中...")
                .setHintTextSize(16) // 设置字体大小 dp
                .setHintTextColor(Color.DKGRAY)  // 设置字体颜色
                .setDurationTime(0.3) // 设置动画时间百分比 - 0.5倍
                .setDialogBackgroundColor(Color.parseColor("#CCffffff")); // 设置背景色，默认白色
        rxPermissions = new RxPermissions(getActivity());
        concle = getViewAndClick(R.id.bt_shoucang);
        setOnClickListener(R.id.bt_zhuanfa);
        setOnClickListener(R.id.bt_Sendmessage);
        setOnClickListener(R.id.bt_newCall);
        setOnClickListener(R.id.jilv);
        tv_companyName = getView(R.id.companyName);
        companyadress = getView(R.id.companyadress);
        phone = getView(R.id.phone);
        email = getView(R.id.email);
        Website = getView(R.id.Website);
        TypeDes = getView(R.id.TypeDes);
        name = getView(R.id.name);
        bt_rtn_err=getViewAndClick(R.id.bt_rtn_err);
    }

    @Override
    public void initData() {


        vipstatu   =getIntent().getBooleanExtra("vipstatu",false);
        bean = (PoiInfoBean) getIntent().getSerializableExtra("bean");
        getSearchstate();



    }

    @Override
    public void requestData() {

    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        try {
            if(phonenumber!=null)
            {

            }
            else
            {
                phonenumber="暂无数据";
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            phonenumber="暂无数据";
        }
        try {
            postdatatoserver(MyApplication.getCurrentUserInfo().getUserId(),bean.getPoiName(),collcted,msgsend,call,phonenumber);
        }
        catch (Exception e)
        {
            //postdatatoserver(MyApplication.getCurrentUserInfo().getUserId(),bean.getPoiName(),collcted,msgsend,call,phonenumber);
        }

        finish();
    }
    private void postdatatoserver(String uid,String poiname,String collcted,String Msgsend,String call,String phone)
    {

        OkHttpUtils.post().url("http://bisonchat.com/index/enterprise_info/create_msg_call_info.html")
                .addParams("uid", uid)
                .addParams("enterprise_name", poiname)
                .addParams("is_collect", collcted)
                .addParams("msg", Msgsend)
                .addParams("call", call)
                .addParams("phone",phone)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {

                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getInt("code") == 200) {



                    } else if (jsonObject.getInt("code") == 500) {


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void updateUI() {

    }

    @Override
    public void onViewClick(View v) {
        /*
        if (bean1.getInfo().getData().size() == 0) {
            MyToast.show(context, "暂无公司数据，不能进行相关操作！");
            return;
        }
        */
        switch (v.getId()) {
            case R.id.bt_shoucang://收藏
                if (concle.isSelected()) {//取消收藏

                    setCancleCollection();
                    collcted="1";

                } else {//收藏
                    setCollection();
                }
                break;
            case R.id.bt_zhuanfa://转发
                break;
            case R.id.bt_Sendmessage://短信
                if (isPhoneNumber(bean1.getResult().getResult().getBaseInfo().getPhoneNumber())) {
                    sendSMS(name.getText().toString() + "经理，您好！我是刚才和您通话的那位，很高兴认识您，这是我的电话请惠存", "smsto:" + bean1.getResult().getResult().getBaseInfo().getPhoneNumber());
                    msgsend="1";
                } else {
                    MyToast.show(context, "不是有效的电话号码");
                }
                break;
            case R.id.bt_newCall://电话
                if (TextUtils.isEmpty(phone.getText().toString())) {
                    return;
                }
                rxPermissions
                        .request(Manifest.permission.CALL_PHONE)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if (aBoolean) {
                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_CALL);
                                    intent.setData(Uri.parse("tel:" + phone.getText()));
                                    call="1";
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getActivity(), "请打开拨打电话权限", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                break;
            case R.id.jilv://记录
                Intent intent=new Intent(context,Act_HistoricalRecord.class);

                        intent.putExtra("targetuserid",MyApplication.getCurrentUserInfo().getUserId());
                    startActivity(intent);
                break;


            case R.id.bt_rtn_err:

                rt_company_detail("客户报错，需要联系客户处理");
                MyToast.show(getActivity(),"感谢您的反馈，我们会在24小时内处理");
                break;
        }
    }

    public static boolean isPhoneNumber(String phoneNo) {
        if (TextUtils.isEmpty(phoneNo)) {
            return false;
        }

        if (phoneNo.length() == 11) {
            for (int i = 0; i < 11; i++) {
                if (!PhoneNumberUtils.isISODigit(phoneNo.charAt(i))) {
                    return false;
                }
            }
            Pattern p = Pattern.compile("^((13[^4,\\D])" + "|(134[^9,\\D])" +
                    "|(14[5,7])" +
                    "|(15[^4,\\D])" +
                    "|(17[3,6-8])" +
                    "|(18[0-9]))\\d{8}$");
            Matcher m = p.matcher(phoneNo);
            return m.matches();
        }
        {

        }
        return false;
    }


    private void sendSMS(String smsBody, String person) {
        Uri smsToUri = Uri.parse(person);
        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
        intent.putExtra("sms_body", smsBody);
        startActivity(intent);
    }

    /**
     * 获取检索信息
     */





    private String parse_Value(JSONObject data, String value) {
        String com_value = "暂无数据";
        if (data.has(value)) {
            try {
                com_value = data.getString(value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return com_value;
    }



    public void postBackDtata() {

        dialog.show();


            OkHttpUtils.get().url(ComantUtils.MyUrlHot + ComantUtils.enterprise_info_get)
                    .addParams("name", bean.getPoiName())
                    .addParams("myuid",MyApplication.getCurrentUserInfo().getUserId()+"")
                    .build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                  //  Log.e("查询错误", e.getMessage());
                   // dialog.dismiss();
                    if (secendGetted)
                    {

                        postBackDtata();
                        secendGetted=false;

                        dialog.dismiss();
                    }
                    else
                    {
                        MyToast.show(getActivity(),"深度查询失败，我们会及时更新");
                        dialog.dismiss();
                        rt_company_detail("查询失败，请检索信息并更新");
                    }
                }

                @Override
                public void onResponse(String response, int id) {
                    dialog.dismiss();
                    try {
                        Gson gson = new Gson();
                        bean1=gson.fromJson(response,CompanyBean.class);
                        tv_companyName.setText(bean1.getResult().getResult().getBaseInfo().getName());
                        email.setText(bean1.getResult().getResult().getBaseInfo().getEmail());
                        Website.setText(bean1.getResult().getResult().getBaseInfo().getWebsiteList());
                        companyadress.setText(bean1.getResult().getResult().getBaseInfo().getRegLocation());
                        TypeDes.setText(bean1.getResult().getResult().getBaseInfo().getBusinessScope());
                        phone.setText(bean1.getResult().getResult().getBaseInfo().getPhoneNumber());

                        try {
                            phonenumber=bean1.getResult().getResult().getBaseInfo().getPhoneNumber();
                        }catch (Exception e)
                        {
                            phonenumber="0000000000";
                        }
                        name.setText(bean1.getResult().getResult().getBaseInfo().getLegalPersonName()+ "-总经理");
                    }

                    catch (Exception e)
                    {
                        e.printStackTrace();
                        MyToast.show(context, "该公司公开数据较少，暂未收录,不扣除查询点数");
                        rt_company_detail("查询失败，请检索信息并更新");
                    }

                }
            });


    }

    /**
     * 收藏
     */
    public void setCollection() {
        dialog.show();
        OkHttpUtils.post().url("http://st.3dgogo.com/index/enterprise_info/create_collect_info")
                .addParams("id", MyApplication.getCurrentUserInfo().getUserId())
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                dialog.dismiss();
            }

            @Override
            public void onResponse(String response, int id) {
                MyToast.show(context, "收藏成功！");
                concle.setSelected(true);
                collcted="1";
                dialog.dismiss();
            }
        });
    }

    /**
     * 取消收藏收藏
     */
    public void setCancleCollection() {
        dialog.show();
        OkHttpUtils.post().url("http://st.3dgogo.com/index/enterprise_info/cancel_collect_info")
                .addParams("id", MyApplication.getCurrentUserInfo().getUserId())
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                dialog.dismiss();
            }

            @Override
            public void onResponse(String response, int id) {
                MyToast.show(context, "取消收藏成功！");
                concle.setSelected(false);
                collcted="0";
                dialog.dismiss();
            }
        });
    }


    private void getSearchstate()
    {
//get自身服务器数据
        OkHttpUtils.get().url("http://st.3dgogo.com/index/user/is_get_enterprise_num.html" )
                .addParams("uid", MyApplication.getCurrentUserInfo().getUserId()+"")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e("查询错误", e.getMessage());
                dialog.dismiss();
            }

            @Override
            public void onResponse(String response, int id) {
                dialog.dismiss();

                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getInt("code")==200)
                        {
                            if (jsonObject.getBoolean("operation") == true) {

                                postBackDtata();

                            }
                            else if (jsonObject.getBoolean("operation") == false)
                            {
                                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
                                builder.setTitle("您今日查询已达上限，您可以提升VIP等级或加入团队来提升每日查询上限")

                                        .setNegativeButton("加入团队", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Intent intent=new Intent(getActivity(), Act_AddressHomeCheck.class);
                                                startActivity(intent);
                                            }
                                        })
                                        .setPositiveButton("提升vip", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Intent intent=new Intent(getActivity(), Act_Recharge.class);
                                                startActivity(intent);
                                            }
                                        }).show();
                            }
                        }


                    }

                catch (Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(getActivity(),"状态校验失败，由彼信免费提供查询，不扣除查询点数",Toast.LENGTH_SHORT).show();
                    postBackDtata();
                }

            }
        });


            }

private void rt_company_detail(String info)
{
    OkHttpUtils.post()
            .url("http://bisonchat.com/index/company_base_record/createCompanyBaseRecordApi.html")
            .addParams("name",bean.getPoiName())
            .addParams("userId",MyApplication.getCurrentUserInfo().getUserId())
            .addParams("userPhone",MyApplication.getCurrentUserInfo().getUserPhone()+"")
            .addParams("info",info)
            .build().execute(new Callback() {
        @Override
        public Object parseNetworkResponse(Response response, int id) throws Exception {
            return null;
        }

        @Override
        public void onError(Call call, Exception e, int id) {

        }

        @Override
        public void onResponse(Object response, int id) {

        }
    });

}
}
