package com.mvc.topay.and.topay_android.bean

data class IncreaseBean(
        var currencyId: Int,
        var isAdd: Boolean,
        var resId: String,
        var title: String,
        var zhContent: String,
        var enContent: String,
        var isVisible: Boolean
)
