package com.mvc.topay.and.topay_android.constract

import com.mvc.topay.and.topay_android.base.*
import com.mvc.topay.and.topay_android.bean.MsgBean
import io.reactivex.Observable

interface IWalletContract {
    abstract class WalletPresenter : BasePresenter<WalletModel, WalletView>() {
        abstract fun getAllAssets()
        abstract fun getBalance()
        abstract fun getMsg(pagesize: Int,timestamp: Long)

    }

    interface WalletModel : IBaseModel {
        fun getAllAssets(): Observable<AssetLanguageBean>
        fun getBalance(): Observable<BalanceBean>
        /**
         *
         * @return
         */
        fun getMsg(pagesize: Int,timestamp: Long): Observable<MsgBean>
    }

    interface WalletView : IBaseActivity {
        fun assetsSuccess(assetListBean: AssetLanguageBean)
        fun balanceSuccess(balanceBean: BalanceBean)
        fun msgSuccess(msgBean: MsgBean)
        fun networkError()
    }
}