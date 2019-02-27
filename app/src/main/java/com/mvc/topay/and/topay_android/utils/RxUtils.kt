package com.mvc.topay.and.topay_android.utils

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class RxUtils {
    companion object {
        private var compositeDisposable = CompositeDisposable()
        val instance: RxUtils by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            RxUtils()
        }
    }

    fun register(dp: Disposable) {
        compositeDisposable.add(dp)
    }

    fun unSubscribe() {
        compositeDisposable.dispose()
    }

    fun unSubscribeAll(){
        compositeDisposable.clear()
    }
}