package com.mvc.topay.and.topay_android.listener

/***
 * Callback for the verification code countdown
 */
interface OnTimeEndCallBack {
    //update time
    fun updata(time: Int)

    //End of time
    fun exit()
}
