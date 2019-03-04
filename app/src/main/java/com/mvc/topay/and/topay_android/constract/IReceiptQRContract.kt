package com.mvc.topay.and.topay_android.constract

import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.base.IBaseActivity
import com.mvc.topay.and.topay_android.base.IBaseModel
import com.mvc.topay.and.topay_android.bean.ReceiptBean

import io.reactivex.Observable

interface IReceiptQRContract {
    abstract class ReceiptQRPresenter : BasePresenter<ReceiptQRModel, ReceiptQRView>() {
        abstract fun getMineQcode(tokenId: Int)
    }

    interface ReceiptQRModel : IBaseModel {
        fun getMineQcode(tokenId: Int): Observable<ReceiptBean>
    }

    interface ReceiptQRView : IBaseActivity {
        fun showSuccess(receiptBean: ReceiptBean)
        fun showError()

    }
}
