package com.mvc.topay.and.topay_android.presenter

import android.view.Gravity
import com.blankj.utilcode.util.EncryptUtils
import com.mvc.topay.and.topay_android.MyApplication
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.constract.ISetPasswordContract
import com.mvc.topay.and.topay_android.model.SetPasswordModel

class SetPasswordPresenter : ISetPasswordContract.SetPasswordPresenter() {
    override fun setPassword(email: String, inviteCode: String, nickname: String, password: String, token: String, transactionPassword: String) {
        if (password.isEmpty()) {
            mIView!!.registerFailed(MyApplication.appContext.getString(R.string.login_null_password))
            return
        }
        if (transactionPassword.isEmpty()) {
            mIView!!.registerFailed(MyApplication.appContext.getString(R.string.login_null_pay_password))
            return
        }
        var md5Password = EncryptUtils.encryptMD5ToString(email + EncryptUtils.encryptMD5ToString(password))
        var md5PayPassword = EncryptUtils.encryptMD5ToString(email + EncryptUtils.encryptMD5ToString(transactionPassword))
        mRxUtils.register(mIModel!!.setPassword(email, inviteCode, nickname, md5Password, token, md5PayPassword)
                .subscribe({ registerBean ->
                    if (registerBean.code === 200) {
                        mIView!!.registerSuccess(registerBean)
                    } else {
                        mIView!!.registerFailed(registerBean.message)
                    }
                }, {
                    mIView!!.registerFailed(it.message!!)
                }))
    }

    override fun getModel(): ISetPasswordContract.SetPasswordModel {
        return SetPasswordModel.instance
    }

    override fun onStart() {

    }

    companion object {
        fun newIntance(): BasePresenter<*, *> {
            return SetPasswordPresenter()
        }
    }
}