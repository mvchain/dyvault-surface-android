package com.mvc.topay.and.topay_android.activity

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.View
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.base.BaseMVPActivity
import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.bean.HttpDataBean
import com.mvc.topay.and.topay_android.common.Constant.SP.TEMP_EMAIL
import com.mvc.topay.and.topay_android.common.Constant.SP.TEMP_TOKEN
import com.mvc.topay.and.topay_android.constract.IVerificationContract
import com.mvc.topay.and.topay_android.listener.OnTimeEndCallBack
import com.mvc.topay.and.topay_android.presenter.VerificationPresenter
import com.mvc.topay.and.topay_android.utils.TimeVerification
import kotlinx.android.synthetic.main.activity_verification_email.*

class VerificationEmailActivity : BaseMVPActivity<IVerificationContract.VerificationView, IVerificationContract.VerificationPresenter>(), IVerificationContract.VerificationView {
    override fun error(msg: String) {
        dismiss()
        showToast(msg, Gravity.CENTER)
    }

    override fun initMVPData() {

    }

    override fun initMVPView() {
        super.initMVPView()

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_verification_email
    }

    override fun initPresenter(): BasePresenter<*, *> {
        return VerificationPresenter.newIntance()
    }

    override fun sendCodeSuccess(msg: String) {
        dismiss()
        showToast(msg, Gravity.CENTER)
        TimeVerification.instance.resume()
        TimeVerification.instance.setOnTimeEndCallBack(object : OnTimeEndCallBack {
            override fun updata(time: Int) {
                verification_send.isEnabled = false
                verification_send.setBackgroundResource(R.drawable.shape_login_get_code_uncheck_18dp)
                verification_send.setTextColor(ContextCompat.getColor(baseContext, R.color.edit_bg))
                verification_send.text = time.toString() + "s"
            }

            override fun exit() {
                verification_send.isEnabled = true
                verification_send.setBackgroundResource(R.drawable.shape_login_get_code_18dp)
                verification_send.setTextColor(ContextCompat.getColor(baseContext, R.color.content_tv_bg))
                verification_send.text = "获取验证码"
            }
        }).updataTime()
    }

    override fun resetPassword(httpDataBean: HttpDataBean) {
        var email = verification_email.text.toString()
        var resetIntent = Intent(this, ResetPasswordActivity::class.java)
        resetIntent.putExtra(TEMP_EMAIL, email)
        resetIntent.putExtra(TEMP_TOKEN, httpDataBean.data)
        startActivity(resetIntent)
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.verification_back -> {
                finish()
            }
            R.id.verification_send -> {
                var email = verification_email.text.toString()
                showDialog("发送验证码中")
                mPresenter.sendCode(email)
            }
            R.id.verification_next -> {
                var email = verification_email.text.toString()
                var code = verification_code.text.toString()
                showDialog("验证中...")
                mPresenter.resetPassword(email, code)
            }
        }
    }
}