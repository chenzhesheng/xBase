package com.niuda.a3jidi.lib_base.base.utils


import android.content.Context
import android.content.SharedPreferences
import android.support.v4.util.SimpleArrayMap

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/08/02
 * desc  : SP相关工具类
</pre> *
 */
class SPUtils private constructor(spName: String) {
    private val sp: SharedPreferences

    /**
     * SP中获取所有键值对
     *
     * @return Map对象
     */
    val all: Map<String, *>
        get() = sp.all

    init {
        sp = Zson.get().app.getSharedPreferences(spName, Context.MODE_PRIVATE)
    }

    /**
     * SP中写入String
     *
     * @param key   键
     * @param value 值
     */
    fun save(key: String, value: String): SPUtils {
        sp.edit().putString(key, value).apply()
        return this
    }

    /**
     * SP中读取String
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值`defaultValue`
     */
    @JvmOverloads
    fun getString(key: String, defaultValue: String = ""): String {
        return sp.getString(key, defaultValue)
    }

    /**
     * SP中写入int
     *
     * @param key   键
     * @param value 值
     */
    fun save(key: String, value: Int): SPUtils {
        sp.edit().putInt(key, value).apply()
        return this
    }

    /**
     * SP中读取int
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值`defaultValue`
     */
    @JvmOverloads
    fun getInt(key: String, defaultValue: Int = -1): Int {
        return sp.getInt(key, defaultValue)
    }

    /**
     * SP中写入long
     *
     * @param key   键
     * @param value 值
     */
    fun save(key: String, value: Long): SPUtils {
        sp.edit().putLong(key, value).apply()
        return this
    }

    /**
     * SP中读取long
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值`defaultValue`
     */
    @JvmOverloads
    fun getLong(key: String, defaultValue: Long = -1L): Long {
        return sp.getLong(key, defaultValue)
    }

    /**
     * SP中写入float
     *
     * @param key   键
     * @param value 值
     */
    fun save(key: String, value: Float): SPUtils {
        sp.edit().putFloat(key, value).apply()
        return this
    }

    /**
     * SP中读取float
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值`defaultValue`
     */
    @JvmOverloads
    fun getFloat(key: String, defaultValue: Float = -1f): Float {
        return sp.getFloat(key, defaultValue)
    }

    /**
     * SP中写入boolean
     *
     * @param key   键
     * @param value 值
     */
    fun save(key: String, value: Boolean): SPUtils {
        sp.edit().putBoolean(key, value).apply()
        return this
    }

    /**
     * SP中读取boolean
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值`defaultValue`
     */
    @JvmOverloads
    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return sp.getBoolean(key, defaultValue)
    }

    /**
     * SP中写入String集合
     *
     * @param key    键
     * @param values 值
     */
    fun save(key: String, values: Set<String>): SPUtils {
        sp.edit().putStringSet(key, values).apply()
        return this
    }

    /**
     * SP中读取StringSet
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值`defaultValue`
     */
    @JvmOverloads
    fun getStringSet(key: String, defaultValue: Set<String> = emptySet()): Set<String> {
        return sp.getStringSet(key, defaultValue)
    }

    /**
     * SP中是否存在该key
     *
     * @param key 键
     * @return `true`: 存在<br></br>`false`: 不存在
     */
    operator fun contains(key: String): Boolean {
        return sp.contains(key)
    }

    /**
     * SP中移除该key
     *
     * @param key 键
     */
    fun remove(key: String) {
        sp.edit().remove(key).apply()
    }

    /**
     * SP中清除所有数据
     */
    fun clear() {
        sp.edit().clear().apply()
    }

    companion object {

        private val SP_UTILS_MAP = SimpleArrayMap<String, SPUtils>()
        private val DEFAULT_PREFFILE_NAME = "default_preffile"

        /**
         * 获取SP实例
         *
         * @param spName sp名
         * @return [SPUtils]
         */
        @JvmOverloads
        operator fun get(spName: String = ""): SPUtils? {
            var spName = spName
            if (isSpace(spName)) {
                spName = DEFAULT_PREFFILE_NAME
            }
            var spUtils: SPUtils? = null
            synchronized(SP_UTILS_MAP) {
                spUtils = SP_UTILS_MAP.get(spName)
                if (spUtils == null) {
                    spUtils = SPUtils(spName)
                    SP_UTILS_MAP.put(spName, spUtils)
                }
            }
            return spUtils
        }

        private fun isSpace(s: String?): Boolean {
            if (s == null) return true
            var i = 0
            val len = s.length
            while (i < len) {
                if (!Character.isWhitespace(s[i])) {
                    return false
                }
                ++i
            }
            return true
        }
    }
}
/**
 * 获取SP实例
 *
 * @return [SPUtils]
 */
/**
 * SP中读取String
 *
 * @param key 键
 * @return 存在返回对应值，不存在返回默认值`""`
 */
/**
 * SP中读取int
 *
 * @param key 键
 * @return 存在返回对应值，不存在返回默认值-1
 */
/**
 * SP中读取long
 *
 * @param key 键
 * @return 存在返回对应值，不存在返回默认值-1
 */
/**
 * SP中读取float
 *
 * @param key 键
 * @return 存在返回对应值，不存在返回默认值-1
 */
/**
 * SP中读取boolean
 *
 * @param key 键
 * @return 存在返回对应值，不存在返回默认值`false`
 */
/**
 * SP中读取StringSet
 *
 * @param key 键
 * @return 存在返回对应值，不存在返回默认值`Collections.<String>emptySet()`
 */
