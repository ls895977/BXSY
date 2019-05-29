package com.maoyongxin.myapplication.tool.utils;

import android.graphics.Bitmap;
import android.os.Build;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.ui.act_businessDetail;
import com.maoyongxin.myapplication.ui.fgt.community.fgt.Fgt_Dynamic;
import com.maoyongxin.myapplication.ui.fgt.message.act.Act_StrangerDetail;

/**
 * 高斯模糊Demo页工具类
 *
 * Created by Bamboy on 2017/4/7.
 */
public class BlurUtil {

    private Act_StrangerDetail mActivity;
    private Fgt_Dynamic FgtDymic;
    private act_businessDetail BusinessActivity;
    /**
     * 工具箱
     */
    public UtilBox utils;

    /**
     * 构造
     * @param activity
     */
    public BlurUtil(act_businessDetail activity) {
        this.BusinessActivity = activity;
        utils = UtilBox.getBox();
    }

    public BlurUtil(Act_StrangerDetail activity) {
        this.mActivity = activity;
        utils = UtilBox.getBox();
    }
    public BlurUtil(Fgt_Dynamic FgtDymic) {
        this.FgtDymic = FgtDymic;
        utils = UtilBox.getBox();
    }

    /**
     * 模糊图片
     *
     * @param iv_head_portrait 需要模糊的ImageView
     */
    public void clickBlurImg(ImageView iv_head_portrait ,float level) {
        // 将图片进行高斯模糊，
        // 最后一个参数是模糊等级，值为 0~25
        utils.bitmap.blurImageView(mActivity, iv_head_portrait, level);
    }
    public void BusinessBlurImg(ImageView iv_head_portrait ,float level) {
        // 将图片进行高斯模糊，
        // 最后一个参数是模糊等级，值为 0~25
        utils.bitmap.blurImageView(BusinessActivity, iv_head_portrait, level);
    }

    public void fgt_clickBlurImg(ImageView iv_head_portrait ,float level) {
        // 将图片进行高斯模糊，
        // 最后一个参数是模糊等级，值为 0~25
        utils.bitmap.blurImageView(FgtDymic.getActivity(), iv_head_portrait, level);
    }
    /**
     * 显示弹窗
     *
     * @param rl_popup_window      弹窗容器
     * @param iv_popup_window_back 弹窗背景
     */
    public void clickPopupWindow(RelativeLayout rl_popup_window, ImageView iv_popup_window_back) {
        // 获取截图的Bitmap
        Bitmap bitmap = utils.ui.getDrawing(mActivity);

        if (utils.info.getPhoneSDK() >= Build.VERSION_CODES.KITKAT && bitmap != null) {
            // 将截屏Bitma放入ImageView
            iv_popup_window_back.setImageBitmap(bitmap);
            // 将ImageView进行高斯模糊【25是最高模糊程度】【最后一个参数是蒙上一层颜色，此参数可不填】
            // 如果需要更高的模糊程度，可以将此行代码写两遍
            utils.bitmap.blurImageView(mActivity, iv_popup_window_back, 25, mActivity.getResources().getColor(R.color.colorWhite_t8));
        } else {
            // 获取的Bitmap为null时，用半透明代替
            iv_popup_window_back.setBackgroundColor(mActivity.getResources().getColor(R.color.colorWhite_tD));
        }

        // 打开弹窗
        utils.anim.showPopupWindow(rl_popup_window, iv_popup_window_back);
    }
    public void fgt_clickPopupWindow(RelativeLayout rl_popup_window, ImageView iv_popup_window_back) {
        // 获取截图的Bitmap
        Bitmap bitmap = utils.ui.getDrawing(FgtDymic.getActivity());

        if (utils.info.getPhoneSDK() >= Build.VERSION_CODES.KITKAT && bitmap != null) {
            // 将截屏Bitma放入ImageView
            iv_popup_window_back.setImageBitmap(bitmap);
            // 将ImageView进行高斯模糊【25是最高模糊程度】【最后一个参数是蒙上一层颜色，此参数可不填】
            // 如果需要更高的模糊程度，可以将此行代码写两遍
            utils.bitmap.blurImageView(FgtDymic.getActivity(), iv_popup_window_back, 25, FgtDymic.getResources().getColor(R.color.colorWhite_t8));
        } else {
            // 获取的Bitmap为null时，用半透明代替
            iv_popup_window_back.setBackgroundColor(FgtDymic.getResources().getColor(R.color.colorWhite_tD));
        }

        // 打开弹窗
        utils.anim.showPopupWindow(rl_popup_window, iv_popup_window_back);
    }
    /**
     * 关闭弹窗
     *
     * @param rl_popup_window      弹窗容器
     * @param iv_popup_window_back 弹窗背景
     */
    public void clickClosePopupWindow(RelativeLayout rl_popup_window, ImageView iv_popup_window_back) {
        utils.anim.hidePopupWindow(rl_popup_window, iv_popup_window_back);
    }

    public void clickClose_fgt_PopupWindow(RelativeLayout rl_popup_window, ImageView iv_popup_window_back) {
        utils.anim.hidePopupWindow(rl_popup_window, iv_popup_window_back);
    }
}
