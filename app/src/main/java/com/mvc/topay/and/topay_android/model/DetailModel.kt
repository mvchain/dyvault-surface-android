package com.mvc.topay.and.topay_android.model

import com.mvc.topay.and.topay_android.base.BaseModel
import com.mvc.topay.and.topay_android.constract.IDetailContract

class DetailModel :BaseModel(),IDetailContract.DetailModel {
    companion object {
        val instance: DetailModel
              get() = DetailModel()
    }
}