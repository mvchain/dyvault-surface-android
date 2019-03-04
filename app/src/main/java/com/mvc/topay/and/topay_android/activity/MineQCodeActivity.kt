package com.mvc.topay.and.topay_android.activity

import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.base.BaseMVPActivity
import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.bean.ReceiptBean
import com.mvc.topay.and.topay_android.constract.IReceiptQRContract
import com.mvc.topay.and.topay_android.presenter.MineReceiptPresenter

class MineQCodeActivity : BaseMVPActivity<IReceiptQRContract.ReceiptQRView, IReceiptQRContract.ReceiptQRPresenter>(), IReceiptQRContract.ReceiptQRView {
    override fun initMVPData() {
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_mineqcode
    }

    override fun initMVPView() {
        super.initMVPView()
    }
    override fun initPresenter(): BasePresenter<*, *> {
        return MineReceiptPresenter.newIntance()
    }

    override fun showSuccess(receiptBean: ReceiptBean) {
    }

    override fun showError() {
    }

}