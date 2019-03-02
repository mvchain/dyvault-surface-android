package com.mvc.topay.and.topay_android.model

import com.mvc.topay.and.topay_android.MyApplication
import com.mvc.topay.and.topay_android.api.ApiStore
import com.mvc.topay.and.topay_android.base.BaseModel
import com.mvc.topay.and.topay_android.bean.HttpDataBean
import com.mvc.topay.and.topay_android.bean.HttpUpdateBean
import com.mvc.topay.and.topay_android.constract.ISetEmailContract
import com.mvc.topay.and.topay_android.utils.RetrofitUtils
import com.mvc.topay.and.topay_android.utils.RxHelper
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject

class SetEmailModel : BaseModel(), ISetEmailContract.SetEmailModel {
    override fun verifyEmail(email: String, token: String, validCode: String): Observable<HttpDataBean> {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("email", email)
            jsonObject.put("token", token)
            jsonObject.put("validCode", validCode)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val validInfo = jsonObject.toString()
        val requestBody = RequestBody.create(MediaType.parse("text/html"), validInfo)
        return RetrofitUtils.client(ApiStore::class.java).updateEmail(MyApplication.token, requestBody)
                .compose(RxHelper.rxSchedulerHelper())
                .map { dataBean -> dataBean }
    }

    override fun sendEmail(email: String): Observable<HttpUpdateBean> {
        return RetrofitUtils.client(ApiStore::class.java).sendCode(MyApplication.token, email)
                .compose(RxHelper.rxSchedulerHelper())
                .map { update -> update }
    }

    companion object {
        val instance: SetEmailModel
            get() = SetEmailModel()
    }
}