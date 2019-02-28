package com.mvc.topay.and.topay_android.presenter

import com.mvc.topay.and.topay_android.MyApplication
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.constract.IRegisterContract
import com.mvc.topay.and.topay_android.model.RegisterModel

class RegisterPresenter : IRegisterContract.RegisterPresenter() {
    override fun verifyUser(name: String, email: String, inviteCode: String, validCode: String?) {
        if (name.isEmpty()) {
            mIView!!.verifyFailed(MyApplication.getAppContext().getString(R.string.login_null_username))
            return
        }
        if (email.isEmpty()) {
            mIView!!.verifyFailed(MyApplication.getAppContext().getString(R.string.login_null_email))
            return
        }
        if (validCode!!.isEmpty()) {
            mIView!!.verifyFailed(MyApplication.getAppContext().getString(R.string.login_null_code))
            return
        }
        mRxUtils.register(mIModel!!.verifyUser(email, inviteCode, validCode)
                .subscribe({ httpData ->
                    if (httpData.code === 200) {
                        mIView!!.verifySuccess(httpData)
                    } else {
                        mIView!!.verifyFailed(httpData.message)
                    }
                }, { throwble ->
                    mIView!!.verifyFailed(throwble.message!!)
                }))
    }

    override fun sendCode(email: String) {
        if (email.isEmpty()) {
            mIView!!.sendCodeError(MyApplication.getAppContext().getString(R.string.login_null_email))
            return
        }
        mRxUtils.register(mIModel!!.sendCode(email)
                .subscribe({ updateBean ->
                    if (updateBean.code === 200) {
                        mIView!!.sendCodeSuccess(MyApplication.getAppContext().getString(R.string.send_code_success))
                    } else {
                        mIView!!.sendCodeError(updateBean.message!!)
                    }
                }, {
                    mIView!!.sendCodeError(it.message!!)
                }))
    }

    override fun getModel(): IRegisterContract.RegisterModel {
        return RegisterModel.instance
    }

    override fun onStart() {

    }

    companion object {
        fun newIntance(): BasePresenter<*, *> {
            return RegisterPresenter()
        }
    }
}