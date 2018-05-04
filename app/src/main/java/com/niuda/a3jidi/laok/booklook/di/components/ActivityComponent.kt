package com.niuda.a3jidi.laok.booklook.di.components

import android.app.Activity
import com.niuda.a3jidi.laok.booklook.di.modules.ActivityModule
import com.niuda.a3jidi.laok.booklook.di.scopes.PerActivity
import dagger.Component


/**
 * 作者: created by chenzhesheng on 2018/5/4 16:42
 */
@PerActivity
@Component(modules = [(ActivityModule::class)])
interface ActivityComponent {
    val activity: Activity
}