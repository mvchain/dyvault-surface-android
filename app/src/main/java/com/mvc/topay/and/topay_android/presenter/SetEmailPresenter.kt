package com.mvc.topay.and.topay_android.presenter

import com.mvc.topay.and.topay_android.MyApplication
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.constract.ISetEmailContract
import com.mvc.topay.and.topay_android.model.SetEmailModel

class SetEmailPresenter : ISetEmailContract.SetEmailPresenter() {
    override fun verifyEmail(email: String, token: String, validCode: String) {
        mRxUtils.register(mIModel!!.verifyEmail(email, token, validCode)
                .subscribe({ tokenBean ->
                    if (tokenBean.code === 200) {
                        mIView!!.verifyEmailSuccess(tokenBean.data)
                    } else {
                        mIView!!.verifyEmailFailed(tokenBean.message!!)
                    }
                }, {
                    mIView!!.verifyEmailFailed(it.message!!)
                }))
    }

    override fun sendEmail(email: String) {
        mRxUtils.register(mIModel!!.sendEmail(email)
                .subscribe({ updateBean ->
                    if (updateBean.code === 200) {
                        mIView!!.sendEmailSuccess(MyApplication.appContext.getString(R.string.send_code_success))
                    } else {
                        mIView!!.sendEmailFailed(updateBean.message!!)
                    }
                }, {
                    mIView!!.sendEmailFailed(it.message!!)
                }))
    }

    companion object {
        fun newIntance(): BasePresenter<*, *> {
            return SetEmailPresenter()
        }
    }

    override fun getModel(): ISetEmailContract.SetEmailModel {
        return SetEmailModel.instance
    }

    override fun onStart() {
    }
}