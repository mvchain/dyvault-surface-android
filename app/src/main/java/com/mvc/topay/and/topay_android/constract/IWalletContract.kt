package com.mvc.topay.and.topay_android.constract

import com.mvc.topay.and.topay_android.base.AssetListBean
import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.base.IBaseActivity
import com.mvc.topay.and.topay_android.base.IBaseModel
import io.reactivex.Observable

interface IWalletContract {
    abstract class WalletPresenter : BasePresenter<WalletModel, WalletView>() {
        abstract fun getAllAssets()
        
    }

    interface WalletModel : IBaseModel {
        fun getAllAssets() :Observable<AssetListBean>
    }

    interface WalletView : IBaseActivity {
        fun assetsSuccess(assetListBean: AssetListBean)
        fun assetsError()
    }
}