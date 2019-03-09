package com.mvc.topay.and.topay_android

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.support.v4.app.Fragment
import android.widget.RadioButton
import com.blankj.utilcode.util.LogUtils
import com.mvc.topay.and.topay_android.adapter.HomePagerAdapter
import com.mvc.topay.and.topay_android.api.ApiStore
import com.mvc.topay.and.topay_android.base.BaseActivity
import com.mvc.topay.and.topay_android.event.LanguageEvent
import com.mvc.topay.and.topay_android.fragment.MineFragment
import com.mvc.topay.and.topay_android.fragment.WalletFragment
import com.mvc.topay.and.topay_android.listener.IDialogViewClickListener
import com.mvc.topay.and.topay_android.utils.AppInnerDownLoder
import com.mvc.topay.and.topay_android.utils.DialogHelper
import com.mvc.topay.and.topay_android.utils.RetrofitUtils
import com.mvc.topay.and.topay_android.utils.RxHelper
import com.per.rslibrary.IPermissionRequest
import com.per.rslibrary.RsPermission
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : BaseActivity() {
    private var isBack = false
    private val timer = Timer()
    private lateinit var mFragment: ArrayList<Fragment>
    private lateinit var pagerAdapter: HomePagerAdapter
    private lateinit var dialogHelper: DialogHelper

    @SuppressLint("CheckResult")
    override fun initData() {
        //check update
        RetrofitUtils.client(ApiStore::class.java).updateApk(MyApplication.token, "apk")
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe({ installApkBean ->
                    if (installApkBean.code === 200) {
                        val packageInfo = packageManager.getPackageInfo(packageName, 0)
                        val versionCode = packageInfo.versionCode
                        if (installApkBean.data.appVersionCode > versionCode) {
                            dialogHelper.create(this@MainActivity, "检查到新版本，是否更新？", object : IDialogViewClickListener {
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
                                                    AppInnerDownLoder.downLoadApk(this@MainActivity, installApkBean.data.httpUrl, "ToPay")
                                                }
                                            }).requestPermission(this@MainActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        RsPermission.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun initView() {
        super.initView()
        mFragment = ArrayList()
        EventBus.getDefault().register(this)
        var walletFragment = WalletFragment()
        mFragment.add(walletFragment)
        var mineFragment = MineFragment()
        mFragment.add(mineFragment)
        pagerAdapter = HomePagerAdapter(supportFragmentManager, mFragment)
        main_pager.adapter = pagerAdapter
        val childCount = main_group.childCount
        for (i in 0 until childCount) {
            main_group.getChildAt(i).setOnClickListener { main_pager.currentItem = i }
        }
        var page = intent.getIntExtra("page", -1)
        if (page !== -1) {
            main_pager.currentItem = page
            (main_group.getChildAt(page) as RadioButton).isChecked = true
        }
        dialogHelper = DialogHelper.instance
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    override fun onBackPressed() {
        if (isBack) {
            super.onBackPressed()
        } else {
            isBack = true
            timer.schedule(object : TimerTask() {
                override fun run() {
                    isBack = false
                }
            }, 1000)
            showToast(R.string.app_exit)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    @Subscribe
    fun changeLanguage(language: LanguageEvent) {
        recreate()
    }
}
