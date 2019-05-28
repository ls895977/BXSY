package com.maoyongxin.myapplication.ui;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lykj.aextreme.afinal.utils.ACache;
import com.lykj.aextreme.afinal.utils.Debug;
import com.lykj.aextreme.afinal.utils.MyToast;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.bean.LoginBean;
import com.maoyongxin.myapplication.common.BaseAct;
import com.maoyongxin.myapplication.common.ComantUtils;
import com.maoyongxin.myapplication.common.MyApplication;
import com.maoyongxin.myapplication.dialog.SimpleChoiceDialog;
import com.maoyongxin.myapplication.http.callback.EntityCallBack;
import com.maoyongxin.myapplication.http.entity.UserInfoService;
import com.maoyongxin.myapplication.http.request.ReqRefreshToken;
import com.maoyongxin.myapplication.http.request.ReqUserServer;
import com.maoyongxin.myapplication.http.response.ChangeUserInfoResponse;
import com.maoyongxin.myapplication.http.response.LoginResponse;
import com.maoyongxin.myapplication.http.response.RefreshTokenResponse;
import com.maoyongxin.myapplication.http.second.HttpNetWorkUtils;
import com.maoyongxin.myapplication.http.second.XGsonUtil;
import com.maoyongxin.myapplication.permission.RxPermissions;
import com.maoyongxin.myapplication.tool.MyGlideEngine;
import com.maoyongxin.myapplication.tool.TimeAddTool;
import com.maoyongxin.myapplication.tool.clickanimview.BamButton;
import com.maoyongxin.myapplication.ui.fgt.community.bean.UploadImageBean;

import com.maoyongxin.myapplication.ui.fgt.min.act.bean.UpdateUserInfoBean;
import com.maoyongxin.myapplication.view.SelectableRoundedImageView;

import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.body.UIProgressResponseCallBack;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;
import com.zhouyou.http.subsciber.IProgressDialog;
import com.zhouyou.http.utils.HttpLog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import okhttp3.Call;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static com.zhouyou.http.EasyHttp.getContext;

/**
 * 注册 头像设置界面
 */
public class Act_Regesite_Headim extends BaseAct implements   TimeAddTool.onBackAddr{
    EditText userresource;
    private int sex;
    SelectableRoundedImageView headimg;
    private ProgressDialog mProgressDialog;
    private static final int REQCODE_LOCAL_PHOTO = 20;
    private ArrayList<String> mCoverChoices;
    private SimpleChoiceDialog mCoverChoiceDialog;
    private String myHeadPath;
    private BamButton dologin;
    private String note;
    private String userId, nickname;
    private String userpsswd,userphone;
    private Boolean uploaded = false;
    private RxPermissions rxPermissions;
    private ACache aCache;
    private String status = "";
    private ImageView backImage;
    private String AVATAR_ORI = "avatar_ori.jpg";
    private String AVATAR_CUT = "avatar.jpg";
    private String birthDay;
    private TextView reedit_backimg,reedit_head;
    @Override
    public int initLayoutId() {
        return R.layout.act_regesite_headim;
    }

    @Override
    public void initView() {
        hideHeader();
        aCache = ACache.get(this);
        getViewAndClick(R.id.headimg);
        getViewAndClick(R.id.dologin);
        headimg = getView(R.id.headimg);
        dologin = getView(R.id.dologin);
        backImage=getViewAndClick(R.id.back_img);
        userresource = getView(R.id.user_resource);
        reedit_backimg=getViewAndClick(R.id.reedit_backimg);
        reedit_head=getViewAndClick(R.id.reedit_head);
    }
    ZLoadingDialog dialog;
    @Override
    public void initData() {
        dialog = new ZLoadingDialog(this);
        dialog.setLoadingBuilder(Z_TYPE.ROTATE_CIRCLE)//设置类型
                .setLoadingColor(Color.DKGRAY)//颜色
                .setHintText("数据加载中...")
                .setHintTextSize(16) // 设置字体大小 dp
                .setHintTextColor(Color.DKGRAY)  // 设置字体颜色
                .setDurationTime(0.5) // 设置动画时间百分比 - 0.5倍
                .setDialogBackgroundColor(Color.parseColor("#CCffffff")); // 设置背景色，默认白色
        userId = getIntent().getStringExtra("userId");
        nickname = getIntent().getStringExtra("nickname");
        userpsswd = getIntent().getStringExtra("userpsswd");
        userphone=getIntent().getStringExtra("userphone");
        mCoverChoices = new ArrayList<>();
        mCoverChoices.add(getString(R.string.local_photo));
        mProgressDialog = new ProgressDialog(this, android.R.style.Theme_DeviceDefault_Dialog);
        mProgressDialog.setCancelable(true);

        rxPermissions = new RxPermissions(this);
    }

