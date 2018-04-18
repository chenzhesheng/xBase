package com.niuda.a3jidi.laok.booklook.dagger

import dagger.Component

/**
 * 作者: created by chenzhesheng on 2018/4/18 16:35
 */


@Component(dependencies = arrayOf(UserAppComponent::class), modules = arrayOf(UserPresenterModule::class))
class BookPresenterComponent {
}