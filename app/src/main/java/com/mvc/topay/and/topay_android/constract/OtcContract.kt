package com.mvc.topay.and.topay_android.constract

import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.base.IBaseFragment
import com.mvc.topay.and.topay_android.base.IBaseModel

interface OtcContract {
    abstract class OtcPresenter : BasePresenter<OtcModel, OtcView>() {

    }

    interface OtcModel : IBaseModel {

    }

    interface OtcView : IBaseFragment {

    }
}