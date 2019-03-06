package com.mvc.topay.and.topay_android.model

import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.LogUtils
import com.mvc.topay.and.topay_android.MyApplication
import com.mvc.topay.and.topay_android.api.ApiStore
import com.mvc.topay.and.topay_android.base.BaseModel
import com.mvc.topay.and.topay_android.bean.HttpUpdateBean
import com.mvc.topay.and.topay_android.bean.RegisterBean
import com.mvc.topay.and.topay_android.constract.ILoginContract
import com.mvc.topay.and.topay_android.utils.RetrofitUtils
import com.mvc.topay.and.topay_android.utils.RxHelper
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject

class LoginModel : BaseModel(), ILoginContract.LoginModel {
    override fun sendCode(email: String): Observable<HttpUpdateBean> {
        return RetrofitUtils.client(ApiStore::class.java).sendCode(email)
                .compose(RxHelper.rxSchedulerHelper())
                .map { updateBean -> updateBean }
    }

    override fun login(imageToken: String, password: String, email: String, validCode: String): Observable<RegisterBean> {
        return RetrofitUtils.client(ApiStore::class.java).getUserSalt(MyApplication.token, email)
                .compose(RxHelper.rxSchedulerHelper())
                .flatMap {
                    httpData->
                    val jsonObject = JSONObject()
                    try {
                        jsonObject.put("imageToken", "")
                        jsonObject.put("password", EncryptUtils.encryptMD5ToString(httpData.data + EncryptUtils.encryptMD5ToString(password)))
                        jsonObject.put("username", email)
                        jsonObject.put("validCode", validCode)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                    val validInfo = jsonObject.toString()
                    val requestBody = RequestBody.create(MediaType.parse("text/html"), validInfo)
                    RetrofitUtils.client(ApiStore::class.java).login(requestBody).compose(RxHelper.rxSchedulerHelper())
                }.map { loginBean -> loginBean }

    }

    companion object {
        val instance: LoginModel
            get() = LoginModel()
    }

}