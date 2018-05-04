package com.gdth.api.http

import com.niuda.a3jidi.lib_base.base.base.BaseApp
import com.niuda.a3jidi.lib_base.base.constans.Const
import com.niuda.a3jidi.lib_base.base.http.CookieJarImpl
import com.niuda.a3jidi.lib_base.base.http.HttpLoggingInterceptor
import com.niuda.a3jidi.lib_base.base.http.HttpsUtils
import com.niuda.a3jidi.lib_base.base.http.store.PersistentCookieStore
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by mac on 2017/3/2.
 */

class OkHttpHeler {
    object Holder {
        val INSTANCE = OkHttpHeler()
    }

    companion object {
        fun get(): OkHttpHeler {
            return Holder.INSTANCE
        }
    }

    private var okHttpClient: OkHttpClient? = null
    private var cacheControl: CacheControl? = null
    private var retrofit: Retrofit? = null

    /**
     * 初始化okhttpclient retrofit
     */
    fun init(level: HttpLoggingInterceptor.Level?): OkHttpHeler {
        if (okHttpClient == null) {
            cacheControl = cacheControl()
            val cookieJar = CookieJarImpl(PersistentCookieStore(BaseApp.get()))
            val sslParams = HttpsUtils.getSslSocketFactory(null, null, null, null)
            okHttpClient = OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .cookieJar(cookieJar)
                    .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                    .hostnameVerifier(HttpsUtils.UnSafeHostnameVerifier)
                    .addInterceptor(cacheInterceptor())
                    .addInterceptor(headersInterceptor())
                    .addInterceptor(HttpLoggingInterceptor(
                            level ?: HttpLoggingInterceptor.Level.BODY))
                    .build()
            retrofit = initRetrofit(okHttpClient, Const.service_url)
        }
        return this
    }

    /**
     * get OkHttpClient对象
     */
    fun getOkHttpClient(): OkHttpClient? {
        init(HttpLoggingInterceptor.Level.BODY)
        return okHttpClient
    }

    /**
     * get OkHttpClient对象
     */
    fun getOkHttpClient(level: HttpLoggingInterceptor.Level): OkHttpClient? {
        init(level)
        return okHttpClient
    }

    /**
     * 配置HTTP缓存
     */
    private fun cacheControl(): CacheControl {
        return CacheControl.Builder()
                .noTransform()//禁止转码
                .maxAge(10, TimeUnit.MILLISECONDS)//指示客户机可以接收生存期不大于指定时间的响应。
                .build()//cacheControl
    }

    /**
     * 配置HTTP缓存
     */
    private fun cacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()
            request = request.newBuilder().cacheControl(cacheControl).build()
            chain?.proceed(request)
        }
    }

    /**
     * 加载公共HTTP header信息
     */
    private fun headersInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()
            val builder = request.newBuilder()
//                    .addHeader("sysVersion", Build.VERSION.SDK_INT.toString())
//                    .addHeader("appVersion", "1.0-test-debug")
//                    .addHeader("channel", channel)
//                    .addHeader("model", Build.BRAND + " " + Build.MODEL)
            request = builder.build()
            chain?.proceed(request)
        }
    }

    /**
     * 初始化Retrofit
     */
    fun initRetrofit(okHttpClient: OkHttpClient?, baseUrl: String): Retrofit {
        return initRetrofit(okHttpClient, baseUrl, GsonConverterFactory.create())
    }

    /**
     * 初始化Retrofit
     */
    fun initRetrofit(okHttpClient: OkHttpClient?, baseUrl: String, factory: Converter.Factory?): Retrofit {
        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .client(okHttpClient)
        if (factory != null) {
            retrofit.addConverterFactory(factory)
        }
        return retrofit.build()
    }

    /**
     * 创建retrofit service工具
     */
    fun <T> create(service: Class<T>): T? {
        return create(service, null, HttpLoggingInterceptor.Level.BODY)
    }

    /**
     * 创建retrofit service工具
     */
    fun <T> create(service: Class<T>, tempRetrofit: Retrofit?, level: HttpLoggingInterceptor.Level?): T? {
        var useRetrofit = tempRetrofit ?: retrofit
        if (useRetrofit == null) {
            init(level)
            useRetrofit = retrofit
        }
        return useRetrofit?.create(service)
    }

}
