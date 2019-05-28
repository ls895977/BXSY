package com.maoyongxin.myapplication.ui.fgt.community.act;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.lykj.aextreme.afinal.utils.Debug;
import com.lykj.aextreme.afinal.utils.MyToast;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.common.BaseAct;
import com.maoyongxin.myapplication.common.MyApplication;
import com.maoyongxin.myapplication.permission.RxPermissions;
import com.maoyongxin.myapplication.tool.LocationTool;
import com.maoyongxin.myapplication.ui.fgt.community.bean.UploadImageBean;
import com.maoyongxin.myapplication.ui.fgt.community.bean.UploadVideoBean;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.body.UIProgressResponseCallBack;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpParams;
import com.zhouyou.http.subsciber.IProgressDialog;
import com.zhouyou.http.utils.HttpLog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import io.reactivex.functions.Consumer;

import static com.zhouyou.http.EasyHttp.getContext;

/**
 * 社区=动态=发布动态=视频
 */
public class Act_UploadVideo extends BaseAct implements LocationTool.onBackLoCtion {
    private ImageView fb_video_img;
    private TextView curr_addr;
    private RxPermissions rxPermissions;
    private EditText inputContext;
    @Override
    protected void handlerPassMsg(int target, int target1, Object obj) {

    }
    @Override
    public int initLayoutId() {
        return R.layout.act_uploadvideo;
    }
    @Override
    public void initView() {
        hideHeader();
        inputContext = getView(R.id.publicshpictures_input);
        rxPermissions = new RxPermissions(getActivity());
        setOnClickListener(R.id.my_video);
        setOnClickListener(R.id.publicshpictures_send);
        setOnClickListener(R.id.friends_back);
        setOnClickListener(R.id.my_addr);
        curr_addr = getView(R.id.curr_addr);
        fb_video_img = getViewAndClick(R.id.fb_video_img);
    }

    String filePath;
    LocationTool locationTool;

