/*
 * Copyright 2016 jeasonlzy(廖子尧)
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
package com.niuda.a3jidi.lib_base.base.base

import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.Window
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.niuda.a3jidi.lib_base.R
import com.niuda.a3jidi.lib_base.base.utils.SystemBarUtils

/**
 * (￣▽￣)"
 *
 * @version V1.0 :
 * @author: Administrator
 * @date: 2017-11-11 14:07
 */
abstract class BaseActivity : AppCompatActivity() {

    private var dialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        // SystemBarUtils.initSystemBarTint(this, translucentStatusBar(), setStatusBarColor());
        setContentView(initLayout())
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT              //避免切换横竖屏
        AppManager.appManager!!.addActivity(this)                                   //将当前Activity添加到容器
        initView()
        initListener()
        initData()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home// 点击返回图标事件
            -> {
                finish()
                return super.onOptionsItemSelected(item)
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    // 子类可以重写改变状态栏颜色
    protected fun setStatusBarColor(): Int {
        return SystemBarUtils.getColorPrimary(this)
    }

    // 子类可以重写决定是否使用透明状态栏
    protected fun translucentStatusBar(): Boolean {
        return false
    }

    // 添加布局
    protected abstract fun initLayout(): Int

    // 初始化view
    protected abstract fun initView()

    // 初始化监听器的代码写在这个方法中
    abstract fun initListener()

    // 初始数据的代码写在这个方法中，用于从服务器获取数据
    abstract fun initData()


    override fun onDestroy() {
        super.onDestroy()
        AppManager.appManager!!.removeActivity(this)
    }

    // 通过Class跳转界面
    fun startActivityForResult(cls: Class<*>, requestCode: Int) {
        startActivityForResult(cls, null, requestCode)
    }

    // 含有Bundle通过Class跳转界面
    fun startActivityForResult(cls: Class<*>, bundle: Bundle?, requestCode: Int) {
        val intent = Intent()
        intent.setClass(this, cls)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivityForResult(intent, requestCode)
    }

    // 含有Bundle通过Class跳转界面
    @JvmOverloads
    fun startActivity(cls: Class<*>, bundle: Bundle? = null) {
        val intent = Intent()
        intent.setClass(this, cls)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }

    // 初始化 Toolbar
    fun initToolBar(toolbar: Toolbar, homeAsUpEnabled: Boolean, title: String) {
        toolbar.title = title
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(homeAsUpEnabled)
    }

    fun initToolBar(toolbar: Toolbar, homeAsUpEnabled: Boolean, resTitle: Int) {
        initToolBar(toolbar, homeAsUpEnabled, getString(resTitle))
    }

    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun showLoading() {
        if (dialog != null && dialog!!.isShowing) return
        dialog = ProgressDialog(this)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setCanceledOnTouchOutside(false)
        dialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        dialog!!.setMessage("请求网络中...")
        dialog!!.show()
    }

    fun dismissLoading() {
        if (dialog != null && dialog!!.isShowing) {
            dialog!!.dismiss()
        }
    }

    fun displayImage(url: String, imageView: ImageView) {
        Glide.with(applicationContext)//
                .load(url)//
                .error(R.mipmap.ic_launcher)//
                .into(imageView)
    }
}