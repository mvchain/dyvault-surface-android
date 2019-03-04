package com.mvc.topay.and.topay_android.model

import com.mvc.topay.and.topay_android.MyApplication
import com.mvc.topay.and.topay_android.api.ApiStore
import com.mvc.topay.and.topay_android.base.BaseModel
import com.mvc.topay.and.topay_android.bean.ReceiptBean
import com.mvc.topay.and.topay_android.constract.IReceiptQRContract
import com.mvc.topay.and.topay_android.utils.RetrofitUtils
import com.mvc.topay.and.topay_android.utils.RxHelper

import io.reactivex.Observable

class MineReceiptModel : BaseModel(), IReceiptQRContract.ReceiptQRModel {

   override fun getMineQcode(tokenId: Int): Observable<ReceiptBean> {
        return RetrofitUtils.client(ApiStore::class.java).getMineQCode(MyApplication.token, tokenId)
                .compose(RxHelper.rxSchedulerHelper())
                .map { receiptBean -> receiptBean }
   }

    companion object {
        val instance: MineReceiptModel
            get() = MineReceiptModel()
    }
}
