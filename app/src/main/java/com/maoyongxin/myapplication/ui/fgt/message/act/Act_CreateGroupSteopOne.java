package com.maoyongxin.myapplication.ui.fgt.message.act;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.common.BaseAct;
import com.maoyongxin.myapplication.http.entity.grideInfo;
import com.maoyongxin.myapplication.ui.fgt.message.adapter.groupType_gride_adapter;
import com.maoyongxin.myapplication.view.TakePictureManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 创建商会
 */
public class Act_CreateGroupSteopOne extends BaseAct {
    ImageView groupImg;
    EditText editGroupName;
    Button newxtStep;
    GridView groupTypegride;
    private TakePictureManager takePictureManager;
    private String tupianUrl = "";
    private String uploadurl = "";
    private String groupName, groupId, groupimg;
    private int UPDATE = 1;
    private Handler handler;
    private List<grideInfo> hangyelists = new ArrayList<>();
    private String grouptypeId = "";
    private groupType_gride_adapter fwgrideAdapter;
    private String grouptyname;

    @Override
    protected void handlerPassMsg(int target, int target1, Object obj) {

    }

    @Override
    public int initLayoutId() {
        return R.layout.act_creategroupsteopone;
    }

    @Override
    public void initView() {
        hideHeader();
        groupImg = getView(R.id.groupimg);
        editGroupName = getView(R.id.editgroupName);
        newxtStep = getView(R.id.newxtStep);
        setOnClickListener(R.id.friends_back);
        groupTypegride = getView(R.id.groupTypegride);
    }

    @Override
    public void initData() {
        getgroupttypeList();
        groupImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActionSheetDialog();
            }
        });
        newxtStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(tupianUrl)) {
                    Toast.makeText(Act_CreateGroupSteopOne.this, "请选个图片先", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(grouptypeId)) {
                    Toast.makeText(Act_CreateGroupSteopOne.this, "请选择商会分类", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(editGroupName.getText().toString()) || TextUtils.isEmpty(editGroupName.getText())) {
                    Toast.makeText(Act_CreateGroupSteopOne.this, "名字莫搞忘了", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(Act_CreateGroupSteopOne.this, Act_CreateGroup.class);
                intent.putExtra("groupImg", tupianUrl);
                intent.putExtra("groupClassifyId", grouptypeId);
                intent.putExtra("groupClassiname", grouptyname);
                intent.putExtra("groupname", editGroupName.getText().toString());
                startActivity(intent);
            }
        });

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                if (msg.what == UPDATE) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fwgrideAdapter = new groupType_gride_adapter(Act_CreateGroupSteopOne.this, hangyelists);
                            groupTypegride.setAdapter(fwgrideAdapter);
                        }
                    }, 10);
                }
            }
        };

        groupTypegride.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final TextView tv_id = (TextView) view.findViewById(R.id.gridId);
                final TextView tv_name = (TextView) view.findViewById(R.id.fuwuname);
                fwgrideAdapter.setCurrentItem(i);
                //通知ListView改变状态
                fwgrideAdapter.notifyDataSetChanged();
                grouptypeId = tv_id.getText() + "";
                grouptyname = tv_name.getText().toString();
            }
        });
    }

    @Override
    public void requestData() {

    }

    @Override
    public void updateUI() {

    }

    @Override
    public void onViewClick(View v) {
            switch (v.getId()){
                case R.id.friends_back:
                    finish();
                    break;
            }
    }


    private String parse_Value(JSONObject data, String value) {
        String com_value = "";
        if (data.has(value)) {
            try {
                com_value = data.getString(value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return com_value;
    }

    private void ActionSheetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)

                .setTitle("选择图片：")
                //设置两个item
                .setItems(new String[]{"相机", "图库"}, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                //  take_photo();
                                new_take();
                                break;
                            case 1:
                                album();
                                // pickPicFromPic();
                                break;
                            default:
                                break;
                        }

                    }
                });
        builder.create().show();
    }

    private void new_take() {
        takePictureManager = new TakePictureManager(this);
        //开启裁剪 比例 1:3 宽高 350 350  (默认不裁剪)
        takePictureManager.setTailor(3, 2, 600, 400);
        //拍照方式
        takePictureManager.startTakeWayByCarema();
        //回调
        takePictureManager.setTakePictureCallBackListener(new TakePictureManager.takePictureCallBackListener() {
            //成功拿到图片,isTailor 是否裁剪？ ,outFile 拿到的文件 ,filePath拿到的URl
            @Override
            public void successful(boolean isTailor, File outFile, Uri filePath) {

                Glide.with(Act_CreateGroupSteopOne.this).load(outFile).into(groupImg);
                tupianUrl = outFile.toString();


            }

            //失败回调
            @Override
            public void failed(int errorCode, List<String> deniedPermissions) {
                //Log.e("==w", deniedPermissions.toString());
            }
        });
    }

    private void album() {
        takePictureManager = new TakePictureManager(this);
        takePictureManager.setTailor(3, 2, 600, 400);
        takePictureManager.startTakeWayByAlbum();
        takePictureManager.setTakePictureCallBackListener(new TakePictureManager.takePictureCallBackListener() {
            @Override
            public void successful(boolean isTailor, File outFile, Uri filePath) {
                Glide.with(Act_CreateGroupSteopOne.this).load(outFile).into(groupImg);
                tupianUrl = outFile.toString();
            }

            @Override
            public void failed(int errorCode, List<String> deniedPermissions) {

            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        takePictureManager.attachToActivityForResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        takePictureManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void getgroupttypeList() {
        final String grouptypeList = "http://st.3dgogo.com/index/chatgroup_gambit/get_chatgroup_classify";
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(grouptypeList).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();

                    try {

                        JSONObject jsonObject = new JSONObject(responseData);

                        JSONArray data = jsonObject.getJSONArray("info");


                        for (int i = 0; i < data.length(); i++) {


                            JSONObject newList = data.getJSONObject(i);
                            grideInfo datas = new grideInfo();
                            groupName = parse_Value(newList, "group_classify_name");
                            groupId = parse_Value(newList, "group_classify_id");
                            groupimg = parse_Value(newList, "image");
                            datas.setfuwuInfo(groupName, groupimg, groupId);
                            hangyelists.add(datas);


                        }

                        Message msg = new Message();
                        msg.what = UPDATE;
                        handler.sendMessage(msg);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    //  showMessagefail("解析失败");
                }
            }
        }).start();
    }
}
