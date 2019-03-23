package com.mvc.topay.and.topay_android.activity

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.SpanUtils
import com.mvc.topay.and.topay_android.MainActivity
import com.mvc.topay.and.topay_android.MyApplication
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.base.BaseMVPActivity
import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.bean.RegisterBean
import com.mvc.topay.and.topay_android.common.Constant.SP.REFRESH_TOKEN
import com.mvc.topay.and.topay_android.common.Constant.SP.RESETPASSWORD_LOGIN
import com.mvc.topay.and.topay_android.common.Constant.SP.TOKEN
import com.mvc.topay.and.topay_android.common.Constant.SP.USER_EMAIL
import com.mvc.topay.and.topay_android.common.Constant.SP.USER_ID
import com.mvc.topay.and.topay_android.common.Constant.SP.USER_RESETPASSWORD_TYPE
import com.mvc.topay.and.topay_android.common.Constant.SP.USER_SALT
import com.mvc.topay.and.topay_android.constract.ILoginContract
import com.mvc.topay.and.topay_android.listener.OnTimeEndCallBack
import com.mvc.topay.and.topay_android.presenter.LoginPresenter
import com.mvc.topay.and.topay_android.utils.TimeVerification
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseMVPActivity<ILoginContract.LoginView, ILoginContract.LoginPresenter>(), ILoginContract.LoginView {
    override fun sendCodeSuccess(msg: String) {
        dismiss()
        showToast(msg)
        TimeVerification.instance.resume()
        TimeVerification.instance.setOnTimeEndCallBack(object : OnTimeEndCallBack {
            override fun updata(time: Int) {
                login_send.isEnabled = false
                login_send.setTextColor(ContextCompat.getColor(baseContext, R.color.edit_bg))
                login_send.text = time.toString() + "s"
            }

            override fun exit() {
                login_send.isEnabled = true
                login_send.setTextColor(ContextCompat.getColor(baseContext, R.color.register_button_bg))
                login_send.text = getString(R.string.get_code)
            }
        }).updataTime()
    }

    override fun sendCodeError(msg: String) {
        dismiss()
        showToast(msg)
    }

    override fun loginSuccess(registerBean: RegisterBean) {
        dismiss()
        var dataBean = registerBean.data
        MyApplication.token = dataBean.token
        SPUtils.getInstance().put(TOKEN, dataBean.token)
        SPUtils.getInstance().put(REFRESH_TOKEN, dataBean.refreshToken)
        SPUtils.getInstance().put(USER_ID, dataBean.userId)
        SPUtils.getInstance().put(USER_EMAIL, dataBean.email)
        SPUtils.getInstance().put(USER_SALT, dataBean.salt)
        var mainIntent = Intent(this, MainActivity::class.java)
        mainIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(mainIntent)
    }

    override fun loginFailed(msg: String) {
        dismiss()
        showToast(msg)
    }

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
        login_register.text = SpanUtils().append(getString(R.string.no_account)).append(getString(R.string.register_content)).setForegroundColor(ContextCompat.getColor(this, R.color.register_button_bg)).create()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun initPresenter(): BasePresenter<*, *> {
        return LoginPresenter.newInstance()
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.login_back -> {
                finish()
            }
            R.id.login_send -> {
                showDialog(getString(R.string.send_code_load))
                mPresenter.sendCode(login_email.text.toString())
            }
            R.id.login_submit -> {
                showDialog(getString(R.string.logging_in))
                var userName = login_email.text.toString()
                var login_password = login_password.text.toString()
                var login_code = login_code.text.toString()
                mPresenter.login("", login_password, userName, login_code)
            }
            R.id.login_forget_password -> {
                SPUtils.getInstance().put(USER_RESETPASSWORD_TYPE, RESETPASSWORD_LOGIN)
                startActivity(Intent(this, VerificationEmailActivity::class.java))
            }
            R.id.login_register -> {
                startActivity(Intent(this, RegisterActivity::class.java))
            }
        }
    }
}