package com.mvp.chenzhesheng.daggertest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject lateinit var userManager: UserManager

    @Inject lateinit var apiService: ApiService

//    @Inject @Named("dev") lateinit var apiService: ApiService
//    @Inject @Named("release") lateinit var apiService1: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        DaggerUserCompoent.builder().userModule(UserModule(this)).build().Inject(this)

        apiService.register()
//        apiService1.register()

        userManager.register()

    }
}
