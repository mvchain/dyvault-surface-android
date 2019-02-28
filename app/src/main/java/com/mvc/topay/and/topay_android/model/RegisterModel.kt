package com.mvc.topay.and.topay_android.model

import com.mvc.topay.and.topay_android.base.BaseModel
import com.mvc.topay.and.topay_android.constract.ILoginConstract
import com.mvc.topay.and.topay_android.constract.IRegisterConstract

class RegisterModel : BaseModel(), IRegisterConstract.RegisterModel {
    companion object {
        val instance: RegisterModel
            get() = RegisterModel()
    }

}