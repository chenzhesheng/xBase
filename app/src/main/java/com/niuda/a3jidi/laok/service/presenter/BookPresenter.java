package com.niuda.a3jidi.laok.service.presenter;

import android.content.Context;
import android.content.Intent;

import com.niuda.a3jidi.laok.service.entity.Book;
import com.niuda.a3jidi.laok.service.manager.DataManager;
import com.niuda.a3jidi.laok.service.view.BookView;
import com.niuda.a3jidi.laok.service.view.View;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * (￣▽￣)"
 *
 * @version V1.0 :
 * @author: Administrator
 * @date: 2017-11-12 15:45
 */

public class BookPresenter implements Presenter {
    private Context mContext;
    private DataManager mManager;
    private BookView mBookView;
    private Book mBook;
//    private CompositeSubscription mCompositeSubscription;

    public BookPresenter(Context context){
        this.mContext = context;
    }

    @Override
    public void onCreate() {
        mManager = new DataManager(mContext);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {
//        if (mCompositeSubscription.hasSubscriptions()){
//            mCompositeSubscription.unsubscribe();
//        }
    }

    @Override
    public void attachView(View view) {
        mBookView = (BookView) view;
    }

    @Override
    public void attachIncomingIntent(Intent intent) {

    }


    public void getSearchBook(String name,String tag,int start,int count){
        mManager.getSearchBook(name, tag, start, count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Book>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Book value) {      // 请求成功1
                        mBook = value;
                    }

                    @Override
                    public void onError(Throwable e) {      //请求失败-1
                        e.printStackTrace();
                        mBookView.onError("请求失败！");
                    }

                    @Override
                    public void onComplete() {      // 返回成功2
                        if(mBook != null){
                            mBookView.onSuccess(mBook);
                        }
                    }
                });
    }

}
