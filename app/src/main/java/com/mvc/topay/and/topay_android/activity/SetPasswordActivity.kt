package com.mvc.topay.and.topay_android.activity

import android.content.Intent
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import cn.jpush.android.api.JPushInterface
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.mvc.topay.and.topay_android.MainActivity
import com.mvc.topay.and.topay_android.MyApplication
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.base.BaseMVPActivity
import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.bean.RegisterBean
import com.mvc.topay.and.topay_android.common.Constant
import com.mvc.topay.and.topay_android.common.Constant.SP.REFRESH_TOKEN
import com.mvc.topay.and.topay_android.common.Constant.SP.TOKEN
import com.mvc.topay.and.topay_android.common.Constant.SP.USER_EMAIL
import com.mvc.topay.and.topay_android.common.Constant.SP.USER_ID
import com.mvc.topay.and.topay_android.common.Constant.SP.USER_SALT
import com.mvc.topay.and.topay_android.constract.ISetPasswordContract
import com.mvc.topay.and.topay_android.presenter.SetPasswordPresenter
import kotlinx.android.synthetic.main.activity_setpassword.*

class SetPasswordActivity : BaseMVPActivity<ISetPasswordContract.SetPasswordView, ISetPasswordContract.SetPasswordPresenter>(), ISetPasswordContract.SetPasswordView {
    override fun registerSuccess(registerBean: RegisterBean) {
        dismiss()
        var dataBean = registerBean.data
        MyApplication.token = dataBean.token
        SPUtils.getInstance().put(TOKEN, dataBean.token)
        SPUtils.getInstance().put(REFRESH_TOKEN, dataBean.refreshToken)
        SPUtils.getInstance().put(USER_ID, dataBean.userId)
        SPUtils.getInstance().put(USER_EMAIL, dataBean.email)
        SPUtils.getInstance().put(USER_SALT, dataBean.salt)
        var mainIntent = Intent(this, MainActivity::class.java)
        JPushInterface.setAlias(applicationContext, dataBean.userId, "${dataBean.userId}")
        mainIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(mainIntent)
    }

    override fun registerFailed(msg: String) {
        showToast(msg)
        dismiss()
    }

    private lateinit var email: String
    private lateinit var inviteCode: String
    private lateinit var nickname: String
    private lateinit var mToken: String

    override fun initMVPData() {

    }

    override fun initMVPView() {
        super.initMVPView()
        /**
         * login password
         */
        set_pwd_login_show.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                set_pwd_login_password.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                set_pwd_login_password.transformationMethod = PasswordTransformationMethod.getInstance()
            }
            set_pwd_login_password.setSelection(set_pwd_login_password.text.length)
        }
        /**
         * pay password
         */
        set_pwd_pay_show.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                set_pwd_pay_password.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                set_pwd_pay_password.transformationMethod = PasswordTransformationMethod.getInstance()
            }
            set_pwd_pay_password.setSelection(set_pwd_pay_password.text.length)
        }
        /**
         * get register page data
         */
        this.email = intent.getStringExtra("email")
        this.inviteCode = intent.getStringExtra("inviteCode")
        this.nickname = intent.getStringExtra("nickname")
        this.mToken = intent.getStringExtra("token")
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_setpassword
    }

    override fun initPresenter(): BasePresenter<*, *> {
        return SetPasswordPresenter.newInstance()
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.set_pwd_back -> {
                finish()
            }

            R.id.set_pwd_submit -> {
                showDialog(getString(R.string.please_wait))
                var login_password = set_pwd_login_password.text.toString()
                var pay_password = set_pwd_pay_password.text.toString()
                mPresenter.setPassword(email, inviteCode, nickname, login_password, mToken, pay_password)
            }
        }
    }
}