package com.mvc.topay.and.topay_android.fragment

import android.content.Intent
import android.view.View
import android.widget.TextView
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.activity.AboutActivity
import com.mvc.topay.and.topay_android.activity.LanguageActivity
import com.mvc.topay.and.topay_android.base.BaseMVPFragment
import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.constract.IMineContract
import com.mvc.topay.and.topay_android.listener.IDialogViewClickListener
import com.mvc.topay.and.topay_android.presenter.MinePresenter
import com.mvc.topay.and.topay_android.utils.DialogHelper

class MineFragment : BaseMVPFragment<IMineContract.MineView, IMineContract.MinePresenter>(), IMineContract.MineView, View.OnClickListener {
    private lateinit var dialogHelper: DialogHelper


    override fun initView() {
        mRootView!!.findViewById<TextView>(R.id.mine_account).setOnClickListener(this)
        mRootView!!.findViewById<TextView>(R.id.mine_language).setOnClickListener(this)
        mRootView!!.findViewById<TextView>(R.id.mine_about).setOnClickListener(this)
        mRootView!!.findViewById<TextView>(R.id.login_up).setOnClickListener(this)
        dialogHelper = DialogHelper.instance
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun initPresenter(): BasePresenter<*, *> {
        return MinePresenter.newIntance()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mine_account -> {

            }
            R.id.mine_language -> {
                startActivity(Intent(mActivity, LanguageActivity::class.java))
            }
            R.id.mine_about -> {
                startActivity(Intent(mActivity, AboutActivity::class.java))
            }
            R.id.login_up->{
                dialogHelper.create(mActivity,"确认登出Topay?",object :IDialogViewClickListener{
                    override fun click(viewId: Int) {
                        when(viewId){
                            R.id.hint_cancle->{

                            }
                            R.id.hint_enter->{
                                startTaskActivity(mActivity)
                            }
                        }
                    }
                }).show()
            }
        }
    }
}