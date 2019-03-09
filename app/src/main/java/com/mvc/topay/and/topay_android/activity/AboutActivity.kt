package com.mvc.topay.and.topay_android.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.blankj.utilcode.util.LogUtils

import com.gyf.barlibrary.ImmersionBar
import com.mvc.topay.and.topay_android.MyApplication
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.api.ApiStore
import com.mvc.topay.and.topay_android.base.BaseActivity
import com.mvc.topay.and.topay_android.listener.IDialogViewClickListener
import com.mvc.topay.and.topay_android.utils.AppInnerDownLoder
import com.mvc.topay.and.topay_android.utils.DialogHelper
import com.mvc.topay.and.topay_android.utils.RetrofitUtils
import com.mvc.topay.and.topay_android.utils.RxHelper
import com.per.rslibrary.IPermissionRequest
import com.per.rslibrary.RsPermission


class AboutActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_about
    }

    private var mVersionAbout: TextView? = null
    private var mBackAbout: ImageView? = null
    private lateinit var dialogHelper: DialogHelper

    override fun initData() {
        mVersionAbout!!.text = getVersionName(this)
    }

    override fun initView() {
        super.initView()
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init()
        mVersionAbout = findViewById(R.id.about_version)
        mBackAbout = findViewById(R.id.about_back)
        mBackAbout!!.setOnClickListener { v -> finish() }
        dialogHelper = DialogHelper.instance
    }

    /**
     * get App versionName
     *
     * @param context
     * @return
     */
    private fun getVersionName(context: Context): String {
        val packageManager = context.packageManager
        val packageInfo: PackageInfo
        var versionName = ""
        try {
            packageInfo = packageManager.getPackageInfo(context.packageName, 0)
            versionName = packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return "Topay: $versionName"
    }

    @SuppressLint("CheckResult")
    fun onClick(view: View) {
        //check update
        RetrofitUtils.client(ApiStore::class.java).updateApk(MyApplication.token, "apk")
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe({ installApkBean ->
                    if (installApkBean.code === 200) {
                        val packageInfo = packageManager.getPackageInfo(packageName, 0)
                        val versionCode = packageInfo.versionCode
                        if (installApkBean.data.appVersionCode > versionCode) {
                            dialogHelper.create(this@AboutActivity, getString(R.string.check_app_update_hint), object : IDialogViewClickListener {
                                override fun click(viewId: Int) {
                                    when (viewId) {
                                        R.id.hint_enter -> {
                                            dialogHelper.dismiss()
                                            RsPermission.getInstance().setiPermissionRequest(object : IPermissionRequest {
                                                override fun toSetting() {

                                                }

                                                override fun cancle(i: Int) {

                                                }

                                                override fun success(i: Int) {
                                                    AppInnerDownLoder.downLoadApk(this@AboutActivity, installApkBean.data.httpUrl, "ToPay")
                                                }
                                            }).requestPermission(this@AboutActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                        }
                                        R.id.hint_cancle -> dialogHelper.dismiss()
                                    }
                                }
                            }).show()
                        }
                    } else {
                        LogUtils.e(installApkBean.message)
                    }
                }, { throwable -> LogUtils.e(throwable.message) })
    }
}
