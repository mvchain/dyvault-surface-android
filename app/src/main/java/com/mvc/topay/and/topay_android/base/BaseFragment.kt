package com.mvc.topay.and.topay_android.base

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import cn.jpush.android.api.JPushInterface
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.mvc.topay.and.topay_android.MyApplication
import com.mvc.topay.and.topay_android.activity.SelectLoginActivity
import com.mvc.topay.and.topay_android.common.Constant
import com.mvc.topay.and.topay_android.common.Constant.SP.REFRESH_TOKEN
import com.mvc.topay.and.topay_android.common.Constant.SP.TOKEN
import com.mvc.topay.and.topay_android.common.Constant.SP.USER_BUSINESSES
import com.mvc.topay.and.topay_android.common.Constant.SP.USER_EMAIL
import com.mvc.topay.and.topay_android.common.Constant.SP.USER_ID
import com.mvc.topay.and.topay_android.common.Constant.SP.USER_INFO
import com.mvc.topay.and.topay_android.common.Constant.SP.USER_PROXY
import com.mvc.topay.and.topay_android.common.Constant.SP.USER_RESETPASSWORD_TYPE
import com.mvc.topay.and.topay_android.common.Constant.SP.USER_SALT
import com.trello.rxlifecycle2.components.support.RxFragment

abstract class BaseFragment : RxFragment() {
    protected var mRootView: View? = null
    protected var mContext: Context? = null
    protected lateinit var mActivity: BaseActivity
    private var mToast: Toast? = null
    private var toastGravity = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mRootView === null) {
            mRootView = inflater.inflate(getLayoutId(), container, false)
            initView()
            initData()
        }
        return mRootView
    }

    abstract fun initData()

    abstract fun initView()

    abstract fun getLayoutId(): Int

    @SuppressLint("MissingSuperCall")
    override fun onAttach(context: Context?) {
        this.mContext = context!!
        this.mActivity = context as BaseActivity
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
        this.mContext = null
    }

    protected fun getToken(): String {
        return SPUtils.getInstance().getString(TOKEN)
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
//        showToast(content, gravity, 0, 0)
        ToastUtils.setGravity(Gravity.CENTER, 0, 0)
        ToastUtils.showShort(content)
    }


    protected fun startTaskActivity(activity: Activity) {
        SPUtils.getInstance().remove(REFRESH_TOKEN)
        SPUtils.getInstance().remove(TOKEN)
        SPUtils.getInstance().remove(USER_ID)
        SPUtils.getInstance().remove(USER_RESETPASSWORD_TYPE)
        SPUtils.getInstance().remove(USER_EMAIL)
        SPUtils.getInstance().remove(USER_SALT)
        SPUtils.getInstance().remove(USER_INFO)
        SPUtils.getInstance().remove(USER_BUSINESSES)
        SPUtils.getInstance().remove(USER_PROXY)
        JPushInterface.deleteAlias(MyApplication.application, SPUtils.getInstance().getInt(USER_ID))
        val intent = Intent(activity, SelectLoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
}