    @Override
    public void initData() {
        filePath = getIntent().getStringExtra("file");
        Glide.with(context).load(filePath).into(fb_video_img);
        locationTool = new LocationTool(context, this);
        locationTool.startLocation();
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
            case R.id.publicshpictures_send://上传
                if (TextUtils.isEmpty(filePath)) {
                    return;
                }
                if (TextUtils.isEmpty(inputContext.getText().toString())) {
                    MyToast.show(context, "您应该说点什么！");
                    return;
                }
                postImageFile(saveBitmap(this, getLocalVideoThumbnail(filePath)));
                break;
            case R.id.friends_back:
                finish();
                break;
            case R.id.fb_video_img:
                Intent intent = new Intent();
                intent.putExtra("file", filePath);
                startAct(intent, Act_Video.class);
                break;
            case R.id.my_addr://我的位置
                rxPermissions
                        .request(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) {
                                if (aBoolean) {
                                    if (curr_addr.getText().toString().equals("新加坡")) {
                                        MyToast.show(context, "正在获取地址，请稍后！");
                                        locationTool.startLocation();
                                        return;
                                    }
                                    Intent intent = new Intent();
                                    intent.setClass(getContext(), Act_Location.class);
                                    intent.putExtra("curAddr", curr_addr.getText().toString());
                                    startActivityForResult(intent, 10);
                                } else {
                                    Toast.makeText(getActivity(), "请打开定位权限", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                break;
        }
    }

    @Override
    public void backAdd(AMapLocation location) {
        curr_addr.setText(location.getCity());
        locationTool.stopLocation();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == 10) {
            String addr = data.getStringExtra("name");
            curr_addr.setText(addr);
            return;
        }
    }

    final UIProgressResponseCallBack listener = new UIProgressResponseCallBack() {
        @Override
        public void onUIResponseProgress(long bytesRead, long contentLength, boolean done) {
            int progress = (int) (bytesRead * 100 / contentLength);
            HttpLog.e(progress + "% ");
        }
    };
    HttpParams params = new HttpParams();
    private ProgressDialog dialog;
    private IProgressDialog mProgressDialog = new IProgressDialog() {
        @Override
        public Dialog getDialog() {
            if (dialog == null) {
                dialog = new ProgressDialog(context);
                dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// 设置进度条的形式为圆形转动的进度条
                dialog.setMessage("正在上传...");
                // 设置提示的title的图标，默认是没有的，如果没有设置title的话只设置Icon是不会显示图标的
                dialog.setTitle("文件上传");
                dialog.setMax(100);
            }
            return dialog;
        }
    };
    UploadVideoBean uploadVideoBean;
    UploadImageBean imageBean;

    /**
     * 上传图片
     *
     * @param path
     */
    public void postImageFile(final String path) {
        Debug.e("---------" + path);
        final File file = new File(path);
        params.put("file[" + 0 + "]", file, listener);
        EasyHttp.post("http://bisonchat.com/index/uploads/photosAll.html")
                .params("val", "dynamicImages")
                .params(params)
                .execute(new ProgressDialogCallBack<String>(mProgressDialog, true, true) {
                    @Override
                    public void onSuccess(String succeed) {
                        Gson gons = new Gson();
                        imageBean = gons.fromJson(succeed, UploadImageBean.class);
                        if (imageBean.getMsg().contains("上传成功！")) {
                            postFile();
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 上传视频
     *
     */
    public void postFile() {
        File file = new File(filePath);
        EasyHttp.post("http://bisonchat.com/index/uploads/files_a")
                .params("val", "dynamicVideo")
                .params("file", file, listener)
                .execute(new ProgressDialogCallBack<String>(mProgressDialog, true, true) {
                    @Override
                    public void onSuccess(String succeed) {
                        Debug.e("----上传视频---onSuccess---"+succeed);
                        Gson gons = new Gson();
                        uploadVideoBean = gons.fromJson(succeed, UploadVideoBean.class);
                        if (uploadVideoBean.getMsg().contains("上传成功！")) {
                            postCradeFile();
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                        Debug.e("----上传视频---onError---"+ e.getMessage());
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void postCradeFile() {
        params.put("images[" + 0 + "]", imageBean.getUrl().get(0));
        params.put("video", uploadVideoBean.getUrl());
        EasyHttp.post("http://bisonchat.com/index/user_dynamic/createUserDynamicApi")
                .params("userId", MyApplication.getCurrentUserInfo().getUserId())
                .params("dynamicContent", new String(Base64.encode(inputContext.getText().toString().getBytes(), Base64.DEFAULT)))
                .params(params)
                .execute(new ProgressDialogCallBack<String>(mProgressDialog, true, true) {
                    @Override
                    public void onSuccess(String succeed) {
                        if (succeed.contains("创建动态成功")) {
                            Toast.makeText(getContext(), "发布动态成功！", Toast.LENGTH_SHORT).show();
                            setResult(10);
                            finish();
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    /**
     * 获取本地视频的第一帧
     *
     * @param filePath
     * @return
     */
    public Bitmap getLocalVideoThumbnail(String filePath) {
        String imgpath = filePath.replace(".mp4", ".png");
        Bitmap bitmap = null;
        if (fileIsExists(imgpath)) {
            bitmap = BitmapFactory.decodeFile(imgpath);
            return bitmap;
        }


        //MediaMetadataRetriever 是android中定义好的一个类，提供了统一
        //的接口，用于从输入的媒体文件中取得帧和元数据；
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            //根据文件路径获取缩略图
            retriever.setDataSource(filePath);

            //获得第一帧图片
            bitmap = retriever.getFrameAtTime();

//            File file = new File(imgpath);
////            Log.e("name",file.getName());
//            saveCropPic(bitmap, file);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            retriever.release();
        }
        return bitmap;
    }

    //判断文件是否存在
    public boolean fileIsExists(String strFile) {
        try {
            File f = new File(strFile);
            if (!f.exists()) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

        return true;
    }

    /**
     * 保存bitmap到本地
     *
     * @param context
     * @param mBitmap
     * @return
     */
    private static final String SD_PATH = "/sdcard/dskqxt/pic/";
    private static final String IN_PATH = "/dskqxt/pic/";

    public static String saveBitmap(Context context, Bitmap mBitmap) {
        String savePath;
        File filePic;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            savePath = SD_PATH;
        } else {
            savePath = context.getApplicationContext().getFilesDir()
                    .getAbsolutePath()
                    + IN_PATH;
        }
        try {
            filePic = new File(savePath + generateFileName() + ".jpg");
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filePic);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        return filePic.getAbsolutePath();
    }

    /**
     * 随机生产文件名
     *
     * @return
     */
    private static String generateFileName() {
        return UUID.randomUUID().toString();
    }
}
