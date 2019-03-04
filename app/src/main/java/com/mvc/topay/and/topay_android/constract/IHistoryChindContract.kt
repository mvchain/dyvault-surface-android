package com.mvc.topay.and.topay_android.constract

import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.base.IBaseActivity
import com.mvc.topay.and.topay_android.base.IBaseModel
import com.mvc.topay.and.topay_android.bean.HttpDataBean
import com.mvc.topay.and.topay_android.bean.HttpUpdateBean
import com.mvc.topay.and.topay_android.bean.TransactionsBean
import io.reactivex.Observable

interface IHistoryChindContract {
    abstract class HistoryChindPresenter : BasePresenter<HistoryChindModel, HistoryChindView>() {
        abstract fun getTransferList(id: Int, pageSize: Int, tokenId: Int, transactionType: Int)
    }

    interface HistoryChindModel : IBaseModel {
        fun getTransferList(id: Int, pageSize: Int, tokenId: Int, transactionType: Int): Observable<TransactionsBean>
    }

    interface HistoryChindView : IBaseActivity {
        fun getHistorySuccess(transactionsBean: ArrayList<TransactionsBean.DataBean>)
        fun getHistoryFailed(msg: String)
    }
}