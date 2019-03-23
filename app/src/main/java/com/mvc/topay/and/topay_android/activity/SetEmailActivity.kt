package com.mvc.topay.and.topay_android.activity

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.View
import com.blankj.utilcode.util.SPUtils
import com.mvc.topay.and.topay_android.MainActivity
import com.mvc.topay.and.topay_android.MyApplication
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.base.BaseMVPActivity
import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.common.Constant
import com.mvc.topay.and.topay_android.common.Constant.SP.TOKEN
import com.mvc.topay.and.topay_android.common.Constant.SP.USER_EMAIL
import com.mvc.topay.and.topay_android.constract.ISetEmailContract
import com.mvc.topay.and.topay_android.listener.OnTimeEndCallBack
import com.mvc.topay.and.topay_android.presenter.SetEmailPresenter
import com.mvc.topay.and.topay_android.utils.TimeVerification
import kotlinx.android.synthetic.main.activity_set_email.*

class SetEmailActivity : BaseMVPActivity<ISetEmailContract.SetEmailView, ISetEmailContract.SetEmailPresenter>(), ISetEmailContract.SetEmailView {
    private lateinit var sToken: String

    override fun initMVPData() {

    }

    override fun initMVPView() {
        super.initMVPView()
        sToken = intent.getStringExtra("token")
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_set_email
    }

    override fun initPresenter(): BasePresenter<*, *> {
        return SetEmailPresenter.newIntance()
    }

    override fun sendEmailSuccess(msg: String) {
        dismiss()
        showToast(msg)
        TimeVerification.instance.resume()
        TimeVerification.instance.setOnTimeEndCallBack(object : OnTimeEndCallBack {
            override fun updata(time: Int) {
                email_send.isEnabled = false
                email_send.setTextColor(ContextCompat.getColor(baseContext, R.color.edit_bg))
                email_send.text = time.toString() + "s"
            }

            override fun exit() {
                email_send.isEnabled = true
                email_send.setTextColor(ContextCompat.getColor(baseContext, R.color.register_button_bg))
                email_send.text = getString(R.string.get_code)
            }
        }).updataTime()
    }

    override fun sendEmailFailed(msg: String) {
        dismiss()
        showToast(msg)

    }

    override fun verifyEmailSuccess(token: String) {
        dismiss()
        showToast(getString(R.string.mailbox_was_successfully))
        SPUtils.getInstance().put(TOKEN, token)
        SPUtils.getInstance().put(USER_EMAIL, email_address.text.toString())
        MyApplication.token = token
//        EventBus.getDefault().post(MineUserEvent())
        var mainIntent = Intent(this, MainActivity::class.java)
        mainIntent.putExtra("page", 1)
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(mainIntent)
    }

    override fun verifyEmailFailed(msg: String) {
        dismiss()
        showToast(msg)

    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.email_back -> {
                finish()
            }
            R.id.email_send -> {
                var emailAddress = email_address.text.toString()
                if (emailAddress === "") {
                    showToast(MyApplication.application!!.getString(R.string.login_null_email))
                    return
                }
                showDialog(getString(R.string.send_code_load))
                mPresenter.sendEmail(emailAddress)
            }
            R.id.email_submit -> {
                var emailAddress = email_address.text.toString()
                var emailCode = email_code.text.toString()
                if (emailAddress === "") {
                    showToast(MyApplication.application!!.getString(R.string.login_null_email))
                    return
                }
                if (emailCode === "") {
                    showToast(getString(R.string.login_null_code))
                    return
                }
                showDialog(getString(R.string.modifying_password))
                mPresenter.verifyEmail(emailAddress, sToken, emailCode)
            }
        }
    }
}