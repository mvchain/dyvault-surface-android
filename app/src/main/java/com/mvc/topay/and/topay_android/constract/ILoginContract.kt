package com.mvc.topay.and.topay_android.constract

import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.base.IBaseActivity
import com.mvc.topay.and.topay_android.base.IBaseModel
import com.mvc.topay.and.topay_android.base.IBaseView
import com.mvc.topay.and.topay_android.bean.HttpUpdateBean
import com.mvc.topay.and.topay_android.bean.RegisterBean
import io.reactivex.Observable

interface ILoginContract {
    abstract class LoginPresenter : BasePresenter<LoginModel, LoginView>() {
        abstract fun login(imageToken: String, password: String, username: String, validCode: String)
        abstract fun sendCode(email: String)
    }

    interface LoginModel : IBaseModel {
        fun login(imageToken: String, password: String, username: String, validCode: String): Observable<RegisterBean>
        fun sendCode(email: String): Observable<HttpUpdateBean>
    }

    interface LoginView : IBaseActivity {
        fun loginSuccess(registerBean: RegisterBean)
        fun loginFailed(msg: String)
        fun sendCodeSuccess(msg:String)
        fun sendCodeError(msg:String)
    }
}