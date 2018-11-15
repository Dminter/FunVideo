package com.zncm.dminter.funvideo;

import android.app.Application;
import android.content.Context;

import java.util.HashSet;
import java.util.Set;


public class MyApp extends Application {

    public static MyApp instance;
    public Context ctx;
    /**
     * 下载请求，防止重复下载
     */
    public Set<String> setReq;

    public static MyApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ctx = this;
        setReq = new HashSet<>();
    }

}
