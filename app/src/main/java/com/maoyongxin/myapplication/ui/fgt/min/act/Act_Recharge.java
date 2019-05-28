package com.maoyongxin.myapplication.ui.fgt.min.act;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.lykj.aextreme.afinal.utils.MyToast;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.common.BaseAct;
import com.maoyongxin.myapplication.common.MyApplication;
import com.maoyongxin.myapplication.http.callback.EntityCallBack;
import com.maoyongxin.myapplication.http.request.ReqGetVipMoney;
import com.maoyongxin.myapplication.http.request.ReqPay;
import com.maoyongxin.myapplication.http.request.ReqUserServer;
import com.maoyongxin.myapplication.http.response.LoginResponse;
import com.maoyongxin.myapplication.http.response.StringObjResponse;
import com.maoyongxin.myapplication.http.response.VipMoneyListResponse;
import com.maoyongxin.myapplication.tool.PayResult;

import java.util.Map;

/**
 * 我要充值
 */
public class Act_Recharge extends BaseAct {
    private TextView userId, vipNum;
    private Button btn_YPay;
    private String vipId = "";
    private static final int SDK_PAY_FLAG = 1000;
    String orderInfo;
    Runnable payRunnable = new Runnable() {

        @Override
        public void run() {
            PayTask alipay = new PayTask(Act_Recharge.this);
            Map<String, String> result = alipay.payV2(orderInfo, true);
            alipay.payV2(orderInfo, true);
            Message msg = new Message();
            msg.what = SDK_PAY_FLAG;
            msg.obj = result;
            mHandler.sendMessage(msg);
        }
    };
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == SDK_PAY_FLAG) {
                PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                /**
                 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                 */
                String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                String resultStatus = payResult.getResultStatus();
                // 判断resultStatus 为9000则代表支付成功
                if (TextUtils.equals(resultStatus, "9000")) {
                    // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                    Toast.makeText(Act_Recharge.this, "支付成功", Toast.LENGTH_SHORT).show();
                    upLoadUserInfo();
                } else {
                    // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                    Toast.makeText(Act_Recharge.this, "支付失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    @Override
    protected void handlerPassMsg(int target, int target1, Object obj) {

    }

    @Override
    public int initLayoutId() {
        return R.layout.act_recharge;
    }

    @Override
    public void initView() {
        hideHeader();
        setOnClickListener(R.id.Recharge_back);
        setOnClickListener(R.id.btn_YPay);
        userId = getView(R.id.userId);
        vipNum = getView(R.id.vipNum);
        btn_YPay = getViewAndClick(R.id.btn_YPay);
    }

    @Override
    public void initData() {
        userId.setText("会员编号:" + MyApplication.getCurrentUserInfo().getUserId());
        vipNum.setText("会员到期日:" + MyApplication.getCurrentUserInfo().getVipNum());
        getVipMoney();
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
            case R.id.Recharge_back:
                finish();
                break;
            case R.id.btn_YPay://开通

                doPay(vipId);
                break;
        }
    }

    private void getVipMoney() {
        ReqGetVipMoney.getMoney(this, getActivityTag(), new EntityCallBack<VipMoneyListResponse>() {
            @Override
            public Class<VipMoneyListResponse> getEntityClass() {
                return VipMoneyListResponse.class;
            }

            @Override
            public void onSuccess(VipMoneyListResponse resp) {
                if (resp.is200()) {
                    for (int i = 0; i < resp.obj.size(); i++) {
                        if (resp.obj.get(i).getType() == 1 && resp.obj.get(i).getVipTime() == 1) {//1个月
                            btn_YPay.setText(resp.obj.get(i).getVipMoney() + "元开通");
                            vipId = resp.obj.get(i).getId() + "";
                        } else if (resp.obj.get(i).getType() == 1 && resp.obj.get(i).getVipTime() == 3) {//3个月
                            btn_YPay.setText(resp.obj.get(i).getVipMoney() + "元开通");
                            vipId = resp.obj.get(i).getId() + "";
                        } else if (resp.obj.get(i).getType() == 2 && resp.obj.get(i).getVipTime() == 1) {//年费
                            btn_YPay.setText(resp.obj.get(i).getVipMoney() + "元开通");
                            vipId = resp.obj.get(i).getId() + "";
                        }
                    }
                }
            }

            @Override
            public void onFailure(Throwable e) {

            }

            @Override
            public void onCancelled(Throwable e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void doPay(String vipId) {
        ReqPay.getAliMessage(this, getActivityTag(), MyApplication.getCurrentUserInfo().getUserId(), vipId, new EntityCallBack<StringObjResponse>() {
            @Override
            public Class<StringObjResponse> getEntityClass() {
                return StringObjResponse.class;
            }

            @Override
            public void onSuccess(StringObjResponse resp) {
                if (resp.is200()) {
                    orderInfo = resp.obj;
                    // 必须异步调用
                    Thread payThread = new Thread(payRunnable);
                    payThread.start();
                }
            }

            @Override
            public void onFailure(Throwable e) {

            }

            @Override
            public void onCancelled(Throwable e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void upLoadUserInfo() {
        ReqUserServer.doLogin(this, getActivityTag(), MyApplication.getLogNum(), MyApplication.getLogPsw(), new EntityCallBack<LoginResponse>() {
            @Override
            public Class<LoginResponse> getEntityClass() {
                return LoginResponse.class;
            }

            @Override
            public void onSuccess(LoginResponse resp) {
                if (resp.is200()) {
                    MyApplication.setCurrentUserInfo(resp.obj);
                }
            }

            @Override
            public void onFailure(Throwable e) {
                MyToast.show(context, "登录失败");
            }

            @Override
            public void onCancelled(Throwable e) {
            }

            @Override
            public void onFinished() {
            }
        });
    }
}
