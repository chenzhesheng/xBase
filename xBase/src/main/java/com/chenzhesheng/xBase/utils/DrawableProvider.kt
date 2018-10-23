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

import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.media.ExifInterface
import android.widget.TextView

import java.io.IOException

/**
 * ================================================
 * 处理 [Drawable] 和 [Bitmap] 的工具类
 *
 *
 * Created by JessYan on 2015/11/24
 * [Contact me](mailto:jess.yan.effort@gmail.com)
 * [Follow me](https://github.com/JessYanCoding)
 * ================================================
 */
class DrawableProvider private constructor() {

    init {
        throw IllegalStateException("you can't instantiate me!")
    }

    companion object {

        /**
         * 获得选择器
         *
         * @param normalDrawable
         * @param pressDrawable
         * @return
         */
        fun getStateListDrawable(normalDrawable: Drawable, pressDrawable: Drawable): Drawable {
            val stateListDrawable = StateListDrawable()
            stateListDrawable.addState(intArrayOf(android.R.attr.state_checked), pressDrawable)
            stateListDrawable.addState(intArrayOf(), normalDrawable)
            return stateListDrawable
        }

        /**
         * 将 TextView/RadioButton 中设置的 drawable 动态的缩放
         *
         * @param percent
         * @param tv
         * @return
         */
        fun getScaleDrawableForRadioButton(percent: Float, tv: TextView): Drawable {
            val compoundDrawables = tv.compoundDrawables
            var drawable: Drawable? = null
            for (d in compoundDrawables) {
                if (d != null) {
                    drawable = d
                }
            }
            return getScaleDrawable(percent, drawable!!)
        }

        /**
         * 将 TextView/RadioButton 中设置的 drawable 动态的缩放
         *
         * @param tv
         * @return
         */
        fun getScaleDrawableForRadioButton2(width: Float, tv: TextView): Drawable {
            val compoundDrawables = tv.compoundDrawables
            var drawable: Drawable? = null
            for (d in compoundDrawables) {
                if (d != null) {
                    drawable = d
                }
            }
            return getScaleDrawable2(width, drawable!!)
        }

        /**
         * 传入图片,将图片按传入比例缩放
         *
         * @param percent
         * @return
         */
        fun getScaleDrawable(percent: Float, drawable: Drawable): Drawable {
            drawable.setBounds(0, 0, (drawable.intrinsicWidth * percent + 0.5f).toInt(), (drawable.intrinsicHeight * percent + 0.5f).toInt())
            return drawable
        }

        /**
         * 传入图片,将图片按传入宽度和原始宽度的比例缩放
         *
         * @param width
         * @return
         */
        fun getScaleDrawable2(width: Float, drawable: Drawable): Drawable {
            val percent = width * 1.0f / drawable.intrinsicWidth
            return getScaleDrawable(percent, drawable)
        }

        /**
         * 设置左边的drawable
         *
         * @param tv
         * @param drawable
         */
        fun setLeftDrawable(tv: TextView, drawable: Drawable) {
            tv.setCompoundDrawables(drawable, null, null, null)
        }

        /**
         * 改变Bitmap的长宽
         *
         * @param bitmap
         * @return
         */
        fun getReSizeBitmap(bitmap: Bitmap, targetWidth: Float, targetheight: Float): Bitmap {
            var returnBm: Bitmap? = null
            val width = bitmap.width
            val height = bitmap.height
            val matrix = Matrix()
            matrix.postScale(targetWidth / width, targetheight / height) //长和宽放大缩小的比例
            try {
                returnBm = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            if (returnBm == null) {
                returnBm = bitmap
            }
            if (bitmap != returnBm) {
                bitmap.recycle()
            }
            return returnBm
        }


        /**
         * 将图片按照某个角度进行旋转
         *
         * @param bm     需要旋转的图片
         * @param degree 旋转角度
         * @return 旋转后的图片
         */
        fun rotateBitmapByDegree(bm: Bitmap, degree: Int): Bitmap? {
            var returnBm: Bitmap? = null

            // 根据旋转角度，生成旋转矩阵
            val matrix = Matrix()
            matrix.postRotate(degree.toFloat())
            try {
                // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
                returnBm = Bitmap.createBitmap(bm, 0, 0, bm.width, bm.height, matrix, true)
            } catch (e: OutOfMemoryError) {
                e.printStackTrace()
            }

            if (returnBm == null) {
                returnBm = bm
            }
            if (bm != returnBm) {
                bm.recycle()
            }
            return returnBm
        }


        /**
         * 读取图片的旋转的角度
         *
         * @param path 图片绝对路径
         * @return 图片的旋转角度
         */
        fun getBitmapDegree(path: String): Int {
            var degree = 0
            try {
                // 从指定路径下读取图片，并获取其EXIF信息
                val exifInterface = ExifInterface(path)
                // 获取图片的旋转信息
                val orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_NORMAL)
                when (orientation) {
                    ExifInterface.ORIENTATION_ROTATE_90 -> degree = 90
                    ExifInterface.ORIENTATION_ROTATE_180 -> degree = 180
                    ExifInterface.ORIENTATION_ROTATE_270 -> degree = 270
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return degree
        }
    }

}