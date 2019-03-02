package com.mvc.topay.and.topay_android.presenter

import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.constract.IMsgContrast
import com.mvc.topay.and.topay_android.model.MsgModel

class MsgPresenter : IMsgContrast.MsgPresenter() {
    override fun getMessage(pageSize: Int, timestamp: Long) {
        mRxUtils.register(mIModel!!.getMessage(pageSize, timestamp)
                .subscribe({
                    msgBean->
                    if (msgBean.code === 200) {
                        mIView!!.msgSuccess(msgBean.data)
                    }else{
                        mIView!!.msgError(msgBean.message!!)
                    }
                },{
                    throwable->
                    mIView!!.msgError(throwable.message!!)
                }))
    }

    companion object {
        fun newInstance(): BasePresenter<*, *> {
                    return MsgPresenter()
        }
    }

    override fun getModel(): IMsgContrast.MsgModel {
        return MsgModel.instance
    }

    override fun onStart() {
    }
}