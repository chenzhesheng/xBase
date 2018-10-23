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
import android.support.v4.app.FragmentManager
import android.view.View

import com.chenzhesheng.xBase.utils.XBaseUtils


/**
 * ================================================
 * [FragmentDelegate] 默认实现类
 *
 *
 * Created by JessYan on 29/04/2017 16:12
 * [Contact me](mailto:jess.yan.effort@gmail.com)
 * [Follow me](https://github.com/JessYanCoding)
 * ================================================
 */
class FragmentDelegateImpl(private var mFragmentManager: FragmentManager?, private var mFragment: Fragment?) : FragmentDelegate {
    private var iFragment: IFragment? = null

    /**
     * Return true if the fragment is currently added to its activity.
     */
    override val isAdded: Boolean
        get() = mFragment != null && mFragment!!.isAdded


    init {
        this.iFragment = mFragment as IFragment
    }

    override fun onAttach(context: Context) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        iFragment!!.setupFragmentComponent(XBaseUtils.obtainAppComponentFromContext(mFragment!!.activity!!))
    }

    override fun onCreateView(view: View?, savedInstanceState: Bundle?) {}

    override fun onActivityCreate(savedInstanceState: Bundle?) {
        iFragment!!.initData(savedInstanceState)
    }

    override fun onStart() {

    }

    override fun onResume() {

    }

    override fun onPause() {

    }

    override fun onStop() {

    }

    override fun onSaveInstanceState(outState: Bundle) {

    }

    override fun onDestroyView() {

    }

    override fun onDestroy() {
        this.mFragmentManager = null
        this.mFragment = null
        this.iFragment = null
    }

    override fun onDetach() {

    }
}
