package com.niuda.a3jidi.laok.booklook.dagger

import android.content.pm.PackageManager
import com.gdth.BaseApp
import com.gdth.api.API
import com.gdth.login.dagger.module.UserAppModule
import com.gdth.model.model.db.TAppYh
import com.google.gson.Gson
import com.niuda.a3jidi.laok.booklook.contract.API
import com.niuda.a3jidi.lib_base.base.base.app.BaseApp
import dagger.Component
import okhttp3.OkHttpClient
import javax.inject.Singleton

/**
 * Created by mac on 2017/5/20.
 */
@Singleton
@Component(modules = arrayOf(UserAppModule::class))
interface UserAppComponent {
    fun inject(baseApp: BaseApp)

    fun getAPI(): API

    fun getPackageManager(): PackageManager

    fun getGson(): Gson

    fun getNewOkHttpClientBuilder(): OkHttpClient.Builder
}