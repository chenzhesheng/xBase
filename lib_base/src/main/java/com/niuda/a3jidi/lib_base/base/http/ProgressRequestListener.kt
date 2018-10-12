package com.niuda.a3jidi.lib_base.base.http

/**
 * Created by mac on 2017/6/29.
 */

interface ProgressRequestListener {
    fun update(bytesRead: Long, contentLength: Long, done: Boolean)
}
