package com.mvc.topay.and.topay_android.utils

import android.app.Dialog
import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.widget.TextView

import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.listener.IDialogViewClickListener

class DialogHelper {
    private lateinit var mDialog: Dialog


    fun create(context: Context, msg: String, clickListener: IDialogViewClickListener): Dialog {
        mDialog = Dialog(context, R.style.tras_dialog)
        mDialog!!.setCanceledOnTouchOutside(false)
        mDialog!!.setCancelable(false)
        val dialogView = LayoutInflater.from(context).inflate(R.layout.layout_dialog, null)
        val cancle = dialogView.findViewById<TextView>(R.id.hint_cancle)
        val enter = dialogView.findViewById<TextView>(R.id.hint_enter)
        val title = dialogView.findViewById<TextView>(R.id.hint_title)
        title.text = msg
        mDialog!!.setContentView(dialogView)
        cancle.setOnClickListener {
            clickListener.click(cancle.id)
            dismiss()
        }
        enter.setOnClickListener {
            clickListener.click(enter.id)
            dismiss()
        }
        return mDialog
    }

    fun dismissDelayed(dialogDialog: IDialogDialog?) {
        Handler().postDelayed({
            dismiss()
            dialogDialog?.callback()
        }, 2000)
    }

    fun dismissDelayed(dialogDialog: IDialogDialog?, delayMillis: Long) {
        Handler().postDelayed({
            dismiss()
            dialogDialog?.callback()
        }, delayMillis)
    }

    fun dismiss() {
        if (mDialog != null) {
            mDialog!!.dismiss()
        }
    }

    interface IDialogDialog {
        fun callback()
    }

    companion object {
        private var mDialogHelper: DialogHelper? = null

        val instance: DialogHelper by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            DialogHelper()
        }
    }
}
