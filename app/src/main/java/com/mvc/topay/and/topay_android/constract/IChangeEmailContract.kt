package com.mvc.topay.and.topay_android.constract

import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.base.IBaseActivity
import com.mvc.topay.and.topay_android.base.IBaseModel
import com.mvc.topay.and.topay_android.bean.HttpDataBean
import com.mvc.topay.and.topay_android.bean.HttpUpdateBean
import io.reactivex.Observable

interface IChangeEmailContract {
    abstract class ChangeEmailPresenter : BasePresenter<ChangeEmailModel, ChangeEmailView>() {
        abstract fun sendEmail()
        abstract fun verifyEmail(validCode:String)
    }

    interface ChangeEmailModel : IBaseModel {
        fun sendEmail(): Observable<HttpUpdateBean>
        fun verifyEmail(validCode:String): Observable<HttpDataBean>
    }

    interface ChangeEmailView : IBaseActivity {
        fun sendEmailSuccess(msg: String)
        fun sendEmailFailed(msg: String)
        fun verifyEmailSuccess(msg: String)
        fun verifyEmailFailed(msg: String)

    }
}