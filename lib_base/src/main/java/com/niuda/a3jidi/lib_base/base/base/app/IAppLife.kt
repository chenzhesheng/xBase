package com.niuda.a3jidi.lib_base.base.base.app

import android.app.Application
import android.content.Context


interface IAppLife {
    fun attachBaseContext(base: Context)

    fun onCreate(application: Application)

    fun onTerminate(application: Application)
}
