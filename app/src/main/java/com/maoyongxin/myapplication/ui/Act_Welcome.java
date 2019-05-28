package com.maoyongxin.myapplication.ui;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lykj.aextreme.afinal.utils.ACache;
import com.lykj.aextreme.afinal.utils.Debug;
import com.lykj.aextreme.afinal.utils.MyToast;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.bean.LoDIngImagBean;
import com.maoyongxin.myapplication.bean.LoginBean;
import com.maoyongxin.myapplication.bean.newBean;
import com.maoyongxin.myapplication.common.AppConfig;
import com.maoyongxin.myapplication.common.BaseAct;
import com.maoyongxin.myapplication.common.MyApplication;
import com.maoyongxin.myapplication.http.callback.EntityCallBack;
import com.maoyongxin.myapplication.http.entity.LocationInfo;
import com.maoyongxin.myapplication.http.entity.UserInfoService;
import com.maoyongxin.myapplication.http.request.ReqRefreshToken;
import com.maoyongxin.myapplication.http.request.ReqUserServer;
import com.maoyongxin.myapplication.http.response.BaiduPushResponse;
import com.maoyongxin.myapplication.http.response.LoginResponse;
import com.maoyongxin.myapplication.http.response.RefreshTokenResponse;
import com.maoyongxin.myapplication.tool.PackageUtils;

import com.maoyongxin.myapplication.tool.PollingUtils;
import com.maoyongxin.myapplication.tool.StreamUtils;
import com.maoyongxin.myapplication.tool.TagAliasOperatorHelper;
import com.maoyongxin.myapplication.tool.ToastUtil;
import com.maoyongxin.myapplication.ui.service.PollingService;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import okhttp3.Call;

import static com.maoyongxin.myapplication.tool.TagAliasOperatorHelper.ACTION_SET;
import static com.maoyongxin.myapplication.ui.fgt.connection.act.Act_ServicePublishing.subStr;

/**
 * 欢迎页
 */
public class Act_Welcome extends Activity {
    private ImageView loginImg;
    private Handler handler = new Handler();
    private boolean isFirstIn;
    private boolean isAutoLogin;
    private String phoneNum, pswNum, version1, forceUpdate, fileName;
    int versionCode;
    int mainversion;
    int subversion;
    String path;
    private String mDownloadUrl;
    private static final int WHAT_SHOW_UPDATE_DIALOG = 100;
    private static final int WHAT_SHOW_ERROR_TOAST = 101;
    private static final int REQUEST_CODE_INSTALL = 100;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case WHAT_SHOW_UPDATE_DIALOG:
                    if (forceUpdate.equals("1")) {


                        forceUpdateDialog((String) msg.obj);
                    } else {
                        forceUpdateDialog((String) msg.obj);
                    }
                    break;
                case WHAT_SHOW_ERROR_TOAST:
                    // 提醒
                    Toast.makeText(Act_Welcome.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
                    load2Home();    //现在不需要在首页的时候需要 ToDO
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//白底黑字+沉浸式导航栏



        setContentView(R.layout.act_welcome);
        initView();
        initData();

    }

    public void initView() {
        loginImg = findViewById(R.id.login_img);
    }

    public void initData() {


        lodingImg();
        getVersionName();//获取版本名称
        iniy();


    }

    public SharedPreferences getSp(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("userInfo", MODE_PRIVATE);
        return sharedPreferences;
    }



    private void goToLogin() {
        startActivity(new Intent(this, Act_Login.class));
        overridePendingTransition(R.anim.scale_in, R.anim.fade_out);
        finish();
    }

    /**
     * 加载欢迎页图片
     */
    private void lodingImg() {
        OkHttpUtils.post().url("http://st.3dgogo.com/index/get_photos/getAppLoginPhotos.html")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
               // Glide.with(Act_Welcome.this).load(R.mipmap.icon_wellcome).into(loginImg);
            }

