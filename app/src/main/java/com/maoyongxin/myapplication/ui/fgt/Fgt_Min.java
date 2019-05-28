package com.maoyongxin.myapplication.ui.fgt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jky.baselibrary.util.common.TimeUtil;
import com.lykj.aextreme.afinal.utils.Debug;
import com.lykj.aextreme.afinal.utils.MyToast;
import com.lykj.aextreme.afinal.utils.MyUtil;
import com.makeramen.roundedimageview.RoundedImageView;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.common.BaseFgt;
import com.maoyongxin.myapplication.common.ComantUtils;
import com.maoyongxin.myapplication.common.MyApplication;
import com.maoyongxin.myapplication.http.callback.EntityCallBack;
import com.maoyongxin.myapplication.http.request.ReqCommunity;
import com.maoyongxin.myapplication.http.request.ReqGetFollowList;
import com.maoyongxin.myapplication.http.request.ReqGetMyFriendsList;
import com.maoyongxin.myapplication.http.response.FollowListResponse;
import com.maoyongxin.myapplication.http.response.FriendsResponse;
import com.maoyongxin.myapplication.http.response.MyCommunityResponse;
import com.maoyongxin.myapplication.ui.fgt.message.act.Act_MyFriends;
import com.maoyongxin.myapplication.ui.fgt.min.act.Act_AccountSettings;
import com.maoyongxin.myapplication.ui.fgt.min.act.Act_AddressHomeCheck;
import com.maoyongxin.myapplication.ui.fgt.min.act.Act_Feedback;
import com.maoyongxin.myapplication.ui.fgt.min.act.Act_Follow;
import com.maoyongxin.myapplication.ui.fgt.min.act.Act_MyCollection;
import com.maoyongxin.myapplication.ui.fgt.min.act.Act_Recharge;
import com.maoyongxin.myapplication.ui.fgt.min.act.Act_TeamInformation;
import com.maoyongxin.myapplication.ui.fgt.min.act.Act_UserEditNew;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

import static android.content.Context.MODE_PRIVATE;

/**
 * 我的
 */
public class Fgt_Min extends BaseFgt {
    private TextView tv_userName, tv_myNote, tv_idNum, tv_vipNum, tv_newedtion, tv_myFollow, friendnum;
    private SharedPreferences sp;
    private ProgressDialog mProgressDialog;
    private RoundedImageView imageView;

    @Override
    public int initLayoutId() {
        return R.layout.fgt_min;
    }

    @Override
    public void initView() {
        hideHeader();
        setOnClickListener(R.id.mine_header);
        setOnClickListener(R.id.tv_doPay);
        setOnClickListener(R.id.ll_myfriend);
        setOnClickListener(R.id.tv_my_communty);
        setOnClickListener(R.id.ll_myfollow);
        setOnClickListener(R.id.ziliao);
        setOnClickListener(R.id.sgs);
        setOnClickListener(R.id.tv_numsetting);
        setOnClickListener(R.id.min_mycollection);
        tv_userName = getViewAndClick(R.id.tv_userName);
        tv_idNum = getViewAndClick(R.id.tv_idNum);
        imageView = getViewAndClick(R.id.mine_header);
        friendnum = getView(R.id.frendnum);
        tv_vipNum = getView(R.id.tv_vipNum);
        tv_myFollow = getView(R.id.tv_myFollow);
    }

    private String nickName, myHeadPath, birthDay, note;
    private int sex;

    @Override
    public void initData() {
        updateUI();
    }

    @Override
    public void requestData() {

    }

    @Override
    public void updateUI() {
        sp = getActivity().getSharedPreferences("config", MODE_PRIVATE);
        sex = 0;
        Glide.with(getActivity()).load(MyApplication.getCurrentUserInfo().getHeadImg()).into(imageView);
        tv_userName.setText(MyApplication.getCurrentUserInfo().getUserName());
        tv_idNum.setText("ID:" + MyApplication.getCurrentUserInfo().getUserId());
        nickName = MyApplication.getCurrentUserInfo().getUserName();
        myHeadPath = MyApplication.getCurrentUserInfo().getHeadImg();
        birthDay = MyApplication.getCurrentUserInfo().getBrithday();
        mProgressDialog = new ProgressDialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog);
        mProgressDialog.setCancelable(true);
        RongIM.getInstance().refreshUserInfoCache(new UserInfo(MyApplication.getCurrentUserInfo().getUserId(),
                MyApplication.getCurrentUserInfo().getUserName(),
                Uri.parse(MyApplication.getCurrentUserInfo().getHeadImg())));
        tv_vipNum.setText("V" + MyApplication.getCurrentUserInfo().getVipNum());
        getFriendList();//获取当前有多少好友
        getMyFollowList();//获取当前有多少关注
    }

    @Override
    public void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.mine_header://头像=资料修改
            case R.id.tv_userName:
            case R.id.tv_idNum:
            case R.id.ziliao:
                startActivityForResult(Act_UserEditNew.class, MyApplication.GETMY_USERINFO);
                break;
            case R.id.tv_doPay://我要充值
                startAct(Act_Recharge.class);
                break;
            case R.id.ll_myfollow://关注
                startAct(Act_Follow.class);
                break;
            case R.id.ll_myfriend://好友
                startAct(Act_MyFriends.class);
            case R.id.ll_fenshi://粉丝
                break;
