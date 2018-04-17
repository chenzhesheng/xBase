package com.niuda.a3jidi.laok.booklook.manager

import android.content.Context
import com.niuda.a3jidi.lib_base.base.http.RetrofitHelper
import io.reactivex.Observable

/**
 * (￣▽￣)"
 *
 * @version V1.0 :数据管理层
 * @author: Administrator
 * @date: 2017-11-11 14:07
 */

class DataManager<T>(context: Context) {

    /**
 * 获取单例Retrofit
 * @param context
 */
    private val sHelper by lazy { RetrofitHelper(context) }


    fun getSearchBook(name: String, tag: String, start: Int, end: Int): Observable<*> {
        return sHelper.getInstance().service.getSearchBook(name, tag, start, end)
    }
}
