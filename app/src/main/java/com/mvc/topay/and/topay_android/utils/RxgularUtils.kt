package com.mvc.topay.and.topay_android.utils

import java.util.regex.Pattern

object RxgularUtils {
    private val eth = "^(0x)?[0-9a-fA-F]{40}$"
    private val btc = "^[123mn][a-zA-Z1-9]{24,34}$"

    fun isETH(content: String): Boolean {
        return Pattern.compile(eth).matcher(content).matches()
    }

    fun isBTC(content: String): Boolean {
        return Pattern.compile(btc).matcher(content).matches()
    }
}
