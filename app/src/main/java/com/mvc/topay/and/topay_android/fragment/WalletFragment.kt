package com.mvc.topay.and.topay_android.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.widget.ImageView
import android.widget.TextView
import com.blankj.utilcode.util.SPUtils
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.activity.HistoryActivity
import com.mvc.topay.and.topay_android.activity.IncreaseCurrencyActivity
import com.mvc.topay.and.topay_android.activity.MsgActivity
import com.mvc.topay.and.topay_android.adapter.recyclerAdapter.WalletAssetsAdapter
import com.mvc.topay.and.topay_android.base.AssetListBean
import com.mvc.topay.and.topay_android.base.BalanceBean
import com.mvc.topay.and.topay_android.base.BaseMVPFragment
import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.common.Constant.SP.ASSETS_LIST
import com.mvc.topay.and.topay_android.constract.IWalletContract
import com.mvc.topay.and.topay_android.event.WalletAssetsListEvent
import com.mvc.topay.and.topay_android.presenter.WalletPresenter
import com.mvc.topay.and.topay_android.utils.JsonHelper
import com.mvc.topay.and.topay_android.utils.TextUtils
import com.mvc.topay.and.topay_android.view.FadingRecyclerView
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class WalletFragment : BaseMVPFragment<IWalletContract.WalletView, IWalletContract.WalletPresenter>(), IWalletContract.WalletView {
    override fun networkError() {
        showToast("服务器繁忙")
        mWalletRefresh.post { mWalletRefresh.isRefreshing = false }
    }

    override fun balanceSuccess(balanceBean: BalanceBean) {
        mWalletBalance.text = TextUtils.doubleToDouble(balanceBean.data)
    }

    private lateinit var walletAdapter: WalletAssetsAdapter
    private lateinit var assetsList: ArrayList<AssetListBean.DataBean>
    private lateinit var mWalletRecyclerView: FadingRecyclerView
    private lateinit var mWalletAddCurrency: ImageView
    private lateinit var mWalletMsg: ImageView
    private lateinit var mWalletBalance: TextView
    private lateinit var mWalletRate: TextView
    private lateinit var mWalletBuyingCoins: TextView
    private lateinit var mWalletRefresh: SwipeRefreshLayout
    private var isRefresh = false
    override fun assetsSuccess(assetListBean: AssetListBean) {
        SPUtils.getInstance().put(ASSETS_LIST, JsonHelper.jsonToString(assetListBean))
        assetsList.clear()
        var dataBean = assetListBean.data
        assetsList.addAll(dataBean)
        walletAdapter.notifyDataSetChanged()
        mWalletRefresh.post { mWalletRefresh.isRefreshing = false }
    }

    override fun initView() {
        this.mWalletRecyclerView = mRootView!!.findViewById(R.id.wallet_rv)
        this.mWalletAddCurrency = mRootView!!.findViewById(R.id.wallet_add_currency)
        this.mWalletMsg = mRootView!!.findViewById(R.id.wallet_msg)
        this.mWalletBalance = mRootView!!.findViewById(R.id.wallet_balance)
        this.mWalletRate = mRootView!!.findViewById(R.id.wallet_buying_coins)
        this.mWalletBuyingCoins = mRootView!!.findViewById(R.id.wallet_rate)
        this.mWalletRefresh = mRootView!!.findViewById(R.id.wallet_swipe)
        this.isRefresh = true
        mWalletAddCurrency.setOnClickListener {
            if (SPUtils.getInstance().getString(ASSETS_LIST) === "") {
                showToast("服务器繁忙")
            } else {
                startActivity(Intent(mActivity, IncreaseCurrencyActivity::class.java))
            }
        }
        mWalletMsg.setOnClickListener { startActivity(Intent(mActivity, MsgActivity::class.java)) }
        assetsList = ArrayList()
        walletAdapter = WalletAssetsAdapter(R.layout.item_home_assets_type, assetsList)
        mWalletRecyclerView.layoutManager = object : LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        mWalletRecyclerView.adapter = walletAdapter
        walletAdapter.setOnItemChildClickListener { _, view, position ->
            when (view.id) {
                R.id.item_assets_layout -> {
                    var tokenId = assetsList[position].tokenId
                    var hisIntent = Intent(mActivity, HistoryActivity::class.java)
                    hisIntent.putExtra("tokenId", tokenId)
                    hisIntent.putExtra("rateType", mWalletBuyingCoins.text.toString())
                    hisIntent.putExtra("tokenName", assetsList[position].tokenName)
                    startActivity(hisIntent)
                }
            }
        }
        mWalletRefresh.post { mWalletRefresh.isRefreshing = true }
        mWalletRefresh.setOnRefreshListener(this::onRefresh)
    }

    override fun initData() {
        super.initData()
        mPresenter.getAllAssets()
        mPresenter.getBalance()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && isRefresh) {
            onRefresh()
        }
    }

    private fun onRefresh() {
        mPresenter.getAllAssets()
        mPresenter.getBalance()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_wallet
    }

    override fun initPresenter(): BasePresenter<*, *> {
        return WalletPresenter.newInstance()
    }

    @Subscribe
    fun eventRefresh(walletAssetsListEvent: WalletAssetsListEvent) {
        mPresenter.getAllAssets()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }
}