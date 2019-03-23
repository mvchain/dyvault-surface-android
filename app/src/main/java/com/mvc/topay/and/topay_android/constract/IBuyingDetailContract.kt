package com.mvc.topay.and.topay_android.constract

import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.base.IBaseActivity
import com.mvc.topay.and.topay_android.base.IBaseFragment
import com.mvc.topay.and.topay_android.base.IBaseModel
import com.mvc.topay.and.topay_android.bean.BuyDetailBean
import com.mvc.topay.and.topay_android.bean.ChannelBean
import io.reactivex.Observable
import java.util.ArrayList

interface IBuyingDetailContract {
    abstract class BuyingDetailPresenter : BasePresenter<BuyingDetailModel, BuyingDetailView>() {
        abstract fun getBuyingDetail(id: Int)
    }

    interface BuyingDetailModel : IBaseModel {
        fun getBuyingDetail(id: Int): Observable<BuyDetailBean>
    }

    interface BuyingDetailView : IBaseFragment {
        fun buyingDetailSuccess(buydetailBean: BuyDetailBean.DataBean)
        fun buyingDetailFailed(msg: String)
    }
}