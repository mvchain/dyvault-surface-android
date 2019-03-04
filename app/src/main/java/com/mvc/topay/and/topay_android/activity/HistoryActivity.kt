package com.mvc.topay.and.topay_android.activity

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.blankj.utilcode.util.ToastUtils
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.adapter.HistoryPagerAdapter
import com.mvc.topay.and.topay_android.base.BaseActivity
import com.mvc.topay.and.topay_android.fragment.HistoryFragment
import com.per.rslibrary.IPermissionRequest
import com.per.rslibrary.RsPermission
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : BaseActivity() {
    private var tokenId = 0
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
    }

    override fun initView() {
        super.initView()
        tokenId = intent.getIntExtra("tokenId", 0)
        fragments = ArrayList()
        historyAdapter = HistoryPagerAdapter(supportFragmentManager, fragments)
        history_vp.adapter = historyAdapter
        history_table.setupWithViewPager(history_vp)
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
}