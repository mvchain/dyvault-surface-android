package com.mvc.topay.and.topay_android.presenter

import com.mvc.topay.and.topay_android.MyApplication
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.constract.IChangeEmailContract
import com.mvc.topay.and.topay_android.model.ChangeEmailModel

class ChangeEmailPresenter : IChangeEmailContract.ChangeEmailPresenter() {
    override fun verifyEmail(validCode: String) {
        mRxUtils.register(mIModel!!.verifyEmail(validCode)
                .subscribe({ dataBean ->
                    if (dataBean.code === 200) {
                        mIView!!.verifyEmailSuccess(dataBean.data)
                    } else {
                        mIView!!.verifyEmailFailed(dataBean.message!!)
                    }
                }, {
                    mIView!!.verifyEmailFailed(it.message!!)
                }))
    }

    companion object {
        fun newIntance(): BasePresenter<*, *> {
            return ChangeEmailPresenter()
        }
    }

    override fun sendEmail() {
        mRxUtils.register(mIModel!!.sendEmail()
                .subscribe({
                    if (it.code === 200 && it.data) {
                        mIView!!.sendEmailSuccess(MyApplication.appContext.getString(R.string.send_code_success))
                    } else {
                        mIView!!.sendEmailFailed(it.message!!)
                    }
                }, {
                    mIView!!.sendEmailFailed(it.message!!)
                }))
    }

    override fun getModel(): IChangeEmailContract.ChangeEmailModel {
        return ChangeEmailModel.instance
    }

    override fun onStart() {
    }
}