    @Override
    public void requestData() {

    }

    @Override
    public void updateUI() {

    }

    private static final int REQUEST_CODE_CHOOSE = 23;//定义请求码常量
    List<Uri> mSelected;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == REQUEST_CODE_CHOOSE) {
                List<String> dataPath = Matisse.obtainPathResult(data);
                for (String imageUri : dataPath) {
                    dialog.show();
                    Avatar(new File(imageUri));
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.reedit_head://头像
                rxPermissions .request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) {
                                if (aBoolean) {
                                    status = "header";
                                    callGallery();
                                } else {
                                    Toast.makeText(getActivity(), "请打开权限", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                break;

            case R.id.headimg://头像
                rxPermissions .request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) {
                                if (aBoolean) {
                                    status = "header";
                                    callGallery();
                                } else {
                                    Toast.makeText(getActivity(), "请打开权限", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                break;

            case R.id.back_img:
                rxPermissions
                        .request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) {
                                if (aBoolean) {
                                    status = "background_img";
                                    callGallery();
                                } else {
                                    Toast.makeText(getActivity(), "请打开权限", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                break;

            case R.id.reedit_backimg:
                rxPermissions
                        .request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) {
                                if (aBoolean) {
                                    status = "background_img";
                                    callGallery();
                                } else {
                                    Toast.makeText(getActivity(), "请打开权限", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                break;
            case R.id.dologin:
                note = userresource.getText().toString();
                if (note != null && !note.equals("")) {
                    if (uploaded) {
                        changeMyUserInfo();
                        doLogin();
                    } else {
                        MyToast.show(context, "请设置头像");
                    }
                } else {
                    MyToast.show(context, "个人描述不能为空");
                }
                break;
        }
    }





    UserInfoService resp;
    private void doLogin() {
        mProgressDialog.show();
        if (aCache.getAsString("phone") != null) {
            userId = aCache.getAsString("phone");
            userpsswd = aCache.getAsString("pwd");
        } else {
            userId = "";
            userpsswd = "";
        }
        OkHttpUtils.post().url("http://bisonchat.com/index/user_login/login.html")
                .addParams("userId", userId)
                .addParams("password", userpsswd)
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
                aCache.put("phone", userId);
                aCache.put("pwd", userpsswd);

                MyApplication.setLogNum(userId);
                MyApplication.setLogPsw(userpsswd);
                MyApplication.setMyPassword(userpsswd);
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

                connect(getApplicationContext(), resp.getToken(), resp.getUserId());
                backdatatoserver();


            }
        });
    }
    public void connect(final Context context, String token, final String userId) {
        RongIM.connect(token, new RongIMClient.ConnectCallback() {

            /**
             * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
             * 2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
             */
            @Override
            public void onTokenIncorrect() {
                ReqRefreshToken.refreshToken(context, getActivityTag(), userId, new EntityCallBack<RefreshTokenResponse>() {
                    @Override
                    public Class<RefreshTokenResponse> getEntityClass() {
                        return RefreshTokenResponse.class;
                    }

                    @Override
                    public void onSuccess(RefreshTokenResponse resp) {
                        if (resp.is200()) {
                            connect(context, resp.obj.getToken(), resp.obj.getUserId() + "");
                        } else {
                            MyToast.show(context, resp.msg);
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

            /**
             * 连接融云成功
             *
             * @param userid 当前 token 对应的用户 id
             */
            @Override
            public void onSuccess(String userid) {
                if (MyApplication.getMyCurrentLocation() == null) {
                    MyToast.show(context, "获取位置信息失败，请检查定位权限或者重新登陆");
                } else {
                    MyToast.show(context, "登录成功");
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("userId", userId);
                    startActivity(intent);
                    finish();
                }
            }

            /**
             * 连接融云失败
             * @param errorCode 错误码，可到官网 查看错误码对应的注释
             */
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Debug.e("-----onError--ErrorCode：" + errorCode.getValue());
            }
        });

    }

    @Override
    protected void handlerPassMsg(int target, int target1, Object obj) {

    }


    private void backdatatoserver() {
        OkHttpUtils.post().url("http://st.3dgogo.com/index/action_log/setActionLogApi.html")
                .addParams("action_uid", userId)
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


    public void Avatar(File file) {
//新建一个File，传入文件夹目录
        File file1 = new File(Environment.getExternalStorageDirectory(), "mywork");
//判断文件夹是否存在，如果不存在就创建，否则不创建
        if (!file1.exists()) {
            //通过file的mkdirs()方法创建目录中包含却不存在的文件夹
            file1.mkdirs();
        }
        Luban.with(this)
                .load(file)
                .ignoreBy(100)
                .setTargetDir(file1.getPath())
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(final File file) {
                        postFile(file);

                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                }).launch();
    }

    @Override
    public void backAddr(String options1, String options2, String options3) {
        //tvHome.setText(options1 + options2 + options3);
        permanent_city = options1 + options2 + options3;
    }


    private void callGallery() {
        Matisse.from(this)
                .choose(MimeType.of(MimeType.JPEG, MimeType.PNG, MimeType.BMP, MimeType.WEBP))//照片视频全部显示MimeType.allOf()
                .countable(true)//true:选中后显示数字;false:选中后显示对号

                .maxSelectable(1)//最大选择数量为9
                //.addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.dip_120))//图片显示表格的大小
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)//图像选择和预览活动所需的方向
                .thumbnailScale(0.85f)//缩放比例
                .theme(R.style.Matisse_Zhihu)//主题  暗色主题 R.style.Matisse_Dracula
                .imageEngine(new MyGlideEngine())//图片加载方式，Glide4需要自定义实现
                .capture(true) //是否提供拍照功能，兼容7.0系统需要下面的配置
                //参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
                .captureStrategy(new CaptureStrategy(true, "com.sendtion.matisse.fileprovider"))//存储到哪里
                .forResult(REQUEST_CODE_CHOOSE);//请求码
    }

    HttpParams params = new HttpParams();
    UploadImageBean imageBean;
    final UIProgressResponseCallBack listener = new UIProgressResponseCallBack() {
        @Override
        public void onUIResponseProgress(long bytesRead, long contentLength, boolean done) {
            int progress = (int) (bytesRead * 100 / contentLength);
            HttpLog.e(progress + "% ");
        }
    };
    private String longitude = "", latitude = "", headImg = "", background_img = "", permanent_city = "", position = "";


    public void postFile(File selectList) {
        params.put("file[" + 0 + "]", selectList, listener);
        EasyHttp.post("http://bisonchat.com/index/uploads/photosAll.html")
                .params("val", "minhader")
                .params(params)
                .execute(new ProgressDialogCallBack<String>(myProgressDialog, true, true) {
                    @Override
                    public void onSuccess(String succeed) {
                        dialog.dismiss();
                        Gson gons = new Gson();
                        imageBean = gons.fromJson(succeed, UploadImageBean.class);
                        if (imageBean.getMsg().contains("上传成功！")) {
                            uploaded=true;
                            switch (status) {
                                case "header"://我的头像
                                    headImg = imageBean.getUrl().get(0);
                                    Glide.with(context).load(ComantUtils.MyUrlHot1 + headImg).into(headimg);
                                    break;
                                case "background_img"://我的封面
                                    background_img = imageBean.getUrl().get(0);
                                    RequestOptions options = new RequestOptions();
                                    options.placeholder(R.drawable.tupian);
                                    Glide.with(context).load(ComantUtils.MyUrlHot1 + background_img).apply(options).into(backImage);
                                    break;


                            }

                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                        dialog.dismiss();
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    IProgressDialog myProgressDialog = new IProgressDialog() {
        @Override
        public Dialog getDialog() {
            ProgressDialog dialog = new ProgressDialog(Act_Regesite_Headim.this);
            dialog.setMessage("上传中...");
            return dialog;
        }
    };

    private void changeMyUserInfo() {
        dialog.show();


        birthDay = "1990-1-1";

        Debug.e("--------头像-00" + headImg);
        OkHttpUtils.post().url(ComantUtils.MyUrlHot + ComantUtils.updateUserInfoApi)

                .addParams("userId", userId + "")
                .addParams("note", note + "")
                .addParams("headImg", headImg)//头像url
                .addParams("background_img", background_img)
                .addParams("userPhone",userphone)
                .addParams("brithday", birthDay)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                dialog.dismiss();
            }

            @Override
            public void onResponse(String response, int id) {
                Debug.e("--------保存成功===" + response);
                Gson gson = new Gson();
                UpdateUserInfoBean bean = gson.fromJson(response, UpdateUserInfoBean.class);
                if (bean.getCode() == 200) {


                    MyApplication.getCurrentUserInfo().setUserPhone(bean.getInfo().getUserPhone());

                    MyApplication.getCurrentUserInfo().setNote(bean.getInfo().getNote());
                    MyApplication.getCurrentUserInfo().setHeadImg(bean.getInfo().getHeadImg());
                    MyApplication.getCurrentUserInfo().setBackground_img(bean.getInfo().getBackground_img());

                    MyApplication.getCurrentUserInfo().setBrithday(bean.getInfo().getBrithday());

                    setResult(10);

                    MyToast.show(getActivity(), "保存成功");
                }
                dialog.dismiss();
            }
        });
    }

}
