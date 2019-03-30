package com.mvc.topay.and.topay_android.adapter.recyclerAdapter

import android.annotation.SuppressLint
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

class HistoryChildAdapter(layoutResId: Int, data: List<TransactionsBean.DataBean>?) : BaseQuickAdapter<TransactionsBean.DataBean, BaseViewHolder>(layoutResId, data) {
    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun convert(helper: BaseViewHolder, item: TransactionsBean.DataBean) {
        val icon = helper.getView<ImageView>(R.id.his_child_icon)
        val title = helper.getView<TextView>(R.id.his_child_title)
        val time = helper.getView<TextView>(R.id.his_child_time)
        val price = helper.getView<TextView>(R.id.his_child_price)
        val status = helper.getView<TextView>(R.id.his_child_status)
        var sb = StringBuffer()
        var statusSb = StringBuffer()
        if (item.classify == 0) {
            if (item.transactionType == 1) {
                Glide.with(mContext).load(R.drawable.recharge).into(icon)
                sb.append(mContext.getString(R.string.top_up_from))
                statusSb.append(mContext.getString(R.string.recharge))
            } else {
                Glide.with(mContext).load(R.drawable.withdraw).into(icon)
                sb.append(mContext.getString(R.string.withdraw_mention))
                statusSb.append(mContext.getString(R.string.withdraw))
            }
            status.visibility = View.VISIBLE
            when (item.status) {
                0, 1 -> {
                    status.text = mContext.getString(R.string.waiting)
                    status.setTextColor(Color.parseColor("#AFAFAF"))
                }
                2 -> {
                    statusSb.append(mContext.getString(R.string.success))
                    status.text = statusSb.toString()
                    status.setTextColor(Color.parseColor("#7FD43F"))
                }
                9 -> {
                    statusSb.append(mContext.getString(R.string.failed))
                    status.text = statusSb.toString()
                    status.setTextColor(Color.RED)
                }
            }
        } else if (item.classify == 5) {
            if (item.transactionType == 1) {
                Glide.with(mContext).load(R.drawable.receive).into(icon)
                sb.append(mContext.getString(R.string.collection_from))
            } else {
                Glide.with(mContext).load(R.drawable.transfer).into(icon)
                sb.append(mContext.getString(R.string.transfer_go))
            }
            status.visibility = View.INVISIBLE
        }else if(item.classify == 6){
            if (item.transactionType == 1) {
                Glide.with(mContext).load(R.drawable.receive).into(icon)
                sb.append(mContext.getString(R.string.collection_from))
            } else {
                Glide.with(mContext).load(R.drawable.pay_icon).into(icon)
                sb.append(mContext.getString(R.string.pay_go))
            }
            status.visibility = View.INVISIBLE
        }
        title.text = sb.append(if (item.transactionType == 1) item.fromAddress else item.toAddress).toString()
        time.text = TimeUtils.millis2String(item.createdAt, SimpleDateFormat("yyyy-MM-dd HH:mm:ss") as DateFormat)
        price.text = "${if (item.transactionType == 1) "+" else "-"}${TextUtils.toBigDecimal(item.value)}"
        helper.addOnClickListener(R.id.his_layout)
    }
}