package com.mvc.topay.and.topay_android.constract

import com.mvc.topay.and.topay_android.base.*
import io.reactivex.Observable

interface IWalletContract {
    abstract class WalletPresenter : BasePresenter<WalletModel, WalletView>() {
        abstract fun getAllAssets()
        abstract fun getBalance()

    }

    interface WalletModel : IBaseModel {
        fun getAllAssets(): Observable<AssetListBean>
        fun getBalance(): Observable<BalanceBean>
    }

    interface WalletView : IBaseActivity {
        fun assetsSuccess(assetListBean: AssetListBean)
        fun balanceSuccess(balanceBean: BalanceBean)
        fun networkError()
    }
}