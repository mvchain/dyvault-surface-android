package com.mvc.topay.and.topay_android.utils

import android.graphics.drawable.Drawable
import android.widget.TextView

object ViewDrawUtils {

    fun setLeftDraw(draw: Drawable, view: TextView) {
        draw.setBounds(0, 0, draw.minimumWidth, draw.minimumHeight)
        setBoundDraw(draw, null, null, null, view)
    }

    fun setTopDraw(draw: Drawable, view: TextView) {
        draw.setBounds(0, 0, draw.minimumWidth, draw.minimumHeight)
        setBoundDraw(null, draw, null, null, view)
    }

    fun setRigthDraw(draw: Drawable, view: TextView) {
        draw.setBounds(0, 0, draw.minimumWidth, draw.minimumHeight)
        setBoundDraw(null, null, draw, null, view)
    }

    fun setBottomDraw(draw: Drawable, view: TextView) {
        draw.setBounds(0, 0, draw.minimumWidth, draw.minimumHeight)
        setBoundDraw(null, null, null, draw, view)
    }

    fun clearDraw(view: TextView) {
        setBoundDraw(null, null, null, null, view)
    }

    private fun setBoundDraw(leftDraw: Drawable?, topDraw: Drawable?, rightDraw: Drawable?, bottomDraw: Drawable?, view: TextView) {
        view.setCompoundDrawables(leftDraw, topDraw, rightDraw, bottomDraw)
    }
}
