package com.mvc.topay.and.topay_android.constract

import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.base.IBaseActivity
import com.mvc.topay.and.topay_android.base.IBaseModel
import com.mvc.topay.and.topay_android.bean.HttpDataBean
import com.mvc.topay.and.topay_android.bean.HttpUpdateBean
import io.reactivex.Observable

interface IChangePasswordContract {
    abstract class ChangePasswordPresenter : BasePresenter<ChangePasswordModel, ChangePasswordView>() {
        abstract fun updateLoginPasssword(newPassword: String, oldPassword: String)
        abstract fun updatePaypassword(newPassword: String, oldPassword: String)
    }

    interface ChangePasswordModel : IBaseModel {
        fun updateLoginPasssword(newPassword: String, oldPassword: String): Observable<HttpUpdateBean>
        fun updatePaypassword(newPassword: String, oldPassword: String): Observable<HttpUpdateBean>
    }

    interface ChangePasswordView : IBaseActivity {
        fun updateSuccess(msg:String,type:Int)
        fun updateFailed(msg:String,type:Int)
    }
}