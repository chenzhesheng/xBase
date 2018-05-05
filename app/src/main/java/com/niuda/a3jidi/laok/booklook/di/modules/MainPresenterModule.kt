package com.niuda.a3jidi.laok.booklook.di.modules

import com.niuda.a3jidi.laok.booklook.contract.API
import com.niuda.a3jidi.laok.booklook.contract.BookContract
import com.niuda.a3jidi.laok.booklook.model.MainModel
import com.niuda.a3jidi.laok.booklook.ui.activity.MainActivity
import com.niuda.a3jidi.lib_base.base.base.BaseView
import dagger.Module
import dagger.Provides

/**
* 作者: created by chenzhesheng on 2017/6/16 10:23
*/
@Module
class MainPresenterModule {

    @Provides
    fun modelProvider(baseView: BaseView, api: API): MainModel {
        return MainModel(baseView, api)
    }

    @Provides
    fun viewProvider(activity: MainActivity): BookContract.BookView {
        return activity
    }
}