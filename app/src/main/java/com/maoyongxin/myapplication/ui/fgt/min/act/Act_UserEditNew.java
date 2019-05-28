package com.maoyongxin.myapplication.ui.fgt.min.act;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.jky.baselibrary.widget.TitleBar;
import com.lykj.aextreme.afinal.utils.Debug;
import com.lykj.aextreme.afinal.utils.MyToast;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.common.BaseAct;
import com.maoyongxin.myapplication.common.ComantUtils;
import com.maoyongxin.myapplication.common.MyApplication;
import com.maoyongxin.myapplication.dialog.SimpleChoiceDialog;
import com.maoyongxin.myapplication.http.callback.EntityCallBack;
import com.maoyongxin.myapplication.http.entity.UserInfoService;
import com.maoyongxin.myapplication.http.request.ReqUserServer;
import com.maoyongxin.myapplication.http.response.ChangeUserInfoResponse;
import com.maoyongxin.myapplication.permission.RxPermissions;
import com.maoyongxin.myapplication.tool.FileUtil;
import com.maoyongxin.myapplication.tool.MyGlideEngine;
import com.maoyongxin.myapplication.tool.SDCardUtil;
import com.maoyongxin.myapplication.tool.TimeAddTool;
import com.maoyongxin.myapplication.ui.fgt.community.act.Act_TopicPublishing;
import com.maoyongxin.myapplication.ui.fgt.community.bean.UploadImageBean;
import com.maoyongxin.myapplication.ui.fgt.community.bean.UserFriendsFollowApiBean;
import com.maoyongxin.myapplication.ui.fgt.min.act.bean.UpdateUserInfoBean;
import com.maoyongxin.myapplication.ui.fgt.min.act.view.ContainsEmojiEditText;

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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.functions.Consumer;
import okhttp3.Call;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static com.zhouyou.http.EasyHttp.getContext;

/**
 * 头像
 */
public class Act_UserEditNew extends BaseAct implements TimeAddTool.onBackAddr {

    TitleBar TitleBarEdit;
    ImageView editHeader;
    TextView tvUserNikeName;
    LinearLayout lineNikeName;
    TextView tvUserBirthDay;
    LinearLayout lineBirthDay;
    TextView tvSex;
    LinearLayout lineSex;
    TextView tvCommunity;
    LinearLayout lineCommunity;
    EditText tvNote;

    TextView tvHome;
    LinearLayout lineHome;
    TextView tvWork;
    LinearLayout lineWork;
    EditText tvPhone;
    LinearLayout linePhone;
    TextView background_headimg;
    //    private ProgressDialog mProgressDialog;
    private boolean mPermissionCamera;
    private static final int REQCODE_TAKE_PHOTO = 1;
    private static final int REQCODE_PERMISSION_COVER = 10;
    private static final int REQCODE_LOCAL_PHOTO = 20;
    private String AVATAR_ORI = "avatar_ori.jpg";
    private String AVATAR_CUT = "avatar.jpg";
    private ArrayList<String> mCoverChoices;
    private boolean mPermissionWrite;
    private boolean mPermissionRead;
    private File mTmpFile;
    private String myHeadPath;
    private String picName;
    private DatePickerDialog datePickerDialog;
    private int year, month, day;
    private String birthDay;
    private String nickName;
    private String note;
    private String phoneNumber;
    private int sex;
    private String localhead;
    private RxPermissions rxPermissions;
    private EditText user_position;
    private ImageView backImage;

    @Override
    public int initLayoutId() {
        return R.layout.act_usereditnew;
    }

    @Override
    public void initView() {
        hideHeader();
        setOnClickListener(R.id.userEdit_back);
        setOnClickListener(R.id.commit);
        rxPermissions = new RxPermissions(getActivity());
        editHeader = getViewAndClick(R.id.edit_header);
        tvUserNikeName = getView(R.id.tv_user_nikeName);
        lineNikeName = getViewAndClick(R.id.line_nikeName);
        tvUserBirthDay = getView(R.id.tv_user_birthDay);
        lineBirthDay = getViewAndClick(R.id.line_birthDay);
        tvSex = getView(R.id.tv_sex);
        lineSex = getViewAndClick(R.id.line_sex);
        tvCommunity = getView(R.id.tv_community);
        lineCommunity = getViewAndClick(R.id.line_community);
        tvNote = getView(R.id.tv_note);

        tvHome = getView(R.id.tv_home);
        lineHome = getViewAndClick(R.id.line_home);
        tvWork = getView(R.id.tv_work);
        lineWork = getView(R.id.line_work);
        tvPhone = getView(R.id.tv_phone);
        linePhone = getView(R.id.line_phone);
        setOnClickListener(R.id.background_img);
        user_position = getView(R.id.user_position);
        backImage = getView(R.id.min_haderback);
        background_headimg=getViewAndClick(R.id.background_headimg);
        if (MyApplication.getCurrentUserInfo().getNote() != null) {
            tvNote.setText(MyApplication.getCurrentUserInfo().getNote());
        }
    }

