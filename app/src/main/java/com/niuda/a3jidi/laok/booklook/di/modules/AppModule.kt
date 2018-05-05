package com.niuda.a3jidi.laok.booklook.di.modules

import android.content.Context
import com.niuda.a3jidi.laok.booklook.MyApplication
import com.niuda.a3jidi.laok.booklook.di.Subcomponent.MainSubComponent
import dagger.Module
import dagger.Provides


/**
* 作者: created by chenzhesheng on 2017/5/20 09:49
*/
@Module(subcomponents = [MainSubComponent::class])
class AppModule {

    @Provides
    fun contextProvider(application: MyApplication): Context {
        return application.applicationContext
    }
}