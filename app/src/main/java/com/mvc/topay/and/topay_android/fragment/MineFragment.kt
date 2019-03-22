package com.mvc.topay.and.topay_android.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.activity.AboutActivity
import com.mvc.topay.and.topay_android.activity.LanguageActivity
import com.mvc.topay.and.topay_android.activity.SelectResetPasswordActivity
import com.mvc.topay.and.topay_android.base.BaseMVPFragment
import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.bean.UserInfoBean
import com.mvc.topay.and.topay_android.common.Constant.SP.USER_INFO
import com.mvc.topay.and.topay_android.constract.IMineContract
import com.mvc.topay.and.topay_android.event.MineUserEvent
import com.mvc.topay.and.topay_android.listener.IDialogViewClickListener
import com.mvc.topay.and.topay_android.presenter.MinePresenter
import com.mvc.topay.and.topay_android.utils.DialogHelper
import com.mvc.topay.and.topay_android.utils.JsonHelper
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MineFragment : BaseMVPFragment<IMineContract.MineView, IMineContract.MinePresenter>(), IMineContract.MineView, View.OnClickListener {


    private lateinit var mMineRefresh: SwipeRefreshLayout
    private lateinit var mUserName: TextView
    private lateinit var mUserEmail: TextView
    private var createCarryOut: Boolean = false

    override fun getUserSuccess(userInfoBean: UserInfoBean.DataBean) {
        mMineRefresh.post { mMineRefresh.isRefreshing = false }
        SPUtils.getInstance().put(USER_INFO, JsonHelper.jsonToString(userInfoBean))
        mUserName.text = userInfoBean.nickname
        mUserEmail.text = userInfoBean.username
    }

    override fun initData() {
        super.initData()
        mPresenter.getUserInfo()
    }

    override fun getUserFailed(userInfoBean: UserInfoBean?) {
        if (userInfoBean != null) {
            if (userInfoBean.code === 400) {
                startTaskActivity(mActivity)
            }
            showToast(userInfoBean.message)
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && createCarryOut) {
            refresh()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mMineRefresh.post { mMineRefresh.isRefreshing = false }
    }

    private lateinit var dialogHelper: DialogHelper


    override fun initView() {
        mRootView!!.findViewById<TextView>(R.id.mine_authentication).setOnClickListener(this)
        mRootView!!.findViewById<TextView>(R.id.mine_payment).setOnClickListener(this)
        mRootView!!.findViewById<TextView>(R.id.mine_account).setOnClickListener(this)
        mRootView!!.findViewById<TextView>(R.id.mine_language).setOnClickListener(this)
        mRootView!!.findViewById<TextView>(R.id.mine_about).setOnClickListener(this)
        mRootView!!.findViewById<TextView>(R.id.login_up).setOnClickListener(this)
        this.mUserName = mRootView!!.findViewById(R.id.mine_nickname)
        this.mUserEmail = mRootView!!.findViewById(R.id.mine_email)
        this.mMineRefresh = mRootView!!.findViewById(R.id.mine_refresh)
        mMineRefresh.post { mMineRefresh.isRefreshing = true }
        mMineRefresh.setOnRefreshListener { this.refresh() }
        dialogHelper = DialogHelper.instance
        createCarryOut = true
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun initPresenter(): BasePresenter<*, *> {
        return MinePresenter.newInstance()
    }

    private fun refresh() {
        mPresenter.getUserInfo()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mine_authentication -> {
//                startActivity(Intent(mActivity, SelectResetPasswordActivity::class.java))
            }
            R.id.mine_payment -> {
//                startActivity(Intent(mActivity, SelectResetPasswordActivity::class.java))
            }
            R.id.mine_account -> {
                startActivity(Intent(mActivity, SelectResetPasswordActivity::class.java))
            }
            R.id.mine_language -> {
                startActivity(Intent(mActivity, LanguageActivity::class.java))
            }
            R.id.mine_about -> {
                startActivity(Intent(mActivity, AboutActivity::class.java))
            }
            R.id.login_up -> {
                dialogHelper.create(mActivity, getString(R.string.sign_out_app), object : IDialogViewClickListener {
                    override fun click(viewId: Int) {
                        when (viewId) {
                            R.id.hint_cancle -> {

                            }
                            R.id.hint_enter -> {
                                startTaskActivity(mActivity)
                            }
                        }
                    }
                }).show()
            }
        }
    }
}