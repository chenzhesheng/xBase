package com.niuda.a3jidi.laok.booklook.di.Subcomponent

import com.niuda.a3jidi.laok.booklook.di.modules.MainPresenterModule
import com.niuda.a3jidi.laok.booklook.ui.activity.MainActivity
import dagger.Subcomponent
import dagger.android.AndroidInjector

/**
* 作者: created by chenzhesheng on 2017/6/16 10:17
*/
@Subcomponent(modules = [MainPresenterModule::class])
interface MainSubComponent : AndroidInjector<MainActivity> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MainActivity>()
}