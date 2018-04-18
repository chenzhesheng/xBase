package com.gdth.login.dagger.module

import android.content.Context
import android.content.pm.PackageManager
import com.elvishew.xlog.XLog
import com.gdth.BaseApp
import com.gdth.adapter.MyDateTypeAdapter
import com.gdth.api.API
import com.gdth.api.http.CookieJarImpl
import com.gdth.api.http.HttpLoggingInterceptor
import com.gdth.api.http.HttpsUtils
import com.gdth.api.http.store.PersistentCookieStore
import com.gdth.base.Const
import com.gdth.coder.CodeUtil
import com.gdth.model.model.db.TAppYh
import com.gdth.model.TAppTokenModel
import com.gdth.util.SPUtils
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.internal.bind.TypeAdapters
import dagger.Module
import dagger.Provides
import io.realm.Realm
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.sql.Timestamp
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


/**
 * Created by mac on 2017/5/20.
 */
@Module
class UserAppModule(val userApp: BaseApp) {

    @Provides
    @Singleton
    fun provideApp(): BaseApp = userApp

    @Provides
    @Singleton
    fun provideConetxt(): Context = userApp.applicationContext

    @Provides
    @Singleton
    fun provideAPI(): API {
        val server_ip = BaseApp.get().getServerUrl()
        val retrofit = initRetrofit(provideOkHttpClient(), server_ip!!)
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
            val state = BaseApp.get().getAppState()
            if (state) {
                BaseApp.get().setAppState(false)
                chain.proceed(oldRequest)
            } else {
                var content = oldRequest.url().queryParameter("content")
                if (content == null || content.isEmpty()) {
                    content = "content"
                }
                //参数加密
                val token = TAppTokenModel.currentToken

                val privateKey = TAppTokenModel.privateKey
                val sPublicKey = TAppTokenModel.publicKeyForServer
                val encodedData = CodeUtil.encryptByPublicForLong(content, sPublicKey)
                val sign = CodeUtil.newSign(encodedData, privateKey)

                XLog.d("token:" + token + ",content " + encodedData.length + " " + content.length)
                if (encodedData.length < 2000) {
                    // 添加新的参数
                    val authorizedUrlBuilder = oldRequest.url()
                            .newBuilder()
                            .scheme(oldRequest.url().scheme())
                            .host(oldRequest.url().host())
                            .addQueryParameter("sign", sign)
                            .addQueryParameter("token", token)
                            .setQueryParameter("content", encodedData);

                    // 新的请求
                    val newRequest = oldRequest.newBuilder()
                            .method(oldRequest.method(), oldRequest.body())
                            .url(authorizedUrlBuilder.build())
                            .header("User-Agent", "OkHttp Headers.java")
                            .addHeader("Accept", "application/json; q=0.5")
                            .addHeader("Accept", "application/vnd.github.v3+json")
                            .build()
                    chain.proceed(newRequest)
                } else {
                    // 添加新的参数
                    val authorizedUrlBuilder = oldRequest.url()
                            .newBuilder()
                            .scheme(oldRequest.url().scheme())
                            .host(oldRequest.url().host())
                            .addQueryParameter("token", token)
                            .setQueryParameter("content", null);

                    val requestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
                            .addFormDataPart("content", encodedData)
                            .addFormDataPart("sign", sign)
                            .build()

                    // 新的请求
                    val newRequest = oldRequest.newBuilder()
                            .method(oldRequest.method(), requestBody)
                            .url(authorizedUrlBuilder.build())
                            .header("User-Agent", "OkHttp Headers.java")
                            .addHeader("Accept", "application/json; q=0.5")
                            .addHeader("Accept", "application/vnd.github.v3+json")
                            .build()

                    chain.proceed(newRequest)
                }
            }
        }
    }

    /**
     * 初始化Retrofit
     */
    private fun initRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val datePattern = "yyyy-MM-dd HH:mm:ss"
        val dateTypeAdapter = MyDateTypeAdapter(Date::class.java, datePattern)
//        val timestampTypeAdapter = MyDateTypeAdapter(Timestamp::class.java, datePattern)
//        val javaSqlDateTypeAdapter = MyDateTypeAdapter(java.sql.Date::class.java, datePattern)

        val builder = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
        builder.registerTypeAdapterFactory(TypeAdapters.newFactory(Date::class.java, dateTypeAdapter))
//        builder.registerTypeAdapterFactory(TypeAdapters.newFactory(Timestamp::class.java, timestampTypeAdapter))
//        builder.registerTypeAdapterFactory(TypeAdapters.newFactory(java.sql.Date::class.java, javaSqlDateTypeAdapter))
        return builder.create()
    }

    @Provides
    fun provideLoginUser(): TAppYh {
        val loginUserZh = SPUtils.get().getString(Const.SHARE_DATA_USER)
        if(loginUserZh.isNotEmpty()) {
            val localUst = Realm.getDefaultInstance()
                    .where(TAppYh::class.java)
                    .equalTo("zh", loginUserZh)
                    .findFirst()!!
            return localUst
        }
        return TAppYh()
    }
}