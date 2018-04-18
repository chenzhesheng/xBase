package com.niuda.a3jidi.laok.booklook

import com.niuda.a3jidi.lib_base.base.base.app.BaseApp
import com.niuda.a3jidi.lib_base.base.constans.Const

/**
 * 作者: created by chenzhesheng on 2018/4/17 18:06
 */
class BaseApplication: BaseApp() {

    override fun onCreate() {
        super.onCreate()

        Const.service_url = "https://api.douban.com/v2"
    }

}