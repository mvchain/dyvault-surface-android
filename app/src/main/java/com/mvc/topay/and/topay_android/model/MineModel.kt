package com.mvc.topay.and.topay_android.model

import com.mvc.topay.and.topay_android.base.BaseModel
import com.mvc.topay.and.topay_android.constract.IMineContract

class MineModel :BaseModel(),IMineContract.MineModel {
    companion object {
        val instance: MineModel
            get() = MineModel()
    }
}