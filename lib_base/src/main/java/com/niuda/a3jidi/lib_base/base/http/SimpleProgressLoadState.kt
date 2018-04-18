package com.niuda.a3jidi.lib_base.base.http

import android.app.ProgressDialog
import android.content.Context
import android.text.TextUtils
import android.widget.Toast
import java.lang.ref.WeakReference

/**
 * Created by mac on 2017/9/14.
 */
class SimpleProgressLoadState(val context: WeakReference<Context>?,
                              val title: String?,
                              val message: String?,
                              val isShowAble: Boolean) : ProgressLoadState()  {
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

    override fun onLoading() {
        if (isShowAble && progressDialog != null)
            progressDialog?.show()
    }

    override fun onLoadComplete() {
        if (isShowAble && progressDialog != null)
            progressDialog?.dismiss()
    }

    override fun dataEmpty() {
    }

    override fun onLoadFailure(error: Throwable?, errorFun: ((e: Throwable?) -> Unit)?) {
        if (isShowAble && progressDialog != null)
            progressDialog?.dismiss()
        if (errorFun != null) {
            errorFun(error)
        } else {
            if (context?.get() != null)
                Toast.makeText(context.get(), error?.message, Toast.LENGTH_SHORT).show()
//                Toasty.error(context.get(), error?.message)
        }
    }

    override fun update(bytesRead: Long, contentLength: Long, done: Boolean) {
        val progress = bytesRead * 100 / contentLength
        if (isShowAble && progressDialog != null)
            progressDialog?.progress = progress.toInt()
    }
}