package com.niuda.a3jidi.laok.booklook.di.modules

import com.niuda.a3jidi.laok.booklook.di.scopes.PerActivity
import com.niuda.a3jidi.lib_base.base.base.BaseView
import dagger.Module
import dagger.Provides



/**
 * 作者: created by chenzhesheng on 2018/5/4 16:44
 *
 * 提供baseactivity的module
 */
@Module
open class ActivityModule(private val baseView: BaseView) {


    @Provides
    @PerActivity
    fun provideBaseView(): BaseView = baseView

}