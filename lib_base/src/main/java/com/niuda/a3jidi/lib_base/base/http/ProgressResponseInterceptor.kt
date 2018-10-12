package com.niuda.a3jidi.lib_base.base.http

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Created by mac on 2017/6/29.
 */

class ProgressResponseInterceptor(private val listener: ProgressResponseListener) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        //		boolean state = BaseApp.baseApp.getAppState();
        //		if (state) {
        //			BaseApp.baseApp.setAppState(false);
        return chain.proceed(request)
        //		} else {
        //			Response originalResponse = chain.proceed(chain.request());
        //
        //			return originalResponse.newBuilder()
        //					.body(new ProgressResponseBody(originalResponse.body(), listener))
        //					.build();
        //		}
    }
}
