package com.mvc.topay.and.topay_android.presenter

import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.constract.IBuyingContract
import com.mvc.topay.and.topay_android.model.BuyingModel

class BuyingPresenter : IBuyingContract.BuyingPresenter() {
    override fun getChannelList(id: Int, pageSize: Int, status: Int) {
        mRxUtils.register(mIModel!!.getChannelList(id, pageSize,status)
                .subscribe({
                    channel->
                    mIView!!.channelSuccess(channel.data)
                },{
                    mIView!!.channelFailed(it.message!!)
                }))
    }


    companion object {
        fun newInstance(): BasePresenter<*, *> {
            return BuyingPresenter()
        }
    }

    override fun getModel(): IBuyingContract.BuyingModel {
        return BuyingModel.instance
    }

    override fun onStart() {
    }

}