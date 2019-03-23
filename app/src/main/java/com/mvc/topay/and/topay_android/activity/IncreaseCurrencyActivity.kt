package com.mvc.topay.and.topay_android.activity

import android.annotation.SuppressLint
import android.support.v4.content.ContextCompat
import android.text.TextWatcher
import android.view.View
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.mvc.topay.and.topay_android.MyApplication
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.adapter.recyclerAdapter.IncreaseAdapter
import com.mvc.topay.and.topay_android.api.ApiStore
import com.mvc.topay.and.topay_android.base.BaseMVPActivity
import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.bean.IncreaseBean
import com.mvc.topay.and.topay_android.constract.IIncreaseContract
import com.mvc.topay.and.topay_android.event.WalletAssetsListEvent
import com.mvc.topay.and.topay_android.listener.IDialogViewClickListener
import com.mvc.topay.and.topay_android.presenter.IncreasePresenter
import com.mvc.topay.and.topay_android.utils.DialogHelper
import com.mvc.topay.and.topay_android.utils.RetrofitUtils
import com.mvc.topay.and.topay_android.utils.RxHelper
import com.mvc.topay.and.topay_android.view.EditTextWatcher
import kotlinx.android.synthetic.main.activity_increase.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.greenrobot.eventbus.EventBus
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList

class IncreaseCurrencyActivity : BaseMVPActivity<IIncreaseContract.IIncreaseView, IIncreaseContract.IncreasePresenter>(), IIncreaseContract.IIncreaseView, BaseQuickAdapter.OnItemChildClickListener {
    private var isSearch = false
    private lateinit var mBean: ArrayList<IncreaseBean>
    private lateinit var mSearch: ArrayList<IncreaseBean>
    private lateinit var increaseAdapter: IncreaseAdapter
    private lateinit var increaseSearchAdapter: IncreaseAdapter
    private lateinit var dialogHelper: DialogHelper

    override fun initMVPData() {
        mPresenter.getCurrencyAll()
    }

