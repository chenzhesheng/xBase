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

import com.chenzhesheng.xBase.di.component.AppComponent


/**
 * ================================================
 * 框架要求框架中的每个 [android.app.Application] 都需要实现此类, 以满足规范
 *
 * @see BaseApplication
 *
 * @see [请配合官方 Wiki 文档学习本框架](https://github.com/JessYanCoding/MVPArms/wiki)
 *
 * @see [更新日志, 升级必看!](https://github.com/JessYanCoding/MVPArms/wiki/UpdateLog)
 *
 * @see [常见 Issues, 踩坑必看!](https://github.com/JessYanCoding/MVPArms/wiki/Issues)
 *
 * @see [MVPArms 官方组件化方案 ArmsComponent, 进阶指南!](https://github.com/JessYanCoding/ArmsComponent/wiki)
 * Created by JessYan on 25/04/2017 14:54
 * [Contact me](mailto:jess.yan.effort@gmail.com)
 * [Follow me](https://github.com/JessYanCoding)
 * ================================================
 */
interface App {
    fun getAppComponent(): AppComponent
}
