package com.mvc.topay.and.topay_android.activity

import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.base.BaseMVPActivity
import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.constract.ISetPasswordConstract
import com.mvc.topay.and.topay_android.presenter.SetPasswordPresenter

class SetPasswordActivity :BaseMVPActivity<ISetPasswordConstract.SetPasswordView,ISetPasswordConstract.SetPasswordPresenter>() {
    override fun initMVPData() {

    }

    override fun initMVPView() {
        super.initMVPView()
    }
    override fun getLayoutId(): Int {
        return R.layout.activity_setpassword
    }

    override fun initPresenter(): BasePresenter<*, *> {
        return SetPasswordPresenter.newIntance()
    }
}