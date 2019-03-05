package com.mvc.topay.and.topay_android.constract

import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.base.IBaseActivity
import com.mvc.topay.and.topay_android.base.IBaseModel
import com.mvc.topay.and.topay_android.bean.HttpUpdateBean
import com.mvc.topay.and.topay_android.bean.IDToTransferBean
import io.reactivex.Observable

interface ITransferContract {
    abstract class TransferPresenter : BasePresenter<TransferModel, TransferView>() {
        abstract fun getDetail(id: Int)
        abstract fun sendTransferMsg(address: String, password: String, tokenId: Int, value: String)
    }

    interface TransferModel : IBaseModel {
        fun getDetail(id: Int): Observable<IDToTransferBean>
        fun sendTransferMsg(address: String, password: String, tokenId: Int, value: String): Observable<HttpUpdateBean>

    }

    interface TransferView : IBaseActivity {
        fun detailSuccess(data: IDToTransferBean.DataBean)
        fun detailFailed(msg: String)
        fun transferSuccess(bean: HttpUpdateBean)
        fun transferFailed(msg:String)
    }
}