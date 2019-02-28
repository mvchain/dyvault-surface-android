package com.mvc.topay.and.topay_android;

import android.app.Application;
import android.content.Context;
import android.view.Gravity;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.mvc.topay.and.topay_android.common.Constant;

public class MyApplication extends Application {
    private static Context mContext;
    private static MyApplication myApplication;
    private static String TOKEN;

    public static Context getAppContext() {
        return mContext;
    }

    public static Context getApplication() {
        return myApplication;
    }

    public static String getTOKEN() {
        return TOKEN == null ? SPUtils.getInstance().getString(Constant.SP.TOKEN) : TOKEN;
    }

    public static void setTOKEN(String TOKEN) {
        MyApplication.TOKEN = TOKEN;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        mContext = getApplicationContext();
        Utils.init(this);
//        JPushInterface.setDebugMode(true);
//        JPushInterface.init(this);
//        MultiDex.install(this);
//        if (SPUtils.getInstance().getString(DEFAULT_ENGLUSH).equals("")) {
//            Locale locale = new Locale(ENGLISH);
//            Locale.setDefault(locale);
//            Configuration config = getResources().getConfiguration();
//            DisplayMetrics metrics = getResources().getDisplayMetrics();
//            config.locale = Locale.SIMPLIFIED_CHINESE;
//            getResources().updateConfiguration(config, metrics);
//            SPUtils.getInstance().put(DEFAULT_LANGUAGE, ENGLISH);
//            SPUtils.getInstance().put(DEFAULT_ACCEPT_LANGUAGE, ACCEPT_ENGLISH);
//        }
    }
}
