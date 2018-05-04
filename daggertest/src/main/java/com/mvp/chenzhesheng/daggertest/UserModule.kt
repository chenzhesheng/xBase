package com.mvp.chenzhesheng.daggertest

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Named

/**
 * 作者: created by chenzhesheng on 2018/4/19 14:30
 */

@Module
class UserModule(var context: Context){

    @Provides
    @Named("dev")
    fun provideApiServiceDev(): ApiService = ApiService(context)

    @Provides
    @Named("release")
    fun provideApiServiceRelease(url: String): ApiService = ApiService(url)

    @Provides
    fun provideUrl():String = "www.baidu.com"

    @Provides
    fun provideUserManager(apiService: ApiService, userStroe: UserStroe):UserManager {
        return UserManager(apiService, userStroe)
    }
}