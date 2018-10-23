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

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.app.Dialog
import android.content.*
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Point
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.PowerManager
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.DisplayMetrics
import android.util.Log
import android.view.Display
import android.view.View
import android.view.ViewConfiguration
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import java.io.File
import java.lang.reflect.Field
import java.text.NumberFormat

/**
 * ================================================
 * 获取设备常用信息和处理设备常用操作的工具类
 *
 *
 * Created by JessYan on 2016/3/15
 * [Contact me](mailto:jess.yan.effort@gmail.com)
 * [Follow me](https://github.com/JessYanCoding)
 * ================================================
 */
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
class DeviceUtils private constructor() {

    init {
        throw IllegalStateException("you can't instantiate me!")
    }

    companion object {
        // 手机网络类型
        val NETTYPE_WIFI = 0x01
        val NETTYPE_CMWAP = 0x02
        val NETTYPE_CMNET = 0x03

        var GTE_HC: Boolean = false
        var GTE_ICS: Boolean = false
        var PRE_HC: Boolean = false
        private var _hasBigScreen: Boolean? = null
        private var _hasCamera: Boolean? = null
        private var _isTablet: Boolean? = null
        private var _loadFactor: Int? = null
        var displayDensity = 0.0f

        init {
            GTE_ICS = Build.VERSION.SDK_INT >= 14
            GTE_HC = Build.VERSION.SDK_INT >= 11
            PRE_HC = Build.VERSION.SDK_INT < 11
        }

        /**
         * dp转px
         *
         * @param context
         * @param dp
         * @return
         */
        fun dpToPixel(context: Context, dp: Float): Float {
            return dp * (getDisplayMetrics(context).densityDpi / 160f)
        }

        /**
         * px转dp
         *
         * @param context
         * @param f
         * @return
         */
        fun pixelsToDp(context: Context, f: Float): Float {
            return f / (getDisplayMetrics(context).densityDpi / 160f)
        }

        fun getDefaultLoadFactor(context: Context): Int {
            if (_loadFactor == null) {
                val integer = Integer.valueOf(0xf and context
                        .resources.configuration.screenLayout)
                _loadFactor = integer
                _loadFactor = Integer.valueOf(Math.max(integer.toInt(), 1))
            }
            return _loadFactor!!.toInt()
        }

        fun getDensity(context: Context): Float {
            if (displayDensity.toDouble() == 0.0)
                displayDensity = getDisplayMetrics(context).density
            return displayDensity
        }

        fun getDisplayMetrics(context: Context): DisplayMetrics {
            val displaymetrics = DisplayMetrics()
            (context.getSystemService(
                    Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.getMetrics(
                    displaymetrics)
            return displaymetrics
        }

        /**
         * 屏幕高度
         *
         * @param context
         * @return
         */
        fun getScreenHeight(context: Context): Float {
            return getDisplayMetrics(context).heightPixels.toFloat()
        }

        /**
         * 屏幕宽度
         *
         * @param context
         * @return
         */
        fun getScreenWidth(context: Context): Float {
            return getDisplayMetrics(context).widthPixels.toFloat()
        }

        /**
         * 获取activity尺寸
         *
         * @param activity
         * @return
         */
        fun getRealScreenSize(activity: Activity): IntArray {
            val size = IntArray(2)
            var screenWidth = 0
            var screenHeight = 0
            val w = activity.windowManager
            val d = w.defaultDisplay
            val metrics = DisplayMetrics()
            d.getMetrics(metrics)
            // since SDK_INT = 1;
            screenWidth = metrics.widthPixels
            screenHeight = metrics.heightPixels
            // includes window decorations (statusbar bar/menu bar)
            if (Build.VERSION.SDK_INT in 14..16)
                try {
                    screenWidth = Display::class.java.getMethod("getRawWidth").invoke(d) as Int
                    screenHeight = Display::class.java.getMethod("getRawHeight").invoke(d) as Int
                } catch (ignored: Exception) {
                }

            // includes window decorations (statusbar bar/menu bar)
            if (Build.VERSION.SDK_INT >= 17)
                try {
                    val realSize = Point()
                    Display::class.java.getMethod("getRealSize", Point::class.java).invoke(d,realSize)
                    screenWidth = realSize.x
                    screenHeight = realSize.y
                } catch (ignored: Exception) {
                }

            size[0] = screenWidth
            size[1] = screenHeight
            return size
        }

        @SuppressLint("PrivateApi")
                /**
         * 获取状态栏高度
         *
         * @param context
         * @return
         */
        fun getStatusBarHeight(context: Context): Int {
            var c: Class<*>? = null
            var obj: Any? = null
            var field: Field? = null
            var x = 0
            try {
                c = Class.forName("com.android.internal.R\$dimen")
                obj = c!!.newInstance()
                field = c.getField("status_bar_height")
                x = Integer.parseInt(field!!.get(obj).toString())
                return context.resources
                        .getDimensionPixelSize(x)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return 0
        }


        fun hasBigScreen(context: Context): Boolean {
            var flag = true
            if (_hasBigScreen == null) {
                val flag1: Boolean
                if (0xf and context.resources
                                .configuration.screenLayout >= 3)
                    flag1 = flag
                else
                    flag1 = false
                val boolean1 = java.lang.Boolean.valueOf(flag1)
                _hasBigScreen = boolean1
                if (!boolean1) {
                    if (getDensity(context) <= 1.5f)
                        flag = false
                    _hasBigScreen = java.lang.Boolean.valueOf(flag)
                }
            }
            return _hasBigScreen!!
        }

        /**
         * 设备是否有相机
         *
         * @param context
         * @return
         */
        fun hasCamera(context: Context): Boolean {
            if (_hasCamera == null) {
                val pckMgr = context.packageManager
                val flag = pckMgr.hasSystemFeature("android.hardware.camera.front")
                val flag1 = pckMgr.hasSystemFeature("android.hardware.camera")
                val flag2: Boolean
                flag2 = flag || flag1
                _hasCamera = java.lang.Boolean.valueOf(flag2)
            }
            return _hasCamera!!
        }

        /**
         * 设备是否有实体菜单
         *
         * @param context
         * @return
         */
        fun hasHardwareMenuKey(context: Context): Boolean {
            var flag = false
            flag = if (PRE_HC)
                true
            else if (GTE_ICS) {
                ViewConfiguration.get(context).hasPermanentMenuKey()
            } else
                false
            return flag
        }

        /**
         * 当前是否有网
         *
         * @param context
         * @return
         */
        fun hasInternet(context: Context): Boolean {
            val flag: Boolean
            val manager = context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            flag = manager.activeNetworkInfo != null
            return flag
        }

        /**
         * 当前的包是否存在
         *
         * @param context
         * @param pckName
         * @return
         */
        fun isPackageExist(context: Context, pckName: String): Boolean {
            try {
                val pckInfo = context.packageManager.getPackageInfo(pckName, 0)
                if (pckInfo != null)
                    return true
            } catch (e: PackageManager.NameNotFoundException) {
                Log.e("TDvice", e.message)
            }

            return false
        }

        fun hideAnimatedView(view: View?) {
            if (PRE_HC && view != null)
                view.setPadding(view.width, 0, 0, 0)
        }

        /**
         * 隐藏软键盘
         *
         * @param context
         * @param view
         */
        fun hideSoftKeyboard(context: Context, view: View?) {
            if (view == null)
                return
            val inputMethodManager = context.getSystemService(
                    Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (inputMethodManager.isActive)
                inputMethodManager.hideSoftInputFromWindow(
                        view.windowToken, 0)
        }

        /**
         * 是否是横屏
         *
         * @param context
         * @return
         */
        fun isLandscape(context: Context): Boolean {
            return context.resources.configuration.orientation == 2
        }

        /**
         * 是否是竖屏
         *
         * @param context
         * @return
         */
        fun isPortrait(context: Context): Boolean {
            var flag = true
            if (context.resources.configuration.orientation != 1)
                flag = false
            return flag
        }

        fun isTablet(context: Context): Boolean {
            if (_isTablet == null) {
                val flag = 0xf and context.resources.configuration.screenLayout >= 3
                _isTablet = java.lang.Boolean.valueOf(flag)
            }
            return _isTablet!!
        }


        fun showAnimatedView(view: View?) {
            if (PRE_HC && view != null)
                view.setPadding(0, 0, 0, 0)
        }

        fun showSoftKeyboard(dialog: Dialog) {
            dialog.window!!.setSoftInputMode(4)
        }

        fun showSoftKeyboard(context: Context, view: View) {
            (context.getSystemService(
                    Context.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(view,
                    InputMethodManager.SHOW_FORCED)
        }

        fun toogleSoftKeyboard(context: Context, view: View) {
            (context.getSystemService(
                    Context.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(0,
                    InputMethodManager.HIDE_NOT_ALWAYS)
        }

        val isSdcardReady: Boolean
            get() = Environment.MEDIA_MOUNTED == Environment
                    .getExternalStorageState()

        fun getCurCountryLan(context: Context): String {
            return (context.resources.configuration.locale
                    .language
                    + "-"
                    + context.resources.configuration.locale
                    .country)
        }

        fun isZhCN(context: Context): Boolean {
            val lang = context.resources
                    .configuration.locale.country
            return lang.equals(other = "CN", ignoreCase = true)
        }

        fun percent(p1: Double, p2: Double): String {
            val str: String
            val p3 = p1 / p2
            val nf = NumberFormat.getPercentInstance()
            nf.minimumFractionDigits = 2
            str = nf.format(p3)
            return str
        }

        fun percent2(p1: Double, p2: Double): String {
            val str: String
            val p3 = p1 / p2
            val nf = NumberFormat.getPercentInstance()
            nf.minimumFractionDigits = 0
            str = nf.format(p3)
            return str
        }


        fun isHaveMarket(context: Context): Boolean {
            val intent = Intent()
            intent.action = "android.intent.action.MAIN"
            intent.addCategory("android.intent.category.APP_MARKET")
            val pm = context.packageManager
            val infos = pm.queryIntentActivities(intent, 0)
            return infos.size > 0
        }

        fun setFullScreen(activity: Activity) {
            val params = activity.window
                    .attributes
            params.flags = params.flags or WindowManager.LayoutParams.FLAG_FULLSCREEN
            activity.window.attributes = params
            activity.window.addFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }

        fun cancelFullScreen(activity: Activity) {
            val params = activity.window
                    .attributes
            params.flags = params.flags and WindowManager.LayoutParams.FLAG_FULLSCREEN.inv()
            activity.window.attributes = params
            activity.window.clearFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }

        fun getPackageInfo(context: Context, pckName: String): PackageInfo? {
            try {
                return context.packageManager
                        .getPackageInfo(pckName, 0)
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }

            return null
        }

        /**
         * 获取版本号
         *
         * @param context
         * @return
         */
        fun getVersionCode(context: Context): Int {
            var versionCode = 0
            try {
                versionCode = context.packageManager
                        .getPackageInfo(context.packageName,
                                0).versionCode
            } catch (ex: PackageManager.NameNotFoundException) {
                versionCode = 0
            }

            return versionCode
        }

        /**
         * 获取指定包名应用的版本号
         *
         * @param context
         * @param packageName
         * @return
         */
        fun getVersionCode(context: Context, packageName: String): Int {
            var versionCode = 0
            try {
                versionCode = context.packageManager
                        .getPackageInfo(packageName, 0).versionCode
            } catch (ex: PackageManager.NameNotFoundException) {
                versionCode = 0
            }

            return versionCode
        }

        /**
         * 获取版本名
         *
         * @param context
         * @return
         */
        fun getVersionName(context: Context): String {
            return try {
                context.packageManager.getPackageInfo(context.packageName,0).versionName
            } catch (ex: PackageManager.NameNotFoundException) {
                ""
            }
        }

        fun isScreenOn(context: Context): Boolean {
            val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
            return pm.isScreenOn
        }

        /**
         * 安装应用
         *
         * @param context
         * @param file
         */
        fun installAPK(context: Context, file: File?) {
            if (file == null || !file.exists())
                return
            val intent = Intent()
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.action = Intent.ACTION_VIEW
            intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive")
            context.startActivity(intent)
        }

        fun getInstallApkIntent(file: File): Intent {
            val intent = Intent()
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.action = Intent.ACTION_VIEW
            intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive")
            return intent
        }

        /**
         * 拨打电话
         *
         * @param context
         * @param number
         */
        fun openDial(context: Context, number: String) {
            val uri = Uri.parse("tel:$number")
            val it = Intent(Intent.ACTION_DIAL, uri)
            context.startActivity(it)
        }

        fun openSMS(context: Context, smsBody: String, tel: String) {
            val uri = Uri.parse("smsto:$tel")
            val it = Intent(Intent.ACTION_SENDTO, uri)
            it.putExtra("sms_body", smsBody)
            context.startActivity(it)
        }

        fun openDail(context: Context) {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }

        fun openSendMsg(context: Context) {
            val uri = Uri.parse("smsto:")
            val intent = Intent(Intent.ACTION_SENDTO, uri)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }

        fun openCamera(context: Context) {
            val intent = Intent() // 调用照相机
            intent.action = "android.media.action.STILL_IMAGE_CAMERA"
            intent.flags = 0x34c40000
            context.startActivity(intent)
        }

        @SuppressLint("MissingPermission")
        fun getIMEI(context: Context): String {
            val tel = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            return tel.deviceId
        }

        val phoneType: String
            get() = Build.MODEL

        fun openApp(context: Context, packageName: String) {
            var mainIntent = context.packageManager
                    .getLaunchIntentForPackage(packageName)
            if (mainIntent == null) {
                mainIntent = Intent(packageName)
            } else {
            }
            context.startActivity(mainIntent)
        }

        fun openAppActivity(context: Context, packageName: String,
                            activityName: String): Boolean {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_LAUNCHER)
            val cn = ComponentName(packageName, activityName)
            intent.component = cn
            try {
                context.startActivity(intent)
                return true
            } catch (e: Exception) {
                return false
            }

        }

        /**
         * wifi是否开启
         *
         * @param context
         * @return
         */
        fun isWifiOpen(context: Context): Boolean {
            var isWifiConnect = false
            val cm = context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            // check the networkInfos numbers
            val networkInfos = cm.allNetworkInfo
            for (i in networkInfos.indices) {
                if (networkInfos[i].state == NetworkInfo.State.CONNECTED) {
                    if (networkInfos[i].type == ConnectivityManager.TYPE_MOBILE) {
                        isWifiConnect = false
                    }
                    if (networkInfos[i].type == ConnectivityManager.TYPE_WIFI) {
                        isWifiConnect = true
                    }
                }
            }
            return isWifiConnect
        }

        /**
         * 卸载软件
         *
         * @param context
         * @param packageName
         */
        fun uninstallApk(context: Context, packageName: String) {
            if (isPackageExist(context, packageName)) {
                val packageURI = Uri.parse("package:$packageName")
                val uninstallIntent = Intent(Intent.ACTION_DELETE,
                        packageURI)
                context.startActivity(uninstallIntent)
            }
        }

        fun copyTextToBoard(context: Context, string: String) {
            if (TextUtils.isEmpty(string))
                return
            val clip = context
                    .getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clip.text = string
        }

        /**
         * 发送邮件
         *
         * @param context
         * @param subject 主题
         * @param content 内容
         * @param emails  邮件地址
         */
        fun sendEmail(context: Context, subject: String,
                      content: String, vararg emails: String) {
            try {
                val intent = Intent(Intent.ACTION_SEND)
                // 模拟器
                // intent.setType("text/plain");
                intent.type = "message/rfc822" // 真机
                intent.putExtra(Intent.EXTRA_EMAIL, emails)
                intent.putExtra(Intent.EXTRA_SUBJECT, subject)
                intent.putExtra(Intent.EXTRA_TEXT, content)
                context.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
            }

        }

        fun getStatuBarHeight(context: Context): Int {
            var c: Class<*>? = null
            var obj: Any? = null
            var field: Field? = null
            var x = 0
            var sbar = 38// 默认为38，貌似大部分是这样的
            try {
                c = Class.forName("com.android.internal.R\$dimen")
                obj = c!!.newInstance()
                field = c.getField("status_bar_height")
                x = Integer.parseInt(field!!.get(obj).toString())
                sbar = context.resources
                        .getDimensionPixelSize(x)

            } catch (e1: Exception) {
                e1.printStackTrace()
            }

            return sbar
        }


        fun hasStatusBar(activity: Activity): Boolean {
            val attrs = activity.window.attributes
            return attrs.flags and WindowManager.LayoutParams.FLAG_FULLSCREEN != WindowManager.LayoutParams.FLAG_FULLSCREEN
        }

        /**
         * 调用系统安装了的应用分享
         *
         * @param context
         * @param title
         * @param url
         */
        fun showSystemShareOption(context: Activity,title: String, url: String) {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_SUBJECT, "分享：$title")
            intent.putExtra(Intent.EXTRA_TEXT, "$title $url")
            context.startActivity(Intent.createChooser(intent, "选择分享"))
        }

        /**
         * 获取当前网络类型
         *
         * @return 0：没有网络 1：WIFI网络 2：WAP网络 3：NET网络
         */
        fun getNetworkType(context: Context): Int {
            var netType = 0
            val connectivityManager = context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo ?: return netType
            val nType = networkInfo.type
            if (nType == ConnectivityManager.TYPE_MOBILE) {
                val extraInfo = networkInfo.extraInfo
                if (extraInfo != null && !extraInfo.isEmpty()) {
                    if (extraInfo.equals("cmnet", ignoreCase = true)) {
                        netType = NETTYPE_CMNET
                    } else {
                        netType = NETTYPE_CMWAP
                    }
                }
            } else if (nType == ConnectivityManager.TYPE_WIFI) {
                netType = NETTYPE_WIFI
            }
            return netType
        }

        fun netIsConnected(context: Context): Boolean {
            val connectMgr = context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            //手机网络连接状态
            val mobNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
            //WIFI连接状态
            val wifiNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
            return !(!mobNetInfo.isConnected && !wifiNetInfo.isConnected)
        }

        /**
         * 判断是否存在sd卡
         *
         * @return
         */
        val isExitsSdcard: Boolean
            get() = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }


}


