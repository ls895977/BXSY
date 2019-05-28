package com.maoyongxin.myapplication.ui.fgt.connection.act;

import android.app.Dialog;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;
import com.lykj.aextreme.afinal.utils.Debug;
import com.lykj.aextreme.afinal.utils.MyToast;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.bean.ClassifyApiBean;
import com.maoyongxin.myapplication.bean.MessageBean;
import com.maoyongxin.myapplication.common.BaseAct;
import com.maoyongxin.myapplication.common.ComantUtils;
import com.maoyongxin.myapplication.common.MyApplication;
import com.maoyongxin.myapplication.tool.TimeAddTool;
import com.maoyongxin.myapplication.tool.UtilsTool;
import com.maoyongxin.myapplication.tool.timeaddtollbean.GetJsonDataUtil;
import com.maoyongxin.myapplication.tool.timeaddtollbean.JsonBean;
import com.maoyongxin.myapplication.ui.fgt.connection.act.bean.ConnectionBean;
import com.maoyongxin.myapplication.ui.fgt.connection.act.bean.ServicePulishingBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;

/**
 * 人脉=服务商=公告==发布需求
 */
public class Act_ServicePublishing extends BaseAct implements TimeAddTool.onBackDateTime, TimeAddTool.onBackAddr, TimeAddTool.onBackItem {

    private TextView time, tvAddr, type;
    ZLoadingDialog dialog;
    private EditText title, context, detailed_area, phone, qq;

    @Override
    protected void handlerPassMsg(int target, int target1, Object obj) {

    }

