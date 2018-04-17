package com.niuda.a3jidi.lib_base.base.http

import android.app.Dialog
import android.content.Context
import android.widget.Toast
import com.niuda.a3jidi.lib_base.base.utils.DialogUtils
import java.lang.ref.WeakReference

/**
 * Created by mac on 2017/9/15.
 */
class SimpleLoadState(val context: WeakReference<Context>?, val message: String) : LoadState {

    private var progressDialog: Dialog? = null

    init {
        if (context?.get() != null)
            progressDialog = DialogUtils.createLoadingDialog(context.get(), message)
    }

    override fun onLoading() {
        if (progressDialog != null)
            progressDialog?.show()
    }

    override fun onLoadComplete() {
        if (progressDialog != null)
            progressDialog?.dismiss()
    }

    override fun dataEmpty() {
    }

    override fun onLoadFailure(error: Throwable?, errorFun: ((e: Throwable?) -> Unit)?) {
        if (progressDialog != null)
            progressDialog?.dismiss()
        if (errorFun != null) {
            errorFun(error)
        } else {
            if (context?.get() != null)
                Toast.makeText(context.get(),error?.message, Toast.LENGTH_SHORT).show()
                //Toasty.error(context.get(), error?.message)
        }
    }
}