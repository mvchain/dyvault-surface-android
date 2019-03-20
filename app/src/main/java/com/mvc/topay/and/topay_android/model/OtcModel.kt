package com.mvc.topay.and.topay_android.model

import com.mvc.topay.and.topay_android.base.BaseModel
import com.mvc.topay.and.topay_android.constract.OtcContract

class OtcModel :BaseModel(),OtcContract.OtcModel {
    companion object {
        val instance: OtcModel
              get() = OtcModel()
    }
}