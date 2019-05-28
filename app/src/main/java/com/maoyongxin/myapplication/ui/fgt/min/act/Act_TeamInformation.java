package com.maoyongxin.myapplication.ui.fgt.min.act;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jky.baselibrary.widget.TitleBar;
import com.lykj.aextreme.afinal.utils.MyToast;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.common.BaseAct;
import com.maoyongxin.myapplication.common.MyApplication;
import com.maoyongxin.myapplication.http.callback.EntityCallBack;
import com.maoyongxin.myapplication.http.request.ReqCommunity;
import com.maoyongxin.myapplication.http.response.BaseResp;
import com.maoyongxin.myapplication.http.response.CommunityUsersResponse;
import com.maoyongxin.myapplication.ui.fgt.message.act.strangerdetail.fgt.adapter.CommunityPersonListAdapter;
import com.maoyongxin.myapplication.view.SelectableRoundedImageView;
import com.maoyongxin.myapplication.view.TeamPopWindow;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 我的=服务号=团队信息
 */
public class Act_TeamInformation extends BaseAct {
    TitleBar TitleBarCMD;
    SelectableRoundedImageView imgDetailCommunityIcon;
    TextView tvCommunityName;
    TextView tvCommunityCreatTime;
    TextView tvCommunityAddress;
    TextView tvCommunityNote;
    ListView lvCommunityPersonList;
    Button btnBecomeManager;
    Button btnDoOutCommunity;
    Button btnEditCommunityMsg;
    Button btnEditSHow;
    TextView imgControl;
    private ProgressDialog progressDialog;
    private String myCommunityIcon;
    private String myCommunityNote;
    private String myCommunityId;
    private String myCommunityName;
    private String myCommunityAddress;
    private String myCommunityCreatTime;
    private CommunityPersonListAdapter adapter;
    private boolean isSuperManager = false;
    private boolean isManager = false;
    private String personId;
    private String personHead;
    private String personusrename;
    private String personTel;
    private String personSex;
    private Boolean hasadvertise = false;
    private String shangjiaInfo;

    @Override
    protected void handlerPassMsg(int target, int target1, Object obj) {

    }

    @Override
    public int initLayoutId() {
        return R.layout.act_teaminformation;
    }

    @Override
    public void initView() {
        hideHeader();
        TitleBarCMD = getView(R.id.TitleBar_CMD);
        setOnClickListener(R.id.teaminformation_back);
        imgDetailCommunityIcon = getView(R.id.img_DetailCommunityIcon);
        tvCommunityName = getView(R.id.tv_CommunityName);
        tvCommunityCreatTime = getView(R.id.tv_communityCreatTime);
        tvCommunityAddress = getView(R.id.tv_communityAddress);
        tvCommunityNote = getView(R.id.tv_communityNote);
        lvCommunityPersonList = getView(R.id.lv_communityPersonList);
        btnBecomeManager = getView(R.id.btn_becomeManager);
        btnDoOutCommunity = getView(R.id.btn_doOutCommunity);
        btnEditCommunityMsg = getView(R.id.btn_EditCommunityMsg);
        btnEditSHow = getView(R.id.btn_EditSHow);
        imgControl = getView(R.id.img_control);
    }

