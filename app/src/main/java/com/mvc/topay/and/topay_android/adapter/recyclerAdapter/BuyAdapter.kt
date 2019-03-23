package com.mvc.topay.and.topay_android.adapter.recyclerAdapter

import android.annotation.SuppressLint
import android.support.v4.content.ContextCompat
import android.widget.LinearLayout
import android.widget.TextView
import com.blankj.utilcode.util.TimeUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.bean.ChannelBean
import com.mvc.topay.and.topay_android.utils.TextUtils
import java.text.SimpleDateFormat

class BuyAdapter(layoutResId: Int, data: List<ChannelBean.DataBean>) : BaseQuickAdapter<ChannelBean.DataBean, BaseViewHolder>(layoutResId, data) {
    private val WAIT = 0
    private val PAYED = 1
    private val COMPLETE = 2
    private val CANCEL = 4
    private val FAIL = 9
    private val STATUS_BUY = 1

    @SuppressLint("SimpleDateFormat")
    override fun convert(helper: BaseViewHolder, item: ChannelBean.DataBean) {
        if(item.orderType == STATUS_BUY){
            helper.setText(R.id.coins_buying_name, "${mContext.getString(R.string.coins_title_status_buy)} ${item.tokenName}")
        }else{
            helper.setText(R.id.coins_buying_name, "${mContext.getString(R.string.coins_title_status_sell)} ${item.tokenName}")
        }
        helper.setText(R.id.coins_price, "${TextUtils.doubleToFour(item.tokenValue)} ${item.tokenName}")
        var statusTv = helper.getView<TextView>(R.id.coins_status)
        var coinsLayout = helper.getView<LinearLayout>(R.id.coins_layout)
        coinsLayout.setBackgroundResource(R.drawable.shape_coins_bg)
        when (item.orderStatus) {
            WAIT -> {
                statusTv.text = mContext.getString(R.string.coins_status_wait)
                statusTv.setTextColor(ContextCompat.getColor(mContext, R.color.coins_status_wait))
            }
            PAYED -> {
                statusTv.text = mContext.getString(R.string.coins_status_payed)
                statusTv.setTextColor(ContextCompat.getColor(mContext, R.color.coins_status_payed))
            }
            COMPLETE -> {
                statusTv.text = mContext.getString(R.string.coins_status_complete)
                statusTv.setTextColor(ContextCompat.getColor(mContext, R.color.coins_status_complete))

            }
            CANCEL -> {
                coinsLayout.setBackgroundResource(R.drawable.shape_coins_un_bg)
                statusTv.text = mContext.getString(R.string.coins_status_cancel)
                statusTv.setTextColor(ContextCompat.getColor(mContext, R.color.coins_status_cancel))

            }
            FAIL -> {
                statusTv.text = mContext.getString(R.string.coins_status_failed)
                statusTv.setTextColor(ContextCompat.getColor(mContext, R.color.coins_status_failed))
            }
        }
        helper.setText(R.id.coins_order, item.orderNumber)
        helper.setText(R.id.coins_time, TimeUtils.millis2String(item.createdAt, SimpleDateFormat("yyyy-MM-dd HH:mm:ss")))
        helper.addOnClickListener(R.id.coins_layout)
    }
}