    @Override
    public int initLayoutId() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        return R.layout.act_servicepublishing;
    }

    @Override
    public void initView() {
        hideHeader();
        setOnClickListener(R.id.service_date);
        setOnClickListener(R.id.service_addr);
        setOnClickListener(R.id.service_type);
        setOnClickListener(R.id.service_coomit);
        time = getView(R.id.service_dateTime);
        tvAddr = getView(R.id.service_tvaddr);
        type = getView(R.id.tvType);
        title = getViewAndClick(R.id.ServicePublishing_title);
        context = getView(R.id.ServicePublishing_Context);
        detailed_area = getView(R.id.service_detailed_area);
        qq = getView(R.id.ServicePublishing_qq);
        setOnClickListener(R.id.friends_back);
        phone = getView(R.id.ServicePublishing_phone);

    }

    @Override
    public void initData() {
        dialog = new ZLoadingDialog(this);
        dialog.setLoadingBuilder(Z_TYPE.ROTATE_CIRCLE)//设置类型
                .setLoadingColor(Color.DKGRAY)//颜色
                .setHintText("数据提交中...")
                .setHintTextSize(16) // 设置字体大小 dp
                .setHintTextColor(Color.DKGRAY)  // 设置字体颜色
                .setDurationTime(0.5) // 设置动画时间百分比 - 0.5倍
                .setDialogBackgroundColor(Color.parseColor("#CCffffff")); // 设置背景色，默认白色
        postBackType();


        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (TextUtils.isEmpty(context.getText().toString())||TextUtils.isEmpty(title.getText().toString()))
                {
                    context.setText("我的需求是："+title.getText());
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

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
            case R.id.service_date:
                new TimeAddTool().initTimePicker(this, this);
                break;
            case R.id.service_addr:
                new TimeAddTool().showPickerView(this, this);
                break;
            case R.id.service_type://类型
                if (typeData.size() == 0) {
                    MyToast.show(this, "还未获取到类型值！");
                    return;
                }
                new TimeAddTool().initNoLinkOptionsPicker(this, typeData, this);
                break;
            case R.id.service_coomit://提交
                addUserNoticeApi();
                break;



        }
    }

    /**
     * 返回时间
     *
     * @param date
     */
    @Override
    public void backDate(String date) {
        time.setText(date);
    }

    /**
     * 选择的地区
     */
    @Override
    public void backAddr(String options1, String options2, String options3) {
        tvAddr.setText(options1 + options2 + options3);
    }

    /**
     * 选择的类型
     *
     * @param options
     */
    @Override
    public void backItem(int options) {
        type.setText(typeBean.getInfo().get(options).getName());
        classify_id = typeBean.getInfo().get(options).getId()+"";
    }

    private ClassifyApiBean typeBean;
    private List<String> typeData = new ArrayList<>();

    /**
     * 获取类型
     */
    public void postBackType() {
        typeData.clear();
        OkHttpUtils.get().url(ComantUtils.MyUrlHot + ComantUtils.getClassifyApi)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(String response, int id) {
                Gson gson = new Gson();
                typeBean = gson.fromJson(response, ClassifyApiBean.class);
                for (int i = 0; i < typeBean.getInfo().size(); i++) {
                    typeData.add(typeBean.getInfo().get(i).getName());
                }
            }
        });
    }

    /**
     * 上传参数
     */
    private String stTitle, stContent, classify_id = "", indate, area, stDetailed_area, stPhone, stQQ;

    public void addUserNoticeApi() {
        stTitle = title.getText().toString();
        stContent = context.getText().toString();
        indate = time.getText().toString();
        area = tvAddr.getText().toString();
        stDetailed_area = detailed_area.getText().toString();
        stPhone = phone.getText().toString();
        stQQ = qq.getText().toString();


        if (TextUtils.isEmpty(stTitle)) {
            MyToast.show(this, "请输入您的需求标题!");
            return;
        }

        if (TextUtils.isEmpty(classify_id)) {
            MyToast.show(this, "请选择需求类型!");
            return;
        }
        if (TextUtils.isEmpty(indate)) {
            MyToast.show(this, "请选择有效期!");
            return;
        }


        if (TextUtils.isEmpty(stQQ)) {
            MyToast.show(this, "请输入您的预算!");
            return;
        }

        dialog.show();
        OkHttpUtils.get().url(ComantUtils.MyUrlHot + ComantUtils.addUserNoticeApi)
                .addParams("uid", MyApplication.getCurrentUserInfo().getUserId())
                .addParams("title", stTitle)
                .addParams("content", stContent)
                .addParams("classify_id", classify_id)
                .addParams("indate", indate)
                .addParams("area", MyApplication.getMyCurrentLocation().getCityName())
                .addParams("detailed_area", MyApplication.getCurrentUserInfo().getHeadImg())
                .addParams("area_code", MyApplication.getMyCurrentLocation().getAdCode())
                .addParams("phone",MyApplication.getCurrentUserInfo().getUserPhone())
                .addParams("qq_account", stQQ)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                dialog.dismiss();
            }

            @Override
            public void onResponse(String response, int id) {
                dialog.dismiss();
                Gson gson = new Gson();
                ServicePulishingBean typeBean = gson.fromJson(response, ServicePulishingBean.class);
                if (typeBean.getCode() == 200) {
                    postPushMessage();
                    MyToast.show(Act_ServicePublishing.this, typeBean.getMsg());
                    setResult(10);
                    finish();
                }
            }
        });
    }


    /**
     * 推送需求消息
     * alias 指定推送人id
     * content推送消息内容
     */
    public void postPushMessage() {
        MessageBean bean = new MessageBean();
        bean.setTitle("彼信商业社区：同城需求");
        Gson gson = new Gson();
        String android_notification = gson.toJson(bean);
        String AdCode = subStr(MyApplication.getMyCurrentLocation().getAdCode(), 4) + "00";
        Debug.e("--------------AdCode===" + AdCode);
        OkHttpUtils.get().url("http://bisonchat.com/index/jpush/tagsPushApi")
                .addParams("tags", AdCode)
                .addParams("content", MyApplication.getMyCurrentLocation().getCityName()+"用户：" + MyApplication.getCurrentUserInfo().getUserName() + "发布了同城需求")
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

    /**
     * 截取字符长度，区分中英文
     *
     * @param abc 字符串内容
     * @param len 截取长度
     * @return
     */
    public static String subStr(String abc, int len) {
        if (TextUtils.isEmpty(abc) || len <= 0)
            return "";
        StringBuffer stringBuffer = new StringBuffer();
        int sum = 0;
        char[] chars = abc.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (sum >= (len * 3)) {
                break;
            }
            char bt = chars[i];
            if (bt > 64 && bt < 123) {
                stringBuffer.append(String.valueOf(bt));
                sum += 2;
            } else {
                stringBuffer.append(String.valueOf(bt));
                sum += 3;
            }
        }
        return stringBuffer.toString();
    }
}
