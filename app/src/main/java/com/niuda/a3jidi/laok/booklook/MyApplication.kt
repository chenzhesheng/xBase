package com.niuda.a3jidi.laok.booklook

import com.niuda.a3jidi.laok.booklook.di.components.AppComponent
import com.niuda.a3jidi.laok.booklook.di.modules.AppModule
import com.niuda.a3jidi.laok.booklook.di.scopes.DaggerUserAppComponent
import com.niuda.a3jidi.lib_base.base.base.BaseApp
import com.niuda.a3jidi.lib_base.base.constans.Const

/**
 * 作者: created by chenzhesheng on 2018/4/17 18:06
 */
class MyApplication: BaseApp() {

    private lateinit var userAppComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        userAppComponent = DaggerUserAppComponent.builder().userAppModule(AppModule(baseApp)).build()
        userAppComponent.inject(BaseApp.baseApp)

        Const.service_url = "https://api.douban.com/v2"
    }



    companion object {
        lateinit var baseApp: MyApplication
        fun get(): BaseApp = baseApp
        fun getAppComponent(): AppComponent? = baseApp.userAppComponent
    }
}