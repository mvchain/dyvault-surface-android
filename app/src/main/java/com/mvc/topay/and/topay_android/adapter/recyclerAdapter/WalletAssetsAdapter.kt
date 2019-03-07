package com.mvc.topay.and.topay_android.adapter.recyclerAdapter

import android.annotation.SuppressLint
import android.widget.TextView
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.R.id.item_assets_introduction
import com.mvc.topay.and.topay_android.base.AssetLanguageBean
import com.mvc.topay.and.topay_android.base.AssetListBean
import com.mvc.topay.and.topay_android.common.Constant.LANGUAGE.CHINESE
import com.mvc.topay.and.topay_android.common.Constant.LANGUAGE.DEFAULT_LANGUAGE
import com.mvc.topay.and.topay_android.common.Constant.SP.DEFAULT_SYMBOL
import com.mvc.topay.and.topay_android.utils.TextUtils
import de.hdodenhof.circleimageview.CircleImageView

class WalletAssetsAdapter(layoutRes: Int, dateBean: ArrayList<AssetLanguageBean.DataBean>) : BaseQuickAdapter<AssetLanguageBean.DataBean, BaseViewHolder>(layoutRes, dateBean) {
    @SuppressLint("SetTextI18n")
    override fun convert(helper: BaseViewHolder, item: AssetLanguageBean.DataBean) {
        val tokenName = item.tokenName
        val type = helper.getView<TextView>(R.id.item_assets_type)
        val actual = helper.getView<TextView>(R.id.item_assets_actual)
        val icon = helper.getView<CircleImageView>(R.id.item_assets_icon)
        val money = helper.getView<TextView>(R.id.item_assets_money)
        val introduction = helper.getView<TextView>(R.id.item_assets_introduction)
        helper.addOnClickListener(R.id.item_assets_layout) //add onclick to the layout to jump startActivity
        type.text = item.tokenName
        money.text = SPUtils.getInstance().getString(DEFAULT_SYMBOL) + TextUtils.rateToPrice(item.ratio * item.value)
        actual.text = "${TextUtils.doubleToFour(item.value)} $tokenName"
        introduction.text = if (SPUtils.getInstance().getString(DEFAULT_LANGUAGE) == CHINESE) item.tokenCnName else item.tokenEnName
        Glide.with(mContext).load(item.tokenImage)/*.apply(options)*/.into(icon)
    }
}