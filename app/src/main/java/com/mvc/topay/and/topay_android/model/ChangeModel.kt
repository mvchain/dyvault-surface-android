package com.mvc.topay.and.topay_android.model

import com.mvc.topay.and.topay_android.MyApplication
import com.mvc.topay.and.topay_android.api.ApiStore
import com.mvc.topay.and.topay_android.base.BaseModel
import com.mvc.topay.and.topay_android.bean.HttpDataBean
import com.mvc.topay.and.topay_android.bean.HttpUpdateBean
import com.mvc.topay.and.topay_android.constract.IChangeContract
import com.mvc.topay.and.topay_android.utils.RetrofitUtils
import com.mvc.topay.and.topay_android.utils.RxHelper
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject

class ChangeModel : BaseModel(), IChangeContract.ChangeModel {
    override fun verifyEmail(validCode: String): Observable<HttpDataBean> {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("validCode", validCode)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val validInfo = jsonObject.toString()
        val requestBody = RequestBody.create(MediaType.parse("text/html"), validInfo)
        return RetrofitUtils.client(ApiStore::class.java).verifyEmailCode(MyApplication.token, requestBody)
                .compose(RxHelper.rxSchedulerHelper())
                .map { dataBean -> dataBean }
    }

    override fun sendEmail(): Observable<HttpUpdateBean> {
        return RetrofitUtils.client(ApiStore::class.java).getEmailOnToken(MyApplication.token)
                .compose(RxHelper.rxSchedulerHelper())
                .map { updateBean -> updateBean }
    }

    companion object {
        val instance: ChangeModel
            get() = ChangeModel()
    }
}