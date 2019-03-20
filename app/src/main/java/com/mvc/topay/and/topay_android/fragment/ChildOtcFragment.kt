package com.mvc.topay.and.topay_android.fragment

import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.base.BaseMVPFragment
import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.constract.OtcContract
import com.mvc.topay.and.topay_android.presenter.OtcPresenter

class ChildOtcFragment : BaseMVPFragment<OtcContract.OtcView, OtcContract.OtcPresenter>(), OtcContract.OtcView {
    private var type = 0
    private var isRefresh = false
    override fun initView() {
        type = arguments!!.getInt("otc_type")
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_child_otc
    }

    override fun initPresenter(): BasePresenter<*, *> {
        return OtcPresenter.newInstance()
    }

    override fun initData() {
        super.initData()
        isRefresh = true
    }
}