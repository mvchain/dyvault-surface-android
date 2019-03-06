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
import com.mvc.topay.and.topay_android.common.Constant.SP.USER_INFO
import com.mvc.topay.and.topay_android.common.Constant.SP.USER_RESETPASSWORD_TYPE
import com.mvc.topay.and.topay_android.common.Constant.SP.USER_SALT
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
     * toast
     *
     * @param content
     */
    fun showToast(content: Int) {
        ToastUtils.setGravity(Gravity.CENTER, 0, 0)
        ToastUtils.showShort(content)
    }

    fun showToast(content: String) {
        ToastUtils.setGravity(Gravity.CENTER, 0, 0)
        ToastUtils.showShort(content)
    }

    protected fun setAlpha(alpha: Float) {
        val attributes = window.attributes
        attributes.alpha = alpha
        window.attributes = attributes
    }

    protected fun startTaskActivity(activity: Activity) {
        SPUtils.getInstance().remove(REFRESH_TOKEN)
        SPUtils.getInstance().remove(TOKEN)
        SPUtils.getInstance().remove(USER_ID)
        SPUtils.getInstance().remove(USER_RESETPASSWORD_TYPE)
        SPUtils.getInstance().remove(USER_EMAIL)
        SPUtils.getInstance().remove(USER_SALT)
        SPUtils.getInstance().remove(USER_INFO)
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