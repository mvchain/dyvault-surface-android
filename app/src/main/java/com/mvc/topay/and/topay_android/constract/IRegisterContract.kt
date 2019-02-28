package com.mvc.topay.and.topay_android.constract

import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.base.IBaseActivity
import com.mvc.topay.and.topay_android.base.IBaseModel
import com.mvc.topay.and.topay_android.bean.CodeBean
import com.mvc.topay.and.topay_android.bean.HttpDataBean
import com.mvc.topay.and.topay_android.bean.HttpUpdateBean
import io.reactivex.Observable

interface IRegisterContract {
    abstract class RegisterPresenter : BasePresenter<RegisterModel, RegisterView>() {
        abstract fun sendCode(email: String)
        abstract fun verifyUser(name:String,email: String, inviteCode: String, validCode: String?)
    }

    interface RegisterModel : IBaseModel {
        fun sendCode(email: String): Observable<HttpUpdateBean>
        fun verifyUser(email: String, inviteCode: String, validCode: String?): Observable<HttpDataBean>
    }

    interface RegisterView : IBaseActivity {
        fun verifyFailed(msg:String)
        fun verifySuccess(httpDataBean: HttpDataBean)
        fun sendCodeSuccess(msg:String)
        fun sendCodeError(msg:String)
    }
}