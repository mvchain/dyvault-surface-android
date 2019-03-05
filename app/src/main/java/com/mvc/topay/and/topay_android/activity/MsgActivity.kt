package com.mvc.topay.and.topay_android.activity

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.adapter.recyclerAdapter.MsgAdapter
import com.mvc.topay.and.topay_android.base.BaseMVPActivity
import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.bean.MsgBean
import com.mvc.topay.and.topay_android.constract.IMsgContrast
import com.mvc.topay.and.topay_android.presenter.MsgPresenter
import kotlinx.android.synthetic.main.activity_msg.*

class MsgActivity : BaseMVPActivity<IMsgContrast.MsgView, IMsgContrast.MsgPresenter>(), IMsgContrast.MsgView {
    private lateinit var mBean: ArrayList<MsgBean.DataBean>
    private lateinit var msgAdapter: MsgAdapter
    private var isRefresh = false


    override fun initMVPData() {
        isRefresh = true
        mPresenter.getMessage(20, 0)

    }

    override fun initMVPView() {
        super.initMVPView()
        mBean = ArrayList()
        msgAdapter = MsgAdapter(R.layout.item_msg_rv, mBean)
        msg_back.setOnClickListener { finish() }
        msg_rv.adapter = msgAdapter
        msg_rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    var layoutManager = recyclerView.layoutManager as LinearLayoutManager;
                    var lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                    if (lastVisibleItemPosition + 1 == msgAdapter.itemCount && msgAdapter.itemCount >= 20 && !isRefresh) {
                        mPresenter.getMessage(20, mBean[mBean.size - 1].createdAt)
                    }
                }
            }
        })
        msg_swipe.setOnRefreshListener { this.refresh() }
        msg_swipe.post { msg_swipe.isRefreshing = true }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_msg
    }

    override fun initPresenter(): BasePresenter<*, *> {
        return MsgPresenter.newInstance()
    }

    override fun msgSuccess(msgBean: List<MsgBean.DataBean>) {
        if (isRefresh) {
            isRefresh = false
            mBean.clear()
        }
        mBean.addAll(msgBean)
        if (mBean.isNotEmpty()) {
            msg_rv.visibility = View.VISIBLE
            msg_null.visibility = View.GONE
            msgAdapter.notifyDataSetChanged()
        } else {
            msg_null.visibility = View.VISIBLE
            msg_rv.visibility = View.GONE
        }
        msg_swipe.post { msg_swipe.isRefreshing = false }
    }

    override fun msgError(msg: String) {
        isRefresh = false
        showToast(msg)
        msg_null.visibility = View.VISIBLE
        msg_rv.visibility = View.GONE
        msg_swipe.post { msg_swipe.isRefreshing = false }
    }

    private fun refresh() {
        isRefresh = true
        mPresenter.getMessage(20, 0)
    }
}