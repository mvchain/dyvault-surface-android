package com.mvc.topay.and.topay_android.utils

import android.content.Intent
import cn.jpush.android.api.JPushInterface
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.Utils
import com.mvc.topay.and.topay_android.MyApplication
import com.mvc.topay.and.topay_android.activity.SelectLoginActivity
import com.mvc.topay.and.topay_android.api.ApiStore
import com.mvc.topay.and.topay_android.common.Constant
import com.mvc.topay.and.topay_android.common.Constant.SP.REFRESH_TOKEN
import com.mvc.topay.and.topay_android.common.Constant.SP.TOKEN
import com.mvc.topay.and.topay_android.common.Constant.SP.USER_BUSINESSES
import com.mvc.topay.and.topay_android.common.Constant.SP.USER_EMAIL
import com.mvc.topay.and.topay_android.common.Constant.SP.USER_ID
import com.mvc.topay.and.topay_android.common.Constant.SP.USER_INFO
import com.mvc.topay.and.topay_android.common.Constant.SP.USER_PROXY
import com.mvc.topay.and.topay_android.common.Constant.SP.USER_RESETPASSWORD_TYPE
import com.mvc.topay.and.topay_android.common.Constant.SP.USER_SALT
import okhttp3.*

import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit

import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitUtils {
    companion object {
        private val instance: Retrofit by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
//                    http://47.110.144.216/api/app/       http://192.168.15.21:10086/   http://54.238.152.0/api/app/
                    .baseUrl("http://47.110.144.216/api/app/")
                    .client(okhttpUtils).build()
        }
        private val okhttpUtils: OkHttpClient
            get() = OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val builder = chain.request().newBuilder()
                        builder.header("Accept-Language", SPUtils.getInstance().getString(Constant.LANGUAGE.DEFAULT_ACCEPT_LANGUAGE))
                        builder.addHeader("versionCode", MyApplication.getAppVersionCode().toString())
                        val request = builder.build()
                        val response = chain.proceed(request)
                        response
                    }
                    .addInterceptor(HttpLoggingInterceptor
                    { message ->
                        LogUtils.e("RetrofitUtils", message)
                    }
                            .setLevel(HttpLoggingInterceptor.Level.BODY))
                    .authenticator { _, response ->
                        val body = RetrofitUtils.client(ApiStore::class.java).refreshToken(SPUtils.getInstance().getString(REFRESH_TOKEN)).execute().body()
                        if (body!!.code == 200) {
                            SPUtils.getInstance().put(TOKEN, body!!.data)
                            MyApplication.token = body!!.data
                            val builder = response.request().newBuilder()
                            builder.header("Authorization", SPUtils.getInstance().getString(TOKEN))
                            builder.addHeader("Accept-Language", SPUtils.getInstance().getString(Constant.LANGUAGE.DEFAULT_ACCEPT_LANGUAGE))
                            builder.addHeader("versionCode", MyApplication.getAppVersionCode().toString())
                            builder.build()
                        } else {
//                            LogUtils.e("token 失效")
                            SPUtils.getInstance().remove(REFRESH_TOKEN)
                            SPUtils.getInstance().remove(TOKEN)
                            SPUtils.getInstance().remove(USER_ID)
                            SPUtils.getInstance().remove(USER_RESETPASSWORD_TYPE)
                            SPUtils.getInstance().remove(USER_EMAIL)
                            SPUtils.getInstance().remove(USER_SALT)
                            SPUtils.getInstance().remove(USER_INFO)
                            SPUtils.getInstance().remove(USER_BUSINESSES)
                            SPUtils.getInstance().remove(USER_PROXY)
                            if(ActivityUtils.getTopActivity() !is SelectLoginActivity){
                                val intent = Intent()
                                JPushInterface.deleteAlias(MyApplication.application, SPUtils.getInstance().getInt(USER_ID))
                                intent.action = "${MyApplication.application!!.packageName}.android.login"
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                Utils.getApp().startActivity(intent)
                            }
                            null
                        }
                    }
                    .sslSocketFactory(createSSLSocketFactory()!!)
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .build()

        private fun createSSLSocketFactory(): SSLSocketFactory? {
            var ssfFactory: SSLSocketFactory? = null
            try {
                val sc = SSLContext.getInstance("TLS")
                sc.init(null, arrayOf<TrustManager>(TrustAllCerts()), SecureRandom())
                ssfFactory = sc.socketFactory
            } catch (e: Exception) {
            }
            return ssfFactory
        }

        fun <T> client(clazz: Class<T>): T {
            return instance.create(clazz)
        }
    }


    private class TrustAllCerts : X509TrustManager {
        @Throws(CertificateException::class)
        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
        }

        @Throws(CertificateException::class)
        override fun checkClientTrusted(x509Certificates: Array<X509Certificate>, s: String) {

        }

        override fun getAcceptedIssuers(): Array<X509Certificate?> {
            return arrayOfNulls(0)
        }
    }
}
