package com.mvc.topay.and.topay_android.presenter

import com.blankj.utilcode.util.LogUtils
import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.constract.IWalletContract
import com.mvc.topay.and.topay_android.model.WalletModel

class WalletPresenter : IWalletContract.WalletPresenter() {
    override fun getBalance() {
        mRxUtils.register(mIModel!!.getBalance()
                .subscribe({ balanceBean ->
                    if(balanceBean.code === 200){
                        mIView!!.balanceSuccess(balanceBean)
                    }else{
                        mIView!!.balanceError()
                    }
                }, {
                    LogUtils.e(it.message)
                    mIView!!.balanceError()
                }))
    }

    override fun getAllAssets() {
        mRxUtils.register(mIModel!!.getAllAssets()
                .subscribe({ asslist ->
                    mIView!!.assetsSuccess(asslist)
                }, {
                    LogUtils.e(it.message)
                    mIView!!.assetsError()
                }))
    }

    companion object {
        fun newInstance(): BasePresenter<*, *> {
            return WalletPresenter()
        }
    }

    override fun getModel(): IWalletContract.WalletModel {
        return WalletModel.instance
    }

    override fun onStart() {

    }
}