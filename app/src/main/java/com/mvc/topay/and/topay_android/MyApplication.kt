package com.mvc.topay.and.topay_android

import android.app.Application
import android.content.Context
import android.view.Gravity

import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.Utils
import com.mvc.topay.and.topay_android.common.Constant

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        myApplication = this
        appContext = applicationContext
        Utils.init(this)
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

    companion object {
        lateinit var appContext: Context
            private set
        private var myApplication: MyApplication? = null
        var token: String = ""
            get() = if (field == null) SPUtils.getInstance().getString(Constant.SP.TOKEN) else field

        val application: Context?
            get() = myApplication
        fun getAppVersionCode() : Long{
            val packageInfo = application!!.packageManager.getPackageInfo(application!!.packageName, 0)
            return packageInfo.longVersionCode
        }
    }
}
