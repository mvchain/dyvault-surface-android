package com.mvc.topay.and.topay_android.activity

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Message
import android.support.v4.content.ContextCompat
import android.text.format.Time
import android.view.View
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.TimeUtils
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.base.BaseMVPActivity
import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.bean.BuyDetailBean
import com.mvc.topay.and.topay_android.bean.ChannelBean
import com.mvc.topay.and.topay_android.constract.IBuyingDetailContract
import com.mvc.topay.and.topay_android.presenter.BuyingDetailPresenter
import com.mvc.topay.and.topay_android.utils.TextUtils
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_buying_detail.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class BuyingDetailActivity : BaseMVPActivity<IBuyingDetailContract.BuyingDetailView, IBuyingDetailContract.BuyingDetailPresenter>(), IBuyingDetailContract.BuyingDetailView {
    private var buyingId = 0
    private val WAIT = 0
    private val PAYED = 1
    private val COMPLETE = 2
    private val CANCEL = 4
    private val FAIL = 9
    private val payTypeArray = ArrayList<String>()
    private var subscribe: Disposable? = null

    private var remainingCollectionTime = 0L
    override fun initMVPData() {
        mPresenter.getBuyingDetail(buyingId)
    }

    override fun initPresenter(): BasePresenter<*, *> {
        return BuyingDetailPresenter.newInstance()
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun buyingDetailSuccess(buydetailBean: BuyDetailBean.DataBean) {
        when (buydetailBean.orderStatus) {
            WAIT -> {
                buy_detail_status.text = getString(R.string.coins_status_wait)
                buy_detail_status.setTextColor(ContextCompat.getColor(baseContext, R.color.coins_status_wait))
                remaining_time.visibility = View.VISIBLE
                remainingCollectionTime = buydetailBean.limitTime
                buy_detail_time.text = TimeUtils.date2String(Date(remainingCollectionTime), SimpleDateFormat(getString(R.string.buy_detail_remaining_time)))
                subscribe = Observable.interval(1, TimeUnit.SECONDS)
                        .flatMap {
                            remainingCollectionTime -= 1000
                            buy_detail_time.text = TimeUtils.date2String(Date(remainingCollectionTime), SimpleDateFormat(getString(R.string.buy_detail_remaining_time)))
                            Observable.just(remainingCollectionTime)
                        }.takeUntil {
                            if (remainingCollectionTime <= 0) {
                                mPresenter.getBuyingDetail(buyingId)
                                true
                            } else {
                                false
                            }
                        }.subscribe()
            }
            PAYED -> {
                buy_detail_status.text = getString(R.string.coins_status_payed)
                buy_detail_status.setTextColor(ContextCompat.getColor(baseContext, R.color.coins_status_payed))
                payment_layout.visibility = View.VISIBLE
                order_line.visibility = View.VISIBLE
                buyer_payment_method.text = payTypeArray[buydetailBean.payType - 1]
                payment_account.text = buydetailBean.payAccount
            }
            COMPLETE -> {
                buy_detail_status.text = getString(R.string.coins_status_complete)
                buy_detail_status.setTextColor(ContextCompat.getColor(baseContext, R.color.coins_status_complete))

            }
            CANCEL -> {
                buy_detail_status.text = getString(R.string.coins_status_cancel)
                buy_detail_status.setTextColor(ContextCompat.getColor(baseContext, R.color.coins_status_cancel))

            }
            FAIL -> {
                buy_detail_status.text = getString(R.string.coins_status_failed)
                buy_detail_status.setTextColor(ContextCompat.getColor(baseContext, R.color.coins_status_failed))
            }
        }
        buy_detail_status_hint.text = "${if(buydetailBean.orderType == 1) getString(R.string.page_buy) else getString(R.string.page_sell)} ${buydetailBean.tokenName}"
        order_amount.text = "${TextUtils.doubleToFour(buydetailBean.amount)}${buydetailBean.tokenName}"
        order_number.text = buydetailBean.orderNumber
        order_buyer.text = buydetailBean.buyUsername
        order_price.text = TextUtils.doubleToFour(buydetailBean.price)
        order_quantity.text = TextUtils.doubleToFour(buydetailBean.tokenValue)
        order_time.text = TimeUtils.millis2String(buydetailBean.createdAt, SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
    }

    override fun buyingDetailFailed(msg: String) {

    }

    override fun onDestroy() {
        super.onDestroy()
        subscribe?.dispose()
    }

    override fun initView() {
        super.initView()
        buyingId = intent.getIntExtra("buyingId", 0)
        payTypeArray.add(getString(R.string.pay_type_bank))
        payTypeArray.add(getString(R.string.pay_type_alipay))
        payTypeArray.add(getString(R.string.pay_type_wechat))
        back.setOnClickListener { finish() }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_buying_detail
    }
}