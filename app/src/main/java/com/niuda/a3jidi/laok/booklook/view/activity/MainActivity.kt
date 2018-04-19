package com.niuda.a3jidi.laok.booklook.view.activity

import android.content.Context
import android.os.Bundle
import com.niuda.a3jidi.laok.R
import com.niuda.a3jidi.laok.booklook.contract.BookContract
import com.niuda.a3jidi.laok.booklook.model.pojo.Book
import com.niuda.a3jidi.laok.booklook.presenter.BookPresenter
import com.niuda.a3jidi.lib_base.base.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity(),BookContract.BookView {

    override fun getViewContext(): Context = this

    override fun layoutResId(): Int  = R.layout.activity_main

    @Inject
    lateinit var presenter: BookPresenter

    override fun onCreated(savedInstanceState: Bundle?) {
        initToolbar(true).setPageTitle("首页")
        initView()
    }


    private fun initView() {
        button_main.setOnClickListener {
            presenter.getBookView()
        }
    }


    override fun Success(it: Book) {
        textview.text = it.toString()
    }

}
