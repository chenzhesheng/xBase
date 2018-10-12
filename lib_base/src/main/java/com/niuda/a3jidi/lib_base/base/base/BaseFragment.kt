package com.gdth.base

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.niuda.a3jidi.lib_base.base.base.BaseActivity

/**
 * Created by mac on 2017/9/13.
 */
abstract class BaseFragment : Fragment() {

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

    var mContext: Activity? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mContext = activity
        return inflater.inflate(getContentLayout(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view, savedInstanceState)
    }

    fun initViews(layoutInflater: LayoutInflater?, viewGroup: ViewGroup?, savedInstanceState: Bundle?): View? = null

    protected abstract fun getContentLayout(): Int
    protected abstract fun init(view: View?, savedInstanceState: Bundle?)

    override fun onDestroy() {
        super.onDestroy()
    }

    fun getColorValue(resId: Int): Int {
        return if (Build.VERSION.SDK_INT >= 23) {
            context?.getColor(resId)!!
        } else {
            resources.getColor(resId)
        }
    }
}