package com.niuda.a3jidi.lib_base.base.base.app

import android.content.Context
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import com.alibaba.android.arouter.launcher.ARouter
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.XLog
import com.github.promeg.pinyinhelper.Pinyin
import com.niuda.a3jidi.lib_base.base.constans.Const
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Created by Administrator on 8/3/2018.
 */
open class BaseApp : MultiDexApplication(){

    lateinit var token: String
    lateinit var mRefWatcher: RefWatcher
    lateinit var applicationDelegate: ApplicationDelegate

    override fun onCreate() {
        super.onCreate()
        baseApp = this

        // 组件配置
        ARouter.openLog()     // 打印日志
        ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        ARouter.init(this);
        applicationDelegate.onCreate(this)

        Realm.init(this)
        val config = RealmConfiguration.Builder()
                .name("laok.realm")
                .schemaVersion(1)
                .directory(getExternalFilesDir(null))
                .build()
        Realm.setDefaultConfiguration(config)
        XLog.init(if (Const.isDebug) LogLevel.ALL else LogLevel.NONE)
        Pinyin.init(Pinyin.newConfig())

    }



    companion object {
        lateinit var baseApp: BaseApp
        fun get(): BaseApp = baseApp
        fun getRefWatcher(): RefWatcher = baseApp.mRefWatcher
        fun setToken(token: String) {
            baseApp.token = token
        }
        //
        fun getToken(): String = baseApp.token
    }

    protected fun setupLeakCanary(): RefWatcher {
        return if (LeakCanary.isInAnalyzerProcess(this)) {
            RefWatcher.DISABLED
        } else LeakCanary.install(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
        applicationDelegate = ApplicationDelegate()
        applicationDelegate.attachBaseContext(base!!)

    }

    override fun onTerminate() {
        super.onTerminate()
        applicationDelegate.onTerminate(this)
    }
}