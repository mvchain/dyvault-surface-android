package com.mvc.topay.and.topay_android.utils

import android.content.Intent
import android.os.AsyncTask.execute
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.Utils
import com.mvc.topay.and.topay_android.MyApplication
import com.mvc.topay.and.topay_android.api.ApiStore
import com.mvc.topay.and.topay_android.bean.HttpDataBean
import com.mvc.topay.and.topay_android.common.Constant
import com.mvc.topay.and.topay_android.common.Constant.SP.REFRESH_TOKEN
import com.mvc.topay.and.topay_android.common.Constant.SP.TOKEN
import com.mvc.topay.and.topay_android.common.Constant.SP.USER_ID
import okhttp3.*

import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit

import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSession
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
                    .baseUrl("http://192.168.15.31:10086/")
                    .client(okhttpUtils).build()
        }
        private val okhttpUtils: OkHttpClient
            get() = OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val builder = chain.request().newBuilder()
                        builder.header("Accept-Language", SPUtils.getInstance().getString(Constant.LANGUAGE.DEFAULT_ACCEPT_LANGUAGE))
                        val request = builder.build()
                        val response = chain.proceed(request)
                        response
                    }
                    .authenticator { _, response ->
                        val body = RetrofitUtils.client(ApiStore::class.java).refreshToken(SPUtils.getInstance().getString(REFRESH_TOKEN)).execute().body()
                        if (body!!.code === 200) {
                            SPUtils.getInstance().put(TOKEN, body!!.data)
                            MyApplication.setTOKEN(body!!.data)
                        } else {
                            RxUtils.instance.unSubscribeAll()
                            ActivityUtils.getTopActivity().finish()
                            SPUtils.getInstance().remove(REFRESH_TOKEN)
                            SPUtils.getInstance().remove(TOKEN)
                            SPUtils.getInstance().remove(USER_ID)
                            val intent = Intent()
                            intent.action = "android.login"
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            Utils.getApp().startActivity(intent)
                        }
                        val builder = response.request().newBuilder()
                        builder.header("Authorization", SPUtils.getInstance().getString(TOKEN))
                        builder.header("Accept-Language", SPUtils.getInstance().getString(Constant.LANGUAGE.DEFAULT_ACCEPT_LANGUAGE))
                        builder.build()
                    }
                    .addInterceptor(HttpLoggingInterceptor
                    { message -> LogUtils.e("RetrofitUtils", message) }
                            .setLevel(HttpLoggingInterceptor.Level.BODY))
                    .sslSocketFactory(createSSLSocketFactory()!!)
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .build()

        //                    .authenticator((route, response) ->
//
//        {
//            if (body.getCode() == 200) {
//                SPUtils.getInstance().put(TOKEN, body.getData());
//                MyApplication.setTOKEN(body.getData());
////                RetrofitUtils.client(ApiStore.class).getPushTag(MyApplication.getTOKEN()).compose(RxHelper.rxSchedulerHelper())
////                        .subscribe(tagBean -> {
////                    if (tagBean.getCode() == 200 && tagBean.getData() != null) {
////                        SPUtils.getInstance().put(TAG_NAME, tagBean.getData());
////                        String[] tags = tagBean.getData().split(",");
////                        for (int i = 0; i < tags.length; i++) {
////                            if (i == 0) {
////                                JPushInterface.setTags(MyApplication.getAppContext(), Integer.parseInt(tags[i]), new HashSet<>(Arrays.asList(tags[i])));
////                            } else {
////                                JPushInterface.addTags(MyApplication.getAppContext(), Integer.parseInt(tags[i]), new HashSet<>(Arrays.asList(tags[i])));
////                            }
////                        }
////                    }
////                }, throwable -> {
////                    LogUtils.e("ParameterInterceptor", throwable.getMessage());
////                });
//            } else {
//                ActivityUtils.getTopActivity().finish();
//                SPUtils.getInstance().remove(REFRESH_TOKEN);
//                SPUtils.getInstance().remove(UPDATE_PASSWORD_TYPE);
//                SPUtils.getInstance().remove(TOKEN);
//                JPushInterface.deleteAlias(MyApplication.getAppContext().getApplicationContext(), SPUtils.getInstance().getInt(USER_ID));
//                SPUtils.getInstance().remove(USER_ID);
//                Intent intent = new Intent();
//                intent.setAction("android.login");
//                //                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                Utils.getApp().startActivity(intent);
//            }
//            Request.Builder builder = response . request ().newBuilder();
//            builder.header("Authorization", SPUtils.getInstance().getString(TOKEN));
//            builder.header("Accept-Language", SPUtils.getInstance().getString(Constant.LANGUAGE.DEFAULT_ACCEPT_LANGUAGE));
//            return builder.build();
//        })
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


//                builder.header("Accept-Language", SPUtils.getInstance().getString(Constant.LANGUAGE.DEFAULT_ACCEPT_LANGUAGE));
    //                .authenticator((route, response) -> {
    //                    HttpTokenBean body = RetrofitUtils.client(ApiStore.class).refreshToken(SPUtils.getInstance().getString(REFRESH_TOKEN)).execute().body();
    //                    if (body.getCode() == 200) {
    //                        SPUtils.getInstance().put(TOKEN, body.getData());
    //                        MyApplication.setTOKEN(body.getData());
    //                        RetrofitUtils.client(ApiStore.class).getPushTag(MyApplication.getTOKEN()).compose(RxHelper.rxSchedulerHelper())
    //                                .subscribe(tagBean -> {
    //                                    if (tagBean.getCode() == 200 && tagBean.getData() != null) {
    //                                        SPUtils.getInstance().put(TAG_NAME, tagBean.getData());
    //                                        String[] tags = tagBean.getData().split(",");
    //                                        for (int i = 0; i < tags.length; i++) {
    //                                            if (i == 0) {
    //                                                JPushInterface.setTags(MyApplication.getAppContext(), Integer.parseInt(tags[i]), new HashSet<>(Arrays.asList(tags[i])));
    //                                            } else {
    //                                                JPushInterface.addTags(MyApplication.getAppContext(), Integer.parseInt(tags[i]), new HashSet<>(Arrays.asList(tags[i])));
    //                                            }
    //                                        }
    //                                    }
    //                                }, throwable -> {
    //                                    LogUtils.e("ParameterInterceptor", throwable.getMessage());
    //                                });
    //                    } else {
    //                        ActivityUtils.getTopActivity().finish();
    //                        SPUtils.getInstance().remove(REFRESH_TOKEN);
    //                        SPUtils.getInstance().remove(UPDATE_PASSWORD_TYPE);
    //                        SPUtils.getInstance().remove(TOKEN);
    //                        JPushInterface.deleteAlias(MyApplication.getAppContext().getApplicationContext(), SPUtils.getInstance().getInt(USER_ID));
    //                        SPUtils.getInstance().remove(USER_ID);
    //                        Intent intent = new Intent();
    //                        intent.setAction("android.login");
    ////                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    //                        Utils.getApp().startActivity(intent);
    //                    }
    //                    Request.Builder builder = response.request().newBuilder();
    //                    builder.header("Authorization", SPUtils.getInstance().getString(TOKEN));
    //                    builder.header("Accept-Language", SPUtils.getInstance().getString(Constant.LANGUAGE.DEFAULT_ACCEPT_LANGUAGE));
    //                    return builder.build();
    //                })


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
