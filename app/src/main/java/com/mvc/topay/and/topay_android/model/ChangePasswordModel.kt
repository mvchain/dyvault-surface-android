package com.mvc.topay.and.topay_android.model

import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.SPUtils
import com.mvc.topay.and.topay_android.MyApplication
import com.mvc.topay.and.topay_android.api.ApiStore
import com.mvc.topay.and.topay_android.base.BaseModel
import com.mvc.topay.and.topay_android.bean.HttpUpdateBean
import com.mvc.topay.and.topay_android.common.Constant.SP.USER_SALT
import com.mvc.topay.and.topay_android.constract.IChangePasswordContract
import com.mvc.topay.and.topay_android.utils.RetrofitUtils
import com.mvc.topay.and.topay_android.utils.RxHelper
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject

class ChangePasswordModel : BaseModel(), IChangePasswordContract.ChangePasswordModel {
    override fun updateLoginPasssword(newPassword: String, oldPassword: String): Observable<HttpUpdateBean> {
        val salt = SPUtils.getInstance().getString(USER_SALT)
        val jsonObject = JSONObject()
        try {
            jsonObject.put("newPassword", EncryptUtils.encryptMD5ToString(salt+EncryptUtils.encryptMD5ToString(newPassword)))
            jsonObject.put("password", EncryptUtils.encryptMD5ToString(salt+EncryptUtils.encryptMD5ToString(oldPassword)))
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val validInfo = jsonObject.toString()
        val requestBody = RequestBody.create(MediaType.parse("text/html"), validInfo)
        return RetrofitUtils.client(ApiStore::class.java).updatePassword(MyApplication.token,requestBody)
                .compose(RxHelper.rxSchedulerHelper())
                .map { update->update }
    }

    override fun updatePaypassword(newPassword: String, oldPassword: String): Observable<HttpUpdateBean> {
        val salt = SPUtils.getInstance().getString(USER_SALT)
        val jsonObject = JSONObject()
        try {
            jsonObject.put("newPassword", EncryptUtils.encryptMD5ToString(salt+EncryptUtils.encryptMD5ToString(newPassword)))
            jsonObject.put("password", EncryptUtils.encryptMD5ToString(salt+EncryptUtils.encryptMD5ToString(oldPassword)))
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val validInfo = jsonObject.toString()
        val requestBody = RequestBody.create(MediaType.parse("text/html"), validInfo)
        return RetrofitUtils.client(ApiStore::class.java).updatePayPassword(MyApplication.token,requestBody)
                .compose(RxHelper.rxSchedulerHelper())
                .map { update->update }
    }

    companion object {
        val instance: ChangePasswordModel
            get() = ChangePasswordModel()
    }
}