package com.mvc.topay.and.topay_android.model

import com.mvc.topay.and.topay_android.base.BaseModel
import com.mvc.topay.and.topay_android.constract.ISetPasswordConstract

class SetPasswordModel : BaseModel(), ISetPasswordConstract.SetPasswordModel {
    companion object {
        val instance: SetPasswordModel
            get() = SetPasswordModel()
    }

}