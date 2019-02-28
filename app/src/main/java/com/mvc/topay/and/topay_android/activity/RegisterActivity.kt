package com.mvc.topay.and.topay_android.activity

import android.content.Intent
import android.view.View
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.base.BaseMVPActivity
import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.constract.IRegisterConstract
import com.mvc.topay.and.topay_android.presenter.RegisterPresenter

class RegisterActivity : BaseMVPActivity<IRegisterConstract.RegisterView, IRegisterConstract.RegisterPresenter>() {
    override fun initMVPData() {
    }

    override fun initMVPView() {
        super.initMVPView()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_register
    }

    override fun initPresenter(): BasePresenter<*, *> {
        return RegisterPresenter.newIntance()
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.register_back -> {
                finish()
            }
            R.id.register_send -> {

            }
            R.id.register_submit -> {

            }
            R.id.register_login -> {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
    }
}