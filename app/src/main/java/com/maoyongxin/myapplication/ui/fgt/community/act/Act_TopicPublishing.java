package com.maoyongxin.myapplication.ui.fgt.community.act;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lykj.aextreme.afinal.utils.Debug;
import com.lykj.aextreme.afinal.utils.MyToast;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.common.BaseAct;
import com.maoyongxin.myapplication.common.ComantUtils;
import com.maoyongxin.myapplication.common.MyApplication;
import com.maoyongxin.myapplication.tool.MyGlideEngine;
import com.maoyongxin.myapplication.tool.SDCardUtil;
import com.maoyongxin.myapplication.tool.StatusBarUtil;
import com.maoyongxin.myapplication.tool.UtilsTool;
import com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.fgt.bean.UserEvent;
import com.maoyongxin.myapplication.ui.fgt.community.bean.TopicPublishingBean;
import com.maoyongxin.myapplication.ui.fgt.community.bean.UploadImageBean;
import com.sendtion.xrichtext.RichTextEditor;
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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.github.rockerhieu.emojicon.EmojiconEditText;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;
import io.rong.eventbus.EventBus;

import static com.zhouyou.http.EasyHttp.getContext;

/**
 * 发布热门话题
 */
public class Act_TopicPublishing extends BaseAct {
    private RichTextEditor et_new_content;
    private ProgressDialog insertDialog;
    private EmojiconEditText param_title;
    private static final int REQUEST_CODE_CHOOSE = 23;//定义请求码常量

    @Override
    protected void handlerPassMsg(int target, int target1, Object obj) {

    }

    @Override
    public int initLayoutId() {
        return R.layout.act_topicpublishing;
    }

    @Override
    public void initView() {
        hideHeader();
        et_new_content = getView(R.id.et_new_content);
//        et_new_content.setFadingEdgeLength(10);
        setOnClickListener(R.id.friends_back);
        setOnClickListener(R.id.fabu);
        setOnClickListener(R.id.addImage);
        param_title = getView(R.id.param_title);
        insertDialog = new ProgressDialog(this);
        insertDialog.setMessage("正在插入图片...");
        insertDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void initData() {
        group_id = getIntent().getStringExtra("group_id");
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
            case R.id.fabu://发布
                commitData();
                break;
            case R.id.addImage://添加图片
                closeSoftKeyInput();
                callGallery();
                break;
        }
    }

    /**
     * 关闭软键盘
     */
    private void closeSoftKeyInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        //boolean isOpen=imm.isActive();//isOpen若返回true，则表示输入法打开
        if (imm != null && imm.isActive() && getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
            //imm.hideSoftInputFromInputMethod();//据说无效
            //imm.hideSoftInputFromWindow(et_content.getWindowToken(), 0); //强制隐藏键盘
            //如果输入法在窗口上已经显示，则隐藏，反之则显示
            //imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
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
                .capture(true) //是否提供拍照功能，兼容7.0系统需要下面的配置
                //参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
                .captureStrategy(new CaptureStrategy(true, "com.sendtion.matisse.fileprovider"))//存储到哪里
                .forResult(REQUEST_CODE_CHOOSE);//请求码
    }

