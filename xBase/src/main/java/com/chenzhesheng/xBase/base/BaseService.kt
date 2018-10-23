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

import android.app.Service
import android.content.Intent
import android.os.IBinder

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * ================================================
 * 基类 [Service]
 *
 *
 * Created by jess on 2016/5/6.
 * [Contact me](mailto:jess.yan.effort@gmail.com)
 * [Follow me](https://github.com/JessYanCoding)
 * ================================================
 */
abstract class BaseService : Service() {
    protected val TAG = this.javaClass.simpleName
    protected var mCompositeDisposable: CompositeDisposable? = null

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        init()
    }

    override fun onDestroy() {
        super.onDestroy()
        unDispose()//解除订阅
        this.mCompositeDisposable = null
    }

    protected fun addDispose(disposable: Disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable!!.add(disposable)//将所有subscription放入,集中处理
    }

    protected fun unDispose() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable!!.clear()//保证activity结束时取消所有正在执行的订阅
        }
    }

    /**
     * 初始化
     */
    abstract fun init()
}
