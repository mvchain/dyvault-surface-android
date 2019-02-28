package com.mvc.topay.and.topay_android.fragment

import android.content.Intent
import android.view.View
import android.widget.TextView
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.activity.LanguageActivity
import com.mvc.topay.and.topay_android.base.BaseMVPFragment
import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.constract.IMineContract
import com.mvc.topay.and.topay_android.presenter.MinePresenter

class MineFragment : BaseMVPFragment<IMineContract.MineView, IMineContract.MinePresenter>(), IMineContract.MineView, View.OnClickListener {
    override fun initView() {
        mRootView!!.findViewById<TextView>(R.id.mine_account).setOnClickListener(this)
        mRootView!!.findViewById<TextView>(R.id.mine_language).setOnClickListener(this)
        mRootView!!.findViewById<TextView>(R.id.mine_about).setOnClickListener(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun initPresenter(): BasePresenter<*, *> {
        return MinePresenter.newIntance()
    }
   override fun onClick(v: View){
        when(v.id){
            R.id.mine_account->{

            }
            R.id.mine_language->{
                startActivity(Intent(activity,LanguageActivity::class.java))
            }
            R.id.mine_about->{

            }
        }
    }
}