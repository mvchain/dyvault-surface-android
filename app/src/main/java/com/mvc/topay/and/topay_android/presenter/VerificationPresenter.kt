package com.mvc.topay.and.topay_android.presenter

import com.mvc.topay.and.topay_android.MyApplication
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.constract.IVerificationContract
import com.mvc.topay.and.topay_android.model.VerificationModel

class VerificationPresenter : IVerificationContract.VerificationPresenter() {
    companion object {
        fun newIntance(): BasePresenter<*, *> {
            return VerificationPresenter()
        }
    }

    override fun sendCode(email: String) {
        if (email.isEmpty()) {
            mIView!!.error(MyApplication.getAppContext().getString(R.string.login_null_email))
            return
        }
        mRxUtils.register(mIModel!!.sendCode(email)
                .subscribe({ updateBean ->
                    if (updateBean.code === 200) {
                        mIView!!.sendCodeSuccess(MyApplication.getAppContext().getString(R.string.send_code_success))
                    } else {
                        mIView!!.error(updateBean.message!!)
                    }
                }, {
                    mIView!!.error(it.message!!)
                }))
    }

    override fun resetPassword(email: String, value: String) {
        if (email.isEmpty()) {
            mIView!!.error(MyApplication.getAppContext().getString(R.string.login_null_email))
            return
        }
        if (value.isEmpty()) {
            mIView!!.error(MyApplication.getAppContext().getString(R.string.login_null_code))
            return
        }
        mRxUtils.register(mIModel!!.resetPassword(email, value)
                .subscribe({ dateBean ->
                    if (dateBean.code === 200) {
                        mIView!!.resetPassword(dateBean)
                    } else {
                        mIView!!.error(dateBean.message!!)
                    }
                }, {
                    mIView!!.error(it.message!!)
                }))
    }

    override fun getModel(): IVerificationContract.VerificationModel {
        return VerificationModel.instance
    }

    override fun onStart() {
    }
}