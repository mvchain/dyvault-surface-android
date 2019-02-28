package com.mvc.topay.and.topay_android.constract

import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.base.IBaseActivity
import com.mvc.topay.and.topay_android.base.IBaseModel
import com.mvc.topay.and.topay_android.bean.HttpUpdateBean
import com.mvc.topay.and.topay_android.bean.RegisterBean
import io.reactivex.Observable

interface IResetPasswordContract {
    abstract class ResetPasswordPresenter : BasePresenter<ResetPasswordModel, ResetPasswordView>() {
        abstract fun resetPassword(email: String,password: String, token: String, type: Int)
    }

    interface ResetPasswordModel : IBaseModel {
        fun resetPassword(password: String, token: String, type: Int): Observable<HttpUpdateBean>
    }

    interface ResetPasswordView : IBaseActivity {
        fun resetSuccess(msg: String)
        fun resetFailed(msg: String)
    }
}