package com.maoyongxin.myapplication.tool;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.util.Base64;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧）Github地址：https://github.com/jeasonlzy0216
 * 版    本：1.0
 * 创建日期：2016/5/8
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class UtilsTool {

    /**
     * 解密Byte64
     */
    public static String jieMiByte64(String neirong) {
        return new String(Base64.decode(neirong.getBytes(), Base64.DEFAULT));
    }

    /**
     * 解密Byte64
     */
    public static String addByte64(String neirong) {
        return Base64.encodeToString(neirong.getBytes(), Base64.DEFAULT);
    }

    /**
     * 生成漂亮的颜色
     */
    public static int generateBeautifulColor() {
        Random random = new Random();
        //为了让生成的颜色不至于太黑或者太白，所以对3个颜色的值进行限定
        int red = random.nextInt(150) + 50;//50-200
        int green = random.nextInt(150) + 50;//50-200
        int blue = random.nextInt(150) + 50;//50-200
        return Color.rgb(red, green, blue);//使用r,g,b混合生成一种新的颜色
    }

    /**
     * 获得状态栏的高度
     */
    public static int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 判断 用户是否安装微信客户端
     */
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    private static ArrayList<Marker> markers = new ArrayList<Marker>();

    /**
     * 添加marker
     *
     * @param amap   需要添加的marker的地图
     * @param center //需要添加到marker上的坐标点
     * @param icon   //坐标点上的marker样式
     */
    public static void addEmulateData(AMap amap, List<LatLng> center, int icon) {
        if (markers.size() == 0) {
            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(icon);
            for (int i = 0; i < center.size(); i++) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.setFlat(true);
//                markerOptions.anchor(0.5f, 0.5f);
                markerOptions.icon(bitmapDescriptor);
                markerOptions.position(new LatLng(center.get(i).latitude, center.get(i).longitude));
                Marker marker = amap.addMarker(markerOptions);
                markers.add(marker);
            }
        } else {
            for (int i = 0; i < center.size(); i++) {
                if (markers.size() - 1 < i) {
                    Marker marker = markers.get(i);
                    marker.setPosition(center.get(i));
                }
            }
        }

    }
//    private static boolean isBase64(String str) {
//        if (str == null || str.length() == 0) {
//            return false;
//        } else {
//            if (str.length() % 4 != 0) {
//                return false;
//            }
//
//            char[] strChars = str.toCharArray();
//            for (char c:strChars) {
//                if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9')
//                        || c == '+' || c == '/' || c == '=') {
//                    continue;
//                } else {
//                    return false;
//                }
//            }
//            return true;
//        }
//    }

}