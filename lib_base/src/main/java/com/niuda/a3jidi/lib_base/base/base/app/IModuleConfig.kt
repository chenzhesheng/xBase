package com.niuda.a3jidi.lib_base.base.base.app

import android.app.Application
import android.content.Context


interface IModuleConfig {
    fun injectAppLifecycle(context: Context, iAppLifes: List<IAppLife>)

    fun injectActivityLifecycle(context: Context, lifecycleCallbackses: List<Application.ActivityLifecycleCallbacks>)

}
