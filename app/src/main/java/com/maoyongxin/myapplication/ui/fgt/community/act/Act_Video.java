package com.maoyongxin.myapplication.ui.fgt.community.act;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.lykj.aextreme.afinal.utils.Debug;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.common.BaseAct;
import com.maoyongxin.myapplication.common.ComantUtils;

import java.io.File;

import cn.jzvd.JZVideoPlayerStandard;


/**
 * 视频图片显示
 */
public class Act_Video extends BaseAct {
    private String url;
    private String thumbUrl = "";
    private String title = "";
    private int type;//1--图片,2--视频
    private JZVideoPlayerStandard videoPlayer;
    private VideoView play_video;
    private MediaController mediaController;

    @Override
    protected void handlerPassMsg(int target, int target1, Object obj) {

    }

    @Override
    public int initLayoutId() {
        return R.layout.act_video;
    }

    String filePath;

    @Override
    public void initView() {
        setOnClickListener(R.id.video_back);
        hideHeader();
        videoPlayer = getView(R.id.videoPlayer);
        play_video = getView(R.id.play_video);
        if (getIntent().getStringExtra("file") != null) {
            mediaController = new MediaController(this);
            videoPlayer.setVisibility(View.GONE);
            play_video.setVisibility(View.VISIBLE);
            filePath = getIntent().getStringExtra("file");
            File file = new File(filePath);
            if (file.exists()) {
                play_video.setVideoPath(file.getAbsolutePath());
                play_video.setMediaController(mediaController);
                mediaController.setMediaPlayer(play_video);
                play_video.requestFocus();
                play_video.start();
            }
        } else {
            videoPlayer.setVisibility(View.VISIBLE);
            play_video.setVisibility(View.GONE);
            getIntentData();
            initVideoEnv();
        }
    }

    private void getIntentData() {
        url = getIntent().getStringExtra("resource");
        type = getIntent().getIntExtra("type", 0);
        thumbUrl = getIntent().getStringExtra("thumb");
        title = getIntent().getStringExtra("title");
    }

    private void initVideoEnv() {
        if (thumbUrl!=null&&thumbUrl.contains("uploads")) {
            videoPlayer.setUp(ComantUtils.MyUrlHot1 + url, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, title);
            Glide.with(context).load(ComantUtils.MyUrlHot1 + thumbUrl).into(videoPlayer.thumbImageView);
        } else {
            videoPlayer.setUp(ComantUtils.MyVideoUir + url, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, title);
            Glide.with(context).load(ComantUtils.BigImgUrl + thumbUrl).into(videoPlayer.thumbImageView);
        }
        videoPlayer.startVideo();
    }

    @Override
    public void initData() {

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
            case R.id.video_back:
                finish();
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoPlayer.goOnPlayOnPause();
    }
}
