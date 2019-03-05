package com.mvc.topay.and.topay_android.fragment

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.blankj.utilcode.util.LogUtils
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.adapter.recyclerAdapter.HistoryChildAdapter
import com.mvc.topay.and.topay_android.base.BaseMVPFragment
import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.bean.TransactionsBean
import com.mvc.topay.and.topay_android.constract.IHistoryChindContract
import com.mvc.topay.and.topay_android.presenter.HistoryChildPresenter
import java.util.*

class HistoryFragment : BaseMVPFragment<IHistoryChindContract.HistoryChindView, IHistoryChindContract.HistoryChindPresenter>(), IHistoryChindContract.HistoryChindView {
    private var type: Int = 0
    private var tokenId: Int = 0
    private var isRefresh = false
    private lateinit var historyChildAdapter: HistoryChildAdapter
    private lateinit var dateBean: ArrayList<TransactionsBean.DataBean>
    private lateinit var recyclerView: RecyclerView
    private lateinit var dataNull: TextView
    override fun initView() {
        type = arguments!!.getInt("type")
        tokenId = arguments!!.getInt("tokenId")
        dateBean = ArrayList()
        LogUtils.e(UUID.randomUUID().toString().replace("-", ""))
        historyChildAdapter = HistoryChildAdapter(R.layout.item_history_child_rv, dateBean)
        recyclerView = mRootView!!.findViewById(R.id.history_child_rv)
        dataNull = mRootView!!.findViewById(R.id.history_child_null)
        recyclerView.adapter = historyChildAdapter
        recyclerView.setHasFixedSize(true)
        recyclerView.isNestedScrollingEnabled = false
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    var layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    var lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                    if (lastVisibleItemPosition + 1 == historyChildAdapter.itemCount && historyChildAdapter.itemCount >= 10 && !isRefresh) {
                        mPresenter.getTransferList(dateBean[dateBean.size - 1].id, 10, tokenId, type)
                    }
                }
            }
        })
        isRefresh = true
    }

    override fun initData() {
        super.initData()
        mPresenter.getTransferList(0, 10, tokenId, type)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_history
    }

    override fun initPresenter(): BasePresenter<*, *> {
        return HistoryChildPresenter.newInstance()
    }

    override fun getHistorySuccess(transactionsBean: ArrayList<TransactionsBean.DataBean>) {
        if (isRefresh) {
            isRefresh = false
            dateBean.clear()
        }
        dateBean.addAll(transactionsBean)
        if (dateBean.size > 0) {
            dataNull.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            historyChildAdapter.notifyDataSetChanged()
        } else {
            dataNull.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        }
    }

    override fun getHistoryFailed(msg: String) {
        isRefresh = false
        dataNull.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }

    fun historyRefresh() {
        isRefresh = true
        dateBean.clear()
        mPresenter.getTransferList(0, 10, tokenId, type)
    }
}