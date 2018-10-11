package com.niuda.a3jidi.laok.booklook.ui.activity

import android.content.Context
import android.os.Bundle
import com.niuda.a3jidi.laok.R
import com.niuda.a3jidi.laok.booklook.contract.API
import com.niuda.a3jidi.laok.booklook.contract.BookContract
import com.niuda.a3jidi.laok.booklook.model.pojo.Book
import com.niuda.a3jidi.laok.booklook.presenter.BookPresenter
import com.niuda.a3jidi.lib_base.base.base.BaseActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

//@TranslucentStatusBar
class MainActivity : BaseActivity(),BookContract.BookView {

    lateinit var api: API

    override fun getViewContext(): Context = this

    override fun layoutResId(): Int  = R.layout.activity_main

    @Inject lateinit var presenter: BookPresenter

    override fun onCreated(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        initToolbar(true).setPageTitle("首页")
        initView()
    }


    private fun initView() {
        button_main.setOnClickListener {
            presenter.getBookView(this, api)
        }
    }


    override fun Success(it: Book) {
        textview.text = it.toString()
    }

}
