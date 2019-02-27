package com.mvc.topay.and.topay_android.base

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.SPUtils
import com.mvc.topay.and.topay_android.common.Constant.SP.TOKEN
import com.trello.rxlifecycle2.components.support.RxFragment

abstract class BaseFragment : RxFragment() {
    protected lateinit var mRootView: View
    protected var mContext: Context? = null
    protected lateinit var mActivity: BaseActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mRootView == null) {
            mRootView = inflater.inflate(getLayoutId(), container, false)
            initView()
            initData()
        }
        return mRootView
    }

    abstract fun initData()

    abstract fun initView()

    abstract fun getLayoutId(): Int

    @SuppressLint("MissingSuperCall")
    override fun onAttach(context: Context?) {
        this.mContext = context!!
        this.mActivity = context as BaseActivity
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
        this.mContext = null
    }

    protected fun getToken(): String {
        return SPUtils.getInstance().getString(TOKEN)
    }

}