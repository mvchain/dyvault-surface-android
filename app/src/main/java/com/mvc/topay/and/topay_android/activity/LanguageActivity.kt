package com.mvc.topay.and.topay_android.activity

import com.gyf.barlibrary.ImmersionBar
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.base.BaseActivity
import com.mvc.topay.and.topay_android.common.Constant.LANGUAGE.ACCEPT_CHINESE
import com.mvc.topay.and.topay_android.common.Constant.LANGUAGE.ACCEPT_ENGLISH
import com.mvc.topay.and.topay_android.common.Constant.LANGUAGE.CHINESE
import com.mvc.topay.and.topay_android.common.Constant.LANGUAGE.ENGLISH
import com.mvc.topay.and.topay_android.utils.LanguageUtils
import kotlinx.android.synthetic.main.activity_language.*

class LanguageActivity : BaseActivity() {
    lateinit var default_language: String
    override fun getLayoutId(): Int {
        return R.layout.activity_language
    }

    override fun initData() {
        switch_china.setOnSuperTextViewClickListener {
            LanguageUtils.changeLocale(CHINESE, ACCEPT_CHINESE, resources.configuration, baseContext)
            switch_china.setRightIcon(R.drawable.language_selected_icon)
            switch_english.setRightIcon(null)
            recreate()
        }
        switch_english.setOnSuperTextViewClickListener {
            LanguageUtils.changeLocale(ENGLISH, ACCEPT_ENGLISH, resources.configuration, baseContext)
            switch_english.setRightIcon(R.drawable.language_selected_icon)
            switch_china.setRightIcon(null)
            recreate()
        }
        language_back.setOnClickListener {
            finish()
        }
    }

    override fun initView() {
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init()
        default_language = LanguageUtils.getUserSetLocal()
        if (default_language == CHINESE) {
            switch_china.setRightIcon(R.drawable.language_selected_icon)
            switch_english.setRightIcon(null)
        } else if (default_language == ENGLISH) {
            switch_english.setRightIcon(R.drawable.language_selected_icon)
            switch_china.setRightIcon(null)
        }
    }
}
