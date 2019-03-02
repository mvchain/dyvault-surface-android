package com.mvc.topay.and.topay_android.adapter.recyclerAdapter

import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.blankj.utilcode.util.SPUtils
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mvc.topay.and.topay_android.R

import com.mvc.topay.and.topay_android.bean.IncreaseBean
import com.mvc.topay.and.topay_android.common.Constant.LANGUAGE.CHINESE
import com.mvc.topay.and.topay_android.common.Constant.LANGUAGE.DEFAULT_LANGUAGE

class IncreaseAdapter(layoutResId: Int, data: List<IncreaseBean>?) : BaseQuickAdapter<IncreaseBean, BaseViewHolder>(layoutResId, data) {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun convert(helper: BaseViewHolder, item: IncreaseBean) {
        val icon = helper.getView<ImageView>(R.id.item_increase_icon)
        val add = helper.getView<TextView>(R.id.item_increase_add)
        helper.setText(R.id.item_increase_title, item.title)
        if (SPUtils.getInstance().getString(DEFAULT_LANGUAGE) == CHINESE) {
            helper.setText(R.id.item_increase_content, item.zhContent)
        } else {
            helper.setText(R.id.item_increase_content, item.enContent)
        }
        add.visibility = if (item.isVisible) View.VISIBLE else View.GONE
        if (!item.isAdd) {
            add.background = ContextCompat.getDrawable(mContext, R.drawable.shape_remove_increase_16dp)
            add.setText(R.string.increase_remove)
        } else {
            add.background = ContextCompat.getDrawable(mContext, R.drawable.shape_add_increase_16dp)
            add.setText(R.string.increase_add)
        }
        Glide.with(mContext).load(item.resId).into(icon)
        helper.addOnClickListener(R.id.item_increase_add)
    }
}
