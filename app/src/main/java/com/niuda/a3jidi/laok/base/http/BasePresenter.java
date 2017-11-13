package com.niuda.a3jidi.laok.base.http;

import android.content.Intent;

import com.niuda.a3jidi.laok.base.view.View;

/**
 * (￣▽￣)"
 *
 * @version V1.0 :
 * @author: Administrator
 * @date: 2017-11-12 15:42
 */

public interface BasePresenter {
    void onCreate();

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void attachView(View view); // 用于绑定View

    void attachIncomingIntent(Intent intent);
}
