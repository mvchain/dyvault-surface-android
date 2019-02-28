package com.mvc.topay.and.topay_android.model

import com.mvc.topay.and.topay_android.api.ApiStore
import com.mvc.topay.and.topay_android.base.BaseModel
import com.mvc.topay.and.topay_android.bean.RegisterBean
import com.mvc.topay.and.topay_android.constract.ISetPasswordContract
import com.mvc.topay.and.topay_android.utils.RetrofitUtils
import com.mvc.topay.and.topay_android.utils.RxHelper
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject

class SetPasswordModel : BaseModel(), ISetPasswordContract.SetPasswordModel {
    override fun setPassword(email: String, inviteCode: String, nickname: String, password: String, token: String, transactionPassword: String): Observable<RegisterBean> {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("email", email)
            jsonObject.put("inviteCode", inviteCode)
            jsonObject.put("nickname", nickname)
            jsonObject.put("password", password)
            jsonObject.put("token", token)
            jsonObject.put("transactionPassword", transactionPassword)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val validInfo = jsonObject.toString()
        val requestBody = RequestBody.create(MediaType.parse("text/html"), validInfo)
        return RetrofitUtils.client(ApiStore::class.java).register(requestBody)
                .compose(RxHelper.rxSchedulerHelper())
                .map { registerBean -> registerBean }
    }

    companion object {
        val instance: SetPasswordModel
            get() = SetPasswordModel()
    }

}