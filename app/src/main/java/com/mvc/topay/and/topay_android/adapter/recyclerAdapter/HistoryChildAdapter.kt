package com.mvc.topay.and.topay_android.adapter.recyclerAdapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mvc.topay.and.topay_android.bean.TransactionsBean

class HistoryChildAdapter(layoutResId: Int, data: List<TransactionsBean.DataBean>?) : BaseQuickAdapter<TransactionsBean.DataBean, BaseViewHolder>(layoutResId, data)  {
    override fun convert(helper: BaseViewHolder?, item: TransactionsBean.DataBean?) {
        
    }
}