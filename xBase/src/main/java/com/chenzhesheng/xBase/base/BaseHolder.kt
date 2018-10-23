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

import android.support.v7.widget.RecyclerView
import android.view.View


/**
 * ================================================
 * 基类 [RecyclerView.ViewHolder]
 *
 *
 * Created by JessYan on 2015/11/24.
 * [Contact me](mailto:jess.yan.effort@gmail.com)
 * [Follow me](https://github.com/JessYanCoding)
 * ================================================
 */
abstract class BaseHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    protected var mOnViewClickListener: OnViewClickListener? = null
    protected val TAG = this.javaClass.simpleName

    init {
        itemView.setOnClickListener(this)//点击事件
    }


    /**
     * 设置数据
     *
     * @param data
     * @param position
     */
    abstract fun setData(data: T, position: Int)


    /**
     * 在 Activity 的 onDestroy 中使用 [DefaultAdapter.releaseAllHolder] 方法 (super.onDestroy() 之前)
     * [BaseHolder.onRelease] 才会被调用, 可以在此方法中释放一些资源
     */
    open fun onRelease() {

    }

    override fun onClick(view: View) {
        if (mOnViewClickListener != null) {
            mOnViewClickListener!!.onViewClick(view, this.position)
        }
    }

    interface OnViewClickListener {
        fun onViewClick(view: View, position: Int)
    }

    fun setOnItemClickListener(listener: OnViewClickListener) {
        this.mOnViewClickListener = listener
    }
}
