package com.mvc.topay.and.topay_android.activity

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.adapter.recyclerAdapter.ChannelAdapter
import com.mvc.topay.and.topay_android.base.BaseMVPActivity
import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.bean.ChannelBean
import com.mvc.topay.and.topay_android.constract.IBuyingContract
import com.mvc.topay.and.topay_android.presenter.BuyingPresenter
import kotlinx.android.synthetic.main.activity_buying_coins.*
import java.util.ArrayList

class BuyingCoinsActivity : BaseMVPActivity<IBuyingContract.BuyingView, IBuyingContract.BuyingPresenter>(), IBuyingContract.BuyingView {
    private lateinit var channelBean: ArrayList<ChannelBean.DataBean>
    private lateinit var channelAdapter: ChannelAdapter
    private var isRefresh = false
    override fun initMVPData() {
        isRefresh = true
        mPresenter.getChannelList(0, 10)
    }

    override fun initMVPView() {
        super.initMVPView()
        channelBean = ArrayList()
        channelAdapter = ChannelAdapter(R.layout.item_buying_coins, channelBean)
        coins_rv.adapter = channelAdapter
        coins_rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
        buying_back.setOnClickListener { finish() }
        coins_swipe.post { coins_swipe.isRefreshing = true }
        coins_swipe.setOnRefreshListener { onRefresh() }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_buying_coins
    }

    override fun initPresenter(): BasePresenter<*, *> {
        return BuyingPresenter.newInstance()
    }
    fun onRefresh(){
        isRefresh = true
        mPresenter.getChannelList(0, 10)
    }
    override fun channelSuccess(channelBean: ArrayList<ChannelBean.DataBean>) {
        coins_swipe.post { coins_swipe.isRefreshing = false }
        if (isRefresh) {
            isRefresh = false
            this.channelBean.clear()
        }
        this.channelBean.addAll(channelBean)
        if (this.channelBean.size > 0) {
            coins_null.visibility = View.GONE
            coins_rv.visibility = View.VISIBLE
            channelAdapter.notifyDataSetChanged()
        } else {
            coins_null.visibility = View.VISIBLE
            coins_rv.visibility = View.GONE
        }
    }

    override fun channelFailed(msg: String) {
        coins_swipe.post { coins_swipe.isRefreshing = false }
        isRefresh = false
        coins_null.visibility = View.VISIBLE
        coins_rv.visibility = View.GONE
    }
}