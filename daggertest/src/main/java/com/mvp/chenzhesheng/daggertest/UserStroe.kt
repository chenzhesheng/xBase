package com.mvp.chenzhesheng.daggertest

import android.util.Log
import javax.inject.Inject

/**
 * 作者: created by chenzhesheng on 2018/4/19 16:15
 */


class UserStroe @Inject constructor(var url: String) {

    fun login(){
        Log.i("TAG","UserStroe")
    }
}