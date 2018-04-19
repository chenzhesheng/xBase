package com.gdth.dagger.module

import com.gdth.login.dagger.scope.PreView
import com.niuda.a3jidi.lib_base.base.base.BaseView
import dagger.Module
import dagger.Provides


/**
 * Created by mac on 2017/5/20.
 */
@Module
class PresenterModule(val baseView: BaseView) {

    @Provides
    @PreView
    fun provideBaseView(): BaseView = baseView
}