    override fun initMVPView() {
        super.initMVPView()
        this.dialogHelper = DialogHelper.instance
        mBean = ArrayList()
        mSearch = ArrayList()
        increaseAdapter = IncreaseAdapter(R.layout.item_increase_rv, mBean)
        increaseSearchAdapter = IncreaseAdapter(R.layout.item_increase_rv, mSearch)
        increase_rv.adapter = increaseAdapter
        increase_search_rv.adapter = increaseSearchAdapter
        increase_edit.addTextChangedListener(object : EditTextWatcher() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchTv = s.toString()
                if (searchTv == "") {
                    increase_serach_null.visibility = View.GONE
                    increase_search_rv.visibility = View.GONE
                    increase_rv.visibility = View.VISIBLE
                } else {
                    mPresenter.getCurrencySerach(searchTv)
                }
            }
        })
        increaseAdapter.onItemChildClickListener = this
        increaseSearchAdapter.onItemChildClickListener = this
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_increase
    }

    override fun initPresenter(): BasePresenter<*, *> {
        return IncreasePresenter.newInstance()
    }

    override fun showCurrency(beanList: List<IncreaseBean>) {
        mBean.clear()
        mBean.addAll(beanList)
        increase_serach_null.visibility = View.GONE
        increase_search_rv.visibility = View.GONE
        increase_rv.visibility = View.VISIBLE
        increaseAdapter.notifyDataSetChanged()
    }

    override fun showSearchCurrency(beanList: List<IncreaseBean>) {
        mSearch.clear()
        mSearch.addAll(beanList)
        increase_serach_null.visibility = View.GONE
        increase_search_rv.visibility = View.VISIBLE
        increase_rv.visibility = View.GONE
        increaseSearchAdapter.notifyDataSetChanged()
    }

    override fun showCurrencyNull() {
        increase_serach_null.visibility = View.VISIBLE
        increase_search_rv.visibility = View.GONE
        increase_rv.visibility = View.GONE
        increase_serach_null.text = getString(R.string.unacquired_currency)

    }

    override fun showSearchNull() {
        increase_serach_null.visibility = View.VISIBLE
        increase_search_rv.visibility = View.GONE
        increase_rv.visibility = View.GONE
        increase_serach_null.text = getString(R.string.null_search_data)
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.increase_back -> finish()
            R.id.increase_search -> {
                // TODO 18/12/03
                isSearch = !isSearch
                if (isSearch) {
                    increase_search.setImageDrawable(ContextCompat.getDrawable(baseContext, R.drawable.cancel_icon_black))
                    increase_edit.visibility = View.VISIBLE
                    increase_edit.isFocusable = true
                    increase_edit.requestFocus()
                    increase_title.visibility = View.GONE
                    KeyboardUtils.showSoftInput(this)
                } else {
                    increase_search.setImageDrawable(ContextCompat.getDrawable(baseContext, R.drawable.search))
                    increase_edit.visibility = View.GONE
                    increase_title.visibility = View.VISIBLE
                    //Search content is also turned off when the search box is closed.
                    increase_serach_null.visibility = View.GONE
                    increase_search_rv.visibility = View.GONE
                    increase_rv.visibility = View.VISIBLE
                    KeyboardUtils.hideSoftInput(this)
                }
            }
        }
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View, position: Int) {
        when (view.id) {
            R.id.item_increase_add -> {
                if (increase_search_rv.visibility == View.VISIBLE) {
                    // 搜索的结果
                    if (!mSearch[position].isAdd) {
                        dialogHelper.create(this, getString(R.string.del_currency) + mSearch[position].title + "?", object : IDialogViewClickListener {
                            override fun click(viewId: Int) {
                                when (viewId) {
                                    R.id.hint_cancle -> {
                                    }
                                    R.id.hint_enter -> {
                                        dialogHelper.dismiss()
                                        pullStack(position)
                                    }
                                }
                            }
                        }).show()
                    } else {
                        pushStack(position)
                    }
                } else {
                    //全部列表结果
                    if (!mBean[position].isAdd) {
                        dialogHelper.create(this, getString(R.string.del_currency) + mBean[position].title + "?", object : IDialogViewClickListener {
                            override fun click(viewId: Int) {
                                when (viewId) {
                                    R.id.hint_cancle -> {
                                    }
                                    R.id.hint_enter -> {
                                        dialogHelper.dismiss()
                                        pullStack(position)
                                    }
                                }
                            }
                        }).show()
                    } else {
                        pushStack(position)
                    }
                }
            }
        }
    }

    /**
     * @param position
     */
    @SuppressLint("CheckResult")
    private fun pullStack(position: Int) {
        var currencyId = 0
        if (increase_search_rv.visibility == View.VISIBLE) {
            currencyId = mSearch[position].currencyId
        } else {
            currencyId = mBean[position].currencyId
        }
        val json = JSONObject()
        try {
            json.put("addTokenIdArr", "")
            json.put("removeTokenIdArr", currencyId)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val body = RequestBody.create(MediaType.parse("text/html"), json.toString())
        RetrofitUtils.client(ApiStore::class.java).updateAssetList(MyApplication.token, body).compose(RxHelper.rxSchedulerHelper()).subscribe({ updateBean ->
            if (updateBean.code === 200 && updateBean.data) {
                EventBus.getDefault().post(WalletAssetsListEvent())
            }
        }, { throwable -> LogUtils.d("IncreaseCurrencyActivit", throwable.message) })
        if (increase_search_rv.visibility == View.VISIBLE) {
            val add = mSearch[position].isAdd
            mSearch[position].isAdd = !add
        } else {
            val add = mBean[position].isAdd
            mBean[position].isAdd = !add
        }
        increaseAdapter.notifyDataSetChanged()
        increaseSearchAdapter.notifyDataSetChanged()
    }

    @SuppressLint("CheckResult")
    private fun pushStack(position: Int) {
        var currencyId = 0
        if (increase_search_rv.visibility == View.VISIBLE) {
            currencyId = mSearch[position].currencyId
        } else {
            currencyId = mBean[position].currencyId
        }
        val json = JSONObject()
        try {
            json.put("addTokenIdArr", currencyId)
            json.put("removeTokenIdArr", "")
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val body = RequestBody.create(MediaType.parse("text/html"), json.toString())
        RetrofitUtils.client(ApiStore::class.java).updateAssetList(getToken(), body).compose(RxHelper.rxSchedulerHelper()).subscribe({ updateBean ->
            if (updateBean.code === 200 && updateBean.data) {
                EventBus.getDefault().post(WalletAssetsListEvent())
            }
        }, { throwable -> LogUtils.e("IncreaseCurrencyActivity", throwable.message) })
        if (increase_search_rv.visibility == View.VISIBLE) {
            val add = mSearch[position].isAdd
            mSearch[position].isAdd = !add
        } else {
            val add = mBean[position].isAdd
            mBean[position].isAdd = !add
        }
        increaseAdapter.notifyDataSetChanged()
        increaseSearchAdapter.notifyDataSetChanged()
    }
}