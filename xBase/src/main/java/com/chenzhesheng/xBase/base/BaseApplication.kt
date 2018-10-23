/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.chenzhesheng.xBase.base

import android.app.Application
import android.content.Context

import com.chenzhesheng.xBase.base.delegete.AppDelegate
import com.chenzhesheng.xBase.base.delegete.AppLifecycles
import com.chenzhesheng.xBase.di.component.AppComponent
import com.chenzhesheng.xBase.utils.Preconditions


/**
 * ================================================
 * MVPArms 是一个整合了大量主流开源项目的 Android MVP 快速搭建框架, 其中包含 Dagger2、Retrofit、RxJava 以及
 * RxLifecycle、RxCache 等 Rx 系三方库, 并且提供 UI 自适应方案, 本框架将它们结合起来, 并全部使用 Dagger2 管理
 * 并提供给开发者使用, 使用本框架开发您的项目, 就意味着您已经拥有一个 MVP + Dagger2 + Retrofit + RxJava 项目
 *
 * @see [请配合官方 Wiki 文档学习本框架](https://github.com/JessYanCoding/MVPArms/wiki)
 *
 * @see [更新日志, 升级必看!](https://github.com/JessYanCoding/MVPArms/wiki/UpdateLog)
 *
 * @see [常见 Issues, 踩坑必看!](https://github.com/JessYanCoding/MVPArms/wiki/Issues)
 *
 * @see [MVPArms 官方组件化方案 ArmsComponent, 进阶指南!](https://github.com/JessYanCoding/ArmsComponent/wiki)
 * Created by JessYan on 22/03/2016
 * [Contact me](mailto:jess.yan.effort@gmail.com)
 * [Follow me](https://github.com/JessYanCoding)
 * ================================================
 */
class BaseApplication : Application(), App {

    private var mAppDelegate: AppLifecycles? = null

    /**
     * 将 [AppComponent] 返回出去, 供其它地方使用, [AppComponent] 接口中声明的方法所返回的实例, 在 [.getAppComponent] 拿到对象后都可以直接使用
     *
     * @see com.chenzhesheng.xBase.utils.XBaseUtils.Companion.obtainAppComponentFromContext
     * @return AppComponent
     */
    override fun getAppComponent(): AppComponent {
        Preconditions.checkNotNull(mAppDelegate, "%s cannot be null", AppDelegate::class.java.name)
        Preconditions.checkState(mAppDelegate is App, "%s must be implements %s", mAppDelegate!!.javaClass.name, App::class.java.name)
        return (mAppDelegate as App).getAppComponent()
    }


    /**
     * 这里会在 [BaseApplication.onCreate] 之前被调用,可以做一些较早的初始化
     * 常用于 MultiDex 以及插件化框架的初始化
     *
     * @param base
     */
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        if (mAppDelegate == null)
            this.mAppDelegate = AppDelegate(base)
        this.mAppDelegate!!.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        if (mAppDelegate != null)
            this.mAppDelegate!!.onCreate(this)
    }

    /**
     * 在模拟环境中程序终止时会被调用
     */
    override fun onTerminate() {
        super.onTerminate()
        if (mAppDelegate != null)
            this.mAppDelegate!!.onTerminate(this)
    }

}
