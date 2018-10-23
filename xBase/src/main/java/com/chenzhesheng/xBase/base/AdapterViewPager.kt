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

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

/**
 * ================================================
 * 基类 [FragmentStatePagerAdapter]
 *
 *
 * Created by JessYan on 22/03/2016
 * [Contact me](mailto:jess.yan.effort@gmail.com)
 * [Follow me](https://github.com/JessYanCoding)
 * ================================================
 */
class AdapterViewPager : FragmentStatePagerAdapter {
    private var mList: List<Fragment>? = null
    private var mTitles: Array<CharSequence>? = null

    constructor(fragmentManager: FragmentManager, list: List<Fragment>) : super(fragmentManager) {
        this.mList = list
    }


    constructor(fragmentManager: FragmentManager, list: List<Fragment>, titles: Array<CharSequence>) : super(fragmentManager) {
        this.mList = list
        this.mTitles = titles
    }

    override fun getItem(position: Int): Fragment {
        return mList!![position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (mTitles != null && position < mTitles!!.size) {
            mTitles!![position]
        } else super.getPageTitle(position)
    }

    override fun getCount(): Int {
        return mList!!.size
    }
}
