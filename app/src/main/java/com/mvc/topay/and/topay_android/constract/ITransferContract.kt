package com.mvc.topay.and.topay_android.constract

import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.base.IBaseActivity
import com.mvc.topay.and.topay_android.base.IBaseModel

interface ITransferContract {
    abstract class TransferPresenter : BasePresenter<TransferModel, TransferView>() {

    }

    interface TransferModel : IBaseModel {

    }

    interface TransferView : IBaseActivity {

    }
}