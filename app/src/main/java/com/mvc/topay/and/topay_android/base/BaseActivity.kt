package com.mvc.topay.and.topay_android.base

import android.os.Bundle
import com.blankj.utilcode.util.SPUtils
import com.gyf.barlibrary.ImmersionBar
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.common.Constant
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity

abstract class BaseActivity : RxAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initView()
        initData()
    }

    abstract fun initData()

    open fun initView(){
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init()
    }

    abstract fun getLayoutId(): Int

    protected fun getToken(): String {
        return SPUtils.getInstance().getString(Constant.SP.TOKEN)
    }
}