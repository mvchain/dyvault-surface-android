package com.mvc.topay.and.topay_android.model

import com.mvc.topay.and.topay_android.MyApplication
import com.mvc.topay.and.topay_android.api.ApiStore
import com.mvc.topay.and.topay_android.base.BaseModel
import com.mvc.topay.and.topay_android.bean.HttpUpdateBean
import com.mvc.topay.and.topay_android.bean.IDToTransferBean
import com.mvc.topay.and.topay_android.constract.ITransferContract
import com.mvc.topay.and.topay_android.utils.RetrofitUtils
import com.mvc.topay.and.topay_android.utils.RxHelper
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject

class TransferModel : BaseModel(), ITransferContract.TransferModel {
    override fun getTransFee(address: String): Observable<HttpUpdateBean> {
        return RetrofitUtils.client(ApiStore::class.java).getTransFee(MyApplication.token, address)
                .compose(RxHelper.rxSchedulerHelper())
                .map { updateBean -> updateBean }
    }

    override fun getDetail(tokenId: Int): Observable<IDToTransferBean> {
        return RetrofitUtils.client(ApiStore::class.java).getTransaction(MyApplication.token, tokenId)
                .compose(RxHelper.rxSchedulerHelper())
                .map { idToBean -> idToBean }
    }

    override fun sendTransferMsg(address: String, password: String, tokenId: Int, value: String): Observable<HttpUpdateBean> {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("address", address)
            jsonObject.put("password", password)
            jsonObject.put("tokenId", tokenId)
            jsonObject.put("value", value)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val body = RequestBody.create(MediaType.parse("text/html"), jsonObject.toString())
        return RetrofitUtils.client(ApiStore::class.java).sendTransferRequest(MyApplication.token, body)
                .compose(RxHelper.rxSchedulerHelper())
                .map { updateBean -> updateBean }
    }

    companion object {
        val instance: TransferModel
            get() = TransferModel()
    }

}