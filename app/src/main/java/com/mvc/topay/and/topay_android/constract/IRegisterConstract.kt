package com.mvc.topay.and.topay_android.constract

import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.base.IBaseActivity
import com.mvc.topay.and.topay_android.base.IBaseModel
import com.mvc.topay.and.topay_android.base.IBaseView

interface IRegisterConstract {
    abstract class RegisterPresenter : BasePresenter<RegisterModel, RegisterView>() {
        abstract fun register(email:String,password:String,code:String)
    }

    interface RegisterModel : IBaseModel {

    }

    interface RegisterView : IBaseActivity {

    }

}