package com.mvc.topay.and.topay_android.presenter

import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.constract.ITransferContract
import com.mvc.topay.and.topay_android.model.TransferModel

class TransferPresenter : ITransferContract.TransferPresenter() {
    override fun getDetail(tokenId: Int) {
        mRxUtils.register(mIModel!!.getDetail(tokenId)
                .subscribe({ idToBean ->
                    mIView!!.detailSuccess(idToBean.data)
                }, {
                    mIView!!.detailFailed(it.message!!)
                }))
    }

    override fun sendTransferMsg(address: String, password: String, tokenId: Int, value: String) {
        mRxUtils.register(mIModel!!.sendTransferMsg(address, password, tokenId, value)
                .subscribe({ updateBean ->
                    if (updateBean.code === 200) {
                        mIView!!.transferSuccess(updateBean)
                    } else {
                        mIView!!.transferFailed(updateBean.message)
                    }
                }, {
                    mIView!!.transferFailed(it.message!!)
                }))
    }

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