            @Override
            public void onResponse(String response, int id) {
                Gson gson = new Gson();
                LoDIngImagBean bean = gson.fromJson(response, LoDIngImagBean.class);
                if (bean.getInfo().getImg() == null || bean.getInfo().getImg().equals("")) {
                    Glide.with(Act_Welcome.this).load(R.mipmap.icon_wellcome).into(loginImg);
                    return;
                }
                if (bean.getInfo().getImg() != null) {
                    Glide.with(Act_Welcome.this).load(bean.getInfo().getImg()).into(loginImg);
                }

            }
        });

    }

    /**
     * 获取版本名称
     *
     * @return
     */
    public String getVersionName() {
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        version1 = packInfo.versionName;
        versionCode = packInfo.versionCode;
        return version1;
    }

    String publishVersionNum;
    private List<String> newFunction, upgrade;

    /**
     * 版本升级
     */
    private void iniy() {
        OkHttpUtils.post().url("http://bisonchat.com/index/versions_update/get_versions_update_info.html")
                .addParams("versionCode", versionCode+"")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                doLogin();
            }

            @Override
            public void onResponse(String response, int id) {
                Gson gson = new Gson();
                newBean gengxinBean = gson.fromJson(response, newBean.class);
                newBean.HeadnewBean head = gengxinBean.getHead();
                boolean success = head.isSuccess();
                if (success == true) {
                    path = gengxinBean.getBody().getPath();
                    MyApplication.setmDownloadUrl(path);
                    fileName = gengxinBean.getBody().getFileName();
                    publishVersionNum = gengxinBean.getBody().getPublishVersionNum();
                    newFunction = gengxinBean.getBody().getNewFunction();
                    upgrade = gengxinBean.getBody().getMajorization();
                    forceUpdate = gengxinBean.getBody().getIsUpdated() + "";//是否登陆
                    int localVersionCode = PackageUtils
                            .getVersionCode(Act_Welcome.this);
                    getPersimmions();//弹出   动态的权限
                } else {
                    mHandler.postDelayed(new Runnable() {    //做个延时 的处理。
                        @Override
                        public void run() {
                            doLogin();
                        }
                    }, 1000);
                }
            }
        });

    }

    private ACache aCache;

    private void doLogin() {
        aCache = ACache.get(this);
        if (aCache.getAsString("phone") != null) {
            phoneNum = aCache.getAsString("phone");
            pswNum = aCache.getAsString("pwd");
        } else {
            phoneNum = "";
            pswNum = "";
        }
        OkHttpUtils.post().url("http://bisonchat.com/index/user_login/login.html")
                .addParams("userId", phoneNum)
                .addParams("password", pswNum)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(String response, int id) {
                Gson gson = new Gson();
                LoginBean loginBean = gson.fromJson(response, LoginBean.class);
                if (loginBean.isOperation() == false) {
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), Act_Login.class);
                    startActivity(intent);
                    finish();
                    return;
                }
                aCache.put("phone", phoneNum);
                aCache.put("pwd", pswNum);
                MyApplication.setLogNum(phoneNum);
                MyApplication.setLogPsw(pswNum);
                MyApplication.setMyPassword(pswNum);
                resp = new UserInfoService();
                resp.setUserId(loginBean.getUserInfo().getUserId());
                resp.setUserName(loginBean.getUserInfo().getUserName());
                resp.setLongitude(loginBean.getUserInfo().getLongitude());
                resp.setLatitude(loginBean.getUserInfo().getLatitude());
                resp.setToken(loginBean.getUserInfo().getBrithday());
                resp.setCreateDate(loginBean.getUserInfo().getCreateDate());
                resp.setUserPhone(loginBean.getUserInfo().getUserPhone());
                resp.setVipNum(loginBean.getUserInfo().getVipNum());
                resp.setNote(loginBean.getUserInfo().getNote());
                resp.setHeadImg(loginBean.getUserInfo().getHeadImg());
                resp.setBackground_img(loginBean.getUserInfo().getBackground_img());
                resp.setSex(loginBean.getUserInfo().getSex());
                resp.setBrithday(loginBean.getUserInfo().getBrithday());
                resp.setPermanent_city(loginBean.getUserInfo().getPermanent_city());
                resp.setPosition(loginBean.getUserInfo().getPosition());
                MyApplication.setCurrentUserInfo(resp);
                setAlias();
                connect(getApplicationContext(), resp.getToken(), resp.getUserId());
                  backdatatoserver();


            }
        });
    }

    UserInfoService resp;

    public void connect(final Context context, String token, final String userId) {
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            /**
             * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
             * 2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
             */
            @Override
            public void onTokenIncorrect() {

                ReqRefreshToken.refreshToken(Act_Welcome.this, "", userId, new EntityCallBack<RefreshTokenResponse>() {
                    @Override
                    public Class<RefreshTokenResponse> getEntityClass() {
                        return RefreshTokenResponse.class;
                    }

                    @Override
                    public void onSuccess(RefreshTokenResponse resp) {
                        if (resp.is200()) {
                            connect(Act_Welcome.this, resp.obj.getToken(), resp.obj.getUserId() + "");
                        } else {
                            //   NToast.shortToast(WelcomeActivity.this,resp.msg);
                        }
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        Toast.makeText(Act_Welcome.this, "自动登录失败", Toast.LENGTH_SHORT).show();

                        goToLogin();
                    }

                    @Override
                    public void onCancelled(Throwable e) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
            }

            /**
             * 连接融云成功
             *
             * @param userid 当前 token 对应的用户 id
             */
            @Override
            public void onSuccess(final String userid) {
                if (MyApplication.getMyCurrentLocation() == null) {
                    MyToast.show(context, "获取位置信息失败，请检查定位权限或者重新登陆");


                                //  builder.show();
                                MyApplication.setMyCurrentLocation(new LocationInfo());
                                welcomeLoginBison(userid);

                } else {
                   // MyToast.show(context, "获取位置信息失败，已使用默认地址信息");
                    MyApplication.setMyCurrentLocation(new LocationInfo());
                                //  builder.show();
                                welcomeLoginBison(userid);


                }
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                // Log.d("LoginActivity", "--onError--ErrorCode：" + errorCode);
            }
            /**
             * 连接融云失败
             *
             * @param errorCode 错误码，可到官网 查看错误码对应的注释
             */
        });
    }

    private void forceUpdateDialog(String content) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);// 点击其他区域不可取消dialog
        // 设置title
        builder.setIcon(R.mipmap.app_icon);
        builder.setTitle("彼信商业社区");
        // 设置message,由服务器指定的


        builder.setMessage(content);


        builder.setPositiveButton("立刻更新", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                downloadNewApk();
            }
        });

        // dialog显示,UI操作

        mHandler.postDelayed(new Runnable() {    //做个延时 的处理。

            @Override
            public void run() {
                if (!Act_Welcome.this.isDestroyed()) {
                    builder.show();
                }
            }
        }, 2000);


    }

    private void downloadNewApk() {
        // 弹出进度的dialog
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setProgressNumberFormat(""); //设置左下角不显示
        dialog.setCancelable(false);
        dialog.show();

        // 去网络下载
        new Thread(new DownloadApkTask(dialog)).start();
    }

    private class DownloadApkTask implements Runnable {
        private ProgressDialog dialog;
        private String name;

        public DownloadApkTask(ProgressDialog dialog) {
            this.dialog = dialog;
        }

        @Override
        public void run() {
            name = "community";
            String url = mDownloadUrl;// 怎来
            FileOutputStream fos = null;
            InputStream inputStream = null;
            try {
                // 1.去具体的网络接口去下载apk文件
                String sdpath = Environment.getExternalStorageDirectory() + "";
                String mSavePath = sdpath + name + "/" + "updatePath";
                mDownloadUrl = path;
                MyApplication.setmDownloadUrl(mDownloadUrl);

                HttpURLConnection conn = (HttpURLConnection) new URL(mDownloadUrl)
                        .openConnection();
                // 设置超时
                conn.setConnectTimeout(2 * 1000);
                conn.setReadTimeout(2 * 1000);
                // 新的apk文件流
                inputStream = conn.getInputStream();
                // 获得要下载文件的大小
                int contentLength = conn.getContentLength();
                dialog.setMax(contentLength);   //TODO  可以改
                // 指定输出的apk文件,sdcard下
                File file = new File(sdpath,
                        name);
                // 写到文件中
                fos = new FileOutputStream(file);
                int len = -1;
                byte[] buffer = new byte[1024 * 1];
                int progress = 0;
                // 反复的读写输入流
                while ((len = inputStream.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                    progress += len;
                    // 设置进度
                    dialog.setProgress(progress);

                }
                // 下载完成
                // dialog消失
                dialog.dismiss();
                // 提示安装
                installApk(file);
            } catch (Exception e) {
                Toast.makeText(Act_Welcome.this, e.getMessage() + "", Toast.LENGTH_SHORT).show();
//                notifyError("Error:101");
            } finally {
                StreamUtils.closeIO(inputStream);
                StreamUtils.closeIO(fos);
            }
        }
    }


    private void installApk(File file) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");

        startActivityForResult(intent, REQUEST_CODE_INSTALL);

    }


    private void load2Home() {
        mHandler.postDelayed(new Runnable() {    //做个延时 的处理。
            @Override
            public void run() {
                doLogin();
            }
        }, 2500);
    }

    private final int SDK_PERMISSION_REQUEST = 127;
    private String permissionInfo;

    private void getPersimmions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();

            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.INTERNET);
            }
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.CAMERA);
            }
            /*
             * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
             */
            // 读写权限
            if (addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
            }
            // 读取路径状态权限
            if (addPermission(permissions, Manifest.permission.READ_PHONE_STATE)) {
                permissionInfo += "Manifest.permission.READ_PHONE_STATE Deny \n";
            }
            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            }
        }
        showUpdateDialog(newFunction, upgrade, forceUpdate);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) { // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
            if (shouldShowRequestPermissionRationale(permission)) {
                return true;
            } else {
                permissionsList.add(permission);
                return false;
            }
        } else {
            return true;
        }
    }

    private String content = "";



    private void showUpdateDialog(List<String> newFunction, List<String> upgrade, String forceUpdate) {
        content += "\n";
        content += "新增功能" + "\n";
        content += "\n";
        for (int i = 0; i < newFunction.size(); i++) {
            content += ((i + 1) + ": " + (newFunction.get(i) + "\n"));
        }
        content += "\n";
        content += "BUG修复" + "\n";
        content += "\n";
        for (int i = 0; i < upgrade.size(); i++) {
            content += ((i + 1) + ": " + (upgrade.get(i) + "\n"));
        }

        Message msg = Message.obtain();
        msg.what = WHAT_SHOW_UPDATE_DIALOG;
        msg.obj = content;
        MyApplication.setUpdatemsg(content);
        mHandler.sendMessage(msg);
    }
    private static int sequence;
    private void backdatatoserver() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpUtils.post().url("http://st.3dgogo.com/index/action_log/setActionLogApi.html")
                        .addParams("action_uid", MyApplication.getCurrentUserInfo().getUserId())
                        .addParams("action_user_name", MyApplication.getCurrentUserInfo().getUserName())
                        .addParams("action_type", "1")
                        .addParams("action_remarks", "APP内登陆")
                        .build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(String response, int id) {

                    }
                });

            }
        }).start();
    }

    private void welcomeLoginBison(String userId)
    {
        ToastUtil toastUtil = new ToastUtil();
        toastUtil.Short(Act_Welcome.this, "登陆成功").setToastColor(Color.WHITE, R.drawable.toast_radius).show();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("userId", userId + "");
        startActivity(intent);


        finish();
    }
  
    Set<String> tags = new HashSet<>();
    private void setAlias() {
        tags.add(MyApplication.getCurrentUserInfo().getUserId());
        try{
            tags.add( subStr(MyApplication.getMyCurrentLocation().getAdCode(), 2) + "0000");
            tags.add( subStr(MyApplication.getMyCurrentLocation().getAdCode(), 4) + "00");
            tags.add(MyApplication.getMyCurrentLocation().getAdCode());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            tags.add( "510000");
            tags.add( "510100");
            tags.add("510117");
        }

        Debug.d("---------------alias==" +   MyApplication.getCurrentUserInfo().getUserId());
        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
        tagAliasBean.action = ACTION_SET;
        tagAliasBean.tags = tags;
        sequence++;
        tagAliasBean.alias = MyApplication.getCurrentUserInfo().getUserId();
        tagAliasBean.isAliasAction = true;
        TagAliasOperatorHelper.getInstance().handleAction(getApplicationContext(), sequence, tagAliasBean);
        ReqUserServer.uploadBaiduPushInfo(this,
                "id",
                Long.parseLong(MyApplication.getCurrentUserInfo().getUserId()),
                JPushInterface.getRegistrationID(this),
                AppConfig.DEVICE_TYPE, new EntityCallBack<BaiduPushResponse>() {
                    @Override
                    public Class<BaiduPushResponse> getEntityClass() {
                        return BaiduPushResponse.class;
                    }

                    @Override
                    public void onSuccess(BaiduPushResponse resp) {
                        Log.d("---alias", "success");
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

}
