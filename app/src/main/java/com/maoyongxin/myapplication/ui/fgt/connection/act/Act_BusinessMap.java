package com.maoyongxin.myapplication.ui.fgt.connection.act;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.jky.baselibrary.util.image.glide.GlideCircleTransform;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.ScaleAnimation;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;


import com.bumptech.glide.Glide;
import com.jky.baselibrary.util.image.glide.GlideCircleTransform;
import com.lykj.aextreme.afinal.utils.ACache;
import com.lykj.aextreme.afinal.utils.Debug;
import com.lykj.aextreme.afinal.utils.MyToast;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.common.BaseAct;
import com.maoyongxin.myapplication.common.MyApplication;
import com.maoyongxin.myapplication.http.callback.EntityCallBack;
import com.maoyongxin.myapplication.http.entity.PoiInfoBean;
import com.maoyongxin.myapplication.http.request.ReqCommunity;
import com.maoyongxin.myapplication.http.request.ReqNearByUser;
import com.maoyongxin.myapplication.http.response.CommunityListResponse;
import com.maoyongxin.myapplication.http.response.NearbyUserResponse;
import com.maoyongxin.myapplication.ui.fgt.community.act.Act_News_Web;
import com.maoyongxin.myapplication.ui.fgt.connection.adapter.PoiListAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.functions.Consumer;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 企业地图
 */
public class Act_BusinessMap extends BaseAct implements AMap.OnMarkerClickListener {
    private TextureMapView map;
    private double poiLat;
    private double poiLng;
    private String poiType;
    private PoiSearch poiSearch;
    private PoiSearch.Query query;
    private ArrayList<PoiInfoBean> infoBeen = new ArrayList<>();
    private ListView list_poiResult;
    private EditText seacher;
    private Boolean isUseable = false;
    private ACache aCache;
    private ArrayList<PoiInfoBean> vipinfoBeen =new ArrayList<>();
    private final double EARTH_RADIUS = 6378137.0;
    private CheckBox vip_company,cus_company;
    private String webapi="http://st.3dgogo.com/index/enterprise_publicity/get_community_id_details_url_api.html?community_id=";
    private String searchableApi="http://st.3dgogo.com/index/user/is_get_enterprise_num.html?uid=";
    @Override
    protected void handlerPassMsg(int target, int target1, Object obj) {

    }
    @Override
    public int initLayoutId() {
        return R.layout.act_businessmap;
    }

    @Override
    public void initView() {
        hideHeader();
        aCache = ACache.get(this);
        setOnClickListener(R.id.friends_back);
        list_poiResult = getView(R.id.list_poiResult);
        seacher = getView(R.id.edit_poi_search);

        vip_company=getView(R.id.vip_company);
        cus_company=getView(R.id.cus_company);

        mapInit();
    }

