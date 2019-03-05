package com.mvc.topay.and.topay_android.bean

data class BalancnOnIdBean(
        var code: Int,
        var data: DataBean,
        var message: String
) {
    data class DataBean(
            var ratio: Double,
            var tokenId: Int,
            var tokenImage: String,
            var tokenName: String,
            var value: Double
    )
}