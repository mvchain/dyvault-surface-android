package com.mvc.topay.and.topay_android.bean

data class RegisterBean(
        var code: Int,
        var data: DataBean,
        var message: String
) {
    data class DataBean(
            var email: String,
            var refreshToken: String,
            var salt: String,
            var token: String,
            var userId: Int
    )
}