package com.maoyongxin.myapplication.ui.fgt.community.act;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.lykj.aextreme.afinal.utils.ACache;
import com.lykj.aextreme.afinal.utils.Debug;
import com.lykj.aextreme.afinal.utils.MyToast;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.bean.MessageBean;
import com.maoyongxin.myapplication.common.AppConfig;
import com.maoyongxin.myapplication.common.BaseAct;
import com.maoyongxin.myapplication.common.MyApplication;
import com.maoyongxin.myapplication.permission.RxPermissions;
import com.maoyongxin.myapplication.tool.LocationTool;
import com.maoyongxin.myapplication.ui.fgt.community.adapter.GridImageAdapter;
import com.maoyongxin.myapplication.ui.fgt.community.bean.UploadImageBean;
import com.maoyongxin.myapplication.view.FullyGridLayoutManager;
import com.sendtion.xrichtext.RichTextEditor;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.body.UIProgressResponseCallBack;
import com.zhouyou.http.callback.ProgressDialogCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.subsciber.IProgressDialog;
import com.zhouyou.http.utils.HttpLog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.github.rockerhieu.emojicon.EmojiconEditText;
import io.reactivex.functions.Consumer;
import okhttp3.Call;

import static com.zhouyou.http.EasyHttp.getContext;

/**
 * 社区=动态=发布动态=图片
 */
public class Act_PublishPictures extends BaseAct implements LocationTool.onBackLoCtion {
    private EmojiconEditText inputContext;
    private RecyclerView recyclerIssueColumn;
    private List<LocalMedia> selectList = new ArrayList<>();
    private GridImageAdapter mGridImageAdapter;
    private int chooseMode = PictureMimeType.ofImage();
    private int maxSelectNum = 9;
    private int themeId;
    private TextView status, statusTitle, curr_addr;
    private RxPermissions rxPermissions;
    private ACache aCache;
    private String is_anymit = "1";
    private ZLoadingDialog mProgressDialog;

    @Override
    protected void handlerPassMsg(int target, int target1, Object obj) {

    }

    @Override
    public int initLayoutId() {
        return R.layout.act_publishpictures;
    }

    @Override
    public void initView() {
        hideHeader();
        aCache = ACache.get(this);
        rxPermissions = new RxPermissions(getActivity());
        curr_addr = getView(R.id.curr_addr);
        statusTitle = getView(R.id.publicshpictures_status_itlte);
        status = getViewAndClick(R.id.publicshpictures_status);
        statusTitle.setText("公开");
        status.setSelected(true);
        themeId = R.style.picture_default_style;
        inputContext = getView(R.id.publicshpictures_input);
        setOnClickListener(R.id.friends_back);
        setOnClickListener(R.id.publicshpictures_send);
        setOnClickListener(R.id.my_addr);
        recyclerIssueColumn = getView(R.id.recycler_issue_column);
        FullyGridLayoutManager manager = new FullyGridLayoutManager(Act_PublishPictures.this, 3, GridLayoutManager.VERTICAL, false);
        recyclerIssueColumn.setLayoutManager(manager);
        mGridImageAdapter = new GridImageAdapter(Act_PublishPictures.this, onAddPicClickListener);
        mGridImageAdapter.setList(selectList);
        recyclerIssueColumn.setAdapter(mGridImageAdapter);
        mGridImageAdapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (selectList.size() > 0) {
                    LocalMedia media = selectList.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            PictureSelector.create(Act_PublishPictures.this).themeStyle(themeId).openExternalPreview(position, selectList);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(Act_PublishPictures.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(Act_PublishPictures.this).externalPictureAudio(media.getPath());
                            break;
                    }
                }
            }
        });
        mProgressDialog = new ZLoadingDialog(this);
        mProgressDialog.setLoadingBuilder(Z_TYPE.ROTATE_CIRCLE)//设置类型
                .setLoadingColor(Color.DKGRAY)//颜色
                .setHintText("图片上传中...")
                .setHintTextSize(16) // 设置字体大小 dp
                .setHintTextColor(Color.DKGRAY)  // 设置字体颜色
                .setDurationTime(0.5) // 设置动画时间百分比 - 0.5倍
                .setDialogBackgroundColor(Color.parseColor("#CCffffff")); // 设置背景色，默认白色
    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            PictureSelector.create(Act_PublishPictures.this)
                    .openGallery(chooseMode)
                    .maxSelectNum(maxSelectNum)// 最大图片选择数量
                    .theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                    .minSelectNum(1)// 最小选择数量
                    .imageSpanCount(4)// 每行显示个数
                    .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                    .selectionMode(PictureConfig.MULTIPLE)
                    .isCamera(true)
                    .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                    .synOrAsy(true)//同步true或异步false 压缩 默认同步
                    .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                    .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                    .minimumCompressSize(100)// 小于100kb的图片不压缩
                    .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
        }
    };
    LocationTool locationTool;

    @Override
    public void initData() {
        locationTool = new LocationTool(context, this);
        locationTool.startLocation();
    }

    @Override
    public void requestData() {

    }

    @Override
    public void updateUI() {

    }

    private List<File> files = new ArrayList<>();

    @Override
    public void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.friends_back:
                finish();
                break;
            case R.id.publicshpictures_send://发布
                if (selectList.size() == 0) {
                    MyToast.show(context, "请选择一张图片！");
                    return;
                }
                if (TextUtils.isEmpty(inputContext.getText().toString())) {
                    MyToast.show(context, "您应该说点什么！");
                    return;
                }
                postFile(selectList);
                break;
            case R.id.publicshpictures_status:
                if (status.isSelected()) {
                    status.setSelected(false);
                    statusTitle.setText("仅对自己可见");
                    is_anymit = "0";
                } else {
                    status.setSelected(true);
                    statusTitle.setText("公开");
                    is_anymit = "1";
                }
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == 10) {
            String addr = data.getStringExtra("name");
            curr_addr.setText(addr);
            return;
        }
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    List<LocalMedia> selectList1 = PictureSelector.obtainMultipleResult(data);
                    for (LocalMedia media : selectList1) {
                        Log.i("图片-----》", media.getPath());
                        selectList.add(media);
                    }
                    mGridImageAdapter.setList(selectList);
                    mGridImageAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    @Override
    public void backAdd(AMapLocation location) {
        curr_addr.setText(location.getCity());
        locationTool.stopLocation();
    }

    //    private ProgressDialog dialog;
