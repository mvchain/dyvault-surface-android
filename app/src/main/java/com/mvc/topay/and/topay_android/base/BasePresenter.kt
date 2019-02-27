package com.mvc.topay.and.topay_android.base

import com.mvc.topay.and.topay_android.utils.RxUtils

abstract class BasePresenter<M, V> {
    protected var mIModel: M? = null
    protected var mIView: V? = null
    protected var mRxUtils = RxUtils.instance

    fun attchMVP(v: V) {
        this.mIModel = getModel()
        this.mIView = v
        this.onStart()
    }

    fun detachMVP(){
        this.mRxUtils.unSubscribe()
        this.mIModel = null
        this.mIView = null

    }

    abstract fun getModel(): M

    /**
     * IView和IModel绑定完成立即执行
     *
     *
     * 实现类实现绑定完成后的逻辑,例如数据初始化等,界面初始化, 更新等
     */
    abstract fun onStart()
}