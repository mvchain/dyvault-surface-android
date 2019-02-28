package com.mvc.topay.and.topay_android.activity

import android.content.Intent
import android.view.View
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.base.BaseActivity

class SelectLoginActivity : BaseActivity() {
    override fun initData() {
    }

    override fun initView() {
        super.initView()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_select_login
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.login_submit -> {
                startActivity(Intent(this, LoginActivity::class.java))
            }
            R.id.register_submit -> {
                startActivity(Intent(this, RegisterActivity::class.java))
            }
        }
    }
}