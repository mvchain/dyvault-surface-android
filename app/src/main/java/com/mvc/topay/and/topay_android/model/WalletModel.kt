package com.mvc.topay.and.topay_android.model

import com.blankj.utilcode.util.SPUtils
import com.mvc.topay.and.topay_android.MyApplication
import com.mvc.topay.and.topay_android.api.ApiStore
import com.mvc.topay.and.topay_android.base.AssetListBean
import com.mvc.topay.and.topay_android.base.BaseModel
import com.mvc.topay.and.topay_android.base.ExchangeRateBean
import com.mvc.topay.and.topay_android.common.Constant.SP.CURRENCY_LIST
import com.mvc.topay.and.topay_android.common.Constant.SP.DEFAULT_RATE
import com.mvc.topay.and.topay_android.common.Constant.SP.DEFAULT_SYMBOL
import com.mvc.topay.and.topay_android.common.Constant.SP.RATE_LIST
import com.mvc.topay.and.topay_android.common.Constant.SP.SET_RATE
import com.mvc.topay.and.topay_android.constract.IWalletContract
import com.mvc.topay.and.topay_android.utils.JsonHelper
import com.mvc.topay.and.topay_android.utils.RetrofitUtils
import com.mvc.topay.and.topay_android.utils.RxHelper
import io.reactivex.Observable

class WalletModel : BaseModel(), IWalletContract.WalletModel {

    override fun getAllAssets(): Observable<AssetListBean> {
        return RetrofitUtils.client(ApiStore::class.java).getExchangeRate(MyApplication.token)
                .compose(RxHelper.rxSchedulerHelper())
                .flatMap { exchangeRateBean ->
                    //Check if there is a default exchange rate setting, if not, save one. If you have one, ignore it.
                    //Save the total exchange rate list for use as a POPWindow display
                    var dataBean = exchangeRateBean.data[0]
                    val default_rate = SPUtils.getInstance().getString(SET_RATE) //Get the default exchange rate
                    val defaule_symbol = SPUtils.getInstance().getString(DEFAULT_SYMBOL)//Get the default unit
                    SPUtils.getInstance().put(DEFAULT_RATE, JsonHelper.jsonToString(dataBean))
                    if (default_rate === "") {
                        SPUtils.getInstance().put(SET_RATE, JsonHelper.jsonToString(dataBean))
                    } else {
                        for (bean in exchangeRateBean.data) {
                            val setDataBean = JsonHelper.stringToJson(default_rate, ExchangeRateBean.DataBean::class.java) as ExchangeRateBean.DataBean
                            if (bean.name === setDataBean.name) {
                                SPUtils.getInstance().put(SET_RATE, JsonHelper.jsonToString(setDataBean))
                                break
                            }
                        }
                    }
                    //Save the default currency symbol
                    if (defaule_symbol == "") {
                        val symbol = dataBean.name
                        val newSymbol = symbol.substring(0, 1)
                        SPUtils.getInstance().put(DEFAULT_SYMBOL, "$newSymbol ")
                    }
                    SPUtils.getInstance().put(RATE_LIST, JsonHelper.jsonToString(exchangeRateBean))
                    RetrofitUtils.client(ApiStore::class.java).getCurrencyList(MyApplication.token).compose(RxHelper.rxSchedulerHelper())
                }.flatMap { currencyBean ->
                    //Save all tokens
                    SPUtils.getInstance().put(CURRENCY_LIST, JsonHelper.jsonToString(currencyBean))
                    RetrofitUtils.client(ApiStore::class.java).getAssetsList(MyApplication.token).compose(RxHelper.rxSchedulerHelper())
                }.map { asslist -> asslist }
    }

    companion object {
        val instance: WalletModel
            get() = WalletModel()
    }
}