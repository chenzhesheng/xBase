package com.niuda.a3jidi.laok.booklook

import com.gdth.api.http.OkHttpHeler
import com.niuda.a3jidi.laok.booklook.contract.API
import com.niuda.a3jidi.lib_base.base.base.BaseApp
import com.niuda.a3jidi.lib_base.base.constans.Const

/**
 * 作者: created by chenzhesheng on 2018/4/17 18:06
 */
class MyApplication: BaseApp() {


    override fun onCreate() {
        super.onCreate()

        Const.service_url = "https://api.douban.com"
        OkHttpHeler.get().baseUrl(Const.service_url).create(API::class.java)
    }



    companion object {
        lateinit var baseApp: MyApplication
        fun get(): BaseApp = baseApp
    }
}