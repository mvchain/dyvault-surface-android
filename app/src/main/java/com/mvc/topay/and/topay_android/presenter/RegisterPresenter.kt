package com.mvc.topay.and.topay_android.presenter

import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.constract.IRegisterConstract
import com.mvc.topay.and.topay_android.model.RegisterModel

class RegisterPresenter : IRegisterConstract.RegisterPresenter() {
    override fun register(email: String, password: String, code: String) {

    }

    override fun getModel(): IRegisterConstract.RegisterModel {
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