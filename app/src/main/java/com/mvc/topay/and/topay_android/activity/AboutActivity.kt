package com.mvc.topay.and.topay_android.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.gyf.barlibrary.ImmersionBar
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.base.BaseActivity


class AboutActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_about
    }

    private var mVersionAbout: TextView? = null
    private var mBackAbout: ImageView? = null

    override fun initData() {
        mVersionAbout!!.text = getVersionName(this)
    }

    override fun initView() {
        super.initView()
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init()
        mVersionAbout = findViewById(R.id.about_version)
        mBackAbout = findViewById(R.id.about_back)
        mBackAbout!!.setOnClickListener { v -> finish() }
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
        showToast("当前版本已是最新", Gravity.CENTER)
//        RetrofitUtils.client(ApiStore::class.java).updateApk(MyApplication.getTOKEN(), "apk")
//                .compose(RxHelper.rxSchedulerHelper())
//                .subscribe({ installApkBean ->
//                    if (installApkBean.getCode() === 200) {
//                        val packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0)
//                        val versionCode = packageInfo.versionCode
//                        if (installApkBean.getData().getAppVersionCode() > versionCode) {
//                            dialogHelper.create(this@AboutActivity, "检查到新版本，是否更新？", { viewId ->
//                                when (viewId) {
//                                    R.id.hint_enter -> {
//                                        dialogHelper.dismiss()
//                                        RsPermission.getInstance().setiPermissionRequest(object : IPermissionRequest {
//                                            override fun toSetting() {
//
//                                            }
//
//                                            override fun cancle(i: Int) {
//
//                                            }
//
//                                            override fun success(i: Int) {
//                                                AppInnerDownLoder.downLoadApk(this@AboutActivity, installApkBean.getData().getHttpUrl(), "BZT")
//                                            }
//                                        }).requestPermission(this@AboutActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                                    }
//                                    R.id.hint_cancle -> dialogHelper.dismiss()
//                                }
//                            }).show()
//                        } else {
//                            ToastUtils.showShort("当前版本已是最新")
//                        }
//                    } else {
//                        LogUtils.e(installApkBean.getMessage())
//                    }
//                }, { throwable -> LogUtils.e(throwable.getMessage()) })
    }
}
