package com.mvc.topay.and.topay_android.constract

import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.base.IBaseActivity
import com.mvc.topay.and.topay_android.base.IBaseFragment
import com.mvc.topay.and.topay_android.base.IBaseModel
import com.mvc.topay.and.topay_android.bean.BuyDetailBean
import com.mvc.topay.and.topay_android.bean.ChannelBean
import com.mvc.topay.and.topay_android.bean.HttpUpdateBean
import io.reactivex.Observable
import java.util.ArrayList

interface IBuyingDetailContract {
    abstract class BuyingDetailPresenter : BasePresenter<BuyingDetailModel, BuyingDetailView>() {
        abstract fun getBuyingDetail(id: Int)
        abstract fun putBuyingDetail(id: Int)
    }

    interface BuyingDetailModel : IBaseModel {
        fun getBuyingDetail(id: Int): Observable<BuyDetailBean>
        fun putBuyingDetail(id: Int): Observable<HttpUpdateBean>
    }

    interface BuyingDetailView : IBaseFragment {
        fun buyingDetailSuccess(buydetailBean: BuyDetailBean.DataBean)
        fun confirmDetailSuccess(httpBean: HttpUpdateBean)
        fun buyingDetailFailed(msg: String)
        fun confirmDetailFailed(msg: String)
    }
}