    private void setSexyText() {
        if (sex == 0) {
            tvSex.setText("保密");
        } else if (sex == 1) {
            tvSex.setText("男");
        } else {
            tvSex.setText("女");
        }
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
        mCoverChoices = new ArrayList<>();
//        mCoverChoices.add(getString(R.string.take_photo));
        mCoverChoices.add(getString(R.string.local_photo));
        if (MyApplication.getCurrentUserInfo().getSex() == null) {
            sex = 0;
        } else if (MyApplication.getCurrentUserInfo().getSex().equals("1")) {
            sex = 1;
        } else {
            sex = 2;
        }
        nickName = MyApplication.getCurrentUserInfo().getUserName();
        birthDay = MyApplication.getCurrentUserInfo().getBrithday();
        myHeadPath = MyApplication.getCurrentUserInfo().getHeadImg();
        localhead = myHeadPath;
        phoneNumber = MyApplication.getCurrentUserInfo().getUserPhone();
        note = MyApplication.getCurrentUserInfo().getNote();
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;
        day = c.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(Act_UserEditNew.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                year = i;
                month = i1 + 1;
                day = i2;
                String months, days;
                if (month < 10) {
                    months = "0" + month;
                } else {
                    months = month + "";
                }
                if (day < 10) {
                    days = "0" + day;
                } else {
                    days = day + "";
                }
                birthDay = year + "-" + months + "-" + days + "-";
                tvUserBirthDay.setText(birthDay);
            }
        }, year, month, day);
        Glide.with(context).load(MyApplication.getCurrentUserInfo().getBackground_img()).into(backImage);
        Glide.with(getActivity()).load(MyApplication.getCurrentUserInfo().getHeadImg()).into(editHeader);
        setSexyText();
        tvPhone.setText(MyApplication.getCurrentUserInfo().getUserPhone());
        tvUserBirthDay.setText(MyApplication.getCurrentUserInfo().getBrithday());
        tvUserNikeName.setText(MyApplication.getCurrentUserInfo().getUserName());
    }

    @Override
    public void requestData() {

    }

    @Override
    public void updateUI() {

    }

    String status = "";

    @Override
    public void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.edit_header:
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
            case R.id.background_headimg:
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

            case R.id.line_nikeName:
                setNickName();
                break;
            case R.id.line_birthDay:
                datePickerDialog.show();
                break;
            case R.id.line_sex:
                showSexyChoiceDialog();
                break;
            case R.id.line_community://小区名称
                startActivity(new Intent(getActivity(), Act_AddressHomeCheck.class));
                break;
            case R.id.userEdit_back:
                finish();
                break;
            case R.id.commit:
                changeMyUserInfo();
                break;
            case R.id.line_home:
                new TimeAddTool().showPickerView(this, this);
                break;

            case R.id.min_haderback:

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
            case R.id.background_img:
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
        }
    }

    @Override
    protected void handlerPassMsg(int target, int target1, Object obj) {

    }

    /**
     * 修改昵称
     */
    private void setNickName() {
        final ContainsEmojiEditText inputServer = new ContainsEmojiEditText(Act_UserEditNew.this);
        AlertDialog.Builder builder = new AlertDialog.Builder(Act_UserEditNew.this);
        builder.setTitle("请输入昵称").setView(inputServer)
                .setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                nickName = inputServer.getText().toString();
                tvUserNikeName.setText(nickName);
            }
        });
        builder.show();
    }

    /**
     * 性别选择
     */
    private void showSexyChoiceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("你好").setMessage("请选择您的性别").setPositiveButton("高富帅", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sex = 1;
                setSexyText();
            }
        }).setNegativeButton("白富美", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sex = 2;
                setSexyText();
            }
        }).setNeutralButton("让你们猜", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sex = 0;
                setSexyText();
            }
        }).setCancelable(false).show();
    }

    private String longitude = "", latitude = "", headImg = "", background_img = "", permanent_city = "", position = "";

    private void changeMyUserInfo() {
        dialog.show();
        position = user_position.getText().toString();
        if (birthDay.equals("")) {
            birthDay = "1990-1-1";
        }
        Debug.e("--------头像-00" + headImg);
        OkHttpUtils.post().url(ComantUtils.MyUrlHot + ComantUtils.updateUserInfoApi)
                .addParams("userId", MyApplication.getCurrentUserInfo().getUserId())
                .addParams("userName", tvUserNikeName.getText().toString())
                .addParams("longitude", longitude)
                .addParams("latitude", latitude)
                .addParams("note", tvNote.getText()+ "")
                .addParams("headImg", headImg)//头像url
                .addParams("background_img", background_img)
                .addParams("sex", sex + "")
                .addParams("brithday", birthDay)
                .addParams("permanent_city", permanent_city)//常住城市
                .addParams("position", position)//职位
                .addParams("userPhone", tvPhone.getText().toString())
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
                    if (bean.getInfo() != null) {
                        MyApplication.getCurrentUserInfo().setUserName(bean.getInfo().getUserName());
                    }
                    MyApplication.getCurrentUserInfo().setLongitude(bean.getInfo().getLongitude());
                    MyApplication.getCurrentUserInfo().setLatitude(bean.getInfo().getLatitude());
                    MyApplication.getCurrentUserInfo().setToken(bean.getInfo().getBrithday());
                    MyApplication.getCurrentUserInfo().setUserPhone(bean.getInfo().getUserPhone());
                    MyApplication.getCurrentUserInfo().setVipNum(bean.getInfo().getVipNum());
                    MyApplication.getCurrentUserInfo().setNote(bean.getInfo().getNote());
                    MyApplication.getCurrentUserInfo().setHeadImg(bean.getInfo().getHeadImg());
                    MyApplication.getCurrentUserInfo().setBackground_img(bean.getInfo().getBackground_img());
                    MyApplication.getCurrentUserInfo().setSex(bean.getInfo().getSex());
                    MyApplication.getCurrentUserInfo().setBrithday(bean.getInfo().getBrithday());
                    MyApplication.getCurrentUserInfo().setPermanent_city(bean.getInfo().getPermanent_city());
                    MyApplication.getCurrentUserInfo().setPosition(bean.getInfo().getPosition());
                    setResult(10);
                    finish();
                    MyToast.show(getActivity(), "保存成功");
                }
                dialog.dismiss();
            }
        });
    }

    @Override
    public void backAddr(String options1, String options2, String options3) {
        tvHome.setText(options1 + options2 + options3);
        permanent_city = options1 + options2 + options3;
    }

    /**
     * 调用图库选择
     */
    private void callGallery() {

        Matisse.from(this)
                .choose(MimeType.of(MimeType.JPEG, MimeType.PNG, MimeType.GIF))//照片视频全部显示MimeType.allOf()
                .countable(true)//true:选中后显示数字;false:选中后显示对号
                .maxSelectable(3)//最大选择数量为9
                //.addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.dip_120))//图片显示表格的大小
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)//图像选择和预览活动所需的方向
                .thumbnailScale(0.85f)//缩放比例
                .theme(R.style.Matisse_Zhihu)//主题  暗色主题 R.style.Matisse_Dracula
                .imageEngine(new MyGlideEngine())//图片加载方式，Glide4需要自定义实现
                .capture(false) //是否提供拍照功能，兼容7.0系统需要下面的配置
                //参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
                .captureStrategy(new CaptureStrategy(true, "com.sendtion.matisse.fileprovider"))//存储到哪里
                .forResult(REQUEST_CODE_CHOOSE);//请求码
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

    /**
     * 图片压缩
     */
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

    final UIProgressResponseCallBack listener = new UIProgressResponseCallBack() {
        @Override
        public void onUIResponseProgress(long bytesRead, long contentLength, boolean done) {
            int progress = (int) (bytesRead * 100 / contentLength);
            HttpLog.e(progress + "% ");
        }
    };
    HttpParams params = new HttpParams();
    UploadImageBean imageBean;

    public void postFile(File selectList) {
        params.put("file[" + 0 + "]", selectList, listener);
        EasyHttp.post("http://bisonchat.com/index/uploads/photosAll.html")
                .params("val", "minhader")
                .params(params)
                .execute(new ProgressDialogCallBack<String>(mProgressDialog, true, true) {
                    @Override
                    public void onSuccess(String succeed) {
                        dialog.dismiss();
                        Gson gons = new Gson();
                        imageBean = gons.fromJson(succeed, UploadImageBean.class);
                        if (imageBean.getMsg().contains("上传成功！")) {
                            switch (status) {
                                case "header"://我的头像
                                    headImg = imageBean.getUrl().get(0);
                                    Glide.with(context).load(ComantUtils.MyUrlHot1 + headImg).into(editHeader);
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

    IProgressDialog mProgressDialog = new IProgressDialog() {
        @Override
        public Dialog getDialog() {
            ProgressDialog dialog = new ProgressDialog(Act_UserEditNew.this);
            dialog.setMessage("上传中...");
            return dialog;
        }
    };

}