//            case R.id.tv_my_personal://个人设置
//                startActivityForResult( UserEd、itNewActivity.class,MyApplication.GETMY_USERINFO);
//                break;
            case R.id.tv_my_communty://服务号管理
                getMyOwnCommunity();
                break;
            case R.id.sgs://反馈建议
                startAct(Act_Feedback.class);
                break;
            case R.id.tv_numsetting://账号设置
                startAct(Act_AccountSettings.class);
                break;
            case R.id.min_mycollection://我的收藏
                startAct(Act_MyCollection.class);
                break;
        }
    }

    @Override
    public void sendMsg(int flag, Object obj) {

    }

    /**
     * 获得多少好友
     */
    private void getFriendList() {
        ReqGetMyFriendsList.getFriends(getActivity(), getTag(), MyApplication.getCurrentUserInfo().getUserId(), new EntityCallBack<FriendsResponse>() {
            @Override
            public Class<FriendsResponse> getEntityClass() {
                return FriendsResponse.class;
            }

            @Override
            public void onSuccess(FriendsResponse resp) {
                if (resp.is200()) {
                    MyApplication.getCurrentUserInfo().setFriendNum(resp.obj.getFriendList().size() + "");
                    friendnum.setText(resp.obj.getFriendList().size() + "");
                } else {
                    MyToast.show(getActivity(), resp.msg);
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

    /**
     * 活得多少个关注
     */
    private void getMyFollowList() {
        ReqGetFollowList.getList(getActivity(), getTag(), MyApplication.getCurrentUserInfo().getUserId(), new EntityCallBack<FollowListResponse>() {
            @Override
            public Class<FollowListResponse> getEntityClass() {
                return FollowListResponse.class;
            }

            @Override
            public void onSuccess(final FollowListResponse resp) {
                if (resp.is200()) {
                    tv_myFollow.setText(resp.obj.getFollowList().size() + "");
                } else {

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

    private void getMyOwnCommunity() {
        ReqCommunity.getMyCommunity(getActivity(), getClass().getSimpleName(), MyApplication.getCurrentUserInfo().getUserId(), new EntityCallBack<MyCommunityResponse>() {
            @Override
            public Class<MyCommunityResponse> getEntityClass() {
                return MyCommunityResponse.class;
            }

            @Override
            public void onSuccess(MyCommunityResponse resp) {
                if (resp.is200()) {
                    Intent intent = new Intent(getActivity(), Act_TeamInformation.class);
                    intent.putExtra("myCommunityIcon", resp.obj.getCommunityImg());
                    intent.putExtra("myCommunityName", resp.obj.getCommunityName());
                    intent.putExtra("myCommunityNote", resp.obj.getCommunityNote());
                    intent.putExtra("myCommunityAddress", resp.obj.getAddress());
                    intent.putExtra("myCommunityCreatTime", TimeUtil.getFormatYMDFromMillis(resp.obj.getCreatTime(), "yyyy-MM-dd"));
                    intent.putExtra("myCommunityId", resp.obj.getCommunityId() + "");
                    startActivity(intent);
                } else {
                    Intent intent=new Intent(getContext(),Act_AddressHomeCheck.class);
                    intent.putExtra("userid",MyApplication.getCurrentUserInfo().getUserId()+"");
                    intent.putExtra("adcode",MyApplication.getMyCurrentLocation().getAdCode()+"");
                    startActivity(new Intent(getActivity(), Act_AddressHomeCheck.class));
                }
            }

            @Override
            public void onFailure(Throwable e) {
                Intent intent=new Intent(getContext(),Act_AddressHomeCheck.class);
                intent.putExtra("userid",MyApplication.getCurrentUserInfo().getUserId()+"");
                intent.putExtra("adcode",MyApplication.getMyCurrentLocation().getAdCode()+"");
                startActivity(new Intent(getActivity(), Act_AddressHomeCheck.class));
                startActivity(new Intent(getActivity(), Act_AddressHomeCheck.class));
            }

            @Override
            public void onCancelled(Throwable e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MyApplication.GETMY_USERINFO && resultCode == 10) {
            updateUI();
        }

    }
}
