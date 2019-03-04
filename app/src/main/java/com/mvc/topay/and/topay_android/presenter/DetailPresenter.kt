package com.mvc.topay.and.topay_android.presenter

import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.constract.IDetailContract
import com.mvc.topay.and.topay_android.model.DetailModel

class DetailPresenter : IDetailContract.DetailPresenter() {
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