package com.mvc.topay.and.topay_android.base

abstract class BaseMVPFragment<V,P:BasePresenter<*,V>> :BaseFragment(),IBaseFragment {
    protected lateinit var mPresenter: P

    override fun initData() {
        mPresenter = initPresenter() as P
        if(mPresenter !== null){
            mPresenter?.attchMVP(this as V)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if(mPresenter !== null){
            mPresenter!!.detachMVP()
        }
    }
}