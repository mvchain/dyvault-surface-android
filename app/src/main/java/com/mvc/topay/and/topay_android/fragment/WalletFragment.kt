package com.mvc.topay.and.topay_android.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import com.blankj.utilcode.util.SPUtils
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.activity.BuyingCoinsActivity
import com.mvc.topay.and.topay_android.activity.HistoryActivity
import com.mvc.topay.and.topay_android.activity.IncreaseCurrencyActivity
import com.mvc.topay.and.topay_android.activity.MsgActivity
import com.mvc.topay.and.topay_android.adapter.recyclerAdapter.WalletAssetsAdapter
import com.mvc.topay.and.topay_android.base.*
import com.mvc.topay.and.topay_android.common.Constant.SP.ASSETS_LIST
import com.mvc.topay.and.topay_android.common.Constant.SP.BALANCE
import com.mvc.topay.and.topay_android.common.Constant.SP.DEFAULT_SYMBOL
import com.mvc.topay.and.topay_android.common.Constant.SP.RATE_LIST
import com.mvc.topay.and.topay_android.common.Constant.SP.SET_RATE
import com.mvc.topay.and.topay_android.constract.IWalletContract
import com.mvc.topay.and.topay_android.event.WalletAssetsListEvent
import com.mvc.topay.and.topay_android.listener.IPopViewListener
import com.mvc.topay.and.topay_android.presenter.WalletPresenter
import com.mvc.topay.and.topay_android.utils.JsonHelper
import com.mvc.topay.and.topay_android.utils.TextUtils
import com.mvc.topay.and.topay_android.utils.ViewDrawUtils
import com.mvc.topay.and.topay_android.view.FadingRecyclerView
import com.mvc.topay.and.topay_android.view.PopViewHelper
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class WalletFragment : BaseMVPFragment<IWalletContract.WalletView, IWalletContract.WalletPresenter>(), IWalletContract.WalletView {
    override fun networkError() {
        showToast("服务器繁忙")
        mWalletRefresh.post { mWalletRefresh.isRefreshing = false }
    }

    override fun balanceSuccess(balanceBean: BalanceBean) {
        SPUtils.getInstance().put(BALANCE, JsonHelper.jsonToString(balanceBean))
        mWalletBalance.text = TextUtils.rateToPrice(balanceBean.data)
    }

    private lateinit var walletAdapter: WalletAssetsAdapter
    private lateinit var assetsList: ArrayList<AssetLanguageBean.DataBean>
    private lateinit var mWalletRecyclerView: FadingRecyclerView
    private lateinit var mWalletAddCurrency: ImageView
    private lateinit var mWalletMsg: ImageView
    private lateinit var mWalletBalance: TextView
    private lateinit var mWalletRate: TextView
    private lateinit var mWalletBuyingCoins: TextView
    private lateinit var mWalletRefresh: SwipeRefreshLayout
    private lateinit var mPopView: PopupWindow
    private lateinit var mExchange: ArrayList<ExchangeRateBean.DataBean>
    private var createCarryOut: Boolean = false


    private var isRefresh = false
    override fun assetsSuccess(assetListBean: AssetLanguageBean) {
        SPUtils.getInstance().put(ASSETS_LIST, JsonHelper.jsonToString(assetListBean))
        assetsList.clear()
        var dataBean = assetListBean.data
        assetsList.addAll(dataBean)
        walletAdapter.notifyDataSetChanged()
        mWalletRefresh.post { mWalletRefresh.isRefreshing = false }
        initPop()
        var defaultRate = SPUtils.getInstance().getString(SET_RATE)
        if (defaultRate !== "") {
            val defaultBean = JsonHelper.stringToJson(
                    defaultRate, ExchangeRateBean.DataBean::class.java) as ExchangeRateBean.DataBean
            val default_type = defaultBean.name
            mWalletRate.text = default_type.substring(1, default_type.length)
        }
    }

    override fun initView() {
        this.mWalletRecyclerView = mRootView!!.findViewById(R.id.wallet_rv)
        this.mWalletAddCurrency = mRootView!!.findViewById(R.id.wallet_add_currency)
        this.mWalletMsg = mRootView!!.findViewById(R.id.wallet_msg)
        this.mWalletBalance = mRootView!!.findViewById(R.id.wallet_balance)
        this.mWalletRate = mRootView!!.findViewById(R.id.wallet_rate)
        this.mWalletBuyingCoins = mRootView!!.findViewById(R.id.wallet_buying_coins)
        this.mWalletRefresh = mRootView!!.findViewById(R.id.wallet_swipe)
        this.isRefresh = true
        mExchange = ArrayList()
        mWalletBuyingCoins.setOnClickListener {
            startActivity(Intent(mActivity, BuyingCoinsActivity::class.java))
        }
        mWalletRate.setOnClickListener {
            if (mPopView != null) {
                if (mPopView.isShowing) {
                    mPopView.dismiss()
                } else {
                    mPopView.showAsDropDown(mWalletRate, (mWalletRate.left / 2), 0, Gravity.CENTER)
                    ViewDrawUtils.setRigthDraw(ContextCompat.getDrawable(mActivity, R.drawable.arrow)!!, mWalletRate)
                }
            }
        }
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
                    hisIntent.putExtra("rateType", mWalletRate.text.toString())
                    hisIntent.putExtra("tokenName", assetsList[position].tokenName)
                    startActivity(hisIntent)
                }
            }
        }
        mWalletRefresh.post { mWalletRefresh.isRefreshing = true }
        mWalletRefresh.setOnRefreshListener(this::onRefresh)
        createCarryOut = true
    }

    override fun initData() {
        super.initData()
        mPresenter.getAllAssets()
        mPresenter.getBalance()
    }
//    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
//        super.setUserVisibleHint(isVisibleToUser)
//        if (isVisibleToUser && createCarryOut) {
//            onRefresh()
//        }
//    }

    private fun initPop() {
        val content = java.util.ArrayList<String>()
        val rate_list = SPUtils.getInstance().getString(RATE_LIST)
        if (rate_list != "") {
            val rateBean = JsonHelper.stringToJson(rate_list, ExchangeRateBean::class.java) as ExchangeRateBean
            for (dataBean in rateBean.data) {
                content.add(dataBean.name)
                mExchange.add(dataBean)
            }
            mPopView = PopViewHelper.instance.create(mActivity, R.layout.layout_rate_pop, content, object : IPopViewListener {
                override fun changeRate(position: Int) {
                    val dataBean = mExchange[position]
                    SPUtils.getInstance().put(SET_RATE, JsonHelper.jsonToString(dataBean))
                    val symbol = dataBean.name
                    val newSymbol = symbol.substring(0, 1)
                    SPUtils.getInstance().put(DEFAULT_SYMBOL, "$newSymbol ")
                    val default_type = dataBean.name
                    mWalletRate.text = default_type.substring(1, default_type.length)
                    val assetBean = JsonHelper.stringToJson(SPUtils.getInstance().getString(BALANCE), BalanceBean::class.java) as BalanceBean
                    mWalletBalance.text = TextUtils.rateToPrice(assetBean.data)
                    walletAdapter.notifyDataSetChanged()
                }

                override fun dismiss() {

                }
            })
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && createCarryOut) {
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
        mPresenter.getBalance()
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