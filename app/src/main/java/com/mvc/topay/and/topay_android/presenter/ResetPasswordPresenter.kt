package com.mvc.topay.and.topay_android.presenter

import com.blankj.utilcode.util.EncryptUtils
import com.mvc.topay.and.topay_android.MyApplication
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.common.Constant.SP.RESETPASSWORD_LOGIN
import com.mvc.topay.and.topay_android.constract.IResetPasswordContract
import com.mvc.topay.and.topay_android.model.ResetPasswordModel

class ResetPasswordPresenter : IResetPasswordContract.ResetPasswordPresenter() {
    override fun resetPassword(email: String, password: String, token: String, type: Int) {
        if (password.isEmpty()) {
            mIView!!.resetFailed(if (type === RESETPASSWORD_LOGIN) MyApplication.getAppContext().getString(R.string.login_null_password)
            else
                MyApplication.getAppContext().getString(R.string.login_null_pay_password))
            return
        }
        var md5Password = EncryptUtils.encryptMD5ToString(email + EncryptUtils.encryptMD5ToString(password))
        mRxUtils.register(mIModel!!.resetPassword(md5Password, token, type)
                .subscribe({ updateBean ->
                    if (updateBean.code === 200) {
                        mIView!!.resetSuccess(if (type === RESETPASSWORD_LOGIN) MyApplication.getAppContext().getString(R.string.reset_password_success)
                        else
                            MyApplication.getAppContext().getString(R.string.reset_pay_password_success))
                    } else {
                        mIView!!.resetFailed(updateBean.message)
                    }
                }, {
                    mIView!!.resetFailed(it.message!!)
                }))
    }

    override fun getModel(): IResetPasswordContract.ResetPasswordModel {
        return ResetPasswordModel.instance
    }

    override fun onStart() {
    }

    companion object {
        fun newIntance(): BasePresenter<*, *> {
            return ResetPasswordPresenter()
        }
    }
}