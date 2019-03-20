package com.mvc.topay.and.topay_android.fragment

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.adapter.OtcPagerAdapter
import com.mvc.topay.and.topay_android.base.BaseFragment

class OtcFragment : BaseFragment() {
    private lateinit var otcFragment: ArrayList<Fragment>
    private lateinit var otcAdapter: OtcPagerAdapter
    private lateinit var otcVp: ViewPager
    private lateinit var otcTab: TabLayout

    override fun initData() {

    }

    override fun initView() {
        otcFragment = ArrayList()
        otcVp = mRootView!!.findViewById(R.id.otc_vp)
        otcTab = mRootView!!.findViewById(R.id.otc_tab)
        var buyingFragment = ChildOtcFragment()
        var buyingBundle = Bundle()
        buyingBundle.putInt("otc_type", 0)
        buyingFragment.arguments = buyingBundle
        otcFragment.add(buyingFragment)
        var sellingFragment = ChildOtcFragment()
        var sellingBundle = Bundle()
        sellingBundle.putInt("otc_type", 1)
        sellingFragment.arguments = sellingBundle
        otcFragment.add(sellingFragment)
        otcAdapter = OtcPagerAdapter(childFragmentManager, otcFragment)
        otcVp.adapter = otcAdapter
        otcTab.setupWithViewPager(otcVp)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_otc
    }
}