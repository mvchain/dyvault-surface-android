package com.mvc.topay.and.topay_android.model

import com.mvc.topay.and.topay_android.MyApplication
import com.mvc.topay.and.topay_android.api.ApiStore
import com.mvc.topay.and.topay_android.base.BaseModel
import com.mvc.topay.and.topay_android.bean.TransactionsBean
import com.mvc.topay.and.topay_android.constract.IHistoryChindContract
import com.mvc.topay.and.topay_android.utils.RetrofitUtils
import com.mvc.topay.and.topay_android.utils.RxHelper
import io.reactivex.Observable

class HistoryChildModel : BaseModel(), IHistoryChindContract.HistoryChindModel {
    companion object {
        val instance: HistoryChildModel
            get() = HistoryChildModel()
    }

    override fun getTransferList(id: Int, pageSize: Int, tokenId: Int, transactionType: Int): Observable<TransactionsBean> {
        return RetrofitUtils.client(ApiStore::class.java).getTransferList(MyApplication.token, id, pageSize, tokenId, transactionType)
                .compose(RxHelper.rxSchedulerHelper())
                .map { transactionBean -> transactionBean }
    }
}