package com.mvc.topay.and.topay_android.adapter.recyclerAdapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.bean.ChannelBean

class ChannelAdapter(layoutResId: Int, data: List<ChannelBean.DataBean>) : BaseQuickAdapter<ChannelBean.DataBean, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder, item: ChannelBean.DataBean) {
        helper.setText(R.id.coins_name, "${item.channelName}")
        helper.setText(R.id.coins_contact, "${item.contact}")
        helper.setText(R.id.coins_info, "${item.info}")
    }
}