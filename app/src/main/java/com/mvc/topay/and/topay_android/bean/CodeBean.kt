package com.mvc.topay.and.topay_android.bean

data class CodeBean(
        var code: Int,
        var data: DataBean,
        var message: String
) {
    data class DataBean(
            var result: String,
            var status: Int,
            var uid: String
    )
}