package com.mvc.topay.and.topay_android.constract

import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.base.IBaseActivity
import com.mvc.topay.and.topay_android.base.IBaseFragment
import com.mvc.topay.and.topay_android.base.IBaseModel
import com.mvc.topay.and.topay_android.bean.ChannelBean
import io.reactivex.Observable
import java.util.ArrayList

interface IBuyingContract {
    abstract class BuyingPresenter : BasePresenter<BuyingModel, BuyingView>() {
        abstract fun getChannelList(id: Int, pageSize: Int)
    }

    interface BuyingModel : IBaseModel {
        fun getChannelList(id: Int, pageSize: Int): Observable<ChannelBean>
    }

    interface BuyingView : IBaseFragment {
        fun channelSuccess(channelBean: ArrayList<ChannelBean.DataBean>)
        fun channelFailed(msg: String)
    }
}