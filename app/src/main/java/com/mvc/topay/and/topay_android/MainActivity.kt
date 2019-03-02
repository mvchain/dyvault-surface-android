package com.mvc.topay.and.topay_android

import android.support.v4.app.Fragment
import android.widget.RadioButton
import com.mvc.topay.and.topay_android.adapter.HomePagerAdapter
import com.mvc.topay.and.topay_android.base.BaseActivity
import com.mvc.topay.and.topay_android.event.LanguageEvent
import com.mvc.topay.and.topay_android.fragment.MineFragment
import com.mvc.topay.and.topay_android.fragment.WalletFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : BaseActivity() {
    private var isBack = false
    private val timer = Timer()
    private lateinit var mFragment: ArrayList<Fragment>
    private lateinit var pagerAdapter: HomePagerAdapter


    override fun initData() {
    }

    override fun initView() {
        super.initView()
        mFragment = ArrayList()
        var walletFragment = WalletFragment()
        mFragment.add(walletFragment)
        var mineFragment = MineFragment()
        mFragment.add(mineFragment)
        pagerAdapter = HomePagerAdapter(supportFragmentManager, mFragment)
        main_pager.adapter = pagerAdapter
        val childCount = main_group.childCount
        for (i in 0 until childCount) {
            main_group.getChildAt(i).setOnClickListener { main_pager.currentItem = i }
        }
        var page = intent.getIntExtra("page", -1)
        if (page !== -1) {
            main_pager.currentItem = page
            (main_group.getChildAt(page) as RadioButton).isChecked = true
        }
    }

    override fun onBackPressed() {
        if (isBack) {
            super.onBackPressed()
        } else {
            isBack = true
            timer.schedule(object : TimerTask() {
                override fun run() {
                    isBack = false
                }
            }, 1000)
            showToast(R.string.app_exit)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    @Subscribe
    fun changeLanguage(language: LanguageEvent) {
        recreate()
    }
}
