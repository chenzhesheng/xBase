package com.niuda.a3jidi.laok.booklook.di.modules

import android.content.Context
import android.content.pm.PackageManager
import com.elvishew.xlog.XLog
import com.niuda.a3jidi.laok.booklook.contract.API
import com.niuda.a3jidi.lib_base.base.base.BaseApp
import com.niuda.a3jidi.lib_base.base.constans.Const
import com.niuda.a3jidi.lib_base.base.http.CookieJarImpl
import com.niuda.a3jidi.lib_base.base.http.HttpLoggingInterceptor
import com.niuda.a3jidi.lib_base.base.http.HttpsUtils
import com.niuda.a3jidi.lib_base.base.http.store.PersistentCookieStore
import dagger.Module
import dagger.Provides
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


/**
* 作者: created by chenzhesheng on 2017/5/20 09:49
*/
@Module
class AppModule(val userApp: BaseApp) {

    @Provides
    @Singleton
    fun provideApp(): BaseApp = userApp

    @Provides
    @Singleton
    fun provideConetxt(): Context = userApp.applicationContext

    @Provides
    @Singleton
    fun provideAPI(): API {
        val retrofit = initRetrofit(provideOkHttpClient(), Const.service_url)
        return retrofit.create<API>(API::class.java)
    }

    @Provides
    @Singleton
    fun providePackageManager(): PackageManager = userApp.packageManager

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val cacheControl = newCacheControl()
        val cookieJar = CookieJarImpl(PersistentCookieStore(userApp))
        var sslParams: HttpsUtils.SSLParams? = null
        try {
            sslParams = HttpsUtils.getSslSocketFactory(null, null, null, null)
        } catch (e: IOException) {
            XLog.e("证书加载错误", e)
        }

        val builder = OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .cookieJar(cookieJar)
                .hostnameVerifier(HttpsUtils.UnSafeHostnameVerifier)
                .addInterceptor(cacheInterceptor(cacheControl))
                .addInterceptor(commonParamsInterceptor())
                .addInterceptor(HttpLoggingInterceptor(HttpLoggingInterceptor.Level.BODY))
        if (sslParams != null) {
            builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
        }
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideNewOkHttpClientBuilder(mOkHttpClient: OkHttpClient): OkHttpClient.Builder {
        val newOkhttpClientBuilder = mOkHttpClient.newBuilder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
        newOkhttpClientBuilder.interceptors().clear()
        val cacheControl = newCacheControl()
        newOkhttpClientBuilder.addInterceptor(cacheInterceptor(cacheControl))
                .hostnameVerifier(HttpsUtils.UnSafeHostnameVerifier)
                .addInterceptor(commonParamsInterceptor())
        return newOkhttpClientBuilder
    }

    /**
     * 配置HTTP缓存
     */
    private fun newCacheControl(): CacheControl {
        return CacheControl.Builder()
                .noTransform()//禁止转码
                .maxAge(10, TimeUnit.MILLISECONDS)//指示客户机可以接收生存期不大于指定时间的响应。
                .build()//cacheControl
    }

    /**
     * 配置HTTP缓存
     */
    private fun cacheInterceptor(cacheControl: CacheControl): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()
            request = request.newBuilder().cacheControl(cacheControl).build()
            chain.proceed(request)
        }
    }

    /**
     * 加载公共HTTP header信息
     */
    private fun commonParamsInterceptor(): Interceptor {
        return Interceptor { chain ->
            val oldRequest = chain.request()
                chain.proceed(oldRequest)
        }
    }

    /**
     * 初始化Retrofit
     */
    private fun initRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .build()
    }
}