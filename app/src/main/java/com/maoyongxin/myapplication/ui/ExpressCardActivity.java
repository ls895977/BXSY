package com.maoyongxin.myapplication.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.maoyongxin.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ExpressCardActivity extends BamFragmentActivity implements View.OnClickListener {



    /**
     * 工具类 用于控制出场动画
     */
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_express_card);
    }


    @Override
    protected void findView() {
        // 实例化View
        vp_card = findViewById(R.id.vp_card);
    }

    @Override
    protected void setListener() {
        findViewById(R.id.btn_show_card_inproperorder).setOnClickListener(this);
        findViewById(R.id.btn_show_card_unfold).setOnClickListener(this);
        findViewById(R.id.btn_show_card_rotation).setOnClickListener(this);
        findViewById(R.id.btn_change_stack).setOnClickListener(this);
        findViewById(R.id.btn_change_scale).setOnClickListener(this);
        findViewById(R.id.btn_change_windmill).setOnClickListener(this);
    }

    @Override
    protected void init() {
        // 实例化出场动画工具类
        mUtilAnim = new UtilShowAnim(vp_card);

        // 初始化数据
        initData();

        // 实例化适配器

        vp_card.setAdapter(mAdapter);

        // 实例化ViewPager切换动画类
        mTransformer = new CardTransformer();

        // 设置动画类
        vp_card.setPageTransformer(true, mTransformer);

        // 设置切换动画为 层叠效果，并获取预加载数量
        int offscreen = mTransformer.setTransformerType(CardTransformer.ANIM_TYPE_STACK);
        // 设置ViewPager的预加载数量
        vp_card.setOffscreenPageLimit(offscreen);

        initBtn();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }

    }

    /**
     * 处理数据
     */
    private void initData() {
        mList = new ArrayList<>();


    }

    // =============================================================================================
    // =========================== 以下是按钮显示动画相关，与本Demo无关 ================================
    // =============================================================================================

    /**
     * 出场效果按钮容器
     */
    private RelativeLayout rl_show_card;
    /**
     * 出场效果按钮容器中的箭头
     */
    private ImageView iv_show_card_arrow;
    /**
     * 切换效果按钮容器
     */
    private RelativeLayout rl_change;
    /**
     * 切换效果按钮容器中的箭头
     */
    private ImageView iv_change_arrow;

    /**
     * 初始化按钮
     */
    private void initBtn() {
        rl_show_card = findViewById(R.id.rl_show_card);
        iv_show_card_arrow = findViewById(R.id.iv_show_card_arrow);
        rl_change = findViewById(R.id.rl_change);
        iv_change_arrow = findViewById(R.id.iv_change_arrow);

        // 点击事件
        rl_show_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 改变显示状态
                changeBtnsShowType(rl_show_card, iv_show_card_arrow);
            }
        });
        // 点击事件
        rl_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 改变显示状态
                changeBtnsShowType(rl_change, iv_change_arrow);
            }
        });

        // 隐藏按钮
        rl_show_card.post(new Runnable() {
            @Override
            public void run() {
                iv_show_card_arrow.setRotation(180);
                hideBtns(rl_show_card, iv_show_card_arrow, 500);
            }
        });

        // 隐藏按钮
        rl_change.post(new Runnable() {
            @Override
            public void run() {
                iv_change_arrow.setRotation(180);
                hideBtns(rl_change, iv_change_arrow, 540);
            }
        });
    }

    /**
     * 改变显示状态
     *
     * @param rl
     * @param arrow
     */
    private void changeBtnsShowType(RelativeLayout rl, ImageView arrow) {
        if (rl == null) {
            return;
        }

        float ty = rl.getTranslationY();

        if (ty == 0) {
            hideBtns(rl, arrow);
        } else {
            showBtns(rl, arrow);
        }
    }

    /**
     * 显示按钮
     *
     * @param rl
     * @param arrow
     */
    private void showBtns(RelativeLayout rl, ImageView arrow) {
        rl.animate()
                .translationY(0)
                .setDuration(500)
                .setStartDelay(0)
                .setInterpolator(new OvershootInterpolator(1.5f))
                .start();

        if (arrow != null) {
            arrow.animate()
                    .rotation(180)
                    .setDuration(500)
                    .setStartDelay(0)
                    .setInterpolator(new OvershootInterpolator(1.5f))
                    .start();
        }
    }

    /**
     * 隐藏按钮
     *
     * @param rl
     * @param arrow
     */
    private void hideBtns(RelativeLayout rl, ImageView arrow) {
        hideBtns(rl, arrow, 0);
    }

    /**
     * 隐藏按钮
     *
     * @param rl
     * @param arrow
     * @param startDelay 延迟时间
     */
    private void hideBtns(RelativeLayout rl, ImageView arrow, int startDelay) {

        int arrowHeight = 0;
        if (arrow != null) {
            arrowHeight = arrow.getHeight();
        }
        int ty = rl.getHeight() - arrowHeight - rl.getPaddingTop();

        rl.animate()
                .translationY(ty)
                .setDuration(500)
                .setStartDelay(startDelay)
                .setInterpolator(new OvershootInterpolator(1.5f))
                .start();

        if (arrow != null) {
            arrow.animate()
                    .rotation(0)
                    .setDuration(500)
                    .setStartDelay(startDelay)
                    .setInterpolator(new OvershootInterpolator(1.5f))
                    .start();
        }
    }
}
