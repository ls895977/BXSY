/**
 * Project Name:Android_Car_Example
 * File Name:PoiSearchTask.java
 * Package Name:com.amap.api.car.example
 * Date:2015年4月7日上午11:25:07
 */

package com.maoyongxin.myapplication.tool;

import android.content.Context;
import android.util.Log;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.poisearch.PoiSearch.OnPoiSearchListener;
import com.amap.api.services.poisearch.PoiSearch.Query;
import com.lykj.aextreme.afinal.utils.Debug;

import java.util.List;


/**
 * ClassName:PoiSearchTask <br/>
 * Function: 简单封装了poi搜索的功能，搜索结果配合RecommendAdapter进行使用显示 <br/>
 * Date: 2015年4月7日 上午11:25:07 <br/>
 *
 * @author yiyi.qi
 * @see
 * @since JDK 1.6
 */
public class PoiSearchTask implements OnPoiSearchListener, GeocodeSearch.OnGeocodeSearchListener {
    private Context mContext;
    private PoiSearchLiner onLiner;
    private GeocodeSearch geocoderSearch;
    private onGeocodeSearchedListener searchListener;
    private onRegeocodeSearched regeocodeListener;
    private Query query;

    public PoiSearchTask(Context context) {
        mContext = context;
        geocoderSearch = new GeocodeSearch(context);
        geocoderSearch.setOnGeocodeSearchListener(this);
    }

    /**
     * @param keyWord  搜索字段
     * @param city     搜素城市
     * @param PageSize 搜索返回条数
     * @param PageNum  搜素返回页数
     */
    public void search(String keyWord, String city, int PageSize, int PageNum) {
        query = new Query(keyWord, "", city);
        query.setPageSize(PageSize);
        query.setPageNum(PageNum);
        PoiSearch poiSearch = new PoiSearch(mContext, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }

    /**
     * 不带范围的搜索查询 带搜索结果
     * <p/>
     * 搜索字段
     * 搜素城市
     * 搜索返回条数
     * 搜素返回页数
     * onLiner  实现返回结果监听
     */
    public void search(String keywrokd, String citAddr, PoiSearchLiner onLiner) {
        this.onLiner = onLiner;
        query = new Query(keywrokd, "",citAddr);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(0);// 设置查第一页
        PoiSearch poiSearch = new PoiSearch(mContext, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }

    /**
     * 带范围的搜索查询
     *
     * @param keyWord  搜索字段
     * @param city     搜素城市
     * @param PageSize 搜索返回条数
     * @param PageNum  搜素返回页数
     * @param lp       经纬度
     * @param km       范围值
     */
    public void search(String keyWord, String city, int PageSize, int PageNum, LatLonPoint lp, int km) {
        query = new Query(keyWord, "", city);
        query.setPageSize(PageSize);
        query.setPageNum(PageNum);
        PoiSearch poiSearch = new PoiSearch(mContext, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
        poiSearch = new PoiSearch(mContext, query);
        poiSearch.setBound(new PoiSearch.SearchBound(lp, km, true));//
        // 设置搜索区域为以lp点为圆心，其周围5000米范围
        poiSearch.searchPOIAsyn();// 异步搜索
    }

    /**
     * 带范围的搜索查询  带搜索结果返回值
     *
     * @param keyWord
     * @param city
     * @param PageSize
     * @param PageNum
     * @param lp
     * @param km
     * @param onLiner
     */
    public void search(String keyWord, String city, int PageSize, int PageNum, LatLonPoint lp, int km, PoiSearchLiner onLiner) {
        this.onLiner = onLiner;
        query = new Query(keyWord, "", city);
        query.setPageSize(PageSize);
        query.setPageNum(PageNum);
        PoiSearch poiSearch = new PoiSearch(mContext, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
        poiSearch = new PoiSearch(mContext, query);
        poiSearch.setBound(new PoiSearch.SearchBound(lp, km, true));//
        // 设置搜索区域为以lp点为圆心，其周围5000米范围
        poiSearch.searchPOIAsyn();// 异步搜索
    }

    /**
     * 搜索结果回调
     * @param poiResult
     * @param resultCode
     */
    @Override
    public void onPoiSearched(PoiResult poiResult, int resultCode) {
        if (resultCode == 1000) {
            if (poiResult != null && poiResult.getQuery() != null) {// 搜索poi的结果
                if (poiResult.getQuery().equals(query)) {    // 是否是同一条
                    List<PoiItem> poiItems = poiResult.getPois();
                      onLiner.onSearched(poiResult, resultCode);
                }
            } else {
                Debug.e("对不起，没有搜索到相关数据！");
            }
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    public interface PoiSearchLiner {
        /**
         * 返回一个按要求搜索到的结果
         *
         * @param poiResult
         * @param resultCode
         */
        void onSearched(PoiResult poiResult, int resultCode);
    }

    /**
     * 响应地理编码
     */
    public void getLatlon(String name, String citycode, onGeocodeSearchedListener geocode) {
        this.searchListener = geocode;
        GeocodeQuery query = new GeocodeQuery(name, citycode);// 第一个参数表示地址，第二个参数表示查询城市，中文或者中文全拼，citycode、adcode，
        geocoderSearch.getFromLocationNameAsyn(query);// 设置同步地理编码请求
    }

    /**
     * 响应逆地理编码
     */
    public void getAddress(final LatLonPoint latLonPoint, int m, onRegeocodeSearched ml) {
        this.regeocodeListener = ml;
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, m,
                GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        geocoderSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求
    }

    /**
     * 逆地理编码回调
     */
    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
        if (rCode == 1000) {
            if (result != null && result.getRegeocodeAddress() != null
                    && result.getRegeocodeAddress().getFormatAddress() != null) {
                regeocodeListener.Searchedsuccess(result, rCode);
            } else {
                regeocodeListener.Searchedfail(rCode);
            }
        } else {
            Log.e("message", "逆地编码出现错误！请检查...传入的地址是否正确！");
        }
    }

    /**
     * 地理编码查询回调
     */
    @Override
    public void onGeocodeSearched(GeocodeResult result, int rCode) {
        if (rCode == 1000) {
            if (result != null && result.getGeocodeAddressList() != null && result.getGeocodeAddressList().size() > 0) {
                searchListener.Searchedsuccess(result, rCode);
            } else {
                searchListener.Searchedfail(rCode);
            }
        } else {
            Log.e("message", "地理编码出现错误！请检查...传入的地址是否正确！");
        }
    }

    public interface onGeocodeSearchedListener {
        void Searchedsuccess(GeocodeResult result, int rCode);

        void Searchedfail(int rCode);
    }

    public interface onRegeocodeSearched {
        void Searchedsuccess(RegeocodeResult result, int rCode);

        void Searchedfail(int rCode);
    }
}
