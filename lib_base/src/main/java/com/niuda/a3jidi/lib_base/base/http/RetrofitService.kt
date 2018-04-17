package com.niuda.a3jidi.lib_base.base.http


import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
/**
 * (￣▽￣)"
 *
 * @version V1.0 : 接口层
 * @author: Administrator
 * @date: 2017-10-18 10:04
 */

interface RetrofitService<T> {

    companion object {
        val Url = "https://api.douban.com/v2/"
    }

    @GET("book/search")
    fun getSearchBook(@Query("q") name: String,
                      @Query("tag") tag: String,
                      @Query("start") start: Int,
                      @Query("count") count: Int): Observable<T>
}
