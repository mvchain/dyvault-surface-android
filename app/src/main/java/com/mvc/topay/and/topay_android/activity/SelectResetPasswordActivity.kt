package com.mvc.topay.and.topay_android.activity

import android.content.Intent
import android.view.View
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.base.BaseActivity
import com.mvc.topay.and.topay_android.common.Constant.SP.RESETPASSWORD_LOGIN
import com.mvc.topay.and.topay_android.common.Constant.SP.RESETPASSWORD_PAY

class SelectResetPasswordActivity : BaseActivity() {
    override fun initData() {
    }

    override fun initView() {
        super.initView()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_select_resetpassword
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.account_back -> {
                finish()
            }
            R.id.account_email -> {
                startActivity(Intent(this,ChangeEmailVerificationActivity::class.java))
            }
            R.id.account_password -> {
                var sIntent = Intent(this,ChangePasswordActivity::class.java)
                sIntent.putExtra("type",RESETPASSWORD_LOGIN)
                startActivity(sIntent)
            }
            R.id.account_pay_password -> {
                var sIntent = Intent(this,ChangePasswordActivity::class.java)
                sIntent.putExtra("type", RESETPASSWORD_PAY)
                startActivity(sIntent)
            }
        }
    }
}