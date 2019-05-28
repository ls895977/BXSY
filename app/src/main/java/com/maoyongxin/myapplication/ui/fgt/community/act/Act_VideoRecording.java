package com.maoyongxin.myapplication.ui.fgt.community.act;
import android.os.Bundle;
import android.util.Log;

import com.video.recorder.CameraActivity;

/**
 * 视频录制类
 */
public class Act_VideoRecording extends CameraActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("luXiangActivity","onCreate");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.e("luXiangActivity","onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("luXiangActivity","onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("luXiangActivity","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("luXiangActivity","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("luXiangActivity","onDestroy");
    }
}
