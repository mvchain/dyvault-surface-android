package com.mvc.topay.and.topay_android.activity

import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.base.BaseMVPActivity
import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.constract.ITransferContract
import com.mvc.topay.and.topay_android.presenter.TransferPresenter

class TransferActivity : BaseMVPActivity<ITransferContract.TransferView, ITransferContract.TransferPresenter>(), ITransferContract.TransferView {
    override fun initMVPData() {
    }

    override fun initMVPView() {
        super.initMVPView()
    }
    override fun getLayoutId(): Int {
        return R.layout.activity_transfer
    }

    override fun initPresenter(): BasePresenter<*, *> {
        return TransferPresenter.newInstance()
    }

}