package com.mvc.topay.and.topay_android.fragment

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.adapter.recyclerAdapter.ChannelAdapter
import com.mvc.topay.and.topay_android.base.BaseMVPFragment
import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.bean.ChannelBean
import com.mvc.topay.and.topay_android.constract.IBuyingContract
import com.mvc.topay.and.topay_android.presenter.BuyingPresenter
import kotlinx.android.synthetic.main.fragment_buyingcoins.*
import java.util.ArrayList

class BuyingCoinsFragment : BaseMVPFragment<IBuyingContract.BuyingView, IBuyingContract.BuyingPresenter>(), IBuyingContract.BuyingView {
    private lateinit var channelBean: ArrayList<ChannelBean.DataBean>
    private lateinit var channelAdapter: ChannelAdapter
    private var isRefresh = false
    private lateinit var mSwipeRefresh: SwipeRefreshLayout
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mDataNull: TextView
    private var type = 0

    override fun initData() {
        super.initData()
        isRefresh = true
        mPresenter.getChannelList(0, 10)
    }

    override fun initView() {
        type = arguments!!.getInt("type")
        channelBean = ArrayList()
        channelAdapter = ChannelAdapter(R.layout.item_buying_coins, channelBean)
        mSwipeRefresh = mRootView!!.findViewById(R.id.coins_swipe)
        mRecyclerView = mRootView!!.findViewById(R.id.coins_rv)
        mDataNull = mRootView!!.findViewById(R.id.coins_null)
        mRecyclerView.adapter = channelAdapter
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    var layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    var lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                    if (lastVisibleItemPosition + 1 == channelAdapter.itemCount && channelAdapter.itemCount >= 10 && !isRefresh) {
                        mPresenter.getChannelList(channelBean[channelBean.size - 1].id, 10)
                    }
                }
            }
        })
        mSwipeRefresh.post { mSwipeRefresh.isRefreshing = true }
        mSwipeRefresh.setOnRefreshListener { onRefresh() }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_buyingcoins
    }

    override fun initPresenter(): BasePresenter<*, *> {
        return BuyingPresenter.newInstance()
    }

    override fun channelSuccess(channelBean: ArrayList<ChannelBean.DataBean>) {
        mSwipeRefresh.post { mSwipeRefresh.isRefreshing = false }
        if (isRefresh) {
            isRefresh = false
            this.channelBean.clear()
        }
        this.channelBean.addAll(channelBean)
        if (this.channelBean.size > 0) {
            coins_null.visibility = View.GONE
            mRecyclerView.visibility = View.VISIBLE
            channelAdapter.notifyDataSetChanged()
        } else {
            coins_null.visibility = View.VISIBLE
            mRecyclerView.visibility = View.GONE
        }
    }

    override fun channelFailed(msg: String) {
        mSwipeRefresh.post { mSwipeRefresh.isRefreshing = false }
        isRefresh = false
        coins_null.visibility = View.VISIBLE
        mRecyclerView.visibility = View.GONE
    }

    fun onRefresh() {
        isRefresh = true
        mPresenter.getChannelList(0, 10)
    }
}