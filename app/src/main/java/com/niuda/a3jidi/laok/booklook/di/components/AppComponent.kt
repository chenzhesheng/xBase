package com.niuda.a3jidi.laok.booklook.di.components

import android.content.pm.PackageManager
import com.niuda.a3jidi.laok.booklook.contract.API
import com.niuda.a3jidi.laok.booklook.di.modules.AppModule
import com.niuda.a3jidi.lib_base.base.base.BaseApp
import dagger.Component
import okhttp3.OkHttpClient
import javax.inject.Singleton

/**
* 作者: created by chenzhesheng on 2017/5/20 09:46
*/
@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent {
    fun inject(baseApp: BaseApp)

    fun getAPI(): API

    fun getPackageManager(): PackageManager

    fun getNewOkHttpClientBuilder(): OkHttpClient.Builder
}