package com.niuda.a3jidi.lib_base.base.http

import android.content.Context
import com.google.gson.GsonBuilder

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * (￣▽￣)"
 *
 * @version V1.0 : Retrofit 实例层
 * @author: Administrator
 * @date: 2017-11-01 17:51
 */

class RetrofitHelper(val context: Context) {

    private val factory = GsonConverterFactory.create(GsonBuilder().create())
    private var mRetrofit: Retrofit? = null

    // okhttp初始化
    private val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                // 添加通用的Header
                val builder = chain.request().newBuilder()
                //                    builder.addHeader("token", "123");
                chain.proceed(builder.build())
            }.build()


    /**
     * 获取RetrofitServer的实体类
     * @return
     */
    val service: RetrofitService<*> = mRetrofit!!.create(RetrofitService::class.java)

    init {
        resetApp()
    }


    /**
     * Retrofit的创建
     */
    private fun resetApp() {
        if (mRetrofit == null) {
            mRetrofit = Retrofit.Builder()
                    .baseUrl(RetrofitService.Url)
                    .client(client)
                    .addConverterFactory(factory)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
        }
    }

    private val sHelper by lazy { RetrofitHelper(context) }

    fun getInstance():RetrofitHelper{
        return sHelper
    }
}
