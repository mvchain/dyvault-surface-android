package com.mvc.topay.and.topay_android.activity

import android.content.Intent
import android.text.InputType
import android.view.Gravity
import android.view.View
import com.blankj.utilcode.util.SPUtils
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.base.BaseMVPActivity
import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.common.Constant
import com.mvc.topay.and.topay_android.common.Constant.SP.RESETPASSWORD_LOGIN
import com.mvc.topay.and.topay_android.common.Constant.SP.RESETPASSWORD_PAY
import com.mvc.topay.and.topay_android.common.Constant.SP.USER_RESETPASSWORD_TYPE
import com.mvc.topay.and.topay_android.constract.IChangePasswordContract
import com.mvc.topay.and.topay_android.presenter.ChangePasswordPresenter
import kotlinx.android.synthetic.main.activity_update_password.*
import android.text.InputFilter


class ChangePasswordActivity : BaseMVPActivity<IChangePasswordContract.ChangePasswordView, IChangePasswordContract.ChangePasswordPresenter>(), IChangePasswordContract.ChangePasswordView {
    private var TYPE = 0

    override fun updateSuccess(msg: String, type: Int) {
        showToast(msg)
        if (type === RESETPASSWORD_LOGIN) {
            startTaskActivity(this)
        } else {
            finish()
        }
    }

    override fun updateFailed(msg: String, type: Int) {
        showToast(msg)
    }

    override fun initMVPData() {

    }

    override fun initMVPView() {
        super.initMVPView()
        TYPE = intent.getIntExtra("type", -1)
        if (TYPE === RESETPASSWORD_PAY) {
            update_title.text = "修改支付密码"
            update_old_password.hint = "原支付密码"
            update_new_password.hint = "新支付密码"
            update_old_password.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(6)) //最大输入长度
            update_new_password.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(6)) //最大输入长度
            update_old_password.inputType = InputType.TYPE_NUMBER_VARIATION_PASSWORD or InputType.TYPE_CLASS_NUMBER
            update_new_password.inputType = InputType.TYPE_NUMBER_VARIATION_PASSWORD or InputType.TYPE_CLASS_NUMBER
        } else {
            update_title.text = "修改登录密码"
            update_old_password.hint = "原登录密码"
            update_new_password.hint = "新登录密码"
            update_old_password.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
            update_new_password.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_update_password
    }

    override fun initPresenter(): BasePresenter<*, *> {
        return ChangePasswordPresenter.newInstance()
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.update_back -> {
                finish()
            }
            R.id.forget_password -> {
                SPUtils.getInstance().put(USER_RESETPASSWORD_TYPE, TYPE)
                startActivity(Intent(this, VerificationEmailActivity::class.java))
            }
            R.id.update_submit -> {
                var oldPsw = update_old_password.text.toString()
                var newPsw = update_new_password.text.toString()
                if (oldPsw === "") {
                    showToast("原密码不可为空")
                    return
                }
                if (newPsw === "") {
                    showToast("新密码不可为空")
                    return
                }
                if (TYPE === RESETPASSWORD_LOGIN) {
                    mPresenter.updateLoginPasssword(newPsw, oldPsw)
                } else {
                    mPresenter.updatePaypassword(newPsw, oldPsw)
                }
            }
        }
    }
}