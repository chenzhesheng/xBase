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
package com.chenzhesheng.xBase.base.delegete

import android.app.Application
import android.content.Context

/**
 * ================================================
 * 用于代理 [Application] 的生命周期
 *
 * @see AppDelegate
 * Created by JessYan on 18/07/2017 17:43
 * [Contact me](mailto:jess.yan.effort@gmail.com)
 * [Follow me](https://github.com/JessYanCoding)
 * ================================================
 */
interface AppLifecycles {
    fun attachBaseContext(base: Context)

    fun onCreate(application: Application)

    fun onTerminate(application: Application)
}
