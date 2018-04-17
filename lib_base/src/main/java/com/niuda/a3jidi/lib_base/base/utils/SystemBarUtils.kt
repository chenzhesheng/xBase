package com.niuda.a3jidi.lib_base.base.utils

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import com.niuda.a3jidi.lib_base.R

/**
 * (￣▽￣)"
 *
 * @version V1.0 :
 * @author: Administrator
 * @date: 2017-12-26 15:08
 */

object SystemBarUtils {

    /** 设置状态栏颜色  */
    fun initSystemBarTint(act: Activity, isStatusBar: Boolean, color: Int) {
        val window = act.window
        if (isStatusBar) {
            // 设置状态栏全透明
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = Color.TRANSPARENT
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                act.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            }
            return
        }
        // 沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0以上使用原生方法
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = color
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4-5.0使用三方工具类，有些4.4的手机有问题，这里为演示方便，不使用沉浸式
            //            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            val tintManager = SystemBarTintManager(act)
            tintManager.isStatusBarTintEnabled = true
            tintManager.setStatusBarTintColor(color)
        }
    }

    /** 获取主题色  */
    fun getColorPrimary(act: Activity): Int {
        val typedValue = TypedValue()
        act.theme.resolveAttribute(R.attr.colorPrimary, typedValue, true)
        return typedValue.data
    }

    /** 获取深主题色  */
    fun getDarkColorPrimary(act: Activity): Int {
        val typedValue = TypedValue()
        act.theme.resolveAttribute(R.attr.colorPrimaryDark, typedValue, true)
        return typedValue.data
    }
}
