package com.mvc.topay.and.topay_android.adapter.recyclerAdapter

import android.annotation.SuppressLint
import android.widget.TextView
import com.blankj.utilcode.util.SPUtils
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.base.AssetListBean
import com.mvc.topay.and.topay_android.common.Constant.SP.DEFAULT_SYMBOL
import com.mvc.topay.and.topay_android.utils.TextUtils
import de.hdodenhof.circleimageview.CircleImageView

class WalletAssetsAdapter(layoutRes: Int, dateBean: ArrayList<AssetListBean.DataBean>) : BaseQuickAdapter<AssetListBean.DataBean, BaseViewHolder>(layoutRes, dateBean) {
    @SuppressLint("SetTextI18n")
    override fun convert(helper: BaseViewHolder, item: AssetListBean.DataBean) {
        val tokenName = item.tokenName
        val type = helper.getView<TextView>(R.id.item_assets_type)
        val actual = helper.getView<TextView>(R.id.item_assets_actual)
        val icon = helper.getView<CircleImageView>(R.id.item_assets_icon)
        val money = helper.getView<TextView>(R.id.item_assets_money)
        helper.addOnClickListener(R.id.item_assets_layout) //add onclick to the layout to jump startActivity
        type.setText(item.tokenName)
        money.text = SPUtils.getInstance().getString(DEFAULT_SYMBOL) + TextUtils.rateToPrice(item.ratio * item.value)
        actual.text = "${TextUtils.doubleToFour(item.value)} $tokenName"
//        val options = RequestOptions().fallback(R.drawable.default_project).placeholder(R.drawable.loading_img).error(R.drawable.default_project)
        Glide.with(mContext).load(item.tokenImage)/*.apply(options)*/.into(icon)
    }
}