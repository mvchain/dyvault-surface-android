package com.mvc.topay.and.topay_android.model

import com.mvc.topay.and.topay_android.MyApplication
import com.mvc.topay.and.topay_android.api.ApiStore
import com.mvc.topay.and.topay_android.base.BaseModel
import com.mvc.topay.and.topay_android.bean.HttpDataBean
import com.mvc.topay.and.topay_android.bean.HttpUpdateBean
import com.mvc.topay.and.topay_android.constract.IVerificationContract
import com.mvc.topay.and.topay_android.utils.RetrofitUtils
import com.mvc.topay.and.topay_android.utils.RxHelper
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject

class VerificationModel : BaseModel(), IVerificationContract.VerificationModel {
    override fun resetPassword(email: String, value: String): Observable<HttpDataBean> {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("email", email)
            jsonObject.put("value", value)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val validInfo = jsonObject.toString()
        val requestBody = RequestBody.create(MediaType.parse("text/html"), validInfo)
        return RetrofitUtils.client(ApiStore::class.java).verificationEmailResetPassword(MyApplication.token, requestBody)
                .compose(RxHelper.rxSchedulerHelper())
                .map { dateBean -> dateBean }
    }

    override fun sendCode(email: String): Observable<HttpUpdateBean> {
        return RetrofitUtils.client(ApiStore::class.java).sendCode(email)
                .compose(RxHelper.rxSchedulerHelper())
                .map { updateBean -> updateBean }
    }

    companion object {
        val instance: VerificationModel
            get() = VerificationModel()
    }
}