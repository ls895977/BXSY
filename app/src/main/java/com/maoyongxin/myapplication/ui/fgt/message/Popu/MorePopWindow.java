package com.maoyongxin.myapplication.ui.fgt.message.Popu;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.lykj.aextreme.afinal.utils.MyToast;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.common.MyApplication;
import com.maoyongxin.myapplication.ui.ExpressCardActivity;
import com.maoyongxin.myapplication.ui.fgt.community.act.Act_News_Web;
import com.maoyongxin.myapplication.ui.fgt.message.act.Act_CreateGroupSteopOne;
import com.maoyongxin.myapplication.ui.fgt.message.act.Act_FindAndSearch;
import com.maoyongxin.myapplication.ui.fgt.message.act.Act_GroupList;
import com.maoyongxin.myapplication.ui.fgt.min.act.Act_AddressHomeCheck;


public class MorePopWindow extends PopupWindow {

    @SuppressLint("InflateParams")
    public MorePopWindow(final Activity context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View content = inflater.inflate(R.layout.popu_add, null);
        // 设置SelectPicPopupWindow的View
        this.setContentView(content);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);

        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimationPreview);


        RelativeLayout re_addfriends = (RelativeLayout) content.findViewById(R.id.re_addfriends);
        RelativeLayout re_scanner = (RelativeLayout) content.findViewById(R.id.re_scanner);
        RelativeLayout re_chatroom = (RelativeLayout) content.findViewById(R.id.re_chatroom);
        re_addfriends.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加好友
                Intent intent = new Intent(new Intent(context, Act_FindAndSearch.class));
                context.startActivity(intent);
                MorePopWindow.this.dismiss();
            }

        });
        re_scanner.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, Act_AddressHomeCheck.class));

            }
        });

        re_chatroom.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {


         //   context.startActivity(new Intent(context, Act_CreateGroupSteopOne.class));
                  //    context.startActivity(new Intent(context, ExpressCardActivity.class));

                    Intent intent=new Intent(context,Act_News_Web.class);

                    intent.putExtra("communityId","2");
                    intent.putExtra("url", "http://www.bisonchat.com/home/introduce/index.html");


                     context.startActivity(intent);
//
            }

        });


    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAsDropDown(parent, 0, 0);
        } else {
            this.dismiss();
        }
    }
}
