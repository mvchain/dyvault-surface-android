package com.mvc.topay.and.topay_android.activity

import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.base.BaseActivity
import android.content.Intent
import android.os.Handler
import com.blankj.utilcode.util.SPUtils
import com.mvc.topay.and.topay_android.common.Constant.LANGUAGE.ACCEPT_CHINESE
import com.mvc.topay.and.topay_android.common.Constant.LANGUAGE.DEFAULT_ACCEPT_LANGUAGE


class StartActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_start
    }

    override fun initView() {
        super.initView()
        SPUtils.getInstance().put(DEFAULT_ACCEPT_LANGUAGE, ACCEPT_CHINESE)
        Handler().postDelayed({
            //            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                val intent = Intent(this, SelectLoginActivity::class.java)
//                val pair = Pair<View, String>(select_icon, "TransitionName")
//                val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pair)
//                ActivityCompat.startActivity(this, intent, optionsCompat.toBundle())
//                finish()
//            } else {
            startActivity(Intent(this, SelectLoginActivity::class.java))
            finish()
//            }
        }, 300)
    }

    override fun initData() {

    }
}