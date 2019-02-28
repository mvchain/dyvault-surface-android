package com.mvc.topay.and.topay_android.api

import com.mvc.topay.and.topay_android.bean.HttpDataBean
import com.mvc.topay.and.topay_android.bean.HttpUpdateBean
import com.mvc.topay.and.topay_android.bean.RegisterBean
import com.mvc.topay.and.topay_android.common.HttpUrl
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiStore {
    /**
     * user login
     */
    @POST(HttpUrl.USER_LOGIN)
    fun login(@Body body: RequestBody): Observable<RegisterBean>

    /**
     * user register
     */
    @POST(HttpUrl.USER_REGISTER)
    fun register(@Body body: RequestBody): Observable<RegisterBean>

    /**
     *Verify registration information
     */
    @POST(HttpUrl.USER_VERIFICATION)
    fun verifyUser(@Body body: RequestBody): Observable<HttpDataBean>

    /**
     * refresh Token
     */
    @POST(HttpUrl.USER_REFRESH)
    fun refreshToken(@Header("Authorization") refreshToken: String): Call<HttpDataBean>

    /**
     * Verify the mailbox before changing the password
     */
    @POST(HttpUrl.USER_RESET)
    fun verificationEmailResetPassword(@Header("Authorization") token: String, @Body body: RequestBody): Observable<HttpDataBean>

    /**
     * Send the verification code
     */
    @GET(HttpUrl.USER_SEND_EMAIL)
    fun sendCode(@Header("Authorization") token: String, @Query("email") email: String): Observable<HttpUpdateBean>

    /**
     * forget password  change password
     */
    @PUT(HttpUrl.USER_FORGET)
    fun resetPassword(@Header("Authorization") token: String, @Body body: RequestBody): Observable<HttpUpdateBean>
}

