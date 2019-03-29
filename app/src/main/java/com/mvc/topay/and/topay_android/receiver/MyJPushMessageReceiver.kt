package com.mvc.topay.and.topay_android.receiver

import android.content.Context
import cn.jpush.android.api.JPushMessage
import cn.jpush.android.service.JPushMessageReceiver
import com.blankj.utilcode.util.LogUtils

class MyJPushMessageReceiver : JPushMessageReceiver() {
    override fun onTagOperatorResult(context: Context?, jPushMessage: JPushMessage?) {
        //tag增删查改的操作会在此方法中回调结果。
        LogUtils.e("MyJPushMessageReceiver", jPushMessage!!.tags)
        super.onTagOperatorResult(context, jPushMessage)
    }

    override fun onCheckTagOperatorResult(context: Context?, jPushMessage: JPushMessage?) {
        //查询某个tag与当前用户的绑定状态的操作会在此方法中回调结果。
        LogUtils.e("MyJPushMessageReceiver", jPushMessage!!.tags)
        super.onCheckTagOperatorResult(context, jPushMessage)
    }

    override fun onAliasOperatorResult(context: Context?, jPushMessage: JPushMessage?) {
        //alias相关的操作会在此方法中回调结果。
        LogUtils.e("MyJPushMessageReceiver", "jPushMessage.getErrorCode():" + jPushMessage!!.errorCode)
        LogUtils.e("MyJPushMessageReceiver", jPushMessage.alias)
        super.onAliasOperatorResult(context, jPushMessage)
    }

    override fun onMobileNumberOperatorResult(context: Context?, jPushMessage: JPushMessage?) {
        //设置手机号码会在此方法中回调结果。
        LogUtils.e("MyJPushMessageReceiver", jPushMessage!!.mobileNumber)
        super.onMobileNumberOperatorResult(context, jPushMessage)
    }
}
