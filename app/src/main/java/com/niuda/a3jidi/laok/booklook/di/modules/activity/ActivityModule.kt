package com.niuda.a3jidi.laok.booklook.di.modules.activity

import android.app.Activity
import com.niuda.a3jidi.laok.booklook.di.Subcomponent.MainSubComponent
import com.niuda.a3jidi.laok.booklook.ui.activity.MainActivity
import com.niuda.a3jidi.lib_base.base.di.modules.BaseActivityModule
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap


/**
 * 作者: created by chenzhesheng on 2018/5/4 16:44
 *
 * 提供baseactivity的module
 */
@Module
abstract class ActivityModule : BaseActivityModule(){


    @Binds
    @IntoMap
    @ActivityKey(MainActivity::class)
    internal abstract fun bindMainActivity(builder: MainSubComponent.Builder): AndroidInjector.Factory<out Activity>


}