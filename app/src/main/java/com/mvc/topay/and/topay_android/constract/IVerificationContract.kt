package com.mvc.topay.and.topay_android.constract

import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.base.IBaseActivity
import com.mvc.topay.and.topay_android.base.IBaseModel
import com.mvc.topay.and.topay_android.bean.HttpDataBean
import com.mvc.topay.and.topay_android.bean.HttpUpdateBean
import io.reactivex.Observable

interface IVerificationContract {
    abstract class VerificationPresenter : BasePresenter<VerificationModel, VerificationView>() {
        abstract fun sendCode(email: String)
        abstract fun resetPassword(email: String, value: String)
    }

    interface VerificationModel : IBaseModel {
        //        fun sendCode(email: String): Observable<CodeBean>
        fun sendCode(email: String): Observable<HttpUpdateBean>
        fun resetPassword(email: String, value: String): Observable<HttpDataBean>
    }

    interface VerificationView : IBaseActivity {
        fun sendCodeSuccess(msg:String)
        fun error(msg:String)
        fun resetPassword(httpDataBean: HttpDataBean)
    }
}