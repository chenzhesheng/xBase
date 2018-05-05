package com.gdth.base

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.gdth.bank.base.annotation.Layout
import com.niuda.a3jidi.lib_base.base.base.BaseActivity
import java.util.*

/**
 * Created by mac on 2017/6/29.
 */
abstract class BaseDialogFragment : DialogFragment() {
    val FTAG = UUID.randomUUID().toString()

    fun initToolbar(toolbar: Toolbar, isShowHomeUp: Boolean) {
        initToolbar(toolbar, activity?.title.toString(), isShowHomeUp)
    }

    fun initToolbar(toolbar: Toolbar, title: String, isShowHomeUp: Boolean) {
        if (activity is BaseActivity) {
            val act = activity as BaseActivity
            act.initToolbar(toolbar, title, isShowHomeUp)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                activity?.finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * 标志位，View已经初始化完成。
     * 2016/04/29
     * 用isAdded()属性代替
     * 2016/05/03
     * isPrepared还是准一些,isAdded有可能出现onCreateView没走完但是isAdded了
     */
    private var isPrepared: Boolean = false
    /**
     * 是否第一次加载
     */
    private var isFirstLoad = true

    var mContext: Activity? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mContext = activity

        isFirstLoad = true
        var view: View? = null
        val layoutRes = javaClass.getAnnotation(Layout::class.java)
        if (layoutRes != null) {
            view = inflater.inflate(layoutRes.value, container, false)
        } else {
            initViews(inflater, container, savedInstanceState)
        }
        isPrepared = true
        init(view)
        return view!!
    }

    fun initViews(layoutInflater: LayoutInflater?, viewGroup: ViewGroup?, savedInstanceState: Bundle?){}

    protected abstract fun init(view: View?)

    override fun onDestroy() {
        super.onDestroy()
    }
}