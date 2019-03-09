package com.mvc.topay.and.topay_android.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import com.blankj.utilcode.util.LogUtils

import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.adapter.recyclerAdapter.RateAdapter
import com.mvc.topay.and.topay_android.listener.IPayWindowListener
import com.mvc.topay.and.topay_android.listener.IPopViewListener
import com.mvc.topay.and.topay_android.listener.PswMaxListener
import java.util.ArrayList

class PopViewHelper {
    private lateinit var mPopView: PopupWindow
    @SuppressLint("WrongConstant")
    fun create(context: Context, layoutId: Int, priceCount: String,  address: String, feers: String,iPayWindowListener: IPayWindowListener, maxListener: PswMaxListener): PopupWindow {
        val linear = LayoutInflater.from(context.applicationContext).inflate(layoutId, null) as LinearLayout
        linear.findViewById<ImageView>(R.id.pay_close).setOnClickListener { v -> iPayWindowListener.onclick(v) }
        val pswText = linear.findViewById<PswText>(R.id.pay_text)
        pswText.setOnClickListener { v -> iPayWindowListener.onclick(v) }
        pswText.setMaxListener(maxListener)
        linear.findViewById<TextView>(R.id.pay_forget).setOnClickListener { v -> iPayWindowListener.onclick(v) }
        (linear.findViewById(R.id.pay_price) as TextView).text = priceCount
        (linear.findViewById(R.id.pay_address) as TextView).text = address
        (linear.findViewById(R.id.pay_fee) as TextView).text = feers
        mPopView = PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        mPopView.setOnDismissListener {
            iPayWindowListener.dismiss()
        }
        mPopView.contentView = linear
        mPopView.isFocusable = true
        mPopView.setBackgroundDrawable(BitmapDrawable())
        mPopView.isOutsideTouchable = false
        mPopView.softInputMode = PopupWindow.INPUT_METHOD_NEEDED or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        return mPopView
    }

    fun create(context: Context, layoutId: Int, content: ArrayList<String>, iPopViewListener: IPopViewListener): PopupWindow {
        val linear = LayoutInflater.from(context.applicationContext).inflate(layoutId, null) as LinearLayout
        val mRateView = linear.findViewById<RecyclerView>(R.id.rate_rv)
        val adapter = RateAdapter(R.layout.item_rate_rv, content)
        mRateView.adapter = adapter
        mRateView.addItemDecoration(RuleRecyclerLines(context.applicationContext, RuleRecyclerLines.HORIZONTAL_LIST, 1))
        adapter.setOnItemChildClickListener { adapter1, view, position ->
            when (view.id) {
                R.id.rate_item_content -> if (iPopViewListener != null) {
                    mPopView.dismiss()
                    iPopViewListener.changeRate(position)
                }
            }
        }
        mPopView = PopupWindow(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        mPopView.setOnDismissListener {
            if (iPopViewListener != null) {
                iPopViewListener.dismiss()
            }
        }
        mPopView.isOutsideTouchable = true
        mPopView.setBackgroundDrawable(ColorDrawable())
        mPopView.contentView = linear
        return mPopView
    }


    fun dismiss() {
        if (mPopView != null && mPopView.isShowing) {
            mPopView.dismiss()
        }
    }

    companion object {
        val instance: PopViewHelper by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            PopViewHelper()
        }
    }
}
