package com.mvc.topay.and.topay_android.presenter

import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.constract.IMineContract
import com.mvc.topay.and.topay_android.constract.IWallteContract
import com.mvc.topay.and.topay_android.model.MineModel
import com.mvc.topay.and.topay_android.model.WallteModel

class MinePresenter : IMineContract.MinePresenter() {
    companion object {
        fun newIntance(): BasePresenter<*, *> {
            return MinePresenter()
        }
    }

    override fun getModel(): IMineContract.MineModel {
        return MineModel.instance
    }

    override fun onStart() {

    }
}