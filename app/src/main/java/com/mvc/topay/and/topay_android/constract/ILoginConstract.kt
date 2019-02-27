package com.mvc.topay.and.topay_android.constract

import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.base.IBaseActivity
import com.mvc.topay.and.topay_android.base.IBaseModel
import com.mvc.topay.and.topay_android.base.IBaseView

interface ILoginConstract {
    abstract class LoginPresenter : BasePresenter<LoginModel, LoginView>() {
        abstract fun login(email:String,password:String,code:String)
    }

    interface LoginModel : IBaseModel {

    }

    interface LoginView : IBaseActivity {

    }

}