package com.maoyongxin.myapplication.http.entity;

import com.amap.api.location.AMapLocation;

/**
 * Created by maoyongxin on 2017/11/29.
 */

public class LocationInfo {
    private String address="成都";
    private String latitude="30.778693";
    private String lngtitude="103.860635";
    private String cityName="成都";
    private String adCode="510117";
    private String cityCode="5101";

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    private AMapLocation aMapLocation;

    public AMapLocation getaMapLocation() {
        return aMapLocation;
    }

    public void setaMapLocation(AMapLocation aMapLocation) {
        this.aMapLocation = aMapLocation;
    }

    public String getAdCode() {
        String adCodes="";
        try{
            if (adCode!=null)
            {
                return adCode;
            }
            else
            {
                adCodes="510117";
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return adCodes;
        }
        return adCodes;
    }

    public void setAdCode(String adCode) {
        this.adCode = adCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongtitude() {
        return lngtitude;
    }

    public void setLngtitude(String lngtitude) {
        this.lngtitude = lngtitude;
    }
}
