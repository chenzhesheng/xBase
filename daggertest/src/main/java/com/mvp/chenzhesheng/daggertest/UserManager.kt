package com.mvp.chenzhesheng.daggertest

/**
 * 作者: created by chenzhesheng on 2018/4/19 16:03
 */
class UserManager(var apiService: ApiService, var userStroe: UserStroe){


    fun register(){
        apiService.register()
        userStroe.login()
    }


}