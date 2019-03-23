package com.mvc.topay.and.topay_android.model

import com.mvc.topay.and.topay_android.MyApplication
import com.mvc.topay.and.topay_android.api.ApiStore
import com.mvc.topay.and.topay_android.base.BaseModel
import com.mvc.topay.and.topay_android.bean.ChannelBean
import com.mvc.topay.and.topay_android.constract.IBuyingContract
import com.mvc.topay.and.topay_android.utils.RetrofitUtils
import com.mvc.topay.and.topay_android.utils.RxHelper
import io.reactivex.Observable

class BuyingModel : BaseModel(), IBuyingContract.BuyingModel {
    override fun getChannelList(id: Int, pageSize: Int, status: Int): Observable<ChannelBean> {
        return RetrofitUtils.client(ApiStore::class.java)
                .getBusinessList(MyApplication.token, id, pageSize, status)
                .compose(RxHelper.rxSchedulerHelper())
                .map { channel -> channel }
    }


    companion object {
        val instance: BuyingModel
            get() = BuyingModel()
    }
}