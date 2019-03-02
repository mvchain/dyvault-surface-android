package com.mvc.topay.and.topay_android.activity

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.View
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.mvc.topay.and.topay_android.MyApplication
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.base.BaseMVPActivity
import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.bean.UserInfoBean
import com.mvc.topay.and.topay_android.common.Constant.SP.USER_INFO
import com.mvc.topay.and.topay_android.constract.IChangeEmailContract
import com.mvc.topay.and.topay_android.listener.OnTimeEndCallBack
import com.mvc.topay.and.topay_android.presenter.ChangeEmailPresenter
import com.mvc.topay.and.topay_android.utils.JsonHelper
import com.mvc.topay.and.topay_android.utils.TimeVerification
import kotlinx.android.synthetic.main.activity_changeemail.*

class ChangeEmailVerificationActivity : BaseMVPActivity<IChangeEmailContract.ChangeEmailView, IChangeEmailContract.ChangeEmailPresenter>(), IChangeEmailContract.ChangeEmailView {
    private var isEmail = false
    override fun sendEmailSuccess(msg: String) {
        dismiss()
        showToast(msg, Gravity.CENTER)
        TimeVerification.instance.setOnTimeEndCallBack(object : OnTimeEndCallBack {
            override fun updata(time: Int) {
                change_send.isEnabled = false
                change_send.setTextColor(ContextCompat.getColor(baseContext, R.color.edit_bg))
                change_send.text = time.toString() + "s"
            }

            override fun exit() {
                change_send.isEnabled = true
                change_send.setTextColor(ContextCompat.getColor(baseContext, R.color.register_button_bg))
                change_send.text = "获取验证码"
            }
        }).updataTime()
    }

    override fun sendEmailFailed(msg: String) {
        dismiss()
        showToast(msg, Gravity.CENTER)
    }

    override fun verifyEmailSuccess(token: String) {
        dismiss()
        var sIntent = Intent(this, SetEmailActivity::class.java)
        sIntent.putExtra("token", token)
        startActivity(sIntent)
    }

    override fun verifyEmailFailed(msg: String) {
        dismiss()
        showToast(msg, Gravity.CENTER)
    }

    override fun initMVPData() {
        val userJson = SPUtils.getInstance().getString(USER_INFO)
        LogUtils.e(userJson)
        if (userJson !== "") {
            val infoBean = JsonHelper.stringToJson(userJson, UserInfoBean.DataBean::class.java) as UserInfoBean.DataBean
            change_user_email.text = infoBean.username
            isEmail = true
        }
    }

    override fun initMVPView() {
        super.initMVPView()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_changeemail
    }

    override fun initPresenter(): BasePresenter<*, *> {
        return ChangeEmailPresenter.newIntance()
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.change_back -> {
                finish()
            }
            R.id.change_send -> {
                if (!isEmail) {
                    return
                }
                showDialog("发送验证码中...")
                mPresenter.sendEmail()
            }
            R.id.change_next -> {
                var code = change_code.text.toString()
                if (code === "") {
                    showToast(MyApplication.application!!.getString(R.string.login_null_code), Gravity.CENTER)
                    return
                }
                showDialog("请稍后...")
                mPresenter.verifyEmail(code)
            }
        }
    }
}