package com.mvc.topay.and.topay_android.utils

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object RxHelper {
    /**
     * Unified thread processing
     *
     * Publish event io thread, receive event main thread
     */
    fun <T> rxSchedulerHelper(): ObservableTransformer<T, T> {//compose处理线程 并且处理token过期问题
        return ObservableTransformer { upstream ->
            upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}