    @Override
    public void initData() {
        myCommunityId = getIntent().getStringExtra("myCommunityId");
        myCommunityIcon = getIntent().getStringExtra("myCommunityIcon");
        myCommunityName = getIntent().getStringExtra("myCommunityName");
        myCommunityNote = getIntent().getStringExtra("myCommunityNote");
        myCommunityAddress = getIntent().getStringExtra("myCommunityAddress");
        myCommunityCreatTime = getIntent().getStringExtra("myCommunityCreatTime");
        progressDialog = new ProgressDialog(this, android.R.style.Theme_DeviceDefault_Dialog);
        progressDialog.setCancelable(false);
        if (myCommunityIcon.equals("")) {
            Glide.with(context).load(R.mipmap.jingying).into(imgDetailCommunityIcon);
        } else {
            Glide.with(context).load(myCommunityIcon).into(imgDetailCommunityIcon);
        }
        tvCommunityAddress.setText(myCommunityAddress);
        tvCommunityCreatTime.setText(myCommunityId);
        tvCommunityName.setText(myCommunityName);
        tvCommunityNote.setText(myCommunityNote);
        Hasadvertise();
        btnEditSHow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!hasadvertise) {
                    // Intent intent = new Intent(getActivity(), CompanyshowDefine.class);
//                    Intent intent = new Intent(getActivity(), fwDetail.class);
//                    intent.putExtra("companyName", myCommunityName);
//                    startActivity(intent);
                } else if (hasadvertise) {
//                    Intent intent = new Intent(getActivity(), xiangqingye.class);
//                    intent.putExtra("shangjiaInfo", shangjiaInfo);
//                    startActivity(intent);
                }

            }
        });
        TitleBarCMD.setOnTitleBarClickListener(new TitleBar.OnClickListener() {
            @Override
            public boolean onClick(int function) {
                if (function == TitleBar.FUNCTION_RIGHT_TEXT) {
//                    Intent intent = new Intent(CommunityDetailActivity.this, CommunityMessageActivity.class);
//                    intent.putExtra("communityId", myCommunityId);
//                    if (isSuperManager) {
//                        intent.putExtra("type", "superManager");
//                        startActivity(intent);
//                    } else {
//                        if (isManager) {
//                            intent.putExtra("type", "manager");
//                            startActivity(intent);
//                        } else {
//                            showToastShort("对不起，只有管理员才有权限查看请求信息");
//                        }
//                    }

                }
                return false;
            }
        });
        btnEditCommunityMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(CommunityDetailActivity.this, CommunityEditActivity.class);
