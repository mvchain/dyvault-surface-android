package com.mvc.topay.and.topay_android.constract

import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.base.IBaseActivity
import com.mvc.topay.and.topay_android.base.IBaseModel
import com.mvc.topay.and.topay_android.bean.DetailBean
import io.reactivex.Observable

interface IDetailContract {
    abstract class DetailPresenter : BasePresenter<DetailModel, DetailView>() {
        abstract fun getDetailOnID(id: Int)
    }

    interface DetailModel : IBaseModel {
        fun getDetailOnID(id: Int): Observable<DetailBean>
    }

    interface DetailView : IBaseActivity {
        fun detailSuccess(detailBean: DetailBean.DataBean)
        fun detailFailed(msg: String)
    }
}