package com.mvc.topay.and.topay_android.adapter.recyclerAdapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mvc.topay.and.topay_android.R

class RateAdapter(layoutResId: Int, data: List<String>?) : BaseQuickAdapter<String, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder, item: String) {
        helper.setText(R.id.rate_item_content, item.substring(1, item.length))
        helper.addOnClickListener(R.id.rate_item_content)
    }
}
