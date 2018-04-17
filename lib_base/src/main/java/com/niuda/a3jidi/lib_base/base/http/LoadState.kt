package com.niuda.a3jidi.lib_base.base.http

/**
 * Created by mac on 2017/9/14.
 */
interface LoadState {
    fun onLoading()
    fun onLoadComplete()
    fun dataEmpty()
    fun onLoadFailure(error:Throwable?,errorFun: ((e: Throwable?) -> Unit)?)
}