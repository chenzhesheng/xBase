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

import com.chenzhesheng.xBase.integration.lifecycle.ActivityLifecycleable
import com.chenzhesheng.xBase.integration.lifecycle.FragmentLifecycleable
import com.chenzhesheng.xBase.integration.lifecycle.Lifecycleable
import com.chenzhesheng.xBase.mvp.IView
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.RxLifecycle
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.FragmentEvent
import com.trello.rxlifecycle2.android.RxLifecycleAndroid

import io.reactivex.annotations.NonNull

/**
 * ================================================
 * 使用此类操作 RxLifecycle 的特性
 *
 *
 * Created by JessYan on 26/08/2017 17:52
 * [Contact me](mailto:jess.yan.effort@gmail.com)
 * [Follow me](https://github.com/JessYanCoding)
 * ================================================
 */

class RxLifecycleUtils private constructor() {

    init {
        throw IllegalStateException("you can't instantiate me!")
    }

    companion object {

        /**
         * 绑定 Activity 的指定生命周期
         *
         * @param view
         * @param event
         * @param <T>
         * @return
        </T> */
        fun <T> bindUntilEvent(@NonNull view: IView,
                               event: ActivityEvent): LifecycleTransformer<T> {
            Preconditions.checkNotNull(view, "view == null")
            return if (view is ActivityLifecycleable) {
                bindUntilEvent(view as ActivityLifecycleable, event)
            } else {
                throw IllegalArgumentException("view isn't ActivityLifecycleable")
            }
        }

        /**
         * 绑定 Fragment 的指定生命周期
         *
         * @param view
         * @param event
         * @param <T>
         * @return
        </T> */
        fun <T> bindUntilEvent(@NonNull view: IView,
                               event: FragmentEvent): LifecycleTransformer<T> {
            Preconditions.checkNotNull(view, "view == null")
            return if (view is FragmentLifecycleable) {
                bindUntilEvent(view as FragmentLifecycleable, event)
            } else {
                throw IllegalArgumentException("view isn't FragmentLifecycleable")
            }
        }

        fun <T, R> bindUntilEvent(@NonNull lifecycleable: Lifecycleable<R>,
                                  event: R): LifecycleTransformer<T> {
            Preconditions.checkNotNull(lifecycleable, "lifecycleable == null")
            return RxLifecycle.bindUntilEvent(lifecycleable.provideLifecycleSubject(), event)
        }


        /**
         * 绑定 Activity/Fragment 的生命周期
         *
         * @param view
         * @param <T>
         * @return
        </T> */
        fun <T> bindToLifecycle(@NonNull view: IView): LifecycleTransformer<T> {
            Preconditions.checkNotNull(view, "view == null")
            return if (view is Lifecycleable<*>) {
                bindToLifecycle(view as Lifecycleable<*>)
            } else {
                throw IllegalArgumentException("view isn't Lifecycleable")
            }
        }

        fun <T> bindToLifecycle(@NonNull lifecycleable: Lifecycleable<*>): LifecycleTransformer<T> {
            Preconditions.checkNotNull(lifecycleable, "lifecycleable == null")
            return if (lifecycleable is ActivityLifecycleable) {
                RxLifecycleAndroid.bindActivity(lifecycleable.provideLifecycleSubject())
            } else if (lifecycleable is FragmentLifecycleable) {
                RxLifecycleAndroid.bindFragment(lifecycleable.provideLifecycleSubject())
            } else {
                throw IllegalArgumentException("Lifecycleable not match")
            }
        }
    }

}
