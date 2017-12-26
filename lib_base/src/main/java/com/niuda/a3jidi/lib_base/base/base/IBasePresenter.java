package com.niuda.a3jidi.lib_base.base.base;

/**
 * (￣▽￣)"
 *
 * @version V1.0 :
 * @author: Administrator
 * @date: 2017-11-12 15:42
 */

public interface IBasePresenter {
    void attachView(IBaseView baseView);

    void onCreate();

    void onStop();
    //绑定数据
    void subscribe();
    //解除绑定
    void unSubscribe();
}
