package com.niuda.a3jidi.laok.booklook

import com.niuda.a3jidi.lib_base.base.base.BaseApp
import com.niuda.a3jidi.lib_base.base.constans.Const
import dagger.android.HasActivityInjector

/**
 * 作者: created by chenzhesheng on 2018/4/17 18:06
 */
class MyApplication: BaseApp() ,  HasActivityInjector {


    override fun onCreate() {
        super.onCreate()

        Const.service_url = "https://api.douban.com/v2"
    }



    companion object {
        lateinit var baseApp: MyApplication
        fun get(): BaseApp = baseApp
    }
}