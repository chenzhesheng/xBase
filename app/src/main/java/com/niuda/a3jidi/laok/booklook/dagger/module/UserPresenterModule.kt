package com.gdth.login.dagger.module

import com.gdth.base.BaseView
import com.gdth.login.dagger.scope.UserPreView

import dagger.Module
import dagger.Provides


/**
 * Created by mac on 2017/5/20.
 */
@Module
class UserPresenterModule(val baseView: BaseView) {

    @Provides
    @UserPreView
    fun provideBaseView(): BaseView = baseView
}