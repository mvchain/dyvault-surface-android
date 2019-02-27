package com.mvc.topay.and.topay_android.base

import android.os.Bundle

abstract class BaseMVPActivity<V, P : BasePresenter<*, V>> : BaseActivity(), IBaseView {
    protected lateinit var mPresenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = initPresenter() as P
        if (mPresenter !== null) {
            mPresenter.attchMVP(this as V)
        }
        initMVPView()
        initMVPData()
    }

    abstract fun initMVPData()

    abstract fun initMVPView()

    override fun initView() {
    }

    override fun initData() {
    }
}