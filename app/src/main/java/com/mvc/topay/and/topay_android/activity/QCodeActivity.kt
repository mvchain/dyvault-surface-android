package com.mvc.topay.and.topay_android.activity

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.blankj.utilcode.util.ToastUtils
import com.gyf.barlibrary.ImmersionBar
import com.lzy.imagepicker.ImagePicker
import com.lzy.imagepicker.bean.ImageItem
import com.lzy.imagepicker.ui.ImageGridActivity
import com.lzy.imagepicker.view.CropImageView
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.base.BaseActivity
import com.mvc.topay.and.topay_android.utils.ImagePickerLoader
import com.mvc.topay.and.topay_android.utils.RxgularUtils
import com.per.rslibrary.IPermissionRequest
import com.per.rslibrary.RsPermission
import com.uuzuche.lib_zxing.activity.CaptureFragment
import com.uuzuche.lib_zxing.activity.CodeUtils

import java.util.ArrayList

class QCodeActivity : BaseActivity(), View.OnClickListener {

    private lateinit var captureFragment: CaptureFragment
    private lateinit var mBackQcode: ImageView
    private lateinit var mPhotoQcode: TextView
    private var tokenId: Int = 0

    /**
     * 二维码解析回调函数
     */
    internal var analyzeCallback: CodeUtils.AnalyzeCallback = object : CodeUtils.AnalyzeCallback {
        override fun onAnalyzeSuccess(mBitmap: Bitmap, result: String) {
            val resultIntent = Intent()
            val bundle = Bundle()
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS)
            bundle.putString(CodeUtils.RESULT_STRING, result)
            if (tokenId == 0) {
                bundle.putBoolean("QODE", false)
            } else if (tokenId == 4 || tokenId == 2) {
                if (!RxgularUtils.isBTC(result)) {
                    bundle.putBoolean("QODE", false)
                } else {
                    bundle.putBoolean("QODE", true)
                }
            } else if (tokenId != 4) {
                if (!RxgularUtils.isETH(result)) {
                    bundle.putBoolean("QODE", false)
                } else {
                    bundle.putBoolean("QODE", true)
                }
            } else {
                bundle.putBoolean("QODE", true)
            }
            resultIntent.putExtras(bundle)
            this@QCodeActivity.setResult(200, resultIntent)
            this@QCodeActivity.finish()
        }

        override fun onAnalyzeFailed() {
            val resultIntent = Intent()
            val bundle = Bundle()
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED)
            bundle.putString(CodeUtils.RESULT_STRING, "")
            resultIntent.putExtras(bundle)
            this@QCodeActivity.setResult(200, resultIntent)
            this@QCodeActivity.finish()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_qcode
    }

    override fun initData() {

    }

    override fun initView() {
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init()
        tokenId = intent.getIntExtra("tokenId", 0)
        captureFragment = CaptureFragment()
        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.layout_camera)
        captureFragment!!.analyzeCallback = analyzeCallback
        supportFragmentManager.beginTransaction().replace(R.id.qcode_layout, captureFragment!!).commit()
        mBackQcode = findViewById(R.id.qcode_back)
        mPhotoQcode = findViewById(R.id.qcode_photo)
        mBackQcode!!.setOnClickListener(this)
        mPhotoQcode!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.qcode_back ->
                // TODO 18/12/07
                finish()
            R.id.qcode_photo ->
                // TODO 18/12/07
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    RsPermission.getInstance().setRequestCode(200).setiPermissionRequest(object : IPermissionRequest {
                        override fun toSetting() {
                            RsPermission.getInstance().toSettingPer()
                        }

                        override fun cancle(i: Int) {
                            ToastUtils.showLong("未给予读取权限无法读取相册图片")
                        }

                        override fun success(i: Int) {
                            startPhotoActivity()
                        }
                    }).requestPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                } else {
                    startPhotoActivity()
                }
        }
    }

    /**
     * jump to photo
     */
    private fun startPhotoActivity() {
        val imagePicker = ImagePicker.getInstance()
        imagePicker.imageLoader = ImagePickerLoader()   //设置图片加载器
        imagePicker.isShowCamera = false  //显示拍照按钮
        imagePicker.isCrop = false        //允许裁剪（单选才有效）
        imagePicker.isSaveRectangle = true //是否按矩形区域保存
        imagePicker.selectLimit = 1    //选中数量限制
        imagePicker.style = CropImageView.Style.RECTANGLE  //裁剪框的形状
        val intent = Intent(this@QCodeActivity, ImageGridActivity::class.java)
        startActivityForResult(intent, 100)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        RsPermission.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
                if (requestCode == 100) {
                    val images = data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS) as ArrayList<ImageItem>
                    val intent = Intent()
                    intent.putExtra(CodeUtils.RESULT_STRING, images[0].path)
                    setResult(200, intent)
                    finish()
                } else {
                    ToastUtils.showLong("图片不正确，请重新选择")
                }
            }
        }
    }
}
