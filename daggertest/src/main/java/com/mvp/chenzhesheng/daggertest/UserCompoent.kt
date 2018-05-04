package com.mvp.chenzhesheng.daggertest

import dagger.Component

/**
 * 作者: created by chenzhesheng on 2018/4/19 14:39
 */

@Component(modules = arrayOf(UserModule::class))
interface UserCompoent {
    fun Inject(mainActivity: MainActivity)
}