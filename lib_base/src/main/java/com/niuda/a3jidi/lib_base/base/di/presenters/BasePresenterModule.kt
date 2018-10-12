package com.niuda.a3jidi.lib_base.base.di.presenters

import com.niuda.a3jidi.lib_base.base.base.BaseView
import com.niuda.a3jidi.lib_base.base.di.modules.BaseModel
import dagger.Module
import dagger.Provides

/**
* 作者: created by chenzhesheng on 2017/6/16 10:23
*/
@Module
open class BasePresenterModule {

    @Provides
    fun provider(): BaseModel {
        return BaseModel()
    }

    @Provides
    fun viewProvider(baseView: BaseView): BaseView {
        return baseView
    }
}