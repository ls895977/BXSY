package com.maoyongxin.myapplication.ui.fgt.community.fgt;


import android.content.Intent;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import android.view.View;

import com.google.gson.Gson;
import com.lykj.aextreme.afinal.utils.MyToast;
import com.maoyongxin.myapplication.R;

import com.maoyongxin.myapplication.common.BaseFgt;
import com.maoyongxin.myapplication.common.ComantUtils;

import com.maoyongxin.myapplication.tool.FragmentCallBack;

import com.maoyongxin.myapplication.ui.CardFragment;
import com.maoyongxin.myapplication.ui.CardTransformer;
import com.maoyongxin.myapplication.ui.CardViewPagerAdapter;
import com.maoyongxin.myapplication.ui.UtilShowAnim;
import com.maoyongxin.myapplication.ui.fgt.community.act.Act_JournalismDetails;
import com.maoyongxin.myapplication.ui.fgt.community.bean.JournalismBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fgt_Express_news extends BaseFgt implements FragmentCallBack , View.OnClickListener{


    private UtilShowAnim mUtilAnim;
    /**
     * ViewPager对象
     */
    private ViewPager vp_card;
    /**
     * ViewPager适配器
     */
    private CardViewPagerAdapter mAdapter;
    /**
     * 数据源
     */
    private List<CardFragment> mList;
    /**
     * 切换动画
     */
    private CardTransformer mTransformer;
    private int page = 1;

    JournalismBean bean;
    @Override
    public int initLayoutId() {
        return R.layout.fragment_fgt__express_news;
    }
    @Override
    public void initView() {


        vp_card=getViewAndClick(R.id.vp_card);

    }

    @Override
    public void initData() {

        mUtilAnim = new UtilShowAnim(vp_card);

        getnewsdata();

    }



    @Override
    public void updateUI() {

    }



    @Override
    public void onViewClick(View v) {

        switch (v.getId()) {

            // 出场效果 =》逐一效果
            case R.id.vp_card:

                MyToast.show(context,"点击了");

                break;
        }
    }


    @Override
    public void requestData() {

    }

    @Override
    public void sendMsg(int flag, Object obj) {

    }



    private void getnewsdata()
    {
        mList = new ArrayList<CardFragment>();
        OkHttpUtils.post().url(ComantUtils.MyUrlHot + ComantUtils.Community_New)
                .addParams("page", page + "")
                .addParams("per_page", 12 + "")
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(String response, int id) {
                Gson gson = new Gson();
                 bean = gson.fromJson(response, JournalismBean.class);

                for (int i = 0; i < bean.getInfo().getData().size(); i++) {


                    CardFragment fragment = new CardFragment();
                    fragment.setExpress(bean.getInfo().getData().get(i));

                    mList.add(fragment);
                }

                mAdapter = new CardViewPagerAdapter(getChildFragmentManager(), mList,Fgt_Express_news.this,1,context);


                vp_card.setAdapter(mAdapter);




                // 实例化ViewPager切换动画类
                mTransformer = new CardTransformer();

                // 设置动画类
                vp_card.setPageTransformer(true, mTransformer);

                // 设置切换动画为 层叠效果，并获取预加载数量
                int offscreen = mTransformer.setTransformerType(CardTransformer.ANIM_TYPE_STACK);
                // 设置ViewPager的预加载数量
                vp_card.setOffscreenPageLimit(offscreen);

            }
        });
    }


    @Override
    public void callBack(int position) {

        Intent intent = new Intent(getActivity(), Act_JournalismDetails.class);
        intent.putExtra("id1", bean.getInfo().getData().get(position).getId());
        intent.putExtra("uid",  bean.getInfo().getData().get(position).getUid());
        intent.putExtra("newstitle", bean.getInfo().getData().get(position).getTitle() + "");
        intent.putExtra("content", bean.getInfo().getData().get(position).getContent() + "");
        intent.putExtra("source", bean.getInfo().getData().get(position).getSource() + "");
        intent.putExtra("news_Img", "http://118.24.2.164:8083/news/" + bean.getInfo().getData().get(position).getImg());
        intent.putExtra("dateTime",bean.getInfo().getData().get(position).getCreate_time());

        getActivity().startActivity(intent);


    }

}
