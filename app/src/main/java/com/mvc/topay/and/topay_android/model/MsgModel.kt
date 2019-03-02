package com.mvc.topay.and.topay_android.model

import com.mvc.topay.and.topay_android.MyApplication
import com.mvc.topay.and.topay_android.api.ApiStore
import com.mvc.topay.and.topay_android.base.BaseModel
import com.mvc.topay.and.topay_android.bean.MsgBean
import com.mvc.topay.and.topay_android.constract.IMsgContrast
import com.mvc.topay.and.topay_android.utils.RetrofitUtils
import com.mvc.topay.and.topay_android.utils.RxHelper
import io.reactivex.Observable

class MsgModel : BaseModel(), IMsgContrast.MsgModel {
    override fun getMessage(pageSize: Int, timestamp: Long): Observable<MsgBean> {
        return RetrofitUtils.client(ApiStore::class.java).getMessage(MyApplication.token, pageSize, timestamp)
                .compose(RxHelper.rxSchedulerHelper())
                .map { msgBean -> msgBean }
    }

    companion object {
        val instance: MsgModel
            get() = MsgModel()
    }

}