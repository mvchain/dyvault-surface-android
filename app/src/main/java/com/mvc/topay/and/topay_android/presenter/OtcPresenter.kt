package com.mvc.topay.and.topay_android.presenter

import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.constract.OtcContract
import com.mvc.topay.and.topay_android.model.OtcModel

class OtcPresenter : OtcContract.OtcPresenter() {
    companion object {
        fun newInstance(): BasePresenter<*, *> {
                    return OtcPresenter()
        }
    }
    override fun getModel(): OtcContract.OtcModel {
        return OtcModel.instance
    }

    override fun onStart() {
    }
}