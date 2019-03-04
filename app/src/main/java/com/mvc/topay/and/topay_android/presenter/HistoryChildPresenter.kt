package com.mvc.topay.and.topay_android.presenter

import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.constract.IHistoryChindContract
import com.mvc.topay.and.topay_android.model.HistoryChildModel

class HistoryChildPresenter : IHistoryChindContract.HistoryChindPresenter() {
    override fun getTransferList(id: Int, pageSize: Int, tokenId: Int, transactionType: Int) {
        mRxUtils.register(mIModel!!.getTransferList(id, pageSize, tokenId, transactionType)
                .subscribe({
                    transaBean->
                    if (transaBean.code === 200) {
                        mIView!!.getHistorySuccess(transaBean.data)
                    }else{
                        mIView!!.getHistoryFailed(transaBean.message!!)
                    }
                },{
                    mIView!!.getHistoryFailed(it.message!!)
                }))
    }

    override fun getModel(): IHistoryChindContract.HistoryChindModel {
        return HistoryChildModel.instance
    }

    override fun onStart() {
    }
    companion object {
        fun newInstance(): BasePresenter<*, *> {
                    return HistoryChildPresenter()
        }
    }
}