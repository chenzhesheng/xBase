package com.niuda.a3jidi.laok.booklook.contract

import com.niuda.a3jidi.lib_base.base.http.HttpJsonResult
import io.reactivex.Observable
import retrofit2.http.GET

interface API {

    @GET("/book/1220562")
    fun getBook(): Observable<HttpJsonResult>

}
