package com.mvc.topay.and.topay_android.presenter

import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.constract.IBuyingDetailContract
import com.mvc.topay.and.topay_android.model.BuyingDetailModel

class BuyingDetailPresenter : IBuyingDetailContract.BuyingDetailPresenter() {
    override fun getBuyingDetail(id: Int) {
        mRxUtils.register(mIModel!!.getBuyingDetail(id)
                .subscribe({
                    buyBean->
                    mIView!!.buyingDetailSuccess(buyBean.data)
                },{
                    mIView!!.buyingDetailFailed(it.message!!)
                }))
    }

    companion object {
        fun newInstance(): BasePresenter<*, *> {
            return BuyingDetailPresenter()
        }
    }

    override fun getModel(): IBuyingDetailContract.BuyingDetailModel {
        return BuyingDetailModel.instance
    }

    override fun onStart() {
    }
}