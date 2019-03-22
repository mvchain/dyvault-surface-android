package com.mvc.topay.and.topay_android.activity

import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.api.ApiStore
import com.mvc.topay.and.topay_android.base.BaseActivity
import com.mvc.topay.and.topay_android.utils.RetrofitUtils

class BuyingDetailActivity : BaseActivity() {
    private var buyingId = 0

    override fun initData() {
//        RetrofitUtils.client(ApiStore::class.java)
    }
    

    override fun initView() {
        super.initView()
        buyingId = intent.getIntExtra("buyingId",0)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_buying_detail
    }
}