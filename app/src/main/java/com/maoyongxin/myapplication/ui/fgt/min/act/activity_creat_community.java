package com.maoyongxin.myapplication.ui.fgt.min.act;


import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
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

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;

import com.bumptech.glide.Glide;

import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.lykj.aextreme.afinal.utils.Debug;
import com.lykj.aextreme.afinal.utils.MyToast;
import com.maoyongxin.myapplication.R;

import com.maoyongxin.myapplication.common.BaseAct;

import com.maoyongxin.myapplication.common.ComantUtils;
import com.maoyongxin.myapplication.common.MyApplication;
import com.maoyongxin.myapplication.dialog.SimpleChoiceDialog;
import com.maoyongxin.myapplication.http.second.HttpNetWorkUtils;
import com.maoyongxin.myapplication.permission.RxPermissions;
import com.maoyongxin.myapplication.tool.FileUtil;
import com.maoyongxin.myapplication.tool.MyGlideEngine;
import com.maoyongxin.myapplication.tool.clickanimview.BamTextView;
import com.maoyongxin.myapplication.ui.fgt.community.bean.UploadImageBean;
import com.maoyongxin.myapplication.ui.fgt.min.act.bean.UpdateUserInfoBean;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
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
import java.util.List;


import io.reactivex.functions.Consumer;
import okhttp3.Call;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static com.maoyongxin.myapplication.common.ComantUtils.createcommunity;
import static com.zhouyou.http.EasyHttp.getContext;


public class activity_creat_community extends BaseAct {


    private   TextView btnDoCreate;

    private    EditText editCommunityName;
    private  EditText editCommunityNote;

    private Boolean updateimg=false;
    private  TextView tvCommunityAddress;
    private    ImageView iconBack;
    private    BamTextView re_location;
    private   ImageView img_communityCreate;
    private   EditText companyName;
    private RxPermissions rxPermissions;





    private AMap aMap;
    private String  myLatitude, myLngtitude;
    private String myAdCode;
    private String myAddress;
    private String mySimpleAddress;
    private String mCurrentCity;
    private boolean mPermissionCamera;
    private static final int REQCODE_TAKE_PHOTO = 1;
    private static final int REQCODE_PERMISSION_COVER = 10;
    private static final int REQCODE_LOCAL_PHOTO = 20;
    private String AVATAR_ORI = "avatar_ori.jpg";
    private String AVATAR_CUT = "avatar.jpg";
    private ArrayList<String> mCoverChoices =new ArrayList<>();;

    private boolean mPermissionWrite;
    private boolean mPermissionRead;
    private String picName;
    private File mTmpFile;
    private String myPicturePath;
    private List<File> picFile= new ArrayList<>();;
    private   ZLoadingDialog dialog;
    private String userimg;

    @Override
    public void initView() {
        dialog = new ZLoadingDialog(this);
        dialog.setLoadingBuilder(Z_TYPE.ROTATE_CIRCLE)//设置类型
                .setLoadingColor(Color.DKGRAY)//颜色
                .setHintText("数据加载中...")
                .setHintTextSize(16) // 设置字体大小 dp
                .setHintTextColor(Color.DKGRAY)  // 设置字体颜色
                .setDurationTime(0.5) // 设置动画时间百分比 - 0.5倍
                .setDialogBackgroundColor(Color.parseColor("#CCffffff")); // 设置背景色，默认白色

        rxPermissions = new RxPermissions(getActivity());
        companyName=getView(R.id.edit_communityName);
        btnDoCreate=getViewAndClick(R.id.btn_doCreate);

        editCommunityName=getViewAndClick(R.id.edit_communityName);
        editCommunityNote=getViewAndClick(R.id.edit_communityNote);

        tvCommunityAddress=getViewAndClick(R.id.tv_communityAddress);
        img_communityCreate=getViewAndClick(R.id.img_communityCreate);
        re_location=getViewAndClick(R.id.re_location);
        iconBack=getViewAndClick(R.id.icon_back);

        iconBack.setOnClickListener(this);
        btnDoCreate.setOnClickListener(this);
        img_communityCreate.setOnClickListener(this);
        tvCommunityAddress.setText(MyApplication.getMyCurrentLocation().getAddress());
        myLngtitude=MyApplication.getMyCurrentLocation().getLongtitude();
        myLatitude=MyApplication.getCurrentUserInfo().getLatitude();








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
                .forResult(23);//请求码
    }


