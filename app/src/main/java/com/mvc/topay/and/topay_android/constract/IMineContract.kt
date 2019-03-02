package com.mvc.topay.and.topay_android.constract

import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.base.IBaseActivity
import com.mvc.topay.and.topay_android.base.IBaseModel
import com.mvc.topay.and.topay_android.bean.UserInfoBean
import io.reactivex.Observable

interface IMineContract {
    abstract class MinePresenter : BasePresenter<MineModel, MineView>() {
        abstract fun getUserInfo()
    }

    interface MineModel : IBaseModel {
        fun getUserInfo(): Observable<UserInfoBean>
    }

    interface MineView : IBaseActivity {
        fun getUserSuccess(userInfoBean: UserInfoBean.DataBean)
        fun getUserFailed(msg: String)
    }
}