package com.mvc.topay.and.topay_android.view

import android.text.Editable
import android.text.TextWatcher

/**
 * Empty implementation,specifically to subclasses
 */
open class EditTextWatcher : TextWatcher {
    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }
}