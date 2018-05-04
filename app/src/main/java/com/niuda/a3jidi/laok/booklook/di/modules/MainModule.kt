package com.niuda.a3jidi.laok.booklook.di.modules

import com.niuda.a3jidi.laok.booklook.di.scopes.PerActivity
import com.niuda.a3jidi.lib_base.base.base.BaseView
import dagger.Module
import dagger.Provides


/**
* 作者: created by chenzhesheng on 2017/5/20 08:56
*/
@Module
class MainModule(private val baseView: BaseView) {


    @Provides
    @PerActivity
    fun provideBaseView(): BaseView = baseView
}