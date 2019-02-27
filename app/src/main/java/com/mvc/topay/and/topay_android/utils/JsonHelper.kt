package com.mvc.topay.and.topay_android.utils

import com.google.gson.Gson

object JsonHelper {
    private val gson = Gson()

    fun jsonToString(obj: Any): String {
        return gson.toJson(obj)
    }

    fun stringToJson(json: String, clazz: Class<*>): Any {
        return gson.fromJson<Any>(json, clazz)
    }
}
