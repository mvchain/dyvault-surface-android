package com.mvc.topay.and.topay_android.receiver

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.util.Log
import cn.jpush.android.api.JPushInterface
import com.blankj.utilcode.util.LogUtils
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.activity.MsgActivity
import org.json.JSONException
import org.json.JSONObject

class PushReceiver : BroadcastReceiver() {
    private var nm: NotificationManager? = null
    private val ORDER_STATUS = "ORDER_STATUS"


    override fun onReceive(context: Context, intent: Intent) {
        if (null == nm) {
            nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }
        if (JPushInterface.ACTION_REGISTRATION_ID == intent.action) {
            LogUtils.e(TAG, "JPush用户注册成功")
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED == intent.action) {
            LogUtils.e(TAG, "接受到推送下来的自定义消息")
            try {
                createNotification(context, intent)
            } catch (e: JSONException) {
                LogUtils.e(TAG, e.message)
                e.printStackTrace()
            }

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED == intent.action) {
            LogUtils.e(TAG, "接受到推送下来的通知")
            //            receivingNotification(context,bundle);
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED == intent.action) {
            LogUtils.e(TAG, "用户点击打开了通知")
            //      openNotification(context,bundle);
            //            openLaikan(context);
        } else if (JPushInterface.ACTION_CONNECTION_CHANGE == intent.action) {
            val connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false)
            Log.e(TAG, "[MyReceiver]" + intent.action + " connected:" + connected)
        } else {
            LogUtils.e(TAG, "Unhandled intent - " + intent.action!!)
        }
    }

    @Throws(JSONException::class)
    private fun createNotification(context: Context, intent: Intent) {
        val bundle = intent.extras
        val title = bundle!!.getString(JPushInterface.EXTRA_TITLE)
        val message = bundle.getString(JPushInterface.EXTRA_MESSAGE)
        val extra = bundle.getString(JPushInterface.EXTRA_EXTRA)
        LogUtils.e(TAG, bundle)
        val extraJson = JSONObject(extra)
        val id = extraJson.getInt("id")
        val builder: NotificationCompat.Builder
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = NotificationCompat.Builder(context, "to_pay")
            nm!!.createNotificationChannel(NotificationChannel("to_pay", "新消息通知", NotificationManager.IMPORTANCE_HIGH))
        } else {
            builder = NotificationCompat.Builder(context)
        }
        val msgIntent = Intent(context, MsgActivity::class.java)
        val mPendingIntent = PendingIntent.getActivity(context, 1, msgIntent, PendingIntent.FLAG_CANCEL_CURRENT)
        //设置通知栏标题
        builder.setContentTitle("消息提醒")
                //设置通知栏显示内容
                .setContentText(message)
                ////设置通知栏点击意图
                .setContentIntent(mPendingIntent)
                //通知首次出现在通知栏，带上升动画效果的
                .setTicker("您有新的消息")
                //通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setWhen(System.currentTimeMillis())
                //设置该通知优先级
                .setPriority(Notification.PRIORITY_DEFAULT)
                //设置这个标志当用户单击面板就可以让通知将自动取消
                .setAutoCancel(true)
                //使用当前的用户默认设置
                .setDefaults(Notification.DEFAULT_VIBRATE)
                //设置通知小ICON(应用默认图标)
                .setSmallIcon(R.mipmap.icon)
        nm!!.notify(id, builder.build())
    }

    companion object {
        private val TAG = "PushReceiver"
    }
}
