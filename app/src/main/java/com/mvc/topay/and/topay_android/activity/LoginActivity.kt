package com.mvc.topay.and.topay_android.activity

import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.base.BaseMVPActivity
import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.constract.ILoginConstract
import com.mvc.topay.and.topay_android.presenter.LoginPresenter

class LoginActivity : BaseMVPActivity<ILoginConstract.LoginView, ILoginConstract.LoginPresenter>() {
    var httpUrl = "http://www.bilibili.com/video/av9526132/?from%3Dsearch%26seid%3D8875280544793868560"
    override fun initMVPData() {
    }

    override fun initMVPView() {
        super.initMVPView()

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun initPresenter(): BasePresenter<*, *> {
        return LoginPresenter.newIntance()
    }

}