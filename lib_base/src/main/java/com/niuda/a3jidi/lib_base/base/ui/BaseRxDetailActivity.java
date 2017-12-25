package com.niuda.a3jidi.lib_base.base.ui;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * (￣▽￣)"
 *
 * @version V1.0 :统一管理所有的订阅生命周期
 * @author: Administrator
 * @date: 2017-11-11 14:07
 */
public abstract class BaseRxDetailActivity extends BaseActivity {

    private CompositeDisposable compositeDisposable;

    public void addDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    public void dispose() {
        if (compositeDisposable != null) compositeDisposable.dispose();
    }
}
