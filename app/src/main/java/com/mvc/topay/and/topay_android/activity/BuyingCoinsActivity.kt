package com.mvc.topay.and.topay_android.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.adapter.BuyingPagerAdapter
import com.mvc.topay.and.topay_android.base.BaseActivity
import com.mvc.topay.and.topay_android.fragment.BuyingCoinsFragment
import kotlinx.android.synthetic.main.activity_buying_coins.*
import java.util.ArrayList

class BuyingCoinsActivity : BaseActivity() {
    private lateinit var mFragments: ArrayList<Fragment>
    private lateinit var mBuyingAdapter: BuyingPagerAdapter

    override fun initData() {

    }

    override fun initView() {
        super.initView()
        mFragments = ArrayList()
        var allFragment = BuyingCoinsFragment()
        var allBundle = Bundle()
        allBundle.putInt("type", 0)
        allFragment.arguments = allBundle
        mFragments.add(allFragment)
        var loadFragment = BuyingCoinsFragment()
        var loadBundle = Bundle()
        loadBundle.putInt("type", 1)
        loadFragment.arguments = loadBundle
        mFragments.add(loadFragment)
        var completedFragment = BuyingCoinsFragment()
        var completedBundle = Bundle()
        completedBundle.putInt("type", 2)
        completedFragment.arguments = completedBundle
        mFragments.add(completedFragment)
        var cancelFragment = BuyingCoinsFragment()
        var cancelBundle = Bundle()
        cancelBundle.putInt("type", 4)
        cancelFragment.arguments = cancelBundle
        mFragments.add(cancelFragment)
        mBuyingAdapter = BuyingPagerAdapter(supportFragmentManager, mFragments)
        coins_tab.setupWithViewPager(coins_vp)
        coins_vp.adapter = mBuyingAdapter
        buying_back.setOnClickListener { finish() }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_buying_coins
    }
}