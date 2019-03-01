package com.mvc.topay.and.topay_android.utils


import com.blankj.utilcode.util.SPUtils
import com.mvc.topay.and.topay_android.base.ExchangeRateBean
import com.mvc.topay.and.topay_android.common.Constant.SP.DEFAULT_RATE
import com.mvc.topay.and.topay_android.common.Constant.SP.SET_RATE
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat


object TextUtils {
    fun stringToInt(string: String): Int {
        return Integer.valueOf(string)
    }

    fun doubleToInt(db: Double): Int? {
        return db.toInt()
    }

    fun stringToDouble(string: String): Double {
        return java.lang.Double.parseDouble(string)
    }

    fun doubleToFour(price: Double): String {
        val decimal = BigDecimal(java.lang.Double.toString(price))
        return decimal.setScale(4, RoundingMode.DOWN).toString()
    }

    fun doubleToFourPrice(price: Double): String {
        val format = DecimalFormat("0.0000")
        return format.format(price)
    }

    fun doubleToSix(price: Double): String {
        val decimal = BigDecimal(java.lang.Double.toString(price))
        return decimal.setScale(6, RoundingMode.DOWN).toString()
    }

    fun doubleToDouble(price: Double): String {
        val format = DecimalFormat("0.00")
        return format.format(price)
    }

    //
    fun stringToFloat(price: String): Float {
        return java.lang.Float.valueOf(price)
    }

    fun toBigDecimal(price: Double): String {
        return BigDecimal(java.lang.Double.toString(price)).setScale(4, RoundingMode.DOWN).toString()
    }

    fun toFourFloat(price: Float): String {
        val format = DecimalFormat("0.0000")
        return format.format(price.toDouble())
    }

    fun rateToPrice(price: Double): String {
        val format = DecimalFormat("0.00")
        var set_rate = SPUtils.getInstance().getString(SET_RATE)
        var default_rate = SPUtils.getInstance().getString(DEFAULT_RATE)
        var setBean = JsonHelper.stringToJson(set_rate, ExchangeRateBean.DataBean::class.java) as ExchangeRateBean.DataBean
        var defaultBean = JsonHelper.stringToJson(default_rate, ExchangeRateBean.DataBean::class.java) as ExchangeRateBean.DataBean
        if (setBean.name.equals("CNY")) {
            return format.format(price * setBean.value);
        } else {
            return format.format(price / setBean.value);
        }
        return ""
    }
}
