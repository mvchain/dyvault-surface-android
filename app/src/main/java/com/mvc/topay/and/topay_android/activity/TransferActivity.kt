package com.mvc.topay.and.topay_android.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.support.v4.content.ContextCompat
import android.text.InputFilter
import android.view.Gravity
import android.view.View
import android.widget.PopupWindow
import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.mvc.topay.and.topay_android.R
import com.mvc.topay.and.topay_android.base.BaseMVPActivity
import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.bean.HttpUpdateBean
import com.mvc.topay.and.topay_android.bean.IDToTransferBean
import com.mvc.topay.and.topay_android.common.Constant.SP.RESETPASSWORD_PAY
import com.mvc.topay.and.topay_android.common.Constant.SP.USER_RESETPASSWORD_TYPE
import com.mvc.topay.and.topay_android.common.Constant.SP.USER_SALT
import com.mvc.topay.and.topay_android.constract.ITransferContract
import com.mvc.topay.and.topay_android.event.HistoryEvent
import com.mvc.topay.and.topay_android.event.HistoryFragmentEvent
import com.mvc.topay.and.topay_android.event.WalletAssetsListEvent
import com.mvc.topay.and.topay_android.listener.IPayWindowListener
import com.mvc.topay.and.topay_android.listener.PswMaxListener
import com.mvc.topay.and.topay_android.presenter.TransferPresenter
import com.mvc.topay.and.topay_android.utils.PointLengthFilter
import com.mvc.topay.and.topay_android.utils.TextUtils
import com.mvc.topay.and.topay_android.utils.WeiboDialogUtils
import com.mvc.topay.and.topay_android.view.EditTextWatcher
import com.mvc.topay.and.topay_android.view.PopViewHelper
import com.mvc.topay.and.topay_android.view.PswText
import com.per.rslibrary.IPermissionRequest
import com.per.rslibrary.RsPermission
import com.uuzuche.lib_zxing.activity.CodeUtils
import kotlinx.android.synthetic.main.activity_transfer.*
import org.greenrobot.eventbus.EventBus

class TransferActivity : BaseMVPActivity<ITransferContract.TransferView, ITransferContract.TransferPresenter>(), ITransferContract.TransferView {
    private lateinit var tokenName: String
    private lateinit var mHash: String
    private var tokenId = 0
    private lateinit var mTransBean: IDToTransferBean.DataBean
    private lateinit var mPopView: PopupWindow
    private lateinit var tranDialog: Dialog
    override fun initMVPData() {
        btc_price.text = tokenName
        transfer_title.text = "$tokenName${getString(R.string.btn_transfer)}"
        transfer_trans_address.setText(mHash)
        mPresenter.getDetail(tokenId)
        transfer_trans_price.filters = arrayOf<InputFilter>(PointLengthFilter())
    }

