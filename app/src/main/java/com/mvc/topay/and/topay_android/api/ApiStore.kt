package com.mvc.topay.and.topay_android.api

import com.mvc.topay.and.topay_android.bean.HttpDataBean
import com.mvc.topay.and.topay_android.bean.RegisterBean
import com.mvc.topay.and.topay_android.common.HttpUrl
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiStore {
    @POST(HttpUrl.USER_REGISTER)
    fun register(@Body body:RequestBody): Observable<RegisterBean>

    @POST(HttpUrl.USER_VERIFICATION)
    fun verifyUser(@Body body:RequestBody): Observable<HttpDataBean>

}