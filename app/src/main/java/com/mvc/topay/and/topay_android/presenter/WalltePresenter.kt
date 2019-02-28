package com.mvc.topay.and.topay_android.presenter

import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.constract.IWallteContract
import com.mvc.topay.and.topay_android.model.WallteModel

class WalltePresenter : IWallteContract.WalltePresenter() {
    companion object {
        fun newIntance(): BasePresenter<*, *> {
            return WalltePresenter()
        }
    }

    override fun getModel(): IWallteContract.WallteModel {
        return WallteModel.instance
    }

    override fun onStart() {

    }
}