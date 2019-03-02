package com.mvc.topay.and.topay_android.base

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.gyf.barlibrary.ImmersionBar
import com.mvc.topay.and.topay_android.MyApplication
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.activity.SelectLoginActivity
import com.mvc.topay.and.topay_android.common.Constant
import com.mvc.topay.and.topay_android.common.Constant.SP.REFRESH_TOKEN
import com.mvc.topay.and.topay_android.common.Constant.SP.TOKEN
import com.mvc.topay.and.topay_android.common.Constant.SP.USER_EMAIL
import com.mvc.topay.and.topay_android.common.Constant.SP.USER_ID
import com.mvc.topay.and.topay_android.common.Constant.SP.USER_RESETPASSWORD_TYPE
import com.mvc.topay.and.topay_android.utils.LanguageUtils
import com.mvc.topay.and.topay_android.utils.WeiboDialogUtils
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import org.greenrobot.eventbus.EventBus
import java.util.*

abstract class BaseActivity : RxAppCompatActivity() {
    private lateinit var loadDialogUtils: Dialog
    private var mToast: Toast? = null
    private var toastGravity = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initView()
        initData()
    }

    override fun onDestroy() {
        ImmersionBar.with(this).destroy()
        super.onDestroy()
    }

    abstract fun initData()

    open fun initView() {
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init()
    }

    abstract fun getLayoutId(): Int

    protected fun getToken(): String {
        return SPUtils.getInstance().getString(Constant.SP.TOKEN)
    }

    protected fun showDialog(msg: String) {
        loadDialogUtils = WeiboDialogUtils.createLoadingDialog(this, msg)
        loadDialogUtils.show()
    }

    protected fun dismiss() {
        if (loadDialogUtils !== null) {
            loadDialogUtils.dismiss()
        }
    }

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

    fun showToast(content: String, gravity: Int) {
        showToast(content, gravity, 0, 0)
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

    private fun showToast(content: String, gravity: Int, rid: Int, index: Int) {
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

    protected fun startTaskActivity(activity: Activity) {
        SPUtils.getInstance().remove(REFRESH_TOKEN)
        SPUtils.getInstance().remove(TOKEN)
        SPUtils.getInstance().remove(USER_ID)
        SPUtils.getInstance().remove(USER_RESETPASSWORD_TYPE)
        SPUtils.getInstance().remove(USER_EMAIL)
        val intent = Intent(activity, SelectLoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    /**
     * change default language
     */
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LanguageUtils.wrapLocale(newBase, Locale(LanguageUtils.getUserSetLocal())))
    }
}