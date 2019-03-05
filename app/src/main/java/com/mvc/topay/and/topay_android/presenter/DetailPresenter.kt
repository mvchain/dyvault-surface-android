package com.mvc.topay.and.topay_android.presenter

import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.constract.IDetailContract
import com.mvc.topay.and.topay_android.model.DetailModel

class DetailPresenter : IDetailContract.DetailPresenter() {
    override fun getDetailOnID(id: Int) {
        mRxUtils.register(mIModel!!.getDetailOnID(id)
                .subscribe({
                    detail->
                    if (detail.code ===200) {
                        mIView!!.detailSuccess(detail.data)
                    }else{
                        mIView!!.detailFailed(detail.message)
                    }
                },{
                    mIView!!.detailFailed(it.message!!)
                }))
    }

    companion object {
        fun newInstance(): BasePresenter<*, *> {
            return DetailPresenter()
        }
    }

    override fun getModel(): IDetailContract.DetailModel {
        return DetailModel.instance
    }

    override fun onStart() {
    }
}