package com.niuda.a3jidi.laok.booklook.presenter

import com.niuda.a3jidi.lib_base.base.http.HttpJsonResult
import retrofit2.http.GET
import rx.Observable

interface BookAPI {

    @GET("/book/1220562")
    fun getBook(): Observable<HttpJsonResult>

}
