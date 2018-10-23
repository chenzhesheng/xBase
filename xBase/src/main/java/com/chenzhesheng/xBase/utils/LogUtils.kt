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
package com.chenzhesheng.xBase.utils

import android.text.TextUtils
import android.util.Log

/**
 * ================================================
 * 日志工具类
 *
 *
 * Created by JessYan on 2015/11/23.
 * [Contact me](mailto:jess.yan.effort@gmail.com)
 * [Follow me](https://github.com/JessYanCoding)
 * ================================================
 */
class LogUtils private constructor() {

    init {
        throw IllegalStateException("you can't instantiate me!")
    }

    companion object {
        private val DEFAULT_TAG = "MVPArms"
        var isLog = true

        fun debugInfo(tag: String, msg: String) {
            if (!isLog || TextUtils.isEmpty(msg)) return
            Log.d(tag, msg)

        }

        fun debugInfo(msg: String) {
            debugInfo(DEFAULT_TAG, msg)
        }

        fun warnInfo(tag: String, msg: String) {
            if (!isLog || TextUtils.isEmpty(msg)) return
            Log.w(tag, msg)

        }

        fun warnInfo(msg: String) {
            warnInfo(DEFAULT_TAG, msg)
        }

        /**
         * 这里使用自己分节的方式来输出足够长度的 message
         *
         * @param tag 标签
         * @param msg 日志内容
         */
        fun debugLongInfo(tag: String, msg: String) {
            var msg = msg
            if (!isLog || TextUtils.isEmpty(msg)) return
            msg = msg.trim { it <= ' ' }
            var index = 0
            val maxLength = 3500
            var sub: String
            while (index < msg.length) {
                if (msg.length <= index + maxLength) {
                    sub = msg.substring(index)
                } else {
                    sub = msg.substring(index, index + maxLength)
                }

                index += maxLength
                Log.d(tag, sub.trim { it <= ' ' })
            }
        }

        fun debugLongInfo(msg: String) {
            debugLongInfo(DEFAULT_TAG, msg)
        }
    }
}
