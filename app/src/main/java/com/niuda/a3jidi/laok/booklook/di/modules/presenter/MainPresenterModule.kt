package com.niuda.a3jidi.laok.booklook.di.modules.presenter

import com.niuda.a3jidi.laok.booklook.contract.BookContract
import com.niuda.a3jidi.laok.booklook.ui.activity.MainActivity
import com.niuda.a3jidi.lib_base.base.di.presenters.BasePresenterModule
import dagger.Module
import dagger.Provides

/**
* 作者: created by chenzhesheng on 2017/6/16 10:23
*/
@Module
class MainPresenterModule : BasePresenterModule() {

    @Provides
    fun viewProvider(mainActivity: MainActivity): BookContract.BookView {
        return mainActivity
    }
}