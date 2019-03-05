package com.mvc.topay.and.topay_android.adapter.recyclerAdapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.blankj.utilcode.util.TimeUtils
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.bean.TransactionsBean
import com.mvc.topay.and.topay_android.utils.TextUtils
import java.text.SimpleDateFormat

@Suppress("DEPRECATED_IDENTITY_EQUALS")
class HistoryChildAdapter(layoutResId: Int, data: List<TransactionsBean.DataBean>?) : BaseQuickAdapter<TransactionsBean.DataBean, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder, item: TransactionsBean.DataBean) {
        val icon = helper.getView<ImageView>(R.id.his_child_icon)
        val title = helper.getView<TextView>(R.id.his_child_title)
        val time = helper.getView<TextView>(R.id.his_child_time)
        val price = helper.getView<TextView>(R.id.his_child_price)
        val status = helper.getView<TextView>(R.id.his_child_status)
        var sb = StringBuffer()
        if (item.classify === 0) {
            if (item.transactionType === 1) {
                Glide.with(mContext).load(R.drawable.receive).into(icon)
                sb.append("收款：")
            } else {
                Glide.with(mContext).load(R.drawable.transfer).into(icon)
                sb.append("转账：")
            }
        } else if (item.classify === 5) {

            if (item.transactionType === 1) {
                Glide.with(mContext).load(R.drawable.recharge).into(icon)
                sb.append("充值：")
            } else {
                Glide.with(mContext).load(R.drawable.withdraw).into(icon)
                sb.append("提现：")
            }
        }
        title.text = sb.append("${item.orderRemark}").toString()
        time.text = TimeUtils.millis2String(item.createdAt, SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
        price.text = "${if (item.transactionType === 1) "+" else "-"}${TextUtils.toBigDecimal(item.value)}"
        status.visibility = View.INVISIBLE
        helper.addOnClickListener(R.id.his_layout)
    }
}