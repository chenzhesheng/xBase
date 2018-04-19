package com.niuda.a3jidi.laok.booklook.dagger

import com.gdth.dagger.module.PresenterModule
import com.gdth.login.dagger.scope.PreView
import com.niuda.a3jidi.laok.booklook.view.activity.MainActivity
import dagger.Component
import javax.inject.Singleton

/**
 * 作者: created by chenzhesheng on 2018/4/18 16:35
 */

@PreView
@Singleton
@Component(dependencies = [(AppComponent::class)] , modules = [(PresenterModule::class)])
interface PresenterComponent {
    fun inject(mainActivity: MainActivity)
}