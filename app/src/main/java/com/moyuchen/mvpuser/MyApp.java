package com.moyuchen.mvpuser;

import android.app.Application;

import org.xutils.x;

/**
 * User: 张亚博
 * Date: 2017-09-27 15:05
 * Description：
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.setDebug(true);
        x.Ext.init(this);
//        CrashHandler.getInstance().init(this);
    }
}
