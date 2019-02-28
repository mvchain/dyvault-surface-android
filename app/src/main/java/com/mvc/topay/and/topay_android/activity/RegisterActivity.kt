package com.mvc.topay.and.topay_android.activity

import android.content.Intent
import android.view.Gravity
import android.view.View
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.base.BaseMVPActivity
import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.bean.HttpDataBean
import com.mvc.topay.and.topay_android.constract.IRegisterContract
import com.mvc.topay.and.topay_android.presenter.RegisterPresenter
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseMVPActivity<IRegisterContract.RegisterView, IRegisterContract.RegisterPresenter>(), IRegisterContract.RegisterView {

    override fun verifyFailed(msg: String) {
        showToast(msg, Gravity.CENTER)
        dismiss()
    }

    override fun verifySuccess(httpDataBean: HttpDataBean) {
        var token = httpDataBean.data
        var setPasswordIntent = Intent(this, SetPasswordActivity::class.java)
        setPasswordIntent.putExtra("email", register_email.text.toString())
        setPasswordIntent.putExtra("inviteCode", "")
        setPasswordIntent.putExtra("nickname", register_name.text.toString())
        setPasswordIntent.putExtra("token", token)
        startActivity(setPasswordIntent)
        dismiss()
    }

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
                mPresenter.sendCode(register_email.text.toString())
            }
            R.id.register_submit -> {
                var email = register_email.text.toString()
                var name = register_name.text.toString()
                showDialog("请稍后...")
                mPresenter.verifyUser(name, email, "", "null")
            }
            R.id.register_login -> {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
    }
}