package com.mvc.topay.and.topay_android.model

import com.mvc.topay.and.topay_android.base.BaseModel
import com.mvc.topay.and.topay_android.constract.IWallteContract

class WallteModel :BaseModel(),IWallteContract.WallteModel {
    companion object {
        val instance: WallteModel
            get() = WallteModel()
    }
}