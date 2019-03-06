package com.mvc.topay.and.topay_android.presenter

import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.SPUtils
import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.common.Constant.SP.RESETPASSWORD_LOGIN
import com.mvc.topay.and.topay_android.common.Constant.SP.RESETPASSWORD_PAY
import com.mvc.topay.and.topay_android.common.Constant.SP.USER_EMAIL
import com.mvc.topay.and.topay_android.constract.IChangePasswordContract
import com.mvc.topay.and.topay_android.model.ChangePasswordModel

class ChangePasswordPresenter : IChangePasswordContract.ChangePasswordPresenter() {
    override fun updateLoginPasssword(newPassword: String, oldPassword: String) {
        var email = SPUtils.getInstance().getString(USER_EMAIL)
        mRxUtils.register(mIModel!!.updateLoginPasssword(newPassword, oldPassword)
                .subscribe({ update ->
                    if (update.code === 200) {
                        mIView!!.updateSuccess("登入密码修改成功", RESETPASSWORD_LOGIN)
                    } else {
                        mIView!!.updateFailed(update.message!!, RESETPASSWORD_LOGIN)
                    }
                }, {
                    mIView!!.updateFailed(it.message!!, RESETPASSWORD_LOGIN)
                }))
    }

    override fun updatePaypassword(newPassword: String, oldPassword: String) {
        var email = SPUtils.getInstance().getString(USER_EMAIL)
        mRxUtils.register(mIModel!!.updatePaypassword(newPassword, oldPassword)
                .subscribe({ update ->
                    if (update.code === 200) {
                        mIView!!.updateSuccess("支付密码修改成功", RESETPASSWORD_PAY)
                    } else {
                        mIView!!.updateFailed(update.message!!, RESETPASSWORD_PAY)
                    }
                }, {
                    mIView!!.updateFailed(it.message!!, RESETPASSWORD_PAY)
                }))
    }

    override fun getModel(): IChangePasswordContract.ChangePasswordModel {
        return ChangePasswordModel.instance
    }

    override fun onStart() {
    }

    companion object {
        fun newInstance(): BasePresenter<*, *> {
            return ChangePasswordPresenter()
        }
    }
}