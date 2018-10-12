package com.niuda.a3jidi.lib_base.base.di.components

import android.app.Application
import com.niuda.a3jidi.lib_base.base.base.BaseApp
import com.niuda.a3jidi.lib_base.base.di.modules.AppModule
import com.niuda.a3jidi.lib_base.base.di.modules.BaseActivityModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

/**
* 作者: created by chenzhesheng on 2017/5/20 09:46
*/
@Component(modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
//        BaseFragmentModule::class,
        BaseActivityModule::class
])
interface AppComponent : AndroidInjector<BaseApp> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    override fun inject(application: BaseApp)

}