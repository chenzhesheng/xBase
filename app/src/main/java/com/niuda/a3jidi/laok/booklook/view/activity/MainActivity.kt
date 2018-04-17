package com.niuda.a3jidi.laok.booklook.view.activity

import android.view.View
import android.widget.Toast
import com.niuda.a3jidi.laok.R
import com.niuda.a3jidi.laok.booklook.model.Book
import com.niuda.a3jidi.laok.booklook.model.IBookView
import com.niuda.a3jidi.laok.booklook.presenter.BookPresenter
import com.niuda.a3jidi.lib_base.base.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    private val mBookPresenter = BookPresenter(this)


    private val mBookView = object : IBookView {
        override fun onSuccess(book: Book) {
            button.visibility = View.GONE
            textview.text = book.toString()
        }

        override fun onError(result: String) {
            Toast.makeText(this@MainActivity, result, Toast.LENGTH_SHORT).show()
        }
    }


    override fun initLayout(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        mBookPresenter.onCreate()
        mBookPresenter.attachView(mBookView)
        button.setOnClickListener {
            mBookPresenter.getSearchBook("孙子兵法", "", 1, 10)
        }
    }

    override fun initListener() {

    }

    override fun initData() {

    }


    override fun onDestroy() {
        super.onDestroy()
        mBookPresenter.onStop()
    }

}
