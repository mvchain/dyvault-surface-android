package com.mvc.topay.and.topay_android.presenter

import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.constract.ILoginContract
import com.mvc.topay.and.topay_android.model.LoginModel

class LoginPresenter : ILoginContract.LoginPresenter() {
    override fun login(email: String, password: String, code: String) {

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