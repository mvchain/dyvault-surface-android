package com.mvc.topay.and.topay_android.constract

import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.base.IBaseActivity
import com.mvc.topay.and.topay_android.base.IBaseModel

interface ISetPasswordConstract {
    abstract class SetPasswordPresenter : BasePresenter<SetPasswordModel, SetPasswordView>() {
        abstract fun setPassword(login_password: String, pay_password: String)
    }

    interface SetPasswordModel : IBaseModel {

    }

    interface SetPasswordView : IBaseActivity {

    }

}