    @Override
    public void updateUI() {

    }

    @Override
    protected void handlerPassMsg(int target, int target1, Object obj) {

    }

    @Override
    public void onViewClick(View v) {

        switch (v.getId())
        {
            case R.id.btn_doCreate:

                if (editCommunityName.getText().toString() != null && !editCommunityName.getText().toString().equals("")) {
                    if (editCommunityNote.getText().toString() != null && !editCommunityNote.getText().toString().equals("")) {
                        if (updateimg)
                        {
                            creatCommunity();
                        }
                        else {
                            MyToast.show(this,"请上传图片");
                        }

                    } else {
                        MyToast.show(this,"请输入服务号名称");
                    }
                } else {
                    MyToast.show(this,"请输入服务号描述");
                }

                break;
            case R.id.re_location:
                MyToast.show(getActivity(),"位置已经刷新");

                try {
                    tvCommunityAddress.setText(MyApplication.getMyCurrentLocation().getAddress());
                    myLngtitude=MyApplication.getMyCurrentLocation().getLongtitude();
                    myLatitude=MyApplication.getCurrentUserInfo().getLatitude();
                }
                catch (Exception e)
                {
                    e.getLocalizedMessage();
                    MyToast.show(this,"定位失败");
                }
                break;

            case  R.id.img_communityCreate:

                try
                {
                    rxPermissions
                            .request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                            .subscribe(new Consumer<Boolean>() {
                                @Override
                                public void accept(Boolean aBoolean) throws Exception {
                                    if (aBoolean) {
                                        //     status = "background_img";
                                        callGallery();
                                    } else {
                                        Toast.makeText(getActivity(), "请打开权限", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

                catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;
            case  R.id.icon_back:
                finish();



                break;
        }



    }

    @Override
    public void initData() {


    }


    @Override
    public int initLayoutId() {
        return R.layout.activity_creat_community;
    }

    @Override
    public void requestData() {

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 23) {
            List<String> dataPath = Matisse.obtainPathResult(data);
            for (String imageUri : dataPath) {
          dialog.show();
                Avatar(new File(imageUri));
            }
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
                .execute(new ProgressDialogCallBack<String>(myProgressDialog, true, true) {
                    @Override
                    public void onSuccess(String succeed) {
                    //    dialog.dismiss();
                        Gson gons = new Gson();
                        imageBean = gons.fromJson(succeed, UploadImageBean.class);
                        if (imageBean.getMsg().contains("上传成功！")) {

                                userimg = imageBean.getUrl().get(0);
                                    Glide.with(context).load(ComantUtils.MyUrlHot1 + userimg).into(img_communityCreate);
                            dialog.dismiss();
                            updateimg=true;
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
            ProgressDialog dialog = new ProgressDialog(activity_creat_community.this);
            dialog.setMessage("上传中...");
            return dialog;
        }
    };


    private void creatCommunity()
    {


        dialog.show();

        OkHttpUtils.post().url(ComantUtils.MyUrlHot + createcommunity)
                .addParams("userId", MyApplication.getCurrentUserInfo().getUserId())
                .addParams("communityName", editCommunityName.getText().toString())
                .addParams("communityNote", editCommunityNote.getText().toString())
                .addParams("areaCode", MyApplication.getMyCurrentLocation().getAdCode())
                .addParams("address", MyApplication.getMyCurrentLocation().getAddress())
                .addParams("addressName", MyApplication.getMyCurrentLocation().getAddress())//头像url
                .addParams("longitude",  MyApplication.getMyCurrentLocation().getLongtitude())
                .addParams("latitude",  MyApplication.getMyCurrentLocation().getLatitude())
                .addParams("communityImg",userimg)
                .addParams("companyName", companyName.getText().toString())//常住城市
                .addParams("companyId", "")//职位

                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                dialog.dismiss();
            }

            @Override
            public void onResponse(String response, int id) {

                Gson gson = new Gson();
                UpdateUserInfoBean bean = gson.fromJson(response, UpdateUserInfoBean.class);
                if (bean.getCode() == 200) {

                    setResult(10);
                    finish();
                    MyToast.show(getActivity(), "提交成功，请等待审核");
                    dialog.dismiss();
                }
                else
                {
                    MyToast.show(getActivity(), "您已经有服务号，请等待审核");
                }
                dialog.dismiss();
            }
        });
    }


}
