package com.mvc.topay.and.topay_android.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SpanUtils
import com.blankj.utilcode.util.ToastUtils
import com.mvc.topay.and.topay_android.MyApplication
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.adapter.HistoryPagerAdapter
import com.mvc.topay.and.topay_android.api.ApiStore
import com.mvc.topay.and.topay_android.base.BaseActivity
import com.mvc.topay.and.topay_android.fragment.HistoryFragment
import com.mvc.topay.and.topay_android.utils.RetrofitUtils
import com.mvc.topay.and.topay_android.utils.RxHelper
import com.mvc.topay.and.topay_android.utils.TextUtils
import com.per.rslibrary.IPermissionRequest
import com.per.rslibrary.RsPermission
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : BaseActivity() {
    private var tokenId = 0
    private lateinit var rateType: String

    private lateinit var fragments: ArrayList<Fragment>
    private lateinit var historyAdapter: HistoryPagerAdapter
    override fun initData() {
        //All records
        var allFragment = HistoryFragment()
        var allBundle = Bundle()
        allBundle.putInt("type", 0)
        allBundle.putInt("tokenId", tokenId)
        allFragment.arguments = allBundle
        fragments.add(allFragment)
        // Expenditure record
        var outFragment = HistoryFragment()
        var outBundle = Bundle()
        outBundle.putInt("type", 2)
        outBundle.putInt("tokenId", tokenId)
        outFragment.arguments = outBundle
        fragments.add(outFragment)
        // Income record
        var inFragment = HistoryFragment()
        var inBundle = Bundle()
        inBundle.putInt("type", 1)
        inBundle.putInt("tokenId", tokenId)
        inFragment.arguments = inBundle
        fragments.add(inFragment)
        historyAdapter.notifyDataSetChanged()
        loadAssetsOnId(tokenId)
    }

    override fun initView() {
        super.initView()
        tokenId = intent.getIntExtra("tokenId", 0)
        rateType = intent.getStringExtra("rateType")
        fragments = ArrayList()
        historyAdapter = HistoryPagerAdapter(supportFragmentManager, fragments)
        history_vp.adapter = historyAdapter
        history_table.setupWithViewPager(history_vp)
        history_swipe.setOnRefreshListener { this.refresh() }
        history_swipe.post { history_swipe.isRefreshing = true }
    }

    private fun refresh() {
        loadAssetsOnId(tokenId)
        (fragments[history_vp.currentItem] as HistoryFragment).historyRefresh()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_history
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.history_back -> {
                finish()
            }
            R.id.history_qcode -> {
                // TODO 18/11/29
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    RsPermission.getInstance().setRequestCode(200).setiPermissionRequest(object : IPermissionRequest {
                        override fun toSetting() {
                            RsPermission.getInstance().toSettingPer()
                        }

                        override fun cancle(i: Int) {
                            ToastUtils.showLong("未给予相机权限将无法扫描二维码")
                        }

                        override fun success(i: Int) {
                            val intent = Intent(this@HistoryActivity, QCodeActivity::class.java)
                            intent.putExtra("tokenId", tokenId)
                            startActivityForResult(intent, 200)
                        }
                    }).requestPermission(this, Manifest.permission.CAMERA)
                } else {
                    intent.setClass(this@HistoryActivity, QCodeActivity::class.java)
                    intent.putExtra("tokenId", tokenId)
                    startActivityForResult(intent, 200)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n", "CheckResult")
    fun loadAssetsOnId(tokenId: Int) {
        RetrofitUtils.client(ApiStore::class.java)
                .getCurrencyBalance(MyApplication.token, tokenId)
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe({ balance ->
                    if (balance.code === 200) {
                        val dataBean = balance.data
                        wallet_balance.text = SpanUtils().append("${TextUtils.rateToPrice(dataBean.ratio * dataBean.value)} ").setFontSize(36, true).create()
                        wallet_rate.text = rateType
                        wallet_buying_coins.text = "${TextUtils.doubleToFour(dataBean.value)}  ${dataBean.tokenName}"
                        history_swipe.post { history_swipe.isRefreshing = false }
                    }
                }, {
                    history_swipe.post { history_swipe.isRefreshing = false }
                    LogUtils.e(it!!.message)
                    showToast("服务器繁忙")
                })
    }
}