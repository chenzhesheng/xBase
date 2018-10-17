package com.niuda.a3jidi.laok.booklook.mvp.ui.activity

import android.content.Context
import android.os.Bundle
import com.niuda.a3jidi.laok.R
import com.niuda.a3jidi.laok.booklook.mvp.contract.BookContract
import com.niuda.a3jidi.laok.booklook.mvp.model.entity.Book
import com.niuda.a3jidi.laok.booklook.mvp.presenter.BookPresenter

class MainActivity : BaseActivity(),BookContract.BookView {


    override fun getViewContext(): Context = this

    override fun layoutResId(): Int  = R.layout.activity_main

    var presenter = BookPresenter()

    override fun onCreated(savedInstanceState: Bundle?) {
        initToolbar(true).setPageTitle("首页")
        initView()
    }


    private fun initView() {
        button_main.setOnClickListener {
            presenter.getBookView(this)
        }
    }


    override fun Success(it: Book) {
        textview.text = it.toString()
    }

}