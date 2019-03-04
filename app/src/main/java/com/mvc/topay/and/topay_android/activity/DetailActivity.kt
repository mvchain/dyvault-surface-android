package com.mvc.topay.and.topay_android.activity

import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.base.BaseMVPActivity
import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.bean.DetailBean
import com.mvc.topay.and.topay_android.constract.IDetailContract
import com.mvc.topay.and.topay_android.presenter.DetailPresenter

class DetailActivity : BaseMVPActivity<IDetailContract.DetailView, IDetailContract.DetailPresenter>(), IDetailContract.DetailView {
    override fun initMVPData() {
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_detail
    }

    override fun initPresenter(): BasePresenter<*, *> {
        return DetailPresenter.newInstance()
    }

    override fun detailSuccess(detailBean: DetailBean.DataBean) {

    }

    override fun detailFailed(msg: String) {
    }


}