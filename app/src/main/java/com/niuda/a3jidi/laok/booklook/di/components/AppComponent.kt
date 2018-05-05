package com.niuda.a3jidi.laok.booklook.di.components

import android.app.Application
import com.niuda.a3jidi.laok.booklook.MyApplication
import com.niuda.a3jidi.laok.booklook.di.modules.AppModule
import com.niuda.a3jidi.laok.booklook.di.modules.MainActivityModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

/**
* 作者: created by chenzhesheng on 2017/5/20 09:46
*/
//@Singleton
@Component(modules = [AndroidSupportInjectionModule::class
    , AppModule::class
    , MainActivityModule::class
//    , FragmentModule::class
])
interface AppComponent : AndroidInjector<MyApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): AppComponent.Builder

        fun build(): AppComponent
    }

    override fun inject(application: MyApplication)

}