package com.mvc.topay.and.topay_android.fragment

import android.content.Intent
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.blankj.utilcode.util.LogUtils
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.activity.DetailActivity
import com.mvc.topay.and.topay_android.adapter.recyclerAdapter.HistoryChildAdapter
import com.mvc.topay.and.topay_android.base.BaseMVPFragment
import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.bean.TransactionsBean
import com.mvc.topay.and.topay_android.constract.IHistoryChindContract
import com.mvc.topay.and.topay_android.event.HistoryEvent
import com.mvc.topay.and.topay_android.event.HistoryFragmentEvent
import com.mvc.topay.and.topay_android.presenter.HistoryChildPresenter
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.*

class HistoryFragment : BaseMVPFragment<IHistoryChindContract.HistoryChindView, IHistoryChindContract.HistoryChindPresenter>(), IHistoryChindContract.HistoryChindView {
    private var type: Int = 0
    private var tokenId: Int = 0
    private var isRefresh = false
    private lateinit var historyChildAdapter: HistoryChildAdapter
    private lateinit var dateBean: ArrayList<TransactionsBean.DataBean>
    private lateinit var recyclerView: RecyclerView
    private lateinit var mRefreshView: SwipeRefreshLayout
    private lateinit var dataNull: TextView
    override fun initView() {
        EventBus.getDefault().register(this)
        type = arguments!!.getInt("type")
        tokenId = arguments!!.getInt("tokenId")
        dateBean = ArrayList()
        historyChildAdapter = HistoryChildAdapter(R.layout.item_history_child_rv, dateBean)
        recyclerView = mRootView!!.findViewById(R.id.history_child_rv)
        dataNull = mRootView!!.findViewById(R.id.history_child_null)
        mRefreshView = mRootView!!.findViewById(R.id.history_swipe)
        recyclerView.adapter = historyChildAdapter
        historyChildAdapter.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.his_layout -> {
                    var id = dateBean[position].id
                    var dIntent = Intent(mActivity, DetailActivity::class.java)
                    dIntent.putExtra("id", id)
                    startActivity(dIntent)
                }
            }
        }
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

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                mRefreshView.isEnabled = (recyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition() == 0;
            }
        })
        mRefreshView.post { mRefreshView.isRefreshing = true }
        mRefreshView.setOnRefreshListener { historyRefresh() }
    }

    override fun initData() {
        super.initData()
        isRefresh = true
        mPresenter.getTransferList(0, 10, tokenId, type)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_history
    }

    override fun initPresenter(): BasePresenter<*, *> {
        return HistoryChildPresenter.newInstance()
    }

    override fun getHistorySuccess(transactionsBean: ArrayList<TransactionsBean.DataBean>) {
        mRefreshView.post { mRefreshView.isRefreshing = false }
        LogUtils.e(isRefresh)
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
        mRefreshView.post { mRefreshView.isRefreshing = false }
        isRefresh = false
        dataNull.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }

    private fun historyRefresh() {
        isRefresh = true
        dateBean.clear()
        mPresenter.getTransferList(0, 10, tokenId, type)
        EventBus.getDefault().post(HistoryEvent())
    }


    @Subscribe
    fun refresh(event: HistoryFragmentEvent?) {
        isRefresh = true
        dateBean.clear()
        mPresenter.getTransferList(0, 10, tokenId, type)
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }
}