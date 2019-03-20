package com.mvc.topay.and.topay_android.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import java.util.ArrayList

class OtcPagerAdapter(fm: FragmentManager, private val fragments: ArrayList<Fragment>) : FragmentPagerAdapter(fm) {
    private val titles = arrayOf("购买", "出售")

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }
}