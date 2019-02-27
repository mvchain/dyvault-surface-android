package com.mvc.topay.and.topay_android.utils

import android.os.Handler
import android.os.Looper
import android.os.Message

import com.mvc.topay.and.topay_android.listener.OnTimeEndCallBack


/***
 * Countdown after verification code click
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
     * reset timer
     */
    fun resume() {
        if (mOnTimeEndCallBack != null) {
            mOnTimeEndCallBack!!.exit()
            timeTemp = 60
            mHandler!!.removeCallbacks(mRunnable)
        }
    }

    /**
     * set Time
     *
     * @param temp
     * @return
     */
    fun setTimeTemp(temp: Int): TimeVerification {
        this.timeTemp = temp
        return this
    }

    /**
     * Set the value to increment or decrease each time
     *
     * @param shed value
     */
    fun setTimeDiminished(shed: Int): TimeVerification {
        this.mDiminished = shed
        return this
    }

    /**
     * first update Time
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
     * Destroy timer
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
         * Get timer instance
         *
         * @return
         */
        val instance: TimeVerification by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            TimeVerification()
        }
    }
}
