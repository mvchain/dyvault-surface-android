package com.mvc.topay.and.topay_android.presenter

import com.blankj.utilcode.util.LogUtils
import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.constract.IReceiptQRContract
import com.mvc.topay.and.topay_android.model.MineReceiptModel


class MineReceiptPresenter : IReceiptQRContract.ReceiptQRPresenter() {
    override fun getModel(): IReceiptQRContract.ReceiptQRModel {
        return MineReceiptModel.instance
    }

    override fun getMineQcode(tokenId: Int) {
        mRxUtils.register(mIModel!!.getMineQcode(tokenId).subscribe({ receiptBean ->
            if (receiptBean.code === 200) {
                mIView!!.showSuccess(receiptBean)
            }
        }, { throwable ->
            LogUtils.e(throwable)
            mIView!!.showError()
        }))
    }

    override fun onStart() {

    }

    companion object {
        fun newIntance(): BasePresenter<*, *> {
            return MineReceiptPresenter()
        }
    }
}
