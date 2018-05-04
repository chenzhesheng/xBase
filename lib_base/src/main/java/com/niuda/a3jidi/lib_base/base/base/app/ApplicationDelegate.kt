package com.niuda.a3jidi.lib_base.base.base.app

import android.app.Application
import android.content.Context
import java.util.*

/**
* 作者: created by chenzhesheng on 2017/8/29 21:17
*/

class ApplicationDelegate : IAppLife {
    private var list: List<IModuleConfig>? = null
    private val appLifes: List<IAppLife>?
    private val liferecycleCallbacks: List<Application.ActivityLifecycleCallbacks>?

    init {
        appLifes = ArrayList()
        liferecycleCallbacks = ArrayList()
    }

    override fun attachBaseContext(base: Context) {
        val manifestParser = ManifestParser(base)
        list = manifestParser.parse()
        if (list != null && list!!.isNotEmpty()) {
            for (configModule in list!!) {
                configModule.injectAppLifecycle(base, appLifes!!)
                configModule.injectActivityLifecycle(base, liferecycleCallbacks!!)
            }
        }
        if (appLifes != null && appLifes.size > 0) {
            for (life in appLifes) {
                life.attachBaseContext(base)
            }
        }
    }

    override fun onCreate(application: Application) {
        if (appLifes != null && appLifes.isNotEmpty()) {
            for (life in appLifes) {
                life.onCreate(application)
            }
        }
        if (liferecycleCallbacks != null && liferecycleCallbacks.size > 0) {
            for (life in liferecycleCallbacks) {
                application.registerActivityLifecycleCallbacks(life)
            }
        }
    }

    override fun onTerminate(application: Application) {
        if (appLifes != null && appLifes.isNotEmpty()) {
            for (life in appLifes) {
                life.onTerminate(application)
            }
        }
        if (liferecycleCallbacks != null && liferecycleCallbacks.isNotEmpty()) {
            for (life in liferecycleCallbacks) {
                application.unregisterActivityLifecycleCallbacks(life)
            }
        }
    }
}
