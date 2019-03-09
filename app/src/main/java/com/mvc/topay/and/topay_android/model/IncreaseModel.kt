package com.mvc.topay.and.topay_android.model

import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.google.gson.Gson
import com.mvc.topay.and.topay_android.MyApplication
import com.mvc.topay.and.topay_android.api.ApiStore
import com.mvc.topay.and.topay_android.base.AssetLanguageBean
import com.mvc.topay.and.topay_android.base.AssetListBean
import com.mvc.topay.and.topay_android.base.BaseModel
import com.mvc.topay.and.topay_android.bean.IncreaseBean
import com.mvc.topay.and.topay_android.common.Constant.SP.ASSETS_LIST
import com.mvc.topay.and.topay_android.common.Constant.SP.CURRENCY_LIST
import com.mvc.topay.and.topay_android.constract.IIncreaseContract
import com.mvc.topay.and.topay_android.utils.JsonHelper
import com.mvc.topay.and.topay_android.utils.RetrofitUtils
import com.mvc.topay.and.topay_android.utils.RxHelper
import io.reactivex.Observable
import java.util.ArrayList

class IncreaseModel : BaseModel(), IIncreaseContract.IIncreaseModel {
    private var assetListBean: AssetLanguageBean = JsonHelper.stringToJson(SPUtils.getInstance().getString(ASSETS_LIST), AssetLanguageBean::class.java) as AssetLanguageBean
    private val mList = ArrayList<IncreaseBean>()
    private val mSearchList = ArrayList<IncreaseBean>()


    override fun currencyAll(): Observable<List<IncreaseBean>> {
        return RetrofitUtils.client(ApiStore::class.java).getCurrencyList(MyApplication.token)
                .compose(RxHelper.rxSchedulerHelper())
                .flatMap { currencyBean ->
                    SPUtils.getInstance().put(CURRENCY_LIST, JsonHelper.jsonToString(currencyBean))
                    val currdata = currencyBean.data
                    val assetBean = assetListBean.data
                    for (i in currdata.indices) {
                        if(currdata[i].visible !== 0){
                            val increaseBean = IncreaseBean(currdata[i].tokenId
                                    , false
                                    , currdata[i].tokenImage
                                    , currdata[i].tokenName
                                    , currdata[i].tokenCnName
                                    , currdata[i].tokenEnName
                                    , i >= 1)
                            for (j in assetBean.indices) {
                                if (currdata[i].tokenId === assetBean[j].tokenId) {
                                    increaseBean.isAdd = false
                                    break
                                } else {
                                    increaseBean.isAdd = true
                                }
                            }
                            mList.add(increaseBean)
                        }
                    }
                    Observable.just(mList)
                }
    }

    override fun getCurrencySearchList(search: String): Observable<List<IncreaseBean>> {
        mSearchList.clear()
        if (search != "") {
            for (i in mList.indices) {
                val increaseBean = mList[i]
                if (increaseBean.zhContent.toLowerCase().contains(search.toLowerCase())
                        || increaseBean.title.contains(search)
                        || increaseBean.enContent.toLowerCase().contains(search.toLowerCase())) {
                    mSearchList.add(increaseBean)
                }
            }
        }
        return Observable.just(mSearchList).map { increaseBeans -> mSearchList }
    }
    companion object {
        val instance: IncreaseModel
              get() = IncreaseModel()
    }

}