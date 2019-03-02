package com.mvc.topay.and.topay_android.model

import com.mvc.topay.and.topay_android.MyApplication
import com.mvc.topay.and.topay_android.api.ApiStore
import com.mvc.topay.and.topay_android.base.BaseModel
import com.mvc.topay.and.topay_android.bean.UserInfoBean
import com.mvc.topay.and.topay_android.constract.IMineContract
import com.mvc.topay.and.topay_android.utils.RetrofitUtils
import com.mvc.topay.and.topay_android.utils.RxHelper
import io.reactivex.Observable

class MineModel : BaseModel(), IMineContract.MineModel {
    override fun getUserInfo(): Observable<UserInfoBean> {
        return RetrofitUtils.client(ApiStore::class.java).getUserInfo(MyApplication.token)
                .compose(RxHelper.rxSchedulerHelper())
                .map { userBean -> userBean }
    }

    companion object {
        val instance: MineModel
            get() = MineModel()
    }
}