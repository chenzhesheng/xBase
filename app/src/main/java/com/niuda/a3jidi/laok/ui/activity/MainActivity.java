package com.niuda.a3jidi.laok.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.niuda.a3jidi.laok.R;
import com.niuda.a3jidi.laok.service.RetrofitService;
import com.niuda.a3jidi.laok.service.entity.Book;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.textview);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.douban.com/v2/")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        RetrofitService service = retrofit.create(RetrofitService.class);
        Observable<Book> bookCall = service.getSearchBook("孙子兵法", null, 0, 1);
        bookCall.subscribeOn(Schedulers.io())   //请求数据的事件发生在io线程
                .observeOn(AndroidSchedulers.mainThread())  //请求完成后在主线程更显UI
                .subscribe(new Observer<Book>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Book value) {
                        mTextView.setText(value.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        //所有事件都完成，可以做些操作。。。
                    }
                });

    }
}
