package com.maoyongxin.myapplication.ui;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.lykj.aextreme.afinal.utils.MyToast;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.bean.ClassifyApiBean;
import com.maoyongxin.myapplication.common.BaseAct;
import com.maoyongxin.myapplication.common.ComantUtils;
import com.maoyongxin.myapplication.permission.RxPermissions;
import com.maoyongxin.myapplication.tool.MyGlideEngine;
import com.maoyongxin.myapplication.tool.TimeAddTool;
import com.maoyongxin.myapplication.ui.fgt.community.bean.UploadImageBean;
import com.maoyongxin.myapplication.ui.fgt.min.act.Act_UserEditNew;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.HotCity;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;
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
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import okhttp3.Call;
import okhttp3.Response;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static com.zhouyou.http.EasyHttp.getContext;

public class act_fb_business extends BaseAct implements TimeAddTool.onBackDateTime, TimeAddTool.onBackAddr, TimeAddTool.onBackItem, TimeAddTool.onBackItem2 {
    private TextView tv_typechoic,business_strtTime,business_strtDate,tv_adresschoice;
    private List<String> typeData = new ArrayList<>();
    private List<String> startnoon=new ArrayList<>();
    private List<String> startTime=new ArrayList<>();
    private ClassifyApiBean typeBean;
    private String classify_id;
    private List<HotCity> hotCities;
    private ImageView businessImg;
    private RxPermissions rxPermissions;
    private TextView business_detailEdit;
    private Button business_push;
    private    Bundle detailImg ;
    private String BusinessImgUrl;

    private static final int REQUEST_CODE_CHOOSE = 23;//定义请求码常量
    private static final int REQUEST_CODE_Detail = 22;//定义请求码常量

    @Override
    protected void handlerPassMsg(int target, int target1, Object obj) {

    }

    @Override
    public int initLayoutId() {
        return  (R.layout.activity_act_fb_business);
    }

    @Override
    public void initView() {
        business_push=getViewAndClick(R.id.business_push);
        tv_typechoic=getViewAndClick(R.id.tv_typechoic);
        business_strtTime=getViewAndClick(R.id.business_strtTime);
        business_strtDate=getViewAndClick(R.id.business_strtDate);
        tv_adresschoice=getViewAndClick(R.id.tv_adresschoice);
        businessImg=getViewAndClick(R.id.businessImg);
        business_detailEdit=getViewAndClick(R.id.business_detailEdit);

        rxPermissions = new RxPermissions(getActivity());
        hotCities = new ArrayList<>();
        hotCities.add(new HotCity("北京", "北京", "101010100"));
        hotCities.add(new HotCity("成都", "四川", "101270101"));
        hotCities.add(new HotCity("上海", "上海", "101020100"));
        hotCities.add(new HotCity("广州", "广东", "101280101"));
        hotCities.add(new HotCity("深圳", "广东", "101280601"));
        hotCities.add(new HotCity("杭州", "浙江", "101210101"));
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




        postBackType();

        for(int i =0;i<24;i++)
        {
            startnoon.add(i+" 点 ");
        }
        for(int i=0;i<60;i++)
        {
            startTime.add(i+" 分 ");
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

        switch (v.getId())
        {

            case R.id.business_push:

                postBusiness();
                break;
            case R.id.business_detailEdit:

                 Intent intent=new Intent (getActivity(),act_business_detailEdit.class);


                startActivityForResult(intent ,REQUEST_CODE_Detail);

                break;

            case R.id.businessImg:
                rxPermissions .request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) {
                                if (aBoolean) {

                                    callGallery();
                                } else {
                                    Toast.makeText(getActivity(), "请打开权限", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                break;

            case R.id.tv_typechoic:
                if (typeData.size() == 0) {
                    MyToast.show(this, "还未获取到类型值！");
                    return;
                }
                new TimeAddTool().initNoLinkOptionsPicker(this, typeData, this);


                break;
            case R.id.business_strtDate:
                new TimeAddTool().initTimePicker(this, this);

                break;
            case R.id.business_strtTime:
                new TimeAddTool().initNoLinkOptionsPicker(this, startnoon,startTime,this);

                break;

            case R.id.tv_adresschoice:
              // startActivityForResult(act_business_adress.class,25);

                CityPicker.from(getActivity())
                        .enableAnimation(false)
                        .setAnimationStyle(R.style.DefaultCityPickerAnimation)
                        .setLocatedCity(null)
                        .setHotCities(hotCities)
                        .setOnPickListener(new OnPickListener() {
                            @Override
                            public void onPick(int position, City data) {
                                tv_adresschoice.setText(String.format("举办城市：%s", data.getName()));
                                Toast.makeText(
                                        getApplicationContext(),
                                        String.format("举办城市：%s", data.getName()),
                                        Toast.LENGTH_SHORT)
                                        .show();
                            }

                            @Override
                            public void onCancel() {
                                Toast.makeText(getApplicationContext(), "取消选择", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onLocate() {
                                //开始定位，这里模拟一下定位
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        CityPicker.from(getActivity()).locateComplete(new LocatedCity("成都", "四川", "101270101"), LocateState.SUCCESS);
                                    }
                                }, 1000);
                            }
                        })
                        .show();
                break;

        }

    }

    @Override
    public void backDate(String date) {
        business_strtDate.setText(date);

    }

    @Override
    public void backAddr(String options1, String options2, String options3) {

    }

    @Override
    public void backItem(int options) {
        tv_typechoic.setText(typeBean.getInfo().get(options).getName());
        classify_id = typeBean.getInfo().get(options).getId()+"";
    }


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

    @Override
    public void backItem(int options, int options1) {

        business_strtTime.setText(startnoon.get(options)+startTime.get(options1));

    }


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
            } else if (requestCode == REQUEST_CODE_Detail) {
                if(resultCode == Activity.RESULT_OK) { // 对应B里面的标志为成功
                    detailImg =  data.getExtras();
                    String imgurl=detailImg.getString("content");
                }


            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

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

    HttpParams params = new HttpParams();
    UploadImageBean imageBean;



    final UIProgressResponseCallBack listener = new UIProgressResponseCallBack() {
        @Override
        public void onUIResponseProgress(long bytesRead, long contentLength, boolean done) {
            int progress = (int) (bytesRead * 100 / contentLength);
            HttpLog.e(progress + "% ");
        }
    };
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
                            Glide.with(context).load(ComantUtils.MyUrlHot1 + imageBean.getUrl().get(0)).into(businessImg);
                            BusinessImgUrl=imageBean.getUrl().get(0);
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
            ProgressDialog dialog = new ProgressDialog(getActivity());
            dialog.setMessage("上传中...");
            return dialog;
        }
    };

    private  void  postBusiness()
    {

        OkHttpUtils.post().url("http://bisonchat.com/index/commerce_activity/createCommerceActivityApi.html")
                .addParams("img", "")
                .addParams("activity_title", "")
                .addParams("type_id", "")
                .addParams("admin_organ", "")
                .addParams("activity_time", "")
                .addParams("activity_site", "")
                .addParams("activity_intro", "")
                .addParams("phone", "")
                .addParams("email", "")
                .addParams("activity_guests", "")


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
