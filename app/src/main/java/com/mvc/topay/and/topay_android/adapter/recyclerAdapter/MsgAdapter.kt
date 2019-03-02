package com.mvc.topay.and.topay_android.adapter.recyclerAdapter

import android.annotation.SuppressLint
import com.blankj.utilcode.util.TimeUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.bean.MsgBean
import java.text.SimpleDateFormat

class MsgAdapter(resId: Int, msgBean: ArrayList<MsgBean.DataBean>) : BaseQuickAdapter<MsgBean.DataBean, BaseViewHolder>(resId, msgBean) {
    @SuppressLint("SimpleDateFormat")
    override fun convert(helper: BaseViewHolder, item: MsgBean.DataBean) {
        helper.setText(R.id.item_content, item.message)
        helper.setText(R.id.item_time, TimeUtils.millis2String(item.createdAt, SimpleDateFormat("yyyy-MM-dd HH:mm:ss")))
    }
}