//                startActivity(intent);
            }
        });
        btnDoOutCommunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder buider = new AlertDialog.Builder(context);
                buider.setTitle("提示").setMessage("你确定退出当前社区么？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ReqCommunity.deleteCommunity(context, getActivityTag(), MyApplication.getCurrentUserInfo().getUserId(), myCommunityId, new EntityCallBack<BaseResp>() {
                            @Override
                            public Class<BaseResp> getEntityClass() {
                                return BaseResp.class;
                            }

                            @Override
                            public void onSuccess(BaseResp resp) {
                                MyToast.show(context, resp.msg);
                                if (resp.is200()) {
                                    finish();
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
                }).setNegativeButton("取消", null).show();
            }
        });
        btnBecomeManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText inputServer = new EditText(context);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("提示").setMessage("成为管理员需要审核，请输入请求内容").setView(inputServer)
                        .setNegativeButton("取消", null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ReqCommunity.becomeManager(context, getActivityTag(), MyApplication.getCurrentUserInfo().getUserId(), myCommunityId, inputServer.getText().toString(), new EntityCallBack<BaseResp>() {
                            @Override
                            public Class<BaseResp> getEntityClass() {
                                return BaseResp.class;
                            }

                            @Override
                            public void onSuccess(BaseResp resp) {
                                MyToast.show(context, resp.msg);
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
                });
                builder.show();
            }
        });


        imgControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TeamPopWindow teamPopWindow = new TeamPopWindow(getActivity(), myCommunityId, isSuperManager, isManager, hasadvertise, myCommunityName, shangjiaInfo);
                teamPopWindow.showPopupWindow(imgControl);
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
        switch (v.getId()) {
            case R.id.teaminformation_back:
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMyCommunityPerson();
    }

    private void Hasadvertise() {
        final String XkOne = "http://st.3dgogo.com/index/enterprise_info/get_enterprise_publicity_details_api.html";
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                try {

                    RequestBody requestBody = new FormBody.Builder()
                            .add("community_id", myCommunityId)
                            .build();

                    Request request = new Request.Builder()
                            .url(XkOne)
                            .post(requestBody)
                            .build();
                    try {
                        Call call = okHttpClient.newCall(request);
                        //判断请求是否成功
                        Looper.prepare();
                        try {
                            Response response = call.execute();

                            try {
                                shangjiaInfo = response.body().string();
                                JSONObject jsonObject = new JSONObject(shangjiaInfo);

                                if (jsonObject.getInt("code") == 200) {
                                    Toast.makeText(context, "去bisonchat.com编辑您的服务号，让客户更好的找到你", Toast.LENGTH_SHORT).show();
                                    hasadvertise = true;
                                } else if (jsonObject.getInt("code") == 500) {
                                    hasadvertise = false;
                                    //Toast.makeText(CommunityDetailActivity.this, "您的微官服务号，还没有设置界面请前往官网设置", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(context, "去bisonchat.com编辑您的服务号，让客户更好的找到你", Toast.LENGTH_SHORT).show();

                                    //      openMicroweb();


                                }
                            } catch (Exception e) {
                                e.printStackTrace();


                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Looper.loop();

                    } catch (Exception e) {
                        e.printStackTrace();

                    }


                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }).start();
    }

    private void getMyCommunityPerson() {
        progressDialog.show();
        ReqCommunity.getMyCommunityPersons(this, getActivityTag(), MyApplication.getCurrentUserInfo().getUserId(), myCommunityId, new EntityCallBack<CommunityUsersResponse>() {
            @Override
            public Class<CommunityUsersResponse> getEntityClass() {
                return CommunityUsersResponse.class;
            }

            @Override
            public void onSuccess(final CommunityUsersResponse resp) {
                progressDialog.dismiss();
                if (resp.is200()) {
                    if (resp.obj.getSuperManagerUserId().equals(MyApplication.getCurrentUserInfo().getUserId())) {//超管
                        isSuperManager = true;
                        btnEditCommunityMsg.setVisibility(View.VISIBLE);
                        btnDoOutCommunity.setVisibility(View.GONE);
                        btnBecomeManager.setVisibility(View.GONE);
                    } else {
                        if (resp.obj.getManagerUserId().size() != 0) {
                            for (int i = 0; i < resp.obj.getManagerUserId().size(); i++) {
                                if (resp.obj.getManagerUserId().get(i).equals(MyApplication.getCurrentUserInfo().getUserId())) {
                                    btnEditCommunityMsg.setVisibility(View.GONE);
                                    btnDoOutCommunity.setVisibility(View.VISIBLE);
                                    btnBecomeManager.setVisibility(View.GONE);
                                    isManager = true;
                                    break;
                                } else {
                                    btnEditCommunityMsg.setVisibility(View.GONE);
                                    btnDoOutCommunity.setVisibility(View.VISIBLE);
                                    btnBecomeManager.setVisibility(View.VISIBLE);
                                }
                            }
                        } else {
                            isSuperManager = false;
                            isManager = false;
                            btnDoOutCommunity.setVisibility(View.VISIBLE);
                            btnBecomeManager.setVisibility(View.VISIBLE);
                            btnEditCommunityMsg.setVisibility(View.GONE);
                        }
                    }
                    adapter = new CommunityPersonListAdapter(myCommunityId, resp.obj.getSuperManagerUserId(), resp.obj.getManagerUserId(), resp.obj.getUserList(), context, false);
                    lvCommunityPersonList.setAdapter(adapter);
                    adapter.setOnRefreshListener(new CommunityPersonListAdapter.OnRefreshListener() {
                        @Override
                        public void refresh() {
                            getMyCommunityPerson();
                        }
                    });


                    lvCommunityPersonList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(context, Act_ComunityMateDetail.class);
                            personId = resp.obj.getUserList().get(position).getUserId();
                            personHead = resp.obj.getUserList().get(position).getHeadImg();
                            personusrename = resp.obj.getUserList().get(position).getUserName();
                            personTel = resp.obj.getUserList().get(position).getUserPhone();
                            personSex = resp.obj.getUserList().get(position).getSex() + "";
                            intent.putExtra("personId", personId);
                            intent.putExtra("personHead", personHead);
                            intent.putExtra("personusrename", personusrename);
                            intent.putExtra("personTel", personTel);
                            intent.putExtra("personSex", personSex);
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Throwable e) {
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(Throwable e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
