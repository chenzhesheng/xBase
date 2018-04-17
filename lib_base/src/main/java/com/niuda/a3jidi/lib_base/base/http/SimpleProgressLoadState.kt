package com.niuda.a3jidi.lib_base.base.http

import android.app.ProgressDialog
import android.content.Context
import android.text.TextUtils
import java.lang.ref.WeakReference

/**
 * Created by mac on 2017/9/14.
 */
class SimpleProgressLoadState(val context: WeakReference<Context>?,
                              val title: String?,
                              val message: String?,
                              val isShowAble: Boolean)  {
    constructor(context: WeakReference<Context>?, isShowAble: Boolean) : this(context, null, null, isShowAble)

    var progressDialog: ProgressDialog? = null

    init {
        if (isShowAble && context?.get() != null) {
            progressDialog = ProgressDialog(context.get())
            if (!TextUtils.isEmpty(title)) {
                progressDialog?.setTitle(title)
            }
            if (!TextUtils.isEmpty(message)) {
                progressDialog?.setMessage(message)
            }
            progressDialog?.setCanceledOnTouchOutside(false)
            progressDialog?.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
        }
    }

}