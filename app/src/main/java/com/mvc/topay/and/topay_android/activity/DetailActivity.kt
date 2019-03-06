package com.mvc.topay.and.topay_android.activity

import android.Manifest
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

    override fun detailSuccess(detailBean: DetailBean.DataBean) {
        detail_price_content.text = TextUtils.doubleToFour(detailBean.value)
        if (detailBean.classify === 5) {
            detail_fees_title.text = "订单号"
            detail_fees_content.text = detailBean.orderNumber
            detail_coll_layout.visibility = View.GONE
            detail_hash_layout.visibility = View.GONE
        } else if (detailBean.classify === 0) {
            detail_fees_title.text = "交易手续费"
            detail_fees_content.text = "${detailBean.fee} ${detailBean.feeTokenType}"
            detail_colladd_content.text = if (detailBean.transactionType == 1) detailBean.fromAddress else detailBean.toAddress
            detail_hash_content.text = detailBean.blockHash
            detail_colladd_content.setOnClickListener {
                var cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                // 创建普通字符型ClipData
                var mClipData = ClipData.newPlainText("hash", detail_colladd_content.text.toString())
                // 将ClipData内容放到系统剪贴板里。
                cm.primaryClip = mClipData
                showToast("hash地址已复制到剪贴板")
            }
            detail_hash_content.setOnClickListener {
                val uri = Uri.parse(detailBean.hashLink)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
            detail_hash_content.setOnLongClickListener {
                var cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                // 创建普通字符型ClipData
                var mClipData = ClipData.newPlainText("hash", detail_hash_content.text.toString())
                // 将ClipData内容放到系统剪贴板里。
                cm.primaryClip = mClipData
                showToast("交易hash已复制到剪贴板")
                true
            }
        }
        Glide.with(this).load(R.color.colorRed).into(detail_icon)
        detail_title.text = (if (detailBean.transactionType == 1) "+" else "-") + TextUtils.doubleToFour(detailBean.value) + " " + detailBean.tokenName
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
                        showToast("权限不足")
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
                        val share_intent = Intent.createChooser(parintent, "分享到:")
                        startActivity(share_intent)
                        drawingCache.recycle()
                        share_layout.isDrawingCacheEnabled = false
                    }
                }).requestPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
    }

}