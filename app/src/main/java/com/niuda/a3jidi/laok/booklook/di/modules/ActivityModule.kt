package com.niuda.a3jidi.laok.booklook.di.modules

import android.app.Activity
import com.niuda.a3jidi.laok.booklook.di.scopes.PerActivity
import dagger.Module
import dagger.Provides



/**
 * 作者: created by chenzhesheng on 2018/5/4 16:44
 *
 * 提供baseactivity的module
 */
@Module
class ActivityModule(private val activity: Activity) {

    @Provides
    @PerActivity
    fun provideActivity(): Activity {
        return activity
    }


}