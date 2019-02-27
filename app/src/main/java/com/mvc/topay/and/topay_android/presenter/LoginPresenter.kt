package com.mvc.topay.and.topay_android.presenter

import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.constract.ILoginConstract
import com.mvc.topay.and.topay_android.model.LoginModel

class LoginPresenter : ILoginConstract.LoginPresenter() {
    override fun login(email: String, password: String, code: String) {

    }

    override fun getModel(): ILoginConstract.LoginModel {
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