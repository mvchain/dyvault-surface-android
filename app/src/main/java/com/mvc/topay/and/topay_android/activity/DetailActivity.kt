package com.mvc.topay.and.topay_android.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import com.blankj.utilcode.util.TimeUtils
import com.bumptech.glide.Glide
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.base.BaseMVPActivity
import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.bean.DetailBean
import com.mvc.topay.and.topay_android.constract.IDetailContract
import com.mvc.topay.and.topay_android.presenter.DetailPresenter
import com.mvc.topay.and.topay_android.utils.TextUtils
import com.per.rslibrary.IPermissionRequest
import com.per.rslibrary.RsPermission
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : BaseMVPActivity<IDetailContract.DetailView, IDetailContract.DetailPresenter>(), IDetailContract.DetailView {
    private var tokenId = 0

    override fun initMVPData() {
        mPresenter.getDetailOnID(tokenId)
    }

    override fun initMVPView() {
        super.initMVPView()
        tokenId = intent.getIntExtra("id", 0)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_detail
    }

    override fun initPresenter(): BasePresenter<*, *> {
        return DetailPresenter.newInstance()
    }

    @SuppressLint("SetTextI18n")
    override fun detailSuccess(detailBean: DetailBean.DataBean) {
        var sb = StringBuffer()
        var iconResId = R.drawable.waiting //default icon
        detail_price_content.text = "${TextUtils.doubleToFour(detailBean.value)}  ${detailBean.feeTokenType}"
        if (detailBean.classify == 5) {
            detail_fees_title.text = getString(R.string.order_number_hint)
            detail_fees_content.text = detailBean.orderNumber
            detail_coll_layout.visibility = View.GONE
            detail_hash_layout.visibility = View.GONE
            if (detailBean.transactionType == 1) {
                sb.append("${getString(R.string.collection_from)}${detailBean.fromAddress}")
            } else {
                sb.append("${getString(R.string.transfer_go)}${detailBean.toAddress}")
            }
            iconResId = R.drawable.success
        } else if (detailBean.classify == 0) {
            when (detailBean.status) {
                0, 1 -> {
                    sb.append(getString(R.string.waiting))
                    iconResId = R.drawable.waiting
                }
                2 -> {
                    sb.append(getString(R.string.successful_withdrawal))
                    iconResId = R.drawable.success
                }
                3 -> {
                    sb.append(getString(R.string.cash_withdrawal_failure))
                    iconResId = R.drawable.failure
                }
            }
            detail_colladd_title.text = if (detailBean.transactionType == 1) getString(R.string.source_address) else getString(R.string.target_address)
            detail_fees_title.text = getString(R.string.transaction_fees)
            detail_fees_content.text = "${TextUtils.doubleToSix(detailBean.fee)} ${detailBean.feeTokenType}"
            detail_colladd_content.text = if (detailBean.transactionType == 1) detailBean.fromAddress else detailBean.toAddress
            detail_hash_content.text = detailBean.blockHash
            detail_colladd_content.setOnClickListener {
                var cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                var mClipData = ClipData.newPlainText("hash", detail_colladd_content.text.toString())
                cm.primaryClip = mClipData
                showToast(getString(R.string.hash_copy_hint))
            }
            detail_hash_content.setOnClickListener {
                val uri = Uri.parse(detailBean.hashLink)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
            detail_hash_content.setOnLongClickListener {
                var cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                var mClipData = ClipData.newPlainText("hash", detail_hash_content.text.toString())
                cm.primaryClip = mClipData
                showToast(getString(R.string.tran_hash_copy_hint))
                true
            }
        }
        Glide.with(this).load(iconResId).into(detail_icon)
        detail_title.text = sb.toString()
        detail_time.text = TimeUtils.millis2String(detailBean.createdAt)
    }

    override fun detailFailed(msg: String) {
        showToast(msg)
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.detail_back -> {
                finish()
            }
            R.id.detail_share -> {
                /** * 分享图片 */
                RsPermission.getInstance().setiPermissionRequest(object : IPermissionRequest {
                    override fun toSetting() {
                        RsPermission.getInstance().toSettingPer()
                    }

                    override fun cancle(i: Int) {
                        showToast(getString(R.string.insufficient_permissions))
                    }

                    override fun success(i: Int) {
                        share_layout.isDrawingCacheEnabled = true
                        val drawingCache = Bitmap.createBitmap(share_layout.drawingCache)
                        val parintent = Intent()
                        val parseUri = Uri.parse(MediaStore.Images.Media.insertImage(contentResolver, drawingCache, null, null))
                        parintent.action = Intent.ACTION_SEND//设置分享行为
                        parintent.type = "image/*"  //设置分享内容的类型
                        parintent.putExtra(Intent.EXTRA_STREAM, parseUri)
                        //创建分享的Dialog
                        val share_intent = Intent.createChooser(parintent, getString(R.string.share_hint))
                        startActivity(share_intent)
                        drawingCache.recycle()
                        share_layout.isDrawingCacheEnabled = false
                    }
                }).requestPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
    }

}