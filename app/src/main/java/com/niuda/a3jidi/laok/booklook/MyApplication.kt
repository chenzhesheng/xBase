package com.niuda.a3jidi.laok.booklook

import android.app.Activity
import com.niuda.a3jidi.lib_base.base.base.BaseApp
import com.niuda.a3jidi.lib_base.base.constans.Const
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * 作者: created by chenzhesheng on 2018/4/17 18:06
 */
class MyApplication: BaseApp() , HasActivityInjector {

    @Inject lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingActivityInjector
    }

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder().application(this).build()

        Const.service_url = "https://api.douban.com/v2"
    }



    companion object {
        lateinit var baseApp: MyApplication
        fun get(): BaseApp = baseApp
    }
}