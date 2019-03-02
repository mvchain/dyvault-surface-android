package com.mvc.topay.and.topay_android.constract

import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.base.IBaseActivity
import com.mvc.topay.and.topay_android.base.IBaseModel
import com.mvc.topay.and.topay_android.bean.MsgBean
import io.reactivex.Observable

interface IMsgContrast {
    abstract class MsgPresenter : BasePresenter<MsgModel, MsgView>() {
        abstract fun getMessage(pageSize: Int, timestamp: Long)
    }

    interface MsgModel : IBaseModel {
        fun getMessage(pageSize: Int, timestamp: Long): Observable<MsgBean>
    }

    interface MsgView : IBaseActivity {
        fun msgSuccess(msgBean: List<MsgBean.DataBean>)
        fun msgError(msg: String)
    }
}