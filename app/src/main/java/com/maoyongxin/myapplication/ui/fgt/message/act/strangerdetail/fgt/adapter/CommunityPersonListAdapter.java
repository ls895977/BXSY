package com.maoyongxin.myapplication.ui.fgt.message.act.strangerdetail.fgt.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.bumptech.glide.Glide;
import com.lykj.aextreme.afinal.utils.MyToast;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.common.AppConfig;
import com.maoyongxin.myapplication.common.ComantUtils;
import com.maoyongxin.myapplication.common.MyApplication;
import com.maoyongxin.myapplication.http.callback.EntityCallBack;
import com.maoyongxin.myapplication.http.entity.MyCommunityPersonInfo;
import com.maoyongxin.myapplication.http.request.ReqCommunity;
import com.maoyongxin.myapplication.http.response.BaseResp;
import com.maoyongxin.myapplication.ui.fgt.connection.act.Act_HistoricalRecord;
import com.maoyongxin.myapplication.ui.fgt.message.act.Act_StrangerDetail;
import com.maoyongxin.myapplication.view.SelectableRoundedImageView;

import java.util.List;

import io.rong.imkit.RongIM;

/**
 * Created by maoyongxin on 2017/12/7.
 */

public class CommunityPersonListAdapter extends BaseAdapter {
    private String communityId;
    private String superManagerId;
    private List<String> managerId;
    private List<MyCommunityPersonInfo.UserList> userLists;
    private Context context;
    private LayoutInflater inflater;
    private OnRefreshListener onRefreshListener;
    private boolean isManager = false;
    private boolean isFromFragment=false;

    public CommunityPersonListAdapter(String communityId, String superManagerId, List<String> managerId, List<MyCommunityPersonInfo.UserList> userLists, Context context, boolean isFromFragment) {
        this.communityId = communityId;
        this.userLists = userLists;
        this.context = context;
        this.superManagerId = superManagerId;
        this.managerId = managerId;
        this.inflater = LayoutInflater.from(context);
        this.isFromFragment=isFromFragment;
    }

    @Override
    public int getCount() {
        return userLists.size();
    }

    @Override
    public Object getItem(int position) {
        return userLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_community_person_list, null);
            holder.img_head_communityPerson = (SelectableRoundedImageView) convertView.findViewById(R.id.img_head_communityPerson);
            holder.tv_communityPersonName = (TextView) convertView.findViewById(R.id.tv_communityPersonName);
            holder.tv_communityPersonType = (TextView) convertView.findViewById(R.id.tv_communityPersonType);
            holder.tv_communityPersonNote = (TextView) convertView.findViewById(R.id.tv_communityPersonNote);
            holder.tv_communityPersonAddress = (TextView) convertView.findViewById(R.id.tv_communityPersonAddress);
            holder.btn_deletCommunityPerson = (TextView) convertView.findViewById(R.id.btn_deletCommunityPerson);
            holder.bt_detail=(Button) convertView.findViewById(R.id.bt_detail);
            holder.bt_chat=(Button) convertView.findViewById(R.id.bt_chat);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (userLists.get(position).getHeadImg() != null && !userLists.get(position).getHeadImg().equals("")) {

           // Glide.with(context).load(userLists.get(position).getHeadImg()).into(holder.img_head_communityPerson);
            if(userLists.get(position).getHeadImg().startsWith("http"))
            {
                Glide.with(context).load( userLists.get(position).getHeadImg()).into(holder.img_head_communityPerson);

            }
           else if(userLists.get(position).getHeadImg().startsWith("uploads"))
            {
                Glide.with(context).load(  ComantUtils.MyUrlHot1+userLists.get(position).getHeadImg()).into(holder.img_head_communityPerson);

            }
            else  {

                Glide.with(context).load(  AppConfig.sRootUrl+"/logincontroller/getHeadImg/"+userLists.get(position).getHeadImg()).into(holder.img_head_communityPerson);
            }


        } else {
            Glide.with(context).load(R.mipmap.user_head_img).into(holder.img_head_communityPerson);



        }

        holder.img_head_communityPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, Act_StrangerDetail.class);
                in.putExtra("personId", userLists.get(position).getUserId());
                context.startActivity(in);
            }
        });

        holder.bt_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,Act_HistoricalRecord.class);

                intent.putExtra("targetuserid",userLists.get(position).getUserId());
                context.startActivity(intent);
            }
        });
        holder.tv_communityPersonName.setText(userLists.get(position).getUserName());
        holder.tv_communityPersonNote.setText(userLists.get(position).getNote());
        holder.bt_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RongIM.getInstance().startPrivateChat(context, userLists.get(position).getUserId(),userLists.get(position).getUserName());

            }
        });
        if(isFromFragment){

        }else{
            for (int i = 0; i < managerId.size(); i++) {
                if (userLists.get(position).getUserId().equals(managerId.get(i))) {
                    isManager = true;
                   // holder.btn_deletCommunityPerson.setVisibility(View.GONE);
                    break;
                } else {
                    isManager = false;
                  //  holder.btn_deletCommunityPerson.setVisibility(View.VISIBLE);
                }
            }
            if (userLists.get(position).getUserId().equals(superManagerId)) {
                holder.tv_communityPersonType.setText("管理员");
               // holder.btn_deletCommunityPerson.setVisibility(View.GONE);
            } else {
                if (isManager==false) {
                    holder.tv_communityPersonType.setText("业务经理");
                    holder.btn_deletCommunityPerson.setVisibility(View.VISIBLE);
                } else {
                    holder.tv_communityPersonType.setText("高级业务经理");
              //      holder.btn_deletCommunityPerson.setVisibility(View.GONE);
                }
            }
        }

        GeocodeSearch geocoderSearch = new GeocodeSearch(context);
        final ViewHolder finalHolder = holder;
        geocoderSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onGeocodeSearched(GeocodeResult result, int rCode) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
                finalHolder.tv_communityPersonAddress.setVisibility(View.VISIBLE);
                String formatAddress = result.getRegeocodeAddress().getFormatAddress();
                finalHolder.tv_communityPersonAddress.setText("");
            }
        });
        LatLonPoint lp = new LatLonPoint(userLists.get(position).getLatitude(), userLists.get(position).getLongitude());
        RegeocodeQuery query = new RegeocodeQuery(lp, 200, GeocodeSearch.AMAP);
        geocoderSearch.getFromLocationAsyn(query);
        holder.btn_deletCommunityPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReqCommunity.deleteUser(context, "adapter", MyApplication.getCurrentUserInfo().getUserId(), userLists.get(position).getUserId(), communityId, new EntityCallBack<BaseResp>() {
                    @Override
                    public Class<BaseResp> getEntityClass() {
                        return BaseResp.class;
                    }

                    @Override
                    public void onSuccess(BaseResp resp) {
                        MyToast.show(context, resp.msg);
                        if (resp.is200()) {
                            onRefreshListener.refresh();
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
        });
        return convertView;
    }

    class ViewHolder {
        SelectableRoundedImageView img_head_communityPerson;
        TextView tv_communityPersonName, tv_communityPersonType, tv_communityPersonNote, tv_communityPersonAddress;
        TextView btn_deletCommunityPerson;
        Button bt_chat,bt_detail;
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    public interface OnRefreshListener {
        void refresh();
    }
}
