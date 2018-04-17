package com.niuda.a3jidi.lib_base.base.base

import android.app.Activity
import java.util.*

/**
 * (￣▽￣)"
 *
 * @version V1.0 :
 * @author: Administrator
 * @date: 2017-12-26 16:36
 */
class AppManager private constructor() {

    /**
     * 添加Activity到堆栈
     */
    fun addActivity(activity: Activity) {
        if (activityStack == null) {
            activityStack = Stack()
        }
        activityStack!!.add(activity)
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    fun currentActivity(): Activity? {
        return if (activityStack!!.size == 0) {
            null
        } else activityStack!!.lastElement()
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    fun finishActivity() {
        if (activityStack != null && activityStack!!.size > 0) {
            val activity = activityStack!!.lastElement()
            finishActivity(activity)
        }
    }

    /**
     * 结束指定的Activity
     */
    fun finishActivity(activity: Activity?) {
        var activity = activity
        if (activity != null && !activity.isFinishing) {
            activityStack!!.remove(activity)
            activity.finish()
            activity = null
        }
    }

    /**
     * 移除指定的Activity
     * @param activity
     */
    fun removeActivity(activity: Activity?) {
        if (activity != null) {
            activityStack!!.remove(activity)
        }
    }

    /**
     * 结束指定类名的Activity
     */
    fun finishActivity(cls: Class<*>) {
        for (activity in activityStack!!) {
            if (activity.javaClass == cls) {
                finishActivity(activity)
            }
        }
    }

    /**
     * 结束所有Activity
     */
    private fun finishAllActivity() {
        var i = 0
        val size = activityStack!!.size
        while (i < size) {
            if (null != activityStack!![i]) {
                activityStack!![i].finish()
            }
            i++
        }
        activityStack!!.clear()
    }

    /**
     * 退出应用程序
     */
    fun AppExit() {
        try {
            finishAllActivity()
            android.os.Process.killProcess(android.os.Process.myPid())
            System.exit(0)
        } catch (e: Exception) {
            //
        } finally {
            //
        }
    }

    companion object {

        //栈：也就是stack
        private var activityStack: Stack<Activity>? = null
        @Volatile
        private var instance: AppManager? = null

        /**
         * 单一实例
         */
        val appManager: AppManager?
            get() {
                if (instance == null) {
                    synchronized(AppManager::class.java) {
                        if (instance == null) {
                            instance = AppManager()
                        }
                    }
                }
                return instance
            }
    }

}
