package com.mvc.topay.and.topay_android.base

interface IBaseView {
    fun initPresenter(): BasePresenter<*, *>
}