    override fun initMVPView() {
        super.initMVPView()
        tokenName = intent.getStringExtra("tokenName")
        tokenId = intent.getIntExtra("tokenId", 0)
        mHash = intent.getStringExtra("hash")
        tranDialog = WeiboDialogUtils.createLoadingDialog(this@TransferActivity, getString(R.string.transfer))
        transfer_trans_price.addTextChangedListener(object : EditTextWatcher() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var changePrice = s.toString()
                if (changePrice != "" && mTransBean != null) {
                    if (TextUtils.stringToDouble(changePrice) > mTransBean.balance) {
                        transfer_balance.text = getString(R.string.insufficient_balance)
                        transfer_balance.setTextColor(ContextCompat.getColor(baseContext, R.color.colorRed))
                        transfer_submit.isEnabled = false
                    } else {
                        transfer_balance.text = "${TextUtils.doubleToFour(mTransBean.balance)} $tokenName"
                        transfer_balance.setTextColor(ContextCompat.getColor(baseContext, R.color.content_tv_bg))
                        transfer_submit.isEnabled = true
                    }
                }
            }
        })
        transfer_trans_address.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                mPresenter.getTransFee(transfer_trans_address.text.toString().trim())
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_transfer
    }

    override fun initPresenter(): BasePresenter<*, *> {
        return TransferPresenter.newInstance()
    }

    @SuppressLint("SetTextI18n")
    override fun detailSuccess(data: IDToTransferBean.DataBean) {
        this.mTransBean = data
        transfer_balance.text = "${TextUtils.doubleToFour(data.balance)} $tokenName"
        transfer_fees.text = "${TextUtils.doubleToSix(data.fee)} ${data.feeTokenName}"
    }

    @SuppressLint("SetTextI18n")
    override fun transFeeStatus(isStation: Boolean) {
        if (isStation) {
            transfer_fees.text = "${0.000000} ${mTransBean.feeTokenName}"
        } else {
            transfer_fees.text = "${TextUtils.doubleToSix(mTransBean.fee)} ${mTransBean.feeTokenName}"
        }
    }

    override fun detailFailed(msg: String) {
        showToast(getString(R.string.service_error))
    }

    override fun transferSuccess(bean: HttpUpdateBean) {
        if (bean.code === 200) {
            showToast(getString(R.string.successful_operation))
        }
        EventBus.getDefault().post(WalletAssetsListEvent())
        EventBus.getDefault().post(HistoryEvent())
        EventBus.getDefault().post(HistoryFragmentEvent())
        finish()
        tranDialog.dismiss()

    }

    override fun transferFailed(msg: String) {
        showToast(msg)
        tranDialog.dismiss()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            when (resultCode) {
                200 -> {
                    val qode = data.getBooleanExtra("QODE", false)
                    if (!qode) {
                        showToast(getString(R.string.invalid_address))
                        return
                    }
                    val hash = data.getStringExtra(CodeUtils.RESULT_STRING)
                    transfer_trans_address.setText(hash)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        RsPermission.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.transfer_back -> {
                finish()
            }

            R.id.transfer_qcode -> {
                // TODO 18/12/07
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    RsPermission.getInstance().setRequestCode(200).setiPermissionRequest(object : IPermissionRequest {
                        override fun toSetting() {
                            RsPermission.getInstance().toSettingPer()
                        }

                        override fun cancle(i: Int) {
                            showToast(getString(R.string.camera_not_permission))
                        }

                        override fun success(i: Int) {
                            val intent = Intent(this@TransferActivity, QCodeActivity::class.java)
                            intent.putExtra("tokenId", tokenId)
                            startActivityForResult(intent, 200)
                        }
                    }).requestPermission(this, Manifest.permission.CAMERA)
                } else {
                    val intent = Intent(this@TransferActivity, QCodeActivity::class.java)
                    intent.putExtra("tokenId", tokenId)
                    startActivityForResult(intent, 200)
                }
            }

            R.id.transfer_submit -> {
                var transfer_address = transfer_trans_address.text.toString()
                var transfer_price = transfer_trans_price.text.toString()
                if (transfer_address == "") {
                    showToast("${getString(R.string.collection_address)}${getString(R.string.not_null)}")
                    return
                }
                if (transfer_price == "") {
                    showToast("${getString(R.string.transfer_amount)}${getString(R.string.not_null)}")
                    return
                }
                if (java.lang.Double.parseDouble(transfer_price) <= 0) {
                    showToast("${getString(R.string.transfer_amount)}${getString(R.string.incorrect)}")
                    return
                }
                mPopView = PopViewHelper.instance.create(this
                        , R.layout.layout_paycode
                        , "$transfer_price $tokenName"
                        , transfer_address
                        , transfer_fees.text.toString()
                        , object : IPayWindowListener {
                    override fun onclick(view: View) {
                        when (view.id) {
                            R.id.pay_close -> {
                                mPopView.dismiss()
                                showToast(getString(R.string.cancel_the_deal))
                            }
                            R.id.pay_text -> {
                                KeyboardUtils.showSoftInput(mPopView.contentView.findViewById<View>(R.id.pay_text))
                                setAlpha(0.5f)
                            }
                            R.id.pay_forget -> {
                                SPUtils.getInstance().put(USER_RESETPASSWORD_TYPE, RESETPASSWORD_PAY)
                                startActivity(Intent(this@TransferActivity, VerificationEmailActivity::class.java))
//                                sIntent.putExtra("type", RESETPASSWORD_PAY)
//                                startActivity(sIntent)
                            }
                        }
                    }

                    override fun dismiss() {
                        setAlpha(1f)
                    }

                }
                        , object : PswMaxListener {
                    override fun maxPsw(num: String) {
                        tranDialog.show()
                        KeyboardUtils.hideSoftInput(mPopView.contentView.findViewById<View>(R.id.pay_text))
                        val salt = SPUtils.getInstance().getString(USER_SALT)
                        var md5Password = EncryptUtils.encryptMD5ToString(salt + EncryptUtils.encryptMD5ToString(num))
                        LogUtils.e(md5Password)
                        mPresenter.sendTransferMsg(transfer_address.trim(), md5Password, tokenId, transfer_price)
                        mPopView.dismiss()
                    }

                }
                )
                LogUtils.e(mPopView)
                mPopView.showAtLocation(transfer_submit, Gravity.BOTTOM, 0, 0)
                mPopView.contentView.post { KeyboardUtils.showSoftInput(mPopView.contentView.findViewById<PswText>(R.id.pay_text)) }
                setAlpha(0.5f)
            }

        }
    }

}