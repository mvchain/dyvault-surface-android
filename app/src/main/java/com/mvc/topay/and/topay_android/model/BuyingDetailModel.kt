package com.mvc.topay.and.topay_android.model

import com.mvc.topay.and.topay_android.MyApplication
import com.mvc.topay.and.topay_android.api.ApiStore
import com.mvc.topay.and.topay_android.base.BaseModel
import com.mvc.topay.and.topay_android.bean.BuyDetailBean
import com.mvc.topay.and.topay_android.bean.HttpUpdateBean
import com.mvc.topay.and.topay_android.constract.IBuyingDetailContract
import com.mvc.topay.and.topay_android.utils.RetrofitUtils
import com.mvc.topay.and.topay_android.utils.RxHelper
import io.reactivex.Observable

class BuyingDetailModel : BaseModel(), IBuyingDetailContract.BuyingDetailModel {
    override fun putBuyingDetail(id: Int): Observable<HttpUpdateBean> {
        return RetrofitUtils.client(ApiStore::class.java).putBusinessDetail(MyApplication.token, id)
                .compose(RxHelper.rxSchedulerHelper())
                .map { httpBean -> httpBean }
    }

    override fun getBuyingDetail(id: Int): Observable<BuyDetailBean> {
        return RetrofitUtils.client(ApiStore::class.java).getBusinessDetail(MyApplication.token, id)
                .compose(RxHelper.rxSchedulerHelper())
                .map { buyBean -> buyBean }
    }

    companion object {
        val instance: BuyingDetailModel
            get() = BuyingDetailModel()
    }
}