package com.mvc.topay.and.topay_android.adapter.recyclerAdapter

import android.graphics.Color
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
import java.text.DateFormat
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
        var statusSb = StringBuffer()
        if (item.classify === 0) {
            if (item.transactionType === 1) {
                Glide.with(mContext).load(R.drawable.recharge).into(icon)
                sb.append("充值：来自")
                statusSb.append("充值")
            } else {
                Glide.with(mContext).load(R.drawable.withdraw).into(icon)
                sb.append("提现：提到")
                statusSb.append("提现")
            }
            status.visibility = View.VISIBLE
            when (item.status) {
                0, 1 -> {
                    status.text = "等待中"
                    status.setTextColor(Color.parseColor("#AFAFAF"))
                }
                2 -> {
                    statusSb.append("成功")
                    status.text = statusSb.toString()
                    status.setTextColor(Color.parseColor("#7FD43F"))
                }
                9 -> {
                    statusSb.append("失败")
                    status.text = statusSb.toString()
                    status.setTextColor(Color.RED)
                }
            }
        } else if (item.classify === 5) {
            if (item.transactionType === 1) {
                Glide.with(mContext).load(R.drawable.receive).into(icon)
                sb.append("收款：来自")
            } else {
                Glide.with(mContext).load(R.drawable.transfer).into(icon)
                sb.append("转账：转到")
            }
            status.visibility = View.INVISIBLE
        }
        title.text = sb.append("${if (item.transactionType === 1) item.fromAddress else item.toAddress}").toString()
        time.text = TimeUtils.millis2String(item.createdAt, SimpleDateFormat("yyyy-MM-dd HH:mm:ss") as DateFormat)
        price.text = "${if (item.transactionType === 1) "+" else "-"}${TextUtils.toBigDecimal(item.value)}"
        helper.addOnClickListener(R.id.his_layout)
    }
}