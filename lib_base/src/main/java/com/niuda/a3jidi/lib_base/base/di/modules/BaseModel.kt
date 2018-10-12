package com.niuda.a3jidi.lib_base.base.di.modules

import com.google.gson.Gson
import dagger.Module
import javax.inject.Inject

/**
* 作者: created by chenzhesheng on 2017/6/16 10:19
*/

@Module
open class BaseModel(){

    @Inject
    lateinit var mGson : Gson

}