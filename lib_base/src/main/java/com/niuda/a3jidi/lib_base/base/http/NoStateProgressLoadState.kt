package com.niuda.a3jidi.lib_base.base.http

import com.elvishew.xlog.XLog

/**
 * Created by mac on 2017/9/18.
 */
class NoStateProgressLoadState : LoadState {
    override fun onLoading() {
        XLog.e("TAG", "开始上传===下载")
    }

    override fun onLoadComplete() {
    }

    override fun dataEmpty() {
    }

    override fun onLoadFailure(error: Throwable?,errorFun: ((e: Throwable?) -> Unit)?) {
        if(errorFun!=null){
            errorFun(error)
        }
    }
}