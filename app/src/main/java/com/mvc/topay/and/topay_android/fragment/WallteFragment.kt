package com.mvc.topay.and.topay_android.fragment

import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.base.BaseMVPFragment
import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.constract.IWallteContract
import com.mvc.topay.and.topay_android.presenter.WalltePresenter

class WallteFragment :BaseMVPFragment<IWallteContract.WallteView,IWallteContract.WalltePresenter>(),IWallteContract.WallteView {
    override fun initView() {
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_wallte
    }

    override fun initPresenter(): BasePresenter<*, *> {
        return WalltePresenter.newIntance()
    }
}