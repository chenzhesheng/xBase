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
package com.chenzhesheng.xBase.http.imageloader

import android.content.Context

/**
 * ================================================
 * 图片加载策略,实现 [BaseImageLoaderStrategy]
 * 并通过 [ImageLoader.setLoadImgStrategy] 配置后,才可进行图片请求
 *
 *
 * Created by JessYan on 8/5/2016 15:50
 * [Contact me](mailto:jess.yan.effort@gmail.com)
 * [Follow me](https://github.com/JessYanCoding)
 * ================================================
 */
interface BaseImageLoaderStrategy<T : ImageConfig> {
    /**
     * 加载图片
     *
     * @param ctx
     * @param config
     */
    fun loadImage(ctx: Context, config: T)

    /**
     * 停止加载
     *
     * @param ctx
     * @param config
     */
    fun clear(ctx: Context, config: T)
}
