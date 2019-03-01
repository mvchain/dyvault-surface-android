package com.mvc.topay.and.topay_android.fragment

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.adapter.recyclerAdapter.WalletAssetsAdapter
import com.mvc.topay.and.topay_android.base.AssetListBean
import com.mvc.topay.and.topay_android.base.BaseMVPFragment
import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.constract.IWalletContract
import com.mvc.topay.and.topay_android.presenter.WalletPresenter
import com.mvc.topay.and.topay_android.view.FadingRecyclerView

class WalletFragment : BaseMVPFragment<IWalletContract.WalletView, IWalletContract.WalletPresenter>(), IWalletContract.WalletView {
    private lateinit var walletAdapter: WalletAssetsAdapter
    private lateinit var assetsList: ArrayList<AssetListBean.DataBean>
    private lateinit var mWalletRecyclerView: FadingRecyclerView
    private lateinit var mWalletAddCurrency: ImageView
    private lateinit var mWalletMsg: ImageView
    private lateinit var mWalletRate: TextView
    private lateinit var mWalletBuyingCoins: TextView
    private lateinit var mWalletRefresh: SwipeRefreshLayout
    private var isRefresh = false
    override fun assetsSuccess(assetListBean: AssetListBean) {
        assetsList.clear()
        var dataBean = assetListBean.data
        assetsList.addAll(dataBean)
        walletAdapter.notifyDataSetChanged()
        mWalletRefresh.post { mWalletRefresh.isRefreshing = false }
    }

    override fun assetsError() {
        mWalletRefresh.post { mWalletRefresh.isRefreshing = false }
        showToast("服务器繁忙", Gravity.CENTER)
    }

    override fun initView() {
        this.mWalletRecyclerView = mRootView!!.findViewById(R.id.wallet_rv)
        this.mWalletAddCurrency = mRootView!!.findViewById(R.id.wallet_add_currency)
        this.mWalletMsg = mRootView!!.findViewById(R.id.wallet_msg)
        this.mWalletRate = mRootView!!.findViewById(R.id.wallet_buying_coins)
        this.mWalletBuyingCoins = mRootView!!.findViewById(R.id.wallet_rate)
        this.mWalletRefresh = mRootView!!.findViewById(R.id.wallet_swipe)
        this.isRefresh = true
        assetsList = ArrayList()
        walletAdapter = WalletAssetsAdapter(R.layout.item_home_assets_type, assetsList)
        mWalletRecyclerView.layoutManager = object : LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        mWalletRecyclerView.adapter = walletAdapter
        mWalletRefresh.post { mWalletRefresh.isRefreshing = true }
        mWalletRefresh.setOnRefreshListener(this::onRefresh)
    }

    override fun initData() {
        super.initData()
        mPresenter.getAllAssets()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && isRefresh) {
            onRefresh()
        }
    }

    private fun onRefresh() {
        mPresenter.getAllAssets()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_wallet
    }

    override fun initPresenter(): BasePresenter<*, *> {
        return WalletPresenter.newInstance()
    }
}