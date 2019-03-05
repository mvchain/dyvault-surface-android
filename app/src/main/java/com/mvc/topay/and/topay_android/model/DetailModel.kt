package com.mvc.topay.and.topay_android.model

import com.mvc.topay.and.topay_android.MyApplication
import com.mvc.topay.and.topay_android.api.ApiStore
import com.mvc.topay.and.topay_android.base.BaseModel
import com.mvc.topay.and.topay_android.bean.DetailBean
import com.mvc.topay.and.topay_android.constract.IDetailContract
import com.mvc.topay.and.topay_android.utils.RetrofitUtils
import com.mvc.topay.and.topay_android.utils.RxHelper
import io.reactivex.Observable

class DetailModel : BaseModel(), IDetailContract.DetailModel {
    override fun getDetailOnID(id: Int): Observable<DetailBean> {
        return RetrofitUtils.client(ApiStore::class.java).getDetailOnID(MyApplication.token, id)
                .compose(RxHelper.rxSchedulerHelper())
                .map { detailBean -> detailBean }
    }

    companion object {
        val instance: DetailModel
            get() = DetailModel()
    }
}