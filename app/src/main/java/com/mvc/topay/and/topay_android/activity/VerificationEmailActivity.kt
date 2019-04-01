package com.mvc.topay.and.topay_android.activity

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.View
import com.blankj.utilcode.util.SPUtils
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.base.BaseMVPActivity
import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.bean.HttpDataBean
import com.mvc.topay.and.topay_android.common.Constant.SP.TEMP_EMAIL
import com.mvc.topay.and.topay_android.common.Constant.SP.TEMP_TOKEN
import com.mvc.topay.and.topay_android.common.Constant.SP.USER_EMAIL
import com.mvc.topay.and.topay_android.constract.IVerificationContract
import com.mvc.topay.and.topay_android.listener.OnTimeEndCallBack
import com.mvc.topay.and.topay_android.presenter.VerificationPresenter
import com.mvc.topay.and.topay_android.utils.TimeVerification
import kotlinx.android.synthetic.main.activity_verification_email.*

class VerificationEmailActivity : BaseMVPActivity<IVerificationContract.VerificationView, IVerificationContract.VerificationPresenter>(), IVerificationContract.VerificationView {
    override fun error(msg: String) {
        dismiss()
        showToast(msg)
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
        showToast(msg)
        TimeVerification.instance.resume()
        TimeVerification.instance.setOnTimeEndCallBack(object : OnTimeEndCallBack {
            override fun updata(time: Int) {
                verification_send.isEnabled = false
                verification_send.setTextColor(ContextCompat.getColor(baseContext, R.color.edit_bg))
                verification_send.text = time.toString() + "s"
            }

            override fun exit() {
                verification_send.isEnabled = true
                verification_send.setTextColor(ContextCompat.getColor(baseContext, R.color.register_button_bg))
                verification_send.text = getString(R.string.get_code)
            }
        }).updataTime()
    }

    override fun resetPassword(httpDataBean: HttpDataBean) {
        dismiss()
        var email = verification_email.text.toString()
        var resetIntent = Intent(this, ResetPasswordActivity::class.java)
        resetIntent.putExtra(TEMP_EMAIL, email)
        resetIntent.putExtra(TEMP_TOKEN, httpDataBean.data)
        startActivityForResult(resetIntent,1001)
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.verification_back -> {
                finish()
            }
            R.id.verification_send -> {
                var email = verification_email.text.toString()
                showDialog(getString(R.string.send_code_load))
                mPresenter.sendCode(email)
            }
            R.id.verification_next -> {
                var email = verification_email.text.toString()
                var code = verification_code.text.toString()
                showDialog(getString(R.string.in_verification))
                mPresenter.resetPassword(email, code)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode === 1001){
            finish()
        }
    }
}