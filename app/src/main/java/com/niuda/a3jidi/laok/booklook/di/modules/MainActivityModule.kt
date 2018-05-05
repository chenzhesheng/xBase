package com.niuda.a3jidi.laok.booklook.di.modules

import android.app.Activity
import com.niuda.a3jidi.laok.booklook.di.Subcomponent.MainSubComponent
import com.niuda.a3jidi.laok.booklook.ui.activity.MainActivity
import com.niuda.a3jidi.lib_base.base.base.BaseView
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

/**
* 作者: created by chenzhesheng on 2017/6/16 10:16
*/
@Module
abstract class MainActivityModule(baseView: BaseView) : ActivityModule(baseView) {
    @Binds
    @IntoMap
    @ActivityKey(MainActivity::class)
    internal abstract fun bind(builder: MainSubComponent.Builder): AndroidInjector.Factory<out Activity>

}