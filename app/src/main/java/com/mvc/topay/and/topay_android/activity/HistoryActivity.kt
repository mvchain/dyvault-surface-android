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
import com.mvc.topay.and.topay_android.R.id.history_swipe
import com.mvc.topay.and.topay_android.adapter.HistoryPagerAdapter
import com.mvc.topay.and.topay_android.api.ApiStore
import com.mvc.topay.and.topay_android.base.BaseActivity
import com.mvc.topay.and.topay_android.event.HistoryEvent
import com.mvc.topay.and.topay_android.fragment.HistoryFragment
import com.mvc.topay.and.topay_android.utils.RetrofitUtils
import com.mvc.topay.and.topay_android.utils.RxHelper
import com.mvc.topay.and.topay_android.utils.TextUtils
import com.per.rslibrary.IPermissionRequest
import com.per.rslibrary.RsPermission
import com.uuzuche.lib_zxing.activity.CodeUtils
import kotlinx.android.synthetic.main.activity_history.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class HistoryActivity : BaseActivity() {
    private var tokenId = 0
    private lateinit var rateType: String
    private lateinit var tokenName: String
    private lateinit var fragments: ArrayList<Fragment>
    private lateinit var historyAdapter: HistoryPagerAdapter
    override fun initData() {
        history_title.text = tokenName.trim()
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
        tokenName = intent.getStringExtra("tokenName")
        fragments = ArrayList()
        historyAdapter = HistoryPagerAdapter(supportFragmentManager, fragments)
        history_vp.adapter = historyAdapter
        history_table.setupWithViewPager(history_vp)
    }

    @Subscribe
    fun refresh(event: HistoryEvent?) {
        loadAssetsOnId(tokenId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
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
                            showToast(getString(R.string.camera_not_permission))
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
            R.id.his_out -> {
                val intent = Intent(this@HistoryActivity, TransferActivity::class.java)
                intent.putExtra("tokenId", tokenId)
                intent.putExtra("tokenName", tokenName)
                intent.putExtra("hash", "")
                startActivity(intent)
            }
            R.id.his_in -> {
                var intent = Intent(this, MineQCodeActivity::class.java)
                intent.putExtra("tokenId", tokenId)
                intent.putExtra("tokenName", tokenName)
                startActivity(intent)
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
                    }
                }, {
                    LogUtils.e(it!!.message)
                    showToast(getString(R.string.service_error))
                })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            when (resultCode) {
                200 -> {
                    val qode = data.getBooleanExtra("QODE", false)
                    if (!qode) {
                        showToast(getString(R.string.invalid_address))
                        return
                    }
                    val stringExtra = data.getStringExtra(CodeUtils.RESULT_STRING)
                    val intent = Intent(this@HistoryActivity, TransferActivity::class.java)
                    intent.putExtra("hash", stringExtra)
                    intent.putExtra("tokenId", tokenId)
                    intent.putExtra("tokenName", tokenName)
                    startActivity(intent)
                }
            }
        }
    }
}