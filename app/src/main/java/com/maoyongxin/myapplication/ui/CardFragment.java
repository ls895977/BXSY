package com.maoyongxin.myapplication.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lykj.aextreme.afinal.adapter.ViewOnClick;
import com.lykj.aextreme.afinal.utils.Debug;
import com.lykj.aextreme.afinal.utils.MyToast;
import com.makeramen.roundedimageview.RoundedImageView;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.tool.FragmentCallBack;
import com.maoyongxin.myapplication.ui.fgt.community.act.Act_JournalismDetails;
import com.maoyongxin.myapplication.ui.fgt.community.bean.JournalismBean;
import com.maoyongxin.myapplication.ui.fgt.community.bean.UserFriendsFollowApiBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;


/**
 * Created by liushaochen on 2019/3/4.
 */
public class CardFragment extends Fragment  implements View.OnClickListener  {
    private View mView;
    private JournalismBean.InfoBean.DataBean bean;

    private TextView tv_consignee;
    private TextView tv_express,notice_title1,notice_sender,notice_sender1;
    private RoundedImageView notice_image;
    private WebView webView;
    private ScrollView scroll_view;
    private LinearLayout ll_topview,rl_consignee_info;
    private View view ;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fm_card, null);
        rl_consignee_info=mView.findViewById((R.id.rl_consignee_info));
        ll_topview=mView.findViewById(R.id.ll_topview);
        tv_consignee = mView.findViewById(R.id.tv_pubtime);
        tv_express = mView.findViewById(R.id.tv_express);
        notice_title1=mView.findViewById(R.id.notice_title1);
        notice_sender=mView.findViewById(R.id.notice_sender);
        notice_image=mView.findViewById(R.id.notice_image);
        view=mView.findViewById(R.id.middleLine);
        notice_sender1=mView.findViewById(R.id.notice_sender1);

        notice_sender1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDetail();
            }
        });
        webView=mView.findViewById(R.id.idk);
        notice_title1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDetail();

            }
        });


    view.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            toDetail();
        }
    });



        scroll_view=mView.findViewById(R.id.scroll_view);
        // 初始化

        WebSettings webSettings = webView.getSettings();

        Glide.with(getContext()).load("http://118.24.2.164:8083/news/" + bean.getImg() + "").into((notice_image));
        tv_consignee.setText("时间：" + bean.getCreate_time());
        tv_express.setText("彼信商业头条" );
        notice_title1.setText(bean.getTitle());
        notice_sender.setText(bean.getRead_num());

        webSettings.setJavaScriptEnabled(true);



        webView.loadDataWithBaseURL(null,
                "<html><head><style>img {width:100%;height:auto;margin:auto;}</style></head><body>" + bean.getContent() + "</body></html>",
                "text/html",
                "utf-8",
                null
        );


        return mView;

    }

    @Override
    public void onClick(View v) {

    }


    /**
     * 初始化
     */


    /**
     * 设置数据Bean
     *
     * @param dataBean
     */
    public void setExpress(JournalismBean.InfoBean.DataBean dataBean) {
        this.bean = dataBean;


    }






    public String timeStamp2Date(long time, String format) {
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(time));
    }

    private void toDetail(){



        Intent intent = new Intent(getActivity(),Act_JournalismDetails.class);

        intent.putExtra("id1", bean.getId() + "");
        intent.putExtra("uid", bean.getUid() + "");
        intent.putExtra("newstitle", bean.getTitle() + "");
        intent.putExtra("content", bean.getContent() + "");
        intent.putExtra("source", bean.getSource() + "");
        intent.putExtra("news_Img", "http://118.24.2.164:8083/news/" +bean.getImg());
        intent.putExtra("dateTime",bean.getCreate_time());
        intent.putExtra("readNum",bean.getRead_num());

        Pair<View, String> readnum = new Pair<View, String>(notice_sender,"readnum");
        Pair<View, String> midline = new Pair<View, String>(view,"llll");
        Pair<View, String> transweb = new Pair<View, String>(webView,"tran_web");
        Pair<View, String> rlconsigneeinfo = new Pair<View, String>(rl_consignee_info,"lltop");
        Pair<View, String> tv_souce = new Pair<View, String>(tv_express,"new_source");
        Pair<View, String> tv_time = new Pair<View, String>(tv_consignee,"news_time");
        Pair<View, String> newsImg = new Pair<View, String>(notice_image,"newsImg");
        Pair<View,String> newstitle = new Pair<View, String>(notice_title1,"newstitle");

        ActivityOptionsCompat optionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),readnum,midline,transweb,rlconsigneeinfo,tv_souce,tv_time, newsImg,newstitle);
        startActivity(intent,optionsCompat.toBundle());


    }
    private void readNumfresh(String newsId) {//点赞

        OkHttpUtils.post().url("http://bisonchat.com/index/news/setReadNumApi.html" )
                .addParams("id", newsId)

                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Debug.e("----onError-" + call.toString());

            }

            @Override
            public void onResponse(String response, int id) {


            }
        });
    }

}
