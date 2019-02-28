package com.mvc.topay.and.topay_android.activity

import android.content.Intent
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.Gravity
import android.view.View
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.base.BaseMVPActivity
import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.constract.ILoginContract
import com.mvc.topay.and.topay_android.presenter.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseMVPActivity<ILoginContract.LoginView, ILoginContract.LoginPresenter>() {
    override fun initMVPData() {
    }

    override fun initMVPView() {
        super.initMVPView()
        login_pwd_show.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                login_password.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                login_password.transformationMethod = PasswordTransformationMethod.getInstance()
            }
            login_password.setSelection(login_password.text.length)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun initPresenter(): BasePresenter<*, *> {
        return LoginPresenter.newIntance()
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.login_back -> {
                finish()
            }
            R.id.login_send -> {

            }
            R.id.login_submit -> {
                showToast(R.string.login_null_email, Gravity.CENTER)
            }
            R.id.login_forget_password -> {

            }
            R.id.login_register -> {
                startActivity(Intent(this, RegisterActivity::class.java))
            }
        }
    }
}