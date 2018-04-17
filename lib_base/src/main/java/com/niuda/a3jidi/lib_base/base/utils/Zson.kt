package com.niuda.a3jidi.lib_base.base.utils

import android.app.Application
import java.lang.ref.WeakReference

/**
* 作者: created by chenzhesheng on 2017/8/24 17:38
*/

class Zson {

    private var ref: WeakReference<Application>? = null

    /**
     * 获取Application
     *
     * @return Application
     */
    val app: Application
        get() = if (ref == null || ref!!.get() == null)
            throw NullPointerException("Zson should init first")
        else
            ref!!.get()!!

    companion object {
        private var zson: Zson? = null

        /**
         * 初始化工具类
         *
         * @param app 应用
         */
        fun init(app: Application) {
            if (zson == null) {
                zson = Zson()
            }
            zson!!.ref = WeakReference(app)
        }

        fun get(): Zson? {
            if (zson == null)
                throw NullPointerException("Zson should init first")
            return zson
        }
    }
}
