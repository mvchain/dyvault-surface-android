package com.mvc.topay.and.topay_android.constract

import com.mvc.topay.and.topay_android.base.BasePresenter
import com.mvc.topay.and.topay_android.base.IBaseActivity
import com.mvc.topay.and.topay_android.base.IBaseModel
import com.mvc.topay.and.topay_android.bean.HttpUpdateBean
import com.mvc.topay.and.topay_android.bean.RegisterBean
import io.reactivex.Observable

interface IWallteContract {
    abstract class WalltePresenter : BasePresenter<WallteModel, WallteView>() {
        
    }

    interface WallteModel : IBaseModel {

    }

    interface WallteView : IBaseActivity {

    }
}