    List<File> filses = new ArrayList<>();
    List<Uri> mSelected;
    List<String> dataPath;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data != null) {
                if (requestCode == 1) {
                    //处理调用系统图库
                } else if (requestCode == REQUEST_CODE_CHOOSE) {
                    filses.clear();
                    dataPath = Matisse.obtainPathResult(data);
//                    mSelected = Matisse.obtainResult(data);
////                    // 可以同时插入多张图片
                    for (String imageUri : dataPath) {
//                        String imagePath = SDCardUtil.getFilePathFromUri(Act_TopicPublishing.this, imageUri);
                        Avatar(new File(imageUri));

                    }

                }
            }
        }
    }

    /**
     * 异步方式插入图片
     *
     * @param data
     */
    private Disposable subsInsert;

    private void insertImagesSync(final List<String> data) {
        insertDialog.show();
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) {
                try {
                    et_new_content.measure(0, 0);
                    // 可以同时插入多张图片
                    for (int i = 0; i < data.size(); i++) {
                        emitter.onNext(ComantUtils.MyUrlHot1 + data.get(i));
                    }
                    // 测试插入网络图片 http://p695w3yko.bkt.clouddn.com/18-5-5/44849367.jpg
                    //subscriber.onNext("http://p695w3yko.bkt.clouddn.com/18-5-5/30271511.jpg");
                    emitter.onComplete();
                } catch (Exception e) {
                    e.printStackTrace();
                    emitter.onError(e);
                }
            }
        })
                .subscribeOn(Schedulers.io())//生产事件在io
                .observeOn(AndroidSchedulers.mainThread())//消费事件在UI线程
                .subscribe(new Observer<String>() {
                    @Override
                    public void onComplete() {
                        if (insertDialog != null && insertDialog.isShowing()) {
                            insertDialog.dismiss();
                        }
                        MyToast.show(context, "图片插入成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (insertDialog != null && insertDialog.isShowing()) {
                            insertDialog.dismiss();
                        }
                        MyToast.show(context, "图片插入失败" + e.getMessage());
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        subsInsert = d;
                    }

                    @Override
                    public void onNext(String imagePath) {
                        et_new_content.insertImage(imagePath, et_new_content.getMeasuredWidth());
                    }
                });
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

    public void postFile(List<File> selectList) {

        for (int i = 0; i < selectList.size(); i++) {
            Debug.e("----" + selectList.get(i).getPath());
            params.put("file[" + i + "]", selectList.get(i), listener);
        }
        EasyHttp.post("http://bisonchat.com/index/uploads/photosAll.html")
                .params("val", "groupHuati")
                .params(params)
                .execute(new ProgressDialogCallBack<String>(mProgressDialog, true, true) {
                    @Override
                    public void onSuccess(String succeed) {
                        Gson gons = new Gson();
                        imageBean = gons.fromJson(succeed, UploadImageBean.class);
                        if (imageBean.getMsg().contains("上传成功！")) {
                            insertImagesSync(imageBean.getUrl());
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    IProgressDialog mProgressDialog = new IProgressDialog() {
        @Override
        public Dialog getDialog() {
            ProgressDialog dialog = new ProgressDialog(Act_TopicPublishing.this);
            dialog.setMessage("上传中...");
            return dialog;
        }
    };

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
                .ignoreBy(90)
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
                        filses.add(file);
                        if (dataPath.size() == filses.size()) {
                            postFile(filses);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                }).launch();
    }

    /**
     * 负责处理编辑数据提交等事宜，请自行实现
     */
    private String getEditData() {
        List<RichTextEditor.EditData> editList = et_new_content.buildEditData();
        StringBuilder content = new StringBuilder();
        for (RichTextEditor.EditData itemData : editList) {
            if (itemData.inputStr != null) {
                content.append("<h3>").append(itemData.inputStr).append("</h3>");

            } else if (itemData.imagePath != null) {
                content.append("<img src=\"").append(itemData.imagePath).append("\"/>");
            }
        }
        return content.toString();
    }

    /**
     * 发布热门话题数据
     */
    private String param_imgUrl = "", group_id;

    public void commitData() {
        if (TextUtils.isEmpty(param_title.getText().toString())) {
            MyToast.show(context, "请输入您要发布的标题！");
            return;
        }
        if (TextUtils.isEmpty(getEditData())) {
            MyToast.show(context, "请输入您要发布的内容！");
            return;
        }
        try {
            if (imageBean.getUrl()==null||imageBean.getUrl().size()==0)
            {
                MyToast.show(context, "请上传至少一张图片！");
                return;
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
            MyToast.show(context, "请上传至少一张图片！");
            return;
        }

        EasyHttp.post("http://st.3dgogo.com/index/chatgroup_gambit/set_gambit")
                .params("uid", MyApplication.getCurrentUserInfo().getUserId())
                .params("group_id", group_id)
                .params("title", UtilsTool.addByte64(param_title.getText().toString()))

                .params("content", UtilsTool.addByte64(getEditData()))

                .params("img", imageBean.getUrl().get(0))
                .execute(new ProgressDialogCallBack<String>(mProgressDialog, true, true) {
                    @Override
                    public void onSuccess(String succeed) {
                        Gson gons = new Gson();
                        TopicPublishingBean imageBean = gons.fromJson(succeed, TopicPublishingBean.class);
                        if (imageBean.getMsg().contains("话题创建成功！")) {
                            MyToast.show(context, imageBean.getMsg());
                            EventBus.getDefault().post(new UserEvent("10", "10"));
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
}