//    private IProgressDialog mProgressDialog = new IProgressDialog() {
//        @Override
//        public Dialog getDialog() {
//            if (dialog == null) {
//                dialog = new ProgressDialog(context);
//                dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// 设置进度条的形式为圆形转动的进度条
//                dialog.setMessage("正在上传...");
//                // 设置提示的title的图标，默认是没有的，如果没有设置title的话只设置Icon是不会显示图标的
//                dialog.setTitle("文件上传");
//                dialog.setMax(100);
//            }
//            return dialog;
//        }
//    };
//    final UIProgressResponseCallBack listener = new UIProgressResponseCallBack() {
//        @Override
//        public void onUIResponseProgress(long bytesRead, long contentLength, boolean done) {
//            int progress = (int) (bytesRead * 100 / contentLength);
//            HttpLog.e(progress + "% ");
//            ((ProgressDialog) mProgressDialog.getDialog()).setProgress(progress);
//            if (done) {//完成
//                ((ProgressDialog) mProgressDialog.getDialog()).setMessage("上传完整");
//            }
//        }
//    };
    HttpParams params = new HttpParams();
    UploadImageBean imageBean;

    public void postFile(List<LocalMedia> selectList) {
        for (int i = 0; i < selectList.size(); i++) {
            File file = new File(selectList.get(i).getPath());
            params.put("file[" + i + "]", file);
        }
//        EasyHttp.post("http://bisonchat.com/index/uploads/photosAll.html")
//                .params("val", "dynamicImages")
//                .params(params)
//                .execute(new ProgressDialogCallBack<String>(mProgressDialog, true, true) {
//                    @Override
//                    public void onSuccess(String succeed) {
//                        Gson gons = new Gson();
//                        imageBean = gons.fromJson(succeed, UploadImageBean.class);
//                        if (imageBean.getMsg().contains("上传成功！")) {
//                            postCradeFile();
//                        }
//                    }
//
//                    @Override
//                    public void onError(ApiException e) {
//                        super.onError(e);
//                        Debug.e("-------" + e.getMessage());
//                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
        mProgressDialog.show();
        OkGo.<String>post("http://bisonchat.com/index/uploads/photosAll.html")
                .tag(this)//
                .params(params)
                .params("val", "dynamicImages")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        //注意这里已经是在主线程了
                        String data = response.body();//这个就是返回来的结果
                        Gson gons = new Gson();
                        imageBean = gons.fromJson(data, UploadImageBean.class);
                        if (imageBean.getMsg().contains("上传成功！")) {
                            postCradeFile();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        mProgressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void postCradeFile() {
        for (int i = 0; i < imageBean.getUrl().size(); i++) {
            params.put("images[" + i + "]", imageBean.getUrl().get(i));
        }
//        EasyHttp.post("http://bisonchat.com/index/user_dynamic/createUserDynamicApi")
//                .params("userId", MyApplication.getCurrentUserInfo().getUserId())
//                .params("dynamicContent", new String(Base64.encode(inputContext.getText().toString().getBytes(), Base64.DEFAULT)))
//                .params("is_anonymity", is_anymit)
//                .params(params)
//                .execute(new ProgressDialogCallBack<String>(mProgressDialog, true, true) {
//                    @Override
//                    public void onSuccess(String succeed) {
//                        if (succeed.contains("创建动态成功")) {
//                            Toast.makeText(getContext(), "发布动态成功！", Toast.LENGTH_SHORT).show();
//                            setResult(10);
//                            finish();
//                        }
//                    }
//
//                    @Override
//                    public void onError(ApiException e) {
//                        super.onError(e);
//                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });

        OkGo.<String>post("http://bisonchat.com/index/user_dynamic/createUserDynamicApi")
                .tag(this)
                .params("userId", MyApplication.getCurrentUserInfo().getUserId())
                .params("dynamicContent", new String(Base64.encode(inputContext.getText().toString().getBytes(), Base64.DEFAULT)))
                .params("is_anonymity", is_anymit)
                .params(params)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        //注意这里已经是在主线程了
                        String data = response.body();//这个就是返回来的结果
//                        Gson gons = new Gson();
//                        imageBean = gons.fromJson(data, UploadImageBean.class);
                        if (data.contains("创建动态成功")) {
                            Toast.makeText(getContext(), "发布动态成功！", Toast.LENGTH_SHORT).show();
                            setResult(10);
                            finish();
                        }
                        mProgressDialog.dismiss();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        mProgressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


}
