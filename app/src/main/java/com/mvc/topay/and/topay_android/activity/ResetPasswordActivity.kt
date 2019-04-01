package com.mvc.topay.and.topay_android.activity

import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.blankj.utilcode.util.SPUtils
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.base.BaseMVPActivity
import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.common.Constant.SP.RESETPASSWORD_LOGIN
import com.mvc.topay.and.topay_android.common.Constant.SP.TEMP_EMAIL
import com.mvc.topay.and.topay_android.common.Constant.SP.TEMP_TOKEN
import com.mvc.topay.and.topay_android.common.Constant.SP.USER_RESETPASSWORD_TYPE
import com.mvc.topay.and.topay_android.constract.IResetPasswordContract
import com.mvc.topay.and.topay_android.presenter.ResetPasswordPresenter
import kotlinx.android.synthetic.main.activity_reset_password.*

class ResetPasswordActivity : BaseMVPActivity<IResetPasswordContract.ResetPasswordView, IResetPasswordContract.ResetPasswordPresenter>(), IResetPasswordContract.ResetPasswordView {
    private var type = SPUtils.getInstance().getInt(USER_RESETPASSWORD_TYPE)
    private lateinit var mToken: String
    private lateinit var mEmail: String
    override fun resetSuccess(msg: String) {
        dismiss()
        showToast(msg)
        if (type == RESETPASSWORD_LOGIN) {
            startTaskActivity(this)
        } else {
            setResult(1001)
            finish()
        }
    }

    override fun resetFailed(msg: String) {
        dismiss()
        showToast(msg)
    }

    override fun initMVPData() {

    }

    override fun initMVPView() {
        super.initMVPView()
        this.mToken = intent.getStringExtra(TEMP_TOKEN)
        this.mEmail = intent.getStringExtra(TEMP_EMAIL)
        if (type === RESETPASSWORD_LOGIN) {
            reset_title.text = getString(R.string.account_update_password)
            reset_email.hint = getString(R.string.account_new_password)
            reset_email.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
        } else {
            reset_title.text = getString(R.string.account_update_pay_password)
            reset_email.inputType = InputType.TYPE_NUMBER_VARIATION_PASSWORD or InputType.TYPE_CLASS_NUMBER
            reset_email.hint = getString(R.string.account_new_pay_password)
        }
        reset_pwd_show.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                reset_email.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                reset_email.transformationMethod = PasswordTransformationMethod.getInstance()
            }
            reset_email.setSelection(reset_email.text.length)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_reset_password
    }

    override fun initPresenter(): BasePresenter<*, *> {
        return ResetPasswordPresenter.newInstance()
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.reset_back -> {
                finish()
            }
            R.id.reset_next -> {
                showDialog(getString(R.string.modifying_password))
                mPresenter.resetPassword(mEmail, reset_email.text.toString(), mToken, type)
            }
        }
    }
}