package com.mvc.topay.and.topay_android.presenter


import com.blankj.utilcode.util.LogUtils
import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.constract.IIncreaseContract
import com.mvc.topay.and.topay_android.model.IncreaseModel

class IncreasePresenter : IIncreaseContract.IncreasePresenter() {
    companion object {
        fun newInstance(): BasePresenter<*, *> {
            return IncreasePresenter()
        }
    }

    override fun getCurrencyAll() {
        mRxUtils.register(mIModel!!.currencyAll()
                .subscribe({ bean ->
                    LogUtils.e(bean.isNotEmpty())
                    if (bean.isNotEmpty()) {
                        mIView!!.showCurrency(bean)
                    } else {
                        mIView!!.showCurrencyNull()
                    }
                }, { throwable ->
                    LogUtils.e(throwable.message)
                }))
    }

    override fun getCurrencySerach(serach: String) {
        mRxUtils.register(mIModel!!.getCurrencySearchList(serach)
                .subscribe({ bean ->
                    if (bean.isNotEmpty()) {
                        mIView!!.showSearchCurrency(bean)
                    } else {
                        mIView!!.showSearchNull()
                    }
                }, { throwable ->
                    LogUtils.e(throwable.message)
                }))
    }

    override fun getModel(): IIncreaseContract.IIncreaseModel {
        return IncreaseModel.instance
    }

    override fun onStart() {
    }

}
