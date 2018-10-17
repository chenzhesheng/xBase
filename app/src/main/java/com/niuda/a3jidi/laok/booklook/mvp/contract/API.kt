package com.niuda.a3jidi.laok.booklook.mvp.contract

import retrofit2.http.GET

interface API {

    @GET("/v2/book/1220562")
    fun getBook(): Observable<HttpJsonResult>

}
