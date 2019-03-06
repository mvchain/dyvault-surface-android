package com.mvc.topay.and.topay_android.constract

import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.base.IBaseActivity
import com.mvc.topay.and.topay_android.base.IBaseModel
import com.mvc.topay.and.topay_android.bean.RegisterBean
import io.reactivex.Observable

interface ISetPasswordContract {
    abstract class SetPasswordPresenter : BasePresenter<SetPasswordModel, SetPasswordView>() {
        abstract fun setPassword(email: String, inviteCode: String, nickname: String, password: String, token: String, transactionPassword: String)
    }

    interface SetPasswordModel : IBaseModel {
        /**
         * register
         */
        fun setPassword(email: String, inviteCode: String, nickname: String, password: String, uuid: String, token: String, transactionPassword: String): Observable<RegisterBean>
    }

    interface SetPasswordView : IBaseActivity {
        fun registerSuccess(registerBean: RegisterBean)
        fun registerFailed(msg: String)
    }
}