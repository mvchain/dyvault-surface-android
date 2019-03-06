package com.mvc.topay.and.topay_android.presenter

import com.blankj.utilcode.util.LogUtils
import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.constract.IMineContract
import com.mvc.topay.and.topay_android.model.MineModel

class MinePresenter : IMineContract.MinePresenter() {
    override fun getUserInfo() {
        mRxUtils.register(mIModel!!.getUserInfo()
                .subscribe({
                    if (it.code === 200) {
                        mIView!!.getUserSuccess(it.data)
                    } else {
                        mIView!!.getUserFailed(it)
                    }
                }, {
                    LogUtils.e(it.message!!)
                    mIView!!.getUserFailed(null)
                }))
    }

    companion object {
        fun newInstance(): BasePresenter<*, *> {
            return MinePresenter()
        }
    }

    override fun getModel(): IMineContract.MineModel {
        return MineModel.instance
    }

    override fun onStart() {

    }
}