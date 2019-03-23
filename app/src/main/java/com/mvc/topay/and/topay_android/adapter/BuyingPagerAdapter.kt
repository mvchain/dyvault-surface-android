package com.mvc.topay.and.topay_android.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.mvc.topay.and.topay_android.MyApplication
import com.mvc.topay.and.topay_android.R
import java.util.ArrayList

class BuyingPagerAdapter(fm: FragmentManager, private val fragments: ArrayList<Fragment>,private val titles :ArrayList<String>) : FragmentPagerAdapter(fm) {

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