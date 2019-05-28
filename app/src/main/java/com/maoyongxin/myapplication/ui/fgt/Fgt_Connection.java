package com.maoyongxin.myapplication.ui.fgt;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lykj.aextreme.afinal.utils.Debug;
import com.maoyongxin.myapplication.R;
import com.maoyongxin.myapplication.bean.ConnectionChlideBean;
import com.maoyongxin.myapplication.common.BaseFgt;
import com.maoyongxin.myapplication.common.ComantUtils;
import com.maoyongxin.myapplication.common.MyApplication;
import com.maoyongxin.myapplication.http.entity.PoiInfoBean;
import com.maoyongxin.myapplication.permission.RxPermissions;
import com.maoyongxin.myapplication.tool.LocationTool;
import com.maoyongxin.myapplication.ui.fgt.connection.act.Act_AnnouncementDetails;
import com.maoyongxin.myapplication.ui.fgt.connection.act.Act_AnnouncementRequirement;
import com.maoyongxin.myapplication.ui.fgt.connection.act.Act_BusinessMap;
import com.maoyongxin.myapplication.ui.fgt.connection.act.Act_ServiceProvider;
import com.maoyongxin.myapplication.ui.fgt.connection.act.bean.ConnectionBean;
import com.maoyongxin.myapplication.ui.fgt.connection.act.bean.UserRequitBean;
import com.maoyongxin.myapplication.ui.fgt.connection.adapter.ConnectionAdapter;
import com.maoyongxin.myapplication.ui.fgt.connection.adapter.ConnectionChlideAdapter;
import com.maoyongxin.myapplication.ui.fgt.connection.adapter.User_RequirementAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import okhttp3.Call;

import static android.view.View.GONE;

/**
 * 人脉
 */
public class Fgt_Connection extends BaseFgt implements BaseQuickAdapter.OnItemClickListener {
    private TextureMapView map;
    private double poiLat;
    private double poiLng;
    private PoiSearch.Query query;
    private PoiSearch poiSearch;
    private String poiType;
    private ArrayList<PoiInfoBean> infoBeen = new ArrayList<>();
    private RecyclerView myRecyclerView;
    //第三界面
    @Override
    public int initLayoutId() {
        return R.layout.fgt_connection;
    }

    private RxPermissions rxPermissions;
    private View haderView;

    @Override
    public void initView() {
        hideHeader();
      //  openPermissions();//权限
        haderView = LayoutInflater.from(context).inflate(R.layout.hader_connection, null);
        setOnClickListener(R.id.Service_provider);
        setOnClickListener(haderView, R.id.More);
        myRecyclerView = getView(R.id.Connection_Recyclerview);
        mapInit();
    }

