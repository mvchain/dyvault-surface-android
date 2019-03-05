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
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.base.BaseMVPActivity
import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.bean.ReceiptBean
import com.mvc.topay.and.topay_android.constract.IReceiptQRContract
import com.mvc.topay.and.topay_android.presenter.MineReceiptPresenter
import com.per.rslibrary.IPermissionRequest
import com.per.rslibrary.RsPermission
import com.uuzuche.lib_zxing.activity.CodeUtils
import kotlinx.android.synthetic.main.activity_mineqcode.*

class MineQCodeActivity : BaseMVPActivity<IReceiptQRContract.ReceiptQRView, IReceiptQRContract.ReceiptQRPresenter>(), IReceiptQRContract.ReceiptQRView {
    private var tokenId: Int = 0
    private lateinit var tokenName: String
    override fun initMVPData() {
        m_title.text = "$tokenName 收款"
        m_qcode_content.text = "$tokenName 收款地址"
        mPresenter.getMineQcode(tokenId)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_mineqcode
    }

    override fun initMVPView() {
        super.initMVPView()
        tokenId = intent.getIntExtra("tokenId", 0)
        tokenName = intent.getStringExtra("tokenName")
    }

    override fun initPresenter(): BasePresenter<*, *> {
        return MineReceiptPresenter.newIntance()
    }

    override fun showSuccess(receiptBean: ReceiptBean) {
        val hash = receiptBean.data
        m_qcode_hash.text = hash
        val mBitmap = CodeUtils.createImage(hash, 400, 400, null)
        Glide.with(this).load(mBitmap).into(m_qcode_qc_img)
    }

    override fun showError() {
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.m_qcode_back -> {
                finish()
            }

            R.id.m_qcode_share -> {
                // TODO 18/12/07
                /** * 分享图片 */
                RsPermission.getInstance().setiPermissionRequest(object : IPermissionRequest {
                    override fun toSetting() {
                        RsPermission.getInstance().toSettingPer()
                    }

                    override fun cancle(i: Int) {
                        showToast("权限不足")
                    }

                    override fun success(i: Int) {
                        share_layout.setDrawingCacheEnabled(true)
                        val drawingCache = Bitmap.createBitmap(share_layout.getDrawingCache())
                        val parintent = Intent()
                        val parseUri = Uri.parse(MediaStore.Images.Media.insertImage(contentResolver, drawingCache, null, null))
                        parintent.action = Intent.ACTION_SEND//设置分享行为
                        parintent.type = "image/*"  //设置分享内容的类型
                        parintent.putExtra(Intent.EXTRA_STREAM, parseUri)
                        //创建分享的Dialog
                        val share_intent = Intent.createChooser(parintent, "分享到:")
                        startActivity(share_intent)
                        drawingCache.recycle()
                        share_layout.setDrawingCacheEnabled(false)
                    }
                }).requestPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }

            R.id.m_qcode_hash -> {
                // TODO 18/12/07
                val cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                // 创建普通字符型ClipData
                val mClipData = ClipData.newPlainText("hash", m_qcode_hash.text.toString())
                // 将ClipData内容放到系统剪贴板里。
                cm.primaryClip = mClipData
                ToastUtils.showLong("内容已复制至剪贴板")
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        RsPermission.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}