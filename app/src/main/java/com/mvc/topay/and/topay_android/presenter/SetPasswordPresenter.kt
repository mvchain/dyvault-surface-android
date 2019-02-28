package com.mvc.topay.and.topay_android.presenter

import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.constract.ISetPasswordConstract
import com.mvc.topay.and.topay_android.model.SetPasswordModel

class SetPasswordPresenter : ISetPasswordConstract.SetPasswordPresenter() {
    override fun setPassword(login_password: String, pay_password: String) {

    }

    override fun getModel(): ISetPasswordConstract.SetPasswordModel {
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