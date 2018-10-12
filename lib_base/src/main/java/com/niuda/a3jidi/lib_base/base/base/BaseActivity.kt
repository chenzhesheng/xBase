package com.niuda.a3jidi.lib_base.base.base

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.TypedValue
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.gyf.barlibrary.ImmersionBar
import com.niuda.a3jidi.lib_base.R
import kotlinx.android.synthetic.main.layout_toolbar.*


abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ImmersionBar.with(this)
                .statusBarColor(R.color.colorPrimary)
                .init()  //必须调用方可沉浸式
        setContentView(layoutResId())
        onCreated(savedInstanceState)
    }

    protected abstract fun layoutResId(): Int
    protected abstract fun onCreated(savedInstanceState: Bundle?)

    fun setPageTitle(pageTitle: String) {
        supportActionBar?.title = ""
        toolbar_title.text = pageTitle
    }

    fun initToolbar(isShowHomeUp: Boolean): BaseActivity {
        initToolbar(toolbar, title.toString(), isShowHomeUp)
        return this
    }

    fun initToolbar(toolbar: Toolbar?, title: String, isShowHomeUp: Boolean) {
        if (toolbar == null) return
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(isShowHomeUp)
        supportActionBar?.setDisplayHomeAsUpEnabled(isShowHomeUp)
        supportActionBar?.setDisplayShowHomeEnabled(isShowHomeUp)
        val titleText:View? = toolbar.findViewById(R.id.toolbar_title)
        if(titleText!=null&&titleText is TextView){
            supportActionBar?.title = ""
            titleText.text = title
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        finish()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fragments = supportFragmentManager.fragments
        if (fragments.isNotEmpty()) {
            fragments.forEach {
                it.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

    fun getColorValue(resId: Int): Int {
        return if (Build.VERSION.SDK_INT >= 23) {
            super.getColor(resId)
        } else {
            resources.getColor(resId)
        }
    }

    fun dip2px(context: Context, dpValue: Float): Int =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, resources.displayMetrics).toInt()

    fun px2dip(context: Context, pxValue: Float): Int =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, pxValue, resources.displayMetrics).toInt()

    override fun onDestroy() {
        super.onDestroy()
        ImmersionBar.with(this).destroy(); //必须调用该方法，防止内存泄漏
    }
}