    /**
     * 权限
     */
    public void openPermissions() {
        rxPermissions = new RxPermissions(getActivity());
        rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.RECORD_AUDIO)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) {
                        if (aBoolean) {
                        } else {
//                            openPermissions();
                            Toast.makeText(getActivity(), "请打开拍照权限", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void initData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        myRecyclerView.setLayoutManager(layoutManager);

        myAdapter = new ConnectionChlideAdapter(datas, getContext());
        myAdapter.setOnItemClickListener(this);
        myAdapter.addHeaderView(haderView);
        myRecyclerView.setAdapter(myAdapter);
        postCommunityAndGambitHotListApi();
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
            case R.id.Service_provider://服务商
                startAct(Act_ServiceProvider.class);
                break;
            case R.id.More://更多
                startAct(Act_AnnouncementRequirement.class);
                break;
        }
    }

    @Override
    public void sendMsg(int flag, Object obj) {

    }

    private ProgressDialog mProgressDialog;
    private AMap aMap;
    LocationTool myLocation;
    public void mapInit() {
        poiType = "企业";
        map = getView(R.id.map);
        map.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = map.getMap();
        }
        mProgressDialog = new ProgressDialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog);
        mProgressDialog.setCancelable(true);
        mProgressDialog.setMessage(getString(R.string.locating));
        MyLocationStyle myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
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
        aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
        aMap.setMyLocationRotateAngle(180);
        aMap.getUiSettings().setZoomControlsEnabled(false);
        aMap.getUiSettings().setScaleControlsEnabled(false);
        aMap.getUiSettings().setZoomControlsEnabled(false);
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
                searchPoi("企业", MyApplication.getMyCurrentLocation().getCityName(), poiLat, poiLng);
            }
        });
        aMap.setOnMapTouchListener(new AMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        getView().getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        getView().getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
            }
        });
        try{
            poiLat = Double.parseDouble(MyApplication.getMyCurrentLocation().getLatitude());
            poiLng = Double.parseDouble(MyApplication.getMyCurrentLocation().getLongtitude());
            aMap.moveCamera(CameraUpdateFactory.zoomTo(10));
            searchPoi("企业", MyApplication.getMyCurrentLocation().getCityName(), poiLat, poiLng);
            aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.valueOf(poiLat), Double.valueOf(poiLng)), 15));//镜头转移
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    /*
     * 获取公告
     */
    ConnectionChlideBean bean;

    List<ConnectionChlideBean.InfoBean> datas = new ArrayList<>();
    List<UserRequitBean.InfoBean> datas2=new ArrayList<>();


    private ConnectionChlideAdapter myAdapter;
    private User_RequirementAdapter nMyadapter;

    public void postCommunityAndGambitHotListApi() {
        OkHttpUtils.get().url("http://bisonchat.com/index/user_notice/getGatherNoticeListApi.html")
                .addParams("userId", MyApplication.getCurrentUserInfo().getUserId())
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    Gson gson = new Gson();

                    bean = gson.fromJson(response, ConnectionChlideBean.class);
                    for (int i = 0; i < bean.getInfo().size(); i++) {
                        datas.add(bean.getInfo().get(i));
                    }
                    myAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                }
            }
        });





    }

    Intent intent;

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        intent = new Intent();
        intent.putExtra("user_notice_id", datas.get(position).getId() + "");
        intent.putExtra("classify_id", datas.get(position).getClassify_id()+"");
        intent.setClass(context, Act_AnnouncementDetails.class);
        startActivityForResult(intent, 10);
    }

    /**
     * 开始进行poi搜索
     */
    private void searchPoi(String keyWord, String cityCode, double myLatitude, double myLngtitude) {
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
                aMap.clear();
                if (poiResult.getPois().size() != 0) {
                    infoBeen = new ArrayList<>();
                    for (int i = 0; i < poiResult.getPois().size(); i++) {
                        PoiInfoBean poi = new PoiInfoBean();
                        poi.setLatitude(poiResult.getPois().get(i).getLatLonPoint().getLatitude() + "");
                        poi.setLngtitude(poiResult.getPois().get(i).getLatLonPoint().getLongitude() + "");
                        poi.setPoiAddress(poiResult.getPois().get(i).getSnippet());
                        poi.setPoiDistance(poiResult.getPois().get(i).getDistance() + "公里");
                        poi.setPoiType(poiResult.getPois().get(i).getTypeDes());
                        poi.setPoiName(poiResult.getPois().get(i).getTitle());
                        if (poiResult.getPois().get(i).getTitle().endsWith("有限公司")) {
                            infoBeen.add(poi);
                        }
                    }
                }
                List<LatLng> datas = new ArrayList<>();
                for (int i = 0; i < infoBeen.size(); i++) {
                    LatLng latLng = new LatLng(Double.valueOf(infoBeen.get(i).getLatitude()), Double.valueOf(infoBeen.get(i).getLngtitude()));
                    datas.add(latLng);
                    addMarkersToMap(latLng);
                }
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {
            }
        });
        poiSearch.searchPOIAsyn();
    }
    /**
     * 在地图上添加marker
     */
    private MarkerOptions markerOption;
    Marker marker;
    private void addMarkersToMap(LatLng latlng) {
        MyLocationStyle myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(3000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        // 自定义定位蓝点图标
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ico_myaddress));
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        markerOption = new MarkerOptions().icon(BitmapDescriptorFactory.fromView(getvipView("")))
                .position(latlng)
                .draggable(true);
        marker = aMap.addMarker(markerOption);
    }

    protected View getvipView(String pm_val) {
        View view = getLayoutInflater().inflate(R.layout.vipmaker, null);
        TextView tv_val = (TextView) view.findViewById(R.id.marker_tv_val);
        tv_val.setVisibility(GONE);
        tv_val.setText(pm_val);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == 20) {
            postCommunityAndGambitHotListApi();
        }
    }
}
