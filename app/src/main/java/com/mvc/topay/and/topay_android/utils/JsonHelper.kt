package com.mvc.topay.and.topay_android.utils

import com.google.gson.Gson

object JsonHelper {
    private val sGson = Gson()

    fun jsonToString(obj: Any): String {
        return sGson.toJson(obj)
    }

    fun stringToJson(json: String, clazz: Class<*>): Any {
        return sGson.fromJson<Any>(json, clazz)
    }
}
