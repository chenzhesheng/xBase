package com.niuda.a3jidi.laok.booklook.dagger

import android.content.pm.PackageManager
import com.google.gson.Gson
import com.niuda.a3jidi.laok.booklook.dagger.module.AppModule
import com.niuda.a3jidi.lib_base.base.base.app.BaseApp
import dagger.Component
import javax.inject.Singleton

/**
 * 作者: created by chenzhesheng on 2018/4/18 18:23
 */
@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent {
    fun inject(baseApp: BaseApp)

    fun getPackageManager(): PackageManager

    fun getGson(): Gson

}