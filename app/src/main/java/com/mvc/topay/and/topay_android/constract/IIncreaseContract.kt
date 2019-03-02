package com.mvc.topay.and.topay_android.constract

import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.base.IBaseActivity
import com.mvc.topay.and.topay_android.base.IBaseModel
import com.mvc.topay.and.topay_android.bean.IncreaseBean

import io.reactivex.Observable


interface IIncreaseContract {
    abstract class IncreasePresenter : BasePresenter<IIncreaseModel, IIncreaseView>() {
        abstract fun getCurrencyAll()
        abstract fun getCurrencySerach(serach: String)
    }

    interface IIncreaseModel : IBaseModel {
        fun currencyAll(): Observable<List<IncreaseBean>>
        fun getCurrencySearchList(search: String): Observable<List<IncreaseBean>>
    }

    interface IIncreaseView : IBaseActivity {
        fun showCurrency(beanList: List<IncreaseBean>)
        fun showSearchCurrency(beanList: List<IncreaseBean>)
        fun showCurrencyNull()
        fun showSearchNull()
    }
}
