package com.niuda.a3jidi.laok.base.view;

/**
 * (￣▽￣)"
 *
 * @version V1.0 :
 * @author: Administrator
 * @date: 2017-11-13 23:34
 */

public class BaseActivity {

    /**
     * 线程调度
     */
//    protected <T> ObservableTransformer<T, T> compose(final LifecycleTransformer<T> lifecycle) {
//        return new ObservableTransformer<T, T>() {
//            @Override
//            public ObservableSource<T> apply(Observable<T> observable) {
//                return observable
//                        .subscribeOn(Schedulers.io())
//                        .doOnSubscribe(new Consumer<Disposable>() {
//                            @Override
//                            public void accept(Disposable disposable) throws Exception {
//                                // 可添加网络连接判断等
//                                if (!NetworkUtils.isNetworkAvailable(BaseActivity.this)) {
//                                    Toast.makeText(BaseActivity.this, R.string.toast_network_error, Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        })
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .compose(lifecycle);
//            }
//        };
//    }
}
