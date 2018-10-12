package com.niuda.a3jidi.lib_base.base.di.subcomponent

import com.niuda.a3jidi.lib_base.base.base.BaseActivity
import com.niuda.a3jidi.lib_base.base.di.presenters.BasePresenterModule
import dagger.Subcomponent
import dagger.android.AndroidInjector

/**
* 作者: created by chenzhesheng on 2017/6/16 10:17
*/
@Subcomponent(modules = [BasePresenterModule::class])
open interface BaseSubComponent : AndroidInjector<BaseActivity> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<BaseActivity>()
}