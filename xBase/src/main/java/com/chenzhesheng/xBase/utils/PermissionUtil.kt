/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.chenzhesheng.xBase.utils

import android.Manifest
import com.tbruyelle.rxpermissions2.Permission
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.annotations.NonNull
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import timber.log.Timber
import java.util.*

/**
 * ================================================
 * 权限请求工具类
 *
 * @see [PermissionUtil wiki 官方文档](https://github.com/JessYanCoding/MVPArms/wiki.3.9)
 * Created by JessYan on 17/10/2016 10:09
 * [Contact me](mailto:jess.yan.effort@gmail.com)
 * [Follow me](https://github.com/JessYanCoding)
 * ================================================
 */
class PermissionUtil private constructor() {


    init {
        throw IllegalStateException("you can't instantiate me!")
    }

    interface RequestPermission {
        /**
         * 权限请求成功
         */
        fun onRequestPermissionSuccess()

        /**
         * 用户拒绝了权限请求, 权限请求失败, 但还可以继续请求该权限
         *
         * @param permissions 请求失败的权限名
         */
        fun onRequestPermissionFailure(permissions: List<String>)

        /**
         * 用户拒绝了权限请求并且用户选择了以后不再询问, 权限请求失败, 这时将不能继续请求该权限, 需要提示用户进入设置页面打开该权限
         *
         * @param permissions 请求失败的权限名
         */
        fun onRequestPermissionFailureWithAskNeverAgain(permissions: List<String>)
    }

    companion object {
        val TAG = "Permission"


        fun requestPermission(requestPermission: RequestPermission, rxPermissions: RxPermissions, errorHandler: RxErrorHandler, vararg permissions: String) {
            if (permissions == null || permissions.size == 0) return

            val needRequest = ArrayList<String>()
            for (permission in permissions) { //过滤调已经申请过的权限
                if (!rxPermissions.isGranted(permission)) {
                    needRequest.add(permission)
                }
            }

            if (needRequest.isEmpty()) {//全部权限都已经申请过，直接执行操作
                requestPermission.onRequestPermissionSuccess()
            } else {//没有申请过,则开始申请
                rxPermissions
                        .requestEach(*needRequest.toTypedArray())
                        .buffer(permissions.size)
                        .subscribe(object : ErrorHandleSubscriber<List<Permission>>(errorHandler) {
                            override fun onNext(@NonNull permissions: List<Permission>) {
                                val failurePermissions = ArrayList<String>()
                                val askNeverAgainPermissions = ArrayList<String>()
                                for (p in permissions) {
                                    if (!p.granted) {
                                        if (p.shouldShowRequestPermissionRationale) {
                                            failurePermissions.add(p.name)
                                        } else {
                                            askNeverAgainPermissions.add(p.name)
                                        }
                                    }
                                }
                                if (failurePermissions.size > 0) {
                                    Timber.tag(TAG).d("Request permissions failure")
                                    requestPermission.onRequestPermissionFailure(failurePermissions)
                                }

                                if (askNeverAgainPermissions.size > 0) {
                                    Timber.tag(TAG).d("Request permissions failure with ask never again")
                                    requestPermission.onRequestPermissionFailureWithAskNeverAgain(askNeverAgainPermissions)
                                }

                                if (failurePermissions.size == 0 && askNeverAgainPermissions.size == 0) {
                                    Timber.tag(TAG).d("Request permissions success")
                                    requestPermission.onRequestPermissionSuccess()
                                }
                            }
                        })
            }

        }


        /**
         * 请求摄像头权限
         */
        fun launchCamera(requestPermission: RequestPermission, rxPermissions: RxPermissions, errorHandler: RxErrorHandler) {
            requestPermission(requestPermission, rxPermissions, errorHandler, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
        }


        /**
         * 请求外部存储的权限
         */
        fun externalStorage(requestPermission: RequestPermission, rxPermissions: RxPermissions, errorHandler: RxErrorHandler) {
            requestPermission(requestPermission, rxPermissions, errorHandler, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }


        /**
         * 请求发送短信权限
         */
        fun sendSms(requestPermission: RequestPermission, rxPermissions: RxPermissions, errorHandler: RxErrorHandler) {
            requestPermission(requestPermission, rxPermissions, errorHandler, Manifest.permission.SEND_SMS)
        }


        /**
         * 请求打电话权限
         */
        fun callPhone(requestPermission: RequestPermission, rxPermissions: RxPermissions, errorHandler: RxErrorHandler) {
            requestPermission(requestPermission, rxPermissions, errorHandler, Manifest.permission.CALL_PHONE)
        }


        /**
         * 请求获取手机状态的权限
         */
        fun readPhonestate(requestPermission: RequestPermission, rxPermissions: RxPermissions, errorHandler: RxErrorHandler) {
            requestPermission(requestPermission, rxPermissions, errorHandler, Manifest.permission.READ_PHONE_STATE)
        }
    }

}

