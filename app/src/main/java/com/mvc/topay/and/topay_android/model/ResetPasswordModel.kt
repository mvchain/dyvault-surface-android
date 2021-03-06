package com.mvc.topay.and.topay_android.model

import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.SPUtils
import com.mvc.topay.and.topay_android.MyApplication
import com.mvc.topay.and.topay_android.api.ApiStore
import com.mvc.topay.and.topay_android.base.BaseModel
import com.mvc.topay.and.topay_android.bean.HttpUpdateBean
import com.mvc.topay.and.topay_android.constract.IResetPasswordContract
import com.mvc.topay.and.topay_android.utils.RetrofitUtils
import com.mvc.topay.and.topay_android.utils.RxHelper
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject

class ResetPasswordModel : BaseModel(), IResetPasswordContract.ResetPasswordModel {
    override fun resetPassword(email:String,salt: String, password: String, token: String, type: Int): Observable<HttpUpdateBean> {
        return Observable.just(salt).flatMap { salt ->
            if (salt == "") {
                RetrofitUtils.client(ApiStore::class.java).getUserSalt(MyApplication.token, email).compose(RxHelper.rxSchedulerHelper()).flatMap { httpData ->
                    Observable.just(httpData.data)
                }
            } else {
                Observable.just("")
            }
        }.flatMap {
            salt->
            val jsonObject = JSONObject()
            var md5Pass = EncryptUtils.encryptMD5ToString(salt+ EncryptUtils.encryptMD5ToString(password))
            try {
                jsonObject.put("password", md5Pass)
                jsonObject.put("token", token)
                jsonObject.put("type", type)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            val validInfo = jsonObject.toString()
            val requestBody = RequestBody.create(MediaType.parse("text/html"), validInfo)
            RetrofitUtils.client(ApiStore::class.java).resetPassword(MyApplication.token,requestBody).compose(RxHelper.rxSchedulerHelper())
        }
    }

    companion object {
        val instance: ResetPasswordModel
            get() = ResetPasswordModel()
    }
}