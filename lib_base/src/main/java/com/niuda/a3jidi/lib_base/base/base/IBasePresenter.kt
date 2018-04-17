package com.niuda.a3jidi.lib_base.base.base

/**
 * (￣▽￣)"
 *
 * @version V1.0 :
 * @author: Administrator
 * @date: 2017-11-12 15:42
 */

interface IBasePresenter {
    fun attachView(baseView: IBaseView)

    fun onCreate()

    fun onStop()
    //绑定数据
    fun subscribe()

    //解除绑定
    fun unSubscribe()
}
