package com.mvc.topay.and.topay_android.fragment

import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.base.BaseMVPFragment
import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.constract.IMineContract
import com.mvc.topay.and.topay_android.presenter.MinePresenter

class MineFragment : BaseMVPFragment<IMineContract.MineView, IMineContract.MinePresenter>(), IMineContract.MineView {
    override fun initView() {
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun initPresenter(): BasePresenter<*, *> {
        return MinePresenter.newIntance()
    }
}