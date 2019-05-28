package com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.fgt.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lykj.aextreme.afinal.utils.Debug;
import com.lykj.aextreme.afinal.utils.MyToast;
import com.makeramen.roundedimageview.RoundedImageView;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.common.ComantUtils;
import com.maoyongxin.myapplication.common.MyApplication;
import com.maoyongxin.myapplication.tool.SDCardUtil;
import com.maoyongxin.myapplication.tool.StringUtils;
import com.maoyongxin.myapplication.tool.UtilsTool;
import com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.base.GroupHuatiBase;
import com.maoyongxin.myapplication.ui.fgt.community.act.groupchatdetailnew.bean.ItemImageBean;
import com.sendtion.xrichtext.RichTextEditor;
import com.sendtion.xrichtext.RichTextView;
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.callback.OnUrlClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import io.github.rockerhieu.emojicon.EmojiconTextView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class GroupHuatiAdapter extends BaseMultiItemQuickAdapter<GroupHuatiBase, BaseViewHolder> {
    private Context context;
    private String webapi = "http://st.3dgogo.com/index/enterprise_publicity/get_community_id_details_url_api.html?community_id=";
    private String groupName;

    public GroupHuatiAdapter(List<GroupHuatiBase> data, Context context1, String groupName1) {
        super(data);
        this.context = context1;
        this.groupName = groupName1;
        addItemType(GroupHuatiBase.LEFT_BIG, R.layout.item_huati_img);//一张图片
        addItemType(GroupHuatiBase.THREE_ZHIDING, R.layout.item_huati_zhiding);//置顶
        addItemType(GroupHuatiBase.THREE_SMALL, R.layout.item_dynamic_gridview);//3张5张图片显示布局
        addItemType(GroupHuatiBase.RIGHT_BIG, R.layout.item_dynamic_gridviewnum);//2张4张图片显示布局
    }

    private int i = 1;
    List<ItemImageBean> imageBeans;

    @Override
    protected void convert(final BaseViewHolder helper, final GroupHuatiBase item) {
        switch (helper.getItemViewType()) {
            case GroupHuatiBase.LEFT_BIG://一张图片显示
                if (item.getImg() != null && !item.getImg().equals("")) {
                    helper.getView(R.id.item_videoImage).setVisibility(View.VISIBLE);
                    Glide.with(context).load(item.getImg()).into((RoundedImageView) helper.getView(R.id.item_videoImage));
                } else {
                    helper.getView(R.id.item_videoImage).setVisibility(View.GONE);
                }
                if (item.getContent().equals("")) {
                    helper.getView(R.id.tv_square_msgTitle).setVisibility(View.GONE);
                } else {
                    try {

                        if (UtilsTool.jieMiByte64(item.getContent()).contains("http")) {//截取内容和地址
                            imageBeans = new ArrayList<>();
                            String[] ling = UtilsTool.jieMiByte64(item.getContent()).split("</h3>");
                            for (int i = 0; i < ling.length; i++) {
                                ItemImageBean bean = new ItemImageBean();
                                String[] img = ling[i].split("=");
                                if (img.length > 1) {
                                    String[] img2 = img[1].split("/>");
                                    String[] img3 = img2[0].split("\"");
                                    bean.setUrl(img3[1]);
                                } else {
                                    bean.setUrl("");
                                }
                                String[] title = ling[i].split("<h3>");
                                if (title.length > 1) {
                                    bean.setStContext(title[1]);
                                } else {
                                    bean.setStContext("");
                                }
                                imageBeans.add(bean);
                            }
                            helper.getView(R.id.tv_square_msgTitle).setVisibility(View.VISIBLE);
                            helper.setText(R.id.tv_square_msgTitle, imageBeans.get(0).getStContext());//内容
                        } else {
//                        et_new_content.setVisibility(View.GONE);
                            helper.getView(R.id.tv_square_msgTitle).setVisibility(View.VISIBLE);
                            helper.setText(R.id.tv_square_msgTitle, new String(Base64.decode(item.getContent().getBytes(), Base64.DEFAULT)));//内容
                        }
                    } catch (Exception e) {
                    }
                }
                break;
            case GroupHuatiBase.THREE_ZHIDING://置顶

                break;
            case GroupHuatiBase.THREE_SMALL://3张5张图片显示布局

                break;
            case GroupHuatiBase.RIGHT_BIG://2张4张图片显示布局
                break;
        }
        RequestOptions options = new RequestOptions();
        options.error(R.mipmap.default_group);
        Glide.with(context).load(item.getHeadImg())
                .apply(options)
                .into((ImageView) helper.getView(R.id.img_square_header));
        helper.setText(R.id.tv_square_person_name, item.getUserName());
        if (item.getCreate_time() != null) {
            helper.setText(R.id.tv_huati_timedate, item.getCreate_time());
        }
        try {
            helper.setText(R.id.tv_square_Title, UtilsTool.jieMiByte64(item.getTitle()));
        } catch (Exception e) {
        }

        try{

            EmojiconTextView topic_title =helper.getView(R.id.topic_title);

            topic_title.setText(new String(Base64.decode(item.getTitle().getBytes(), Base64.DEFAULT)));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }



        TextView bac = helper.getView(R.id.tv_groupName);
        if (groupName != null) {
            switch (i) {
                case 1:
                    bac.setBackgroundResource(R.drawable.icon_square_title1);
                    i++;
                    break;
                case 2:
                    bac.setBackgroundResource(R.drawable.icon_square_title2);
                    i++;
                    break;
                case 3:
                    bac.setBackgroundResource(R.drawable.icon_square_title3);
                    i++;
                    break;
                case 4:
                    bac.setBackgroundResource(R.drawable.icon_square_title4);
                    i++;
                    break;
                case 5:
                    bac.setBackgroundResource(R.drawable.icon_square_title5);
                    i = 1;
                    break;
            }
            helper.setText(R.id.tv_groupName, "#" + groupName);
        }
        if (item.getUid().equals(MyApplication.getCurrentUserInfo().getUserId())) {
            helper.getView(R.id.img_delete).setVisibility(VISIBLE);
        } else {
            helper.getView(R.id.img_delete).setVisibility(GONE);
        }
        helper.setText(R.id.tv_pinglun_num, item.getPost_num());
        helper.setText(R.id.numZan, item.getPraise_num());
        helper.addOnClickListener(R.id.img_delete);//删除
        helper.getView(R.id.zanImg).setSelected(item.isPraise());
        helper.addOnClickListener(R.id.zanImg);
    }

}
