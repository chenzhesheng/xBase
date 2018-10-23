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

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View

/**
 * ================================================
 * [Fragment] 代理类,用于框架内部在每个 [Fragment] 的对应生命周期中插入需要的逻辑
 *
 * @see FragmentDelegateImpl
 *
 * @see [FragmentDelegate wiki 官方文档](https://github.com/JessYanCoding/MVPArms/wiki.3.13)
 * Created by JessYan on 29/04/2017 14:30
 * [Contact me](mailto:jess.yan.effort@gmail.com)
 * [Follow me](https://github.com/JessYanCoding)
 * ================================================
 */
interface FragmentDelegate {

    /**
     * Return true if the fragment is currently added to its activity.
     */
    val isAdded: Boolean

    fun onAttach(context: Context)

    fun onCreate(savedInstanceState: Bundle?)

    fun onCreateView(view: View?, savedInstanceState: Bundle?)

    fun onActivityCreate(savedInstanceState: Bundle?)

    fun onStart()

    fun onResume()

    fun onPause()

    fun onStop()

    fun onSaveInstanceState(outState: Bundle)

    fun onDestroyView()

    fun onDestroy()

    fun onDetach()

    companion object {
        val FRAGMENT_DELEGATE = "FRAGMENT_DELEGATE"
    }
}
