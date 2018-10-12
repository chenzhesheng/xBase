package com.niuda.a3jidi.laok.booklook.contract

import com.niuda.a3jidi.lib_base.base.http.HttpJsonResult
import retrofit2.http.GET
import rx.Observable

interface API {

    @GET("/book/1220562")
    fun getBook(): Observable<HttpJsonResult>

}
