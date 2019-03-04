package com.mvc.topay.and.topay_android.model

import com.mvc.topay.and.topay_android.base.BaseModel
import com.mvc.topay.and.topay_android.constract.ITransferContract

class TransferModel : BaseModel(),ITransferContract.TransferModel {
    companion object {
        val instance: TransferModel
              get() = TransferModel()
    }

}