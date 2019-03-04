package com.mvc.topay.and.topay_android.presenter

import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.constract.ITransferContract
import com.mvc.topay.and.topay_android.model.TransferModel

class TransferPresenter : ITransferContract.TransferPresenter() {
    companion object {
        fun newInstance(): BasePresenter<*, *> {
            return TransferPresenter()
        }
    }

    override fun getModel(): ITransferContract.TransferModel {
        return TransferModel.instance
    }

    override fun onStart() {
    }

}