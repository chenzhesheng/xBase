package com.niuda.a3jidi.laok.booklook.view.activity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.niuda.a3jidi.laok.R;
import com.niuda.a3jidi.laok.booklook.model.Book;
import com.niuda.a3jidi.laok.booklook.model.IBookView;
import com.niuda.a3jidi.laok.booklook.presenter.BookPresenter;
import com.niuda.a3jidi.lib_base.base.base.BaseActivity;

import butterknife.BindView;

public class MainActivity extends BaseActivity{
    @BindView(R.id.textview)
    TextView mTextview;
    @BindView(R.id.button)
    Button mButton;
    private BookPresenter mBookPresenter = new BookPresenter(this);


    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mBookPresenter.onCreate();
        mBookPresenter.attachView(mBookView);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TAG","sendRequest");
                mBookPresenter.getSearchBook("孙子兵法", null, 1, 10);
            }
        });
    }


    private IBookView mBookView = new IBookView() {
        @Override
        public void onSuccess(Book book) {
            mButton.setVisibility(View.GONE);
            mTextview.setText(book.toString());
        }

        @Override
        public void onError(String result) {
            Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBookPresenter.onStop();
    }

}
