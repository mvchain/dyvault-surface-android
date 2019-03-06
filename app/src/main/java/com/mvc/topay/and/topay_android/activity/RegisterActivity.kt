package com.mvc.topay.and.topay_android.activity

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.View
import com.blankj.utilcode.util.SpanUtils
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.base.BaseMVPActivity
import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.bean.HttpDataBean
import com.mvc.topay.and.topay_android.constract.IRegisterContract
import com.mvc.topay.and.topay_android.listener.OnTimeEndCallBack
import com.mvc.topay.and.topay_android.presenter.RegisterPresenter
import com.mvc.topay.and.topay_android.utils.TimeVerification
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseMVPActivity<IRegisterContract.RegisterView, IRegisterContract.RegisterPresenter>(), IRegisterContract.RegisterView {
    override fun sendCodeSuccess(msg: String) {
        dismiss()
        showToast(msg)
        TimeVerification.instance.resume()
        TimeVerification.instance.setOnTimeEndCallBack(object : OnTimeEndCallBack {
            override fun updata(time: Int) {
                register_send.isEnabled = false
                register_send.setTextColor(ContextCompat.getColor(baseContext, R.color.edit_bg))
                register_send.text = time.toString() + "s"
            }

            override fun exit() {
                register_send.isEnabled = true
                register_send.setTextColor(ContextCompat.getColor(baseContext, R.color.register_button_bg))
                register_send.text = "获取验证码"
            }
        }).updataTime()
    }

    override fun sendCodeError(msg: String) {
        showToast(msg)
        dismiss()
    }

    override fun verifyFailed(msg: String) {
        showToast(msg)
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
        register_login.text = SpanUtils().append("已有账户？").append(getString(R.string.login_content)).setForegroundColor(ContextCompat.getColor(this, R.color.register_button_bg)).create()
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
                showDialog("发送验证码中...")
                mPresenter.sendCode(register_email.text.toString())
            }
            R.id.register_submit -> {
                var email = register_email.text.toString()
                var name = register_name.text.toString()
                var code = register_code.text.toString()
                showDialog("请稍后...")
                mPresenter.verifyUser(name, email, "", code)
            }
            R.id.register_login -> {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
    }
}