package com.mvc.topay.and.topay_android.model

import com.mvc.topay.and.topay_android.base.BaseModel
import com.mvc.topay.and.topay_android.constract.ILoginConstract

class LoginModel : BaseModel(), ILoginConstract.LoginModel {
    companion object {
        val instance: LoginModel
            get() = LoginModel()
    }

}