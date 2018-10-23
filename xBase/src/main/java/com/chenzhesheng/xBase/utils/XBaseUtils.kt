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

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.support.design.widget.Snackbar
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.RecyclerView
import android.text.SpannableString
import android.text.Spanned
import android.text.SpannedString
import android.text.style.AbsoluteSizeSpan
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import com.chenzhesheng.xBase.base.App
import com.chenzhesheng.xBase.di.component.AppComponent
import com.chenzhesheng.xBase.integration.AppManager
import java.security.MessageDigest

/**
 * ================================================
 * 一些框架常用的工具
 *
 *
 * Created by JessYan on 2015/11/23.
 * [Contact me](mailto:jess.yan.effort@gmail.com)
 * [Follow me](https://github.com/JessYanCoding)
 * ================================================
 */
class XBaseUtils private constructor() {


    init {
        throw IllegalStateException("you can't instantiate me!")
    }

    companion object {
        var mToast: Toast? = null

        /**
         * 设置hint大小
         *
         * @param size
         * @param v
         * @param res
         */
        fun setViewHintSize(context: Context, size: Int, v: TextView, res: Int) {
            val ss = SpannableString(getResources(context).getString(
                    res))
            // 新建一个属性对象,设置文字的大小
            val ass = AbsoluteSizeSpan(size, true)
            // 附加属性到文本
            ss.setSpan(ass, 0, ss.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            // 设置hint
            v.hint = SpannedString(ss) // 一定要进行转换,否则属性会消失
        }

        /**
         * dp 转 px
         *
         * @param context [Context]
         * @param dpValue `dpValue`
         * @return `pxValue`
         */
        fun dip2px(context: Context, dpValue: Float): Int {
            val scale = getResources(context).displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }

        /**
         * px 转 dp
         *
         * @param context [Context]
         * @param pxValue `pxValue`
         * @return `dpValue`
         */
        fun pix2dip(context: Context, pxValue: Int): Int {
            val scale = getResources(context).displayMetrics.density
            return (pxValue / scale + 0.5f).toInt()
        }

        /**
         * sp 转 px
         *
         * @param context [Context]
         * @param spValue `spValue`
         * @return `pxValue`
         */
        fun sp2px(context: Context, spValue: Float): Int {
            val fontScale = getResources(context).displayMetrics.scaledDensity
            return (spValue * fontScale + 0.5f).toInt()
        }

        /**
         * px 转 sp
         *
         * @param context [Context]
         * @param pxValue `pxValue`
         * @return `spValue`
         */
        fun px2sp(context: Context, pxValue: Float): Int {
            val fontScale = getResources(context).displayMetrics.scaledDensity
            return (pxValue / fontScale + 0.5f).toInt()
        }

        /**
         * 获得资源
         */
        fun getResources(context: Context): Resources {
            return context.resources
        }

        /**
         * 得到字符数组
         */
        fun getStringArray(context: Context, id: Int): Array<String> {
            return getResources(context).getStringArray(id)
        }

        /**
         * 从 dimens 中获得尺寸
         *
         * @param context
         * @param id
         * @return
         */
        fun getDimens(context: Context, id: Int): Int {
            return getResources(context).getDimension(id).toInt()
        }

        /**
         * 从 dimens 中获得尺寸
         *
         * @param context
         * @param dimenName
         * @return
         */
        fun getDimens(context: Context, dimenName: String): Float {
            return getResources(context).getDimension(getResources(context).getIdentifier(dimenName, "dimen", context.packageName))
        }

        /**
         * 从String 中获得字符
         *
         * @return
         */

        fun getString(context: Context, stringID: Int): String {
            return getResources(context).getString(stringID)
        }

        /**
         * 从String 中获得字符
         *
         * @return
         */

        fun getString(context: Context, strName: String): String {
            return getString(context, getResources(context).getIdentifier(strName, "string", context.packageName))
        }

        /**
         * findview
         *
         * @param view
         * @param viewName
         * @param <T>
         * @return
        </T> */
        fun <T : View> findViewByName(context: Context, view: View, viewName: String): T {
            val id = getResources(context).getIdentifier(viewName, "id", context.packageName)
            return view.findViewById<View>(id) as T
        }

        /**
         * findview
         *
         * @param activity
         * @param viewName
         * @param <T>
         * @return
        </T> */
        fun <T : View> findViewByName(context: Context, activity: Activity, viewName: String): T {
            val id = getResources(context).getIdentifier(viewName, "id", context.packageName)
            return activity.findViewById<View>(id) as T
        }

        /**
         * 根据 layout 名字获得 id
         *
         * @param layoutName
         * @return
         */
        fun findLayout(context: Context, layoutName: String): Int {
            return getResources(context).getIdentifier(layoutName, "layout", context.packageName)
        }

        /**
         * 填充view
         *
         * @param detailScreen
         * @return
         */
        fun inflate(context: Context, detailScreen: Int): View {
            return View.inflate(context, detailScreen, null)
        }

        /**
         * 单例 toast
         *
         * @param string
         */
        fun makeText(context: Context, string: String) {
            if (mToast == null) {
                mToast = Toast.makeText(context, string, Toast.LENGTH_SHORT)
            }
            mToast!!.setText(string)
            mToast!!.show()
        }

        /**
         * 使用 [Snackbar] 显示文本消息
         * Arms 已将 com.android.support:design 从依赖中移除 (目的是减小 Arms 体积, design 库中含有太多 View)
         * 因为 Snackbar 在 com.android.support:design 库中, 所以如果框架使用者没有自行依赖 com.android.support:design
         * Arms 则会使用 Toast 替代 Snackbar 显示信息, 如果框架使用者依赖了 arms-autolayout 库就不用依赖 com.android.support:design 了
         * 因为在 arms-autolayout 库中已经依赖有 com.android.support:design
         *
         * @param text
         */
        fun snackbarText(text: String) {
            AppManager.getAppManager().showSnackbar(text, false)
        }

        /**
         * 使用 [Snackbar] 长时间显示文本消息
         * Arms 已将 com.android.support:design 从依赖中移除 (目的是减小 Arms 体积, design 库中含有太多 View)
         * 因为 Snackbar 在 com.android.support:design 库中, 所以如果框架使用者没有自行依赖 com.android.support:design
         * Arms 则会使用 Toast 替代 Snackbar 显示信息, 如果框架使用者依赖了 arms-autolayout 库就不用依赖 com.android.support:design 了
         * 因为在 arms-autolayout 库中已经依赖有 com.android.support:design
         *
         * @param text
         */
        fun snackbarTextWithLong(text: String) {
            AppManager.getAppManager().showSnackbar(text, true)
        }


        /**
         * 通过资源id获得drawable
         *
         * @param rID
         * @return
         */
        fun getDrawablebyResource(context: Context, rID: Int): Drawable {
            return getResources(context).getDrawable(rID)
        }


        /**
         * 跳转界面 1, 通过 [AppManager.startActivity]
         *
         * @param activityClass
         */
        fun startActivity(activityClass: Class<*>) {
            AppManager.getAppManager().startActivity(activityClass)
        }

        /**
         * 跳转界面 2, 通过 [AppManager.startActivity]
         *
         * @param
         */
        fun startActivity(content: Intent) {
            AppManager.getAppManager().startActivity(content)
        }


        /**
         * 跳转界面 3
         *
         * @param activity
         * @param homeActivityClass
         */
        fun startActivity(activity: Activity, homeActivityClass: Class<*>) {
            val intent = Intent(activity.applicationContext, homeActivityClass)
            activity.startActivity(intent)
        }

        /**
         * 跳转界面 4
         *
         * @param
         */
        fun startActivity(activity: Activity, intent: Intent) {
            activity.startActivity(intent)
        }

        /**
         * 获得屏幕的宽度
         *
         * @return
         */
        fun getScreenWidth(context: Context): Int {
            return getResources(context).displayMetrics.widthPixels
        }

        /**
         * 获得屏幕的高度
         *
         * @return
         */
        fun getScreenHeidth(context: Context): Int {
            return getResources(context).displayMetrics.heightPixels
        }


        /**
         * 获得颜色
         */
        fun getColor(context: Context, rid: Int): Int {
            return getResources(context).getColor(rid)
        }

        /**
         * 获得颜色
         */
        fun getColor(context: Context, colorName: String): Int {
            return getColor(context, getResources(context).getIdentifier(colorName, "color", context.packageName))
        }

        /**
         * 移除孩子
         *
         * @param view
         */
        fun removeChild(view: View) {
            val parent = view.parent
            if (parent is ViewGroup) {
                parent.removeView(view)
            }
        }

        fun isEmpty(obj: Any?): Boolean {
            return if (obj == null) {
                true
            } else false
        }


        /**
         * MD5
         *
         * @param string
         * @return
         * @throws Exception
         */
        fun encodeToMD5(string: String): String {
            var hash = ByteArray(0)
            try {
                hash = MessageDigest.getInstance("MD5").digest(
                        string.toByteArray(charset("UTF-8")))
            } catch (e: Exception) {
                e.printStackTrace()
            }

            val hex = StringBuilder(hash.size * 2)
            for (b in hash) {
                if (b.toInt() and 0xFF < 0x10) {
                    hex.append("0")
                }
                hex.append(Integer.toHexString(b.toInt() and 0xFF))
            }
            return hex.toString()
        }


        /**
         * 全屏,并且沉侵式状态栏
         *
         * @param activity
         */
        fun statuInScreen(activity: Activity) {
            val attrs = activity.window.attributes
            attrs.flags = attrs.flags and WindowManager.LayoutParams.FLAG_FULLSCREEN.inv()
            activity.window.attributes = attrs
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }


        /**
         * 配置 RecyclerView
         *
         * @param recyclerView
         * @param layoutManager
         */
        @Deprecated("Use {@link #configRecyclerView(RecyclerView, RecyclerView.LayoutManager)} instead")
        fun configRecycleView(recyclerView: RecyclerView, layoutManager: RecyclerView.LayoutManager) {
            recyclerView.layoutManager = layoutManager
            //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
            recyclerView.setHasFixedSize(true)
            recyclerView.itemAnimator = DefaultItemAnimator()
        }

        /**
         * 配置 RecyclerView
         *
         * @param recyclerView
         * @param layoutManager
         */
        fun configRecyclerView(recyclerView: RecyclerView, layoutManager: RecyclerView.LayoutManager) {
            recyclerView.layoutManager = layoutManager
            //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
            recyclerView.setHasFixedSize(true)
            recyclerView.itemAnimator = DefaultItemAnimator()
        }

        /**
         * 执行 [AppManager.killAll]
         */
        fun killAll() {
            AppManager.getAppManager().killAll()
        }

        /**
         * 执行 [AppManager.appExit]
         */
        fun exitApp() {
            AppManager.getAppManager().appExit()
        }

        fun obtainAppComponentFromContext(context: Context): AppComponent {
            Preconditions.checkNotNull(context, "%s cannot be null", Context::class.java.name)
            Preconditions.checkState(context.applicationContext is App, "%s must be implements %s", context.applicationContext.javaClass.name, App::class.java.name)
            return (context.applicationContext as App).getAppComponent()
        }
    }

}
