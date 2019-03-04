package com.mvc.topay.and.topay_android.base

import android.os.Bundle

abstract class BaseMVPActivity<V, P : BasePresenter<*, V>> : BaseActivity(), IBaseView {
    protected lateinit var mPresenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = initPresenter() as P
        if (mPresenter !== null) {
            mPresenter.attachMVP(this as V)
        }
        initMVPView()
        initMVPData()
    }

    abstract fun initMVPData()

    open fun initMVPView() {
        super.initView()
    }

    override fun initView() {
    }

    override fun initData() {
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mPresenter != null) {
            mPresenter.detachMVP()
        }
    }
}