    @Override
    public void initData() {

        seacher.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (seacher.getText().toString().length() > 0) {
                    searchPoi(seacher.getText().toString(), MyApplication.getMyCurrentLocation().getCityName(), poiLat, poiLng);
                }
                else
                {
                    searchPoi("企业", MyApplication.getMyCurrentLocation().getCityName(), poiLat, poiLng);
                }
            }
        });


        vip_company.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
              if(isChecked)
              {

                  if (seacher.getText().toString().length() > 0) {
                      searchPoi(seacher.getText().toString(), MyApplication.getMyCurrentLocation().getCityName(), poiLat, poiLng);
                  } else {
                      searchPoi("企业", MyApplication.getMyCurrentLocation().getCityName(), poiLat, poiLng);
                  }
              }


            }
        });

        cus_company.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked)
                {

                    if (seacher.getText().toString().length() > 0) {
                        searchPoi(seacher.getText().toString(), MyApplication.getMyCurrentLocation().getCityName(), poiLat, poiLng);
                    } else {
                        searchPoi("企业", MyApplication.getMyCurrentLocation().getCityName(), poiLat, poiLng);
                    }
                }
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
            case R.id.friends_back:
                finish();
                break;
        }
    }

    private ProgressDialog mProgressDialog;
    private AMap aMap;

    public void mapInit() {
        poiType = "企业";
        //第三界面
        map = getView(R.id.map);
        map.onCreate(mSavedInstanceState);
        if (aMap == null) {
            aMap = map.getMap();
            aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        }
        mProgressDialog = new ProgressDialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog);
        mProgressDialog.setCancelable(true);
        mProgressDialog.setMessage(getString(R.string.locating));
        MyLocationStyle myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(3000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        // 自定义定位蓝点图标
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ico_myaddress));
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.showBuildings(false);

        aMap.getUiSettings().setZoomControlsEnabled(true);
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。

        aMap.setMyLocationRotateAngle(180);
        aMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
        aMap.getUiSettings().setZoomControlsEnabled(true);
        aMap.getUiSettings().setScaleControlsEnabled(true);
        aMap.getUiSettings().setZoomControlsEnabled(true);
        aMap.getUiSettings().setRotateGesturesEnabled(true);
        aMap.getUiSettings().setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_CENTER);
        aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                startAct(Act_BusinessMap.class);
            }
        });
        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                poiLat = cameraPosition.target.latitude;
                poiLng = cameraPosition.target.longitude;

                aMap.clear();
                markDatas.clear();
                if (seacher.getText().toString().length() > 0) {
                    searchPoi(seacher.getText().toString(), MyApplication.getMyCurrentLocation().getCityName(), poiLat, poiLng);
                } else {
                    searchPoi("企业", MyApplication.getMyCurrentLocation().getCityName(), poiLat, poiLng);
                }

            }
        });
        aMap.setOnMapTouchListener(new AMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        break;
                }
            }
        });
        poiLat = Double.parseDouble(MyApplication.getMyCurrentLocation().
                getLatitude());
        poiLng = Double.parseDouble(MyApplication.getMyCurrentLocation().
                getLongtitude());
        aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        searchPoi("企业", MyApplication.getMyCurrentLocation().getCityName(), poiLat, poiLng);
        LatLng latlng = new LatLng(Double.valueOf(MyApplication.getMyCurrentLocation().getLatitude()), Double.valueOf(MyApplication.getMyCurrentLocation().getLongtitude()));
        aMap.animateCamera(CameraUpdateFactory.newLatLng(latlng));
    }

    /**
     * 开始进行poi搜索
     */
    private void searchPoi(String keyWord, String cityCode,final  double myLatitude,final  double myLngtitude) {
        query = new PoiSearch.Query(keyWord, poiType, cityCode);
        //keyWord表示搜索字符串，
        // cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
        query.setPageSize(50);// 设置每页最多返回多少条poiitem
        query.setPageNum(1);//设置查询页码
        poiSearch = new PoiSearch(getActivity(), query);
        poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(myLatitude,
                myLngtitude), 5000));
        poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult poiResult, int j) {

                infoBeen.clear();



                if(vip_company.isChecked())
                {
                    getNearbyCommunityList(myLatitude,myLngtitude);
                }
                if (cus_company.isChecked())
                {
                    if (poiResult.getPois().size() != 0) {
                        for (int i = 0; i < poiResult.getPois().size(); i++) {
                            PoiInfoBean poi = new PoiInfoBean();
                            poi.setLatitude(poiResult.getPois().get(i).getLatLonPoint().getLatitude() + "");
                            poi.setLngtitude(poiResult.getPois().get(i).getLatLonPoint().getLongitude() + "");
                            poi.setPoiAddress(poiResult.getPois().get(i).getSnippet());
                            poi.setPoiDistance(poiResult.getPois().get(i).getDistance() + "公里");
                            poi.setPoiType(poiResult.getPois().get(i).getTypeDes());
                            poi.setPoiName(poiResult.getPois().get(i).getTitle());
                            poi.setEmail(poiResult.getPois().get(i).getEmail());
                            poi.setTel(poiResult.getPois().get(i).getTel());
                            poi.setWebsite(poiResult.getPois().get(i).getWebsite());

                            if (poiResult.getPois().get(i).getTitle().endsWith("有限公司")) {
                                infoBeen.add(poi);
                                LatLng latLng = new LatLng(poiResult.getPois().get(i).getLatLonPoint().getLatitude(), poiResult.getPois().get(i).getLatLonPoint().getLongitude());

                                addMarkersToMap(latLng, poiResult.getPois().get(i).getTitle());
                            }
                        }
                    }
                }


                for(int i=0;i<vipinfoBeen.size();i++)
                {
                    infoBeen.add(vipinfoBeen.get(i));
                }


                if (adapter == null) {




                    adapter = new PoiListAdapter(getActivity(), infoBeen);

                    adapter.setOnDetailClicklistener(new PoiListAdapter.OnDetailClicklistener() {
                        @Override
                        public void goDetail(PoiInfoBean bean) {
                            if(bean.getPoinote()==1)
                            {
                                Intent serviceIntent=new Intent();

                                serviceIntent.putExtra("communityId",bean.getTargetId());
                                serviceIntent.putExtra("url",webapi+bean.getTargetId());
                                startAct(serviceIntent, Act_News_Web.class);
                            }

                          else{
                                Intent vipintent = new Intent();
                                vipintent.putExtra("bean", bean);
                                vipintent.putExtra("vipstatu",isUseable);
                                startAct(vipintent, Act_CompanyDetails.class);
                            }



                        }
                    });

                    adapter.setOnLineClicklistener(new PoiListAdapter.OnLineClicklistener() {
                        @Override
                        public void lineClick(int position) {
                            List<Marker> mapScreenMarkers = aMap.getMapScreenMarkers();
                            for (int i = 0; i < mapScreenMarkers.size(); i++) {
                                Marker marker = mapScreenMarkers.get(i);
                                if (i == mapScreenMarkers.size() - 1) {
                               //    aMap.clear();
                                }
                            }
                          //  addMarker(infoBeen.get(position).getLatitude(), infoBeen.get(position).getLngtitude(), infoBeen.get(position).getPoiAddress(), infoBeen.get(position).getPoiName(), true);
                        }
                    });

                    list_poiResult.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                else
                 {
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {
            }
        });
        poiSearch.searchPOIAsyn();
    }
    private void getNearbyCommunityList(final double lat,final double lnt) {
        ReqCommunity.getNearbyCommunityList(getActivity(), getActivityTag(), MyApplication.getMyCurrentLocation().getAdCode(), new EntityCallBack<CommunityListResponse>() {
            @Override
            public Class<CommunityListResponse> getEntityClass() {
                return CommunityListResponse.class;
            }

            @Override
            public void onSuccess(CommunityListResponse resp) {

                if (resp.is200()) {
                    vipinfoBeen.clear();
                    for (int i = 0; i < resp.obj.getCommnunityList().size(); i++) {

                        PoiInfoBean poi2 = new PoiInfoBean();
                        poi2.setLatitude(resp.obj.getCommnunityList().get(i).getLatitude() + "");
                        poi2.setLngtitude(resp.obj.getCommnunityList().get(i).getLongitude() + "");
                        poi2.setPoiName(resp.obj.getCommnunityList().get(i).getCommunityName());
                        poi2.setPoiAddress(resp.obj.getCommnunityList().get(i).getAddress());
                        poi2.setPoiType("彼信认证企业："+resp.obj.getCommnunityList().get(i).getCommunityNote());
                        poi2.setTargetId(resp.obj.getCommnunityList().get(i).getCommunityId());
                        poi2.setPoinote(1);
                        if(gps2m(resp.obj.getCommnunityList().get(i).getLatitude(),resp.obj.getCommnunityList().get(i).getLongitude() ,poiLat ,poiLng)<5000)
                        {



                            double mlat = Double.parseDouble(poi2.getLatitude());
                            double mlng = Double.parseDouble(poi2.getLngtitude());

                            LatLng latLng = new LatLng(mlat, mlng);
                            addvipMarkersToMap(latLng,poi2.getPoiName());
                            vipinfoBeen.add(poi2);
                        }

                    }
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

    private PoiListAdapter adapter;
    /**
     * 在地图上添加marker
     */
    private MarkerOptions markerOption;
    Marker marker;
    List<Marker> markDatas = new ArrayList<>();

    private void addMarkersToMap(LatLng latlng, String vipname) {
        MyLocationStyle myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(3000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        // 自定义定位蓝点图标
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ico_myaddress));
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        markerOption = new MarkerOptions().icon(BitmapDescriptorFactory.fromView(getvipView(vipname)))
                .position(latlng)
                .draggable(true);
        marker = aMap.addMarker(markerOption);
        markDatas.add(marker);
    }


    private void addvipMarkersToMap(LatLng latlng, String vipname) {
        MyLocationStyle myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(3000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        // 自定义定位蓝点图标
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ico_myaddress));
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        markerOption = new MarkerOptions().icon(BitmapDescriptorFactory.fromView(getcommunityView(vipname)))
                .position(latlng)
                .draggable(true);
        marker = aMap.addMarker(markerOption);
        markDatas.add(marker);
    }




    protected View getvipView(String pm_val) {
        View view = getLayoutInflater().inflate(R.layout.vipmaker, null);
        TextView tv_val = (TextView) view.findViewById(R.id.marker_tv_val);
        tv_val.setText(pm_val);
        return view;
    }

    protected View getcommunityView(String pm_val) {
        View view = getLayoutInflater().inflate(R.layout.communitymaker, null);
        TextView tv_val = (TextView) view.findViewById(R.id.marker_tv_community);
        tv_val.setText(pm_val);
        return view;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        for (int i = 0; i < markDatas.size(); i++) {
            if (marker.equals(markDatas.get(i))&&(infoBeen.get(i).getPoinote()==2)) {
                Intent vipintent = new Intent();
                vipintent.putExtra("bean", infoBeen.get(i));
                startAct(vipintent, Act_CompanyDetails.class);
            }
            else if(marker.equals(markDatas.get(i))&&(infoBeen.get(i).getPoinote()==1))
            {
                Intent serviceIntent=new Intent();

                serviceIntent.putExtra("communityId",infoBeen.get(i).getTargetId());
                serviceIntent.putExtra("url",webapi+infoBeen.get(i).getTargetId());
                startAct(serviceIntent, Act_News_Web.class);
            }
        }
        return true;
    }

    private void addMarker(String lat, String lng, String myAddress, String snippet, boolean isBig) {
        mProgressDialog.dismiss();
        MarkerOptions makerOpt = getMarkerOptions(lat, lng, myAddress, snippet, isBig);
        Marker maker = aMap.addMarker(makerOpt);
        maker.setInfoWindowEnable(false);
        Animation scAnimation = new ScaleAnimation(maker.getRotateAngle(), 0.5f, 0.5f, 0.5f);
        long duration = 10L;
        scAnimation.setDuration(duration);
        scAnimation.setInterpolator(new LinearInterpolator());
    }

    private void addvipMarker(String lat, String lng, String myAddress, String snippet, boolean isBig) {
        mProgressDialog.dismiss();
        MarkerOptions makerOpt = getVipMarkerOptions(lat, lng, myAddress, snippet, isBig);
        Marker maker = aMap.addMarker(makerOpt);
        maker.setInfoWindowEnable(false);
        Animation scAnimation = new ScaleAnimation(maker.getRotateAngle(), 0.5f, 0.5f, 0.5f);
        long duration = 10L;
        scAnimation.setDuration(duration);
        scAnimation.setInterpolator(new LinearInterpolator());
    }


    private MarkerOptions getMarkerOptions(String lat, String lng, String myAddress, String snippet, boolean isBig) {
        double mlat = Double.parseDouble(lat);
        double mlng = Double.parseDouble(lng);
        LatLng ll = new LatLng(mlat, mlng);
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(ll);
        markerOption.title(myAddress).snippet(snippet);
        markerOption.setFlat(false);
        markerOption.draggable(false);//设置Marker可拖动
        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(getResources(), R.mipmap.icon_poi_new)));
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        return markerOption;
    }

    public void drawMarkers(final LatLng latlng,final String userhead, final String id) {
        //获取自定义View
        final View view = getActivity().getLayoutInflater().inflate(R.layout.layout_user_marker, null);
        final com.makeramen.roundedimageview.RoundedImageView iv_user = (com.makeramen.roundedimageview.RoundedImageView) view.findViewById(R.id.img_myUserHeadIcon);
        if (!userhead.equals("") && userhead != null) {
            Glide.with(getActivity()).load(userhead).into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    iv_user.setImageDrawable(resource);

                    Marker marker = aMap.addMarker(new MarkerOptions().position(latlng)
                            .icon(BitmapDescriptorFactory.fromView(view)).draggable(true));
                    marker.setInfoWindowEnable(true);
                    marker.setSnippet(id);

                }
            });

        }

    }

    private MarkerOptions getVipMarkerOptions(String lat, String lng, String myAddress, String snippet, boolean isBig) {
        double mlat = Double.parseDouble(lat);
        double mlng = Double.parseDouble(lng);
        LatLng ll = new LatLng(mlat, mlng);
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(ll);
        markerOption.title(myAddress).snippet(snippet);
        markerOption.setFlat(false);
        markerOption.draggable(false);//设置Marker可拖动
        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(getResources(), R.mipmap.icon_point_restraunt_poi_big)));
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        return markerOption;
    }



    private void getallpoi(double lat,double ltn)
    {
        aMap.clear();
        markDatas.clear();




            if (seacher.getText().toString().length() > 0) {
                searchPoi(seacher.getText().toString(), MyApplication.getMyCurrentLocation().getCityName(), lat, ltn);
            } else {
                searchPoi("企业", MyApplication.getMyCurrentLocation().getCityName(), lat, ltn);
            }







    }



    private void getStrangerList(final  double lat,final  double lng) {
        ReqNearByUser.getUsers(getActivity(), getActivityTag(), MyApplication.getCurrentUserInfo().getUserId(), lat + "", lng + "", new EntityCallBack<NearbyUserResponse>() {
            @Override
            public Class<NearbyUserResponse> getEntityClass() {
                return NearbyUserResponse.class;
            }

            @Override
            public void onSuccess(NearbyUserResponse resp) {
                if (resp.is200()) {
                    for (int i = 0; i < resp.obj.getUserlist().size(); i++) {
                        drawMarkers(new LatLng(resp.obj.getUserlist().get(i).getLatitude(), resp.obj.getUserlist().get(i).getLongitude()), resp.obj.getUserlist().get(i).getHeadImg(), resp.obj.getUserlist().get(i).getUserId() + "");
                    }
                } else {
                   // NToast.shortToast(getActivity(), resp.msg);
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




    private double gps2m(double lat_a, double lng_a, double lat_b, double lng_b) {

        double radLat1 = (lat_a * Math.PI / 180.0);

        double radLat2 = (lat_b * Math.PI / 180.0);

        double a = radLat1 - radLat2;

        double b = (lng_a - lng_b) * Math.PI / 180.0;

        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));

        s = s * EARTH_RADIUS;

        s = Math.round(s * 10000) / 10000;

        return s;

    }

     private void getallsearch(final double poiLat,final  double poiLng)
     {

         if(vip_company.isChecked())
         {

             getNearbyCommunityList(poiLat,poiLng);

         }



    }




}
