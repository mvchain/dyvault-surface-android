package com.mvc.topay.and.topay_android.api

import com.mvc.topay.and.topay_android.bean.VideoBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiStore {
    @Headers("sign:e36db3569ac77938ea700ed315817ec0"
            , "Authorization:8a817deda9d29b4cb23ab3a898f6e0ac"
            , "App-Agent:version=3.2.0,platform=android,app_store_name=zapp-wandoujia,software_version=6.0,models=Custom Phone - 6.0.0 - API 23 - 768x1280,package_name=com.weishang.qwapp,time=1489484134207")
    @GET("common/video-analysis")
    fun getVideoData(@Query("video_url") videoUrl: String): Observable<VideoBean>
}