package com.niuda.a3jidi.laok.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.niuda.a3jidi.laok.R;
import com.niuda.a3jidi.laok.service.entity.Book;
import com.niuda.a3jidi.laok.service.presenter.BookPresenter;
import com.niuda.a3jidi.laok.service.view.BookView;

public class MainActivity extends AppCompatActivity {
    private TextView mTextView;
    private Button mButton;
    private BookPresenter mBookPresenter = new BookPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.textview);
        mButton = (Button) findViewById(R.id.button);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBookPresenter.getSearchBook("孙子兵法",null,1,10);
            }
        });

        mBookPresenter.onCreate();
        mBookPresenter.attachView(mBookView);
    }

    private BookView mBookView = new BookView() {
        @Override
        public void onSuccess(Book book) {
            mButton.setVisibility(View.GONE);
            mTextView.setText(book.toString());
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
