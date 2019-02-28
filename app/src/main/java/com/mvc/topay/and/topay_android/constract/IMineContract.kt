package com.mvc.topay.and.topay_android.constract

import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.base.IBaseActivity
import com.mvc.topay.and.topay_android.base.IBaseModel

interface IMineContract {
    abstract class MinePresenter : BasePresenter<MineModel, MineView>() {
        
    }

    interface MineModel : IBaseModel {

    }

    interface MineView : IBaseActivity {

    }
}