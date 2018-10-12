package com.niuda.a3jidi.lib_base.base.di.modules

import android.content.Context
import com.niuda.a3jidi.lib_base.base.base.BaseApp
import com.niuda.a3jidi.lib_base.base.di.subcomponent.BaseSubComponent
import dagger.Module
import dagger.Provides


/**
* 作者: created by chenzhesheng on 2017/5/20 09:49
*/
@Module(subcomponents = [BaseSubComponent::class])
class AppModule {

    @Provides
    fun contextProvider(application: BaseApp): Context {
        return application.applicationContext
    }
}