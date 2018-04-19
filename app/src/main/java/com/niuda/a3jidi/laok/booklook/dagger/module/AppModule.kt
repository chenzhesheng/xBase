package com.niuda.a3jidi.laok.booklook.dagger.module

import android.content.Context
import android.content.pm.PackageManager
import com.google.gson.Gson
import com.niuda.a3jidi.lib_base.base.base.app.BaseApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * 作者: created by chenzhesheng on 2018/4/18 18:21
 */

@Module
class AppModule(val baseApp: BaseApp){

    lateinit var mGson: Gson

    @Provides
    @Singleton
    fun provideApp(): BaseApp = baseApp

    @Provides
    @Singleton
    fun provideConetxt(): Context = baseApp.applicationContext

    @Provides
    @Singleton
    fun getPackageManager(): PackageManager = baseApp.packageManager

    @Provides
    @Singleton
    fun getGson(): Gson = mGson

}