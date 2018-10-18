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
package com.niuda.a3jidi.lib_base.base.widget

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.ContextThemeWrapper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout.LayoutParams
import android.widget.PopupWindow

/**
 * ================================================
 * 因为继承于 [PopupWindow] ,所以它本身就是一个 [PopupWindow]
 * 因此如果此类里封装的功能并不能满足您的需求(不想过多封装 UI 的东西,这里只提供思想,觉得不满足需求可以自己仿照着封装)
 * 您可以直接调用 [PopupWindow] 的 Api 满足需求
 * ================================================
 */
class CustomPopupWindow private constructor(builder: Builder) : PopupWindow() {
    private val mContentView: View?
    private val mParentView: View?
    private val mListener: CustomPopupWindowListener?
    private val isOutsideTouch: Boolean
    private val isFocus: Boolean
    private val mBackgroundDrawable: Drawable
    private val mAnimationStyle: Int
    private val isWrap: Boolean

    init {
        this.mContentView = builder.contentView
        this.mParentView = builder.parentView
        this.mListener = builder.listener
        this.isOutsideTouch = builder.isOutsideTouch
        this.isFocus = builder.isFocus
        this.mBackgroundDrawable = builder.backgroundDrawable
        this.mAnimationStyle = builder.animationStyle
        this.isWrap = builder.isWrap
        initLayout()
    }

    private fun initLayout() {
        mListener!!.initPopupView(mContentView)
        width = if (isWrap) LayoutParams.WRAP_CONTENT else LayoutParams.MATCH_PARENT
        height = if (isWrap) LayoutParams.WRAP_CONTENT else LayoutParams.MATCH_PARENT
        isFocusable = isFocus
        isOutsideTouchable = isOutsideTouch
        setBackgroundDrawable(mBackgroundDrawable)
        if (mAnimationStyle != -1)
        //如果设置了动画则使用动画
            animationStyle = mAnimationStyle
        contentView = mContentView
    }

    /**
     * 获得用于展示popup内容的view
     *
     * @return
     */
    override fun getContentView(): View? {
        return mContentView
    }

    fun show() {//默认显示到中间
        if (mParentView == null) {
            showAtLocation(mContentView, Gravity.CENTER or Gravity.CENTER_HORIZONTAL, 0, 0)
        } else {
            showAtLocation(mParentView, Gravity.CENTER or Gravity.CENTER_HORIZONTAL, 0, 0)
        }
    }


    class Builder {
        var contentView: View? = null
        var parentView: View? = null
        var listener: CustomPopupWindowListener? = null
        var isOutsideTouch = true//默认为true
        var isFocus = true//默认为true
        var backgroundDrawable: Drawable = ColorDrawable(0x00000000)//默认为透明
        var animationStyle = -1
        var isWrap: Boolean = false

        fun contentView(contentView: View): Builder {
            this.contentView = contentView
            return this
        }

        fun parentView(parentView: View): Builder {
            this.parentView = parentView
            return this
        }

        fun isWrap(isWrap: Boolean): Builder {
            this.isWrap = isWrap
            return this
        }


        fun customListener(listener: CustomPopupWindowListener): Builder {
            this.listener = listener
            return this
        }


        fun isOutsideTouch(isOutsideTouch: Boolean): Builder {
            this.isOutsideTouch = isOutsideTouch
            return this
        }

        fun isFocus(isFocus: Boolean): Builder {
            this.isFocus = isFocus
            return this
        }

        fun backgroundDrawable(backgroundDrawable: Drawable): Builder {
            this.backgroundDrawable = backgroundDrawable
            return this
        }

        fun animationStyle(animationStyle: Int): Builder {
            this.animationStyle = animationStyle
            return this
        }

        fun build(): CustomPopupWindow {
            if (contentView == null)
                throw IllegalStateException("ContentView is required")
            if (listener == null)
                throw IllegalStateException("CustomPopupWindowListener is required")

            return CustomPopupWindow(this)
        }
    }

    interface CustomPopupWindowListener {
        fun initPopupView(contentView: View?)
    }

    companion object {

        fun builder(): Builder {
            return Builder()
        }

        /**
         * 用于填充contentView,必须传ContextThemeWrapper(比如activity)不然popupwindow要报错
         * @param context
         * @param layoutId
         * @return
         */
        fun inflateView(context: ContextThemeWrapper, layoutId: Int): View {
            return LayoutInflater.from(context)
                    .inflate(layoutId, null)
        }
    }

}
