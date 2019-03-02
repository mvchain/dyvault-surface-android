package com.mvc.topay.and.topay_android.presenter

import com.blankj.utilcode.util.LogUtils
import com.mvc.topay.and.topay_android.MyApplication
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.constract.ILoginContract
import com.mvc.topay.and.topay_android.model.LoginModel

class LoginPresenter : ILoginContract.LoginPresenter() {
    override fun sendCode(email: String) {
        if (email.isEmpty()) {
            mIView!!.sendCodeError(MyApplication.appContext.getString(R.string.login_null_email))
            return
        }
        mRxUtils.register(mIModel!!.sendCode(email)
                .subscribe({ updateBean ->
                    if (updateBean.code === 200) {
                        mIView!!.sendCodeSuccess(MyApplication.appContext.getString(R.string.send_code_success))
                    } else {
                        mIView!!.sendCodeError(updateBean.message!!)
                    }
                }, {
                    LogUtils.e(it.message!!)
                    mIView!!.sendCodeError(it.message!!)
                }))
    }

    override fun login(imageToken: String, password: String, username: String, validCode: String) {
        if (username.isEmpty()) {
            mIView!!.loginFailed(MyApplication.appContext.getString(R.string.login_null_email))
            return
        }
        if (password.isEmpty()) {
            mIView!!.loginFailed(MyApplication.appContext.getString(R.string.login_null_password))
            return
        }
        if (validCode!!.isEmpty()) {
            mIView!!.loginFailed(MyApplication.appContext.getString(R.string.login_null_code))
            return
        }
        mRxUtils.register(mIModel!!.login(imageToken, password, username, validCode)
                .subscribe({ loginBean ->
                    if (loginBean.code === 200) {
                        mIView!!.loginSuccess(loginBean)
                    } else {
                        mIView!!.loginFailed(loginBean.message)
                    }
                }, {
                    mIView!!.loginFailed(it.message!!)
                }))
    }

    override fun getModel(): ILoginContract.LoginModel {
        return LoginModel.instance
    }

    override fun onStart() {

    }

    companion object {
        fun newIntance(): BasePresenter<*, *> {
            return LoginPresenter()
        }
    }
}