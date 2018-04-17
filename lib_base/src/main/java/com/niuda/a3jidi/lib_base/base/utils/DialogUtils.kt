package com.niuda.a3jidi.lib_base.base.utils

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.niuda.a3jidi.lib_base.R

/**
 * Created by mac on 2017/8/30.
 */

object DialogUtils {

    /**
     * 得到自定义的progressDialog
     *
     * @param context
     * @param msg
     * @return
     */
    fun createLoadingDialog(context: Context?, msg: String): Dialog? {
        val v = LayoutInflater.from(context).inflate(R.layout.system_dialog_loading, null)// 得到加载view
        val layout = v.findViewById<LinearLayout>(R.id.dialog_view)// 加载布局
        // main.xml中的ImageView
        val spaceshipImage = v.findViewById<View>(R.id.img) as ImageView
        val tipTextView = v.findViewById<View>(R.id.tipTextView) as TextView// 提示文字
        // 加载动画
        val hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                context, R.anim.loading_animation)
        // 使用ImageView显示动画
        spaceshipImage.startAnimation(hyperspaceJumpAnimation)
        tipTextView.text = msg// 设置加载信息

        val loadingDialog = Dialog(context, R.style.dialog_theme)// 创建自定义样式dialog

        loadingDialog.setCancelable(true)// 不可以用“返回键”取消
        loadingDialog.setContentView(layout, LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT))// 设置布局
        return loadingDialog

    }
}
