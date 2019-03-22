package com.mvc.topay.and.topay_android.activity

import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.base.BaseActivity
import android.content.Intent
import android.os.Handler
import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.mvc.topay.and.topay_android.MainActivity
import com.mvc.topay.and.topay_android.common.Constant.LANGUAGE.ACCEPT_CHINESE
import com.mvc.topay.and.topay_android.common.Constant.LANGUAGE.CHINESE
import com.mvc.topay.and.topay_android.common.Constant.LANGUAGE.DEFAULT_ACCEPT_LANGUAGE
import com.mvc.topay.and.topay_android.common.Constant.LANGUAGE.DEFAULT_LANGUAGE
import com.mvc.topay.and.topay_android.common.Constant.SP.REFRESH_TOKEN
import com.mvc.topay.and.topay_android.common.Constant.SP.TOKEN
import java.util.*


class StartActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_start
    }

    override fun initView() {
        super.initView()
        val default_language = SPUtils.getInstance().getString(DEFAULT_LANGUAGE)
        val default_accept_language = SPUtils.getInstance().getString(DEFAULT_ACCEPT_LANGUAGE)
        //Set to Chinese if there is no default internationalization language  (app)
        LogUtils.e(default_language)
        if (default_language == "") {
            SPUtils.getInstance().put(DEFAULT_LANGUAGE, CHINESE)
        }
        //Set to Chinese if there is no default internationalization language (web)
        if (default_accept_language == "") {
            SPUtils.getInstance().put(DEFAULT_ACCEPT_LANGUAGE, ACCEPT_CHINESE)
        }
        val refreshToken = SPUtils.getInstance().getString(REFRESH_TOKEN)
        val token = SPUtils.getInstance().getString(TOKEN)
        Handler().postDelayed({
            if (refreshToken != "" && token != "") {
                startActivity((Intent(this,MainActivity::class.java)))
                finish()
            } else {
                startTaskActivity(this)
            }
        }, 300)
    }

    override fun initData() {

    }
}