/*
 * Copyright (C) 2015 huwentao.
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

package com.niuda.a3jidi.lib_base.base.http.store

import okhttp3.Cookie
import okhttp3.HttpUrl

interface CookieStore {

    /** 获取当前所有保存的cookie  */
    val allCookie: List<Cookie>

    /** 保存url对应所有cookie  */
    fun saveCookie(url: HttpUrl, cookie: List<Cookie>)

    /** 保存url对应所有cookie  */
    fun saveCookie(url: HttpUrl, cookie: Cookie)

    /** 加载url所有的cookie  */
    fun loadCookie(url: HttpUrl): List<Cookie>

    /** 获取当前url对应的所有的cookie  */
    fun getCookie(url: HttpUrl): List<Cookie>

    /** 根据url和cookie移除对应的cookie  */
    fun removeCookie(url: HttpUrl, cookie: Cookie): Boolean

    /** 根据url移除所有的cookie  */
    fun removeCookie(url: HttpUrl): Boolean

    /** 移除所有的cookie  */
    fun removeAllCookie(): Boolean
}
