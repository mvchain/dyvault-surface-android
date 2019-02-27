package com.mvc.topay.and.topay_android.utils

import android.os.Handler
import android.os.Looper
import android.os.Message

import com.mvc.topay.and.topay_android.listener.OnTimeEndCallBack


/***
 * 验证码点击之后倒计时
 */
class TimeVerification private constructor() {
    private var mOnTimeEndCallBack: OnTimeEndCallBack? = null
    var mRunnable: Runnable? = null

    var mHandler: Handler? = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            if (msg.what != 0) {
                mOnTimeEndCallBack!!.updata(msg.what)
                this.postDelayed(mRunnable, 1000)
            } else {
                mOnTimeEndCallBack!!.exit()
                timeTemp = 60
                this.removeCallbacks(mRunnable)
            }
        }
    }

    internal var timeTemp = 60//默认60s
    private var mDiminished = 1

    fun setOnTimeEndCallBack(onTimeEndCallBack: OnTimeEndCallBack): TimeVerification {
        this.mOnTimeEndCallBack = onTimeEndCallBack
        return this
    }

    /**
     * 重置计时器
     */
    fun resume() {
        if (mOnTimeEndCallBack != null) {
            mOnTimeEndCallBack!!.exit()
            timeTemp = 60
            mHandler!!.removeCallbacks(mRunnable)
        }
    }

    /**
     * 设置时间
     *
     * @param temp
     * @return
     */
    fun setTimeTemp(temp: Int): TimeVerification {
        this.timeTemp = temp
        return this
    }

    /**
     * 设置每次递增或者减少的值
     *
     * @param shed
     */
    fun setTimeDiminished(shed: Int): TimeVerification {
        this.mDiminished = shed
        return this
    }

    /**
     * 第一次更新时间
     */
    fun updataTime() {
        resume()
        mRunnable = Runnable {
            timeTemp -= mDiminished
            mHandler!!.sendEmptyMessageAtTime(timeTemp, 1000)
        }
        mHandler!!.post(mRunnable)
    }

    /**
     * 销毁计时器
     */
    fun onExit() {
        if (mHandler != null && timeVerification != null) {
            mHandler!!.removeCallbacks(mRunnable)
            mHandler = null
            mRunnable = null
            timeVerification = null
            timeTemp = 60//默认60s
            mDiminished = 1
        }
    }

    companion object {
        private var timeVerification: TimeVerification? = null

        /**
         * 获取计时器实例
         *
         * @return
         */
        val instance: TimeVerification by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            TimeVerification()
        }
    }
}
