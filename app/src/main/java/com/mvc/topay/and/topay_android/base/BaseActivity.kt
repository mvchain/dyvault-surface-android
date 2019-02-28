package com.mvc.topay.and.topay_android.base

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
import com.mvc.topay.and.topay_android.common.Constant
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity

abstract class BaseActivity : RxAppCompatActivity() {

    private var mToast: Toast? = null
    private var toastGravity = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initView()
        initData()
    }

    abstract fun initData()

    open fun initView(){
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init()
    }

    abstract fun getLayoutId(): Int

    protected fun getToken(): String {
        return SPUtils.getInstance().getString(Constant.SP.TOKEN)
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

    fun showToast(content: Int, gravity: Int, rid: Int) {
        showToast(content, gravity, rid, 0)
    }

    private fun showToast(content: Int, gravity: Int, rid: Int, index: Int) {
        //永远执行在主线程
        Handler(MyApplication.getAppContext().mainLooper).post {
            //位置相同  复用
            if (mToast == null || gravity != toastGravity) {
                toastGravity = gravity
                mToast = Toast.makeText(MyApplication.getAppContext(), content, Toast.LENGTH_SHORT)
            }
            if (gravity != Gravity.BOTTOM) {
                mToast?.setGravity(gravity, 0, 0)
            }
            mToast?.setText(content)
            if (rid != 0) {
                val layout = mToast?.view as LinearLayout
                val img = ImageView(MyApplication.getAppContext())
                img.setImageResource(rid)
                layout.addView(img, index)
            }
            mToast?.show()
        }
    }
}