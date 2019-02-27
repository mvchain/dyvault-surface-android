package com.mvc.topay.and.topay_android.utils

import android.app.Activity
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.lzy.imagepicker.loader.ImageLoader

class ImagePickerLoader : ImageLoader {
    override fun displayImage(activity: Activity, path: String, imageView: ImageView, width: Int, height: Int) {
        Glide.with(activity).load(path).into(imageView)
    }

    override fun displayImagePreview(activity: Activity, path: String, imageView: ImageView, width: Int, height: Int) {

    }

    override fun clearMemoryCache() {

    }
}
