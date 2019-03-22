package com.mvc.topay.and.topay_android.adapter.recyclerAdapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.bean.ChannelBean

class ChannelAdapter(layoutResId: Int, data: List<ChannelBean.DataBean>) : BaseQuickAdapter<ChannelBean.DataBean, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder, item: ChannelBean.DataBean) {
        helper.setText(R.id.coins_buying_name, "购买${item.channelName}")
        helper.setText(R.id.coins_price, "${item.contact}")
        helper.setText(R.id.coins_status, "${item.info}")
        helper.setText(R.id.coins_order, "${item.info}")
        helper.setText(R.id.coins_time, "${item.info}")
    }
}