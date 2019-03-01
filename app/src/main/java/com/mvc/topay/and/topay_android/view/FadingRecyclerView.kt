package com.mvc.topay.and.topay_android.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet

class FadingRecyclerView : RecyclerView {
    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    override fun getTopFadingEdgeStrength(): Float {
        return 0f
    }
}