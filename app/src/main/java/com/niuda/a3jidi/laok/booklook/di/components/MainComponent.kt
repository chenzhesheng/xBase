package com.niuda.a3jidi.laok.booklook.di.components

import com.niuda.a3jidi.laok.booklook.di.modules.ActivityModule
import com.niuda.a3jidi.laok.booklook.di.modules.MainModule
import com.niuda.a3jidi.laok.booklook.di.scopes.PerActivity
import com.niuda.a3jidi.laok.booklook.view.activity.MainActivity
import dagger.Component

/**
*   作者: created by chenzhesheng on 2018/5/20 09:23
 *
 * MainComponent继承了ActivityComponent，假如ActivityComponent中定义了创建类实例方法，则MainComponent中必须提供@Inject或@Provides对应的
 * 初始化类实例的方法
 *
*/
@PerActivity
@Component(dependencies = [(AppComponent::class)], modules = [(MainModule::class),(ActivityModule::class)])
interface MainComponent : ActivityComponent{
    fun inject(mainActivity: MainActivity)

//    fun mainFragmentComponent(): MainFragmentComponent
}