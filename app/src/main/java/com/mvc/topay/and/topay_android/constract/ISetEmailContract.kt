package com.mvc.topay.and.topay_android.constract

import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.base.IBaseActivity
import com.mvc.topay.and.topay_android.base.IBaseModel
import com.mvc.topay.and.topay_android.bean.HttpDataBean
import com.mvc.topay.and.topay_android.bean.HttpUpdateBean
import io.reactivex.Observable

interface ISetEmailContract {
    abstract class SetEmailPresenter : BasePresenter<SetEmailModel, SetEmailView>() {
        abstract fun sendEmail(email:String)
        abstract fun verifyEmail(email:String,token:String,validCode:String)
    }

    interface SetEmailModel : IBaseModel {
        fun sendEmail(email:String): Observable<HttpUpdateBean>
        fun verifyEmail(email:String,token:String,validCode:String): Observable<HttpDataBean>
    }

    interface SetEmailView : IBaseActivity {
        fun sendEmailSuccess(msg: String)
        fun sendEmailFailed(msg: String)
        fun verifyEmailSuccess(msg: String)
        fun verifyEmailFailed(msg: String)

    }
}