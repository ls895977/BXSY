package com.maoyongxin.myapplication.http.manager;

import com.jky.baselibrary.http.BaseHttpMgr;
import com.maoyongxin.myapplication.http.xHttpMgr;


/**
 * Created by dingke on 2017/8/3.
 */

public class Managers {
    public static BaseHttpMgr getHttpMgr() {
        return xHttpMgr.getMgr();
    }
}
