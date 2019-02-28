package com.mvc.topay.and.topay_android.utils

import android.os.Handler
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.blankj.utilcode.util.ToastUtils
import com.mvc.topay.and.topay_android.MyApplication

object ToastUtils {
    private var mToast: Toast? = null
    private var toastGravity = -1

    /**
     * 弹出toast
     *
     * @param content
     */
    fun showToast(content: Int) {
        showToast(content, Gravity.BOTTOM)
    }

    fun showToast(content: String) {
        ToastUtils.showShort(content)
    }

    fun showToast(content: Int, gravity: Int) {
        showToast(content, gravity, 0)
    }

    fun showToast(content: Int, gravity: Int, rid: Int) {
        showToast(content, gravity, rid, 0)
    }

    private fun showToast(content: Int, gravity: Int, rid: Int, index: Int) {
        //永远执行在主线程
        Handler(MyApplication.appContext.mainLooper).post {
            //位置相同  复用
            if (mToast == null || gravity != toastGravity) {
                toastGravity = gravity
                mToast = Toast.makeText(MyApplication.appContext, content, Toast.LENGTH_SHORT)
            }
            if (gravity != Gravity.BOTTOM) {
                mToast?.setGravity(gravity, 0, 0)
            }
            mToast?.setText(content)
            if (rid != 0) {
                val layout = mToast?.view as LinearLayout
                val img = ImageView(MyApplication.appContext)
                img.setImageResource(rid)
                layout.addView(img, index)
            }
            mToast?.show()
        }
    }
}