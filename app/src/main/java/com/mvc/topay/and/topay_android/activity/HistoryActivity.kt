package com.mvc.topay.and.topay_android.activity

import android.view.View
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.base.BaseActivity

class HistoryActivity : BaseActivity() {
    override fun initData() {
    }

    override fun initView() {
        super.initView()
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

            }
        }
    }
}