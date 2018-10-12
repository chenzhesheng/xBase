package com.niuda.a3jidi.lib_base.base.di.modules

import android.app.Activity
import com.niuda.a3jidi.lib_base.base.base.BaseActivity
import com.niuda.a3jidi.lib_base.base.di.subcomponent.BaseSubComponent
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
abstract class BaseActivityModule {


    @Binds
    @IntoMap
    @ActivityKey(BaseActivity::class)
    internal abstract fun bindMainActivity(builder: BaseSubComponent.Builder): AndroidInjector.Factory<out Activity>


}