package com.mvc.topay.and.topay_android.utils

import android.content.res.Resources
import android.support.design.widget.TabLayout
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout

import com.blankj.utilcode.util.LogUtils

import java.lang.reflect.Field

/**
 * Created by on 2018/1/24.
 */

object TabLayoutUtils {

    /**
     * Change the length of the TabLayout underline
     *
     * @param tabs     tablayout
     * @param leftDip
     * @param rightDip
     */
    fun setIndicator(tabs: TabLayout, leftDip: Int, rightDip: Int) {
        val tabLayout = tabs.javaClass
        var tabStrip: Field? = null
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip")
        } catch (e: NoSuchFieldException) {
            LogUtils.d("TabLayoutUtils", e.message)
        }

        tabStrip!!.isAccessible = true
        var llTab: LinearLayout? = null
        try {
            llTab = tabStrip.get(tabs) as LinearLayout
        } catch (e: IllegalAccessException) {
            LogUtils.d("TabLayoutUtils", e.message)
        }

        val left = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip.toFloat(), Resources.getSystem().displayMetrics).toInt()
        val right = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip.toFloat(), Resources.getSystem().displayMetrics).toInt()

        for (i in 0 until llTab!!.childCount) {
            val child = llTab.getChildAt(i)
            child.setPadding(0, 0, 0, 0)
            val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f)
            params.leftMargin = left
            params.rightMargin = right
            child.layoutParams = params
            child.invalidate()
        }
    }
}
